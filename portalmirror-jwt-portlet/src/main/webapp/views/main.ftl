<#include "commons/init.ftl" >
<@portlet.actionURL var="stuffUrl" name="stuff" />
<#assign txt_err_randomstring><@msg "demo.error.randomstring.required"/></#assign>
<#assign txt_btn_test><@msg "demo.navigation.test"/></#assign>

<h1><@msg "demo.title.main"/></h1>

<@error "demo.error.ws.generic" />
<@aui.form method="post" action="${stuffUrl}">

    <@spring.bind "dummyobject.randomString"/>
<label for="<@portlet.namespace/>${spring.status.expression}"><@msg "demo.label.randomstring"/></label>
    <@aui["field-wrapper"] cssClass="${spring.status.isError()?string('error','')}">
        <@aui.input type="text" name="${spring.status.expression}" value="${spring.status.value!''}" label="" >
            <@aui.validator name="required" errorMessage="${txt_err_randomstring}" />
        </@aui.input>
        <@showErrors />
    </@>

    <@aui.button type="submit" value="${txt_btn_test}" />
</@aui.form>