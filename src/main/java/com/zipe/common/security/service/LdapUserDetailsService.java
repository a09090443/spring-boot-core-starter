package com.zipe.common.security.service;

import com.zipe.common.model.LdapUser;
import com.zipe.common.service.UserService;
import com.zipe.employee.model.Personnel;
import com.zipe.employee.service.EmployeeService;
import com.zipe.exception.LdapException;
import com.zipe.util.LdapUtil;
import com.zipe.util.StringConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class LdapUserDetailsService extends CommonLoginProcess {

    LdapUserDetailsService(UserService sysUserService,
                           PasswordEncoder passwordEncoder,
                           Environment env,
                           MessageSource messageSource,
                           EmployeeService employeeServiceImpl) {
        super(sysUserService, passwordEncoder, env, messageSource, employeeServiceImpl);
    }

    /**
     * 一般使用者認證程序
     *
     * @param loginId
     * @param password
     * @return
     */
    public UsernamePasswordAuthenticationToken verifyNormalUser(String loginId, String password) {
        LdapUtil ldapUtil = null;
        String ldapIp = env.getProperty("ldap.ip");
        String domain = env.getProperty("ldap.domain");
        String port = env.getProperty("ldap.port");
        String dn = env.getProperty("ldap.dn");
        String fullLoginId = loginId.split(StringConstant.AT).length > 1 ? loginId : loginId + StringConstant.AT + domain; // 帳號如無域名自動加入
        Attributes attrs;
        LdapContext ctx;
        LdapUser ldapUser;
        try {
            ldapUtil = new LdapUtil(fullLoginId, password, ldapIp, port, dn);
            ctx = ldapUtil.getLdapContext();
            attrs = ldapUtil.loginLdap();
            ldapUser = convertLdapUser(loginId, attrs, ctx);
        }catch (AuthenticationException ae){
            log.warn(messageSource.getMessage("login.access.error.message", null, LocaleContextHolder.getLocale()));
            throw new BadCredentialsException(messageSource.getMessage("login.access.error.message", null, LocaleContextHolder.getLocale()));
        } catch (NamingException ne) {
            log.warn(messageSource.getMessage("login.ldap.connection.timeout.messages", null, LocaleContextHolder.getLocale()));
            throw new LdapException(messageSource.getMessage("login.ldap.connection.timeout.messages", null, LocaleContextHolder.getLocale()));
        } catch (Exception e) {
            log.warn(messageSource.getMessage("login.ldap.error.messages", new String[]{loginId, e.getMessage()},
                    LocaleContextHolder.getLocale()));
            throw new BadCredentialsException(e.getMessage());
        } finally {
            if (null != ldapUtil) {
                ldapUtil.closeConnection();
            }
        }
        log.info(messageSource.getMessage("login.ldap.success.messages", new String[]{loginId},
                LocaleContextHolder.getLocale()));

        log.info(messageSource.getMessage("login.user.update", new String[]{loginId},
                LocaleContextHolder.getLocale()));
        // 如使用 AD 帳號登入時需要域名需將域名移除
        String[] userNameSplit = fullLoginId.split(StringConstant.AT);

        sysUserService.addUser(ldapUser);

        return new UsernamePasswordAuthenticationToken(userNameSplit[0], password, null);
    }

    private LdapUser convertLdapUser(String userId, Attributes attrs, DirContext context) throws Exception {
        Hashtable envInfo = context.getEnvironment();
        LdapUser ldapUser = new LdapUser();
        ldapUser.setUserId(userId);
        ldapUser.setIsEnabled(StringConstant.SHORT_YES);
        Personnel employee = employeeServiceImpl.findEmployeeByEmpNo(userId);
        ldapUser.setEmail(Optional.ofNullable(employee.getPnEmail1()).orElse(envInfo.get("java.naming.security.principal").toString()));
        ldapUser.setLdapDn(getAttrValue(attrs, "distinguishedName"));
        return ldapUser;
    }


    /**
     * 依LDAP的欄位屬性名稱取出，如: distinguishedName: CN=PWA User
     * PWA使用者,OU=System,OU=fglife,DC=com,DC=tw 並分割字串並只取得內容值
     *
     * @param attrs
     * @param attrName
     * @return
     */
    private String getAttrValue(Attributes attrs, String attrName) {
        return !Objects.isNull(attrs.get(attrName)) ? attrs.get(attrName).toString().split(":", 2)[1].trim() : null;
    }
}
