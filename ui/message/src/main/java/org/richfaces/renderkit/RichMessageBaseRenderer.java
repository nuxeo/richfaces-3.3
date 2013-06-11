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
package org.richfaces.renderkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.renderkit.RendererUtils;
import org.ajax4jsf.resource.InternetResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.renderkit.html.HtmlRichMessageRenderer;

/**
 * @author Anton Belevich
 *
 */

public abstract class RichMessageBaseRenderer extends HeaderResourcesRendererBase{
	
	protected static final Log log = LogFactory.getLog(HtmlRichMessageRenderer.class);
	
	public static final String COMPONENT_NOT_FOUND_IN_VIEW_WARN_MESSAGE = "component not found in the view WARNING";
	
	private static final String ERROR_NAME = "ERROR";
	
	private static final String WARN_NAME = "WARN";
	
	private static final String FATAL_NAME = "FATAL";
	
	private static final String INFO_NAME = "INFO";
	
	private static final String ALL_NAME = "ALL";
	
//	protected List acceptLevels = new ArrayList();
	
	
	private final InternetResource[] styles = {getResource("/org/richfaces/renderkit/html/css/msg.css"),getResource("/org/richfaces/renderkit/html/css/msgs.css")};
	
	private InternetResource[] stylesAll = null;
	
	
	protected InternetResource[] getStyles(){
		synchronized (this){
			if(stylesAll == null){
				
				InternetResource[] rsrcs = super.getStyles();
				boolean ignoreSuper = rsrcs == null || rsrcs.length == 0;
				boolean ignoreThis = styles == null || styles.length == 0;
				
				if(ignoreSuper){
					
					if (ignoreThis) {
						stylesAll = new InternetResource[0];	
					} else {
						stylesAll = styles;
					}
					
				}else{
				
					if(ignoreThis){
						stylesAll = rsrcs;
					}else{
						Set<InternetResource> rsrcsSet = new java.util.LinkedHashSet<InternetResource>();
						for(int i = 0; i < rsrcs.length; i++ ){
							rsrcsSet.add(rsrcs[i]);
						}

						for(int i = 0; i < styles.length; i++ ){
							rsrcsSet.add(styles[i]);
						}
						stylesAll = (InternetResource[]) rsrcsSet.toArray(new InternetResource[rsrcsSet.size()]);
					}
				}
			}
		}
		
		return stylesAll;
	}
		
	protected Iterator<FacesMessage> getMessageIterator(FacesContext context, String forClientId, UIComponent component) throws IOException{
		
		Iterator<FacesMessage> msgIter = null;
		
		if(forClientId != null){
			
			if(forClientId.length() != 0){
				
				UIComponent result = RendererUtils.getInstance().
					findComponentFor(component, forClientId);
				if (result == null) {
					msgIter = Collections.<FacesMessage>emptyList().iterator();
				} else {
					msgIter = context.getMessages(result.getClientId(context));
				}
				
			}else{
				msgIter = context.getMessages(null);
			}
			
		}else{
			msgIter = context.getMessages();
		}
				
		return msgIter;
	}
	
	protected void renderMarkerFacet(UIComponent uiMsg, FacesContext context, ResponseWriter writer,FacesMessage facesMsg) throws IOException{

		UIComponent marker = null;
		
		String markerClass = null;
		
		String markerStyle = null;
		
		Severity severity = null;
		
		
		if(facesMsg != null){
			
			severity = facesMsg.getSeverity(); 
			
			if(severity == FacesMessage.SEVERITY_ERROR){
				
				markerClass = (String)uiMsg.getAttributes().get("errorMarkerClass");
				markerStyle = (String)uiMsg.getAttributes().get("errorMarkerStyle");
				marker = uiMsg.getFacet("errorMarker");
					
			}else if (severity == FacesMessage.SEVERITY_FATAL) {
				
				markerClass = (String)uiMsg.getAttributes().get("fatalMarkerClass");
				markerStyle = (String)uiMsg.getAttributes().get("fatalMarkerStyle");
				marker = uiMsg.getFacet("fatalMarker");
				
			}else if (severity == FacesMessage.SEVERITY_INFO) {
				
				markerClass = (String)uiMsg.getAttributes().get("infoMarkerClass");
				markerStyle = (String)uiMsg.getAttributes().get("infoMarkerStyle");
				marker = uiMsg.getFacet("infoMarker");
					
			}else if (severity == FacesMessage.SEVERITY_WARN) {
				
				markerClass = (String)uiMsg.getAttributes().get("warnMarkerClass");
				markerStyle = (String)uiMsg.getAttributes().get("warnMarkerStyle");
				marker = uiMsg.getFacet("warnMarker");
				
			}
			
		}else if(uiMsg.getFacet("passedMarker") != null){
			
			marker = uiMsg.getFacet("passedMarker");
			markerClass = (String) uiMsg.getAttributes().get("markerClass");
			markerStyle = (String) uiMsg.getAttributes().get("markerStyle");
				
		}
		
		if(marker == null){
			return;
		}
		
		if(!marker.isRendered()){
			return;
		}
		
		renderMarkerHtml(uiMsg, marker, context, writer, markerClass, markerStyle);
	}
	
