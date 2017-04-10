package com.task;

public class App {
	public static void main(String[] args) {
		KeyHandler keyHandler = new KeyHandler();
		MyThread myThread = new MyThread(keyHandler);
		
		new Thread(myThread).start();
		new Thread(myThread).start();
		new Thread(myThread).start();
		new Thread(myThread).start();
		new Thread(myThread).start();
		new Thread(myThread).start();
		new Thread(myThread).start();
	}
}

class MyThread implements Runnable {
	private KeyHandler keyHandler;
	private static int count = 0;
	
	public MyThread(KeyHandler keyHandler) {
		this.keyHandler = keyHandler;
		count++;
	}
	
	public void run() {
		keyHandler.handle(new Key(Integer.toString(count)));
	}
}