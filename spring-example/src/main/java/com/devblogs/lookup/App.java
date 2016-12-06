package com.devblogs.lookup;

import org.springframework.context.support.GenericXmlApplicationContext;

import collections.CollectionInjection;

public class App {
	public static void main(String[] args) {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.load("classpath:lookup/app-context-lookup.xml");
		context.refresh();
		
		DemoBean instance1 = (DemoBean) context.getBean("abstractLookupBean");
		MyHelper myHelper1 = instance1.getMyHelper();
		MyHelper myHelper2 = instance1.getMyHelper();
		
		System.out.println(myHelper1 == myHelper2);
		
		System.out.println();
		
		DemoBean instance3 = (DemoBean) context.getBean("standardLookupBean");
		MyHelper myHelper3 = instance3.getMyHelper();
		MyHelper myHelper4 = instance3.getMyHelper();
		
		System.out.println(myHelper3 == myHelper4);
	}
}