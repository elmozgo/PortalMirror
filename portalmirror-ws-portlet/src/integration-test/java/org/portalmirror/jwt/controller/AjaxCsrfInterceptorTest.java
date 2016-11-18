package org.portalmirror.jwt.controller;

import static java.nio.charset.Charset.defaultCharset;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AjaxCsrfInterceptorTest {

	private static final String LIFERAY_APP_URL_PROPERTY = "liferay.app.url";
	private Properties properties;
	private String baseUrl;
	private String getTokenMethodUrl;
	private HttpClientSetupForTesting hcs;

	@Before
	public void setup() throws IOException {

		hcs = new HttpClientSetupForTesting();

		properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

		baseUrl = properties.getProperty(LIFERAY_APP_URL_PROPERTY);
		getTokenMethodUrl = baseUrl + "/delegate/jwt/ajax/ws-token";

	}

	@After
	public void close() throws IOException {
		hcs.getHttpClient().close();
	}

	@Test
	public void pAuthParameterTest() throws IOException {

		// given
		// when
		HttpResponse response = hcs.execute(new HttpGet(baseUrl));
		String pAuth = extractPAuthFromLiferayWebsite(response);

		// then
		assertThat("http response code", response.getStatusLine().getStatusCode(), is(200));
		assertThat("p_auth not null", pAuth, not(isEmptyOrNullString()));

	}

	@Test
	public void getJwsTokenAjaxCallWithPAuthAsParameterSuccessTest() throws ClientProtocolException, IOException,
			URISyntaxException {
		
		// given
		String pAuth = extractPAuthFromLiferayWebsite(hcs.execute(new HttpGet(baseUrl)));
		// when
		URIBuilder uriBuilder = new URIBuilder(getTokenMethodUrl);
		uriBuilder.addParameter("p_auth", pAuth);
		uriBuilder.addParameter("portletName", "fakePortlet");

		HttpResponse response = hcs.execute(new HttpGet(uriBuilder.build()));

		// then
		assertThat("http response code", response.getStatusLine().getStatusCode(), is(200));
		assertThat("response body", IOUtils.toString(response.getEntity().getContent(), defaultCharset()),
				not(isEmptyOrNullString()));

	}

	@Test
	public void getJwsTokenAjaxCallWithPAuthAsHeaderSuccessTest() throws ClientProtocolException, IOException,
			URISyntaxException {
		
		// given
		String pAuth = extractPAuthFromLiferayWebsite(hcs.execute(new HttpGet(baseUrl)));
		// when
		URIBuilder uriBuilder = new URIBuilder(getTokenMethodUrl);
		uriBuilder.addParameter("portletName", "fakePortlet");

		HttpGet getMethod = new HttpGet(uriBuilder.build());
		getMethod.addHeader("X-CSRF-Token", pAuth);

		HttpResponse response = hcs.execute(getMethod);

		// then
		assertThat("http response code", response.getStatusLine().getStatusCode(), is(200));
		assertThat("response body", IOUtils.toString(response.getEntity().getContent(), defaultCharset()),
				not(isEmptyOrNullString()));

	}

	@Test
	public void getJwsTokenAjaxCallFailTest() throws ClientProtocolException, IOException, URISyntaxException {
	
		// given
		hcs.execute(new HttpGet(baseUrl));
		// when
		URIBuilder uriBuilder = new URIBuilder(getTokenMethodUrl);
		uriBuilder.addParameter("portletName", "fakePortlet");

		HttpResponse response = hcs.execute(new HttpGet(uriBuilder.build()));

		// then
		assertThat("http response code", response.getStatusLine().getStatusCode(), is(403));
		assertThat("response body", IOUtils.toString(response.getEntity().getContent(), defaultCharset()),
				is(isEmptyOrNullString()));

	}

	private static String extractPAuthFromLiferayWebsite(HttpResponse response) throws IOException {

		String website = EntityUtils.toString(response.getEntity());
		Pattern pattern = Pattern.compile("Liferay.authToken=\"(.+?)\"");
		Matcher matcher = pattern.matcher(website);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}

	}
}
