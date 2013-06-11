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

import java.util.Iterator;
import org.ajax4jsf.Messages;
import org.ajax4jsf.component.EventValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;
import org.ajax4jsf.component.AjaxSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ajax4jsf.renderkit.RendererUtils;


/**
 * JSF component class
 *
 */
public abstract class UIEffect extends UIComponentBase implements AjaxSupport {
	
	public static final String COMPONENT_TYPE = "org.richfaces.Effect";
	
	private static final String COMPONENT_FAMILY = "org.richfaces.Effect";

       	private static final Log log = LogFactory.getLog(UIEffect.class);

    /**
     * @return JavaScript eventString. Rebuild on every call, since
     * can be in loop ( as in dataTable ) with different parameters.
     */
    public String getEventString()
    {
        StringBuffer buildOnEvent = new StringBuffer();

	String targetId=getTargetId();
	String targetPart = "{}";
        UIComponent targetComponent=null;

 	if (!"".equals(targetId)) {
		targetComponent=RendererUtils.getInstance().findComponentFor(this,targetId);
	}
        if (targetComponent!=null) {
	       targetPart = "{targetId:'"+targetComponent.getClientId(FacesContext.getCurrentInstance())+"'}";
	} else {
		// it might be html tag id or DOM object
	        targetPart = targetId != ""
		? "typeof "+targetId+"=='object'?{targetId:"+targetId+"}:{targetId:$('"+targetId+"')}" : "{}" ;
	}

	String type=getType();
	String typePart = type!=""?"{type:'"+type+"'}":"{}";

	String params=getParams();
	String paramsPart = params!=""?"{"+params+"}":"{}";

	 
        buildOnEvent.append("Richfaces.processEffect(Object.extend(Object.extend({targetId:this},"+targetPart+"),"+
        "Object.extend("+typePart+","+paramsPart+") ) )");
       
	return buildOnEvent.toString();

//if (typeof '' == 'object') {pm.attachId= '';if (''=='') pm.targetId=''; };

         

    }

    public abstract String getEvent();
    public abstract void setEvent(String event);

    public abstract String getFor();
    public abstract void setFor(String value);


    public abstract String getType();
    public abstract void setType(String value);

    public abstract String getTargetId();
    public abstract void setTargetId(String value);

    public abstract String getParams();
    public abstract void setParams(String value);
    /**
     * After nornal setting <code>parent</code> property in case of
     * created component set Ajax properties for parent.
     * @see javax.faces.component.UIComponentBase#setParent(javax.faces.component.UIComponent)
     */
    public void setParent(UIComponent parent)
    {
        super.setParent(parent);
        if (null != parent && parent.getFamily() != null ) {
			if (log.isDebugEnabled()) {
				log.debug(Messages.getMessage(Messages.CALLED_SET_PARENT, parent.getClass().getName()));
			}
			// TODO If this comopnent configured, set properties for parent component.
			// NEW created component have parent, restored view - null in My faces.
			// and SUN RI not call at restore saved view.
			// In other case - set in restoreState method.
			//        if (parent.getParent() != null)
			{
				if (log.isDebugEnabled()) {
					log.debug(Messages.getMessage(Messages.DETECT_NEW_COMPONENT));
				}
				setParentProperties(parent);

			}
		}
    }


   public void setParentProperties(UIComponent parent){
   	if (getFor() == "" && getEvent() !="" ) {
	   	parent.setValueBinding(getEvent(), new EventValueBinding(this));
	}
   }
			   
	
}
