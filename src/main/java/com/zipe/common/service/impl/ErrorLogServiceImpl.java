package com.zipe.common.service.impl;

import com.zipe.common.model.ErrorLog;
import com.zipe.common.repository.ErrorLogRepository;
import com.zipe.common.service.ErrorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ErrorLogServiceImpl implements ErrorLogService {

    private final ErrorLogRepository errorLogRepository;

    @Autowired
    ErrorLogServiceImpl(ErrorLogRepository errorLogRepository) {
        this.errorLogRepository = errorLogRepository;
    }

    @Override
    @Transactional
    public void saveOrUpdate(ErrorLog errorLog) {
        errorLogRepository.save(errorLog);
    }

    @Override
    @Transactional
    public void delete(ErrorLog errorLog) {
        errorLogRepository.delete(errorLog);
    }
}
