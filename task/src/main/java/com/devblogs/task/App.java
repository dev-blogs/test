package com.devblogs.task;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		TerminatorQuoter bean = context.getBean(TerminatorQuoter.class);
		bean.say();
	}
}