<#include "commons/init.ftl" >

<@portlet.actionURL var="settingsUrl" name="updateSettings" />
<@portlet.resourceURL var="timezoneLookupUrl" id="timezoneLookup" />
<#assign submitbtntxt><@msg "demo.navigation.save"/></#assign>

<h3><@msg "demo.title.settings"/></h3>

<@lr_ui.success key="demo.message.settings.success" message="demo.message.settings.success"/>

<@aui.form method="post" action="${settingsUrl}">
    <@aui.layout>
	    <#assign manualConfigurationId><@portlet.namespace/>manual-configuration-radio</#assign>
	    <#assign locationId><@portlet.namespace/>location</#assign>
	    <#assign manualTimezoneId><@portlet.namespace/>manual-timezone</#assign>
	    <#assign displayedLocationId><@portlet.namespace/>displayed-location</#assign>
	    
	    <@aui.fieldset> 
		    <@spring.bind "settingsForm.manualConfiguration"/>
		    <label for="${manualConfigurationId}" id="${manualConfigurationId}-label" ><@msg "label.settings.manualConfiguration"/></label>
		    <input type="checkbox" name="<@portlet.namespace/>${spring.status.expression}" id="${manualConfigurationId}" value="false" 
		    	<#if spring.status.value==true >checked="checked"</#if>  />
	    </@aui.fieldset>
	    <div id="${locationId}-container"><@aui.fieldset>
	        <@spring.bind "settingsForm.location"/>
	        <label for="${locationId}" id="${locationId}-label"><@msg "label.settings.location"/></label>
	        <input type="text" name="<@portlet.namespace/>${spring.status.expression}" id="${locationId}" value="${spring.status.value?html}" />
	    	<div id="timezone-error" class="error"></div>
	    	<div id="timezone-spinner" class="loading-animation" style="display:none"></div>
	    	<div id="timezone-content" class="label"></div>
	    </@aui.fieldset>
	    </div>
	    
	    <div id="${manualTimezoneId}-container"><@aui.fieldset>
	        <@spring.bind "settingsForm.manualTimezone"/>
	        <label for="${manualTimezoneId}" id="${manualTimezoneId}-label"><@msg "label.settings.manualTimezone"/></label>
	        <input type="text" name="<@portlet.namespace/>${spring.status.expression}" id="${manualTimezoneId}" value="${spring.status.value?html}" />
	    </@aui.fieldset></div>
	    <@aui.fieldset>
		    <@spring.bind "settingsForm.displayedLocation"/>
		    <label for="${displayedLocationId}" id="${displayedLocationId}-label"><@msg "label.settings.displayedLocation"/></label>
		    <input type="text" name="<@portlet.namespace/>${spring.status.expression}" id="${displayedLocationId}" value="${spring.status.value?html}" />
		</@aui.fieldset>
	    <@aui.button type="submit" value="${submitbtntxt}" />
    </@aui.layout>
</@aui.form>

<script type="text/javascript">

    
function isManualConfiguration(){
    return document.getElementById('${manualConfigurationId}').checked == true;
}
    
function hideOrShowProperConfigurationInputs(){
    
    if(isManualConfiguration()) {
        document.getElementById('${locationId}-container').style.display = "none";
        document.getElementById('${manualTimezoneId}-container').style.display = "";
    } else {
        document.getElementById('${locationId}-container').style.display = "";
        document.getElementById('${manualTimezoneId}-container').style.display = "none";
    }
    
}  
    
function timezoneLookup() {
	
	var locationInput  = document.getElementById('${locationId}');
	var spinner = document.getElementById('timezone-spinner');
	var timezone = document.getElementById('timezone-content');
	var timezoneError = document.getElementById('timezone-error');
	
	if(locationInput.value != '') {
		
		spinner.style.display = null;
		
		AUI().use('aui-io-request', function(A){
            A.io.request('${timezoneLookupUrl}', {
                method: 'post',
                dataType: 'json',
				data: {
					<@portlet.namespace/>location: locationInput.value
				},
				on: {
					success: function(){
					
						var data = this.get('responseData');
	
	                    if(data.isNoResults){
	                        timezone.style.display = 'none';
	                        timezoneError.innerHTML = 'Cant find timezone for this location...';
	                        
	                    } else if(data.isApiUnavailable) {
	                        timezone.style.display = 'none';
	                        timezoneError.innerHTML = 'Timezone API unavailable';
	                        
	                    } else {
	                        timezone.innerHTML = 'Timezone found: '+data.timezoneId;
	                        timezone.style.display = null;
	                        timezoneError.innerHTML = null;
	                    
	                    }
					},
					
                	error: function(){
                        timezone.style.display = 'none';
                        timezoneError.innerHTML = 'Cant reach the server...';
                    }
              }
            });

            spinner.style.display = 'none';
	   });
	
    }
}
    
    

document.getElementById('${manualConfigurationId}').onclick = hideOrShowProperConfigurationInputs;
document.getElementById('${locationId}').oninput = debounce(timezoneLookup, 1000);

AUI().ready('event', 'node', function(A){
  
  
  hideOrShowProperConfigurationInputs();
  timezoneLookup();
  
});

    
</script>

    
    