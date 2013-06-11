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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.validator.ValidatorException;

import org.richfaces.component.UIEditor;

/**
 * @author Alexandr Levkovsky
 *
 */
public class EditorBean {

	private Object value = "Some value....";
	private UIEditor editor;
	private boolean rendered = true;
	private String theme = "advanced";
	private String skin = "richfaces";
	private String language = "en";
	private boolean readonly = false;
	private String viewMode = "vision";
	private int width = 600;
	private int height = 600;
	private boolean required = false;
	private boolean invokeFlage = false;
	private boolean convertToObjectFail = false;
	
	public void reset() {
		value = "Some value....";
		rendered = true;
		theme = "advanced";
		skin = "richfaces";
		language = "en";
		readonly = false;
		viewMode = "vision";
		width = 600;
		height = 600;
		required = false;
		invokeFlage = false;
		convertToObjectFail = false;
	}
	
	public String changeTheme(){
		if("advanced".equals(theme)){
			theme = "simple";
		}else{
			theme = "advanced";
		}
		return null;
	}
	
	public String changeSkin(){
		if("richfaces".equals(skin)){
			skin = "o2k7";
		}else{
			skin = "richfaces";
		}
		return null;
	}
	
	public String changeSize(){
		width = 650;
		height = 650;
		return null;
	}
	
	public String changeValue(){
		setValue("Ajax value");
		return null;
	}
	
	public String submit(){
		invokeFlage = true;
		convertToObjectFail = true;
		return null;
	}
	
	public Converter getConvert() {
		return new Converter() {
			public Object getAsObject(FacesContext context,
					UIComponent component, String newValue)
					throws ConverterException {

				if (convertToObjectFail){
					throw new ConverterException(new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "Converter error",
							"Error while convert to Object"));
				}else{
					newValue = "Converter Value";
				}

				return newValue;
			}

			public String getAsString(FacesContext context,
					UIComponent component, Object value)
					throws ConverterException {
				String result = (value == null) ? "" : value.toString();
				return result;
			}
		};
	}

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		if (value != null) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Validation error",
					"Incorrect input"));
		}
	}
	
	/**
	 * @return the editor
	 */
	public UIEditor getEditor() {
		return editor;
	}

	/**
	 * @param editor the editor to set
	 */
	public void setEditor(UIEditor editor) {
		this.editor = editor;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * @return the value
	 */
	public Object getValueFromBinding() {
		return editor.getValue();
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the rendered
	 */
	public boolean isRendered() {
		return rendered;
	}

	/**
	 * @param rendered the rendered to set
	 */
	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	/**
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * @param theme the theme to set
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * @return the skin
	 */
	public String getSkin() {
		return skin;
	}

	/**
	 * @param skin the skin to set
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the readonly
	 */
	public boolean isReadonly() {
		return readonly;
	}

	/**
	 * @param readonly the readonly to set
	 */
	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	/**
	 * @return the viewMode
	 */
	public String getViewMode() {
		return viewMode;
	}

	/**
	 * @param viewMode the viewMode to set
	 */
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * @return the invokeFlage
	 */
	public boolean isInvokeFlage() {
		return invokeFlage;
	}

	/**
	 * @param invokeFlage the invokeFlage to set
	 */
	public void setInvokeFlage(boolean invokeFlage) {
		this.invokeFlage = invokeFlage;
	}

	/**
	 * @return the convertToObjectFail
	 */
	public boolean isConvertToObjectFail() {
		return convertToObjectFail;
	}

	/**
	 * @param convertToObjectFail the convertToObjectFail to set
	 */
	public void setConvertToObjectFail(boolean convertToObjectFail) {
		this.convertToObjectFail = convertToObjectFail;
	}
	
	
}
