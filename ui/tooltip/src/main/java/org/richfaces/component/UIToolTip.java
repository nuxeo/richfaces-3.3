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
package org.richfaces.component;

import java.io.IOException;
import java.util.Set;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.renderkit.AjaxChildrenRenderer;
import org.richfaces.renderkit.html.ToolTipRenderer;

public abstract class UIToolTip extends UICommand implements org.ajax4jsf.component.AjaxChildrenEncoder{

	public static final String COMPONENT_TYPE = "org.richfaces.component.ToolTip";

	public static final String CONTENT_FACET_NAME = "defaultContent";

	public abstract String getLayout();

	public abstract void setLayout(String layout);
	
	public abstract String getMode();

	public abstract void setMode(String mode);
	
	public abstract boolean isDisabled();

	public abstract void setDisabled(boolean disabled);

	public abstract String getDirection();

	public abstract void setDirection(String direction);

	public abstract boolean isFollowMouse();

	public abstract void setFollowMouse(boolean followMouse);

	public abstract int getHorizontalOffset();

	public abstract void setHorizontalOffset(int horizontalOffset);

	public abstract int getVerticalOffset();

	public abstract void setVerticalOffset(int verticalOffset);

	public abstract String getStyle();

	public abstract void setStyle(String style);

	public abstract String getStyleClass();

	public abstract void setStyleClass(String styleClass);

	public abstract String getOncomplete();

	public abstract void setOncomplete(String oncomplete);

	public abstract String getOnshow();

	public abstract void setOnshow(String onshow);

	public abstract String getOnhide();

	public abstract void setOnhide(String onhide);

	public abstract String getOnclick();

	public abstract void setOnclick(String onclick);

	public abstract String getOndblclick();

	public abstract void setOndblclick(String ondblclick);

	public abstract String getOnmouseout();

	public abstract void setOnmouseout(String onmouseout);

	public abstract String getOnmousemove();

	public abstract void setOnmousemove(String onmousemove);

	public abstract String getOnmouseover();

	public abstract void setOnmouseover(String onmouseover);
	
	public abstract int getShowDelay();

	public abstract void setShowDelay(int delay);
	
	public abstract int getHideDelay();

	public abstract void setHideDelay(int delay);

	public abstract int getZorder();

	public abstract void setZorder(int delay);
	
	public abstract String getFor();
	
	public abstract void setFor(String _for);
	
	public abstract String getShowEvent();
	
	public abstract void setShowEvent(String showEvent);
	
	public abstract String getHideEvent();
	
	public abstract void setHideEvent(String hideEvent);
	
	public abstract boolean isAttached();

	public abstract void setAttached(boolean attached);

	public String getUsedElementType(){
		return getLayout().equals("block") ? "div" : "span";
	}
	
	public void broadcast(FacesEvent event) throws AbortProcessingException {

		super.broadcast(event);

		if (event instanceof AjaxEvent) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			AjaxContext ajaxContext = AjaxContext.getCurrentInstance(facesContext);
			ajaxContext.getAjaxAreasToRender().add(this.getClientId(facesContext) + "content");
			ajaxContext.addRegionsFromComponent(this);
		}
	}

	
	/**
	 * Instance of default renderer in ajax responses.
	 */
	private static final AjaxChildrenRenderer _childrenRenderer = new AjaxChildrenRenderer() {

		protected Class<? extends UIComponent> getComponentClass() {
			return UIToolTip.class;
		}

	};
	
	protected AjaxChildrenRenderer getChildrenRenderer() {
		return _childrenRenderer;
	}
	
	public void encodeAjaxChild(FacesContext context, String path, Set<String> ids, Set<String> renderedAreas) 
	                throws IOException {
		
		if(ids.contains(this.getClientId(context) + "content")){
			AjaxContext ajaxContext = AjaxContext.getCurrentInstance(context);
			ToolTipRenderer r = (ToolTipRenderer)getRenderer(context);
			r.encodeTooltipText(context, this);
			ajaxContext.getAjaxRenderedAreas().add(this.getClientId(context) + "content");
		} else {
			_childrenRenderer.encodeAjaxChildren(context, this, path, ids, renderedAreas);
		}
			
	}
	
}
