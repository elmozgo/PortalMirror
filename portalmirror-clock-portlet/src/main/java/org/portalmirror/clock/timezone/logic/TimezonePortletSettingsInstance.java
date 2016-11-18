package org.portalmirror.clock.timezone.logic;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import javax.portlet.PortletPreferences;

import org.apache.commons.lang3.StringUtils;
import org.portalmirror.clock.portlet.keys.SettingsKeys;

public class TimezonePortletSettingsInstance {
	

	private final String manualTimezone;
	private final String location;
	private final String displayedLocation;
	 
	public TimezonePortletSettingsInstance(PortletPreferences preferences) {
		
		this.manualTimezone = preferences.getValue(SettingsKeys.MANUAL_TIMEZONE, StringUtils.EMPTY);
		this.displayedLocation = preferences.getValue(SettingsKeys.MANUAL_DISPLAYED_LOCATION, StringUtils.EMPTY);
		this.location = preferences.getValue(SettingsKeys.LOCATION_STRING, StringUtils.EMPTY);
	}
	
	public boolean isManualTimezone(){
		return isNotBlank(manualTimezone);
	}
	
	public String getLocation() {
		return location;
	}

	public String getDisplayedLocation() {
		return displayedLocation;
	}
	
	public String getManualTimezone() {
		return manualTimezone;
	}

}
