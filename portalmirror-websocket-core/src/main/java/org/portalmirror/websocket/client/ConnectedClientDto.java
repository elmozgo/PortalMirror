package org.portalmirror.websocket.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ConnectedClientDto {

	String socketId;
	String clientId;
	Object jwt;
	String pageId;
	public String getSocketId() {
		return socketId;
	}
	public void setSocketId(String socketId) {
		this.socketId = socketId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	@JsonRawValue
	public Object getJwt() {
		return jwt == null ? null : jwt.toString();
	}
	public void setJwt(JsonNode jwt) {
		this.jwt = jwt;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	
}
