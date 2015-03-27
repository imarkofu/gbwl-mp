package me.gbwl.mp.webcontext;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.gbwl.mp.util.CollectionUtils;
import me.gbwl.mp.util.RequestUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LogHandlerInterceptor implements HandlerInterceptor {
	private static final Logger logger = Logger
			.getLogger(LogHandlerInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (logger.isDebugEnabled()) {
			@SuppressWarnings("rawtypes")
			Enumeration names = request.getAttributeNames();
			List<String> nameList = CollectionUtils.enum2List(names);
			nameList.remove("org.springframework.web.servlet.HandlerMapping.introspectTypeLevelMapping");
			nameList.remove("org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping.useDefaultSuffixPattern");
			nameList.remove("org.springframework.web.servlet.DispatcherServlet.FLASH_MAP_MANAGER");
			nameList.remove("org.springframework.web.servlet.DispatcherServlet.OUTPUT_FLASH_MAP");
			nameList.remove("org.springframework.web.servlet.DispatcherServlet.LOCALE_RESOLVER");
			nameList.remove("org.springframework.web.servlet.DispatcherServlet.THEME_SOURCE");
			nameList.remove("org.springframework.web.servlet.DispatcherServlet.THEME_RESOLVER");
			nameList.remove("org.springframework.web.servlet.DispatcherServlet.CONTEXT");
			nameList.remove("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
			nameList.remove("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping");
			// 最终决定的匹配url
			nameList.remove("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern");
			nameList.remove("HiddenHttpMethodFilter.FILTERED");
			nameList.remove("org.springframework.core.convert.ConversionService");
			for (int i = 0; i < nameList.size(); i++) {
				String name = nameList.get(i);
				nameList.set(i, name + "=" + request.getAttribute(name));
			}
			if (logger.isDebugEnabled()) {
				logger.debug("afterCompletion:"
						+ StringUtils.join(nameList, ","));
			}
		}
		if (ex != null) {
			logger.error(ex.getMessage(), ex);
		}

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (logger.isDebugEnabled()) {
			@SuppressWarnings("rawtypes")
			Enumeration names = request.getAttributeNames();
			List<String> nameList = CollectionUtils.enum2List(names);
			nameList.remove("org.springframework.web.servlet.HandlerMapping.introspectTypeLevelMapping");
			nameList.remove("org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping.useDefaultSuffixPattern");
			nameList.remove("org.springframework.web.servlet.DispatcherServlet.FLASH_MAP_MANAGER");
			nameList.remove("org.springframework.web.servlet.DispatcherServlet.OUTPUT_FLASH_MAP");
			nameList.remove("org.springframework.web.servlet.DispatcherServlet.LOCALE_RESOLVER");
			nameList.remove("org.springframework.web.servlet.DispatcherServlet.THEME_SOURCE");
			nameList.remove("org.springframework.web.servlet.DispatcherServlet.THEME_RESOLVER");
			nameList.remove("org.springframework.web.servlet.DispatcherServlet.CONTEXT");
			nameList.remove("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
			nameList.remove("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping");
			// 最终决定的匹配url
			nameList.remove("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern");
			nameList.remove("HiddenHttpMethodFilter.FILTERED");
			nameList.remove("org.springframework.core.convert.ConversionService");
			for (int i = 0; i < nameList.size(); i++) {
				String name = nameList.get(i);
				nameList.set(i, name + "=" + request.getAttribute(name));
			}
			if (logger.isDebugEnabled()) {
				logger.debug("postHandle:" + StringUtils.join(nameList, ","));
			}
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("apppath") == null) {
			StringBuilder appPath = new StringBuilder(request.getScheme());
			appPath.append("://");
			appPath.append(request.getServerName());
			if (request.getServerPort() != 80) {
				appPath.append(":");
				appPath.append(request.getServerPort());
			}
			if (request.getContextPath() != null) {
				appPath.append(request.getContextPath());
//				appPath.append("/");
			}
			session.setAttribute("apppath", appPath.toString());
		}
		String bestMatchingPattern = (String) request
				.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern");
		String ip = RequestUtils.getIpAddr(request);
		logger.info(ip + " RequestUrl:" + bestMatchingPattern);
		Map<?, ?> map = request.getParameterMap();
		List<String> list = new ArrayList<String>();
		for (Object o : map.entrySet()) {
			@SuppressWarnings("unchecked")
			Entry<String, String[]> e = (Entry<String, String[]>) o;
			list.add(e.getKey() + "=" + StringUtils.join(e.getValue(), "|"));
		}
		logger.info(ip + " RequestParameter:" + StringUtils.join(list, ","));
		return true;
	}
}
