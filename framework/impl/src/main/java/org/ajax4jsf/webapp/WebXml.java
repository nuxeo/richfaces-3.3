/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.ajax4jsf.webapp;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.ajax4jsf.config.WebXMLParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.VersionBean;
import org.richfaces.VersionBean.Version;

/**
 * Parse at startup application web.xml and store servlet and filter mappings.
 * at runtime, used for convert resource key to uri, and vice versa.
 *
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:58:59 $
 *
 */
public class WebXml extends WebXMLParser implements Serializable {

    public static final String CONTEXT_ATTRIBUTE = WebXml.class.getName();

    /**
	 *
	 */
    private static final long serialVersionUID = -9042908418843695017L;

    static final Log _log = LogFactory.getLog(WebXml.class);

    public static final String RESOURCE_URI_PREFIX = "a4j";

    public static final String GLOBAL_RESOURCE_URI_PREFIX = "a4j/g";

    public static final String SESSION_RESOURCE_URI_PREFIX = "a4j/s";

    public static final String RESOURCE_URI_PREFIX_VERSIONED;

    public static final String GLOBAL_RESOURCE_URI_PREFIX_VERSIONED;

    public static final String SESSION_RESOURCE_URI_PREFIX_VERSIONED;

    static {
        VersionBean versionBean = new VersionBean();
        Version version = versionBean.getVersion();

        String suffix = "/" + version.getMajor() + "_" + version.getMinor()
                + "_" + version.getRevision();

        // that's to prevent static compile-time linkage to constant values
        RESOURCE_URI_PREFIX_VERSIONED = RESOURCE_URI_PREFIX + suffix;
        GLOBAL_RESOURCE_URI_PREFIX_VERSIONED = GLOBAL_RESOURCE_URI_PREFIX
                + suffix;
        SESSION_RESOURCE_URI_PREFIX_VERSIONED = SESSION_RESOURCE_URI_PREFIX
                + suffix;
    }

    public static final String RESOURCE_URI_PREFIX_PARAM = "org.ajax4jsf.RESOURCE_URI_PREFIX";

    public static final String GLOBAL_RESOURCE_URI_PREFIX_PARAM = "org.ajax4jsf.GLOBAL_RESOURCE_URI_PREFIX";

    public static final String SESSION_RESOURCE_URI_PREFIX_PARAM = "org.ajax4jsf.SESSION_RESOURCE_URI_PREFIX";

    /**
     * Prefix for resources handled by Chameleon framework.
     */
    String _resourcePrefix = RESOURCE_URI_PREFIX;

    String _globalResourcePrefix;

    String _sessionResourcePrefix;

    protected boolean _prefixMapping = false;

    public static WebXml getInstance() {
        return getInstance(FacesContext.getCurrentInstance());
    }

    public static WebXml getInstance(FacesContext context) {
        WebXml webXml = (WebXml) context.getExternalContext().getApplicationMap().get(
                WebXml.CONTEXT_ATTRIBUTE);
        return webXml;
    }

    @Override
    public void init(ServletContext context, String filterName)
            throws ServletException {
        super.init(context, filterName);
        setupResourcePrefixes(context);
        // Store Instance to context attribute.
        context.setAttribute(CONTEXT_ATTRIBUTE, this);

    }

