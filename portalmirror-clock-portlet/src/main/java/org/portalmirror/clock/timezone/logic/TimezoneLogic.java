package org.portalmirror.clock.timezone.logic;

import static org.portalmirror.clock.timezone.logic.TimezoneDisplayConfiguration.DisplayedError.TIMEZONE_LOOKUP_UNAVAILABLE_ERROR;
import static org.portalmirror.clock.timezone.logic.TimezoneDisplayConfiguration.DisplayedError.TIMEZONE_UNKNOWN_ERROR;

import java.io.IOException;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import org.apache.commons.lang3.StringUtils;
import org.portalmirror.clock.portlet.beans.SettingsForm;
import org.portalmirror.clock.portlet.keys.SettingsKeys;
import org.portalmirror.clock.timezone.api.TimezoneApiLogic;
import org.portalmirror.clock.timezone.api.TimezoneQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TimezoneLogic {

	private static final String DEFAULT_TIMEZONE = "Europe/London";

	@Autowired
	@Qualifier("geonamesApiLogic")
	private TimezoneApiLogic timezoneApiLogic;

	public TimezoneDisplayConfiguration getTimezoneDisplayConfiguration(PortletRequest portletRequest) {

		TimezonePortletSettingsInstance settings = getTimezonePortletSettingsInstance(portletRequest.getPreferences());

		if (settings.isManualTimezone()) {
			return new TimezoneDisplayConfiguration(settings.getManualTimezone(), settings.getDisplayedLocation());
		} else {

			return getDisplayConfigurationWithTimezoneApi(settings);
		}
	}

	public TimezonePortletSettingsInstance getTimezonePortletSettingsInstance(PortletPreferences portletPreferences) {
		
		return new TimezonePortletSettingsInstance(portletPreferences);
	}
	
	public void saveTimezonePortletSettings(SettingsForm form, PortletRequest portletRequest) throws ReadOnlyException, ValidatorException, IOException {
		
		 PortletPreferences preferences = portletRequest.getPreferences();
		 
		 preferences.setValue(SettingsKeys.LOCATION_STRING, form.getLocation());
		 preferences.setValue(SettingsKeys.MANUAL_DISPLAYED_LOCATION, form.getDisplayedLocation());
		 if(form.isManualConfiguration()) {
			 preferences.setValue(SettingsKeys.MANUAL_TIMEZONE, form.getManualTimezone());
		 } else {
			 preferences.setValue(SettingsKeys.MANUAL_TIMEZONE, StringUtils.EMPTY);
		 }
		 
		 preferences.store();
	}

	private TimezoneDisplayConfiguration getDisplayConfigurationWithTimezoneApi(TimezonePortletSettingsInstance settings) {
		
		TimezoneQueryResponse apiResponse = executeApiTimezoneQuery(settings.getLocation());
		String displayedLocation = settings.getDisplayedLocation();
		String weatherLocation = settings.getLocation();
		
		if (apiResponse.isError()) {
			if (apiResponse.isNoResultsError()) {
				return new TimezoneDisplayConfiguration(DEFAULT_TIMEZONE, displayedLocation, TIMEZONE_UNKNOWN_ERROR);
			} else {
				return new TimezoneDisplayConfiguration(DEFAULT_TIMEZONE, displayedLocation,
						TIMEZONE_LOOKUP_UNAVAILABLE_ERROR);
			}
		}

		return new TimezoneDisplayConfiguration(apiResponse.getTimezoneId(), displayedLocation, weatherLocation);
	}

	public TimezoneQueryResponse executeApiTimezoneQuery(String location) {
		return timezoneApiLogic.getTimezoneResponseQuery(location);
	}

}
