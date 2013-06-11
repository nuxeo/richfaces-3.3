package rich;

public class Options {
	private static String[] CALENDAR_EVENT = { "onbeforedomupdate",
			"onchanged", "oncollapse", "oncomplete", "oncurrentdateselect",
			"ondatemouseout", "ondatemouseover", "ondateselect",
			"ondateselected", "onexpand", "oninputblur", "oninputchange",
			"oninputclick", "oninputfocus", "oninputkeydown",
			"oninputkeypress", "oninputkeyup", "oninputselect", "ontimeselect",
			"ontimeselected" };
	private static String[] CALENDAR_STYLE = { "style", "styleClass",
			"inputStyle" };
	private static String[] COLUMNS_STYLE = { "footerClass", "headerClass",
			"styleClass", "style" };
	private static String[] COMBOBOX_EVENT = { "onblur", "onchange", "onclick",
			"ondblclick", "onfocus", "onitemselected", "onkeydown",
			"onkeypress", "onkeyup", "onlistcall", "onmousedown",
			"onmousemove", "onmouseout", "onmouseover", "onmouseup", "onselect" };
	private static String[] COMBOBOX_STYLE = { "buttonClass",
			"buttonDisabledClass", "buttonDisabledStyle",
			"buttonInactiveClass", "buttonInactiveStyle", "buttonStyle",
			"inputClass", "inputDisabledClass", "inputDisabledStyle",
			"inputInactiveClass", "inputInactiveStyle", "itemClass",
			"inputStyle", "listClass", "listStyle" };
	private static String[] CONTEXTMENU_EVENT = { "oncollapse", "onexpand",
			"ongroupactivate", "onitemselect", "onmousemove", "onmouseout",
			"onmouseover" };
	private static String[] CONTEXTMENU_STYLE = { "disabledItemClass",
			"disabledItemStyle", "itemClass", "itemStyle", "selectItemClass",
			"selectItemStyle", "style", "styleClass" };
	private static String[] DATADEFINITIONLIST_STYLE = { "columnClasses",
			"rowClasses", "style", "styleClass" };
	private static String[] DATAORDEREDLIST_STYLE = { "columnClasses",
			"rowClasses", "style", "styleClass", "footerClass", "headerClass" };
	private static String[] DATAFILTERSLIDER_EVENT = { "onbeforedomupdate",
			"onchange", "onclick", "oncomplete", "ondblclick", "onerror",
			"onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove",
			"onmouseout", "onmouseover", "onmouseup", "onslide",
			"onSlideSubmit" };
	private static String[] DATAFILTERSLIDER_STYLE = { "styleClass",
			"rangeStyleClass", "trailerStyleClass", "style", "fieldStyleClass",
			"trackStyleClass", "handleStyleClass" };
	private static String[] DATASCROLLER_EVENT = { "onbeforedomupdate",
			"onclick", "oncomplete", "ondblclick", "onkeydown", "onkeypress",
			"onkeyup", "onmousedown", "onmousemove", "onmouseout",
			"onmouseover", "onmouseup" };
	private static String[] DATASCROLLER_STYLE = { "inactiveStyle",
			"inactiveStyleClass", "selectedStyle", "selectedStyleClass",
			"style", "styleClass", "tableStyle", "tableStyleClass" };
	private static String[] DATATABLE_EVENT = { "onclick", "ondblclick",
			"onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove",
			"onmouseout", "onmouseover", "onmouseup", "onRowClick",
			"onRowDblClick", "onRowMouseDown", "onRowMouseMove",
			"onRowMouseOut", "onRowMouseOver", "onRowMouseUp" };
	private static String[] DATATABLE_STYLE = { "captionClass", "rowClasses",
			"headerClass", "footerClass", "styleClass", "captionStyle",
			"columnClasses" };
	private static String[] DRAGANDDROP_EVENT = { "ondragenter", "ondragexit",
			"ondrop", "ondropend", "oncomplete", "onsubmit",
			"onbeforedomupdate", "oncomplete", "ondragend", "ondragstart",
			"onsubmit", "ondropout", "ondropover" };
	private static String[] DRAGANDDROP_STYLE = { "acceptClass", "rejectClass",
			"style", "styleClass" };
	private static String[] DROPDOWNMENU_EVENT = { "oncollapse", "onexpand",
			"ongroupactivate", "onitemselect", "onmousemove", "onmouseout",
			"onmouseover", "onbeforedomupdate", "onclick", "oncomplete",
			"onmousedown", "onmouseup", "onselect" };
	private static String[] DROPDOWNMENU_STYLE = { "iconClass", "iconStyle",
			"selectClass", "selectStyle", "style", "disabledItemClass",
			"disabledItemStyle", "itemClass", "itemStyle", "selectItemClass",
			"selectItemStyle", "styleClass" };
	private static String[] GMAP_EVENT = { "onclick", "ondblclick",
			"onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove",
			"onmouseout", "onmouseover", "onmouseup" };
	private static String[] GMAP_STYLE = { "style=", "styleClass" };
	private static String[] INPUTNUMBERSLIDER_EVENT = { "onblur", "onchange",
			"onclick", "ondblclick", "onerror", "onfocus", "onkeydown",
			"onkeypress", "onkeyup", "onmousedown", "onmousemove",
			"onmouseout", "onmouseover", "onmouseup", "onselect", "onslide" };
	private static String[] INPUTNUMBERSLIDER_STYLE = { "barClass", "barStyle",
			"handleClass", "inputClass", "handleSelectedClass", "inputSize",
			"styleClass", "inputStyle", "tipStyle", "style", "tipClass" };
	
	private boolean reDefault;
	private boolean reComponent;
	private boolean reProperty;
	private boolean reStraightforward;
	private boolean attribute;
	private boolean log;

	public boolean isLog() {
		return log;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	public Options() {
		log = false;
		reDefault = true;
		reComponent = true;
		reProperty = true;
		reStraightforward = true;
		attribute = false;
	}

	public boolean isReComponent() {
		return reComponent;
	}

	public void setReComponent(boolean reComponent) {
		this.reComponent = reComponent;
	}

	public boolean isReProperty() {
		return reProperty;
	}

	public void setReProperty(boolean reProperty) {
		this.reProperty = reProperty;
	}

	public boolean isReStraightforward() {
		return reStraightforward;
	}

	public void setReStraightforward(boolean reStraightforward) {
		this.reStraightforward = reStraightforward;
	}

	public boolean isReDefault() {
		return reDefault;
	}

	public void setReDefault(boolean reDefault) {
		this.reDefault = reDefault;
	}
	public boolean isAttribute() {
		return attribute;
	}

	public void setAttribute(boolean attribute) {
		this.attribute = attribute;
	}
}
