package com.zipe.employee.controller;

import com.zipe.base.controller.BaseController;
import com.zipe.common.service.SysMenuService;
import com.zipe.employee.model.UserAndSupervisorEmail;
import com.zipe.employee.service.EmployeeService;
import com.zipe.example.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Gary Tsai
 * @created : @Date 2021/3/4 上午 08:38
 **/
@RestController
@RequestMapping("/employee")
public class TestController extends BaseController {

    @Autowired
    private EmployeeService employeeServiceImpl;

    @Autowired
    private ExampleService exampleServiceImpl;

    @Autowired
    private SysMenuService sysMenuServiceImpl;

    @Override
    public ModelAndView initPage() {
        return null;
    }

    @PostMapping("/findEmployee")
    public String findEmployee(@RequestBody List<String> employeeIds ){
        List<String> ids = employeeIds.stream().map( employeeId -> employeeServiceImpl.findEmployeeByEmpNo(employeeId).getPnIdNo()).collect(Collectors.toList());
        System.out.println(ids);
        return "Success";
    }

    @GetMapping("/test")
    public String test(){
        sysMenuServiceImpl.findSysMenuByTreeLevel(3);
//        employeeServiceImpl.findEmployeeByEmpNo("B04575");

        String fgNo = "B03925";
        List<UserAndSupervisorEmail> userAndSupervisorEmails = employeeServiceImpl.findUserAndSupervisorEmail(fgNo);
//        employeeServiceImpl.findEmployeeByEmpNo("B04575");
        return "";
    }
}
