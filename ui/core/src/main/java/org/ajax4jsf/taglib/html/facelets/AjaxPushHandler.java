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

package org.ajax4jsf.taglib.html.facelets;

import java.util.EventListener;

import org.ajax4jsf.component.UIPush;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;

/**
 * @author shura (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 7647 $ $Date: 2008-04-08 00:41:37 +0200 (Tue, 08 Apr 2008) $
 *
 */
public class AjaxPushHandler extends ComponentHandler {

	private static final MetaRule ajaxPushMetaRule = new AjaxPushMetaRule();
	/**
	 * @param config
	 */
	public AjaxPushHandler(ComponentConfig config) {
		super(config);
		
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tag.AjaxComponentHandler#createMetaRuleset(java.lang.Class)
	 */
	protected MetaRuleset createMetaRuleset(Class type) {
		MetaRuleset metaRules = super.createMetaRuleset(type);
		metaRules.addRule(ajaxPushMetaRule);
		return metaRules;
	}

	/**
	 * @author shura (latest modification by $Author: alexsmirnov $)
	 * @version $Revision: 7647 $ $Date: 2008-04-08 00:41:37 +0200 (Tue, 08 Apr 2008) $
	 *
	 */
	static class AjaxPushMetaRule extends MetaRule{

		/**
		 * 
		 */
		public AjaxPushMetaRule() {
			super();
		}

		/* (non-Javadoc)
		 * @see com.sun.facelets.tag.MetaRule#applyRule(java.lang.String, com.sun.facelets.tag.TagAttribute, com.sun.facelets.tag.MetadataTarget)
		 */
		public Metadata applyRule(String name, TagAttribute attribute, MetadataTarget meta) {
	        if (meta.isTargetInstanceOf(UIPush.class)) {


	            if ("eventProducer".equals(name)) {
	                    return new AjaxPushActionMapper(attribute);
	            }
	        }
			return null;
		}

	}
	/**
	 * @author shura (latest modification by $Author: alexsmirnov $)
	 * @version $Revision: 7647 $ $Date: 2008-04-08 00:41:37 +0200 (Tue, 08 Apr 2008) $
	 *
	 */
	static class AjaxPushActionMapper extends Metadata {

		private static final Class<?>[] AJAX_PUSH_ACTION_SIG = new Class[] {EventListener.class};

		private final TagAttribute _send;
		/**
		 * @param attribute
		 */
		public AjaxPushActionMapper(TagAttribute attribute) {
			_send = attribute;
		}

		/* (non-Javadoc)
		 * @see com.sun.facelets.tag.Metadata#applyMetadata(com.sun.facelets.FaceletContext, java.lang.Object)
		 */
		public void applyMetadata(FaceletContext ctx, Object instance) {
		    ((UIPush) instance).setEventProducer(this._send.getMethodExpression(ctx, null,
		    AJAX_PUSH_ACTION_SIG));
		}

	}

}
