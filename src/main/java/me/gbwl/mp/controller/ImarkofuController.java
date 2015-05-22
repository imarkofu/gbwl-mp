package me.gbwl.mp.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import me.gbwl.mp.base.Article;
import me.gbwl.mp.base.StringUtil;
import me.gbwl.mp.util.BlackIP;
import me.gbwl.mp.util.DateUtil;
import me.gbwl.mp.util.RequestUtils;
import me.gbwl.mp.util.WieParameter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * @Title: ImarkofuController.java<br>
 * @package: me.gbwl.mp.controller<br>
 * @Description:TODO<br>
 * @author gbwl<br>
 * @date 2015年3月27日 下午1:42:45<br>
 */
@Controller
@RequestMapping(value="/imarkofu")
public class ImarkofuController {

	private static final Logger logger = Logger.getLogger(ImarkofuController.class);
	private static Map<String, Integer> ips = new HashMap<String, Integer>();
	private static Date date;
	private static final String SESSION_USER = "SESSION_USER";
	@RequestMapping(value="/clovec.do", method=RequestMethod.GET)
	public ModelAndView clovecGet(HttpServletRequest request) {
		String ip = RequestUtils.getIpAddr(request);
		if (BlackIP.getInstance().isBlackIP(ip)) {
			return new ModelAndView("login");
		}
		Object session_user = request.getSession().getAttribute(SESSION_USER);
		if (session_user != null && session_user.equals(WieParameter.getInstance().getUsername())) {
			return new ModelAndView("demo");
		}
		return new ModelAndView("login");
	}
	
	/**
	 * 避免密码错误之后刷新浏览器404错误
	 * @return
	 */
	@RequestMapping(value="/checkLogin.do", method=RequestMethod.GET)
	public ModelAndView checkLogin(HttpSession session) {
		Object session_user = session.getAttribute(SESSION_USER);
		if (session_user != null && session_user.equals(WieParameter.getInstance().getUsername())) {
			return new ModelAndView("demo");
		}
		return new ModelAndView("login");
	}
	
