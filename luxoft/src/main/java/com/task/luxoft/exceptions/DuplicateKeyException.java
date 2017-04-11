package com.task.luxoft.exceptions;

/**
 * 
 * @author zhenya
 *
 */
public class DuplicateKeyException extends RuntimeException {
	public DuplicateKeyException() {
	}
	
	public DuplicateKeyException(String message) {
		super(message);
	}
}