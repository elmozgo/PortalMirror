package org.portalmirror.refresher.logic;

import com.liferay.portal.security.permission.PermissionThreadLocal;

public class AuthorizationLogic {

	public static boolean isRefreshActionAuthorized() {
		
		return PermissionThreadLocal.getPermissionChecker().isOmniadmin();
		
	}
	
}
