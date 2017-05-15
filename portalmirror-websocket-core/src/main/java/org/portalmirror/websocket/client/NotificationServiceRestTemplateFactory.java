package org.portalmirror.websocket.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NotificationServiceRestTemplateFactory {

	@Autowired
	private ObjectMapper objectMapper;

	public NotificationServiceRestTemplate createRestTemplate(String baseUrl) {
		NotificationServiceRestTemplate restTemplate = new NotificationServiceRestTemplate(baseUrl);
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonMessageConverter.setObjectMapper(objectMapper);
		messageConverters.add(jsonMessageConverter);
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}

}
