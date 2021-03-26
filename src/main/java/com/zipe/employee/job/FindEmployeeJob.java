package com.zipe.employee.job;

import com.zipe.employee.model.Personnel;
import com.zipe.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 排程-定期尋找該名員工範例排程
 *
 * @author : Gary Tsai
 * @created : @Date 2021/03/04 下午 04:15
 **/
@Slf4j
public class FindEmployeeJob extends QuartzJobBean {

    @Autowired
    private EmployeeService employeeServiceImpl;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        Personnel personnel = employeeServiceImpl.findEmployeeByEmpNo("B02985");
        log.info("員工資訊 : " + personnel.toString());
    }
}
