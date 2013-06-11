package org.richfaces.helloworld.domain.editor;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.html.HtmlEditor;
import org.richfaces.convert.seamtext.DefaultSeamTextConverter;
import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;

;

@Name("editor")
@Scope(ScopeType.SESSION)
public class Editor {
	private HtmlEditor htmlEditor;
	private String value;
	private String width;
	private String height;
	private String theme;
	private boolean autoResize;
	private boolean immediate;
	private boolean rendered;
	private boolean required;
	private boolean useSeamText;
	private String viewMode;
	private boolean readonly;
	private String tabindex;
	private String dialogType;
	private String language;
	private boolean configuration;

	// private String skin;

	public Editor() {
		// value = "It's easy to make *emphasis*, |monospace|, ~deleted text~,
		// super^scripts^ or _underlines_.";
		// value = "This is a |<tag attribute=\"value\" />| example.";
		value = "Collaboration-oriented websites require a human-friendly markup language for easy entry of formatted text in forum posts, wiki pages, blogs, comments, etc. Seam provides the s:formattedText control for display of formatted text that conforms to the Seam Text language. Seam Text is implemented using an ANTLR-based parser. You don't need to know anything about ANTLR to use it, however.";
		width = "400";
		height = "200";
		theme = "simple";
		autoResize = false;
		immediate = false;
		rendered = true;
		required = false;
		useSeamText = false;
		viewMode = "visual";
		readonly = false;
		tabindex = "1";
		dialogType = "window";
		language = "en";
		// skin = "default";
		configuration = false;
	}

	/*
	 * attach config file to editor
	 */
	public void useConfigFile(ValueChangeEvent e) {
		if (!configuration) {
			htmlEditor.setConfiguration("editorconfig");
		} else {
			htmlEditor.setConfiguration(null);
		}
	}

	/*
	 * Custom valueChangeListener
	 */
	public void valueChangeListener(ValueChangeEvent e) {
		System.out.println("!!! valueChangeListener work !!!");
	}

	/*
	 * Custom Converter for editor
	 */
	private Converter convert = new Converter() {
		public Object getAsObject(FacesContext context, UIComponent component,
				String newValue) throws ConverterException {

			System.out.println("!!! getAsObject work !!!");

			if (false)
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Converter error",
						"Error while convert to Object"));

			return newValue;
		}

		public String getAsString(FacesContext context, UIComponent component,
				Object value) throws ConverterException {

			System.out.println("!!! getAsString work !!!");

			if (false)
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Converter error",
						"Error while convert to String"));

			String result = (value == null) ? "" : value.toString();
			return result;
		}
	};

	public Converter getConvert() {
		return convert;
	}

	/*
	 * Custom Validator for Editor
	 */
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		System.out.println("!!! Validator work !!!");
		if (value != null) {

			if (false) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Validation error",
						"Incorrect input"));
			}
		}
	}

	/*
	 * Add test via reflection
	 */
	public void addHtmlEditor() {
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlEditor);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public HtmlEditor getHtmlEditor() {
		return htmlEditor;
	}

	public void setHtmlEditor(HtmlEditor htmlEditor) {
		this.htmlEditor = htmlEditor;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public boolean isAutoResize() {
		return autoResize;
	}

	public void setAutoResize(boolean autoResize) {
		this.autoResize = autoResize;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isUseSeamText() {
		return useSeamText;
	}

	public void setUseSeamText(boolean useSeamText) {
		this.useSeamText = useSeamText;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public String getTabindex() {
		return tabindex;
	}

	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	public String getDialogType() {
		return dialogType;
	}

	public void setDialogType(String dialogType) {
		this.dialogType = dialogType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean getConfiguration() {
		return configuration;
	}

	public void setConfiguration(boolean configuration) {
		this.configuration = configuration;
	}

	// public String getSkin() {
	// return skin;
	// }
	//
	// public void setSkin(String skin) {
	// this.skin = skin;
	// }
}
