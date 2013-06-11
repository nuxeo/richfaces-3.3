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

import org.ajax4jsf.component.AjaxActionComponent;
import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.event.AjaxSource;
import org.ajax4jsf.renderkit.AjaxRendererUtils;

import javax.faces.component.ActionSource;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;


/**
 * JSF component class
 */
public abstract class UISimpleTogglePanel2 extends AjaxActionComponent implements AjaxComponent, AjaxSource, ActionSource
//public abstract class UISimpleTogglePanel extends  UIInput implements AjaxComponent, AjaxSource, ActionSource
{

    public static final String COMPONENT_FAMILY = "javax.faces.Command";
    public static final String SERVER_SWITCH_TYPE = "server";
    public static final String CLIENT_SWITCH_TYPE = "client";
    public static final String AJAX_SWITCH_TYPE = "ajax";
    public static final boolean COLLAPSED = false;
    public static final boolean EXPANDED = true;
    
    //xxxx by nick - why properties here? just describe them in config and CDK generates
    //valid fields & save/restore code
    //for "public" (.tld etc.) properties you should create abstract getters/setters only

    //xxxx by nick - according to JavaDocs http://webdownload.exadel.com/downloads/ajax4jsf/documentation/javaAPI/org/ajax4jsf/framework/ajax/AjaxActionComponent.html
    //AjaxActionComponent already has immediate property


    public abstract void setSwitchType(String switchType);

    public abstract String getSwitchType();

    public abstract void setOpened(boolean opened);

    public abstract boolean isOpened();

    public boolean getRendersChildren() {
        return true;
    }

    //public void broadcast(FacesEvent facesEvent) throws AbortProcessingException {
    //   super.broadcast(facesEvent);
    //    FacesContext facesContext = FacesContext.getCurrentInstance();
    //    if (AjaxRendererUtils.isAjaxRequest(facesContext) && this.getSwitchType().equals(AJAX_SWITCH_TYPE)) {
    //        AjaxRendererUtils.addRegionByName(facesContext,
    //                this,
    //                this.getId());
    //    }
    //}
}
