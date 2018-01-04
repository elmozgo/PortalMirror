package org.portalmirror;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.portalmirror.websocket.jwt.WebsocketJwtFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jwt.JWTClaimsSet;

@RestController
@CrossOrigin
public class TokenController {

	@Value("${websocket.node.secret}")
	private String secret;
	
	@RequestMapping(method = GET, path="debug/token")
	public @ResponseBody String getDebugToken(
			@RequestParam String username,
			@RequestParam String portletName,
			@RequestParam String requestUri,
			@RequestParam(required = false) Integer validityPeriod) throws KeyLengthException, JOSEException {
		
		return generateToken(username, portletName, requestUri, validityPeriod);
	}
	
	@RequestMapping(method = GET, path="mock/token")
	public @ResponseBody String getMockToken(@RequestParam String portletName, HttpServletRequest request) throws KeyLengthException, JOSEException {
		
		return generateToken("mocked-username", portletName, request.getRequestURI(), 60);
	}

	private String generateToken(String username, String portletName, String requestUri, Integer validityPeriod)
			throws KeyLengthException, JOSEException {
		WebsocketJwtFactory tokenFactory;

		if (validityPeriod != null) {

			tokenFactory = new WebsocketJwtFactory(validityPeriod);
		} else {
			
			tokenFactory = new WebsocketJwtFactory(60);
		}
		
		JWTClaimsSet claimsSet = tokenFactory.createJwtClaimSet(username, portletName, requestUri);
		
		
		return WebsocketJwtFactory.signJwt(claimsSet, secret.getBytes(StandardCharsets.UTF_8)).serialize();
	}
	
}
