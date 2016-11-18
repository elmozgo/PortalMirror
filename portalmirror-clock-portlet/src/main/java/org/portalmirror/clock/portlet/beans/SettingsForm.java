package org.portalmirror.clock.portlet.beans;

public class SettingsForm {

	private String location;
	private String displayedLocation;
	private boolean manualConfiguration;
	private String manualTimezone;
	
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDisplayedLocation() {
		return displayedLocation;
	}
	public void setDisplayedLocation(String displayedLocation) {
		this.displayedLocation = displayedLocation;
	}
	public boolean isManualConfiguration() {
		return manualConfiguration;
	}
	public void setManualConfiguration(boolean manualConfiguration) {
		this.manualConfiguration = manualConfiguration;
	}
	public String getManualTimezone() {
		return manualTimezone;
	}
	public void setManualTimezone(String manualTimezone) {
		this.manualTimezone = manualTimezone;
	}
	
}
