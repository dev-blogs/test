package com.task.luxoft;

/**
 * 
 * @author zhenya
 *
 */
public class Key {
	private String data;
	
	public Key(String data) {
		this.data = data;
	}
	
	public String getData() {
		return data;
	}

	@Override
	public int hashCode() {
		return 3 * data.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Key otherKey = (Key) obj;
		return data.equals(otherKey.data);
	}
}