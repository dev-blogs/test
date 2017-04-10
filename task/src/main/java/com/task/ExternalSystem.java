package com.task;

import java.util.HashMap;
import java.util.Map;

import com.task.exceptions.DuplicateKeyException;

public class ExternalSystem {
	private Map<Key, Thread> map = new HashMap<Key, Thread>();
	private Object lock = new Object();
	
	public void process(Key key) {
		if (map.size() < 8) {
			Thread currentThread = Thread.currentThread();
			
			Thread thread = map.get(key);
			
			if (map.containsKey(key) && !thread.getName().equals(currentThread.getName())) {
				throw new DuplicateKeyException();
			}
			
			map.put(key, currentThread);
			
			System.out.println("Performes some logic for key " + key + " by " + currentThread.getName() + " thread");
			
			map.remove(key);
		}
	}
}