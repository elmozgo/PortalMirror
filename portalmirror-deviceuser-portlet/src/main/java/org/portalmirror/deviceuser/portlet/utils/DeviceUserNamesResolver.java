package org.portalmirror.deviceuser.portlet.utils;

import com.liferay.portal.model.User;

public class DeviceUserNamesResolver {

    public static String resolveOrganisationName(User user) {

        return "devices_organization_" + user.getUserId();
    }

    public static String resolveGroupNameDesc(User user) {

        return "devices of userId: " + user.getUserId();
    }

    public static String resolveOrganisationComment(User user) {

        return "devices of userId: " + user.getUserId();
    }


}
