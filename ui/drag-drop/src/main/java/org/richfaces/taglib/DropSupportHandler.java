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

package org.richfaces.taglib;

import org.ajax4jsf.webapp.taglib.AjaxComponentHandler;
import org.richfaces.component.UIDropSupport;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.el.LegacyMethodBinding;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ComponentConfig;

/**
 * @author Nick - mailto:nbelaevski@exadel.com created 01.03.2007
 * 
 */
public class DropSupportHandler extends AjaxComponentHandler {

	public DropSupportHandler(ComponentConfig config) {
		super(config);
	}

	private static final DropSupportHandlerMetaRule metaRule = new DropSupportHandlerMetaRule();

	// Metarule
	protected MetaRuleset createMetaRuleset(Class type) {
		MetaRuleset m = super.createMetaRuleset(type);
		m.addRule(metaRule);
		return m;
	}

	/**
	 * @author shura (latest modification by $Author$)
	 * @version $Revision$ $Date$
	 * 
	 */
	static class DropSupportHandlerMetaRule extends MetaRule {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.sun.facelets.tag.MetaRule#applyRule(java.lang.String,
		 *      com.sun.facelets.tag.TagAttribute,
		 *      com.sun.facelets.tag.MetadataTarget)
		 */
		public Metadata applyRule(String name, TagAttribute attribute,
				MetadataTarget meta) {
			if (meta.isTargetInstanceOf(UIDropSupport.class)) {
				if ("dropListener".equals(name)) {
					return new dropListenerMapper(attribute);
				}

			}
			return null;
		}

	}

	static class dropListenerMapper extends Metadata {

		private static final Class[] SIGNATURE = new Class[] { org.richfaces.event.DropEvent.class };

		private final TagAttribute _action;

		/**
		 * @param attribute
		 */
		public dropListenerMapper(TagAttribute attribute) {
			_action = attribute;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.sun.facelets.tag.Metadata#applyMetadata(com.sun.facelets.FaceletContext,
		 *      java.lang.Object)
		 */
		public void applyMetadata(FaceletContext ctx, Object instance) {
			((UIDropSupport) instance)
			.setDropListener(new LegacyMethodBinding(this._action
					.getMethodExpression(ctx, null, SIGNATURE)));
		}

	}
}
