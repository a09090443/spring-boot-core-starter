package com.zipe.example.service.example;

import com.zipe.example.base.TestBase;
import com.zipe.example.model.Gleepf;
import com.zipe.example.service.ExampleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author : Gary Tsai
 * @created : @Date 2021/3/12 上午 09:56
 **/
public class ExampleServiceTest extends TestBase {

    @Autowired
    public ExampleService exampleServiceImpl;

    @BeforeEach
    public void insertNewRecord() {
        Gleepf gleepf = new Gleepf();
        gleepf.setEe010("AB12345678");
        gleepf.setEe020(new BigDecimal("55555"));
        gleepf.setEe030("FG");
        gleepf.setEe040("1234");
        gleepf.setEe070("N");
        gleepf.setEe120("***Test@fglife.com.tw");
        gleepf.setEe150(new BigDecimal("10000"));
        gleepf.setEe170(new BigDecimal("212222"));
        gleepf.setEe180("E");
        gleepf.setEe181("E");
        gleepf.setEeMark("測試資料");
        gleepf.setEeDate(new BigDecimal("20210302"));
        gleepf.setEeTime(new BigDecimal("1231"));
        gleepf.setEeUser("TEST");
        exampleServiceImpl.saveGleepf(gleepf);
    }

    @AfterEach
    public void deleteRecord() {
        Gleepf record = exampleServiceImpl.findByEE010("AB12345678");
        exampleServiceImpl.saveGleepf(record);
    }

    @Test
    public void findGleepfTest(){
        Gleepf record = exampleServiceImpl.findByEE010("AB12345678");
        System.out.println(record);
    }

    @Test
    public void updateGleepfTest(){
        Gleepf record = exampleServiceImpl.findByEE010("AB12345678");
        String userEmail = "test1234@fglife.com.tw";
        record.setEe120(userEmail);
        exampleServiceImpl.updateGleepf(record);
        Gleepf newRecord = exampleServiceImpl.findByEE010("AB12345678");
        System.out.println(newRecord.getEe120());
    }

}
