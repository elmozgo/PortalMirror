<#include "commons/init.ftl" >

<@portlet.actionURL var="settingsUrl" name="settings" />
<#assign submitbtntxt><@msg "demo.navigation.save"/></#assign>

<h1><@msg "demo.title.settings"/></h1>

<@lr_ui.success key="demo.message.settings.success" message="demo.message.settings.success"/>

<@aui.form method="post" action="${settingsUrl}">
    <@spring.bind "settings.randomSetting"/>
<label for="<@portlet.namespace/>${spring.status.expression}"><@msg "demo.label.settings.randomsetting"/>
    <input type="checkbox" name="<@portlet.namespace/>${spring.status.expression}" value="true" ${spring.status.value?string('checked="checked"','')}/>
</label>
    <@aui.button type="submit" value="${submitbtntxt}" />
</@>