package com.zipe.employee.service;

import com.zipe.employee.model.Personnel;
import com.zipe.employee.model.UserAndSupervisorEmail;

import java.util.List;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/12/11 下午 04:46
 **/
public interface EmployeeService {
    /**
     * 取得該員工及主管 email
     *
     * @param empNo
     * @return
     */
    List<UserAndSupervisorEmail> findUserAndSupervisorEmail(String empNo);

    Personnel findEmployeeByEmpNo(String empNo);

    void saveEmployee(Personnel personnel);
}
