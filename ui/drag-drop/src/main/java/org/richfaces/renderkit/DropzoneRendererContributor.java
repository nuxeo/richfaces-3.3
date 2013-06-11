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

package org.richfaces.renderkit;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.component.ContextCallbackWrapper;
import org.ajax4jsf.javascript.DnDScript;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.javascript.PrototypeScript;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.richfaces.component.Draggable;
import org.richfaces.component.Dropzone;
import org.richfaces.component.util.MessageUtil;
import org.richfaces.event.DragEvent;
import org.richfaces.event.DropEvent;
import org.richfaces.json.JSONCollection;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONMap;

/**
 * @author shura
 *
 */
public class DropzoneRendererContributor implements RendererContributor {

	public static final String DROP_TARGET_ID = "dropTargetId";

	private DropzoneRendererContributor() {
		super();
	}

	private static RendererContributor instance;

	public static synchronized RendererContributor getInstance() {
		if (instance == null) {
			instance = new DropzoneRendererContributor();
		}

		return instance;
	}

	/**
	 * Utility class for building scripting options
	 * @author Maksim Kaszynski
	 *
	 */
	public class DropZoneOptions extends ScriptOptions {

		public DropZoneOptions(Dropzone zone) {
			//TODO by nick - change this cast
			super((UIComponent) zone);

			Object acceptedTypes = zone.getAcceptedTypes();
			if(acceptedTypes instanceof String) {
				try {
					String typesString = ((String) acceptedTypes).trim();
					if (!typesString.startsWith("[")) {
						typesString = "[" + typesString + "]";
					}

					acceptedTypes = new JSONCollection(typesString);
				} catch (JSONException e) {
					throw new FacesException(e);
				}
			}
			addOption("acceptedTypes", acceptedTypes);

			Object typeMapping = zone.getTypeMapping();
			if(typeMapping instanceof String) {
				try {
					typeMapping = new JSONMap((String)typeMapping);
				} catch (JSONException e) {
					throw new FacesException(e);
				}
			}
			addOption("typeMapping", typeMapping);
			
			Object cursorTypeMapping = zone.getCursorTypeMapping();
			if (cursorTypeMapping instanceof String) {
				try {
					cursorTypeMapping = new JSONMap((String)cursorTypeMapping);
				} catch (JSONException e) {
					throw new FacesException(e);	
				}
			}
			
			addOption("cursorTypeMapping", cursorTypeMapping);
			
			String acceptCursors = zone.getAcceptCursors(); 
			if (!acceptCursors.equals("")) {
				addOption("acceptCursor", acceptCursors);
			}
				
			String rejectCursors = zone.getRejectCursors();
			if (!rejectCursors.equals("")) {
				addOption("rejectCursor", rejectCursors);
			}
							
			addEventHandler("ondragenter", zone.getOndragenter());
			addEventHandler("ondragexit", zone.getOndragexit());
			addEventHandler("onafterdrag");
			addEventHandler("ondrop");
			addEventHandler("ondropend");
		}
	}
	
	public ScriptOptions buildOptions(FacesContext context, UIComponent drop) {
		if (drop instanceof Dropzone) {
			return new DropZoneOptions((Dropzone) drop);
		}

		return null;
	}

	private static final class DraggableDecoderContextCallback implements ContextCallback {

		private Dropzone dropzone;
		
		private Draggable draggable;
		
		private String dragType;
		
		private Object dragValue;
		
		private Object acceptedTypes;
		
		private Object dropValue;
		
		private boolean valid = true;

		private Object draggableLabel;
		
		public DraggableDecoderContextCallback(Dropzone dropzone, Object acceptedTypes, Object dropValue) {
			super();
			
			this.dropzone = dropzone;
			this.acceptedTypes = acceptedTypes;
			this.dropValue = dropValue;
		}

		private boolean validateAcceptTypes(String dragType, Object acceptedTypes) {
			Set<String> set = AjaxRendererUtils.asSet(acceptedTypes);
			return (set != null && set.contains(dragType));
		}
		
		public void invokeContextCallback(FacesContext context,
				UIComponent target) {

			this.draggable = (Draggable) target;

			this.dragType = draggable.getDragType();
			this.dragValue = draggable.getDragValue();
			
			if (validateAcceptTypes(dragType, acceptedTypes)) {

				DragEvent dragEvent = new DragEvent((UIComponent) draggable);
				dragEvent.setDropTarget(dropzone);
				dragEvent.setAcceptedTypes(acceptedTypes);
				dragEvent.setDropValue(dropValue);

				dragEvent.queue();
			} else {
				//invalidate
				this.valid = false;
				//store label for message output
				this.draggableLabel = MessageUtil.getLabel(context, (UIComponent) draggable);
			}
		}
		
