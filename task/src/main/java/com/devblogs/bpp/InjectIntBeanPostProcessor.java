package com.devblogs.bpp;

import java.lang.reflect.Field;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import com.devblogs.annotations.InjectInt;

@Component
public class InjectIntBeanPostProcessor implements BeanPostProcessor {

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			InjectInt annotation = field.getAnnotation(InjectInt.class);
			if (annotation != null) {
				field.setAccessible(true);
				try {
					int value = annotation.value();
					field.set(bean, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}