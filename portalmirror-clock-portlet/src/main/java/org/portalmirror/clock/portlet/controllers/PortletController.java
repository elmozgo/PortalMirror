package org.portalmirror.clock.portlet.controllers;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;

import org.portalmirror.clock.common.portlet.CustomPropsUtil;
import org.portalmirror.clock.common.web.freemarker.keys.FreemarkerKeys;
import org.portalmirror.clock.common.web.freemarker.utils.FreemarkerValueUtil;
import org.portalmirror.clock.portlet.keys.ParameterKeys;
import org.portalmirror.clock.portlet.keys.PortletActionKeys;
import org.portalmirror.clock.portlet.keys.ViewKeys;
import org.portalmirror.clock.timezone.logic.TimezoneLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.context.PortletConfigAware;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;


@Controller
@RequestMapping(PortletActionKeys.VIEW)
public class PortletController implements PortletConfigAware {
    private static final Log LOG = LogFactoryUtil.getLog(PortletController.class);

    private PortletConfig portletConfig;

    @Autowired
    FreemarkerValueUtil freemarkerValueUtil;
    @Autowired
    private TimezoneLogic timezoneLogic;
    
    @RenderMapping
    public String handleRenderRequest(RenderRequest request, Model model) throws SystemException {
        if (!model.containsAttribute(FreemarkerKeys.LANGUAGEUTIL))
            freemarkerValueUtil.addLiferayUtils(request, model);
        
        model.addAttribute(ParameterKeys.DISPLAY_CONFIGURATION, timezoneLogic.getTimezoneDisplayConfiguration(request));
        
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
