package com.devblogs;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.devblogs.config.SpringConfigurator;
import com.devblogs.websocket.WebSocketHandlerNotification;

public class App {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigurator.class);
		
		WebSocketHandlerNotification webSocketHandlerNotification = context.getBean(WebSocketHandlerNotification.class);
		try {
			webSocketHandlerNotification.openWebSocket("wss://ws.us-east-1.everywhere.avid.com:4443/notifications?consumer=");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}