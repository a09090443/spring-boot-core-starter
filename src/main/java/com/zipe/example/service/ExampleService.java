package com.zipe.example.service;

import com.zipe.example.model.Gleepf;

import java.util.List;

/**
 * @author : Gary Tsai
 * @created : @Date 2021/3/12 上午 09:50
 **/
public interface ExampleService {
    List<Gleepf> findAll();
    Gleepf findByEE010(String ee010);
    void saveGleepf(Gleepf gleepf);
    void delGleepf(Gleepf gleepf);
    void updateGleepf(Gleepf gleepf);
}
