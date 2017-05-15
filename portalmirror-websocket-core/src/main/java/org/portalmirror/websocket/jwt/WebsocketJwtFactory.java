package org.portalmirror.websocket.jwt;

import org.joda.time.DateTime;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class WebsocketJwtFactory {

	public static final String LOGGED_USER_CLAIM = "loggedUser";

	public WebsocketJwtFactory(int validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	private int validityPeriod;

	public JWTClaimsSet createJwtClaimSet(String username, String portletId, String requestUri) {

		DateTime timestamp = DateTime.now();

		return new JWTClaimsSet
				.Builder()
				.subject(portletId)
				.issueTime(timestamp.toDate())
				.expirationTime(timestamp.plusSeconds(validityPeriod).toDate())
				.issuer(requestUri)
				.claim(LOGGED_USER_CLAIM, username)
				.build();
	}
	
	public static SignedJWT signJwt(JWTClaimsSet claimsSet, byte[] secret) throws KeyLengthException, JOSEException {
		
		SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
		jwt.sign(new MACSigner(secret));
		
		return jwt;
	}

}
