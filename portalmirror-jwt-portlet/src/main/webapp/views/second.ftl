<#include "commons/init.ftl" >
<@portlet.resourceURL var="getSecretMessageURL" id="secretmessage"/>
<@portlet.actionURL var="killSessionURL" name="killsession" />
<#assign txt_btn_resource><@msg "demo.navigation.resource"/></#assign>
<#assign txt_btn_kill><@msg "demo.navigation.done"/></#assign>

<h1><@pmsg "demo.title.parameter.example" dummyobject.randomString/></h1>

<@aui["button-row"]>
    <@aui.button name="resource" type="button" value="${txt_btn_resource}"/>
    <@aui.button onClick="${killSessionURL}" value="${txt_btn_kill}" last="true"/>
</@>

<script type="text/javascript">
    AUI().use('node', 'aui-base', 'aui-io-request', function(A) {
        var getSecretMessage = A.io.request('${getSecretMessageURL}', {
            dataType: 'json',
            cache: false,
            autoLoad: false, /* So that this doesn't automatically run when the javascript is first loaded */
            on: {
                failure: function () {
                    /* Here we would handle the error */
                },
                success: function () {
                    alert(this.get('responseData').secretmessage);
                    return;
                }
            }
        });

        demo = function() {
            getSecretMessage.set("data", {
                <@portlet.namespace/>mymessage: "This is very secret"
            });
            getSecretMessage.start();
        }

        A.one('#<@portlet.namespace/>resource').on('click', demo);
    });
</script>