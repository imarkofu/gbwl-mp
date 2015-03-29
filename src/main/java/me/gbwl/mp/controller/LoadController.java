package me.gbwl.mp.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import me.gbwl.mp.base.Article;
import me.gbwl.mp.base.PageBean;
import me.gbwl.mp.base.StringUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoadController {

	private static LinkedList<Article> articles = new LinkedList<Article>();
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
		List<Article> list = articles.subList((Integer.parseInt(pageNo)-1)*Integer.parseInt(pageSize), Integer.parseInt(pageNo) * Integer.parseInt(pageSize));
		PageBean<Article> pageBean = new PageBean<Article>();
		pageBean.setPageNo(Integer.parseInt(pageNo));
		pageBean.setPageSize(Integer.parseInt(pageSize));
		
		
		return result;
	}
}
