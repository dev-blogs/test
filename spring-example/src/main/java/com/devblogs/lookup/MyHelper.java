package com.devblogs.lookup;

public class MyHelper {
	private static int index = 0;
	
	public MyHelper() {
		index++;
	}
	
	public void doSomethingHelpful() {
		System.out.println("somethinghelpful " + index);
	}
}