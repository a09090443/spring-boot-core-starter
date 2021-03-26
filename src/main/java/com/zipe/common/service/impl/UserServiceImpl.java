package com.zipe.common.service.impl;

import com.zipe.base.annotation.DS;
import com.zipe.base.service.BaseService;
import com.zipe.common.model.LdapUser;
import com.zipe.common.model.LdapUserLog;
import com.zipe.common.repository.LdapUserLogRepository;
import com.zipe.common.repository.LdapUserRepository;
import com.zipe.common.service.UserService;
import com.zipe.common.vo.SysUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@DS()
public class UserServiceImpl extends BaseService implements UserService {

    private final LdapUserRepository ldapUserRepository;

    private final LdapUserLogRepository ldapUserLogRepository;

    @Autowired
    UserServiceImpl(LdapUserRepository ldapUserRepository, LdapUserLogRepository ldapUserLogRepository) {
        this.ldapUserRepository = ldapUserRepository;
        this.ldapUserLogRepository = ldapUserLogRepository;
    }

    @Override
    public LdapUser findUserByUserId(String userId) {
        return ldapUserRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void addUser(LdapUser ldapUser) {
        ldapUserRepository.save(ldapUser);
    }

    @Override
    @Transactional
    public void addUserLogonRecord(LdapUserLog ldapUserLog) {
        ldapUserLogRepository.save(ldapUserLog);
    }

    @Override
    public SysUserVO findLoginUserInfo(String userId) {
        return fetchLoginUser();
    }

    @Override
    public LdapUserLog findUserLogLastRecord(String userId, String status) {
        return ldapUserLogRepository.findTop1ByUserIdAndStatusOrderByTimeDesc(userId, status);
    }

}
