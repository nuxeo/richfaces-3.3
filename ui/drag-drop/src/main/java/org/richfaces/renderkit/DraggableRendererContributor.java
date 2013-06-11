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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.javascript.DnDScript;
import org.ajax4jsf.javascript.PrototypeScript;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils;
import org.richfaces.component.Draggable;

/**
 * @author shura
 *
 */
public class DraggableRendererContributor implements RendererContributor {
	
	public final static String DRAG_SOURCE_ID = "dragSourceId";
	
	private static DraggableRendererContributor instance;

	private DraggableRendererContributor() {
		super();
	}

	public static synchronized RendererContributor getInstance() {
		if (instance == null) {
			instance = new DraggableRendererContributor();
		}
		
		return instance;
	}
	public class DraggableOptions extends ScriptOptions {

		public DraggableOptions(Draggable draggable) {
			//TODO by nick - change this cast
			super((UIComponent) draggable);
			addOption("dragType", draggable.getDragType());
			
			String grab = draggable.getGrabCursors(); 
			if (!grab.equals("")) {
				addOption("grab", grab);
			}	
			
			String grabbing = draggable.getGrabbingCursors();
			if (!grabbing.equals("")) {
				addOption("grabbing",grabbing);
			}
			
			addEventHandler("ondragstart", draggable.getOndragstart());
			addEventHandler("ondragend", draggable.getOndragend());
			addEventHandler("ondropover",draggable.getOndropover());
			addEventHandler("ondropout",draggable.getOndropout());
		}
		
	}
	
	public void decode(FacesContext context, UIComponent component, CompositeRenderer compositeRenderer) {
		//decoding is done solely by dropzone
	}	
	
	public String[] getStyleDependencies() {
		return new String[] {
			"/org/richfaces/renderkit/html/css/dragIndicator.xcss"
		};
	}
	
	public String[] getScriptDependencies() {
		return new String[] {
				PrototypeScript.class.getName(),
				"/org/richfaces/renderkit/html/scripts/json/json-mini.js",
				DnDScript.class.getName(),
				"/org/richfaces/renderkit/html/scripts/utils.js",
				"/org/richfaces/renderkit/html/scripts/json/json-dom.js",
				"/org/richfaces/renderkit/html/scripts/dnd/dnd-common.js",
				"/org/richfaces/renderkit/html/scripts/dnd/dnd-draggable.js"
			};
	}
	
	public ScriptOptions buildOptions(FacesContext context, UIComponent component) {
		if (component instanceof Draggable) {
			Draggable draggable = (Draggable) component;
			
			DraggableOptions options =  new DraggableOptions(draggable);

			Map eventOptions = AjaxRendererUtils.buildEventOptions(context, component, 
				Collections.singletonMap(DRAG_SOURCE_ID, (Object) component.getClientId(context)));
			Map parameters = (Map) eventOptions.get("parameters");

			if (parameters == null) {
				parameters = new HashMap();
			}
			
			options.addOption("parameters", parameters);
			
			String indicatorId = draggable.getResolvedDragIndicator(context);
			if (indicatorId == null) {
				String simpleId = draggable.getDragIndicator();
				if (simpleId != null) {
					UIComponent indicator = RendererUtils.getInstance().findComponentFor(component, simpleId);
					if (indicator != null) {
						indicatorId = indicator.getClientId(context);
					}
				}
			}
			
			if (indicatorId != null) {
				options.addOption("dragIndicator", indicatorId);
			}
			
			return options;
		}

		return null;
	}
	
	public String getScriptContribution(FacesContext context,
			UIComponent component) {
		// TODO Auto-generated method stub
		return null;
	}

	public Class getAcceptableClass() {
		return Draggable.class;
	}

}
