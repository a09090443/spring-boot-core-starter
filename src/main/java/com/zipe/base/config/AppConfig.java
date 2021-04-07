package com.zipe.base.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zipe.base.formatter.DateFormatter;
import com.zipe.util.VelocityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan(basePackages = {"com.zipe"})
public class AppConfig implements WebMvcConfigurer {

	private final Environment env;

	@Autowired
	AppConfig(Environment env){
		this.env = env;
	}

    @Override
    public void addFormatters (FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter());
    }

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:message");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(Locale.TAIWAN);
		resolver.setCookieName("localeCookie");
		resolver.setCookieMaxAge(4800);
		return resolver;
	}

	@Bean
	public VelocityUtil velocityUtil() {
		VelocityUtil velocityUtil = new VelocityUtil();
		System.out.println(env.getProperty("template.path"));
		velocityUtil.setDir(env.getProperty("template.path"));
		velocityUtil.initClassPath();
		return velocityUtil;
	}

	/**
	 * an interceptor bean that will switch to a new locale based on the value of the language parameter appended to a request:
	 *
	 * @param registry
	 * @language should be the name of the request param i.e  localhost:8010/api/get-greeting?language=fr
	 * <p>
	 * Note: All requests to the backend needing Internationalization should have the "language" request param
	 */
	@Override
	public void addInterceptors (InterceptorRegistry registry) {
//		registry.addInterceptor(localeChangeInterceptor());
//
//        InterceptorRegistration registration = registry.addInterceptor(groupPermissionInterceptor());
//        registration.addPathPatterns("/**");
//        registration.excludePathPatterns(excludes());// 有需要撈非選單路徑的資料，須在下面新增排除路徑，否則會被擋掉
//
//        super.addInterceptors(registry);
	}

    public LocaleChangeInterceptor localeChangeInterceptor () {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
	}

	/**
	 * Configure ResourceHandlers to serve static resources like CSS/ Javascript
	 * etc...
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

    @Bean
    public MultipartResolver multipartResolver() {
       return new CommonsMultipartResolver();
    }

    private String[] excludes () {
        List<String> excludes = new ArrayList<>();
        excludes.add("/login");
        excludes.add("/menu/**");
		excludes.add("/dashboard");
		excludes.add("/services/*");

        String[] array = new String[excludes.size()];

        return excludes.toArray(array);
    }

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

	@Bean
	WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> enableDefaultServlet() {
		return (factory) -> factory.setRegisterDefaultServlet(true);
	}

	@Bean
	public RequestContextListener requestContextListener(){
		return new RequestContextListener();
	}
}
