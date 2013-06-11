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

package org.richfaces.function;

import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils;

/**
 * Created 20.03.2008
 * @author Nick Belaevski
 * @since 3.2
 */

public class RichFunction {

	private static UIComponent findComponent(FacesContext context, String id) {
    	if (id != null) {
    		if (context != null) {
    			UIViewRoot root = context.getViewRoot();

    			if (root != null) {
    				UIComponent component = RendererUtils.getInstance().findComponentFor(root, id);

    				if (component != null) {
    					return component;
    				}
    			}
    		}
    	}

    	return null;
	}
	
    public static String clientId(String id) {
		FacesContext context = FacesContext.getCurrentInstance();
    	UIComponent component = findComponent(context, id);
    	return component != null ? component.getClientId(context) : null;
    }
    
    public static String component(String id) {
    	String element = element(id);
    	if (element != null) {
    		return element + ".component";
    	}

    	return null;
    }

    public static String element(String id) {
    	String clientId = clientId(id);
    	if (clientId != null) {
    		return "document.getElementById('" + clientId + "')";
    	}

    	return null;
    }

    public static UIComponent findComponent(String id) {
    	return findComponent(FacesContext.getCurrentInstance(), id);
    }
    
    /**
     * @since 3.3.1
     * @param rolesObject
     * @return
     */
    public static boolean isUserInRole(Object rolesObject) {
    	Set<String> rolesSet = AjaxRendererUtils.asSet(rolesObject);
    	if (rolesSet != null) {
        	FacesContext facesContext = FacesContext.getCurrentInstance();
        	ExternalContext externalContext = facesContext.getExternalContext();

        	for (String role : rolesSet) {
				if (externalContext.isUserInRole(role)) {
					return true;
				}
			}
    	}
    	
    	return false;
    }
}
