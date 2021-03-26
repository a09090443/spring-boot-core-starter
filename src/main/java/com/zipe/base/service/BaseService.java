package com.zipe.base.service;

import com.zipe.common.model.LdapUserLog;
import com.zipe.common.repository.LdapUserLogRepository;
import com.zipe.common.vo.SysUserVO;
import com.zipe.enums.UserStatusEnum;
import com.zipe.util.DateTimeUtils;
import com.zipe.util.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Optional;

/**
 *系統服務層 基礎類別
 *
 * @author : Gary Tsai
 * @created : @Date 2020/11/20 上午 09:31
 **/
@Slf4j
public abstract class BaseService {

    @Autowired
    private MessageSource messageSource;

    protected Locale currentLocale;

    @Autowired
    private HttpSession session;

    @Autowired
    protected Environment env;

    @Autowired
    private LdapUserLogRepository ldapUserLogRepository;

    protected String getMessage(String key, String... args) {
        currentLocale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, args, currentLocale);
    }


    /**
     * 取得登入者資訊
     *
     * @return
     * @author adam.yeh
     */
    protected SysUserVO fetchLoginUser () {
        String userId = UserInfoUtil.loginUserId();

        if ("anonymousUser".equals(userId)) {
            return null;
        }

        SysUserVO userInfo;
        Object userObject = session.getAttribute(userId);

        if (userObject instanceof SysUserVO) {
            userInfo = (SysUserVO) session.getAttribute(userId);
        } else {
            Optional<LdapUserLog> optionalLdapUserLog = Optional.ofNullable(
                    ldapUserLogRepository.findTop1ByUserIdAndStatusOrderByTimeDesc(userId, UserStatusEnum.LOGIN.desc));

            userInfo = new SysUserVO();
            userInfo.setLoginTime(optionalLdapUserLog.map(value ->
                    value.getTime().format(DateTimeUtils.dateTimeFormate1)).orElseGet(() -> DateTimeUtils.dateTime));
            userInfo.setUserId(userId);
        }

        return userInfo;
    }

}
