package com.zipe.common.config;

import com.zipe.common.security.service.CustomAuthenticationProvider;
import com.zipe.common.security.service.LdapUserDetailsService;
import com.zipe.common.security.service.LoginFailureHandler;
import com.zipe.common.security.service.LoginSuccessHandler;
import com.zipe.common.security.service.LogoutSuccessHandler;
import com.zipe.common.security.service.SessionListener;
import com.zipe.enums.AuthoritzedTypeEnum;
import com.zipe.util.StringConstant;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.Arrays;
import java.util.Objects;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String DEFAULT_LOGIN_PAGE_URI = "/login";

    @Autowired
    private Environment env;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private LdapUserDetailsService ldapUserDetailsService;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    protected SecurityPropertyConfig securityPropertyConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (Objects.equals(securityPropertyConfig.getEnabled(), StringConstant.TRUE.toLowerCase())) {
            allowAllByConditions(http);
        } else {
            allowAll(http);
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        switch (securityPropertyConfig.getAuthoritzedType()){
            case LDAP:{
                auth.authenticationProvider(ldapUserDetailsService);
                break;
            }case DB:{
                auth.authenticationProvider(customAuthenticationProvider);
                break;
            }case LOCAL:{

            } default:{
                auth.inMemoryAuthentication()
                        .withUser("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles("ADMIN", "USER");
            }
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new SessionListener();
    }

    private void allowAll(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().permitAll();
    }

    private void allowAllByConditions(HttpSecurity http) throws Exception {
        if (StringUtils.isBlank(securityPropertyConfig.getAllowUri())) {
            throw new Exception("allow.uri must be setting while security.enabled is ture");
        }
        String[] allowUris = Arrays.stream(securityPropertyConfig.getAllowUri().split(StringConstant.COMMA)).map(String::trim).toArray(String[]::new);
        if (ArrayUtils.isEmpty(allowUris)) {
            throw new Exception("allow.uri can not empty while security.enabled is ture");
        }
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(allowUris).permitAll()
                .anyRequest().authenticated();

        if (StringUtils.isBlank(securityPropertyConfig.getLoginUri())) {
            http.httpBasic()
                    .and()
                    .formLogin()
                    .permitAll()
                    .successHandler(loginSuccessHandler)
                    .failureHandler(loginFailureHandler).and()
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .permitAll()
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .and()
                    .sessionManagement().invalidSessionUrl(DEFAULT_LOGIN_PAGE_URI)
                    .maximumSessions(2).expiredUrl(DEFAULT_LOGIN_PAGE_URI).sessionRegistry(sessionRegistry());
        } else {
            http.formLogin()
                    .loginPage(securityPropertyConfig.getLoginUri())
                    .usernameParameter(securityPropertyConfig.getLoginFormUsernameFieldName())
                    .passwordParameter(securityPropertyConfig.getLoginFormPasswordFieldName())
                    .permitAll()
                    .successHandler(loginSuccessHandler)
                    .failureHandler(loginFailureHandler)
                    .and()
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .permitAll()
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .and()
                    .sessionManagement().invalidSessionUrl(securityPropertyConfig.getLoginUri())
                    .maximumSessions(2).expiredUrl(securityPropertyConfig.getLoginUri()).sessionRegistry(sessionRegistry());
        }
    }

}
