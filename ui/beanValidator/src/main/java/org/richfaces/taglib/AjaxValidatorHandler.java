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

import java.io.IOException;
import java.util.Set;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.webapp.taglib.AjaxComponentHandler;
import org.richfaces.component.UIBeanValidator;
import org.richfaces.validator.FacesBeanValidator;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagException;
import com.sun.facelets.tag.TagHandler;
import com.sun.facelets.tag.jsf.ComponentConfig;

/**
 * @author asmirnov
 * 
 */
public class AjaxValidatorHandler extends TagHandler {

	/**
	 * A UIComponent for capturing a child UIComponent, representative of the
	 * desired Facet
	 * 
	 * @author Jacob Hookom
	 * 
	 */
	private final static class UIFacet extends UIComponentBase {
		public String getFamily() {
			return null;
		}
	}

	private TagAttribute _event;
	private TagAttribute _summary;
	private TagAttribute _profiles;
	private AjaxComponentHandler _validatorHandler;

	/**
	 * @param config
	 */
	public AjaxValidatorHandler(ComponentConfig config) {
		super(config);
		_event = getAttribute("event");
		_summary = getAttribute("summary");
		_profiles = getAttribute("profiles");
		_validatorHandler = new AjaxComponentHandler(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sun.facelets.FaceletHandler#apply(com.sun.facelets.FaceletContext,
	 * javax.faces.component.UIComponent)
	 */
	public void apply(FaceletContext ctx, UIComponent parent)
			throws IOException, FacesException, FaceletException, ELException {
		if (parent == null || !(parent instanceof EditableValueHolder)) {
			throw new TagException(this.tag,
					"Parent not an instance of EditableValueHolder: " + parent);
		}
		if (null == parent.getParent()) {
			// New created component, add validator.
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesBeanValidator validator = (FacesBeanValidator) facesContext
					.getApplication().createValidator(
							FacesBeanValidator.BEAN_VALIDATOR_TYPE);
			if (null != _summary) {
				if (_summary.isLiteral()) {
					validator.setSummary(_summary.getValue(ctx));

				} else {
					validator.setSummary(_summary.getValueExpression(ctx,
							String.class));
				}
			}
			if( null != _profiles){
				if(_profiles.isLiteral()){
					validator.setProfiles(AjaxRendererUtils.asSet(_profiles.getValue()));
				} else {
					validator.setProfiles(_profiles.getValueExpression(ctx, Set.class));
				}
			}
			((EditableValueHolder) parent).addValidator(validator);
		}
		if (null != this._event) {
			UIComponent c;
			UIFacet facet = new UIFacet();
			// Find facet for client validation component
			String eventName = _event.getValue();
			String facetName = UIBeanValidator.BEAN_VALIDATOR_FACET + eventName;
			c = parent.getFacet(facetName);
			if (null != c) {
				parent.getFacets().remove(facetName);
				facet.getChildren().add(c);
			}
			this._validatorHandler.apply(ctx, facet);
			c = (UIComponent) facet.getChildren().get(0);
			parent.getFacets().put(facetName, c);

		}
	}

}
