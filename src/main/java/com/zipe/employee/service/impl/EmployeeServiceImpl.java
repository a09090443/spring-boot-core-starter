package com.zipe.employee.service.impl;

import com.zipe.base.annotation.DS;
import com.zipe.common.model.SqlQuery;
import com.zipe.common.repository.PersonnelRepository;
import com.zipe.employee.model.Personnel;
import com.zipe.employee.model.UserAndSupervisorEmail;
import com.zipe.employee.repository.EmployeeJDBC;
import com.zipe.employee.service.EmployeeService;
import com.zipe.enums.ResourceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/12/11 下午 04:51
 **/
@Slf4j
@Service
@DS(value="example")
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeJDBC jdbc;

    private final PersonnelRepository personnelRepository;

    @Autowired
    EmployeeServiceImpl(EmployeeJDBC jdbc, PersonnelRepository personnelRepository){
        this.jdbc = jdbc;
        this.personnelRepository = personnelRepository;
    }

    @Override
    public List<UserAndSupervisorEmail> findUserAndSupervisorEmail(String empNo) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("empNo", empNo);
        SqlQuery<UserAndSupervisorEmail> sqlQuery = new SqlQuery<>("employee", "FIND_SUPERVISOR_AND_USER_EMAIL", UserAndSupervisorEmail.class, null, param);
        ResourceEnum resource = ResourceEnum.SQL.getResource(sqlQuery.getSqlDir(), sqlQuery.getSqlFileName());
        return jdbc.queryForList(resource, null, sqlQuery.getParams(), UserAndSupervisorEmail.class);
    }

    @Override
    public Personnel findEmployeeByEmpNo(String empNo) {
        return Optional.ofNullable(personnelRepository.findByPnEmpNo(empNo)).orElse(new Personnel());
    }

    @Override
    @Transactional
    public void saveEmployee(Personnel personnel) {
        personnelRepository.save(personnel);
    }

}
