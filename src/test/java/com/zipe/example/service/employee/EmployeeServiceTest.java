package com.zipe.example.service.employee;

import com.zipe.employee.model.Personnel;
import com.zipe.employee.model.UserAndSupervisorEmail;
import com.zipe.employee.service.EmployeeService;
import com.zipe.example.base.TestBase;
import org.castor.core.util.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/12/11 下午 01:56
 **/
public class EmployeeServiceTest extends TestBase {

    @Autowired
    private EmployeeService employeeServiceImpl;

    @Ignore
    @Test
    public void findEmployeeAndSupervisorTest(){
        String fgNo = "B03925";
        List<UserAndSupervisorEmail> userAndSupervisorEmails = employeeServiceImpl.findUserAndSupervisorEmail(fgNo);
        Assert.notNull(userAndSupervisorEmails, "It will find one record");
    }

    @Ignore
    @Test
    public void findEmployeeTest(){
        String fgNo = "B03925";
        Personnel personnel = employeeServiceImpl.findEmployeeByEmpNo(fgNo);
        Assert.notNull(personnel, "It will find one record");
    }

    @Test
    public void saveEmployeeTest(){
        String fgNo = "B04120";
        Personnel personnel = employeeServiceImpl.findEmployeeByEmpNo(fgNo);
        personnel.setPnLivePhoneNo("1234567**");
        employeeServiceImpl.saveEmployee(personnel);
    }
}
