package com.zipe.example.service.impl;

import com.zipe.base.annotation.DS;
import com.zipe.common.model.SqlQuery;
import com.zipe.employee.model.UserAndSupervisorEmail;
import com.zipe.enums.ResourceEnum;
import com.zipe.example.jdbc.ExampleJDBC;
import com.zipe.example.model.Gleepf;
import com.zipe.example.repository.GleepfRepository;
import com.zipe.example.service.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Gary Tsai
 * @created : @Date 2021/3/12 上午 09:51
 **/
@Slf4j
@Service
public class ExampleServiceImpl implements ExampleService {
    private final String SQL_FILE_DIR_KEY = "SQL_EXAMPLE";

    private final ExampleJDBC jdbc;

    private final GleepfRepository gleepfRepository;

    @Autowired
    ExampleServiceImpl(GleepfRepository gleepfRepository, ExampleJDBC jdbc) {
        this.gleepfRepository = gleepfRepository;
        this.jdbc = jdbc;
    }

    @Override
    public List<Gleepf> findAll() {
        return gleepfRepository.findAll();
    }

    @Override
    @DS(value="example")
    public Gleepf findByEE010(String ee010) {
        return gleepfRepository.findByEe010(ee010);
//        Map<String, Object> param = new HashMap<>();
//        param.put("userId", ee010);
//        SqlQuery<Gleepf> sqlQuery = new SqlQuery<>(SQL_FILE_DIR_KEY, "FIND_EMPLOYEE", Gleepf.class, null, param);
//        ResourceEnum resource = ResourceEnum.valueOf(sqlQuery.getSqlDir()).getResource(sqlQuery.getSqlFileName());
//        return jdbc.queryForBean(resource, null, sqlQuery.getParams(), Gleepf.class);
    }

    @Override
    @Transactional
    @DS(value="example")
    public void saveGleepf(Gleepf gleepf) {
        gleepfRepository.save(gleepf);
    }

    @Override
    @Transactional
    public void delGleepf(Gleepf gleepf) {
        gleepfRepository.delete(gleepf);
    }

    @Override
    @Transactional
    @DS(value="example")
    public void updateGleepf(Gleepf gleepf) {
        gleepfRepository.save(gleepf);
    }
}
