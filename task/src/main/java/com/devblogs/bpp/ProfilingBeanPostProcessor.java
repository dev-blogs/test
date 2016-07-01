package com.devblogs.bpp;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.stereotype.Component;

import com.devblogs.annotations.Profile;

@Component
public class ProfilingBeanPostProcessor implements BeanPostProcessor {
	private Map<String, Object> map = new HashMap<String, Object>();

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Class<?> clazz = bean.getClass();
		if (clazz.isAnnotationPresent(Profile.class)) {
			map.put(beanName, clazz);
		}
		return bean;
		
	}
	
	public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
		Class<?> clazz = (Class<?>) map.get(beanName);
		if (clazz != null) {
			return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
				public Object invoke(Object obj, Method m, Object[] objects) throws Throwable {
					System.out.println("Profiling...");
					long start = System.nanoTime();
					Object invoke = m.invoke(bean);
					long end = System.nanoTime();
					System.out.println();
					System.out.println("Duration: " + (end - start));
					System.out.println("End of profiling...");
					return invoke;
				}
			});
		}
		return bean;
	}
}