package org.portalmirror.refresher.admin.portlet.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;

import org.portalmirror.refresher.admin.portlet.beans.ConnectedClientControlPanelDto;
import org.portalmirror.refresher.admin.portlet.keys.ActionKeys;
import org.portalmirror.refresher.common.web.freemarker.keys.FreemarkerKeys;
import org.portalmirror.refresher.common.web.freemarker.utils.FreemarkerValueUtil;
import org.portalmirror.refresher.logic.RefresherNotificationService;
import org.portalmirror.refresher.admin.portlet.keys.PortletActionKeys;
import org.portalmirror.refresher.admin.portlet.keys.ViewKeys;
import org.portalmirror.websocket.client.ConnectedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.context.PortletConfigAware;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;

@Controller
@RequestMapping(PortletActionKeys.VIEW)
public class PortletAdminController implements PortletConfigAware {
    private static final Log LOG = LogFactoryUtil.getLog(PortletAdminController.class);

    private PortletConfig portletConfig;
    
    @Autowired
    RefresherNotificationService refresherNotificationService;

    @Autowired
    FreemarkerValueUtil freemarkerValueUtil;

    @RenderMapping
    public String handleRenderRequest(RenderRequest request, Model model) throws SystemException {
        if (!model.containsAttribute(FreemarkerKeys.LANGUAGEUTIL))
            freemarkerValueUtil.addLiferayUtils(request, model);
       
        List<ConnectedClient> connectedClients = refresherNotificationService.getAllConnectedClients();
        
        model.addAttribute("connectedClients", convertConnectedClientObjectsToDtos(connectedClients));
        return ViewKeys.ADMIN;
    }
    
    @ActionMapping(value = ActionKeys.REFRESH_ACTION)
    public void handleRefreshAction(@RequestParam String clientId) {
    	
    	refresherNotificationService.refreshClient(clientId);
    	
    }
    
    public static List<ConnectedClientControlPanelDto> convertConnectedClientObjectsToDtos(List<ConnectedClient> clients) throws NumberFormatException, SystemException {
    	
    	List<ConnectedClientControlPanelDto> dtos = new ArrayList<>();
    	
    	for(ConnectedClient client : clients) {
    		ConnectedClientControlPanelDto dto = new ConnectedClientControlPanelDto();
    		dto.setClientId(client.getClientId());
    		dto.setSocketId(client.getSocketId());
    		dto.setPageId(client.getPageId());
    		dto.setLoggedUser(client.getLoggedUsername());
    		dto.setJwtRequestedDate(client.getJwt().getIssueTime());
    		
    		Layout layout = LayoutLocalServiceUtil.fetchLayout(Long.valueOf(client.getPageId()));
    		dto.setLinkToPage(layout.getFriendlyURL());
    		
    		dtos.add(dto);
    	}
    	
    	return dtos;
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
