package com.task.substitution;

import com.task.luxoft.Key;
import com.task.luxoft.KeyHandler;

/**
 * 
 * @author zhenya
 *
 */
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