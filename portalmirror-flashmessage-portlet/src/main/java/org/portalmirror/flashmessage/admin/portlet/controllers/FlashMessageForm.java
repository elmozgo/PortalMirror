package org.portalmirror.flashmessage.admin.portlet.controllers;

public class FlashMessageForm {
	
	private String text;
	private Integer period;
	private String clientId;
	
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
