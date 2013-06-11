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

package org.ajax4jsf.renderkit.html;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.Messages;
import org.ajax4jsf.component.UIMediaOutput;
import org.ajax4jsf.renderkit.RendererBase;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.InternetResourceBuilder;

/**
 * @author shura
 *
 */
public class MediaOutputRenderer extends RendererBase {
	
    public static final String RENDERER_TYPE = "org.ajax4jsf.MMediaRenderer";
    
    /**
     * Associationd between element name and uri attributes
     */
    private static final Map uriAttributes;
    
    static {
    	uriAttributes = new HashMap();
    	uriAttributes.put("a","href");
    	uriAttributes.put("img","src");
    	uriAttributes.put("object","data");
    	uriAttributes.put("link","href");
    }


	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.RendererBase#doEncodeEnd(javax.faces.context.ResponseWriter, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	protected void doEncodeEnd(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException {
		UIMediaOutput mmedia = (UIMediaOutput) component;
		String element = mmedia.getElement();
		if(null == element){
			throw new FacesException(Messages.getMessage(Messages.NULL_ATTRIBUTE_ERROR,"element",component.getClientId(context)));
		}
		writer.endElement(element);
	}


	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.RendererBase#getComponentClass()
	 */
	protected Class getComponentClass() {
		// TODO Auto-generated method stub
		return UIMediaOutput.class;
	}


	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.RendererBase#doEncodeBegin(javax.faces.context.ResponseWriter, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	protected void doEncodeBegin(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException {
		UIMediaOutput mmedia = (UIMediaOutput) component;
		String element = mmedia.getElement();
		if(null == element){
			throw new FacesException(Messages.getMessage(Messages.NULL_ATTRIBUTE_ERROR,"element",component.getClientId(context)));
		}
		String uriAttribute = mmedia.getUriAttribute();
		// Check for pre-defined attributes
		if(null == uriAttribute){
			uriAttribute = (String) uriAttributes.get(element);
			if(null == uriAttribute){
				throw new FacesException(Messages.getMessage(Messages.NULL_ATTRIBUTE_ERROR,"uriAttribute",component.getClientId(context)));
			}
		}
		writer.startElement(element,mmedia);
		getUtils().encodeId(context,component);
		InternetResourceBuilder internetResourceBuilder = InternetResourceBuilder.getInstance();
		InternetResource resource = internetResourceBuilder.createUserResource(mmedia.isCacheable(),mmedia.isSession(),mmedia.getMimeType());
		StringBuffer uri = new StringBuffer(resource.getUri(context,mmedia));
		// Append parameters to resource Uri
		boolean haveQestion = uri.indexOf("?")>=0;
        Iterator kids = component.getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();

            if (kid instanceof UIParameter) {
                UIParameter uiParam = (UIParameter) kid;
                String name = uiParam.getName();
                Object value = uiParam.getValue();
                if(null != value){
                	if(haveQestion){
                		uri.append('&');
                	} else {
						uri.append('?');
						haveQestion = true;
					}
                	uri.append(name).append('=').append(value.toString());
                }
            }
        }
		writer.writeURIAttribute(uriAttribute,uri,"uri");
        getUtils().encodeAttributesFromArray(context,component,HTML.PASS_THRU_STYLES);
		getUtils().encodePassThru(context,mmedia);
	}

}
