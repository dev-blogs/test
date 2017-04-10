package com.task;

public class KeyHandler {
	private ExternalSystem externalSystem = new ExternalSystem();

	public void handle(Key key) {
		externalSystem.process(key);
	}
}