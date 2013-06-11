/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces.renderkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.javascript.JSEncoder;
import org.richfaces.component.UIDragIndicator;
import org.richfaces.component.nsutils.NSUtils;
//import org.richfaces.org.apache.commons.lang.StringEscapeUtils;

/**
 * Base renderer class for drag indicator ( marker ).
 * @author shura
 *
 */
public class DragIndicatorRendererBase extends TemplateEncoderRendererBase {
	public static final String ACCEPT_CLASS = "acceptClass";
	public static final String REJECT_CLASS = "rejectClass";

	public static final String ACCEPT = "accept";
	public static final String DEFAULT = "default";
	public static final String REJECT = "reject";

	protected static final Collection MARKERS_PREDEFINED = new ArrayList(3);
	static {
		MARKERS_PREDEFINED.add(ACCEPT);
		MARKERS_PREDEFINED.add(DEFAULT);
		MARKERS_PREDEFINED.add(REJECT);
	}
	
	public static final String SINGLE = "single";
	public static final String MULTI = "multi";

	protected static final Collection FACETS_PREDEFINED = new ArrayList(2);
	static {
		FACETS_PREDEFINED.add(SINGLE);
		FACETS_PREDEFINED.add(MULTI);
	}

	public DragIndicatorRendererBase() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.framework.renderer.RendererBase#getComponentClass()
	 */
	protected Class getComponentClass() {
		return UIDragIndicator.class;
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
	}

//	public String encodeChild(FacesContext context, Object object) throws IOException {
//		ResponseWriter responseWriter = context.getResponseWriter();
//		StringWriter dumpWriter = new StringWriter();
//
//		try {
//			UIComponent component = (UIComponent) object;
//
//			context.setResponseWriter(responseWriter.cloneWithWriter(dumpWriter));
//
//			renderChild(context, component);
//
//			context.getResponseWriter().flush();
//		} finally {
//			context.setResponseWriter(responseWriter);
//		}
//
//		return TemplateUtil.replaceParams(dumpWriter.getBuffer().toString(), "jsParams");
//	}

	protected boolean notHasFacet(UIComponent component, Object facetName) {
		return component.getFacet((String) facetName) == null;
	}

	protected String getPredefinedMarker(FacesContext context, Object facetName) {
		if (MARKERS_PREDEFINED.contains(facetName)) {
			StringBuffer buff = new StringBuffer();
			JSEncoder encoder = new JSEncoder();
			String source = "<img alt=\"\" border=\"0\" width=\"16\" height=\"16\" src=\""  + 
				getResource("/org/richfaces/renderkit/html/images/" + facetName + ".gif").getUri(context, null) +"\" />";
            char chars[] = source.toString().toCharArray();
            for (int i = 0; i < chars.length; i++) {
                    char c = chars[i];
                    if (!encoder.compile(c)) {
                            buff.append(encoder.encode(c));
                    } else {
                            buff.append(c);
                    }
            }
			return buff.toString();
		}

		throw new IllegalArgumentException("No predefined marker with [" + facetName + "] name exists!");
	}

	public void encodeChildScripts(FacesContext context, UIComponent component)
	throws IOException {
		ResponseWriter responseWriter = context.getResponseWriter();

		for (Iterator iterator = MARKERS_PREDEFINED.iterator(); iterator.hasNext();) {
			String markerName = (String) iterator.next();
			
			responseWriter.write("elt.markers['" + markerName + "'] = \"");
			responseWriter.write(getPredefinedMarker(context, markerName));
			responseWriter.write("\";\n");
		} 
		
		for (Iterator iterator = FACETS_PREDEFINED.iterator(); iterator.hasNext();) {
			String facetName = (String) iterator.next();

			UIComponent facet = component.getFacet(facetName);

			responseWriter.write("elt.indicatorTemplates['" + facetName + "'] = ");

			if (facet != null && facet.isRendered()) {
				writeScriptBody(context, facet, false);
				responseWriter.write(";\n");
			} else {
				responseWriter.write(" DefaultDragIndicatorView;");
			}
		}
	}
	
	public void encodeNamespace(FacesContext context, UIDragIndicator component) throws IOException {
		NSUtils.writeNameSpace(context, component);
	}

	public void encodeDnDParams(FacesContext context, UIDragIndicator component) throws IOException {
		DnDParametersEncoder.getInstance().doEncode(context, component);
	}
}
