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
import java.io.InputStream;
import java.io.Writer;
import java.nio.ByteBuffer;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

import org.ajax4jsf.css.CssCompressor;
import org.ajax4jsf.renderkit.RendererBase;
import org.ajax4jsf.renderkit.compiler.HtmlCompiler;
import org.ajax4jsf.renderkit.compiler.PreparedTemplate;
import org.ajax4jsf.renderkit.compiler.TemplateContext;
import org.richfaces.skin.SkinFactory;

/**
 * @author shura
 *
 */
public class TemplateCSSRenderer extends StyleRenderer {
	
	private static final String COMPILED_TEMPLATE_PROPERTY = "compiled-template";
	
	/** Parameter allows switch on/off comressing for css */
	private static final String COMPRESS_STYLE_PARAMETER = "org.ajax4jsf.COMPRESS_STYLE";

	private RendererBase renderer = new RendererBase() {

		protected Class<? extends UIComponent> getComponentClass() {
			// TODO Auto-generated method stub
			return UIComponent.class;
		}
		
	};
	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.BaseResourceRenderer#send(org.ajax4jsf.resource.InternetResource, org.ajax4jsf.resource.ResourceContext)
	 */
	public int send(InternetResource base, ResourceContext context) throws IOException {
		PreparedTemplate template = null;
		CountingOutputWriter countingOutputWriter = new CountingOutputWriter();
		template = getTemplate(base, context);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		boolean _CompressStyleOn = !"false".equals(facesContext.getExternalContext()
					   		.getInitParameter(COMPRESS_STYLE_PARAMETER));
		Writer writer = context.getWriter();
		int bytesLength;
		if(null != facesContext) {
			// Create responseWriter.
			String defaultRenderKitId = facesContext.getApplication().getDefaultRenderKitId();
			if (null == defaultRenderKitId) {
				defaultRenderKitId = RenderKitFactory.HTML_BASIC_RENDER_KIT;
			}
			RenderKitFactory renderKitFactory = (RenderKitFactory) FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
			RenderKit renderKit = renderKitFactory.getRenderKit(facesContext,defaultRenderKitId);
			// TODO - handle response encoding 
				
			ResponseWriter responseWriter = renderKit.createResponseWriter(countingOutputWriter,null,"UTF-8");
			facesContext.setResponseWriter(responseWriter);
			responseWriter.startDocument();
			
			// TODO - parameters and mock renderer/component ?
			// for first time, this template only allow skin or faces variables interaction
			template.encode(renderer,facesContext,null);
			responseWriter.endDocument();
			responseWriter.flush();
			responseWriter.close();
			
			if (_CompressStyleOn) {    
			    	CssCompressor compressor = new CssCompressor(countingOutputWriter.getContent()); // Compressing css document and printing result in response stream
			    	bytesLength = compressor.compress(writer, -1);
			    	writer.flush();
			    	writer.close();
			} else {
			    	writer.write(countingOutputWriter.getContent().toString());  // Write not compressed style content
			    	bytesLength = countingOutputWriter.getWritten();
			    	writer.flush();
			    	writer.close();
			}
			
		} else {
			throw new FacesException("FacesContext for resource from template "+base.getKey()+" is null");
		}
		return bytesLength;
	}

	/**
	 * @param base
	 * @param context
	 * @return
	 * @throws IOException
	 */
	public synchronized PreparedTemplate getTemplate(InternetResource base,
			ResourceContext context) throws IOException {
		PreparedTemplate template;
		// if(base.isCacheable()){
		template = (PreparedTemplate) base
				.getProperty(COMPILED_TEMPLATE_PROPERTY);
		// }
		if (null == template) {
			HtmlCompiler compiler = new HtmlCompiler();
			InputStream resourceAsStream = base.getResourceAsStream(context);
			if (null != resourceAsStream) {
				template = compiler.compile(resourceAsStream, base.getKey());
				base.setProperty(COMPILED_TEMPLATE_PROPERTY, template);
				resourceAsStream.close();
			} else {
				throw new IOException("CSS Template "+base.getKey()+" not found");
			}
		}
		return template;
	}
	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.BaseResourceRenderer#requireFacesContext()
	 */
	public boolean requireFacesContext() {
		return true;
	}

	public Object getData(InternetResource base, FacesContext context, Object data) {
		ResourceContext resourceContext = new FacesResourceContext(context);
		PreparedTemplate template;
		try {
			template = getTemplate(base, resourceContext);
		} catch (IOException e) {
			return null;
		}
		UIComponent component = (UIComponent) (data instanceof UIComponent ? data:null);
		TemplateContext templateContext = new TemplateContext(null,context,component);
		Object value = template.getValue(templateContext);
		if(null == value){
		    SkinFactory skinFactory = SkinFactory.getInstance();
		    int hashCode = skinFactory.getSkin(context).hashCode(context);
		    int baseHashCode = skinFactory.getBaseSkin(context).hashCode(context);
		    value = ByteBuffer.allocate(4*2).putInt(hashCode).putInt(baseHashCode).array();
		}
		return value;
	}

}
