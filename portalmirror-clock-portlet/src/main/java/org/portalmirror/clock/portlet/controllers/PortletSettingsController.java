package org.portalmirror.clock.portlet.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ValidatorException;

import org.apache.commons.lang3.StringUtils;
import org.portalmirror.clock.common.web.freemarker.keys.FreemarkerKeys;
import org.portalmirror.clock.common.web.freemarker.utils.FreemarkerValueUtil;
import org.portalmirror.clock.portlet.beans.SettingsForm;
import org.portalmirror.clock.portlet.beans.TimezoneLookupDto;
import org.portalmirror.clock.portlet.keys.ActionKeys;
import org.portalmirror.clock.portlet.keys.ParameterKeys;
import org.portalmirror.clock.portlet.keys.PortletActionKeys;
import org.portalmirror.clock.portlet.keys.ResourceKeys;
import org.portalmirror.clock.portlet.keys.ViewKeys;
import org.portalmirror.clock.timezone.api.TimezoneQueryResponse;
import org.portalmirror.clock.timezone.logic.TimezoneLogic;
import org.portalmirror.clock.timezone.logic.TimezonePortletSettingsInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.portlet.context.PortletConfigAware;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;


@Controller
@RequestMapping(PortletActionKeys.EDIT)
public class PortletSettingsController implements PortletConfigAware {
    private static final Log LOG = LogFactoryUtil.getLog(PortletSettingsController.class);

    private PortletConfig portletConfig;

    @Autowired
    private FreemarkerValueUtil freemarkerValueUtil;
    
    @Autowired
    private TimezoneLogic timezoneLogic;
    
    @Autowired
    private ObjectMapper objectMapper;

    
    @RenderMapping
    public String handleRenderRequest(RenderRequest request, Model model)  {
        if (!model.containsAttribute(FreemarkerKeys.LANGUAGEUTIL))
            freemarkerValueUtil.addLiferayUtils(request, model);
        
        TimezonePortletSettingsInstance settingsInstance = timezoneLogic.getTimezonePortletSettingsInstance(request.getPreferences());
        
        model.addAttribute(ParameterKeys.SETTINGS_FORM, populateSettingsForm(settingsInstance));
        
        
        return ViewKeys.SETTINGS;
    }


	private SettingsForm populateSettingsForm(TimezonePortletSettingsInstance settingsInstance) {
		SettingsForm form = new SettingsForm();
        form.setDisplayedLocation(settingsInstance.getDisplayedLocation());
        form.setLocation(settingsInstance.getLocation());
        form.setManualConfiguration(settingsInstance.isManualTimezone());
        if(settingsInstance.isManualTimezone()) {
        	form.setManualTimezone(settingsInstance.getManualTimezone());
        } else {
        	form.setManualTimezone(StringUtils.EMPTY);
        }
        return form;
	}

    
    @ActionMapping(value = ActionKeys.UPDATE_SETTINGS)
    public void updateSettings(Model model, @ModelAttribute(ParameterKeys.SETTINGS_FORM) SettingsForm settingsForm,
                            ActionRequest request) throws ReadOnlyException, IOException, ValidatorException {
        
    	
    	timezoneLogic.saveTimezonePortletSettings(settingsForm, request);
    }
    
    @ResourceMapping(value = ResourceKeys.TIMEZONE_LOOKUP)
    public void getTimezoneLookup(@RequestParam String location, ResourceResponse response) throws IOException {
    	
    	TimezoneQueryResponse apiResponse = timezoneLogic.executeApiTimezoneQuery(location);
    	TimezoneLookupDto dto = new TimezoneLookupDto();
    	
    	if(apiResponse.isError()) {
    		
        	dto.setApiUnavailable(apiResponse.isGeneralError());
        	dto.setNoResults(apiResponse.isNoResultsError());	
    	} else {
    		dto.setTimezoneId(apiResponse.getTimezoneId()); 
    	}
    	
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(dto));
        out.close();
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
