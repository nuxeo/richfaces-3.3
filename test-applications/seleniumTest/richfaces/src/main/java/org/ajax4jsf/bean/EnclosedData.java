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

import java.util.ArrayList;
import java.util.List;

/**
 * @author asmirnov
 *
 */
public class EnclosedData {
	
	private List<RepeatData> repeatData;
	
	public EnclosedData() {
		repeatData = new ArrayList<RepeatData>();
		for(int i=0;i<5;i++){
			RepeatData data = new RepeatData();
			data.setText("Top row "+i);
			repeatData.add(data);
		}
	}

	/**
	 * @return the repeatData
	 */
	public List<RepeatData> getRepeatData() {
		return repeatData;
	}

	/**
	 * @param repeatData the repeatData to set
	 */
	public void setRepeatData(List<RepeatData> repeatData) {
		this.repeatData = repeatData;
	}
	
	

}
