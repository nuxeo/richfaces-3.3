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

package org.richfaces.renderkit;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.richfaces.component.UIJQuery;
import org.richfaces.component.util.HtmlUtil;


public class JQueryRendererBase extends HeaderResourcesRendererBase {

		protected Class getComponentClass() {
			return UIJQuery.class;
		}


	
	protected void checkValidity(String clientId, String name, String timing, String query) {
		
		if (  	! "onJScall".equals(timing) &&
				! "onload".equals(timing) &&
				! "immediate".equals(timing) ) {
			throw new FacesException(
			"The timing attribute of the jQuery component (id='"+clientId+"') has an invalid value:'"+ timing +
			"'. It may have only the following values: 'immediate', 'onload', 'onJScall'");
		}
		
		if ( name == null ) {
			throw new FacesException(
					"The name attribute of the jQuery component (id='"+clientId+"') might not be null" );
				
		}

		if ( "".equals(name.trim()) && "onJScall".equals(timing) ) {
			throw new FacesException(
					"The name attribute of the jQuery component (id='"+clientId+"') must be specified when timing attribute equals to 'onJScall'" );
		}

		if ( "".equals(query.trim()) || query==null ) {
			throw new FacesException(
					"The query attribute of the jQuery component (id='"+clientId+"') must be specified" );
		}
		
	}
	protected String replaceClientIds(FacesContext context, UIComponent component, String selector) {
		return HtmlUtil.expandIdSelector(selector, component, context);
	}
	
}
