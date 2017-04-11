package com.task.luxoft;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.task.luxoft.exceptions.DuplicateKeyException;

/**
 * 
 * @author zhenya
 *
 */
public class ExternalSystem {
	private Map<Key, Thread> safeThreadMap = Collections.synchronizedMap(new HashMap<Key, Thread>());
	private Object lock = new Object();
	
	/**
	 * Method process() throws DuplicateKeyException for identical keys
	 * in threads which are performed in method
	 * @param key
	 * @throws InterruptedException
	 */
	public void process(Key key) throws InterruptedException {
		Thread currentThread;
		
		synchronized (lock) {
			currentThread = Thread.currentThread();
			System.out.println("Key \"" + key.getData() + " comes into procceess() method");
			Thread threadFromMap = safeThreadMap.get(key);
			if (safeThreadMap.containsKey(key) && threadFromMap.getId() != currentThread.getId()) {
				throw new DuplicateKeyException("key " + key.getData() + " is being used");
			}
			safeThreadMap.put(key, currentThread);
		}
		
		// unsafe thread code. Key is used here
		System.out.println("Performes some logic for key \"" + key.getData() + "\" by " + currentThread.getName() + " thread");
		Thread.sleep(100);
		
		synchronized (lock) {
			safeThreadMap.remove(key);
			System.out.println("key \"" + key.getData() + " gets out");
		}
	}
}