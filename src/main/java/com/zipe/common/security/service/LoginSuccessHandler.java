package com.zipe.common.security.service;

import com.zipe.common.model.LdapUserLog;
import com.zipe.common.service.UserService;
import com.zipe.common.vo.SysUserVO;
import com.zipe.enums.UserStatusEnum;
import com.zipe.util.DateTimeUtils;
import com.zipe.util.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Service
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService sysUserService;

    private final HttpSession session;

    @Autowired
    public LoginSuccessHandler (UserService sysUserService, HttpSession session, Environment env) {
        this.sysUserService = sysUserService;
        this.session = session;
        String defaultMainPage = env.getProperty("default.main.page.uri");
        if(StringUtils.isNotBlank(defaultMainPage)){
            setDefaultTargetUrl(defaultMainPage);
        }
        setAlwaysUseDefaultTargetUrl(false);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        saveLoginUserInfo();

        // 記錄使用者登入時間
        LdapUserLog ldapUserLog = new LdapUserLog();
        ldapUserLog.setUserId(UserInfoUtil.loginUserId());
        ldapUserLog.setStatus(UserStatusEnum.LOGIN.desc);
        ldapUserLog.setTime(DateTimeUtils.getDateNow());
        sysUserService.addUserLogonRecord(ldapUserLog);
        log.debug("User:" + UserInfoUtil.loginUserId() + "login" + request.getContextPath());
        log.debug("IP:" + getIpAddress(request));

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private void saveLoginUserInfo () {
        String userId = UserInfoUtil.loginUserId();
        SysUserVO userInfo = sysUserService.findLoginUserInfo(userId);
        session.setAttribute(userId, userInfo);
    }
}
