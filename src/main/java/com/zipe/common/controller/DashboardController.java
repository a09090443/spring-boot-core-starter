package com.zipe.common.controller;

import com.zipe.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/11/23 上午 11:46
 **/
@Slf4j
@Controller
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

    @Override
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView initPage() {
        return new ModelAndView("main/dashboard");
    }
}
