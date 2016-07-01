package com.devblogs.task;

public class TerminatorQuoter implements Quoter {
	private String message;
	@InjectInt(value = 10)
	private int repeat;
	
	public TerminatorQuoter() {
		System.out.println("Phase 1");
		System.out.println("repeat: " + repeat);
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void say() {
		for (int i = 0; i < repeat; i++) {
			System.out.println(message);
		}
	}
}