package com.zipe.example.service.impl;

import com.zipe.example.model.Gleepf;
import com.zipe.example.repository.GleepfRepository;
import com.zipe.example.service.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : Gary Tsai
 * @created : @Date 2021/3/12 上午 09:51
 **/
@Slf4j
@Service
public class ExampleServiceImpl implements ExampleService {

    private final GleepfRepository gleepfRepository;

    @Autowired
    ExampleServiceImpl(GleepfRepository gleepfRepository){
        this.gleepfRepository = gleepfRepository;
    }

    @Override
    public List<Gleepf> findAll() {
        return gleepfRepository.findAll();
    }

    @Override
    public Gleepf findByEE010(String ee010) {
        return gleepfRepository.findByEe010(ee010);
    }

    @Override
    @Transactional
    public void saveGleepf(Gleepf gleepf) {
        gleepfRepository.save(gleepf);
    }

    @Override
    @Transactional
    public void delGleepf(Gleepf gleepf) {
        gleepfRepository.delete(gleepf);
    }

    @Override
    public void updateGleepf(Gleepf gleepf) {
        gleepfRepository.save(gleepf);
    }
}