    /**
     * Convert {@link org.ajax4jsf.resource.InternetResource } key to real URL
     * for handle by chameleon filter, depend of mapping in WEB.XML . For prefix
     * or * mapping, prepend servlet prefix and default Resource prefix to key.
     * For suffix mapping, prepend with resource prefix and append default faces
     * suffix to URL ( before request param ). After conversion, call
     * {@link javax.faces.application.ViewHandler#getResourceURL(javax.faces.context.FacesContext, java.lang.String)}
     * and
     * {@link javax.faces.context.ExternalContext#encodeResourceURL(java.lang.String)}
     * .
     *
     * @param context
     * @param Url
     * @return
     */
    public String getFacesResourceURL(FacesContext context, String Url,
            boolean isGlobal) {
        StringBuffer buf = new StringBuffer();
        buf.append(
                isGlobal ? getGlobalResourcePrefix()
                        : getSessionResourcePrefix()).append(Url);
        // Insert suffix mapping
        if (isPrefixMapping()) {
            buf.insert(0, getFacesFilterPrefix());
        } else {
            int index;
            if ((index = buf.indexOf("?")) >= 0) {
                buf.insert(index, getFacesFilterSuffix());
            } else {
                buf.append(getFacesFilterSuffix());
            }
        }
        String resourceURL = context.getApplication().getViewHandler().getResourceURL(
                context, buf.toString());
        return resourceURL;

    }

    @Deprecated
    public String getFacesResourceURL(FacesContext context, String Url) {
        return getFacesResourceURL(context, Url, false);
    }

    public String getFacesResourceKey(String resourcePath) {
        // Remove JSESSIONID - for expired sessions it will merged to path.
        int jsesionidStart;
        if ((jsesionidStart = resourcePath.lastIndexOf(";jsessionid")) >= 0) {
            resourcePath = resourcePath.substring(0, jsesionidStart);
        }
        String resourcePrefix = getResourcePrefix();
        if (isPrefixMapping()) {
            String facesFilterPrefix = getFacesFilterPrefix();

            if (resourcePath.startsWith(facesFilterPrefix)) {
                String sessionResourcePrefix = getSessionResourcePrefix();
                if (resourcePath.startsWith(sessionResourcePrefix,
                        facesFilterPrefix.length())) {
                    return resourcePath.substring(facesFilterPrefix.length()
                            + sessionResourcePrefix.length());
                } else {
                    String globalResourcePrefix = getGlobalResourcePrefix();
                    if (!sessionResourcePrefix.equals(globalResourcePrefix)
                            && resourcePath.startsWith(globalResourcePrefix,
                                    facesFilterPrefix.length())) {

                        return resourcePath.substring(facesFilterPrefix.length()
                                + globalResourcePrefix.length());
                    } else if (!globalResourcePrefix.equals(resourcePrefix)
                            && resourcePath.startsWith(resourcePrefix,
                                    facesFilterPrefix.length())) {

                        return resourcePath.substring(facesFilterPrefix.length()
                                + resourcePrefix.length());
                    }
                }
            }
        } else {
            String sessionResourcePrefix = getSessionResourcePrefix();
            if (resourcePath.startsWith(sessionResourcePrefix)) {
                return resourcePath.substring(sessionResourcePrefix.length(),
                        resourcePath.length() - getFacesFilterSuffix().length());
            } else {
                String globalResourcePrefix = getGlobalResourcePrefix();
                if (!sessionResourcePrefix.equals(globalResourcePrefix)
                        && resourcePath.startsWith(globalResourcePrefix)) {

                    return resourcePath.substring(
                            globalResourcePrefix.length(),
                            resourcePath.length()
                                    - getFacesFilterSuffix().length());
                } else if (!globalResourcePrefix.equals(resourcePrefix)
                        && resourcePath.startsWith(resourcePrefix)) {

                    return resourcePath.substring(resourcePrefix.length(),
                            resourcePath.length()
                                    - getFacesFilterSuffix().length());
                }
            }
        }

        return null;
    }

