package com.zipe.common.controller;

import com.zipe.base.controller.BaseController;
import com.zipe.common.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/11/26 下午 04:47
 **/
@Slf4j
@RestController
@RequestMapping("/menu")
public class SysMenuController extends BaseController {

    private final SysMenuService sysMenuServiceImpl;

    @Autowired
    SysMenuController(SysMenuService sysMenuServiceImpl) {
        this.sysMenuServiceImpl = sysMenuServiceImpl;
    }

    @Override
    public ModelAndView initPage() {
        return null;
    }

    @GetMapping(value = "/getMenuTree/{treeLevel}")
    public List<Map<String, Object>> getMenuTree(@PathVariable(value = "treeLevel") String treeLevel) {
        return sysMenuServiceImpl.findSysMenuByTreeLevel(Integer.parseInt(treeLevel));
    }
}
