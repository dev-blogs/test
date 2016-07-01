package com.devblogs.task;

import javax.annotation.PostConstruct;

import com.devblogs.annotations.InjectInt;
import com.devblogs.annotations.PostProxy;
import com.devblogs.annotations.Profile;

@Profile
public class TerminatorQuoter implements Quoter {
	private String message;
	@InjectInt(value = 5)
	private int repeat;
	
	public TerminatorQuoter() {
		System.out.println("Phase 1");
		System.out.println("repeat: " + repeat);
		System.out.println();
	}
	
	@PostConstruct
	public void init() {
		System.out.println("Phase 2");
		System.out.println("repeat: " + repeat);
		System.out.println();
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@PostProxy
	public void say() {
		System.out.println("Phase 3");
		for (int i = 0; i < repeat; i++) {
			System.out.println(message);
		}
	}
}