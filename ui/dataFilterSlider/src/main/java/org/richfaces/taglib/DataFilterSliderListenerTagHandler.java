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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;

import org.richfaces.event.DataFilterSliderEvent;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagAttributes;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;


public class DataFilterSliderListenerTagHandler extends ComponentHandler {
    private final static String SLIDER_LISTENER = "sliderListener";

    public DataFilterSliderListenerTagHandler(ComponentConfig config) {
	super(config);
    }

    protected MetaRuleset createMetaRuleset(Class clazz) {
	TagAttributes attributes = this.tag.getAttributes();
	TagAttribute attribute = attributes.get("onSlideSubmit");
	if ((attribute != null) && (attributes.get("submitOnSlide") != null)) {
	    TagAttribute idAttribute = attributes.get("id");
	    StringBuilder builder = new StringBuilder();
	    builder.append("submitOnSlide attribute has been already set for component with id=");
	    builder.append(idAttribute != null ? idAttribute.getValue() : null);
	    builder.append(": [").append(attribute.getValue()).append("]. ");
	    builder.append("Attribute onSlideSubmit is deprecated and thus has been dropped!");

	    FacesContext.getCurrentInstance().getExternalContext().log(builder.toString());
	}

	MetaRuleset ruleset = super.createMetaRuleset(clazz);
	ruleset.alias("submitOnSlide", "onSlideSubmit");

	ruleset.addRule(new MetaRule() {

	    public Metadata applyRule(String name, final TagAttribute attribute, MetadataTarget metadataTarget) {
		if (SLIDER_LISTENER.equals(name)) {
		    return new Metadata() {
			public void applyMetadata(FaceletContext context, Object object) {
			    MethodBinding binding = context.getFacesContext().getApplication().createMethodBinding(
				    attribute.getValue(), new Class[] { DataFilterSliderEvent.class });
			    ((UIComponent) object).getAttributes().put(SLIDER_LISTENER, binding);
			}
		    };
		}
		return null;
	    }
	});

	return ruleset;
    }
}

