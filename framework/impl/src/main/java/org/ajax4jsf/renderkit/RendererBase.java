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

package org.ajax4jsf.renderkit;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.ajax4jsf.Messages;
import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.ResourceNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;


/**
 * Base Renderer for all chameleon Skin's and components.
 * At most, make all common procedures and realise concrete work in "template" methods. 
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:58:51 $
 *
 */
public abstract class RendererBase extends Renderer implements
		ChameleonRenderer {
	
	/**
	 * logger for common cases. 
	 */
	protected static final String JAVASCRIPT_NAMESPACE = "Exadel";
	private static final Log log = LogFactory.getLog(RendererBase.class);
	private static final RendererUtils utils = RendererUtils.getInstance();

	private SkinFactory skinFactory = null;
	private InternetResourceBuilder resourceBuilder = null;
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#decode(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void decode(FacesContext context, UIComponent component) {
		// Test for correct parameters.
        if (context == null) throw new NullPointerException(Messages.getMessage(Messages.CONTEXT_NULL_ERROR, "decode"));
        if (component == null) throw new NullPointerException(Messages.getMessage(Messages.COMPONENT_NULL_ERROR, "decode"));
        if (! getComponentClass().isInstance(component)) throw new IllegalArgumentException(Messages.getMessage(Messages.COMPONENT_CLASS_ERROR, "", getComponentClass().getName()));
        if(log.isDebugEnabled()){
        	log.debug(Messages.getMessage(Messages.START_DECODING_COMPONENT_INFO, component.getClientId(context), component.getClass().getName()));
        }		
		preDecode(context,component);
        // TODO - create set od common decoders ( UIInput, ActionSource etc. ) for process decoding.
        if (component.isRendered()) {
			doDecode(context, component);
		}
	}

	protected void preDecode(FacesContext context, UIComponent component)   {
	}

	protected void preEncodeBegin(FacesContext context, UIComponent component) throws IOException  {
		
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		// Test for correct parameters.
        if (context == null) throw new NullPointerException(Messages.getMessage(Messages.CONTEXT_NULL_ERROR, "encodeBegin"));
        if (component == null) throw new NullPointerException(Messages.getMessage(Messages.COMPONENT_NULL_ERROR, "encodeBegin"));
        if (! getComponentClass().isInstance(component)) throw new IllegalArgumentException(Messages.getMessage(Messages.COMPONENT_CLASS_ERROR, component.getClientId(context), getComponentClass().getName()));
        if(log.isDebugEnabled()){
        	log.debug(Messages.getMessage(Messages.START_ENCODING_COMPONENT_INFO, component.getClientId(context), component.getClass().getName()));
        }
		preEncodeBegin(context,component);
        if (component.isRendered()) {
			ResponseWriter writer = context.getResponseWriter();
			doEncodeBegin(writer, context, component);
		}
	}
	
	

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		// Test for correct parameters.
        if (context == null) throw new NullPointerException(Messages.getMessage(Messages.CONTEXT_NULL_ERROR, "encodeBegin"));
        if (component == null) throw new NullPointerException(Messages.getMessage(Messages.COMPONENT_NULL_ERROR, "encodeBegin"));
        if (! getComponentClass().isInstance(component)) throw new IllegalArgumentException(Messages.getMessage(Messages.COMPONENT_CLASS_ERROR, component.getClientId(context), getComponentClass().getName()));
        if(log.isDebugEnabled()){
        	log.debug(Messages.getMessage(Messages.CHILDREN_ENCODING_COMPONENT_INFO, component.getClientId(context), component.getClass().getName()));
        }
		preEncodeBegin(context,component);
        if (component.isRendered()) {
			ResponseWriter writer = context.getResponseWriter();
			doEncodeChildren(writer, context, component);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		// Test for correct parameters.
        if (context == null) throw new NullPointerException(Messages.getMessage(Messages.CONTEXT_NULL_ERROR, "encodeEnd"));
        if (component == null) throw new NullPointerException(Messages.getMessage(Messages.COMPONENT_NULL_ERROR, "encodeEnd"));
        if (! getComponentClass().isInstance(component)) throw new IllegalArgumentException(Messages.getMessage(Messages.COMPONENT_CLASS_ERROR, component.getClientId(context), getComponentClass().getName()));
        if (component.isRendered()) {
			ResponseWriter writer = context.getResponseWriter();
			doEncodeEnd(writer, context, component);
		}
        if(log.isDebugEnabled()){
        	log.debug(Messages.getMessage(Messages.FINISH_ENCODING_COMPONENT_INFO, component.getClientId(context), component.getClass().getName()));
        }
	}

	/**
	 * Base stub method for produce internet resource ( image, script ... )
	 * since resources must be implemented in "lightweight" pattern, it instances
	 * put in internal map to caching.
	 * @param resourceURI - relative ( to renderer class ) uri to resource in jar or
	 * key for generate ( in Java2D , for example ). 
	 * @return - resource instance for this uri.
	 * @throws ResourceNotFoundException - if reqested resource not instantiated.
	 */
	public InternetResource getResource(String resourceURI ) throws FacesException {
		return getResourceBuilder().createResource(this,resourceURI);
	}

	private InternetResourceBuilder getResourceBuilder(){
		if (resourceBuilder == null) {
			resourceBuilder = InternetResourceBuilder.getInstance();
			
		}

		return resourceBuilder;
	}
	//==========================================================
	// Protected common methods - for all Renderer's
	
	/**
	 * Calculate current Skin for rendering.
	 * @param context - current {@link FacesContext }
	 * @return
	 */
	protected Skin	getSkin(FacesContext context) {
		if (skinFactory == null) {
			skinFactory = SkinFactory.getInstance();
			
		}

		return skinFactory.getSkin(context);
	}
	
	
	/**
	 * Due to big number of common utility methods, base renderer divide to 2 classes - renderer and utils.
	 * since use static methods of utility class breack object paradigm, we use getter for concrete util instance.
	 * Developer can override any utility metod in 2 stages :
	 * 1) Create subclass of {@link RendererUtils} and override utility method.
	 * 2) Override this method for return instance of such subclass.
	 * 
	 * @return Returns the utils.
	 */
	public RendererUtils getUtils() {
		return utils;
	}


	/**
	 * Get base component slass , targetted for this renderer. Used for check arguments in decode/encode.
	 * @return
	 */
	protected abstract Class<? extends UIComponent> getComponentClass();


	/**
	 * Template method for custom decoding of concrete renderer.
	 * All parameters checking if performed in original {@see decode } method.
	 * @param context
	 * @param component
	 */
	protected void doDecode(FacesContext context, UIComponent component) {
		
	}

	/**
	 * Template method for custom start encoding of concrete renderer.
	 * All parameters checking and writer is performed in original {@link encodeBegin } method.
	 * @param writer
	 * @param context
	 * @param component
	 */
	protected void doEncodeBegin(ResponseWriter writer,FacesContext context, UIComponent component) throws IOException {
		
	}
	/**
	 * @param writer
	 * @param context
	 * @param component
	 */
	protected void doEncodeChildren(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException  {
		// Hook method, must be overriden in renderers with special children processing
	}

	/**
	 * Template method for custom finish encoding of concrete renderer.
	 * All parameters checking and writer is performed in original {@link encodeEnd } method.
	 * @param writer
	 * @param context
	 * @param component
	 * @throws IOException 
	 */
	protected void doEncodeEnd(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException {
		
	}
	/**
	 * Append new resource to renderer cache.
	 * @param key
	 * @param resource
	 */
	protected void addResource(String key, InternetResource resource) {
		getResourceBuilder().addResource(key,resource);
	}

    /**
     * Render all children for given component.
     * @param facesContext
     * @param component
     * @throws IOException
     */
    public void renderChildren(FacesContext facesContext, UIComponent component) throws IOException {
        for (UIComponent child : component.getChildren()) {
            renderChild(facesContext, child);   
        }
	}


	/**
	 * Render one component and it childrens
	 * @param facesContext
	 * @param child
	 * @throws IOException
	 */
	public void renderChild(FacesContext facesContext, UIComponent child)
			throws IOException {
		if (!child.isRendered()) {
			return;
		}

		child.encodeBegin(facesContext);
		if (child.getRendersChildren()) {
			child.encodeChildren(facesContext);
		} else {
			renderChildren(facesContext, child);
		}
		child.encodeEnd(facesContext);
	}

}
