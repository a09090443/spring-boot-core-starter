package com.zipe.employee.model;

import lombok.Data;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/12/11 下午 04:43
 **/
@Data
public class UserAndSupervisorEmail {

    // 員工姓名
    String empName;
    // 員工編號
    String empNo;
    // 員工 Email
    String empEmail;
    // 主管員編
    String supervisorNo;
    // 主管 Email
    String supervisorEmail;

}
