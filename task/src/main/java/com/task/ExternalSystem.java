package com.task;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.task.exceptions.DuplicateKeyException;

/**
 * 
 * @author zhenya
 *
 */
public class ExternalSystem {
	private Map<Key, Thread> safeThreadMap = Collections.synchronizedMap(new HashMap<Key, Thread>());
	private Object lock = new Object();
	
	/**
	 * Method process() limits only 8 threads and generates DuplicateKeyException for identical keys
	 * in threads which are performed in method
	 * @param key
	 * @throws InterruptedException
	 */
	public void process(Key key) throws InterruptedException {
		Thread currentThread;
		
		synchronized (lock) {
			currentThread = Thread.currentThread();
			Thread threadFromMap = safeThreadMap.get(key);
			if (safeThreadMap.containsKey(key) && !threadFromMap.getName().equals(currentThread.getName())) {
				throw new DuplicateKeyException();
			}
			safeThreadMap.put(key, currentThread);
		}
		
		// unsafe thread code. Key is used here
		System.out.println("Performes some logic for key " + key + " by " + currentThread.getName() + " thread");
		Thread.sleep(100);
		
		synchronized (lock) {
			safeThreadMap.remove(key);
		}
	}
}