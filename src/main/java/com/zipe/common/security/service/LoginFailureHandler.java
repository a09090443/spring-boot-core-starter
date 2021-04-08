package com.zipe.common.security.service;

import com.zipe.common.config.SecurityPropertyConfig;
import com.zipe.common.model.ErrorLog;
import com.zipe.common.model.LdapUserLog;
import com.zipe.common.service.UserService;
import com.zipe.enums.UserStatusEnum;
import com.zipe.exception.LdapException;
import com.zipe.util.DateTimeUtils;
import com.zipe.util.StringConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Slf4j
@Service
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final MessageSource messageSource;

    private final UserService sysUserServiceImpl;

    private final SecurityPropertyConfig securityPropertyConfig;

    @Autowired
    public LoginFailureHandler(MessageSource messageSource, UserService sysUserServiceImpl, SecurityPropertyConfig securityPropertyConfig) {
        this.messageSource = messageSource;
        this.sysUserServiceImpl = sysUserServiceImpl;
        this.securityPropertyConfig = securityPropertyConfig;
        String loginUri = securityPropertyConfig.getLoginUri();
        if (StringUtils.isNotBlank(loginUri)) {
            setDefaultFailureUrl(loginUri);
        }
        setUseForward(true);
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String loginId = request.getParameter("username");
        String address = request.getRemoteAddr();
        log.info("Source ip address : {}", address);
        Locale currentLocale = LocaleContextHolder.getLocale();
        ErrorLog pojo = new ErrorLog();

        if (exception.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
            // 無此使用者
            logger.warn(messageSource.getMessage("login.user.not.exist", new String[]{loginId}, currentLocale));
            pojo.setMessage(messageSource.getMessage("login.user.verify.fail", new String[]{loginId}, currentLocale));
        } else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
            // 使用者未啟用
            logger.warn(messageSource.getMessage("login.user.not.enabled", new String[]{loginId}, currentLocale));
            pojo.setMessage(messageSource.getMessage("login.user.verify.fail", new String[]{loginId}, currentLocale));
        } else if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
            // 帳號或密碼錯誤
            logger.warn(messageSource.getMessage("login.access.error.message", new String[]{loginId}, currentLocale));
            pojo.setMessage(messageSource.getMessage("login.access.error.message", new String[]{loginId}, currentLocale));
        } else if (exception.getClass().isAssignableFrom(LdapException.class)) {
            // LDAP 連線愈時
            logger.warn(messageSource.getMessage("login.ldap.connection.timeout.messages", null, currentLocale));
            pojo.setMessage(messageSource.getMessage("login.ldap.error.messages", new String[]{loginId, exception.getMessage()}, currentLocale));
        } else {
            pojo.setMessage(messageSource.getMessage("login.user.verify.fail", null, currentLocale));
        }

        // 使用者登入失敗訊息
        logger.warn(messageSource.getMessage("login.ldap.error.messages", new String[]{loginId, exception.getMessage()}, currentLocale));

        // 記錄使用者登入失敗時間
        LdapUserLog ldapUserLog = new LdapUserLog();
        ldapUserLog.setUserId(loginId);
        ldapUserLog.setStatus(UserStatusEnum.FAIL.desc);
        ldapUserLog.setTime(DateTimeUtils.getDateNow());
        if (securityPropertyConfig.getRecordUserLogonEnabled().equalsIgnoreCase(StringConstant.TRUE)) {
            try {
                sysUserServiceImpl.addUserLogonRecord(ldapUserLog);
            } catch (Exception e) {
                log.error(messageSource.getMessage("login.user.save.fail.record.error", null, currentLocale));
            }
        }

        request.setAttribute("error", pojo.getMessage());

        super.onAuthenticationFailure(request, response, exception);
    }

}
