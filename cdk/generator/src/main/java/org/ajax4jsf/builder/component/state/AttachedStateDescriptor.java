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

package org.ajax4jsf.builder.component.state;

import org.ajax4jsf.builder.model.JavaField;

/**
 * Save attached state,
 * @author Maksim Kaszynski
 *
 */
public class AttachedStateDescriptor extends ComponentStateDescriptor {
	
	private static final String restoreTemplate = "(%s)restoreAttachedState(context, %s)";
	
	private static final String saveTemplate = "saveAttachedState(context, %s)";
	
	
	public AttachedStateDescriptor(JavaField field) {
		super(field);
	}
	
	@Override
	public String saveStateCode() {
		return String.format(saveTemplate, getField().getName());
	}
	
	@Override
	public String restoreStateCode(String stateFragment) {
		return String.format(restoreTemplate, getField().getType().getSimpleName(), stateFragment);
	}
}
