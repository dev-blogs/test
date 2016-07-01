package com.devblogs.config;

import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.devblogs.annotations.PostProxy;

@Component
public class ContextUpdateListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private ConfigurableListableBeanFactory factory;

	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext context = event.getApplicationContext();
		String[] names = context.getBeanDefinitionNames();
		for (String name : names) {			
			BeanDefinition beanDefinition = factory.getBeanDefinition(name);
			String originalClassName = beanDefinition.getBeanClassName();
			try {
				Class<?> originalClass = Class.forName(originalClassName);
				Method[] methods = originalClass.getMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(PostProxy.class)) {
						Object currentBean = context.getBean(name);
						try {
							Method currentMethod = currentBean.getClass().getMethod(method.getName(), method.getParameterTypes());
							currentMethod.invoke(currentBean);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}