package com.zipe.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("applicationContextHelper")
public class ApplicationContextHelper implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

    @Override
    @SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public static Object getBean(String beanName) {
		if (applicationContext == null) {
			throw new NullPointerException("ApplicationContext is null!");
		}
		return applicationContext.getBean(beanName);
	}

	/**
	 * 根据类获取bean
	 *
	 * @param clazz
	 * @param <T>
	 * @return <T>
	 */
	public static <T> T popBean(Class<T> clazz) {
		if (applicationContext == null) {
			return null;
		}
		return applicationContext.getBean(clazz);
	}

	/**
	 * 根据类和bean的名字获取bean
	 *
	 * @param name
	 * @param clazz
	 * @param <T>
	 * @return <T>
	 */
	public static <T> T popBean(String name, Class<T> clazz) {
		if (applicationContext == null) {
			return null;
		}
		return applicationContext.getBean(name, clazz);
	}
}
