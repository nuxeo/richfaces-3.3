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

import java.awt.Graphics2D;

import org.richfaces.component.UIPaint2D;
import com.sun.facelets.FaceletContext;
import com.sun.facelets.el.LegacyMethodBinding;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;

/**
 * @author shura (latest modification by $Author: ishabalov $)
 * @version $Revision: 1.2 $ $Date: 2007/02/21 20:35:04 $
 *
 */
public class Image2DTagHandler extends ComponentHandler {

	private static final MetaRule image2DMetaRule = new Image2DMetaRule();
	/**
	 * @param config
	 */
	public Image2DTagHandler(ComponentConfig config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tag.AjaxComponentHandler#createMetaRuleset(java.lang.Class)
	 */
	protected MetaRuleset createMetaRuleset(Class type) {
		MetaRuleset metaRules = super.createMetaRuleset(type);
		metaRules.addRule(image2DMetaRule);
		return metaRules;
	}

	/**
	 * @author shura (latest modification by $Author: ishabalov $)
	 * @version $Revision: 1.2 $ $Date: 2007/02/21 20:35:04 $
	 *
	 */
	static class Image2DMetaRule extends MetaRule{

		/**
		 * 
		 */
		public Image2DMetaRule() {
			super();
			// TODO Auto-generated constructor stub
		}

		/* (non-Javadoc)
		 * @see com.sun.facelets.tag.MetaRule#applyRule(java.lang.String, com.sun.facelets.tag.TagAttribute, com.sun.facelets.tag.MetadataTarget)
		 */
		public Metadata applyRule(String name, TagAttribute attribute, MetadataTarget meta) {
	        if (meta.isTargetInstanceOf(UIPaint2D.class)) {


	            if ("paint".equals(name)) {
	                    return new Image2DActionMapper(attribute);
	            }
	        }
			return null;
		}

	}
	/**
	 * @author shura (latest modification by $Author: ishabalov $)
	 * @version $Revision: 1.2 $ $Date: 2007/02/21 20:35:04 $
	 *
	 */
	static class Image2DActionMapper extends Metadata {

		private static final Class[] Image2D_ACTION_SIG = new Class[] {Graphics2D.class,Object.class};

		private final TagAttribute _paint;
		/**
		 * @param attribute
		 */
		public Image2DActionMapper(TagAttribute attribute) {
			_paint = attribute;
		}

		/* (non-Javadoc)
		 * @see com.sun.facelets.tag.Metadata#applyMetadata(com.sun.facelets.FaceletContext, java.lang.Object)
		 */
		public void applyMetadata(FaceletContext ctx, Object instance) {
            ((UIPaint2D) instance)
            .setPaint(new LegacyMethodBinding(this._paint
                    .getMethodExpression(ctx, null,
                            Image2D_ACTION_SIG)));
		}

	}

}
