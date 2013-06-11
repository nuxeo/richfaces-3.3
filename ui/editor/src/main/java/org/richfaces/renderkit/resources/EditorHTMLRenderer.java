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
package org.richfaces.renderkit.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;

import org.ajax4jsf.Messages;
import org.ajax4jsf.resource.BaseResourceRenderer;
import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.ResourceContext;

/**
 * Tiny_mce html resource pages renderer. 
 * Used for correcting scripts/images/css/irame src or href for normal loading by Resource Builder
 * 
 * @author Alexandr Levkovsky
 *
 */
public class EditorHTMLRenderer extends BaseResourceRenderer {

	/** Specific script resource name which will be used to get server suffix */
	private final static String SPECIFIC_SCRIPT_RESOURCE_NAME = "org/richfaces/renderkit/html/1$1.js";
	/** Specific xcss resource name which will be used to get server suffix */
	private final static String SPECIFIC_XCSS_RESOURCE_NAME = "org/richfaces/renderkit/html/1$1.xcss";

	/** Regular expression pattern for finding elements for replacing and correcting */
	private final static Pattern REGEXP = Pattern.compile("\\<(script|link|img|iframe)\\s+.*(?:src|href)\\s*=\\s*[\"']([^'\"]+)\\.([^\\'\"]+)[\"'][^>]*\\>", Pattern.CASE_INSENSITIVE);
	
	/** Script resource suffix due to servlet or filter mappings */
	private String scriptSuffix;
	/** XCSS resource suffix due to servlet or filter mappings */
	private String xcssSuffix;

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.BaseResourceRenderer#getCommonAttrs()
	 */
	@Override
	protected String[][] getCommonAttrs() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.BaseResourceRenderer#getHrefAttr()
	 */
	@Override
	protected String getHrefAttr() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.BaseResourceRenderer#getTag()
	 */
	@Override
	protected String getTag() {
		return "span";
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.ResourceRenderer#getContentType()
	 */
	public String getContentType() {
		return "text/html";
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.BaseResourceRenderer#send(org.ajax4jsf.resource.InternetResource, org.ajax4jsf.resource.ResourceContext)
	 */
	@Override
	public int send(InternetResource base, ResourceContext context)
			throws IOException {

		InputStream in = base.getResourceAsStream(context);
		OutputStream out = context.getOutputStream();
		if (null == in) {
			String message = Messages.getMessage(
					Messages.NO_INPUT_STREAM_ERROR, base.getKey());
			throw new IOException(message);
		}
		int total;
		
		// update scripts src
		total = updateAndSendResource(in, out);

		return total;
	}

	/**
	 * Method to correct included resources src
	 * 
	 * @param resource - html page to be checked 
	 */
	private int updateAndSendResource(InputStream in, OutputStream out) throws IOException {
		
		int total = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		//XXX nick - why we need BufferedWriter?
		
		//XXX nick - cannot be changed in runtime, cache in private field
		String scriptSuffix = getScriptSuffix();
		String cssSuffix = getXcssSuffix();
		
		Pattern pattern = REGEXP;
		
		try {
			while (br.ready()) {
				String line = br.readLine();
				
				 Matcher matcher = pattern.matcher(line);
				 if (matcher.find()){
					 String tagName = matcher.group(1);		 
				
					if(scriptSuffix != null && (tagName.toLowerCase().equals("script")||tagName.toLowerCase().equals("iframe"))){
						String path = matcher.group(2);
						String extension = matcher.group(3);
						line = line.replace(path + "." + extension, path + "." + extension + scriptSuffix);
					}
					
					if(scriptSuffix != null && tagName.toLowerCase().equals("img")){
						String path = matcher.group(2);
						String extension = matcher.group(3);
						if(path.indexOf("http://") == -1){
							line = line.replace(path  + "." + extension, path  + "." + extension + scriptSuffix);
						}
					}
					
					if(cssSuffix != null && tagName.toLowerCase().equals("link")){
						String path = matcher.group(2);
						String extension = matcher.group(3);
						line = line.replace(path  + "." + extension, path  + "." + (extension.charAt(0)=='c' ? "xcss" : "XCSS") + cssSuffix);
					}
				}
				line += "\n";
				out.write(line.getBytes());
				total += line.getBytes().length;
			}
		} finally {
			//XXX nick - close always flushes, no need to do that explicitly
			br.close();
			out.close();
		}
		return total;
	}

	/**
	 * Method to get exact script resource URI suffix
	 * 
	 * @return string with script resource URI suffix
	 */
	protected String getSriptMappingSuffix() {
		return getResourceSuffix(SPECIFIC_SCRIPT_RESOURCE_NAME);
	}
	
	/**
	 * Method to get exact xcss resource URI suffix
	 * 
	 * @return string with xcss resource URI suffix
	 */
	protected String getCssMappingSuffix() {
		return getResourceSuffix(SPECIFIC_XCSS_RESOURCE_NAME);
	}
	
	/**
	 * Method to get resource URI suffix which was added due to web.xml mappings
	 * 
	 * @param resourceName - name of the resource which should be checked  
	 * @return string with resource URI suffix which was added after resource name
	 */
	private String getResourceSuffix(String resourceName) {
		InternetResourceBuilder builder = InternetResourceBuilder.getInstance();
		InternetResource resource = builder.getResource(resourceName);
		String resourceUri = resource.getUri(FacesContext.getCurrentInstance(), null);
		String suffix = resourceUri.substring(resourceUri.indexOf(resourceName)
				+ resourceName.length());
		if (suffix != null && suffix.length() == 0) {
			suffix = null;
		}
		return suffix;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.BaseResourceRenderer#requireFacesContext()
	 */
	@Override
	public boolean requireFacesContext() {
		return true;
	}
	
	/**
	 * Getter for scriptSuffix
	 * 
	 * @return the scriptSuffix
	 */
	public String getScriptSuffix() {
		if(scriptSuffix == null){
			scriptSuffix = getSriptMappingSuffix();
		}
		return scriptSuffix;
	}

	/**
	 * Getter for xcssSuffix
	 * 
	 * @return the xcssSuffix
	 */
	public String getXcssSuffix() {
		if(xcssSuffix == null){
			xcssSuffix = getCssMappingSuffix();
		}
		return xcssSuffix;
	}

}