	@RequestMapping(value="/checkLogin.do", method=RequestMethod.POST)
	public ModelAndView checkLogin(HttpServletRequest request) {
		if (date == null) {
			date = new Date();
			ips.clear();
		} else {
			if (!DateUtil.formatDate(date, "yyyy-MM-dd").equals(DateUtil.formatDate(new Date(), "yyyy-MM-dd"))) {
				date = new Date();	//为了保证ips不过度膨胀，每日清空该列表
				ips.clear();
			}
		}
		String ip = RequestUtils.getIpAddr(request);
		if (BlackIP.getInstance().isBlackIP(ip)) {
			return new ModelAndView("login");
		}
		Integer count = ips.get(ip)==null?0:ips.get(ip);
		if (count > WieParameter.getInstance().getMaxCount()) {
			BlackIP.getInstance().addBlackIP(ip);	//怀疑该ip是在尝试暴力破解密码
			ips.remove(ip);	//已经加入黑名单故从ips中删去以节省内存
			return new ModelAndView("login");
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if ((WieParameter.getInstance().getUsername().equals(username) && WieParameter.getInstance().getPassword().equals(password))) {
			if (request.getSession().getAttribute(SESSION_USER) == null) {
				request.getSession().setAttribute(SESSION_USER, username);
			}
			return new ModelAndView("demo");
		}
		ips.remove(ip);
		ips.put(ip, ++count);
		return new ModelAndView("login");
	}
	
	@RequestMapping(value="/clovec.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> clovecPost(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String type = request.getParameter("type");
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String summary = request.getParameter("summary");
		String body = request.getParameter("body");
		if (!StringUtil.isNumeric(type) || Integer.parseInt(type) <= 0 || Integer.parseInt(type) >= 8) {
			result.put("result", false);result.put("msg", "分类错误");return result;
		}
		if (StringUtil.isEmpty(title)) {
			result.put("result", false);result.put("msg", "标题不能为空");return result;
		}
		if (StringUtil.isEmpty("author")) {
			result.put("result", false);result.put("msg", "编辑不能为空");return result;
		}
		if (StringUtil.isEmpty(summary)) {
			result.put("result", false);result.put("msg", "概要不能为空");return result;
		}
		if (StringUtil.isEmpty(body)) {
			result.put("result", false);result.put("msg", "内容不能为空");return result;
		}
		Date now = new Date();
		//开始生成页面
		String path;
		FileReader fr = null;
		FileWriter fw = null;
		try {
			path = type+"/"+DateUtil.formatDate(now, "yyyy-MM")+"/"+StringUtil.randomString(16) +".html";
			String model = WieParameter.class.getClassLoader().getResource("model").getPath();
			File modelFile = new File(model);
			fr = new FileReader(modelFile);
			char[] t = new char[(int) modelFile.length()];
			fr.read(t);
			String modelContent = new String(t);
			modelContent = modelContent.replaceAll("\\$title", title);
			modelContent = modelContent.replaceAll("\\$main_domain", WieParameter.getInstance().getDomain());
			modelContent = modelContent.replaceAll("\\$nav_url", WieParameter.getInstance().getDomain()+type+"/index.html");
			modelContent = modelContent.replaceAll("\\$nav_name", WieParameter.getInstance().getType(Integer.parseInt(type)));
			modelContent = modelContent.replaceAll("\\$date", DateUtil.formatDate(now, "yyyy-MM-dd"));
			modelContent = modelContent.replaceAll("\\$author", author);
			modelContent = modelContent.replaceAll("\\$body", Matcher.quoteReplacement(body));
			modelContent = modelContent.replaceAll("\\$domain", WieParameter.getInstance().getDomain()+path);
			File file = new File(request.getSession().getServletContext().getRealPath("/")+"/"+path);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			fw = new FileWriter(file, false);
			fw.write(modelContent);
			fw.flush();
		} catch (FileNotFoundException e) {
			logger.error("模版文件不存在：" + e.getMessage(), e.getCause());
			result.put("result", false);result.put("msg", "模版文件不存在");return result;
		} catch (IOException e) {
			logger.error("读取模版或生成html页面异常：" + e.getMessage(), e.getCause());
			result.put("result", false);result.put("msg", "读取模版或生成html页面异常");return result;
		} catch (Exception e) {
			logger.error("读取模版或生成Html页面未知异常：" + e.getMessage(), e.getCause());
			result.put("result", false);result.put("msg", "未知异常");return result;
		} finally {
			if (fw != null) try { fw.close(); } catch (Exception e) { }
			if (fr != null) try { fr.close(); } catch (Exception e) { }
		}
		//保存目录结构
		try {
			String articles = WieParameter.class.getClassLoader().getResource("articles").getPath();
			File file = new File(articles);
			fr = new FileReader(file);
			char[] t = new char[(int) file.length()];
			fr.read(t);
			String content = new String(t);
			List<Article> list = null;
			if (StringUtil.isEmpty(content) || "".equals(content.trim())) {
				list = new LinkedList<Article>();
			} else {
				try {
					list = JSON.parseArray(content.trim(), Article.class);
				} catch (Exception e) {
					logger.error("JSON转对象异常："+ e.getMessage(), e.getCause());
					result.put("result", false);result.put("msg", "JSON转对象异常");return result;
				}
			}
			Article article = new Article();
			article.setDate(DateUtil.formatDate(now, "yyyy-MM-dd"));
			article.setSummary(summary);
			article.setTitle(title);
			article.setType(Integer.parseInt(type));
			article.setUrl(WieParameter.getInstance().getDomain()+path);
			list.add(0, article);
			fw = new FileWriter(file, false);
			fw.write(JSON.toJSONString(list));
			fw.flush();
		} catch (FileNotFoundException e) {
			logger.error("目录文件不存在：" + e.getMessage(), e.getCause());
			result.put("result", false);result.put("msg", "目录文件不存在");return result;
		} catch (IOException e) {
			logger.error("读取或写目录文件异常：" + e.getMessage(), e.getCause());
			result.put("result", false);result.put("msg", "读取或写目录文件异常");return result;
		} catch (Exception e) {
			logger.error("更新目录文件未知异常：" + e.getMessage(), e.getCause());
			result.put("result", false);result.put("msg", "未知异常");return result;
		} finally {
			if (fw != null) try { fw.close(); } catch (Exception e) { }
			if (fr != null) try { fr.close(); } catch (Exception e) { }
		}
		result.put("result", true);
		result.put("msg", "保存成功");
		return result;
	}
}
