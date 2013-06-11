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

package org.ajax4jsf.renderkit.compiler;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.renderkit.RendererBase;


/**
 * Created 27.02.2008
 * @author Nick Belaevski
 * @since 3.2
 */

public class ImportResourceElement extends ElementBase {

	private PreparedTemplate template;

	public ImportResourceElement(String source) {
		template = HtmlCompiler.compileResource(source);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.PreparedTemplate#getTag()
	 */
	public String getTag() {
		return HtmlCompiler.NS_PREFIX+HtmlCompiler.IMPORT_RESOURCE_TAG;
	}

	public void encode(RendererBase renderer, FacesContext context,
			UIComponent component) throws IOException {
		template.encode(renderer, context, component);
	}

	public void encode(TemplateContext context, String breakPoint)
			throws IOException {
		template.encode(context, breakPoint);
	}

	public void encode(TemplateContext context) throws IOException {
		template.encode(context);
	}

	public Object getValue(TemplateContext context) {
		return template.getValue(context);
	}

}
