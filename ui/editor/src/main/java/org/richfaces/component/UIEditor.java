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

/*
 * UIEditor.java		Date created: 16.09.2008
 * Last modified by: $Author: abelevich $
 * $Revision: 12089 $	$Date: 2009-01-05 11:01:39 +0100 (Mon, 05 Jan 2009) $
 */

package org.richfaces.component;

import javax.faces.FacesException;
import javax.faces.component.UIInput;
import javax.faces.convert.Converter;

/**
 * JSF component class
 * @author Alexandr Levkovsky
 *
 */
public abstract class UIEditor extends UIInput {

	/** Editor component type */
	public static final String COMPONENT_TYPE = "org.richfaces.Editor";

	/** Editor component family */
	public static final String COMPONENT_FAMILY = "org.richfaces.Editor";
	
	/** Id suffix of textarea which used as target element for tinyMCE  scripts*/
	public static final String EDITOR_TEXT_AREA_ID_SUFFIX = "TextArea";
	
	private Converter defaultSeamTextConverter = null;
	
	public abstract void setType(String type);

	public abstract String getType();

	public abstract Integer getWidth();

	public abstract void setWidth(Integer width);

	public abstract Integer getHeight();

	public abstract void setHeight(Integer height);
	
	public abstract void setTheme(String theme);

	public abstract String getTheme();
	
	public abstract void setUseSeamText(boolean useSeamText);

	public abstract boolean isUseSeamText();
		
	public abstract void setPlugins(String plugins);

	public abstract String getPlugins();
	
	public abstract void setLanguage(String language);

	public abstract String getLanguage();
	
	public abstract void setTabindex(String tabindex);

	public abstract String getTabindex();
	
	public abstract void setOninit(String oninit);

	public abstract String getOninit();
	
	public abstract void setOnchange(String onchange);

	public abstract String getOnchange();
	
	public abstract void setOnsave(String onsave);

	public abstract String getOnsave();
	
	public abstract void setOnsetup(String onsetup);

	public abstract String getOnsetup();
	
	public abstract void setAutoResize(boolean autoResize);

	public abstract boolean isAutoResize();
	
	public abstract void setReadonly(boolean readonly);

	public abstract boolean isReadonly();
	
	public abstract void setConfiguration(String configuration);

	public abstract String getConfiguration();
	
	public abstract String getCustomPlugins();
	
	public abstract void setCustomPlugins(String customPlugins);
	
	public abstract void setSkin(String skin);

	public abstract String getSkin();
	
	public abstract void setViewMode(String viewMode);

	public abstract String getViewMode();
	
	public abstract void setDialogType(String dialogType);

	public abstract String getDialogType();

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponentBase#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIOutput#getConverter()
	 */
	@Override
	public Converter getConverter() {
		Converter converter = super.getConverter();
		if(isUseSeamText() && converter == null) {
			if(defaultSeamTextConverter == null) {
				try {
	//				create default seam text converter
					Class clazz = Thread.currentThread().getContextClassLoader().loadClass("org.richfaces.convert.seamtext.DefaultSeamTextConverter");
					defaultSeamTextConverter = (Converter) clazz.newInstance();
				} catch (Exception e) {
					throw new FacesException(e);
				}
			} 
			converter = defaultSeamTextConverter;
		}
		return converter;
	}
	
}
