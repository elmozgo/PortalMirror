package org.portalmirror.flashmessage.admin.portlet.controllers;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;

import org.portalmirror.flashmessage.admin.portlet.keys.ActionKeys;
import org.portalmirror.flashmessage.admin.portlet.keys.ParameterKeys;
import org.portalmirror.flashmessage.admin.portlet.keys.PortletActionKeys;
import org.portalmirror.flashmessage.admin.portlet.keys.ViewKeys;
import org.portalmirror.flashmessage.common.web.freemarker.keys.FreemarkerKeys;
import org.portalmirror.flashmessage.common.web.freemarker.utils.FreemarkerValueUtil;
import org.portalmirror.flashmessage.logic.FlashMessageNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.context.PortletConfigAware;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * main controller class for the order portlet spring mvc application
 */

@Controller
@RequestMapping(PortletActionKeys.VIEW)
public class PortletAdminController implements PortletConfigAware {
	private static final Log LOG = LogFactoryUtil.getLog(PortletAdminController.class);

	private PortletConfig portletConfig;

	@Autowired
	FreemarkerValueUtil freemarkerValueUtil;

	@Autowired
	FlashMessageNotificationService flashMessageNotificationService;

	@RenderMapping
	public String handleRenderRequest(RenderRequest request, Model model) throws SystemException {
		if (!model.containsAttribute(FreemarkerKeys.LANGUAGEUTIL))
			freemarkerValueUtil.addLiferayUtils(request, model);

		model.addAttribute("connectedClients", flashMessageNotificationService.getAllConnectedClients());

		return ViewKeys.ADMIN;
	}

	@ActionMapping(value = ActionKeys.SEND_MESSAGE_ACTION)
	public void handleSendMessageAction(@ModelAttribute(ParameterKeys.FLASH_MESSAGE_FORM) FlashMessageForm form) {

		flashMessageNotificationService.sendMessage(form.getClientId(), form.getText(), form.getPeriod());

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
