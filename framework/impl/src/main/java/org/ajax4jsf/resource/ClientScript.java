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

package org.ajax4jsf.resource;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.ajax4jsf.Messages;
import org.ajax4jsf.javascript.PrototypeScript;
import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.ResourceContext;
import org.ajax4jsf.resource.ResourceNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Resource for AJAX client-side script. Render one time per page.
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.3 $ $Date: 2007/01/26 14:15:18 $
 *
 */
public abstract class ClientScript extends JarResource {

	private static final Log log = LogFactory.getLog(ClientScript.class);
	
	protected boolean usePrototype = false;
	/**
	 * Set JavaScript renderer and modification time to application-startup time.
	 */
	public ClientScript() {
		super();
		InternetResourceBuilder resourceBuilder = InternetResourceBuilder.getInstance();
		setRenderer(resourceBuilder.getScriptRenderer());
		String key = this.getClass().getName();
		try {
			// Search already registered resource for this class.
			InternetResource resource = resourceBuilder.getResource(key);
			this.setKey(resource.getKey());
		} catch(ResourceNotFoundException ex){
			// If script not registered, append it to builder.
			setLastModified(new Date(resourceBuilder.getStartTime()));
			resourceBuilder.addResource(key,this);
		}
		String script ;
		if (getJavaScript().startsWith("/")) {
			// remove lead / for classloader covention.
			script = getJavaScript().substring(1);
		} else {
			script = this.getClass().getPackage().getName().replace('.', '/')+"/"
			+ getJavaScript();			
		}
		setPath(script);
	}
	
	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.InternetResourceBase#getLastModified()
	 */
	public Date getLastModified(ResourceContext resourceContext) {

		if (isCacheable(resourceContext)) {
			return super.getLastModified(resourceContext);
		} else {
			return new Date(System.currentTimeMillis()+1000L);
		}
	}


	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.InternetResourceBase#isCacheable()
	 */
	public boolean isCacheable(ResourceContext resourceContext) {
		return true;
	}

	/**
	 * @return Returns the javaScript.
	 */
	public abstract String getJavaScript();

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.InternetResourceBase#encode(javax.faces.context.FacesContext, java.lang.Object, java.util.Map)
	 */
	public void encode(FacesContext context, Object data, Map<String, Object> attributes) throws IOException {
		if (isNotAjaxRequest(context)) {
			encodePrototype(context);
			super.encode(context, data, attributes);
		} else if (log.isDebugEnabled()) {
			log.debug(Messages.getMessage(Messages.SKIP_ENCODING_HTML_INFO, getKey()));
		}

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.InternetResourceBase#encodeBegin(javax.faces.context.FacesContext, java.lang.Object, java.util.Map)
	 */
	public void encodeBegin(FacesContext context, Object component, Map<String, Object> attrs) throws IOException {
		if (isNotAjaxRequest(context)) {
			encodePrototype(context);
			super.encodeBegin(context, component, attrs);
		} else if (log.isDebugEnabled()) {
			log.debug(Messages.getMessage(Messages.SKIP_ENCODE_BEGIN_HTML_INFO, getKey()));
		}
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.InternetResourceBase#encodeEnd(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void encodeEnd(FacesContext context, Object component) throws IOException {
		if (isNotAjaxRequest(context)) {
			super.encodeEnd(context, component);
		} else if (log.isDebugEnabled()) {
			log.debug(Messages.getMessage(Messages.SKIP_ENCODE_END_HTML_INFO, getKey()));
		}
	}
	

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.InternetResourceBase#encode(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void encode(FacesContext context, Object data) throws IOException {
		if (isNotAjaxRequest(context)) {
			encodePrototype(context);
			super.encode(context, data);
		}else if (log.isDebugEnabled()) {
			log.debug(Messages.getMessage(Messages.SKIP_ENCODING_HTML_INFO, getKey()));
		}
	}

	/**
	 * @return Returns the usePrototype.
	 */
	protected boolean isUsePrototype() {
		return usePrototype;
	}


	private boolean isNotAjaxRequest(FacesContext context){
		return true;//! AjaxRendererUtils.isAjaxRequest(context);
	}
	
	private void encodePrototype(FacesContext context) throws  IOException{
		if (isUsePrototype()) {
			InternetResourceBuilder.getInstance().createResource(null,PrototypeScript.class.getName()).encode(context,null);
		}

	}


	/**
	 * @param usePrototype the usePrototype to set
	 */
	public void setUsePrototype(boolean usePrototype) {
		this.usePrototype = usePrototype;
	}
	
	
}
