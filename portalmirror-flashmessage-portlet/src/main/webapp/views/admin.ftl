<#include "commons/init.ftl" >

<@portlet["actionURL"] name="sendMessage" var="sendMessageUrl"/>

<@aui.form action="${sendMessageUrl}" method="post" name="flashMessageForm">
	
		<@aui.input label="Message" name="text" type="textarea" />
		<@aui.input label="Period" name="period" type="range" min=500 max=20000 value=2000 />
		<@aui.select label="Client" id="clientId" name="clientId" required=true showEmptyOption=false >
            <#list connectedClients as client>
            	<@aui.option value="${client.clientId}">${client.clientId}</@>
            </#list>
        </@>
	
	<@aui["button-row"]>
        <@aui.button type="submit">Send</@>
	</@>
	
</@>
