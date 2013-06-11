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

package org.richfaces.taglib;

import java.util.Map;

import javax.faces.context.FacesContext;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */
final class RequestUniqueIdGenerator {

	private static final String GENERATOR_ATTRIBUTE = RequestUniqueIdGenerator.class.getName();
	
	public static Integer generateId(FacesContext context) {
		Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
		Integer id = (Integer) requestMap.get(GENERATOR_ATTRIBUTE);
		
		if (id == null) {
			Map<String, Object> attributes = context.getViewRoot().getAttributes();
			id = (Integer) attributes.get(GENERATOR_ATTRIBUTE);
			if (id == null) {
				//start from zero
				id = Integer.valueOf(0);
			} else {
				//increase id used for last view creation
				id = Integer.valueOf(id.intValue() + 1);
			}
			
			attributes.put(GENERATOR_ATTRIBUTE, id);
			requestMap.put(GENERATOR_ATTRIBUTE, id);
		} else {
			//already generated id for this request, reuse
		}
		
		return id;
	}
}
