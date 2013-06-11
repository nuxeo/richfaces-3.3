/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces.rf3899;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.richfaces.component.UITree;

/**
 */

public class Bean {

	private static final String KEY_NAME = Bean.class.getName();
	private int requestCounter = -1;
	
	private int getRequestNumber() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, Object> map = context.getExternalContext().getRequestMap();
		if (map.get(KEY_NAME) == null) {
			map.put(KEY_NAME, Boolean.TRUE);
			return ++requestCounter;
		} else {
			return requestCounter;
		}
	}
	
	public Boolean adviseNodeOpened(UITree tree) {
		int num = getRequestNumber();
		
		if (num < 1) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}
}
