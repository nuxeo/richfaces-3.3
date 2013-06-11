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

package org.richfaces.renderkit.html;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.component.UIToolBarGroup;

public class ToolBarGroupRenderer extends ToolBarRendererBase {
    
    protected Class<? extends UIComponent> getComponentClass() {
        return UIToolBarGroup.class;
    }
    
    public boolean getRendersChildren() {
        return true;
    }
    
    public void encodeChildren(FacesContext facesContext, UIComponent component)
            throws IOException {
        
        UIToolBarGroup toolBarGroup = (UIToolBarGroup) component;
        List<UIComponent> renderedChildren = toolBarGroup.getRenderedChildren();
        if (renderedChildren.size() <= 0) {
            return;
        }

        ResponseWriter writer = facesContext.getResponseWriter();
        
        
        renderChild(facesContext, toolBarGroup, writer, renderedChildren.get(0));
        for (int i = 1; i < renderedChildren.size(); i++) {
            
            insertSeparatorIfNeed(facesContext, toolBarGroup, writer);

            renderChild(facesContext, toolBarGroup, writer, renderedChildren.get(i));
        }
    }

    private void renderChild(FacesContext facesContext,
            UIToolBarGroup toolBarGroup, ResponseWriter writer,
            UIComponent child) throws IOException {
        
        writer.startElement(HTML.td_ELEM, toolBarGroup);
        writeClassValue(toolBarGroup, writer);
        writeStyleValue(toolBarGroup, writer);
        encodeEventsAttributes(facesContext, toolBarGroup, writer);

        super.renderChild(facesContext, child);
        
        writer.endElement(HTML.td_ELEM);
    }

    private void writeStyleValue(UIToolBarGroup toolBarGroup, ResponseWriter writer) throws IOException {
        String style = getStringAttribute(toolBarGroup, HTML.style_ATTRIBUTE);
        String contentStyle = getStringAttribute(getParentToolBar(toolBarGroup), "contentStyle");
        
        String value = contentStyle + ";" + style;
        getUtils().writeAttribute(writer, HTML.style_ATTRIBUTE, value);
    }

    private void writeClassValue(UIToolBarGroup toolBarGroup, ResponseWriter writer) throws IOException {
        String styleClass = getStringAttribute(toolBarGroup, HTML.STYLE_CLASS_ATTR);
        String contentClass = getStringAttribute(getParentToolBar(toolBarGroup), "contentClass");

        String value = "dr-toolbar-int rich-toolbar-item " + contentClass + " " + styleClass;
        writer.writeAttribute(HTML.class_ATTRIBUTE, value, null);
    }

    private String getStringAttribute(UIComponent toolBarGroup, String attribute) {
        String value = (String) toolBarGroup.getAttributes().get(attribute);
        return null == value ? "" : value;
    }
}
