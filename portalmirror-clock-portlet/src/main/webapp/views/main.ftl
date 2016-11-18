<#include "commons/init.ftl" >


<#assign clockPortletId><@portlet.namespace/>clock-portlet</#assign>



<div class="clock-portlet" id="${clockPortletId}">
		<div class="top-bar small">
            <span>
                <span class="date"></span>&nbsp;<span class="temp dimmed"></span><span class="location xdimmed">${displayconfiguration.locationString}</span>
            </span>
        </div>
		<div class="time">
            <span>
                <span class="hours-minutes">00:00</span><span class="seconds dimmed">12</span>
            </span>
		</div>
        <div class="bottom-bar small dimmed"><span class="location">${displayconfiguration.locationString}</span></div>
</div>




<script type="text/javascript">
	    
	document.addEventListener('DOMContentLoaded', function(){ 
		var portletDiv = document.getElementById('${clockPortletId}');
		var portletConfiguration = new PortletFrontConfiguration(portletDiv, '${displayconfiguration.timezoneId}');

		portletConfiguration.init();
		var theWeather = Object.create(weather);
		theWeather.params = Object.create(weather.params); //this is super ugly
		theWeather.init('4f2f98b678e46c27301e7a7ebf145e85', '${displayconfiguration.locationString}', portletDiv);
		
	}, false);

</script>
    
    
 