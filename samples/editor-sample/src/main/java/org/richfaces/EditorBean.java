package org.richfaces;

import java.util.HashMap;
import java.util.Map;

import org.richfaces.component.UIEditor;

public class EditorBean {
	
	private String value = "Some value....";
	private UIEditor editor;
	private boolean rendered = true;
	private String theme = "advanced";
	private String skin = "richfaces";
	private String language = "en";
	private boolean readonly = false;
	private String viewMode = "vision";
	
	private Map<String, Object> dataMap;
	
	public EditorBean () {
		dataMap = new HashMap<String, Object>();
		dataMap.put("name1", "value1");
		dataMap.put("name2", false);
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String action1(){
		value += "1";
		return null;
	}
	
	public String action2(){
		editor.setValue(editor.getValue() + "1");
		return null;
	}
	
	public String changeRendered(){
		if(rendered) {
			rendered = false;
		}else{
			rendered = true;
		}
		return null;
	}
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public UIEditor getEditor() {
		return editor;
	}

	public void setEditor(UIEditor editor) {
		this.editor = editor;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}
	
	
}
