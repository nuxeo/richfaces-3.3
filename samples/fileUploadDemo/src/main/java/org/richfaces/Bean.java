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

package org.richfaces;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

/**
 * @author $Autor$
 *
 */
public class Bean {
	
	private List data = new ArrayList();
	
	private boolean flag;
	
	private String fileTypes = "*";
	
	private Integer maxFiles = 5;
	
	private String width = "400";
	
	private String height = "210";
	
	public boolean isFlag() {
		return flag;
	}
	
	public List getFileList () {
	    return data;
	}


	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public void listener(UploadEvent event){
	    UploadItem item = event.getUploadItem();
	    System.out.println("File : '" + item.getFileName() + "' was uploaded");
	    if (item.isTempFile()) {
		File file = item.getFile();
		System.out.println("Absolute Path : '" + file.getAbsolutePath() + "'!");
		file.delete();
	    }else {
	    	try {
	    		byte[] bytes = item.getData();
	    		int numberOfBytes = 256;
	    		
	    		if (bytes.length > numberOfBytes) {
					System.out.println("First " + numberOfBytes + " bytes of uploaded file:");
					System.out.println(new String(bytes, 0, numberOfBytes));
	    		} else {
					System.out.println("Uploaded file contents:");
					System.out.println(new String(bytes, 0, bytes.length));
	    		}
			} catch (Exception e) {
				// TODO: handle exception
			}
		
	    }
	}

	
	/**
	 * @return the data
	 */
	public List getData() {
	    return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List data) {
	    this.data = data;
	}

	/**
	 * @return the fileTypes
	 */
	public String getFileTypes() {
	    return fileTypes;
	}

	/**
	 * @param fileTypes the fileTypes to set
	 */
	public void setFileTypes(String fileTypes) {
	    this.fileTypes = fileTypes;
	}

	/**
	 * @return the maxFiles
	 */
	public Integer getMaxFiles() {
	    return maxFiles;
	}

	/**
	 * @param maxFiles the maxFiles to set
	 */
	public void setMaxFiles(Integer maxFiles) {
	    this.maxFiles = maxFiles;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
	    return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(String width) {
	    this.width = width;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
	    return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(String height) {
	    this.height = height;
	}
	
	
}


