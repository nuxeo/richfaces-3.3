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

import java.lang.reflect.Method;

import javax.el.MethodExpression;

import org.ajax4jsf.gwt.client.GwtFacesEvent;
import org.ajax4jsf.gwt.client.GwtFacesResult;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;

/**
 * @author shura (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:56 $
 *
 */
public class GwtActionsRule extends MetaRule {


    public final static Class[] GWT_LISTENER_SIG = new Class[] { GwtFacesEvent.class };


    public final static class GwtListenerMapper extends Metadata {

        private final TagAttribute attr;
        
        private final String name;

        public GwtListenerMapper(String name, TagAttribute attr) {
            this.attr = attr;
            // convert attribute name to setter
            this.name = "set"+name.substring(0,1).toUpperCase()+name.substring(1);
        }

        public void applyMetadata(FaceletContext ctx, Object instance) {
			MethodExpression methodExpression = this.attr.getMethodExpression(ctx, GwtFacesResult.class,GwtActionsRule.GWT_LISTENER_SIG);
        	if (instance instanceof GwtSource) {
				GwtSource gwtSource = (GwtSource) instance;
				gwtSource.addGwtListener(new GwtListenerMethodHelper(methodExpression,null));
			} else {
        	Class clazz = instance.getClass();
        	try {
        		// Find setter method
				Method setter = clazz.getMethod(name,
						new Class[] { MethodExpression.class });
				// Set gwtActionListener method expression
				setter.invoke(instance, new Object[] { methodExpression });
			} catch (Exception e) {
				// TODO: handle exception
			}
			}
        }

    }

    public final static GwtActionsRule instance = new GwtActionsRule();

    public GwtActionsRule() {
        super();
    }

    public Metadata applyRule(String name, TagAttribute attribute,
            MetadataTarget meta) {
        if (meta.isTargetInstanceOf(GwtComponent.class)) {

            if (name.startsWith("gwt") && name.endsWith("Listener")) {
                    return new GwtListenerMapper(name,attribute);
            }
        }
        return null;
    }

}
