package com.zipe.base.controller;

import com.zipe.common.model.LdapUserLog;
import com.zipe.common.service.UserService;
import com.zipe.common.vo.SysUserVO;
import com.zipe.enums.UserStatusEnum;
import com.zipe.util.DateTimeUtils;
import com.zipe.util.UserInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;

/**
 * 基礎控制類別
 *
 * @author : Gary Tsai
 * @created : @Date 2020/11/20 下午 04:05
 */
public abstract class BaseController {

    @Autowired
    protected Environment env;

    @Autowired
    protected AuthenticationTrustResolver authenticationTrustResolver;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService sysUserService;

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Locale currentLocale;
    protected String defaultMsg;

    public abstract ModelAndView initPage ();

    @ModelAttribute()
    public void myModel(HttpServletRequest request, HttpServletResponse response, Model model) {
        SysUserVO sysUserVO = getUserInfo();

        if (!isCurrentAuthenticationAnonymous()) {
            this.request = request;
            this.response = response;
            this.currentLocale = LocaleContextHolder.getLocale();
            this.defaultMsg = "Message resource not found.";
        }
        model.addAttribute("sysUserVO", sysUserVO);
    }

    /**
     * This method returns true if users is already authenticated [logged-in], else
     * false.
     */
    protected boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }

    /**
     * 取得登入者資訊
     *
     * @return
     */
    protected SysUserVO getUserInfo() {
        Optional<SysUserVO> optionalSysUserVO = Optional.ofNullable(sysUserService.findLoginUserInfo(UserInfoUtil.loginUserId()));
        SysUserVO sysUserVO = optionalSysUserVO.orElse(new SysUserVO());

        Optional<LdapUserLog> optionalLdapUserLog = Optional.ofNullable(sysUserService.findUserLogLastRecord(sysUserVO.getUserId(),
                UserStatusEnum.LOGIN.desc));
        sysUserVO.setLoginTime(optionalLdapUserLog.map(ldapUserLog ->
                ldapUserLog.getTime().format(DateTimeUtils.dateTimeFormate1)).orElseGet(() -> DateTimeUtils.dateTime));

        return sysUserVO;
    }

    protected String getMessage(String key, String... args) {
        if (StringUtils.isBlank(key)) {
            return "";
        }

        return messageSource.getMessage(key, args, currentLocale);
    }

}
