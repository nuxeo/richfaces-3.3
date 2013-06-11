/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author asmirnov
 *
 */
public class ListAction {
	
	private List data;
	
	private Set keys;
	
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

	public Set getKeys(){
    	return keys;
    }
    
    public String timer(){
    	if(null != data){
    		keys=new HashSet();
    		int random = (int)(Math.random()*10.0);
    		for(int i=0;i<random;i++){
    			Bean bean = (Bean) data.get(i);
    			bean.setText(bean.getText()+" X");
    			keys.add(new Integer(i));
    		}
    	}
    	return null;
    }


}
