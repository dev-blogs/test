package com.devblogs.websocket;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.Endpoint;
import javax.websocket.WebSocketContainer;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.springframework.stereotype.Service;

@Service
public class WebSocketHandlerNotification extends WebSocketAdapter {
	private UUID uuid;
	private String token;

	public WebSocketHandlerNotification() {
	}

	@PostConstruct
	public void init() {
		token = "OTgyNTU1NWQtNTA2NC00OTUzLThjMTYtMWU4OTY4NDdjYTMw";
	}

	public UUID getUuid() {
		return this.uuid;
	}

	public void openWebSocket(String url) throws Exception {
		System.out.println("WEBSOCKET: open web socket for notification");
		uuid = UUID.randomUUID();
		URI wsUri = URI.create(url + uuid.toString());
		connectWebSocket(wsUri);
	}

	public void connectWebSocket(URI endpointURI) {
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.setDefaultMaxSessionIdleTimeout(0);

			final ClientEndpointConfig.Builder configBuilder = ClientEndpointConfig.Builder.create();
			configBuilder.configurator(new ClientEndpointConfig.Configurator() {
				@Override
				public void beforeRequest(final Map<String, List<String>> headers) {
					headers.put("Authorization", Arrays.asList("Bearer " + token));
				}
			});
			ClientEndpointConfig clientConfig = configBuilder.build();

			Endpoint endpoint = new WebsocketClientEndpoint();

			container.connectToServer(endpoint, clientConfig, endpointURI);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}