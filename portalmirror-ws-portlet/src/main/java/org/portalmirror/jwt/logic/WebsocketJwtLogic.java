package org.portalmirror.jwt.logic;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.portalmirror.jwt.common.portlet.CustomPropsUtil;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;



public class WebsocketJwtLogic {
	
	private static final String WEBSOCKET_NODE_SECRET_PROPERTY = "websocket.node.secret";
	private static final String LOGGED_USER_CLAIM = "logged-user";
	
	private static Log log = LogFactoryUtil.getLog(WebsocketJwtLogic.class);
	
	public static SignedJWT createSignedJwt(HttpServletRequest request, String portletName) throws PortalException, SystemException, JOSEException {
		
		byte[] secret = CustomPropsUtil.getProp(WEBSOCKET_NODE_SECRET_PROPERTY).getBytes(StandardCharsets.UTF_8);
		JWSSigner signer = new MACSigner(secret);
		JWTClaimsSet claimsSet = createJwtClaimSet(request, portletName);
		
		log.info("jwt created: \n" + claimsSet.toJSONObject().toJSONString());
		
		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
		signedJWT.sign(signer);
		
		return signedJWT;
	}
	
	private static JWTClaimsSet createJwtClaimSet(HttpServletRequest request, String portletName) throws PortalException, SystemException{
		
		DateTime timestamp = DateTime.now();
		
		return new JWTClaimsSet.Builder()
			.subject(portletName)
			.issueTime(timestamp.toDate())
			.expirationTime(timestamp.plusHours(1).toDate())
			.issuer(request.getRequestURI())
			.claim(LOGGED_USER_CLAIM, getLoggedInUsernameOrNull(request))
			.build();
	}

	private static String getLoggedInUsernameOrNull(HttpServletRequest request) throws PortalException, SystemException {
		
		User user = PortalUtil.getUser(request);
		if(user != null) {
			return user.getLogin();
		} else {
			return null;
		}
	}

}
