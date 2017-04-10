package com.task;

import java.util.ArrayList;
import java.util.List;

public class ExternalSystem {
	private List<Thread> threads = new ArrayList<Thread>();
	
	public void process(Key key) {
		Thread currentThread = Thread.currentThread();
		if (threads.size() < 8) {
			threads.add(currentThread);
			System.out.println("Performes some logic by " + currentThread.getName() + " thread");
		}
	}
}