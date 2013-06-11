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
package org.richfaces.renderkit.html;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.component.UIRichMessage;
import org.richfaces.renderkit.RichMessageBaseRenderer;


/**
 * @author Anton Belevich
 *
 */
public class HtmlRichMessageRenderer extends RichMessageBaseRenderer{
	
	
	protected void doEncodeEnd(ResponseWriter writer, FacesContext context,	UIComponent component) throws IOException {
		
		Iterator<FacesMessage> msgIter = null;
		
		UIRichMessage msgComponent = (UIRichMessage)component;
		String forClientId = msgComponent.getFor();
		
		if(forClientId == null){
	
			if(log.isErrorEnabled()){
				log.error("'for' attribute cannot be null");
			}
			
		}else{
			
			msgIter = getMessageIterator(context, forClientId, msgComponent);
			writer.startElement(HTML.SPAN_ELEM, msgComponent);
			getUtils().writeAttribute(writer, HTML.id_ATTRIBUTE, msgComponent.getClientId(context));
			
			if(!msgIter.hasNext() && msgComponent.isPassed()){
				
				encodingUIContent(msgComponent, context, null);
				
			}else if(msgIter.hasNext() ){
				
				FacesMessage message = (FacesMessage)msgIter.next();
				encodingUIContent(msgComponent, context, message);	
			
			}
			
			writer.endElement(HTML.SPAN_ELEM);
		}	
	}
	
	protected void encodingUIContent(UIRichMessage uiMsg, FacesContext context, FacesMessage facesMsg) throws IOException{
					
		ResponseWriter writer = context.getResponseWriter();
		
		List<String> acceptLevels = creatAcceptLevels(uiMsg.getLevel().split(","));
		if(isAcceptableMessage(facesMsg, acceptLevels)){
			outerStyles(uiMsg, context, writer, facesMsg);
			renderMarkerFacet(uiMsg,context, writer,facesMsg);
			renderLabel(uiMsg, context, writer, facesMsg);
		}	
	}
	
	public void renderOuterStyles(UIComponent component, FacesContext context, ResponseWriter writer,String outerStyle, String outerClass) throws IOException{
		
		UIRichMessage uiMsg = null;
		
		if(!(component instanceof UIRichMessage)){
			return;
		}
		
		uiMsg = (UIRichMessage)component;
		
		String parentStyle = (String)uiMsg.getAttributes().get("style"); 
		String parentClass = (String)uiMsg.getAttributes().get("styleClass");
	   
		String richMessageClass = "rich-message" + (null != outerClass ? " " + outerClass : "");
		String richMessageStyle = (null != outerStyle ? outerStyle + "," : "");
		  
		parentStyle = parentStyle != null ? (richMessageStyle + parentStyle) :  richMessageStyle;  
		parentClass = parentClass != null ? (richMessageClass + " " + parentClass) : richMessageClass;    
		   
		getUtils().writeAttribute(writer, HTML.class_ATTRIBUTE, parentClass);
		getUtils().writeAttribute(writer, HTML.style_ATTRIBUTE, parentStyle);
	}
	
	public void renderMarkerHtml(UIComponent component,UIComponent marker, FacesContext context, 
								 ResponseWriter writer,String markerClass, String markerStyle) throws IOException{
		
		UIRichMessage uiMsg = null;
		
		if(!(component instanceof UIRichMessage)){
			return;
		}
		
		uiMsg = (UIRichMessage)component;
		
		writer.startElement(HTML.SPAN_ELEM, uiMsg);
		
		String classes = markerClass == null ? "rich-message-marker" : "rich-message-marker " + markerClass;
		
		getUtils().writeAttribute(writer, HTML.class_ATTRIBUTE, classes);
		getUtils().writeAttribute(writer, HTML.style_ATTRIBUTE, markerStyle);
		
		renderChild(context, marker);
		
		writer.endElement(HTML.SPAN_ELEM);
	
	}
	
	public void renderLabelHtml(UIComponent component, FacesContext context, ResponseWriter writer,
								FacesMessage facesMsg, String labelClass, String labelStyle, String passLabel) throws IOException{
		 	
		String summary = null;
		
		String detail = null; 
		
		String title = null;
		
		UIRichMessage uiMsg = null;
		
		if(!(component instanceof UIRichMessage)){
			return;
		}
		
		uiMsg = (UIRichMessage)component;
		
		boolean isTooltip = getUtils().isBooleanAttribute(uiMsg, "tooltip");
		   
		boolean showSummary = uiMsg.isShowSummary();
			
		boolean showDetail = uiMsg.isShowDetail();
		
		
		if(facesMsg != null){
			summary = (null != (summary = facesMsg.getSummary())) ? summary : "";
			detail = (null != (detail = facesMsg.getDetail())) ? detail : "";
		}	   
		   
		String classes = labelClass == null ? "rich-message-label" : "rich-message-label " + labelClass;
		   
		writer.startElement(HTML.SPAN_ELEM, uiMsg);
		   
		getUtils().writeAttribute(writer, HTML.class_ATTRIBUTE,classes);
		getUtils().writeAttribute(writer, HTML.style_ATTRIBUTE, labelStyle);
		
		title = (String) component.getAttributes().get("title");
		if(title != null) {
			writer.writeAttribute("title", title, "title");
		}
		
		if(facesMsg != null){
			boolean wroteTooltip = false;
			if (showSummary && showDetail && isTooltip) {
				if (title == null || title.length() == 0) {
					writer.writeAttribute("title", summary, "title");
				}
				wroteTooltip = true;
			} 

			if (!wroteTooltip && showSummary) {
				writer.writeText(summary, component, null);
			}
			
			if (showDetail) {
				writer.writeText(detail, component, null);
			}	
		} else if(passLabel != null){
				writer.writeText(passLabel, null);
				writer.writeText("\t", null);
		}
		   
		writer.endElement(HTML.SPAN_ELEM);
		
	}
	
	protected Class<? extends UIComponent> getComponentClass() {

		return UIMessage.class;
	}
}
 
