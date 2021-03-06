package com.zipe.common.security.service;

import com.zipe.common.config.SecurityPropertyConfig;
import com.zipe.common.model.LdapUserLog;
import com.zipe.common.service.UserService;
import com.zipe.common.service.impl.UserServiceImpl;
import com.zipe.enums.UserStatusEnum;
import com.zipe.util.ApplicationContextHelper;
import com.zipe.util.DateTimeUtils;
import com.zipe.util.StringConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpSessionEvent;

@Slf4j
public class SessionListener extends HttpSessionEventPublisher {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setMaxInactiveInterval(60 * 15);// Seconds
        super.sessionCreated(event);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        SessionRegistry sessionRegistry = getSessionRegistry();
        // 取得正確的 session
        SessionInformation sessionInfo = sessionRegistry
                .getSessionInformation(event.getSession().getId());
        String userId = "";
        if (sessionInfo != null) {
            userId = (String) sessionInfo.getPrincipal();
        }
        SecurityPropertyConfig securityPropertyConfig = (SecurityPropertyConfig) ApplicationContextHelper.getBean("securityPropertyConfig");
        MessageSource message = (MessageSource) ApplicationContextHelper.getBean("messageSource");
        if (securityPropertyConfig.getRecordUserLogonEnabled().equalsIgnoreCase(StringConstant.TRUE)) {
            if (StringUtils.isNotBlank(userId)) {
                UserService sysUserService = (UserServiceImpl) ApplicationContextHelper.getBean("sysUserServiceImpl");
                // 記錄使用者登出時間
                LdapUserLog sysUserLog = new LdapUserLog();
                sysUserLog.setUserId(userId);
                sysUserLog.setStatus(UserStatusEnum.LOGOUT.desc);
                sysUserLog.setTime(DateTimeUtils.getDateNow());
                try {
                    sysUserService.addUserLogonRecord(sysUserLog);
                } catch (Exception e) {
                    log.error(message.getMessage("logon.record.error", null, LocaleContextHolder.getLocale()));
                }
            }
        }

        super.sessionDestroyed(event);
    }

    private SessionRegistry getSessionRegistry() {
        return (SessionRegistry) ApplicationContextHelper.getBean("sessionRegistry");
    }
}
