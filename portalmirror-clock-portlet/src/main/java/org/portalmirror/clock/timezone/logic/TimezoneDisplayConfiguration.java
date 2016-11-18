package org.portalmirror.clock.timezone.logic;

public class TimezoneDisplayConfiguration {

	private final String timezoneId;
	private final String locationString;
	private final String weatherLocation;

	private final DisplayedError error;

	public TimezoneDisplayConfiguration(String timezoneId, String locationString) {
		this.timezoneId = timezoneId;
		this.locationString = locationString;
		this.weatherLocation = locationString;
		this.error = null;
	}

	public TimezoneDisplayConfiguration(String timezoneId, String locationString, String weatherLocation) {
		this.timezoneId = timezoneId;
		this.locationString = locationString;
		this.weatherLocation = weatherLocation;
		this.error = null;
	}

	public TimezoneDisplayConfiguration(String timezoneId, String locationString, DisplayedError error) {
		this.timezoneId = timezoneId;
		this.locationString = locationString;
		this.weatherLocation = locationString;
		this.error = error;
	}

	public String getLocationString() {
		return locationString;
	}

	public String getWeatherLocation() {
		return weatherLocation;
	}

	public String getTimezoneId() {
		return timezoneId;
	}

	public boolean isError() {
		return error != null;
	}

	public boolean isTimezoneUnknownError() {
		return error == DisplayedError.TIMEZONE_UNKNOWN_ERROR;
	}

	public boolean isTimezoneLookupUnavailable() {
		return error == DisplayedError.TIMEZONE_LOOKUP_UNAVAILABLE_ERROR;
	}

	public boolean isGeneralError() {
		return error == DisplayedError.GENERAL_ERROR;
	}

	public enum DisplayedError {
		TIMEZONE_UNKNOWN_ERROR, TIMEZONE_LOOKUP_UNAVAILABLE_ERROR, GENERAL_ERROR;
	}

}
