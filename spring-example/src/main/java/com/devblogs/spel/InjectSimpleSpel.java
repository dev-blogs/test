package com.devblogs.spel;

import org.springframework.context.support.GenericXmlApplicationContext;

public class InjectSimpleSpel {
	private String name;
	private int age;
	private float height;
	private boolean programmer;
	private Long ageInSeconds;

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setProgrammer(boolean programmer) {
		this.programmer = programmer;
	}

	public void setAgeInSeconds(Long ageInSeconds) {
		this.ageInSeconds = ageInSeconds;
	}
	
	public String toString() {
		return "Name: " + name + "\n" +
				"Age: " + age + "\n" +
				"Age in Seconds: " + ageInSeconds + "\n" +
				"Height: " + height + "\n" +
				"Is Programmer? " + programmer;
				
	}

	public static void main(String[] args) {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.load("classpath:spel/spel-context-xml.xml");
		context.refresh();

		InjectSimpleSpel simple = (InjectSimpleSpel) context.getBean("injectSimpleSpel");
		System.out.println(simple);
	}
}