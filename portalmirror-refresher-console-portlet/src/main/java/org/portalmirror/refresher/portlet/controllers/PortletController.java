package org.portalmirror.refresher.portlet.controllers;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.util.PortalUtil;
import org.portalmirror.refresher.common.web.freemarker.utils.FreemarkerValueUtil;
import org.portalmirror.refresher.common.web.freemarker.keys.FreemarkerKeys;
import org.portalmirror.refresher.common.portlet.CustomPropsUtil;
import org.portalmirror.refresher.portlet.beans.DummyObject;
import org.portalmirror.refresher.portlet.keys.ParameterKeys;
import org.portalmirror.refresher.portlet.keys.ActionKeys;
import org.portalmirror.refresher.portlet.keys.PortletActionKeys;
import org.portalmirror.refresher.portlet.keys.ViewKeys;
import org.portalmirror.refresher.portlet.keys.ResourceKeys;
import org.portalmirror.refresher.portlet.validators.DemoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.portlet.context.PortletConfigAware;

import javax.portlet.*;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;

// TODO: Make readme for github */
// TODO: Publish on github publically as well, remember readme, and my name as copyright and publish in my blog as well
// and remember to mention that you can use both jsp and freemarker*/

/**
 * main controller class for the order portlet spring mvc application
 */

@Controller
@RequestMapping(PortletActionKeys.VIEW)
/* Use this for automatically retaining model attributes on the users session */
@SessionAttributes({ParameterKeys.DUMMYOBJECT})
public class PortletController implements PortletConfigAware {
    private static final Log LOG = LogFactoryUtil.getLog(PortletController.class);

    private PortletConfig portletConfig;

    @Autowired
    FreemarkerValueUtil freemarkerValueUtil;
    @Autowired
    DemoValidator demoValidator;
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
        LOG.warn(CustomPropsUtil.getProp("custom.portlet.properties"));
        return ViewKeys.MAIN;
    }

    /**
     * Render mapping after handling a stuff action request successfully
     *
     * @param request Render request
     * @param model Spring MVC model object
     * @return view to render
     */
    @RenderMapping(params = ActionKeys.STUFF_KEY)
    public String handleStuffRenderRequest(RenderRequest request, Model model) {
        /* We could potentially also ensure that these three objects were stored in session rather than
        adding them in each render request */
        if (!model.containsAttribute(FreemarkerKeys.LANGUAGEUTIL))
            freemarkerValueUtil.addLiferayUtils(request, model);
        return ViewKeys.SECOND;
    }

    /**
     * Demo action mapping
     * @param dummyObject A dummy model object for demonstration
     * @param binding Spring MVC binding object
     * @param model Spring MVC model object
     * @param request Action request
     */
    @ActionMapping(value = ActionKeys.STUFF)
    public void doStuff( @ModelAttribute(ParameterKeys.DUMMYOBJECT) @Valid DummyObject dummyObject,
                        BindingResult binding,
                        Model model, ActionRequest request, ActionResponse response) {
        /* Hide the default Liferay request failed error message on error. We use our own. */
        SessionMessages.add(request, PortalUtil.getPortletId(request)+SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
        /**
         * Example of using a manually defined validator. Notice that the dummyObject also uses inline annoations for validation,
         * you can choose to use either, or both. See http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single
         * for more information. You should prefer inline annotations whenever possible.
         **/
        demoValidator.validate(dummyObject, binding);
        if (binding.hasErrors())
            return;
        /**
         * If we wantet to trigger our generic demo.error.ws.generic message (see main.ftl) here we would use the following:
         * if (something) {
         *     SessionErrors.add(request, ErrorMessageKeys.GENERIC_WS);
         *     return;
         * }
         */
        model.addAttribute(ParameterKeys.DUMMYOBJECT, dummyObject);
        response.setRenderParameter(ActionKeys.ACTION, ActionKeys.STUFF);
    }

    /**
     * Example of killing the session when complete
     *
     * @param request Action request
     * @param model Spring MVC model object
     * @param status session status object we are going to use to finish the session
     */
    @ActionMapping(value = ActionKeys.KILLSESSION)
    public void killSession(ActionRequest request,
                            Model model, SessionStatus status) {
        /* Hide the default Liferay request failed error message on error. We use our own. */
        SessionMessages.add(request, PortalUtil.getPortletId(request)+SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
        status.setComplete();
    }

    /**
     * Example of a resource request
     *
     * @param mymessage example of getting a parameter
     * @param response Resource response
     * @throws IOException
     */
    @ResourceMapping(value = ResourceKeys.SECRETMESSAGE)
    public void getSecretMessage(@RequestParam String mymessage, ResourceResponse response) throws IOException {
        /* Here we really can return anything we like, json, text, xml (if you're one of _those_ guys), html,
        a pdf, an image. Just make sure that the content type is correctly set. The manual construction of the
         json below is just for demo purposes. Use a library to convert java POJOS to json.
         */
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{\"secretmessage\": \"Your message was: " + mymessage + "\"}");
        out.close();
    }

    /**
     * Create Spring MVC model attribute that demonstrates the use of automatically created model objects
     *
     * @return a new DummyObject
     */
    @ModelAttribute(ParameterKeys.DUMMYOBJECT)
    public DummyObject createDummyObject() {
        /* Here you could perhaps want to populate the object with default values */
        return new DummyObject();
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
