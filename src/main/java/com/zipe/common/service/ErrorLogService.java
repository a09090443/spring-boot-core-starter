package com.zipe.common.service;

import com.zipe.common.model.ErrorLog;

public interface ErrorLogService {

	void saveOrUpdate(ErrorLog errorLog);

	void delete(ErrorLog errorLog);

}
