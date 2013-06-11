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
package org.ajax4jsf.gwt.client.ui;

import org.ajax4jsf.gwt.client.ComponentEntryPoint;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;

/**
 * Version of original {@link com.google.gwt.user.client.ui.Image} widget to load
 * images in packed to jar images from module public path. This image will work with
 * same relative url's in hosted mode and as packed component.
 * @author shura
 *
 */
public class JSFImage extends Image {

	/**
	 * 
	 */
	public JSFImage() {
		super();
		// TODO Auto-generated constructor stub
	}
	  /**
	   * Causes the browser to pre-fetch the image at a given URL.
	   * 
	   * @param url the URL of the image to be prefetched
	   */
	  public static void prefetch(String url) {
			String base = ComponentEntryPoint.getMetaProperty("base");
			if(null != base){
				url=base+GWT.getModuleName()+"/"+url;
			}
			Image.prefetch(url);
	  }

	/**
	 * @param arg0
	 */
	public JSFImage(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Image#getUrl()
	 */
	public String getUrl() {
		// TODO Auto-generated method stub
		String url = super.getUrl();
		String base = ComponentEntryPoint.getMetaProperty("base");
		if(null != base){
			url = url.substring((base+GWT.getModuleName()+"/").length());
		}
		return url;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Image#setUrl(java.lang.String)
	 */
	public void setUrl(String url) {
		String base = ComponentEntryPoint.getMetaProperty("base");
		if(null != base){
			url=base+GWT.getModuleName()+"/"+url;
		}
		super.setUrl(url);
	}

}
