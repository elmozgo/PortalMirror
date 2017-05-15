package org.portalmirror.flashmessage.admin.portlet.controllers;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;

import org.portalmirror.flashmessage.common.web.freemarker.keys.FreemarkerKeys;
import org.portalmirror.flashmessage.common.web.freemarker.utils.FreemarkerValueUtil;
import org.portalmirror.flashmessage.admin.portlet.keys.PortletActionKeys;
import org.portalmirror.flashmessage.admin.portlet.keys.ViewKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.context.PortletConfigAware;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

// TODO: Make readme for github */
// TODO: Publish on github publically as well, remember readme, and my name as copyright and publish in my blog as well
// and remember to mention that you can use both jsp and freemarker*/

/**
 * main controller class for the order portlet spring mvc application
 */

@Controller
@RequestMapping(PortletActionKeys.VIEW)
/* Use this for automatically retaining model attributes on the users session */
public class PortletAdminController implements PortletConfigAware {
    private static final Log LOG = LogFactoryUtil.getLog(PortletAdminController.class);

    private PortletConfig portletConfig;

    @Autowired
    FreemarkerValueUtil freemarkerValueUtil;
    @Autowired
    /* Autowire other stuff here */

    /**
     * General render mapping. Includes correct log ussage, and portlet.properties that can be overridden in
     * portal-ext.properties example
     *
     * @param request Render request
     * @param model Spring MVC model object
     * @return view to render
     */
    @RenderMapping
    public String handleRenderRequest(RenderRequest request, Model model) throws SystemException {
        if (!model.containsAttribute(FreemarkerKeys.LANGUAGEUTIL))
            freemarkerValueUtil.addLiferayUtils(request, model);
        /* Always use the Liferay log system. This doubles as an example of using the CustomPropsUtil to have
        portal.properties props that can be overridden in portal-ext.properties. Note that you will get an exception
        if you try to retrieve a property that doesn't exist. If you are unsure if a propertie exists, for example
        if you don't plan on putting a default property in the portal.properties file, use the second method that
        takes a default value.
         */
        return ViewKeys.MAIN;
    }

    /**
     * Ensure we get access to the portlet config object
     *
     * @param portletConfig
     */
    @Override
    public void setPortletConfig(PortletConfig portletConfig) {
        this.portletConfig = portletConfig;
    }

}
