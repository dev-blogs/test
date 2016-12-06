package com.devblogs;

import org.springframework.context.support.GenericXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		//context.load("classpath:app-context-xml.xml");
		context.load("classpath:app-context-annotation.xml");
		context.refresh();
		
		MessageRenderer messageRenderer = context.getBean("messageRenderer", MessageRenderer.class);
		messageRenderer.render();
	}
}