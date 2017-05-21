package org.portalmirror.flashmessage.logic;

public class FlashMessage {

	private String text;
	private Integer period;
	
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
	
}
