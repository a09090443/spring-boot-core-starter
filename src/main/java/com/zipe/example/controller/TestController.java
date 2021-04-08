package com.zipe.example.controller;

import com.zipe.base.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : Gary Tsai
 * @created : @Date 2021/3/22 下午 03:20
 **/
@RestController
@RequestMapping("/example")
public class TestController extends BaseController {

    @GetMapping("/test")
    public String test() {
        return "Test";
    }

    @Override
    public ModelAndView initPage() {
        return null;
    }
}
