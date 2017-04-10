package com.task;

import com.task.substitution.UnsafeKeyHandler;

public class App {
	public static void main(String[] args) {
		KeyHandler keyHandler = new UnsafeKeyHandler();
		MyThread myThread = new MyThread(keyHandler);
		
		new Thread(myThread).start();
		new Thread(myThread).start();
		new Thread(myThread).start();
		//new Thread(myThread).start();
		//new Thread(myThread).start();
		//new Thread(myThread).start();
		//new Thread(myThread).start();
	}
}

class MyThread implements Runnable {
	private KeyHandler keyHandler;
	
	public MyThread(KeyHandler keyHandler) {
		this.keyHandler = keyHandler;
	}
	
	public void run() {
		Key key = new Key("key");
		//Key key = new Key("key");
		keyHandler.handle(key);
	}
}