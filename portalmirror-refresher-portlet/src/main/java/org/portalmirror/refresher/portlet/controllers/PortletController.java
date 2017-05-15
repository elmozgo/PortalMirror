package org.portalmirror.refresher.portlet.controllers;

import java.io.IOException;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.portalmirror.refresher.common.web.freemarker.keys.FreemarkerKeys;
import org.portalmirror.refresher.common.web.freemarker.utils.FreemarkerValueUtil;
import org.portalmirror.refresher.logic.AuthorizationLogic;
import org.portalmirror.refresher.logic.RefresherNotificationService;
import org.portalmirror.refresher.portlet.keys.PortletActionKeys;
import org.portalmirror.refresher.portlet.keys.ResourceKeys;
import org.portalmirror.refresher.portlet.keys.ViewKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
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

    @Autowired
    RefresherNotificationService refresherNotificationService;
    
    @RenderMapping
    public String handleRenderRequest(RenderRequest request, Model model) throws SystemException {
        if (!model.containsAttribute(FreemarkerKeys.LANGUAGEUTIL))
            freemarkerValueUtil.addLiferayUtils(request, model);
       
        String portletId = (String) request.getAttribute(WebKeys.PORTLET_ID);
        
        ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute (WebKeys.THEME_DISPLAY);
        String instanceId = themeDisplay.getPortletDisplay().getInstanceId();
        Layout layout = themeDisplay.getLayout(); 
        Long plid = layout.getPlid() ;
        
        model.addAttribute("portletId", portletId + "_INSTANCE_" + instanceId);
        model.addAttribute("plid", Long.toString(plid));
        model.addAttribute("refresherActionAuthorized", AuthorizationLogic.isRefreshActionAuthorized());
        
        return ViewKeys.MAIN;
    }
    
    
    @ResourceMapping(value = ResourceKeys.REFRESH_AJAX_RESOURCE)
    public void refreshAllInstancesOfRequestSourcePageId(ResourceRequest request, ResourceResponse response) throws IOException {
    
    	if(AuthorizationLogic.isRefreshActionAuthorized()) {
    		
    		refresherNotificationService.refreshAllClientsOnPage(Long.toString(getPlid(request)));
    		
    	} else {
    		throw new IllegalStateException("User not authorised to refresh");
    	}
    }

    private Long getPlid(PortletRequest request) {
        
    	ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute (WebKeys.THEME_DISPLAY);
        return themeDisplay.getLayout().getPlid();	
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
