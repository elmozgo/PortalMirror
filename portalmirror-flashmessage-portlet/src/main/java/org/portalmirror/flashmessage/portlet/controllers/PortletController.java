package org.portalmirror.flashmessage.portlet.controllers;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;

import org.portalmirror.flashmessage.common.web.freemarker.keys.FreemarkerKeys;
import org.portalmirror.flashmessage.common.web.freemarker.utils.FreemarkerValueUtil;
import org.portalmirror.flashmessage.portlet.keys.PortletActionKeys;
import org.portalmirror.flashmessage.portlet.keys.ViewKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.context.PortletConfigAware;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;


@Controller
@RequestMapping(PortletActionKeys.VIEW)
public class PortletController implements PortletConfigAware {
    private static final Log LOG = LogFactoryUtil.getLog(PortletController.class);

    private PortletConfig portletConfig;

    @Autowired
    FreemarkerValueUtil freemarkerValueUtil;

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
        
        //TODO: VERY UGLY COPY OF REFRESHER PORTLET!!!! - REFACTOR PLEASE
        String portletId = (String) request.getAttribute(WebKeys.PORTLET_ID);
        
        ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute (WebKeys.THEME_DISPLAY);
        String instanceId = themeDisplay.getPortletDisplay().getInstanceId();
        Layout layout = themeDisplay.getLayout(); 
        Long plid = layout.getPlid() ;
        
        model.addAttribute("portletId", portletId + "_INSTANCE_" + instanceId);
        model.addAttribute("plid", Long.toString(plid));
        
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
