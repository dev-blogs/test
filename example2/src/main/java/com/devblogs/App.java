package com.devblogs;

import java.util.List;
import org.springframework.context.support.GenericXmlApplicationContext;
import com.devblogs.domain.Contact;
import com.devblogs.service.ContactService;

public class App {
	public static void main(String[] args) {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.load("classpath:spring/context.xml");
		context.refresh();
		
		ContactService contactService = context.getBean("contactService", ContactService.class);
		
		Contact contact = contactService.findById(1L);
		contact.setFirstName("Peter");
		contactService.save(contact);
		
		List<Contact> contacts = contactService.findAll();
		
		for (Contact c : contacts) {
			System.out.println(c);
		}
		
		System.out.println("Contact count: " + contactService.countAll());
	}
}