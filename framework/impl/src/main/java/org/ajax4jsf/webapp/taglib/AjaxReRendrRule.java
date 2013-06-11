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

package org.ajax4jsf.webapp.taglib;

import java.util.Set;

import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.renderkit.AjaxRendererUtils;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;

/**
 * @author shura (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/02/01 15:31:22 $
 *
 */
public class AjaxReRendrRule extends MetaRule {

	public static final AjaxReRendrRule instance = new AjaxReRendrRule();
	/**
	 * 
	 */
	public AjaxReRendrRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sun.facelets.tag.MetaRule#applyRule(java.lang.String, com.sun.facelets.tag.TagAttribute, com.sun.facelets.tag.MetadataTarget)
	 */
	public Metadata applyRule(String name, TagAttribute attribute,
			MetadataTarget meta) {
        if (meta.isTargetInstanceOf(AjaxComponent.class)) {
        	if ("reRender".equals(name)) {
				if (attribute.isLiteral()) {
					return new ReRendersSetMataData(attribute.getValue());
				} else {
					// Process as usual expression
					return null;
				}
			}
        }
		return null;
	}

	static class ReRendersSetMataData extends Metadata{

		private Set _reRender;
		/**
		 * @param value
		 */
		public ReRendersSetMataData(String value) {
			_reRender = AjaxRendererUtils.asSet(value);
		}

		/* (non-Javadoc)
		 * @see com.sun.facelets.tag.Metadata#applyMetadata(com.sun.facelets.FaceletContext, java.lang.Object)
		 */
		public void applyMetadata(FaceletContext ctx, Object instance) {
			((AjaxComponent) instance).setReRender(_reRender);
			
		}
		
	}
}
