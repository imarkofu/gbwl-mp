package me.gbwl.mp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import me.gbwl.mp.base.Article;
import me.gbwl.mp.base.PageBean;
import me.gbwl.mp.base.StringUtil;
import me.gbwl.mp.util.WieParameter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoadController {

	@RequestMapping(value="/load.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> load(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String type = request.getParameter("type");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if (!StringUtil.isNumeric(pageNo) || Integer.parseInt(pageNo) <= 0) {
			pageNo = "1";
		}
		if (!StringUtil.isNumeric(pageSize) || Integer.parseInt(pageSize) <= 4 || Integer.parseInt(pageSize) >= 50) {
			pageSize = "10";
		}
		List<Article> articles = new ArrayList<Article>(WieParameter.getInstance().getArticlesCache());
		if (StringUtil.isNumeric(type) && Integer.parseInt(type) > 0 && Integer.parseInt(type) <= WieParameter.getInstance().getTypeSize()) {
			for (int i = 0; i < articles.size(); i ++) {
				if (articles.get(i).getType() != Integer.parseInt(type)) {
					articles.remove(i --);
				}
			}
		}
		PageBean<Article> pageBean = new PageBean<Article>();
		pageBean.setPageNo(Integer.parseInt(pageNo));
		pageBean.setPageSize(Integer.parseInt(pageSize));
		int from = (Integer.parseInt(pageNo)-1)*Integer.parseInt(pageSize);
		int to = Integer.parseInt(pageNo) * Integer.parseInt(pageSize);
		if (articles == null || articles.size() <= 0 || from >= articles.size()) {
			pageBean.setList(new ArrayList<Article>());
			pageBean.setRowCount(0);
		} else {
			if (to > articles.size())
				to = articles.size();
			pageBean.setList(articles.subList(from, to));
			pageBean.setRowCount(articles.size());
		}
		result.put("result", true);
		result.put("pageBean", pageBean);
		return result;
	}
}
