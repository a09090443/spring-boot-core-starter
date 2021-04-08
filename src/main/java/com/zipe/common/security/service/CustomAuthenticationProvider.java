package com.zipe.common.security.service;

import com.zipe.common.config.SecurityPropertyConfig;
import com.zipe.common.model.LdapUser;
import com.zipe.common.service.UserService;
import com.zipe.util.StringConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class CustomAuthenticationProvider extends CommonLoginProcess {

    CustomAuthenticationProvider(UserService sysUserService,
                                 PasswordEncoder passwordEncoder,
                                 Environment env,
                                 MessageSource messageSource,
                                 SecurityPropertyConfig securityPropertyConfig) {
        super(sysUserService, passwordEncoder, env, messageSource, securityPropertyConfig);
    }

    /**
     * 一般使用者認證程序
     *
     * @param loginId
     * @param password
     * @return
     */
    @Override
    public UsernamePasswordAuthenticationToken verifyNormalUser(String loginId, String password) {
        LdapUser ldapUserEntity = sysUserService.findUserByUserId(loginId);

        if (Objects.isNull(ldapUserEntity)) {
            throw new UsernameNotFoundException("");
        } else if (ldapUserEntity.getIsEnabled().equals(StringConstant.SHORT_NO)) {
            throw new DisabledException("");
        }

        return new UsernamePasswordAuthenticationToken(loginId, password, null);
    }
}
