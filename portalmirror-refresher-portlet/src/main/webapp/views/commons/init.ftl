<#assign portlet=JspTaglibs["/WEB-INF/tld/liferay-portlet.tld"]>
<#assign lr_portlet=JspTaglibs["/WEB-INF/tld/liferay-portlet-ext.tld"]>
<#assign lr_ui=JspTaglibs["/WEB-INF/tld/liferay-ui.tld"]>
<#assign lr_util=JspTaglibs["/WEB-INF/tld/liferay-util.tld"]>
<#assign lr_theme=JspTaglibs["/WEB-INF/tld/liferay-theme.tld"]>
<#assign aui=JspTaglibs["/WEB-INF/tld/aui.tld"]>

<@portlet.defineObjects />
<@lr_theme.defineObjects />

<#macro msg code>${languageUtil.get(portletConfig, locale, code)}</#macro>
<#macro pmsg code params>${languageUtil.format(portletConfig, locale, code, params)}</#macro>
<#macro info key><div class="alert alert-info"><i class="icon-info-sign"></i> <@msg key/></div></#macro>
<#macro error dkey><@lr_ui.error message=dkey key=dkey /></#macro>

<#macro showErrors>
<div class="form-validator-stack help-inline">
    <#list spring.status.errorMessages as error>
        <div class="required" role="alert"><@msg error/></div>
    </#list>
</div>
</#macro>