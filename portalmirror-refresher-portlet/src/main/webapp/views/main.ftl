<#include "commons/init.ftl" >

<script type="text/javascript">
	var refresherSocketConnection = new RefresherSocketConnection('${portletId}', '${plid?c}');
	refresherSocketConnection.init();
</script>

<#if refresherActionAuthorized>
	
	<@portlet.resourceURL var="refreshUrl" id="refreshFromFront" />
		
	<script type="text/javascript">
		var refresherIcon = new RefresherIcon('${refreshUrl}');
		refresherIcon.init();
	</script>
</#if>