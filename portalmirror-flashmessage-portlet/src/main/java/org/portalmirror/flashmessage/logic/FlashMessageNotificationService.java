package org.portalmirror.flashmessage.logic;

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
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

@Service
public class FlashMessageNotificationService implements NotificationService{

	@Autowired
	private NotificationServiceRestTemplate restTemplate;
	
	
	//TODO: its the copy of RefresherNotificationService - move to NotificationService
	@Override
	public List<ConnectedClient> getAllConnectedClients() {
		
		UriTemplate uriTemplate = new UriTemplate("/internal/flash-message/sockets");
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
	
	public void sendMessage(String clientId, String text, Integer period) {
		
		FlashMessage message = new FlashMessage();
		message.setText(text);
		message.setPeriod(period);
		
		UriTemplate uriTemplate = new UriTemplate("/internal/flash-message/sockets/{clientId}/message");
			
			restTemplate.postForEntity(uriTemplate.expand(clientId), message, Void.class);
		}
	}