	protected void renderLabel(UIComponent component, FacesContext context, ResponseWriter writer,FacesMessage facesMsg) throws IOException{
		
		String labelClass = null;
		   
		String labelStyle = null;
		   
		String passLabel = null;
		   
		Severity severity = null;
		   
		       
		if(facesMsg != null){
		   
			severity = facesMsg.getSeverity();
			   
			if(severity == FacesMessage.SEVERITY_ERROR){
					
				labelClass = (String)component.getAttributes().get("errorLabelClass");
				labelStyle = (String)component.getAttributes().get("errorLabelStyle");
							
			}else if(severity == FacesMessage.SEVERITY_FATAL){
					
				labelClass = (String)component.getAttributes().get("fatalLabelClass");
				labelStyle = (String)component.getAttributes().get("fatalLabelStyle");
							
			}else if(severity == FacesMessage.SEVERITY_WARN){
					
				labelClass = (String)component.getAttributes().get("warnLabelClass");
				labelStyle = (String)component.getAttributes().get("warnLabelStyle");
							
			}else if(severity == FacesMessage.SEVERITY_INFO){
					
				labelClass = (String)component.getAttributes().get("infoLabelClass");
				labelStyle = (String)component.getAttributes().get("infoLabelStyle");
					
			}
			   
		}else if(component.getAttributes().get("passedLabel") != null){
			
			passLabel = (String) component.getAttributes().get("passedLabel");				
			labelClass = (String) component.getAttributes().get("labelClass");
			labelStyle = (String) component.getAttributes().get("labelStyle");
				
		}

		renderLabelHtml(component, context, writer, facesMsg, labelClass, labelStyle, passLabel);
	}
	
	protected void  outerStyles(UIComponent component, FacesContext context, ResponseWriter writer,FacesMessage facesMsg) throws IOException{
		
		String outerClass = null;
		   
		String outerStyle = null;
		   
		Severity severity = null;
		   
		   
		if(facesMsg != null){
			   
			severity = facesMsg.getSeverity();
			if(severity == FacesMessage.SEVERITY_ERROR){
					
				outerClass = (String)component.getAttributes().get("errorClass");
				outerStyle = (String)component.getAttributes().get("errorStyle");
							
			}else if(severity == FacesMessage.SEVERITY_FATAL){
					
				outerClass = (String)component.getAttributes().get("fatalClass");
				outerStyle = (String)component.getAttributes().get("fatalStyle");
								
			}else if(severity == FacesMessage.SEVERITY_WARN){
					
				outerClass = (String)component.getAttributes().get("warnClass");
				outerStyle = (String)component.getAttributes().get("warnStyle");
							
			}else if(severity == FacesMessage.SEVERITY_INFO){
					
				outerClass = (String)component.getAttributes().get("infoClass");
				outerStyle = (String)component.getAttributes().get("infoStyle");
			}
		}
		
		renderOuterStyles(component, context, writer, outerStyle, outerClass);
		
	}
	
	protected boolean isAcceptableMessagesPresent(List<String> acceptLevels, Iterator<FacesMessage> messagesIter) {
		while(messagesIter.hasNext()){
			FacesMessage message = (messagesIter.next());
			boolean exist = isAcceptableMessage(message, acceptLevels); 
			if (exist) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean isAcceptableMessage(FacesMessage message, List<String> acceptLevels){
		
		boolean accept = false;
		
		if(acceptLevels.contains(ALL_NAME) || message == null){
			return true;
		}
		
		Severity severity = message.getSeverity();
		
		if(severity == FacesMessage.SEVERITY_ERROR 
				&& acceptLevels.contains(ERROR_NAME)){
			accept = true;
			
		}else if(severity == FacesMessage.SEVERITY_FATAL 
				&& acceptLevels.contains(FATAL_NAME)){
			accept = true;
			
		}else if(severity == FacesMessage.SEVERITY_INFO 
				&& acceptLevels.contains(INFO_NAME)){
			accept = true;
			
		}else if(severity == FacesMessage.SEVERITY_WARN 
				&& acceptLevels.contains(WARN_NAME)){
			accept = true;
		}
		
		return accept;
	}
	
	protected List<String> creatAcceptLevels(String [] levels){

		List<String> acceptLevels = new ArrayList<String>();

		for (int i = 0; i < levels.length; i++) {
			acceptLevels.add(levels[i].toUpperCase());
		}
	
		return acceptLevels;
	}
	
	 
	
	public abstract void renderMarkerHtml(UIComponent component, UIComponent markerFacet, FacesContext context, ResponseWriter writer, 
			  							  String markerClass, String markerStyle) throws IOException;

	public abstract void renderLabelHtml(UIComponent component, FacesContext context, ResponseWriter writer,
										 FacesMessage facesMsg, String labelClass, String labelStyle, String passLabel) throws IOException;
	
	public abstract void renderOuterStyles(UIComponent component, FacesContext context, ResponseWriter writer, String outerStyle, String outerClass) throws IOException;
}
