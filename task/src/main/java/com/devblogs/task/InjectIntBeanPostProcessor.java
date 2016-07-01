package com.devblogs.task;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class InjectIntBeanPostProcessor implements BeanPostProcessor {

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Class<?> clazz = bean.getClass();
		Annotation annotation = clazz.getAnnotation(InjectInt.class);		
		if (annotation != null) {
			Field[] fields = clazz.getFields();
			try {
				Field fieldMessage = clazz.getField("repeat");
				if (fieldMessage != null) {
					fieldMessage.setAccessible(true);
					fieldMessage.set(bean, 10);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}