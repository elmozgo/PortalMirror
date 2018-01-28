package org.portalmirror.jwt.controller;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

import java.util.jar.Attributes.Name;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.PortalUtil;

public class AjaxCsrfInterceptor extends HandlerInterceptorAdapter {

	private static Log log = LogFactoryUtil.getLog(AjaxCsrfInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		
		try {
			AuthTokenUtil.checkCSRFToken(PortalUtil.getOriginalServletRequest(request), this.getClass().getName());
		} catch (PrincipalException exception) {
			
			log.info("Incorrect CSRF Token;"+request.getRemoteAddr()+";"+request.getRequestURL().toString(), exception);
			
			response.setStatus(SC_FORBIDDEN);
			
			return false;
		}
		
		return true;
	}
	
}
