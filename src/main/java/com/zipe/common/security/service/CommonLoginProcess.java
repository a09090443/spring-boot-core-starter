package com.zipe.common.security.service;

import com.zipe.common.config.SecurityPropertyConfig;
import com.zipe.common.service.UserService;
import com.zipe.employee.service.EmployeeService;
import com.zipe.enums.UserEnum;
import com.zipe.util.DateTimeUtils;
import com.zipe.util.StringConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author gary.tsai 2019/8/26
 */
abstract class CommonLoginProcess implements AuthenticationProvider {

    protected final Environment env;

    protected final MessageSource messageSource;

    protected final UserService sysUserService;

    protected final PasswordEncoder passwordEncoder;

    protected final SecurityPropertyConfig securityPropertyConfig;

    @Autowired
    CommonLoginProcess(UserService sysUserService,
                       PasswordEncoder passwordEncoder,
                       Environment env,
                       MessageSource messageSource,
                       SecurityPropertyConfig securityPropertyConfig){
        this.sysUserService = sysUserService;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
        this.messageSource = messageSource;
        this.securityPropertyConfig = securityPropertyConfig;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = authentication.getName();
        String password = (String) authentication.getCredentials();

        if (StringUtils.isBlank(loginId)) {
            throw new UsernameNotFoundException(StringConstant.BLANK);
        }

        if (UserEnum.ADMIN.name().equalsIgnoreCase(authentication.getName())) {
            return this.verifySpecialUser(loginId, password);
        } else {
            return verifyNormalUser(loginId, password);
        }
    }

    /**
     * ?????????????????????????????????:???????????????
     *
     * @param userName
     * @param password
     * @return
     */
    public UsernamePasswordAuthenticationToken verifySpecialUser(String userName, String password) {
        // ????????????????????????
        String dynamicPassword = DateTimeUtils.getDateNow(DateTimeUtils.dateTimeFormate7);
        if (!dynamicPassword.equalsIgnoreCase(password)) {
            throw new BadCredentialsException(StringConstant.BLANK);
        }
        return new UsernamePasswordAuthenticationToken(userName, password, null);
    }

    abstract UsernamePasswordAuthenticationToken verifyNormalUser(String loginId, String password);

}
