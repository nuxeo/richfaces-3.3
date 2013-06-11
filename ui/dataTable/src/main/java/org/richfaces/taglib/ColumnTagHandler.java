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
package org.richfaces.taglib;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;

/**
 * @author Konstantin Mishin
 *
 */
public class ColumnTagHandler extends ComponentHandler {

	private final static String FILTER_METHOD = "filterMethod";

	public ColumnTagHandler(ComponentConfig config) {
		super(config);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected MetaRuleset createMetaRuleset(Class type) {
		MetaRuleset ruleset = super.createMetaRuleset(type);
		
		ruleset.addRule(new MetaRule() {

			@Override
			public Metadata applyRule(String name, final TagAttribute attribute, MetadataTarget metadataTarget) {
				if (FILTER_METHOD.equals(name)) {
					return new Metadata() {
						public void applyMetadata(FaceletContext context, Object object) {
							FacesContext facesContext = context.getFacesContext();
							
							MethodExpression expression = facesContext.getApplication().getExpressionFactory().
								createMethodExpression(facesContext.getELContext(), attribute.getValue(), boolean.class, new Class[]{Object.class});
							((UIComponent) object).getAttributes().put(FILTER_METHOD, expression);
						}
					};
				}
				
				return null;
			}
		});
		
		return ruleset;
	}
}
