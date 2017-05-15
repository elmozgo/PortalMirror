package org.portalmirror.refresher.logic;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.portalmirror.websocket.client.ConnectedClient;
import org.portalmirror.websocket.client.ConnectedClientDto;
import org.portalmirror.websocket.client.NotificationService;
import org.portalmirror.websocket.client.NotificationServiceRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

public class RefresherNotificationService implements NotificationService{

	@Autowired
	private NotificationServiceRestTemplate restTemplate;
	
	
	@Override
	public List<ConnectedClient> getAllConnectedClients() {
		
		UriTemplate uriTemplate = new UriTemplate("/internal/refresher/sockets");
		ResponseEntity<ConnectedClientDto[]> responseEntity = restTemplate.getForEntity(uriTemplate.expand(), ConnectedClientDto[].class);
		
		List<ConnectedClientDto> connectedClientDtos = Arrays.asList(responseEntity.getBody());
		
		List<ConnectedClient> connectedClients = new ArrayList<>();
		
		for(ConnectedClientDto dto : connectedClientDtos) {
			
			ConnectedClient client;
			try {
				client = new ConnectedClient(dto.getSocketId(), dto.getClientId(), dto.getPageId(), (String) dto.getJwt());
			} catch (ParseException e) {
				throw new IllegalStateException(e);
			}
			
			connectedClients.add(client);
		}
		
		return connectedClients;
	}
	
	public void refreshAllClientsOnPage(String layoutId) {
		
		URI uri = UriComponentsBuilder
				.fromUriString("/internal/refresher/sockets/refresh")
				.queryParam("pageId", layoutId)
				.build()
				.toUri();
		
		restTemplate.postForEntity(uri, null, Void.class);
	}
	
	public void refreshClient(String clientId) {
		
		UriTemplate uriTemplate = new UriTemplate("/internal/refresher/sockets/{clientId}/refresh");
		restTemplate.postForEntity(uriTemplate.expand(clientId), null, Void.class);
		
	}
	

}
