package org.richfaces;

import java.util.HashMap;
import java.util.Map;

import org.richfaces.component.UIEditor;

public class EditorBean {
	
	private String value = "It's easy to make *emphasis*, |monospace|,~deleted text~, super^scripts^ or _underlines_.\n\n" +
			"+This is a big heading\n" +
			"You /must/ have some text following a heading!\n\n" +
			"++This is a smaller heading\n" +
			"This is the first paragraph. We can split it across multiple" +
			"lines, but we must end it with a blank line.\n\n" +
			"This is the second paragraph.\n\n" +
			"An ordered list:\n\n" +
			"#first item\n" + "#second item\n" +
			"#and even the /third/ item\n\n" + "An unordered list:\n\n" +
			"=an item\n" + "=another item\n\n" +
			"[Slashdot - News for nerds, stuff that matters link=>http://slashdot.org]"
			;
	private UIEditor editor;
	private boolean rendered = true;
	private boolean useSeamText = true;
	
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

	public boolean isUseSeamText() {
		return useSeamText;
	}

	public void setUseSeamText(boolean useSeamText) {
		this.useSeamText = useSeamText;
	}
	
	
}
