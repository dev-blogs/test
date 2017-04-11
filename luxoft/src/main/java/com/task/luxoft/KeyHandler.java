package com.task.luxoft;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author zhenya
 * 
 */
public class KeyHandler {
	protected ExternalSystem externalSystem = new ExternalSystem();
	private Map<Key, Thread> safeThreadMap = Collections.synchronizedMap(new HashMap<Key, Thread>());
	private Object lock = new Object();

	/**
	 * For solution apply guarded suspension pattern (охраняемая приостановка)
	 * @param key
	 */
	public void handle(Key key) {
		try {
			synchronized (lock) {
				Thread currentThread = Thread.currentThread();
				Thread threadFromMap = safeThreadMap.get(key);
				
				while (safeThreadMap.containsKey(key) && threadFromMap.getId() != currentThread.getId()) {
					lock.wait();
				}
				
				safeThreadMap.put(key, currentThread);
			}
			
			externalSystem.process(key);
			
			synchronized (lock) {
				safeThreadMap.remove(key);
				lock.notifyAll();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}