package com.zipe.common.controller;

import com.zipe.base.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登入
 *
 * @author adam.yeh
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Override
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView initPage() {

		return new ModelAndView("/main/login");
	}

}
