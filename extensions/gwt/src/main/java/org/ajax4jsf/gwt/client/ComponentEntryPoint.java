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
package org.ajax4jsf.gwt.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Base class for G4JSF components.  Provides infrastructure for hooking module loading, integrating
 * with JSF state management, handling widget parameters, et al.
 *
 * @author shura
 * @author Rob Jellinghaus
 */
public abstract class ComponentEntryPoint implements EntryPoint {

	public static final String GWT_MODULE_NAME_PARAMETER = "x-gwtcallingmodule";
	private static String viewState;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		// Init callback script
		String jsFunction = "_create_" + GWT.getModuleName().replace('.', '_');
		initJS(jsFunction);
		List elements = findElementsForClass(RootPanel.getBodyElement(), GWT
				.getModuleName());
		Element viewStateField = DOM.getElementById("javax.faces.ViewState");
		if (null != viewStateField) {
			viewState = DOM.getAttribute(viewStateField, "value");
		}
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			Element element = (Element) iter.next();
			String id = DOM.getAttribute(element, "id");
			// got viev state parameter
			Widget widget = createWidget(id);
			// RootPanel clear it content - widget can need use it.
			RootPanel panel = RootPanel.get(id);
			panel.add(widget);
		}
	}

	/**
	 * Get map of widget parameters. Default data will be stored in hidden span
	 * element with id as id+":_data" by child elements - title attribute is
	 * param name, enclosing text - value.
	 *
	 * @param id -
	 *            clientId of JSF component.
	 * @return map of widget parameters.
	 */
	protected Map getWidgetParams(String id) {
		// Parse parameters.
		String dataId = id + ":_data";
		Element dataElement = DOM.getElementById(dataId);
		Map params = new HashMap();
		if (null != dataElement) {
			int childCount = DOM.getChildCount(dataElement);
			fillParamsMap(dataElement, params, childCount);
		}
		return params;
	}

	/**
	 * Fill parameters map by data from given element. If data element don't have child elements,
	 * got content as text, else - put new map with content of recursive same method.
	 * @param dataElement
	 * @param params
	 * @param childCount
	 */
	private void fillParamsMap(Element dataElement, Map params, int childCount) {
		for (int it = 0; it < childCount; it++) {
			Element data = DOM.getChild(dataElement, it);
			String key = DOM.getAttribute(data, "title");
			if (null != key) {
				int dataChildCount = DOM.getChildCount(data);
				if (dataChildCount > 0) {
					// Put data from child elements as new map
					Map innerData = new HashMap(dataChildCount);
					fillParamsMap(data,innerData,dataChildCount);
					params.put(key,innerData);
				} else {
					// Simple text value
					String innerText = DOM.getInnerText(data);
					params.put(key, innerText);
				}
			}
		}
	}

    /**
     * Private cached static map of meta gwt:property values, defined as
     * (meta name="gwt:property" content="propName=value").
     */
    private static Map gwtMetaProperties = null;

    /**
     * Get a named meta property from the current root panel's head element.
     * Statically cached for efficiency.  Whoops, what if there are different meta properties
     * on each page?
     */
    public static String getMetaProperty(String propName) {
        if (gwtMetaProperties == null) {
            gwtMetaProperties = new HashMap();

            Element bodyElement = RootPanel.getBodyElement();
            // go up to the parent
            Element htmlElement = DOM.getParent(bodyElement);
            // iterate over meta children
            Element headElement = DOM.getFirstChild(htmlElement);

            int count = DOM.getChildCount(headElement);
            for (int i = 0; i < count; i++) {
                Element nextChild = DOM.getChild(headElement, i);
                if ("meta".equalsIgnoreCase(getElementTagName(nextChild))) {
                    String nameAttribute = DOM.getAttribute(nextChild, "name");
                    if ("gwt:property".equals(nameAttribute)) {
                        String contentAttribute = DOM.getAttribute(nextChild, "content");
                        if (contentAttribute != null) {
                            int eqPos = contentAttribute.indexOf("=");
                            if (eqPos != -1) {
                                String name = contentAttribute.substring(0, eqPos);
                                gwtMetaProperties.put(name, contentAttribute.substring(eqPos + 1));
                            }
                        }
                    }
                }
            }
        }

        return (String)gwtMetaProperties.get(propName);
    }

    /**
     * Get the tag name of an element.  I can't believe this isn't in the DOM class.
     */
    public static native String getElementTagName (Element element) /*-{
        return element.tagName;
    }-*/;

    // this doesn't work anymore because document is the cached module document, not the gwt.js
    // main document.
        /*-{
	 // made private in module init function....
	 //return __gwt_getMetaProperty(name);

      var metas = document.getElementsByTagName("meta");
      for (var i = 0, n = metas.length; i < n; ++i) {
        var meta = metas[i];
        var name = meta.getAttribute("name");
        if (name) {
          if (name == "gwt:property") {
            var content = meta.getAttribute("content");
            if (content) {
              var name = content, value = "";
              var eq = content.indexOf("=");
              if (eq != -1) {
                name = content.substring(0, eq);
                value = content.substring(eq+1);
              }
              if (propName == name) {
                return value;
              }
            }
          }
        }
      }
      return null;
	 }-*/;

	/**
	 * Returns a List of Element objects that have the specified CSS class name.
	 *
	 * @param element
	 *            Element to start search from
	 * @param className
	 *            name of class to find
	 */
	public static List findElementsForClass(Element element, String className) {
		ArrayList result = new ArrayList();
		recElementsForClass(result, element, className);
		return result;
	}

	/**
	 * Recursive search dom element and it's children for specified CSS class
	 * name. helper method for {@link #findElementsForClass(Element, String)}
	 *
	 * @param res
	 * @param element
	 * @param className
	 */
	private static void recElementsForClass(ArrayList res, Element element,
			String className) {
		if (isHaveClass(element, className)) {
			res.add(element);
		}

		for (int i = 0; i < DOM.getChildCount(element); i++) {
			Element child = DOM.getChild(element, i);
			recElementsForClass(res, child, className);
		}
	}

	/**
	 * Check for dom element is have specified ctyle class.
	 *
	 * @param element
	 * @param className
	 */
	public static boolean isHaveClass(Element element, String className) {
		String c = DOM.getAttribute(element, "className");

		if (c != null) {
			String[] p = c.split(" ");

			for (int x = 0; x < p.length; x++) {
				if (p[x].equals(className)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Append content of given element to widget. Can be used to append content
	 * of rendered facet or children elements to widget.
	 *
	 * @param widget
	 *            target widget
	 * @param content
	 *            element with content for append to widget
	 */
	public static void appendContent(Widget widget, Element content) {
		Element element = widget.getElement();
		int childCount = DOM.getChildCount(content);
		for (int i = 0; i < childCount; i++) {
			Element child = DOM.getChild(content, i);
			DOM.appendChild(element, child);
		}
	}

	/**
	 * Setup service endpoint to same as page action URL with view state
	 * parameter.
	 *
	 * @param service
	 */
	public static void setupServiceEndpoint(ServiceDefTarget service) {
		String endpoint = getEndpoint();
		service.setServiceEntryPoint(endpoint);
	}

	/**
     * Get service endpoint URL.  Include JSF viewState and GWT module name as URL parameters,
     * to facilitate JSF dispatching logic in GwtFacesServlet.
	 */
	public static String getEndpoint() {
        String action = getMetaProperty("action");
        String endpoint = action + "?javax.faces.ViewState="
				+ viewState + "&x-gwtcallingmodule="+GWT.getModuleName();
		return endpoint;
	}

	/**
	 * Factory method for create and setup JSF service to perform RPC call on
	 * component instance
	 *
	 * @param clientId -
	 *            identify target component
	 * @return service stub instance
	 */
	public static GwtFacesServiceAsync createFacesService(String clientId) {
		GwtFacesServiceAsync service = (GwtFacesServiceAsync) GWT
				.create(GwtFacesService.class);
		String endpoint = getEndpoint();
		if (null != clientId) {
			endpoint = endpoint + "&clientId=" + clientId;
		}
		((ServiceDefTarget) service).setServiceEntryPoint(endpoint);
		return service;
	}

	/**
	 * Implementation must owerride this method to create concrete widget.  Note that
     * the id passed will be the specific id of this type of widget, so if multiple
     * widgets are implemented in this module, the createWidget method in the
     * EntryPoint subclass can dispatch on the particular id to instantiate one widget
     * or the other.
	 *
	 * @param id -
	 *            clientId of JSF component.
	 */
	protected abstract Widget createWidget(String id);

	/**
	 * Create widget from Java-script inline in page ( can be used for re-create
	 * widgets in Ajax4jsf .
	 * 
	 * @param id -
	 *            IDREF for base widget element.
	 */
	public final void appendWidget(String id) {
		// got viev state parameter
		Widget widget = createWidget(id);
		// RootPanel clear it content - widget can need use it.
		RootPanel panel = RootPanel.get(id);
		panel.add(widget);
	}

	/**
	 * create javaScript funtion for callback creation of widgets in page.
	 * 
	 * @param moduleName
	 */
	private native void initJS(String moduleName)/*-{
	 $wnd[moduleName] = function(id){
	 this.@org.ajax4jsf.gwt.client.ComponentEntryPoint::appendWidget(Ljava/lang/String;)(id);
	 };
	 }-*/;

	/**
	 * @return Returns the viewState.
	 */
	public static String getViewState() {
		return viewState;
	}

	/**
	 * @param viewState
	 *            The viewState to set.
	 */
	public static void setViewState(String viewState) {
		ComponentEntryPoint.viewState = viewState;
	}
}
