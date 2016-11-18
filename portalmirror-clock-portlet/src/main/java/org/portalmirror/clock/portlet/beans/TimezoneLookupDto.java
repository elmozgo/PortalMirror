package org.portalmirror.clock.portlet.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimezoneLookupDto {
	
	private String timezoneId;
	private boolean noResults;
	private boolean apiUnavailable;
	
	public String getTimezoneId() {
		return timezoneId;
	}
	public void setTimezoneId(String timezoneId) {
		this.timezoneId = timezoneId;
	}
	
	@JsonProperty("isNoResults")
	public boolean isNoResults() {
		return noResults;
	}
	public void setNoResults(boolean noResults) {
		this.noResults = noResults;
	}
	@JsonProperty("isApiUnavailable")
	public boolean isApiUnavailable() {
		return apiUnavailable;
	}
	public void setApiUnavailable(boolean apiUnavailable) {
		this.apiUnavailable = apiUnavailable;
	}
	

}
