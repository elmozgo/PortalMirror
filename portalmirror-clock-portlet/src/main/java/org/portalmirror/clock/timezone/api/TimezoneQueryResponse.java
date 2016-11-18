package org.portalmirror.clock.timezone.api;


public class TimezoneQueryResponse {

	private final String timezoneId;
	private final ErrorType error;
	private final Exception originalException;

	public TimezoneQueryResponse(String timezoneId) {
		this.timezoneId = timezoneId;
		this.error = null;
		this.originalException = null;
		
	}

	public TimezoneQueryResponse(ErrorType error) {
		this(error, null);
	}

	public TimezoneQueryResponse(ErrorType error, Exception originalException) {
		this.timezoneId = null;
		this.error = error;
		this.originalException = originalException;
	}

	public String getTimezoneId() {
		return timezoneId;
	}
	
	public boolean isError() {
		return error != null;
	}

	public boolean isNoResultsError() {
		return error == ErrorType.NO_RESULTS;
	}
	
	public boolean isGeneralError() {
		return error == ErrorType.GENERAL_ERROR;
	}

	public Exception getOriginalException() {
		return originalException;
	}

	public enum ErrorType {
		NO_RESULTS, GENERAL_ERROR;
	}

}
