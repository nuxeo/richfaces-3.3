/*
 *  Copyright
 *      Copyright (c) Exadel,Inc. 2006
 *      All rights reserved.
 *  
 *  History
 *      $Source: /cvs-master/intralinks-jsf-comps/web-projects/data-view-grid-demo/src/com/exadel/jsf/demo/datagrid/service/ImageStore.java,v $
 *      $Revision: 1.1 $ 
 */

package org.richfaces.demo.datagrid.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maksim Kaszynski
 *
 */
public class ImageStore{
	public ImageStore() {
		super();
		System.out.println("--> ImageStore.ImageStore()");
	}

	private Map <Object, String> images = new HashMap<Object, String>();


	/**
	 * @return the images
	 */
	public Map <Object, String> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(Map <Object, String> images) {
		this.images = images;
	}

	
}