    /**
     * Detect request to resource and extract key from request
     *
     * @param request current http request
     * @return resource key, or null for ordinary faces request.
     */
    public String getFacesResourceKey(HttpServletRequest request) {
        String resourcePath = request.getRequestURI().substring(
                request.getContextPath().length());// isPrefixMapping()?request.getPathInfo():request.getServletPath();
        String s = getFacesResourceKey(resourcePath);
        if (null != s) {
            try {
                return URLDecoder.decode(s, "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                // Doesn't happen.
            }
        }

        return null;
    }

    /**
     * Detect request to {@link javax.faces.webapp.FacesServlet}
     *
     * @param request
     * @return true if request parsed to JSF.
     */
    public boolean isFacesRequest(HttpServletRequest request) {
        // String resourcePath =
        // request.getRequestURI().substring(request.getContextPath().length());//isPrefixMapping()?request.getPathInfo():request.getServletPath();
        // if(isPrefixMapping() ) {
        // if (resourcePath.startsWith(getFacesFilterPrefix())) {
        // return true;
        // }
        // } else if (resourcePath.endsWith(getFacesFilterSuffix())) {
        // return true;
        // }
        // return false;
        return true;
    }

    /**
     * @return Returns the resourcePrefix.
     */
    @Deprecated
    public String getResourcePrefix() {
        return _resourcePrefix;
    }

    /**
     * @since 3.2.2
     * @return
     */
    public String getGlobalResourcePrefix() {
        return _globalResourcePrefix;
    }

    /**
     * @since 3.2.2
     * @return
     */
    public String getSessionResourcePrefix() {
        return _sessionResourcePrefix;
    }

    /**
     * @return Returns the prefixMapping.
     */
    public boolean isPrefixMapping() {
        return _prefixMapping;
    }

    /**
     * @param resourcePrefix The resourcePrefix to set.
     */
    @Deprecated
    void setResourcePrefix(String resourcePrefix) {
        _resourcePrefix = resourcePrefix;
    }

    /**
     * @since 3.2.2
     * @param resourcePrefix The resourcePrefix to set.
     */
    void setGlobalResourcePrefix(String resourcePrefix) {
        _globalResourcePrefix = resourcePrefix;
    }

    /**
     * @since 3.2.2
     * @param resourcePrefix The resourcePrefix to set.
     *
     */
    void setSessionResourcePrefix(String resourcePrefix) {
        _sessionResourcePrefix = resourcePrefix;
    }

    /**
     * @param context
     */
    void setupResourcePrefixes(ServletContext context) {
        String globalResourcePrefix = context.getInitParameter(GLOBAL_RESOURCE_URI_PREFIX_PARAM);

        String sessionResourcePrefix = context.getInitParameter(SESSION_RESOURCE_URI_PREFIX_PARAM);

        String resourcePrefix = context.getInitParameter(RESOURCE_URI_PREFIX_PARAM);

        if (null != resourcePrefix) {
            if (globalResourcePrefix == null) {
                // TODO overriden
                globalResourcePrefix = resourcePrefix;
            }

            if (sessionResourcePrefix == null) {
                // TODO overriden
                sessionResourcePrefix = resourcePrefix;
            }
        } else {
            resourcePrefix = RESOURCE_URI_PREFIX_VERSIONED;
        }

        if (globalResourcePrefix == null) {
            // TODO overriden
            globalResourcePrefix = GLOBAL_RESOURCE_URI_PREFIX_VERSIONED;
        }

        if (sessionResourcePrefix == null) {
            // TODO overriden
            sessionResourcePrefix = SESSION_RESOURCE_URI_PREFIX_VERSIONED;
        }

        if (null != getFacesFilterPrefix()) {
            _prefixMapping = true;
            if (getFacesFilterPrefix().endsWith("/")) {
                setGlobalResourcePrefix(globalResourcePrefix);
                setSessionResourcePrefix(sessionResourcePrefix);
                setResourcePrefix(resourcePrefix);
            } else {
                setGlobalResourcePrefix("/" + globalResourcePrefix);
                setSessionResourcePrefix("/" + sessionResourcePrefix);
                setResourcePrefix("/" + resourcePrefix);
            }
        } else if (null != getFacesFilterSuffix()) {
            _prefixMapping = false;
            setResourcePrefix("/" + resourcePrefix);
            setGlobalResourcePrefix("/" + globalResourcePrefix);
            setSessionResourcePrefix("/" + sessionResourcePrefix);
        }
    }
}
