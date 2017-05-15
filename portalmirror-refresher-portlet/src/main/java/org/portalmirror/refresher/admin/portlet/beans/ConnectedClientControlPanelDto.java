package org.portalmirror.refresher.admin.portlet.beans;

import java.util.Date;

public class ConnectedClientControlPanelDto {

	private String clientId;
	private String socketId;
	private String pageId;
	private String loggedUser;
	private Date jwtRequestedDate;
	private String linkToPage;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSocketId() {
		return socketId;
	}
	public void setSocketId(String socketId) {
		this.socketId = socketId;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getLoggedUser() {
		return loggedUser;
	}
	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}
	public Date getJwtRequestedDate() {
		return jwtRequestedDate;
	}
	public void setJwtRequestedDate(Date jwtRequestedDate) {
		this.jwtRequestedDate = jwtRequestedDate;
	}
	public String getLinkToPage() {
		return linkToPage;
	}
	public void setLinkToPage(String linkToPage) {
		this.linkToPage = linkToPage;
	}
	
}
