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

import javax.el.ValueExpression;

import org.richfaces.component.UIDatascroller;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;

/**
 * Created 11.03.2008
 * @author Nick Belaevski
 * @since 3.2
 */

public class DatascrollerTagHandlerBase extends ComponentHandler {

    private static final MetaRule pageRule = new MetaRule() {

	public Metadata applyRule(String name, TagAttribute attribute, MetadataTarget meta) {
	    if ("page".equals(name)) {
		return new PageMapper(attribute);
	    } else {
		return null;
	    }
	}
	
    };
    
    private static final class PageMapper extends Metadata {

	private final TagAttribute page;

	/**
	 * @param attribute
	 */
	public PageMapper(TagAttribute attribute) {
	    page = attribute;
	}

	/* (non-Javadoc)
	 * @see com.sun.facelets.tag.Metadata#applyMetadata(com.sun.facelets.FaceletContext, java.lang.Object)
	 */
	public void applyMetadata(FaceletContext ctx, Object instance) {
	    UIDatascroller datascroller = (UIDatascroller) instance;
	    ValueExpression ve = page.getValueExpression(ctx, int.class);
	    if (ve.isLiteralText()) {
		Integer value = (Integer) ve.getValue(ctx.getFacesContext().getELContext());
		datascroller.setPage(value);
	    } else {
		datascroller.setValueExpression("page", ve);
	    }
	}

    }

    public DatascrollerTagHandlerBase(ComponentConfig config) {
	super(config);
    }

    protected MetaRuleset createMetaRuleset(Class type) {
        MetaRuleset ruleset = super.createMetaRuleset(type);
        ruleset.addRule(pageRule);
        return ruleset;
    }
}
