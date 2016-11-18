package org.portalmirror.refresher.common.web.freemarker;

import org.springframework.web.portlet.context.PortletContextAware;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.portlet.PortletContext;
import javax.servlet.ServletContext;
import java.lang.reflect.Method;

/**
 * This class is a fix used to ensure that freemarker works in portlets. Liferay only supports
 * freemarker at the portal level currently, this fix ensures that the servlet context is
 * derived from the portlet context rather than the portal context. For usage see order-portlet.xml
 */
public class ExtendedFreemarkerViewResolver extends FreeMarkerViewResolver implements PortletContextAware {
        private FreeMarkerConfigurer fmConfigurer;

        @Override
        protected AbstractUrlBasedView buildView(String viewName) throws Exception {
                FreeMarkerView fmView = (FreeMarkerView)super.buildView(viewName);

                fmView.setConfiguration(fmConfigurer.getConfiguration());
                fmView.setServletContext(getServletContext());

                return fmView;
        }

        public void setFmConfigurer(FreeMarkerConfigurer fmConfigurer) {
                this.fmConfigurer = fmConfigurer;
        }

        @Override
        public void setPortletContext(PortletContext context) {
                try {
                        Method m = context.getClass().getDeclaredMethod("getServletContext");
                        setServletContext((ServletContext)m.invoke(context));
                } catch (Exception e) {
                        throw new UnsupportedOperationException("couldn't get servletcontext", e);
                }
        }
}
