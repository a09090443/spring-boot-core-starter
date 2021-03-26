package com.zipe.common.repository;

import com.zipe.common.model.LdapUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LdapUserRepository extends JpaRepository<LdapUser, Long> {

	LdapUser findByUserId(String userId);
}
