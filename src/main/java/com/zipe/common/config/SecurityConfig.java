package com.zipe.common.config;

import com.zipe.common.security.service.CustomAuthenticationProvider;
import com.zipe.common.security.service.LdapUserDetailsService;
import com.zipe.common.security.service.LoginFailureHandler;
import com.zipe.common.security.service.LoginSuccessHandler;
import com.zipe.common.security.service.LogoutSuccessHandler;
import com.zipe.common.security.service.SessionListener;
import com.zipe.util.StringConstant;
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

import java.util.Objects;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.
//				authorizeRequests()
//				.anyRequest() //對象為所有網址
//				.authenticated() //存取必須通過驗證
//				.and()
//				.formLogin() //若未不符合authorize條件，則產生預設login表單
//				.and()
//				.httpBasic(); //產生基本表單
		http.csrf().disable();
		http.authorizeRequests()
	        .antMatchers("/resources/**", "/static/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.usernameParameter("username")
			.passwordParameter("password")
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
			.sessionManagement().invalidSessionUrl("/login")
			.maximumSessions(2).expiredUrl("/login").sessionRegistry(sessionRegistry());
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		if (Objects.equals(env.getProperty("ldap.enabled"), StringConstant.TRUE.toLowerCase())) {
			auth.authenticationProvider(ldapUserDetailsService);
		} else {
			auth.authenticationProvider(customAuthenticationProvider);
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

}
