package org.portalmirror.websocket.client;


import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class NotificationServiceRestTemplate extends RestTemplate {

	private String baseUrl;

	public NotificationServiceRestTemplate(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}

	@Override
	protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback,
			ResponseExtractor<T> responseExtractor) throws RestClientException {
		
		URI fullUrl = null;
		try {
			fullUrl = new URI(baseUrl + url.toString());
		} catch (URISyntaxException e) {
			throw new RestClientException("incorrect uri", e);
		}		
		
		return super.doExecute(fullUrl, method, requestCallback, responseExtractor);
		
	}
	
	
}
