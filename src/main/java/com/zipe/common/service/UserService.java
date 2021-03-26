package com.zipe.common.service;

import com.zipe.common.model.LdapUser;
import com.zipe.common.model.LdapUserLog;
import com.zipe.common.vo.SysUserVO;

public interface UserService {

    LdapUser findUserByUserId(String userId);

    SysUserVO findLoginUserInfo(String userId);

    LdapUserLog findUserLogLastRecord(String userId, String status);

    void addUser(LdapUser ldapUser);

    void addUserLogonRecord(LdapUserLog sysUserLog);

}
