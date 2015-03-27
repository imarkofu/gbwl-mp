package me.gbwl.mp.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import me.gbwl.mp.base.Article;
import me.gbwl.mp.base.StringUtil;
import me.gbwl.mp.util.DateUtil;
import me.gbwl.mp.util.WieParameter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(value="/clovec.do", method=RequestMethod.GET)
	public ModelAndView clovecGet(HttpServletRequest request) {
		return new ModelAndView("demo");
	}
	
	@RequestMapping(value="/clovec.do", method=RequestMethod.POST)
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
		try {
			path = type+"/"+DateUtil.formatDate(now, "yyyy-MM")+"/"+StringUtil.randomString(16) +".html";
			String model = WieParameter.class.getClassLoader().getResource("model").getPath();
			File modelFile = new File(model);
			FileReader fr = new FileReader(modelFile);
			char[] t = new char[(int) modelFile.length()];
			fr.read(t);
			String modelContent = new String(t);
			modelContent = modelContent.replaceAll("\\$title", title);
			modelContent = modelContent.replaceAll("\\$main_domain", WieParameter.getInstance().getDomain());
			modelContent = modelContent.replaceAll("\\$nav_url", WieParameter.getInstance().getDomain()+type+"/index.html");
			modelContent = modelContent.replaceAll("\\$nav_name", WieParameter.getInstance().getType(Integer.parseInt(type)));
			modelContent = modelContent.replaceAll("\\$date", DateUtil.formatDate(now, "yyyy-MM-dd"));
			modelContent = modelContent.replaceAll("\\$author", author);
			modelContent = modelContent.replaceAll("\\$body", body);
			modelContent = modelContent.replaceAll("\\$domain", WieParameter.getInstance().getDomain()+"/"+path);
			File file = new File(request.getSession().getServletContext().getRealPath("/")+path);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileWriter fw = new FileWriter(file, false);
			fw.write(modelContent);
			fw.flush();
			fw.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);result.put("msg", "静态页面生成失败");return result;
		}
		//保存目录结构
		try {
			String articles = WieParameter.class.getClassLoader().getResource("articles").getPath();
			File file = new File(articles);
			FileReader fr = new FileReader(file);
			char[] t = new char[(int) file.length()];
			fr.read(t);
			String content = new String(t);
			fr.close();
			List<Article> list = null;
			if (StringUtil.isEmpty(content) || "".equals(content.trim())) {
				list = new ArrayList<Article>();
			} else {
				list = JSON.parseArray(content, Article.class);
				result.put("result", false);result.put("msg", "JSON转对象异常");return result;
			}
			Article article = new Article();
			article.setDate(DateUtil.formatDate(now, "yyyy-MM-dd"));
			article.setSummary(summary);
			article.setTitle(title);
			article.setType(Integer.parseInt(type));
			article.setUrl(WieParameter.getInstance().getDomain()+"/"+path);
			list.add(article);
			FileWriter fw = new FileWriter(file, false);
			fw.write(JSON.toJSONString(list));
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);result.put("msg", "目录更新失败");return result;
		}
		result.put("result", true);
		result.put("msg", "保存成功");
		return result;
	}
}
