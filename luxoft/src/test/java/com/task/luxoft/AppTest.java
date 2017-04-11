package com.task.luxoft;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Test;
import com.task.luxoft.exceptions.DuplicateKeyException;
import com.task.substitution.UnsafeKeyHandler;

public class AppTest {
	
	/**
	 * test duplicate keys for KeyHanler
	 * @result duplicate keys should be arranged in right order
	 */
	@Test
	public void testDuplicateKeysForKeyHandler() {
		KeyHandler keyHandler = new KeyHandler();
		
		ExecutorService executorService = Executors.newFixedThreadPool(8);	
		
		// first task1, task2, task3, task4 and task5 have the same keys
		Future<?> future1 = executorService.submit(new Task(keyHandler, "key1"));
		Future<?> future2 = executorService.submit(new Task(keyHandler, "key1"));
		Future<?> future3 = executorService.submit(new Task(keyHandler, "key1"));
		Future<?> future4 = executorService.submit(new Task(keyHandler, "key1"));
		Future<?> future5 = executorService.submit(new Task(keyHandler, "key1"));
		Future<?> future6 = executorService.submit(new Task(keyHandler, "key6"));
		Future<?> future7 = executorService.submit(new Task(keyHandler, "key7"));
		Future<?> future8 = executorService.submit(new Task(keyHandler, "key8"));

		try {
			future1.get();
			future2.get();
			future3.get();
			future4.get();
			future5.get();
			future6.get();
			future7.get();
			future8.get();
		} catch (Exception e) {
			// retrieving exception from thread in parent thread
			String className = e.getCause().toString().split(":")[0];
			if (className.equals(DuplicateKeyException.class.getName())) {
				throw new com.task.luxoft.exceptions.DuplicateKeyException(e.getCause().toString().split(":")[1]);
			}
		}
		executorService.shutdown();
	}
	
	/**
	 * test different keys for KeyHanler
	 * @result duplicate keys should be arranged in right order
	 */
	@Test
	public void testDifferentKeysForKeyHandler() {
		KeyHandler keyHandler = new KeyHandler();
		
		ExecutorService executorService = Executors.newFixedThreadPool(8);	
		
		// first task1, task2, task3, task4 and task5 have the same keys
		Future<?> future1 = executorService.submit(new Task(keyHandler, "key1"));
		Future<?> future2 = executorService.submit(new Task(keyHandler, "key2"));
		Future<?> future3 = executorService.submit(new Task(keyHandler, "key3"));
		Future<?> future4 = executorService.submit(new Task(keyHandler, "key4"));
		Future<?> future5 = executorService.submit(new Task(keyHandler, "key5"));
		Future<?> future6 = executorService.submit(new Task(keyHandler, "key6"));
		Future<?> future7 = executorService.submit(new Task(keyHandler, "key7"));
		Future<?> future8 = executorService.submit(new Task(keyHandler, "key8"));

		try {
			future1.get();
			future2.get();
			future3.get();
			future4.get();
			future5.get();
			future6.get();
			future7.get();
			future8.get();
		} catch (Exception e) {
			// retrieving exception from thread in parent thread
			String className = e.getCause().toString().split(":")[0];
			if (className.equals(DuplicateKeyException.class.getName())) {
				throw new com.task.luxoft.exceptions.DuplicateKeyException(e.getCause().toString().split(":")[1]);
			}
		}
		executorService.shutdown();
	}
	
	/**
	 * test duplicate keys for UnsafeKeyHanler
	 * @result should be DuplicateKeyException
	 */
	@Test(expected = DuplicateKeyException.class)
	public void testDuplicateKeysForUnsafeThreadKeyHandler() {
		// for catching duplicate catch exception use UnsafeKeyHandler
		KeyHandler keyHandler = new UnsafeKeyHandler();
		
		ExecutorService executorService = Executors.newFixedThreadPool(8);	
		
		// task1, task2 and task3 have the same keys
		Future<?> future1 = executorService.submit(new Task(keyHandler, "key1"));
		Future<?> future2 = executorService.submit(new Task(keyHandler, "key1"));
		Future<?> future3 = executorService.submit(new Task(keyHandler, "key1"));
		Future<?> future4 = executorService.submit(new Task(keyHandler, "key4"));
		Future<?> future5 = executorService.submit(new Task(keyHandler, "key5"));
		Future<?> future6 = executorService.submit(new Task(keyHandler, "key6"));
		Future<?> future7 = executorService.submit(new Task(keyHandler, "key7"));
		Future<?> future8 = executorService.submit(new Task(keyHandler, "key8"));

		try {
			future1.get();
			future2.get();
			future3.get();
			future4.get();
			future5.get();
			future6.get();
			future7.get();
			future8.get();
		} catch (Exception e) {
			// retrieving exception from thread in parent thread
			String className = e.getCause().toString().split(":")[0];
			if (className.equals(DuplicateKeyException.class.getName())) {
				throw new com.task.luxoft.exceptions.DuplicateKeyException(e.getCause().toString().split(":")[1]);
			}
		}
		executorService.shutdown();
	}
	
	/**
	 * test different keys for UnsafeKeyHanler
	 * @result There are no issues for threads
	 */
	@Test
	public void testDifferentKeysForUnsafeThreadKeyHandler() {
		// use UnsafeKeyHandeler
		KeyHandler keyHandler = new UnsafeKeyHandler();
		
		ExecutorService executorService = Executors.newFixedThreadPool(8);	
		
		// first task have the same keys
		Future<?> future1 = executorService.submit(new Task(keyHandler, "key1"));
		Future<?> future2 = executorService.submit(new Task(keyHandler, "key2"));
		Future<?> future3 = executorService.submit(new Task(keyHandler, "key3"));
		Future<?> future4 = executorService.submit(new Task(keyHandler, "key4"));
		Future<?> future5 = executorService.submit(new Task(keyHandler, "key5"));
		Future<?> future6 = executorService.submit(new Task(keyHandler, "key6"));
		Future<?> future7 = executorService.submit(new Task(keyHandler, "key7"));
		Future<?> future8 = executorService.submit(new Task(keyHandler, "key8"));

		try {
			future1.get();
			future2.get();
			future3.get();
			future4.get();
			future5.get();
			future6.get();
			future7.get();
			future8.get();
		} catch (Exception e) {
			// retrieving exception from thread in parent thread
			String className = e.getCause().toString().split(":")[0];
			if (className.equals(DuplicateKeyException.class.getName())) {
				throw new com.task.luxoft.exceptions.DuplicateKeyException(e.getCause().toString().split(":")[1]);
			}
		}
		executorService.shutdown();
	}
}