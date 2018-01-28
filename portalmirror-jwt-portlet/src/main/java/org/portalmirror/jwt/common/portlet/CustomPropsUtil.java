package org.portalmirror.jwt.common.portlet;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.util.portlet.PortletProps;

/**
 * Static util used to retrieve project properties
 */
public class CustomPropsUtil {

    /**
     * Get a project property. If the property is overridden in portal-ext.properties, the override is used. If not
     * the default value from portlet.properties is used. If Neither exist an exception is thrown.
     * @param name property name
     * @return property value
     * @throws SystemException
     */
    public static String getProp(String name) throws SystemException {
        String value = PrefsPropsUtil.getString(name);
        if (value == null || value.length() < 1) {
            value = PortletProps.get(name);
        }
        if (value == null || value.length() < 1) {
            throw new SystemException("No "+name+" specified.");
        }
        return value;
    }

    /**
     * Get a project property. If the property is overridden in portal-ext.properties, the override is used. If not
     * the default value from portlet.properties is used. If Neither exist defaultValue is used.
     * @param name property name
     * @param defaultValue default value to use if property not found.
     * @return property value
     * @throws SystemException
     */
    public static String getProp(String name, String defaultValue) {
        String value = null;
        try {
            value = PrefsPropsUtil.getString(name);
        } catch (SystemException e) {
            return defaultValue;
        }
        if (value == null || value.length() < 1) {
            value = PortletProps.get(name);
        }
        if (value == null || value.length() < 1) {
            return defaultValue;
        }
        return value;
    }
}
