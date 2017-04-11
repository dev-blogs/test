package com.task.luxoft;

public class App {
	public static void main(String[] args) {
		KeyHandler keyHandler = new KeyHandler();
		Task task1 = new Task(keyHandler, "key1");
		Task task2 = new Task(keyHandler, "key2");
		Task task3 = new Task(keyHandler, "key2");
		Task task4 = new Task(keyHandler, "key2");
		Task task5 = new Task(keyHandler, "key2");
		Task task6 = new Task(keyHandler, "key6");
		Task task7 = new Task(keyHandler, "key7");
		Task task8 = new Task(keyHandler, "key8");
		
		new Thread(task1).start();
		new Thread(task2).start();
		new Thread(task3).start();
		new Thread(task4).start();
		new Thread(task5).start();
		new Thread(task6).start();
		new Thread(task7).start();
		new Thread(task8).start();
	}
}

class Task implements Runnable {
	private KeyHandler keyHandler;
	private String keyData;
	
	public Task(KeyHandler keyHandler, String keyData) {
		this.keyHandler = keyHandler;
		this.keyData = keyData;
	}
	
	public void run() {
		Key key = new Key(keyData);
		keyHandler.handle(key);
	}
}