package me.gbwl.mp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView clovec(HttpServletRequest request) {
		return new ModelAndView("demo");
	}
}
