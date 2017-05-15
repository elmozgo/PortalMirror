package org.portalmirror.websocket.client;

import java.text.ParseException;

import org.portalmirror.websocket.jwt.WebsocketJwtFactory;

import com.nimbusds.jwt.JWTClaimsSet;

public class ConnectedClient {

	public ConnectedClient(String socketId, String clientId, String pageId, String jwtJsonString)
			throws ParseException {
		this.jwt = JWTClaimsSet.parse(jwtJsonString);
		this.socketId = socketId;
		this.clientId = clientId;
		this.pageId = pageId;
	}

	private final String socketId;
	private final String clientId;
	private final JWTClaimsSet jwt;
	private final String pageId;

	public String getSocketId() {
		return socketId;
	}

	public String getClientId() {
		return clientId;
	}

	public JWTClaimsSet getJwt() {
		return jwt;
	}

	public String getPageId() {
		return pageId;
	}

	public String getLoggedUsername() {

		try {
			return jwt.getStringClaim(WebsocketJwtFactory.LOGGED_USER_CLAIM);
		} catch (ParseException e) {
			throw new IllegalStateException("String claim is not a String");
		}

	}

}
