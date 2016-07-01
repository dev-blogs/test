package com.devblogs.task;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		//AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		//Quoter bean = context.getBean("terminator", Quoter.class);
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/context.xml");
		Quoter terminatorQuoter = context.getBean(Quoter.class);
	}
}