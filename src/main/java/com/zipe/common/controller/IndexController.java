package com.zipe.common.controller;

import com.zipe.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首頁導向 dashboard
 *
 * @author gary.tsai 2019/12/6
 */
@Controller
public class IndexController extends BaseController {

    @Override
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView initPage() {
        return new ModelAndView("redirect:/dashboard");
    }
}
