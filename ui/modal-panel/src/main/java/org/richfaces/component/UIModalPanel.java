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

package org.richfaces.component;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIInput;
import javax.faces.el.ValueBinding;

import org.richfaces.json.JSONException;
import org.richfaces.json.JSONMap;

/**
 * JSF component class
 *
 */
public abstract class UIModalPanel extends UIInput {
	
	public static final String COMPONENT_TYPE = "org.richfaces.ModalPanel";
	
	public static final String COMPONENT_FAMILY = "org.richfaces.ModalPanel";
	
	private Map<String, Object> visualOptions;
	
	/**
     * Shadow depth.
     */
    private boolean resizeable = true;
    private boolean resizeableSet = false;

	public abstract int getWidth();
	public abstract int getHeight();

	public abstract void setWidth(int width);
	public abstract void setHeight(int height);

	public abstract int getMinWidth();
	public abstract int getMinHeight();

	public abstract void setMinWidth(int width);
	public abstract void setMinHeight(int height);

	public abstract boolean isMoveable();
	public abstract boolean isAutosized();

	public abstract void setMoveable(boolean moveable);
	public abstract void setAutosized(boolean autosized);

	public abstract String getLeft();
	public abstract String getTop();

	public abstract void setLeft(String left);
	public abstract void setTop(String top);
	
	public abstract int getZindex();
	public abstract void setZindex(int zindex);
	
	public abstract boolean isShowWhenRendered();
	public abstract void setShowWhenRendered(boolean opened);
	
	public abstract boolean isKeepVisualState();
	public abstract void setKeepVisualState(boolean keepVisualState);
	
	public abstract String getTridentIVEngineSelectBehavior();
	public abstract void setTridentIVEngineSelectBehavior(String tridentIVEngineSelectBehavior);

	public abstract boolean isTrimOverlayedElements();
	public abstract void setTrimOverlayedElements(boolean trim);
	
	public abstract String getDomElementAttachment();
	public abstract void setDomElementAttachment(String domElementAttachment);
	
	public abstract boolean isOverlapEmbedObjects();
	public abstract void setOverlapEmbedObjects(boolean overlapEmbedObjects);
	
	public boolean getRendersChildren() {
		return true;
	}
	
	public String getShadowStyle() {
		String shadow = (String) getAttributes().get("shadowDepth");
		String shadowStyle ="";
	        if (shadow != null) {
	            shadowStyle = "top: " + shadow + "px; left: " + shadow + "px;";
	        }
        
	        String opacity = (String) getAttributes().get("shadowOpacity");
	        String filterOpacity;

	        if (opacity != null) {
        	        try {
        	            Double op = Double.valueOf(opacity);
        	            filterOpacity = Integer.toString(op.intValue() * 10);
        	            opacity = Double.toString(op.doubleValue() / 10);
        	        } catch (Exception e) {
        	            // illegal opacity
        	            return "";
        	        }
        	        shadowStyle += " opacity:" + opacity
        	                + "; filter:alpha(opacity=" + filterOpacity + ");";
	        }
	        return shadowStyle;
	}
	
	public Object getVisualOptions() {
		if (null != this.visualOptions) {
			return this.visualOptions;
		}
		
		ValueExpression ve = getValueExpression("visualOptions");
		if (null != ve) {
		    try {
		    	Object value = ve.getValue(getFacesContext().getELContext());
		    	this.visualOptions = prepareVisualOptions(value);
		    	return this.visualOptions;
		    } catch (ELException e) {
				throw new FacesException(e);
		    }
		}
		
		if (null == this.visualOptions) {
			this.visualOptions = new HashMap<String, Object>();
		}
		return this.visualOptions;
	}
	public void setVisualOptions(Object visualOptions) {
		this.visualOptions = prepareVisualOptions(visualOptions);
	}
	
	 /**
	 * if "true" there is possibility to change component size
	 * Setter for resizeable
	 * @param resizeable - new value
	 */
	public void setResizeable(boolean __resizeable) {
		this.resizeable = __resizeable;
		this.resizeableSet = true;
	}

	/**
	 * if "true" there is possibility to change component size
	 * Getter for resizeable
	 * @return resizeable value from local variable or value bindings
	 */
	public boolean isResizeable() {
		if (this.resizeableSet) {
			return this.resizeable;
		} 
		ValueBinding vb = getValueBinding("resizeable");
		if (vb != null) {
			Boolean value = (Boolean) vb.getValue(getFacesContext());
			if (null == value) {
				return !isAutosized();
			}
			return (value.booleanValue());
		} else {
			return !isAutosized();
		}
	}
	
	protected Map<String, Object> prepareVisualOptions(Object value) {
		if (null == value) {
			return new HashMap<String, Object>();
    	} else if (value instanceof Map) {
			return (Map<String, Object>) value;
		} else if (value instanceof String) {
			String s = (String) value;
			if (!s.startsWith("{")) {
				s = "{" + s + "}";
			}
			try {
				return new HashMap<String, Object>(new JSONMap(s));
			} catch (JSONException e) {
				throw new FacesException(e);
			}
		} else {
			throw new FacesException("Attribute visualOptions of component [" + 
					this.getClientId(getFacesContext()) + "] must be instance of Map or String, but its type is " +
					value.getClass().getSimpleName());
		}
	}
}
