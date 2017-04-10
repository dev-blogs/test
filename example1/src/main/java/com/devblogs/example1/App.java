package com.devblogs.example1;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.context.support.GenericXmlApplicationContext;
import com.devblogs.domain.Contact;
import com.devblogs.service.ContactService;

public class App {
	public static void main1(String[] args) {
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

    public static void main(String [] args) {
        TreeMap<Ingredient, Integer> inv;
        inv = new  TreeMap<Ingredient, Integer>(new IngredientComparator());

        inv.put(Ingredient.COFFEE, 15);
        inv.put(Ingredient.DECAF_COFFEE, 10);

        Set<Ingredient> set = inv.keySet();
        for (Ingredient ingredient : set) {
            System.out.println(ingredient + " - " + inv.get(ingredient));
        }
    }
}

enum Ingredient {
    DECAF_COFFEE(0.78, "Decaf Coffee"), COFFEE(0.75, "Coffee");

    private double cost;
    private String name;

    private Ingredient(double cost, String name) {
        this.cost = cost;
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return this.name;
    }
}

class IngredientComparator implements Comparator<Ingredient> {
    @Override
    public int compare(Ingredient o1, Ingredient o2) {
        if (o1.getCost() > o2.getCost()) {
            return 1;
        } else if (o1.getCost() < o2.getCost()) {
            return -1;
        } else {
            return 0;
        }
    }
}