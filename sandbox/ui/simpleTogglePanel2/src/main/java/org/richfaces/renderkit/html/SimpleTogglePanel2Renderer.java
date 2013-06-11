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

/*
 * Created on 04.07.2006
 */
package org.richfaces.renderkit.html;

import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.richfaces.component.UISimpleTogglePanel2;
import org.richfaces.event.SimpleToggle2Event;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import java.io.IOException;
import java.util.Map;

//public class SimpleTogglePanelRenderer extends AjaxCommandLinkRenderer {

public class SimpleTogglePanel2Renderer extends org.ajax4jsf.renderkit.HeaderResourcesRendererBase {
    //XXXX by nick - denis - seems there is a lot of code common to org.richfaces.renderkit.html.ToggleControlRenderer. Please commonize!
    //private InternetResource[] _scripts = {new PrototypeScript(), getResource("scripts/simpleTogglePanel.js") };

    protected Class getComponentClass() {
        return UISimpleTogglePanel2.class;
    }

    //XXXX by nick - denis - move scripts to template
    //protected InternetResource[] getAdditionalScripts() {
    //	return _scripts;
    //}


    public boolean getRendersChildren() {
        return true;
    }

    public void doDecode(FacesContext context, UIComponent component) {

        super.doDecode(context, component);


        ExternalContext exCtx = context.getExternalContext();

        Map rqMap = exCtx.getRequestParameterMap();
        Object clnId = rqMap.get(component.getClientId(context));
        UISimpleTogglePanel2 panel = (UISimpleTogglePanel2) component;

        if (clnId != null) {
            // enqueue event here for this component or for component with Id
            // taken fro forId attribute
            
            String switchType = panel.getSwitchType();
            if (!(UISimpleTogglePanel2.CLIENT_SWITCH_TYPE.equals(switchType))) {

                //xxxx by nick - denis - use constants, please!
                if ((panel.isOpened() == UISimpleTogglePanel2.EXPANDED)) {
                    panel.setOpened(UISimpleTogglePanel2.COLLAPSED);
                } else {
                    //xxxx by nick - denis - use constants, please!
                    panel.setOpened(UISimpleTogglePanel2.EXPANDED);
                }
                SimpleToggle2Event event = new SimpleToggle2Event(panel, (panel.isOpened()));
                if (panel.isImmediate()) {
                    event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
                } else {
                    event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                }
                event.queue();
                
            } else {
                if (panel.isOpened()!= new Boolean((String) clnId).booleanValue()){
                    SimpleToggle2Event event = new SimpleToggle2Event(panel, (panel.isOpened()));
                    if (panel.isImmediate()) {
                        event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
                    } else {
                        event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                    }
                    event.queue();
                }                
                panel.setOpened(new Boolean((String) clnId).booleanValue());
            }
            


        }
        
        
         if (AjaxRendererUtils.isAjaxRequest(context) && panel.getSwitchType().equals(UISimpleTogglePanel2.AJAX_SWITCH_TYPE)) {
                AjaxRendererUtils.addRegionByName(context,
                        panel,
                        panel.getId());
         }


    }

    public String getdivdisplay(FacesContext context, UIComponent component) {

        String Switch = Boolean.toString(((UISimpleTogglePanel2) component).isOpened());
        if (Switch == null || Switch.equals(Boolean.toString(UISimpleTogglePanel2.EXPANDED)))
        {
            //xxxx by nick - denis - do not set "block" explicitly - that can break some elements, set "" for display. See Element.show() in prototype.js
            return "";
        }
        return "none";
    }

    public String getOnClick(FacesContext context, UIComponent component) {
        UISimpleTogglePanel2 tgComp = (UISimpleTogglePanel2) component;

        String switchType = tgComp.getSwitchType();
        StringBuffer onClick = new StringBuffer();
        //String userOnClick = (String)component.getAttributes().get("onclick");
        //if(userOnClick!=null) {
        //	onClick.append(userOnClick);
        //	if(!userOnClick.trim().endsWith(";")) {
        //		onClick.append("; ");
        //	}
        //}


        if (UISimpleTogglePanel2.CLIENT_SWITCH_TYPE.equals(switchType)) {
            // Client
            String panelId = tgComp.getClientId(context);
            onClick.append("SimpleTogglePanelManager.toggleOnClient('")
                    .append(panelId)
                    .append("');");
        } else if (UISimpleTogglePanel2.AJAX_SWITCH_TYPE.equals(switchType)) {
            // Ajax
            // writer.writeAttribute(HTML.onclick_ATTRIBUTE,AjaxRendererUtils.buildOnClick(tab,context),"ajaxOnclick");
            onClick.append(AjaxRendererUtils.buildOnClick(component, context));
            //return super.getOnClick(context, component);
        } else {
            // Server


            onClick.append("SimpleTogglePanelManager.toggleOnServer('")
                    .append(component.getClientId(context))
                    .append("'")


                    .append(");");
            //.append(tgComp.getSwitch()==null?"'0'":"'" + tgComp.getSwitch() + "'")
            //.append("")
        }
        return onClick.toString();
    }

    protected String getValueAsString(FacesContext context, UISimpleTogglePanel2 Panel) {
        return getUtils().getValueAsString(context, Panel);
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        // TODO Auto-generated method stub
        UISimpleTogglePanel2 comp = (UISimpleTogglePanel2) component;
        //xxxx by nick - denis - use constants, please!
        if (!(((comp.getSwitchType() == null) || (comp.getSwitchType().equals(UISimpleTogglePanel2.CLIENT_SWITCH_TYPE) != true)) && (comp.isOpened() == UISimpleTogglePanel2.COLLAPSED)))
        {
            super.encodeChildren(context, component);
        }
    }
}