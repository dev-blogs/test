package com.devblogs.websocket;

import javax.websocket.ClientEndpoint;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

@ClientEndpoint
public class WebsocketClientEndpoint extends Endpoint {

	@Override
	public void onOpen(Session session, EndpointConfig config) {
		javax.websocket.MessageHandler messageHandler = new javax.websocket.MessageHandler.Whole<String>() {
            public synchronized void onMessage(String message) {
                try {
                    System.out.println("Notification from cloud by WebSocket: " /*+ message*/ );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        session.addMessageHandler(messageHandler);
	}

}