		public Draggable getDraggable() {
			return draggable;
		}
		
		public String getDragType() {
			return dragType;
		}
		
		public Object getDragValue() {
			return dragValue;
		}
		
		public boolean isValid() {
			return valid;
		}
		
		public Object getDraggableLabel() {
			return draggableLabel;
		}
	}

	private final static String MISTYPED_DND_MESSAGE = "Dropzone [{0}] with accepted types {1} cannot accept Draggable [{2}] with dragType [{3}]";
	
	public void decode(FacesContext context, UIComponent component, CompositeRenderer compositeRenderer) {
		String clientId = component.getClientId(context);
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();

		if(clientId.equals(paramMap.get(DROP_TARGET_ID))){
			String dragSourceId = (String) paramMap.get(DraggableRendererContributor.DRAG_SOURCE_ID);

			if (dragSourceId != null && dragSourceId.length() != 0) {
				Dropzone dropzone = (Dropzone) component;
				Object acceptedTypes = dropzone.getAcceptedTypes();
				Object dropValue = dropzone.getDropValue();

				DraggableDecoderContextCallback draggableDecoderContextCallback = 
					new DraggableDecoderContextCallback(dropzone, 
							acceptedTypes, dropValue);
				
				context.getViewRoot().invokeOnComponent(context, dragSourceId, 
					new ContextCallbackWrapper(draggableDecoderContextCallback));
			
				Draggable draggable = draggableDecoderContextCallback.getDraggable();
				if (draggable != null) {
					if (draggableDecoderContextCallback.isValid()) {
						DropEvent dropEvent = new DropEvent(component);
						dropEvent.setDraggableSource(draggable);
						dropEvent.setDragType(draggableDecoderContextCallback.getDragType());
						dropEvent.setDragValue(draggableDecoderContextCallback.getDragValue());

						dropEvent.queue();
					} else {
						String text = MessageFormat.format(
								MISTYPED_DND_MESSAGE, 
								new Object[] {
										MessageUtil.getLabel(context, component), 
										acceptedTypes, 
										draggableDecoderContextCallback.getDraggableLabel(), 
										draggableDecoderContextCallback.getDragType()});

						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text);

						context.addMessage(component.getClientId(context), message);

						context.renderResponse();
					}
				}
			}
		}
	}
	
	public String getScriptContribution(FacesContext context, UIComponent component) {
		StringBuffer result = new StringBuffer();

		result.append(".drop = ");

		JSFunctionDefinition definition = new JSFunctionDefinition();
		definition.addParameter("event");
		definition.addParameter("drag");

		Map requestOpts = AjaxRendererUtils.buildEventOptions(context, component);
		definition.addToBody("var options = ").addToBody(ScriptUtils.toScript(requestOpts)).addToBody(";");
		definition.addToBody("options.parameters['" + DROP_TARGET_ID + "'] = '" + component.getClientId(context) + "';");
		//TODO nick - remove as legacy
		definition.addToBody("Object.extend(options.parameters, drag.getParameters());");
		definition.addToBody("var dzOptions = this.getDropzoneOptions(); if (dzOptions.ondrop) { if (!dzOptions.ondrop.call(this, event)) return; };");
		
		JSFunction dropFunction = AjaxRendererUtils.buildAjaxFunction(component, context);
		dropFunction.addParameter(new JSReference("options"));
		
		definition.addToBody(dropFunction.toScript()).addToBody(";");
		definition.appendScript(result);
		result.append(";");

		return result.toString();
	}

	public String[] getStyleDependencies() {
		return null;
	}
	
	public String[] getScriptDependencies() {
		return new String[] {
			PrototypeScript.class.getName(),
			"/org/richfaces/renderkit/html/scripts/json/json-mini.js",
			DnDScript.class.getName(),
			"/org/richfaces/renderkit/html/scripts/utils.js",
			"/org/richfaces/renderkit/html/scripts/json/json-dom.js",
			"/org/richfaces/renderkit/html/scripts/dnd/dnd-common.js",
			"/org/richfaces/renderkit/html/scripts/dnd/dnd-dropzone.js"
		};
	}

	public Class getAcceptableClass() {
		return Dropzone.class;
	}

}
