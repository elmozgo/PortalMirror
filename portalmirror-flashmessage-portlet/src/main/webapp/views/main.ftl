<#include "commons/init.ftl" >

<#assign messagePlaceholderId><@portlet.namespace/>message-placeholder</#assign>

<div class="flash-message-container" id="${messagePlaceholderId}">
	<span></span>
</div>

<script type="text/javascript">
	
	var placeholder = document.getElementById('${messagePlaceholderId}');
	
	var flashMessageSocketConnection = new FlashMessageSocketConnection(placeholder, '${portletId}', '${plid?c}');
	flashMessageSocketConnection.init();
</script>