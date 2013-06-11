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

package org.richfaces.skin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.el.ValueExpression;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Singleton ( in respect as collection of different skins ) for produce
 * instances properties for all used skins.
 * 
 * @author shura (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:59:41 $
 * 
 */
public abstract class BasicSkinImpl implements Skin {

    public static final String RENDER_KIT_PARAMETER = "render.kit";

    public static final String REQUEST_HASH_CODES_MAP_PARAMETER = "org.ajax4jsf.skin.HASH_CODES_MAP"; 

    private Map<String, Object> skinParams = new HashMap<String, Object>();

    /**
     * Skin can instantiate only by factory method.
     * 
     * @param skinName
     */
    BasicSkinImpl(Map properties) {
	this.skinParams = properties;
    }
    
    protected Map<String, Object> getSkinParams() {
	return skinParams;
    }

    /* (non-Javadoc)
     * @see org.richfaces.skin.Skin#getRenderKitId(javax.faces.context.FacesContext)
     */
    public String getRenderKitId(FacesContext context) {
	return (String) getValueReference(context, resolveSkinParameter(context, RENDER_KIT_PARAMETER));
    }

    /* (non-Javadoc)
     * @see org.richfaces.skin.Skin#getParameter(javax.faces.context.FacesContext, java.lang.String)
     */
    public Object getParameter(FacesContext context, String name) {
	return getValueReference(context, resolveSkinParameter(context, name));
    }

    /* (non-Javadoc)
     * @see org.richfaces.skin.Skin#getParameter(javax.faces.context.FacesContext, java.lang.String, java.lang.String, java.lang.Object)
     */
    public Object getParameter(FacesContext context, String name,  Object defaultValue) {
	Object value = getValueReference(context, resolveSkinParameter(context, name));
	if(null == value){
	    value = defaultValue;
	}
	return value;
    }

    protected Object getLocalParameter(FacesContext context, String name) {
	return getValueReference(context, skinParams.get(name));
    }
    
    protected abstract Object resolveSkinParameter(FacesContext context, String name);

    /**
     * Calculate concrete value for property - if it stored as @see ValueBinding ,
     * return interpreted value.
     * @param context
     * @param property
     * @return
     */
    protected Object getValueReference(FacesContext context, Object property) {
	if (property instanceof ValueExpression) {
	    ValueExpression value = (ValueExpression) property;
	    return value.getValue(context.getELContext());
	}
	return property;
    }


    public String toString() {
	return this.getClass().getSimpleName() + ": " + skinParams.toString();
    }
    
    protected int computeHashCode(FacesContext context) {
	int hash = 0;
	for (Iterator iter = skinParams.keySet().iterator(); iter.hasNext();) {
	    String key = (String) iter.next();
	    Object parameter = getLocalParameter(context, key);
	    hash = 31*hash + key.hashCode();
	    hash = 31*hash + (parameter != null ? parameter.hashCode() : 0);
	}

	return hash;
    }

    /* (non-Javadoc)
     * @see org.richfaces.skin.Skin#hashCode(javax.faces.context.FacesContext)
     */
    public int hashCode(FacesContext context) {
	ExternalContext externalContext = context.getExternalContext();
	Map<String, Object> requestMap = externalContext.getRequestMap();
	ConcurrentMap<Skin, Integer> map;
	synchronized (requestMap) {
	    map = (ConcurrentMap<Skin, Integer>) requestMap.get(REQUEST_HASH_CODES_MAP_PARAMETER);
	    if (map == null) {
		map = new ConcurrentHashMap<Skin, Integer>();
		requestMap.put(REQUEST_HASH_CODES_MAP_PARAMETER, map);
	    }
	}

	Integer requestCode = (Integer) map.get(this);
	if(null == requestCode){
	    requestCode = new Integer(computeHashCode(context));
	    // store hash for this skin as request-skope parameter - not calculate on next calls for same request
	    map.putIfAbsent(this, requestCode);
	}
	return requestCode.intValue();
    }

}
