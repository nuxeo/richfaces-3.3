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

import javax.faces.component.UIComponent;

/**
 * A4JInclude Test Bean
 * @author Alexandr Levkovsky
 *
 */
public class A4JIncludeTestBean {
    
	private String layout;

	private Boolean keepTransient;

	private String text;

	private UIComponent binding;
	
	public void init() {
		layout = "inline";
		text = "text";
		keepTransient = true;
		binding = null;
	}

	public String submit() {
		return text;
	}

	public void setTransient() {
		binding.setTransient(true);
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getLayout() {
		return layout;
	}

	public void setKeepTransient(Boolean keepTransient) {
		this.keepTransient = keepTransient;
	}

	public Boolean getKeepTransient() {
		return keepTransient;
	}

	public String getTransientState() {
		return binding != null ? Boolean.toString(binding.isTransient()) : "";
	}

	public void setBinding(UIComponent binding) {
		this.binding = binding;
	}

	public UIComponent getBinding() {
		return binding;
	}
}
