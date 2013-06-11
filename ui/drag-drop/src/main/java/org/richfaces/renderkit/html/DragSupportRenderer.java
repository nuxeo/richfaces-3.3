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
import java.util.LinkedHashSet;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.javascript.AjaxScript;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.resource.InternetResource;
import org.richfaces.component.UIDragSupport;
import org.richfaces.renderkit.CompositeRenderer;
import org.richfaces.renderkit.DnDParametersEncoder;
import org.richfaces.renderkit.DraggableRendererContributor;
import org.richfaces.renderkit.RendererContributor;
import org.richfaces.renderkit.ScriptOptions;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 23.02.2007
 * 
 */
public class DragSupportRenderer extends CompositeRenderer {

	private InternetResource [] scripts = null; 
	
	public DragSupportRenderer() {
		super();

		addContributor(DraggableRendererContributor.getInstance());
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.RendererBase#getComponentClass()
	 */
	protected Class getComponentClass() {
		return UIDragSupport.class;
	}
	
	protected InternetResource[] getScripts() {
		synchronized (this) {
			if (scripts == null) {
				LinkedHashSet set = new LinkedHashSet();
				InternetResource[] resources = super.getScripts();
				if (resources != null) {
					for (int i = 0; i < resources.length; i++) {
						set.add(resources[i]);
					}
				}

				set.add(new AjaxScript());
				set.add(getResource("/org/richfaces/renderkit/html/scripts/simple-draggable.js"));
				
				scripts = (InternetResource[]) set.toArray(new InternetResource[set.size()]);
			}
		}
		
		return scripts;
	}
	
	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {
		super.encodeEnd(context, component);
		StringBuffer buffer = new StringBuffer();
		JSFunction function = new JSFunction("new DnD.SimpleDraggable");
		function.addParameter(component.getParent().getClientId(context));
		RendererContributor contributor = DraggableRendererContributor.getInstance();
		ScriptOptions dragOptions = contributor.buildOptions(context, component);
		
		DnDParametersEncoder parametersEncoder = DnDParametersEncoder.getInstance();
		dragOptions.addOption("dndParams", parametersEncoder.doEncodeAsString(context, component));
		
		function.addParameter(dragOptions);
		function.appendScript(buffer);

		String scriptContribution = contributor.getScriptContribution(context, component);
		if (scriptContribution != null && scriptContribution.length() != 0) {
			buffer.append(scriptContribution);
		}

		ResponseWriter writer = context.getResponseWriter();
		
		writer.startElement(HTML.SCRIPT_ELEM, component);
		writer.writeAttribute("id", component.getClientId(context), "id");
		writer.write(escapeHtmlEntities(buffer));
		writer.endElement(HTML.SCRIPT_ELEM);
	}
}
