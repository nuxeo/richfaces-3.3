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
package org.ajax4jsf.gwt.jsf;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

/**
 * Render a gwt:page tag.  Scrapes the component tree for GWT components, and adds the
 * appropriate meta tags to the header.  Also creates the GWT script tag as the last child
 * of the head, and the history iframe as the first child of the body.
 * @author shura
 */
public class PageRenderer extends Renderer {

    public static final String FACES_SERVLET_PREFIX_PARAM = "org.ajax4jsg.gwt.SCRIPT_PREFIX";

    private static final String RENDERED_FLAG_BASE = "org.ajax4jsf.gwt.rendered.";
    /*
      * (non-Javadoc)
      *
      * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext,
      *      javax.faces.component.UIComponent)
      */
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeBegin(context, component);

        ResponseWriter writer = context.getResponseWriter();
        Map attributes = component.getAttributes();
        writer.startElement("html", component);

        // added by RobJ for Facelets compatibility
        writer.writeAttribute("xmlns", "http://www.w3.org/1999/xhtml", null);

        writer.startElement("head", component);

        UIComponent head = component.getFacet("head");
        if (null != head) {
            head.encodeAll(context);
        }

        Object title = attributes.get("title");
        if (null != title) {
            writer.startElement("title", component);
            writer.writeText(title, "title");
            writer.endElement("title");
        }

        String scriptsBase = getScriptBase(context);
        writeParameters(context);

        writer.endElement("head");
        writer.startElement("body", component);

        // Used GWT modules
        Set modules = findGwtModules(context, component);
        for (Iterator iter = modules.iterator(); iter.hasNext();) {
            String moduleName = (String) iter.next();
            writeModule(context, scriptsBase, moduleName);
        }

        // Navigation history iframe
        writeHistoryFrame(context);

    }

    /**
     * Write the GWT history frame.  Expects to be the first element in the body.
     * @throws IOException
     */
    public static void writeHistoryFrame(FacesContext context) throws IOException {
        String renderedFlag = RENDERED_FLAG_BASE+"history";
        Map requestMap = context.getExternalContext().getRequestMap();
        Object rendered = requestMap.get(renderedFlag);
        if (rendered == null) {
            requestMap.put(renderedFlag, Boolean.TRUE);
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("iframe", null);
            writer.writeAttribute("id", "__gwt_historyFrame", null);
            writer.writeAttribute("style", "width:0;height:0;border:0", null);
            writer.endElement("iframe");
        }
    }

    /*
      * (non-Javadoc)
      *
      * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext,
      *      javax.faces.component.UIComponent)
      */
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("body");
        writer.endElement("html");
    }

    private Set findGwtModules(FacesContext context, UIComponent component) {
        Set found = new HashSet();
        if (component instanceof GwtComponent) {
            found.add(((GwtComponent) component).getModuleName());
        }
        for (Iterator iter = component.getFacetsAndChildren(); iter.hasNext();) {
            UIComponent child = (UIComponent) iter.next();
            found.addAll(findGwtModules(context, child));
        }
        return found;
    }

    /**
     * Write all necessary GWT properties and parameters as meta tags.
     * @param context
     * @throws IOException
     */
    public static void writeParameters(FacesContext context) throws IOException{
        String renderedFlag = RENDERED_FLAG_BASE+"script";
        Map requestMap = context.getExternalContext().getRequestMap();
        Object rendered = requestMap.get(renderedFlag);
        if (rendered == null) {
            requestMap.put(renderedFlag, Boolean.TRUE);
            ResponseWriter writer = context.getResponseWriter();
            String viewId = context.getViewRoot().getViewId();
            writeGwtProperty(writer, "viewid", viewId);
            Locale locale = context.getViewRoot().getLocale();
            writeGwtProperty(writer, "locale", locale.toString());
            String action = context.getApplication().getViewHandler().getActionURL(
                    context, viewId);
            action = context.getExternalContext().encodeActionURL(action);
            writeGwtProperty(writer, "action", action);
            String scriptsBase = getScriptBase(context);
            writeGwtProperty(writer, "base", scriptsBase);
        }
    }

    /**
     * Write the module script tag.  This will load the "selection script" (GWT developer
     * terminology) for the module.  The path in the script element will be something like
     * /webappname/faces/gwt/modulename/modulename.nocache.js.
     */
    public static void writeModule(FacesContext context, String base,String name) throws IOException{
        String renderedFlag = RENDERED_FLAG_BASE+name;
        Map requestMap = context.getExternalContext().getRequestMap();
        Object rendered = requestMap.get(renderedFlag);
        if (rendered == null) {
            requestMap.put(renderedFlag, Boolean.TRUE);
            ResponseWriter writer = context.getResponseWriter();

            String scriptsBase = getScriptBase(context);

            // Marker script BEFORE the actual script, to work around weird Firefox issue with
            // adding it from the selection script computeScriptBase function.
            // Marker script looks like:  <script id="__gwt_marker_demo.gwt.HelloWidget"></script>
            writer.startElement("script", null);
            writer.writeAttribute("language", "javascript", null);
            writer.writeAttribute("id", "__gwt_js_marker_" + name, null);
            writer.endElement("script");

            // Write the selection script
            writer.startElement("script", null);
            writer.writeAttribute("language", "javascript", null);
            writer.writeAttribute("src", scriptsBase + name + "/" + name + ".nocache.js", null);
            writer.endElement("script");


        }
    }

    /**
     * Get the base URL for scripts.  This will in general be something like "/webappname/faces/gwt".
     * @param context
     */
    public static String getScriptBase(FacesContext context) {
        String resourcesPrefix = context.getExternalContext().getInitParameter(FACES_SERVLET_PREFIX_PARAM);
        if( null == resourcesPrefix){
            resourcesPrefix = "/faces/";
        }
        String scriptsBase = context.getApplication().getViewHandler().getResourceURL(context,resourcesPrefix+GwtPhaseListener.GWT_RESOURCE_PREFIX);
        return scriptsBase;
    }

    /**
     * Write a GWT meta property.  Meta properties are meta elements with name="gwt:property" and
     * content="name=value".
     */
    private static void writeGwtProperty(ResponseWriter writer, String name, String value) throws IOException{
        writeMeta(writer, "gwt:property", name+"="+value);
    }

    /**
     * Write a meta element directly to the response.
     */
    private static void writeMeta(ResponseWriter writer, String name, String content) throws IOException{
        writer.startElement("meta", null);
        writer.writeAttribute("name", name, null);
        writer.writeAttribute("content", content, null);
        writer.endElement("meta");

    }
}
