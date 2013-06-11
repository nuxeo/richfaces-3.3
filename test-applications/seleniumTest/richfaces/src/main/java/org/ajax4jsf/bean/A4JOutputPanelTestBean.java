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
 * A4JOutputPanel Test Bean
 * @author Alexandr Levkovsky
 *
 */
public class A4JOutputPanelTestBean {
    
	private String layout;

	private Boolean ajaxRendered;

	private Boolean keepTransient;

	private String outputText;

	private UIComponent binding;
	
	public void init() {
		layout = "inline";
		ajaxRendered = false;
		outputText = "text";
		keepTransient = true;
		binding = null;
	}

	public void submit() {
		outputText = "changed";
	}

	public void setTransient() {
		binding.setTransient(true);
	}

	public void setOutputText(String outputText) {
		this.outputText = outputText;
	}

	public String getOutputText() {
		return outputText;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getLayout() {
		return layout;
	}

	public void setAjaxRendered(Boolean ajaxRendered) {
		this.ajaxRendered = ajaxRendered;
	}

	public Boolean getAjaxRendered() {
		return ajaxRendered;
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
