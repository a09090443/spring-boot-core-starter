package com.zipe.common.repository;

import com.zipe.common.model.LdapUserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LdapUserLogRepository extends JpaRepository<LdapUserLog, Long> {

    LdapUserLog findTop1ByUserIdAndStatusOrderByTimeDesc(String userId, String status);

}
