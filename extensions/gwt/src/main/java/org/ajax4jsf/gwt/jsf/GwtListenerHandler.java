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

import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.ajax4jsf.gwt.client.GwtFacesResult;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagAttributeException;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagException;
import com.sun.facelets.tag.TagHandler;

/**
 * Parses the attributes of a gwt:gwtListener tag, and creates the appropriate type of helper components
 * in the JSF component tree.
 * <p/>
 * There are several attributes that can be used with this tag:
 * <ul>
 * <li>binding - this is an EL value expression that specifies an object implementing GwtListener;
 * this object will be invoked to handle the request</li>
 * <li>method - this is an EL method expression that specifies an object and method that take a
 * single GwtFacesEvent argument; the GWT class must use GwtFacesService to route an event to this
 * 'event handler' method</li>
 * <li>event - the specific class of GwtFacesEvent to be passed to the 'event handler' method;
 * this attribute must be used with the method attribute</li>
 * <li>serviceBean - this is an EL value expression that resolves to an object that implements a
 * GWT service interface; the incoming RPC will be delegated to this object (if it is an RPC on that
 * object's interface)</li>
 * <li>type - if no other attributes are defined, this is the class name of a GwtListener class that
 * will be instantiated (with its no-argument constructor) and used to handle the RPC</li>
 * </ul>
 * Note that the binding, method, and serviceBean attributes are exclusive; each gwtListener tag must
 * have only one of those attributes.  Multiple gwtListener tags may be used to define multiple
 * listeners.
 *
 * @author shura
 * @author Rob Jellinghaus
 */
public class GwtListenerHandler extends TagHandler {

    private Class listenerType;
    
    private Class[] signature = GwtActionsRule.GWT_LISTENER_SIG;

    /**
     * A string literal specifying the type of listener to construct (via no-argument constructor)
     * if no other attributes are defined on this tag.
     */
    private final TagAttribute type;

    /**
     * An EL value expression that yields a GwtListener instance which should process the request.
     */
    private final TagAttribute binding;

    /**
     * An EL method expression which yields a method that should be passed an instance of
     * GwtFacesEvent.
     */
    private final TagAttribute method;

    /**
     * The specific subclass of GwtFacesEvent that should be passed to a declared method.
     */
    private final TagAttribute event;

    /**
     * An EL value expression that yields an object which implements a service interface to be
     * invoked directly by RPC.
     */
    private final TagAttribute serviceBean;

    private Class eventType;

    /**
     * Construct a GwtListenerHandler from the given tag.
     */
    public GwtListenerHandler(TagConfig config) {
		super(config);
        this.binding = this.getAttribute("binding");
        this.method = this.getAttribute("method");
        this.event = this.getAttribute("event");
        this.type = this.getAttribute("type");
        this.serviceBean = this.getAttribute("serviceBean");
        if (type != null) {
            if (!type.isLiteral()) {
                throw new TagAttributeException(this.tag, this.type, "Must be literal");
            }
            try {
                this.listenerType = Class.forName(type.getValue());
            } catch (Exception e) {
                throw new TagAttributeException(this.tag, this.type, e);
            }
        }
        if (event != null) {
            if (!event.isLiteral()) {
                throw new TagAttributeException(this.tag, this.event, "Must be literal");
            }
            try {
                this.eventType = Class.forName(event.getValue());
            	this.signature = new Class[]{this.eventType};
            } catch (Exception e) {
                throw new TagAttributeException(this.tag, this.event, e);
            }
        }
        int numAttrs = (binding == null ? 0 : 1) +
                (method == null ? 0 : 1) +
                (serviceBean == null ? 0 : 1);
        if (numAttrs > 1) {
            throw new TagException(this.tag,
                    "Must have only one of 'binding', 'method', 'serviceBean' attributes on single gwtListener; " +
                            "use multiple gwtListeners instead");
        }
	}

	/**
	 * @see com.sun.facelets.FaceletHandler#apply(com.sun.facelets.FaceletContext, javax.faces.component.UIComponent)
	 */
	public void apply(FaceletContext ctx, UIComponent parent)
			throws IOException, FacesException, FaceletException, ELException {
        if (parent instanceof GwtSource) {
            // only process if parent was just created
            if (parent.getParent() == null) {
                GwtSource src = (GwtSource) parent;
                GwtListener listener = null;
                ValueExpression ve = null;
                if (this.binding != null) {
                    ve = this.binding.getValueExpression(ctx,
                            GwtListener.class);
                    listener = new GwtListenerHelper(ve);
                } else if (this.method != null) {
					MethodExpression me = this.method.getMethodExpression(ctx,GwtFacesResult.class,signature);
                    listener = new GwtListenerMethodHelper(me,eventType);
				} else if (this.serviceBean != null) {
                    // could be any kind of object... we're not particular... yet
                    ve = this.serviceBean.getValueExpression(ctx, Object.class);
                    listener = new GwtListenerServiceHelper(ve);
                }
                if (listener == null) {
                    try {
                        listener = (GwtListener) listenerType.newInstance();
                    } catch (Exception e) {
                        throw new TagAttributeException(this.tag, this.type, e.getCause());
                    }
                    if (ve != null) {
                        ve.setValue(ctx, ve);
                    }
                }
                src.addGwtListener(listener);
            }
        } else {
            throw new TagException(this.tag,
                    "Parent is not of type GwtSource, type is: " + parent);
        }
	}

}
