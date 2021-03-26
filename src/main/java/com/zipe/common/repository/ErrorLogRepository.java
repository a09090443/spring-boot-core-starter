package com.zipe.common.repository;

import com.zipe.common.model.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {

	ErrorLog findByServerIp(String serverIp);
}
