package com.task;

public class Key {
	private String data;
	
	public Key(String data) {
		this.data = data;
	}
	
	public String getData() {
		return data;
	}

	/**
	 * hashCode is used for checking by contains() method in map
	 */
	@Override
	public int hashCode() {
		return data.hashCode();
	}

	/**
	 * equals is used for checking by contains() method in map
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}