package com.devblogs.simpl.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service("injectSimple")
public class InjectSimple {
	@Value("Ievgenii Mitiguz")
	private String name;
	@Value("35")
	private int age;
	@Value("1.78")
	private float height;
	@Value("true")
	private boolean programmer;
	@Value("1103760000")
	private Long ageInSeconds;
	
	/*public void setAgeInSeconds(Long ageInSeconds) {
		this.ageInSeconds = ageInSeconds;
	}
	
	public void setProgrammer(boolean programmer) {
		this.programmer = programmer;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setName(String name) {
		this.name = name;
	}*/
	
	public String toString() {
		return "Name: " + name + "\n" +
				"Age: " + age + "\n" +
				"Age in Seconds: " + ageInSeconds + "\n" +
				"Height: " + height + "\n" +
				"Is Programmer? " + programmer;
				
	}
	
	public static void main(String [] args) {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.load("classpath:injectSimple-context-xml.xml");
		context.refresh();
		
		InjectSimple injectSimple = (InjectSimple) context.getBean("injectSimple");
		System.out.println(injectSimple);
	}
}