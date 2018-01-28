package org.portalmirror.jwt.logic;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.portalmirror.jwt.common.portlet.CustomPropsUtil;
import org.portalmirror.websocket.jwt.WebsocketJwtFactory;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;



public class WebsocketJwtLogic {
	
	private static final int VALID_FOR_TWO_MINUTES = 120;


	private static final String WEBSOCKET_NODE_SECRET_PROPERTY = "websocket.node.secret";

	
	private static Log log = LogFactoryUtil.getLog(WebsocketJwtLogic.class);
	
	public static SignedJWT createSignedJwt(HttpServletRequest request, String portletName) throws PortalException, SystemException, JOSEException {
		
		byte[] secret = CustomPropsUtil.getProp(WEBSOCKET_NODE_SECRET_PROPERTY).getBytes(StandardCharsets.UTF_8);
		
		WebsocketJwtFactory jwtFactory = new WebsocketJwtFactory(VALID_FOR_TWO_MINUTES);
		
		JWTClaimsSet claimsSet = jwtFactory.createJwtClaimSet(getLoggedInUsernameOrNull(request), portletName, request.getRequestURI());
		
		log.info("jwt created: \n" + claimsSet.toJSONObject().toJSONString());
		
			
		return WebsocketJwtFactory.signJwt(claimsSet, secret);
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
