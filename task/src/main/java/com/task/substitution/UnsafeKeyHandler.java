package com.task.substitution;

import com.task.Key;
import com.task.KeyHandler;

public class UnsafeKeyHandler extends KeyHandler {

	@Override
	public void handle(Key key) {
		try {
			externalSystem.process(key);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}