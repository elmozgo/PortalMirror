<#include "commons/init.ftl" >


<@lr_ui["search-container"] delta=20 emptyResultsMessage="There are no connected clients.">
    <@lr_ui["search-container-results"] results=connectedClients total=connectedClients?size>
    </@>

    <@lr_ui["search-container-row"] className="org.portalmirror.refresher.admin.portlet.beans.ConnectedClientControlPanelDto" keyProperty="clientId" modelVar="client">
        <@portlet["actionURL"] name="refresh" var="actionURL">
            <@portlet["param"] name="clientId" value=client.getClientId()> </@>
        </@>
        <@lr_ui["search-container-column-text"] name="Client ID" orderable=true value=client.getClientId()> </@>
        <@lr_ui["search-container-column-text"] name="Page ID" orderable=true value=client.getPageId()> </@>
        <@lr_ui["search-container-column-text"] name="Logged User" orderable=true value=client.getLoggedUser()> </@>
        <@lr_ui["search-container-column-date"] name="JWT iss" orderable=true value=client.getJwtRequestedDate()> </@>
        <@lr_ui["search-container-column-text"] name="Link" orderable=false href=client.getLinkToPage() value=client.getLinkToPage()> </@>
        <@lr_ui["search-container-column-text"] name="Action" orderable=false href=actionURL value="Refresh"> </@>
    </@>
    
    <@lr_ui["search-iterator"]> </@>    
</@>