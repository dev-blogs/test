package com.devblogs;

//@Service("messageRenderer")
public class StandardOutMessageRenderer implements MessageRenderer {
	//@Autowired
	private MessageProvider messageProvider;
	
	//@Autowired
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
	
	/*public MessageProvider getMessageProvider() {
		return this.messageProvider;
	}*/
	
	public void render() {
		if (messageProvider == null) {
			throw new RuntimeException("You must set the property messageProvider of class: " + StandardOutMessageRenderer.class.getName());
		}
		System.out.println("The message is " + messageProvider.getMessage());
	}
}