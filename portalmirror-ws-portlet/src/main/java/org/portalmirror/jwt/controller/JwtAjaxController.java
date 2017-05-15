package org.portalmirror.jwt.controller;

import static org.portalmirror.jwt.controller.JwtAjaxController.JWT_AJAX_CONTROLLER_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.servlet.http.HttpServletRequest;

import org.portalmirror.jwt.logic.WebsocketJwtLogic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

@RequestMapping(JWT_AJAX_CONTROLLER_PATH)
public class JwtAjaxController {

	public static final String JWT_AJAX_CONTROLLER_PATH = "jwt/ajax";
	private static Log log = LogFactoryUtil.getLog(JwtAjaxController.class);

	@RequestMapping(value = "/ws-token", method = GET)
	public ResponseEntity<String> getWSAccessJwt(HttpServletRequest request, @RequestParam String portletName) {


		SignedJWT jwt;
		
		try {
			jwt = WebsocketJwtLogic.createSignedJwt(request, portletName);

		} catch (PortalException | SystemException  | JOSEException e) {
			log.error(e);
			return ResponseEntity.status(500).body("Server Error");
		} 
		
		return ResponseEntity.ok(jwt.serialize());
	}

}
