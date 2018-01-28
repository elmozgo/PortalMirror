package org.portalmirror.jwt.controller;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientSetupForTesting {

	private CloseableHttpClient httpClient;
	private HttpClientContext context;

	public HttpClientSetupForTesting() {
		httpClient = HttpClients.createDefault();
		context = HttpClientContext.create();

		// CookieStore cookieStore = new BasicCookieStore();
		// context.setCookieStore(cookieStore);
	}

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public HttpClientContext getContext() {
		return context;
	}

	public HttpResponse execute(HttpGet get) throws ClientProtocolException, IOException {

		HttpResponse response = httpClient.execute(get, context);
		Cookie jSessionIdCookie = getJSessionIdCookie(context.getCookieStore().getCookies());
		String jSessionId = "no JSESSIONID";
		if(jSessionIdCookie != null) {
			jSessionId = jSessionIdCookie.getValue();
		}
		
		System.out.println("JSESSIONID in Response: " + jSessionId);
		return response;
	}

	private static Cookie getJSessionIdCookie(List<Cookie> cookies) {
		Cookie pAuthCookie = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("JSESSIONID")) {
				pAuthCookie = cookie;
				break;
			}
		}
		return pAuthCookie;
	}
}
