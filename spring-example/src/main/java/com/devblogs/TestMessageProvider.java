package com.devblogs;

//@Service("messageProvider")
public class TestMessageProvider implements MessageProvider {
	//@Autowired
	private String message;
	
	
	//@Autowired
	public TestMessageProvider(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}