package org.portalmirror.jwt.common.web.freemarker.utils;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.portalmirror.jwt.common.web.freemarker.keys.FreemarkerKeys;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;

/**
 * Created by nobrelee on 2/19/14.
 */
public class FreemarkerValueUtil {
    @Autowired
    private LanguageUtil languageUtil;
    @Autowired
    private HtmlUtil htmlUtil;

    /**
     * Adds needed Liferay utils and attributes for use in freemarker
     *
     * @param request request to retrieve portletconfig from
     * @param model model to add needed utils to
     */
    public void addLiferayUtils(RenderRequest request, Model model) {
        PortletConfig portletConfig = (PortletConfig) request.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
        model.addAttribute(FreemarkerKeys.PORTLETCONFIG, portletConfig);
        model.addAttribute(FreemarkerKeys.LANGUAGEUTIL, languageUtil);
        model.addAttribute(FreemarkerKeys.LOCALE, request.getLocale());
	model.addAttribute(FreemarkerKeys.HTML_UTIL, htmlUtil);
    }
}
