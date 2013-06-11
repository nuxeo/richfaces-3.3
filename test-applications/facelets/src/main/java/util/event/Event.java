package util.event;

import javax.faces.model.SelectItem;
import java.util.ArrayList;

/**
 * User: ayanul Date: Feb 11, 2008 Time: 10:15:14 AM
 */
public class Event {
	private String onmouseup;
    private String ontabenter;
    private String ontableave;
    private String onLoadMap;
    private String onselectionchange;
    private String ondownclick;
    private String onupclick;
    private String oninit;
    private String ongroupactivate;
    private String onitemselect;
    private String onsubmit;
    private String onRowClick;
    private String onRowDblClick;
    private String onRowMouseDown;
    private String onRowMouseMove;
    private String onRowMouseOut;
    private String onRowMouseOver;
    private String onRowMouseUp;
    private String onselect;
    private String onchange;
    private String onfocus;
    private String onblur;
    private String onclick;
    private String oncollapse;
    private String oncomplete;
    private String onclear;    
    private String ondblclick;
    private String ondragend;
    private String ondragenter;
    private String ondragexit;
    private String ondragstart;
    private String ondrop;
    private String ondropend;
    private String onexpand;
    private String onkeydown;
    private String onkeypress;
    private String onkeyup;
    private String onmousedown;
    private String onmousemove;
    private String onmouseout;
    private String onmouseover;
    private String onselected;
    private String onhide;
    private String onshow;
    private String onlistcall;
    private String onitemselected;
    private String oninputblur;
    private String oninputchange;
    private String oninputclick;
    private String oninputfocus;
    private String oninputkeydown;
    private String oninputkeypress;
    private String oninputkeyup;
    private String oninputselect;
    private String ondateselected;
    private String ondateselect;
    private String ontimeselect;
    private String ontimeselected;
    private String onchanged;
    private String ondatemouseover;
    private String ondatemouseout;
    private String oncurrentdateselect;
    private String onbeforedomupdate;
    private String onslide;
    private String ondropover;
    private String ondropout;
    private String onerror;
    private String ontopclick;
    private String onbottomclick;
    private String onorderchanged;
    private String onmove;
    private String onheaderclick;
    private String onmaskclick;
    private String onresize;
    private String onmaskmouseover;
    private String onmaskmouseout;
    private String onmaskmousemove;
    private String onmaskmouseup;
    private String onmaskmousedown;
    private String onmaskdblclick;
    private String onmaskcontextmenu;
    private String onitemchange;
    private String onitemhover;
    private String ongroupexpand;
    private String oncontextmenu;
    private String ongroupcollapse;
    private String onSlideSubmit;
    private String oneditactivated;
    private String oneditactivation;
    private String oninputdblclick;
    private String oninputmousedown;
    private String oninputmousemove;
    private String oninputmouseout;
    private String oninputmouseover;
    private String oninputmouseup;
    private String onsizerejected;
    private String ontyperejected;
    private String onupload;
    private String onuploadcanceled;
    private String onuploadcomplete;    
    private String onviewactivated;
    private String onviewactivation;
    private String onbeforehide;
    private String onbeforeshow;
    private String opened;
    private String onobjectchange;
    private String onadd;
    /* Events for toolBar */
    private String onitemclick;
    private String onitemdblclick;
    private String onitemkeydown;
    private String onitemkeypress;
    private String onitemkeyup;
    private String onitemmousedown;
    private String onitemmousemove;
    private String onitemmouseout;
    private String onitemmouseover;
    private String onitemmouseup;
    private String onsetup;
    private String onsave;
    private String onsizeexceeded;
    private String onlistchanged; 
    private String onload;
    private String onunload;

	public String getOnlistchanged() {
		return onlistchanged;
	}

	public void setOnlistchanged(String onlistchanged) {
		this.onlistchanged = onlistchanged;
	}

	public String getOnsizeexceeded() {
		return onsizeexceeded;
	}

	public void setOnsizeexceeded(String onsizeexceeded) {
		this.onsizeexceeded = onsizeexceeded;
	}

	// showEvent('onkeypressInputID', 'onkeypress work!')
    public Event() {
    	onlistchanged = "showEvent('formID:infoSubview:onlistchangedInputID', 'onlistchanged work!')";
    	onsave = "showEvent('formID:infoSubview:onsaveInputID', 'onsave work!')";
    	onsetup = "showEvent('formID:infoSubview:onsetupInputID', 'onsetup work!')";
    	onitemclick = "showEvent('formID:infoSubview:onitemclickInputID', 'onitemclick work!')";
    	onitemdblclick = "showEvent('formID:infoSubview:onitemdblclickInputID', 'onitemdblclick work!')";
    	onitemkeydown = "showEvent('formID:infoSubview:onitemkeydownInputID', 'onitemkeydown work!')";
    	onitemkeypress = "showEvent('formID:infoSubview:onitemkeypressInputID', 'onitemkeypress work!')";
    	onitemkeyup = "showEvent('formID:infoSubview:onitemkeyupInputID', 'onitemkeyup work!')";
    	onitemmousedown = "showEvent('formID:infoSubview:onitemmousedownInputID', 'onitemmousedown work!')";
    	onitemmousemove = "showEvent('formID:infoSubview:onitemmousemoveInputID', 'onitemmousemove work!')";
    	onitemmouseout = "showEvent('formID:infoSubview:onitemmouseoutInputID', 'onitemmouseout work!')";
    	onitemmouseover = "showEvent('formID:infoSubview:onitemmouseoverInputID', 'onitemmouseover work!')";
    	onitemmouseup = "showEvent('formID:infoSubview:onitemmouseupInputID', 'onitemmouseup work!')";
    	/* --- */
		onobjectchange = "showEvent('formID:infoSubview:onobjectchangeInputID', 'onobjectchange work!')";
    	onLoadMap = "showEvent('formID:infoSubview:onLoadMapInputID', 'onLoadMap work!')";
    	opened = "showEvent('formID:infoSubview:openedInputID', 'opened work!')";
    	onRowClick = "showEvent('formID:infoSubview:onRowClickInputID', 'onRowClick work!')";
    	onRowDblClick = "showEvent('formID:infoSubview:onRowDblClickInputID', 'onRowDblClick work!')";
    	onRowMouseDown = "showEvent('formID:infoSubview:onRowMouseDownInputID', 'onRowMouseDown work!')";
    	onRowMouseMove = "showEvent('formID:infoSubview:onRowMouseMoveInputID', 'onRowMouseMove work!')";
    	onRowMouseOut = "showEvent('formID:infoSubview:onRowMouseOutInputID', 'onRowMouseOut work!')";
    	onRowMouseOver = "showEvent('formID:infoSubview:onRowMouseOverInputID', 'onRowMouseOver work!')";
    	onRowMouseUp = "showEvent('formID:infoSubview:onRowMouseUpInputID', 'onRowMouseUp work!')";
    	onbeforedomupdate = "showEvent('formID:infoSubview:onbeforedomupdateInputID', 'onbeforedomupdate work!')";
    	onblur = "showEvent('formID:infoSubview:onblurInputID', 'onblur work!')";
    	onbottomclick = "showEvent('formID:infoSubview:onbottomclickInputID', 'onbottomclick work!')";
    	onchange = "showEvent('formID:infoSubview:onchangeInputID', 'onchange work!')";
    	onchanged = "showEvent('formID:infoSubview:onchangedInputID', 'onchanged work!')";
    	onclick = "showEvent('formID:infoSubview:onclickInputID', 'onclick work!')";
    	oncollapse = "showEvent('formID:infoSubview:oncollapseInputID', 'oncollapse work!')";
    	oncomplete = "showEvent('formID:infoSubview:oncompleteInputID', 'oncomplete work!')";
    	oncontextmenu = "showEvent('formID:infoSubview:oncontextmenuInputID', 'oncontextmenu work!')";
    	onclear = "showEvent('formID:infoSubview:onclearInputID', 'onclear work!')";
    	oncurrentdateselect = "showEvent('formID:infoSubview:oncurrentdateselectInputID', 'oncurrentdateselect work!')";
    	ondatemouseout = "showEvent('formID:infoSubview:ondatemouseoutInputID', 'ondatemouseout work!')";
    	ondatemouseover = "showEvent('formID:infoSubview:ondatemouseoverInputID', 'ondatemouseover work!')";
    	ondateselect = "showEvent('formID:infoSubview:ondateselectInputID', 'ondateselect work!')";
    	ondateselected = "showEvent('formID:infoSubview:ondateselectedInputID', 'ondateselected work!')";
    	ondblclick = "showEvent('formID:infoSubview:ondblclickInputID', 'ondblclick work!')";
    	ondownclick = "showEvent('formID:infoSubview:ondownclickInputID', 'ondownclick work!')";
    	ondragend = "showEvent('formID:infoSubview:ondragendInputID', 'ondragend work!')";
    	ondragenter = "showEvent('formID:infoSubview:ondragenterInputID', 'ondragenter work!')";
    	ondragexit = "showEvent('formID:infoSubview:ondragexitInputID', 'ondragexit work!')";
    	ondragstart = "showEvent('formID:infoSubview:ondragstartInputID', 'ondragstart work!')";
    	ondrop = "showEvent('formID:infoSubview:ondropInputID', 'ondrop work!')";
    	ondropend = "showEvent('formID:infoSubview:ondropendInputID', 'ondropend work!')";
    	ondropout = "showEvent('formID:infoSubview:ondropoutInputID', 'ondropout work!')";
    	ondropover = "showEvent('FormID:infoSubview:ondropoverInputID', 'ondropover work!')";
    	onerror = "showEvent('formID:infoSubview:onerrorInputID', 'onerror work!')";
    	onexpand = "showEvent('formID:infoSubview:onexpandInputID', 'onexpand work!')";
    	onfocus = "showEvent('formID:infoSubview:onfocusInputID', 'onfocus work!')";
    	ongroupactivate = "showEvent('formID:infoSubview:ongroupactivateInputID', 'ongroupactivate work!')";
    	ongroupexpand = "showEvent('formID:infoSubview:ongroupexpandInputID', 'ongroupexpand work!')";
    	onheaderclick = "showEvent('formID:infoSubview:onheaderclickInputID', 'onheaderclick work!')";
    	onhide = "showEvent('formID:infoSubview:onhideInputID', 'onhide work!')";
    	oninit = "showEvent('formID:infoSubview:oninitInputID', 'oninit work!')";
    	oninputblur = "showEvent('formID:infoSubview:oninputblurInputID', 'oninputblur work!')";
    	oninputchange = "showEvent('formID:infoSubview:oninputchangeInputID', 'oninputchange work!')";
    	oninputclick = "showEvent('formID:infoSubview:oninputclickInputID', 'oninputclick work!')";
    	oninputfocus = "showEvent('formID:infoSubview:oninputfocusInputID', 'oninputfocus work!')";
    	oninputkeydown = "showEvent('formID:infoSubview:oninputkeydownInputID', 'oninputkeydown work!')";
    	oninputkeypress = "showEvent('formID:infoSubview:oninputkeypressInputID', 'oninputkeypress work!')";
    	oninputkeyup = "showEvent('formID:infoSubview:oninputkeyupInputID', 'oninputkeyup work!')";
    	oninputselect = "showEvent('formID:infoSubview:oninputselectInputID', 'oninputselect work!')";
    	onitemchange = "showEvent('formID:infoSubview:onitemchangeInputID', 'onitemchange work!')";
    	onitemhover = "showEvent('formID:infoSubview:onitemhoverInputID', 'onitemhover work!')";
    	onitemselect = "showEvent('formID:infoSubview:onitemselectInputID', 'onitemselect work!')";
    	onitemselected = "showEvent('formID:infoSubview:onitemselectedInputID', 'onitemselected work!')";
    	onkeydown = "showEvent('formID:infoSubview:onkeydownInputID', 'onkeydown work!')";
    	onkeypress = "showEvent('formID:infoSubview:onkeypressInputID', 'onkeypress work!')";
    	onkeyup = "showEvent('formID:infoSubview:onkeyupInputID', 'onkeyup work!')";
    	onlistcall = "showEvent('formID:infoSubview:onlistcallInputID', 'onlistcall work!')";
    	onmaskclick = "showEvent('formID:infoSubview:onmaskclickInputID', 'onmaskclick work!')";
    	onmaskcontextmenu = "showEvent('formID:infoSubview:onmaskcontextmenuInputID', 'onmaskcontextmenu work!')";
    	onmaskdblclick = "showEvent('formID:infoSubview:onmaskdblclickInputID', 'onmaskdblclick work!')";
    	onmaskmousedown = "showEvent('formID:infoSubview:onmaskmousedownInputID', 'onmaskmousedown work!')";
    	onmaskmousemove = "showEvent('formID:infoSubview:onmaskmousemoveInputID', 'onmaskmousemove work!')";
    	onmaskmouseout = "showEvent('formID:infoSubview:onmaskmouseoutInputID', 'onmaskmouseout work!')";
    	onmaskmouseover = "showEvent('formID:infoSubview:onmaskmouseoverInputID', 'onmaskmouseover work!')";
    	onmaskmouseup = "showEvent('formID:infoSubview:onmaskmouseupInputID', 'onmaskmouseup work!')";
    	onmousedown = "showEvent('formID:infoSubview:onmousedownInputID', 'onmousedown work!')";
    	onmousemove = "showEvent('formID:infoSubview:onmousemoveInputID', 'onmousemove work!')";
    	onmouseout = "showEvent('formID:infoSubview:onmouseoutInputID', 'onmouseout work!')";
    	onmouseover = "showEvent('formID:infoSubview:onmouseoverInputID', 'onmouseover work!')";
    	onmouseup = "showEvent('formID:infoSubview:onmouseupInputID', 'onmouseup work!')";
    	onmove = "showEvent('formID:infoSubview:onmoveInputID', 'onmove work!')";
    	onorderchanged = "showEvent('formID:infoSubview:onorderchangedInputID', 'onorderchanged work!')";
    	onresize = "showEvent('formID:infoSubview:onresizeInputID', 'onresize work!')";
    	onselect = "showEvent('formID:infoSubview:onselectInputID', 'onselect work!')";
    	onselected = "showEvent('formID:infoSubview:onselectedInputID', 'onselected work!')";
    	onselectionchange = "showEvent('formID:infoSubview:onselectionchangeInputID', 'onselectionchange work!')";
    	onshow = "showEvent('formID:infoSubview:onshowInputID', 'onshow work!')";
    	onslide = "showEvent('formID:infoSubview:onslideInputID', 'onslide work!')";
    	onsubmit = "showEvent('formID:infoSubview:onsubmitInputID', 'onsubmit work!')";
    	ontabenter = "showEvent('formID:infoSubview:ontabenterInputID', 'ontabenter work!')";
    	ontableave = "showEvent('formID:infoSubview:ontableaveInputID', 'ontableave work!')";
    	ontimeselect = "showEvent('formID:infoSubview:ontimeselectInputID', 'ontimeselect work!')";
    	ontimeselected = "showEvent('formID:infoSubview:ontimeselectedInputID', 'ontimeselected work!')";
    	ontopclick = "showEvent('formID:infoSubview:ontopclickInputID', 'ontopclick work!')";
    	onupclick = "showEvent('formID:infoSubview:onupclickInputID', 'onupclick work!')";
    	ongroupcollapse = "showEvent('formID:infoSubview:ongroupcollapseInputID', 'ongroupcollapse work!')";
    	onSlideSubmit = "showEvent('formID:infoSubview:onSlideSubmitInputID', 'onSlideSubmit work!')";
    	oneditactivated = "showEvent('formID:infoSubview:oneditactivatedInputID', 'oneditactivated work!')";
    	oneditactivation = "showEvent('formID:infoSubview:oneditactivationInputID', 'oneditactivation work!')";
    	oninputdblclick = "showEvent('formID:infoSubview:oninputdblclickInputID', 'oninputdblclick work!')";
    	oninputmousedown = "showEvent('formID:infoSubview:oninputmousedownInputID', 'oninputmousedown work!')";
    	oninputmousemove = "showEvent('formID:infoSubview:oninputmousemoveInputID', 'oninputmousemove work!')";
    	oninputmouseout = "showEvent('formID:infoSubview:oninputmouseoutInputID', 'oninputmouseout work!')";
    	oninputmouseover = "showEvent('formID:infoSubview:oninputmouseoverInputID', 'oninputmouseover work!')";
    	oninputmouseup = "showEvent('formID:infoSubview:oninputmouseupInputID', 'oninputmouseup work!')";
    	onsizerejected = "showEvent('formID:infoSubview:onsizerejectedInputID', 'onsizerejected work!')";
    	ontyperejected = "showEvent('formID:infoSubview:ontyperejectedInputID', 'ontyperejected work!')";    	
    	onupload = "showEvent('formID:infoSubview:onuploadInputID', 'onupload work!')";
    	onuploadcanceled = "showEvent('formID:infoSubview:onuploadcanceledInputID', 'onuploadcanceled work!')";
    	onuploadcomplete = "showEvent('formID:infoSubview:onuploadcompleteInputID', 'onuploadcomplete work!')";    	
    	onviewactivated = "showEvent('formID:infoSubview:onviewactivatedInputID', 'onviewactivated work!')";
    	onviewactivation = "showEvent('formID:infoSubview:onviewactivationInputID', 'onviewactivation work!')";
    	onbeforehide = "showEvent('formID:infoSubview:onbeforehideInputID', 'onbeforehide work!')";
    	onbeforeshow = "showEvent('formID:infoSubview:onbeforeshowInputID', 'onbeforeshow work!')";
    	onadd = "showEvent('formID:infoSubview:onaddInputID', 'onadd work!')";
    	onsizeexceeded = "showEvent('formID:infoSubview:onsizeexceededInputID', 'onsizeexceeded work!')";
    	onload = "showEvent('formID:infoSubview:onloadInputID', 'onload work!')";
    	onunload = "showEvent('formID:infoSubview:onunloadInputID', 'onunload work!')";
    }

    public String getOncontextmenu() {
		return oncontextmenu;
	}

	public void setOncontextmenu(String oncontextmenu) {
		this.oncontextmenu = oncontextmenu;
	}

	public String getOngroupexpand() {
		return ongroupexpand;
	}

	public void setOngroupexpand(String ongroupexpand) {
		this.ongroupexpand = ongroupexpand;
	}

	public String getOnitemhover() {
		return onitemhover;
	}

	public void setOnitemhover(String onitemhover) {
		this.onitemhover = onitemhover;
	}

	public String getOnitemchange() {
		return onitemchange;
	}

	public void setOnitemchange(String onitemchange) {
		this.onitemchange = onitemchange;
	}

	public String getOnheaderclick() {
		return onheaderclick;
	}

	public void setOnheaderclick(String onheaderclick) {
		this.onheaderclick = onheaderclick;
	}

	public String getOnmove() {
		return onmove;
	}

	public void setOnmove(String onmove) {
		this.onmove = onmove;
	}

	public String getOntopclick() {
		return ontopclick;
	}

	public void setOntopclick(String ontopclick) {
		this.ontopclick = ontopclick;
	}

	public String getOnerror() {
		return onerror;
	}

	public void setOnerror(String onerror) {
		this.onerror = onerror;
	}

	public String getOndropover() {
		return ondropover;
	}

	public void setOndropover(String ondropover) {
		this.ondropover = ondropover;
	}

	public String getOnslide() {
		return onslide;
	}

	public void setOnslide(String onslide) {
		this.onslide = onslide;
	}

	public String getOnbeforedomupdate() {
		return onbeforedomupdate;
	}

	public void setOnbeforedomupdate(String onbeforedomupdate) {
		this.onbeforedomupdate = onbeforedomupdate;
	}

    public ArrayList<SelectItem> getEvent(String event) {
        ArrayList<SelectItem> arr = new ArrayList<SelectItem>();
        arr.add(new SelectItem("showEvent('" + event + "', '" + event + " work!'"));
        arr.add(new SelectItem("alert('" + event + "')"));
        arr.add(new SelectItem(""));
        return arr;

    }

     public ArrayList<SelectItem> getOntabenterSI() {
        return getEvent("ontabenter");
    }

    public ArrayList<SelectItem> getOntableaveSI() {
        return getEvent("ontableave");
    }

    public ArrayList<SelectItem> getOnLoadMapSI() {
        return getEvent("onLoadMap");
    }

    public ArrayList<SelectItem> getOnselectionchangeSI() {
        return getEvent("onselectionchange");
    }

    public ArrayList<SelectItem> getOndownclickSI() {
        return getEvent("ondownclick");
    }

    public ArrayList<SelectItem> getOnupclickSI() {
        return getEvent("onupclick");
    }

    public ArrayList<SelectItem> getOninitSI() {
        return getEvent("oninit");
    }

    public ArrayList<SelectItem> getOngroupactivateSI() {
        return getEvent("ongroupactivate");
    }

    public ArrayList<SelectItem> getOnitemselectSI() {
        return getEvent("onitemselect");
    }

    public ArrayList<SelectItem> getOnsubmitSI() {
        return getEvent("onsubmit");
    }

    public ArrayList<SelectItem> getOnRowClickSI() {
        return getEvent("onRowClick");
    }

    public ArrayList<SelectItem> getOnRowDblClickSI() {
        return getEvent("onRowDblClick");
    }

    public ArrayList<SelectItem> getOnRowMouseDownSI() {
        return getEvent("onRowMouseDown");
    }

    public ArrayList<SelectItem> getOnRowMouseMoveSI() {
        return getEvent("onRowMouseMove");
    }

    public ArrayList<SelectItem> getOnRowMouseOutSI() {
        return getEvent("onRowMouseOut");
    }

    public ArrayList<SelectItem> getOnRowMouseOverSI() {
        return getEvent("onRowMouseOut");
    }

    public ArrayList<SelectItem> getOnRowMouseUpSI() {
        return getEvent("onRowMouseUp");
    }

    public ArrayList<SelectItem> getOnselectSI() {
        return getEvent("onselect");
    }

    public ArrayList<SelectItem> getOnchangeSI() {
        return getEvent("onchange");
    }

    public ArrayList<SelectItem> getOnfocusSI() {
        return getEvent("onfocus");
    }

    public ArrayList<SelectItem> getOnblurSI() {
        return getEvent("onblur");
    }

    public ArrayList<SelectItem> getOnclickSI() {
        return getEvent("onclick");
    }

    public ArrayList<SelectItem> getOncollapseSI() {
        return getEvent("oncollapse");
    }

    public ArrayList<SelectItem> getOncompleteSI() {
        return getEvent("oncomplete");
    }

    public ArrayList<SelectItem> getOndblclickSI() {
        return getEvent("ondblclick");
    }

    public ArrayList<SelectItem> getOndragendSI() {
        return getEvent("ondragend");
    }

    public ArrayList<SelectItem> getOndragenterSI() {
        return getEvent("ondragenter");
    }

    public ArrayList<SelectItem> getOndragexitSI() {
        return getEvent("ondragexit");
    }

    public ArrayList<SelectItem> getOndragstartSI() {
        return getEvent("ondragstart");
    }

    public ArrayList<SelectItem> getOndropSI() {
        return getEvent("ondrop");
    }

    public ArrayList<SelectItem> getOndropendSI() {
        return getEvent("ondropend");
    }

    public ArrayList<SelectItem> getOnexpandSI() {
        return getEvent("onexpand");
    }

    public ArrayList<SelectItem> getOnkeydownSI() {
        return getEvent("onkeydown");
    }

    public ArrayList<SelectItem> getOnkeypressSI() {
        return getEvent("onkeypress");
    }

    public ArrayList<SelectItem> getOnkeyupSI() {
        return getEvent("onkeyup");
    }

    public ArrayList<SelectItem> getOnmousedownSI() {
        return getEvent("onmousedown");
    }

    public ArrayList<SelectItem> getOnmousemoveSI() {
        return getEvent("onmousemove");
    }

    public ArrayList<SelectItem> getOnmouseoutSI() {
        return getEvent("onmouseout");
    }

    public ArrayList<SelectItem> getOnmouseoverSI() {
        return getEvent("onmouseover");
    }

    public ArrayList<SelectItem> getOnselectedSI() {
        return getEvent("onselected");
    }

    public ArrayList<SelectItem> getOnhideSI() {
        return getEvent("onhide");
    }

    public ArrayList<SelectItem> getOnshowSI() {
        return getEvent("onshow");
    }
	
    public ArrayList<SelectItem> getOnmouseupSI() {
        return getEvent("onmouseup");
    }

    public ArrayList<SelectItem> getOnlistcallSI() {
        return getEvent("onlistcall");
    }

    public ArrayList<SelectItem> getOnitemselectedSI() {
        return getEvent("onitemselected");
    }
    
    public ArrayList<SelectItem> getOninputblurSI() {
        return getEvent("oninputblur");
    }

	public String getOnmouseup() {
		return onmouseup;
	}

	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	public String getOntabenter() {
		return ontabenter;
	}

	public void setOntabenter(String ontabenter) {
		this.ontabenter = ontabenter;
	}

	public String getOntableave() {
		return ontableave;
	}

	public void setOntableave(String ontableave) {
		this.ontableave = ontableave;
	}

	public String getOnLoadMap() {
		return onLoadMap;
	}

	public void setOnLoadMap(String onLoadMap) {
		this.onLoadMap = onLoadMap;
	}

	public String getOnselectionchange() {
		return onselectionchange;
	}

	public void setOnselectionchange(String onselectionchange) {
		this.onselectionchange = onselectionchange;
	}

	public String getOndownclick() {
		return ondownclick;
	}

	public void setOndownclick(String ondownclick) {
		this.ondownclick = ondownclick;
	}

	public String getOnupclick() {
		return onupclick;
	}

	public void setOnupclick(String onupclick) {
		this.onupclick = onupclick;
	}

	public String getOninit() {
		return oninit;
	}

	public void setOninit(String oninit) {
		this.oninit = oninit;
	}

	public String getOngroupactivate() {
		return ongroupactivate;
	}

	public void setOngroupactivate(String ongroupactivate) {
		this.ongroupactivate = ongroupactivate;
	}

	public String getOnitemselect() {
		return onitemselect;
	}

	public void setOnitemselect(String onitemselect) {
		this.onitemselect = onitemselect;
	}

	public String getOnsubmit() {
		return onsubmit;
	}

	public void setOnsubmit(String onsubmit) {
		this.onsubmit = onsubmit;
	}

	public String getOnRowClick() {
		return onRowClick;
	}

	public void setOnRowClick(String onRowClick) {
		this.onRowClick = onRowClick;
	}

	public String getOnRowDblClick() {
		return onRowDblClick;
	}

	public void setOnRowDblClick(String onRowDblClick) {
		this.onRowDblClick = onRowDblClick;
	}

	public String getOnRowMouseDown() {
		return onRowMouseDown;
	}

	public void setOnRowMouseDown(String onRowMouseDown) {
		this.onRowMouseDown = onRowMouseDown;
	}

	public String getOnRowMouseMove() {
		return onRowMouseMove;
	}

	public void setOnRowMouseMove(String onRowMouseMove) {
		this.onRowMouseMove = onRowMouseMove;
	}

	public String getOnRowMouseOut() {
		return onRowMouseOut;
	}

	public void setOnRowMouseOut(String onRowMouseOut) {
		this.onRowMouseOut = onRowMouseOut;
	}

	public String getOnRowMouseOver() {
		return onRowMouseOver;
	}

	public void setOnRowMouseOver(String onRowMouseOver) {
		this.onRowMouseOver = onRowMouseOver;
	}

	public String getOnRowMouseUp() {
		return onRowMouseUp;
	}

	public void setOnRowMouseUp(String onRowMouseUp) {
		this.onRowMouseUp = onRowMouseUp;
	}

	public String getOnselect() {
		return onselect;
	}

	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOncollapse() {
		return oncollapse;
	}

	public void setOncollapse(String oncollapse) {
		this.oncollapse = oncollapse;
	}

	public String getOncomplete() {
		return oncomplete;
	}

	public void setOncomplete(String oncomplete) {
		this.oncomplete = oncomplete;
	}

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public String getOndragend() {
		return ondragend;
	}

	public void setOndragend(String ondragend) {
		this.ondragend = ondragend;
	}

	public String getOndragenter() {
		return ondragenter;
	}

	public void setOndragenter(String ondragenter) {
		this.ondragenter = ondragenter;
	}

	public String getOndragexit() {
		return ondragexit;
	}

	public void setOndragexit(String ondragexit) {
		this.ondragexit = ondragexit;
	}

	public String getOndragstart() {
		return ondragstart;
	}

	public void setOndragstart(String ondragstart) {
		this.ondragstart = ondragstart;
	}

	public String getOndrop() {
		return ondrop;
	}

	public void setOndrop(String ondrop) {
		this.ondrop = ondrop;
	}

	public String getOndropend() {
		return ondropend;
	}

	public void setOndropend(String ondropend) {
		this.ondropend = ondropend;
	}

	public String getOnexpand() {
		return onexpand;
	}

	public void setOnexpand(String onexpand) {
		this.onexpand = onexpand;
	}

	public String getOnkeydown() {
		return onkeydown;
	}

	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	public String getOnkeypress() {
		return onkeypress;
	}

	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	public String getOnkeyup() {
		return onkeyup;
	}

	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	public String getOnmousedown() {
		return onmousedown;
	}

	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	public String getOnmousemove() {
		return onmousemove;
	}

	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	public String getOnmouseout() {
		return onmouseout;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public String getOnmouseover() {
		return onmouseover;
	}

	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	public String getOnselected() {
		return onselected;
	}

	public void setOnselected(String onselected) {
		this.onselected = onselected;
	}

	public String getOnhide() {
		return onhide;
	}

	public void setOnhide(String onhide) {
		this.onhide = onhide;
	}

	public String getOnshow() {
		return onshow;
	}

	public void setOnshow(String onshow) {
		this.onshow = onshow;
	}

	public String getOnlistcall() {
		return onlistcall;
	}

	public void setOnlistcall(String onlistcall) {
		this.onlistcall = onlistcall;
	}

	public String getOnitemselected() {
		return onitemselected;
	}

	public void setOnitemselected(String onitemselected) {
		this.onitemselected = onitemselected;
	}

	public String getOninputblur() {
		return oninputblur;
	}

	public void setOninputblur(String oninputblur) {
		this.oninputblur = oninputblur;
	}

	public String getOninputclick() {
		return oninputclick;
	}

	public void setOninputclick(String oninputclick) {
		this.oninputclick = oninputclick;
	}

	public String getOninputfocus() {
		return oninputfocus;
	}

	public void setOninputfocus(String oninputfocus) {
		this.oninputfocus = oninputfocus;
	}

	public String getOninputkeydown() {
		return oninputkeydown;
	}

	public void setOninputkeydown(String oninputkeydown) {
		this.oninputkeydown = oninputkeydown;
	}

	public String getOninputkeypress() {
		return oninputkeypress;
	}

	public void setOninputkeypress(String oninputkeypress) {
		this.oninputkeypress = oninputkeypress;
	}

	public String getOninputkeyup() {
		return oninputkeyup;
	}

	public void setOninputkeyup(String oninputkeyup) {
		this.oninputkeyup = oninputkeyup;
	}

	public String getOninputselect() {
		return oninputselect;
	}

	public void setOninputselect(String oninputselect) {
		this.oninputselect = oninputselect;
	}

	public String getOndateselected() {
		return ondateselected;
	}

	public void setOndateselected(String ondateselected) {
		this.ondateselected = ondateselected;
	}

	public String getOndateselect() {
		return ondateselect;
	}

	public void setOndateselect(String ondateselect) {
		this.ondateselect = ondateselect;
	}

	public String getOntimeselect() {
		return ontimeselect;
	}

	public void setOntimeselect(String ontimeselect) {
		this.ontimeselect = ontimeselect;
	}

	public String getOntimeselected() {
		return ontimeselected;
	}

	public void setOntimeselected(String ontimeselected) {
		this.ontimeselected = ontimeselected;
	}

	public String getOnchanged() {
		return onchanged;
	}

	public void setOnchanged(String onchanged) {
		this.onchanged = onchanged;
	}

	public String getOndatemouseover() {
		return ondatemouseover;
	}

	public void setOndatemouseover(String ondatemouseover) {
		this.ondatemouseover = ondatemouseover;
	}

	public String getOndatemouseout() {
		return ondatemouseout;
	}

	public void setOndatemouseout(String ondatemouseout) {
		this.ondatemouseout = ondatemouseout;
	}

	public String getOncurrentdateselect() {
		return oncurrentdateselect;
	}

	public void setOncurrentdateselect(String oncurrentdateselect) {
		this.oncurrentdateselect = oncurrentdateselect;
	}

	public String getOninputchange() {
		return oninputchange;
	}

	public void setOninputchange(String oninputchange) {
		this.oninputchange = oninputchange;
	}

	public String getOndropout() {
		return ondropout;
	}

	public void setOndropout(String ondropout) {
		this.ondropout = ondropout;
	}

	public String getOnbottomclick() {
		return onbottomclick;
	}

	public void setOnbottomclick(String onbottomclick) {
		this.onbottomclick = onbottomclick;
	}

	public String getOnorderchanged() {
		return onorderchanged;
	}

	public void setOnorderchanged(String onorderchanged) {
		this.onorderchanged = onorderchanged;
	}

	public String getOnmaskclick() {
		return onmaskclick;
	}

	public void setOnmaskclick(String onmaskclick) {
		this.onmaskclick = onmaskclick;
	}

	public String getOnresize() {
		return onresize;
	}

	public void setOnresize(String onresize) {
		this.onresize = onresize;
	}

	public String getOnmaskmouseover() {
		return onmaskmouseover;
	}

	public void setOnmaskmouseover(String onmaskmouseover) {
		this.onmaskmouseover = onmaskmouseover;
	}

	public String getOnmaskmouseout() {
		return onmaskmouseout;
	}

	public void setOnmaskmouseout(String onmaskmouseout) {
		this.onmaskmouseout = onmaskmouseout;
	}

	public String getOnmaskmousemove() {
		return onmaskmousemove;
	}

	public void setOnmaskmousemove(String onmaskmousemove) {
		this.onmaskmousemove = onmaskmousemove;
	}

	public String getOnmaskmouseup() {
		return onmaskmouseup;
	}

	public void setOnmaskmouseup(String onmaskmouseup) {
		this.onmaskmouseup = onmaskmouseup;
	}

	public String getOnmaskmousedown() {
		return onmaskmousedown;
	}

	public void setOnmaskmousedown(String onmaskmousedown) {
		this.onmaskmousedown = onmaskmousedown;
	}

	public String getOnmaskdblclick() {
		return onmaskdblclick;
	}

	public void setOnmaskdblclick(String onmaskdblclick) {
		this.onmaskdblclick = onmaskdblclick;
	}

	public String getOnmaskcontextmenu() {
		return onmaskcontextmenu;
	}

	public void setOnmaskcontextmenu(String onmaskcontextmenu) {
		this.onmaskcontextmenu = onmaskcontextmenu;
	}
	

	public String getOngroupcollapse() {
		return ongroupcollapse;
	}

	public void setOngroupcollapse(String ongroupcollapse) {
		this.ongroupcollapse = ongroupcollapse;
	}

	public String getOpened() {
		return opened;
	}

	public void setOpened(String opened) {
		this.opened = opened;
	}

	public String getOneditactivated() {
		return oneditactivated;
	}

	public void setOneditactivated(String oneditactivated) {
		this.oneditactivated = oneditactivated;
	}
	

	public String getOnviewactivation() {
		return onviewactivation;
	}

	public void setOnviewactivation(String onviewactivation) {
		this.onviewactivation = onviewactivation;
	}

	public String getOnviewactivated() {
		return onviewactivated;
	}

	public void setOnviewactivated(String onviewactivated) {
		this.onviewactivated = onviewactivated;
	}

	public String getOninputmouseup() {
		return oninputmouseup;
	}

	public void setOninputmouseup(String oninputmouseup) {
		this.oninputmouseup = oninputmouseup;
	}

	public String getOninputmouseover() {
		return oninputmouseover;
	}

	public void setOninputmouseover(String oninputmouseover) {
		this.oninputmouseover = oninputmouseover;
	}

	public String getOninputmouseout() {
		return oninputmouseout;
	}

	public void setOninputmouseout(String oninputmouseout) {
		this.oninputmouseout = oninputmouseout;
	}

	public String getOninputmousemove() {
		return oninputmousemove;
	}

	public void setOninputmousemove(String oninputmousemove) {
		this.oninputmousemove = oninputmousemove;
	}

	public String getOninputmousedown() {
		return oninputmousedown;
	}

	public void setOninputmousedown(String oninputmousedown) {
		this.oninputmousedown = oninputmousedown;
	}

	public String getOninputdblclick() {
		return oninputdblclick;
	}

	public void setOninputdblclick(String oninputdblclick) {
		this.oninputdblclick = oninputdblclick;
	}

	public String getOneditactivation() {
		return oneditactivation;
	}

	public void setOneditactivation(String oneditactivation) {
		this.oneditactivation = oneditactivation;
	}

	public String getOnclear() {
		return onclear;
	}

	public void setOnclear(String onclear) {
		this.onclear = onclear;
	}

	public String getOnSlideSubmit() {
		return onSlideSubmit;
	}

	public void setOnSlideSubmit(String onSlideSubmit) {
		this.onSlideSubmit = onSlideSubmit;
	}

	public String getOnsizerejected() {
		return onsizerejected;
	}

	public void setOnsizerejected(String onsizerejected) {
		this.onsizerejected = onsizerejected;
	}

	public String getOntyperejected() {
		return ontyperejected;
	}

	public void setOntyperejected(String ontyperejected) {
		this.ontyperejected = ontyperejected;
	}

	public String getOnupload() {
		return onupload;
	}

	public void setOnupload(String onupload) {
		this.onupload = onupload;
	}

	public String getOnuploadcanceled() {
		return onuploadcanceled;
	}

	public void setOnuploadcanceled(String onuploadcanceled) {
		this.onuploadcanceled = onuploadcanceled;
	}

	public String getOnuploadcomplete() {
		return onuploadcomplete;
	}

	public void setOnuploadcomplete(String onuploadcomplete) {
		this.onuploadcomplete = onuploadcomplete;
	}

	public String getOnbeforehide() {
		return onbeforehide;
	}

	public void setOnbeforehide(String onbeforehide) {
		this.onbeforehide = onbeforehide;
	}

	public String getOnbeforeshow() {
		return onbeforeshow;
	}

	public void setOnbeforeshow(String onbeforeshow) {
		this.onbeforeshow = onbeforeshow;
	}

	public String getOnobjectchange() {
		return onobjectchange;
	}

	public void setOnobjectchange(String onobjectchange) {
		this.onobjectchange = onobjectchange;
	}

	public String getOnadd() {
		return onadd;
	}

	public void setOnadd(String onadd) {
		this.onadd = onadd;
	}

	public String getOnitemclick() {
		return onitemclick;
	}

	public void setOnitemclick(String onitemclick) {
		this.onitemclick = onitemclick;
	}

	public String getOnitemdblclick() {
		return onitemdblclick;
	}

	public void setOnitemdblclick(String onitemdblclick) {
		this.onitemdblclick = onitemdblclick;
	}

	public String getOnitemkeydown() {
		return onitemkeydown;
	}

	public void setOnitemkeydown(String onitemkeydown) {
		this.onitemkeydown = onitemkeydown;
	}

	public String getOnitemkeypress() {
		return onitemkeypress;
	}

	public void setOnitemkeypress(String onitemkeypress) {
		this.onitemkeypress = onitemkeypress;
	}

	public String getOnitemkeyup() {
		return onitemkeyup;
	}

	public void setOnitemkeyup(String onitemkeyup) {
		this.onitemkeyup = onitemkeyup;
	}

	public String getOnitemmousedown() {
		return onitemmousedown;
	}

	public void setOnitemmousedown(String onitemmousedown) {
		this.onitemmousedown = onitemmousedown;
	}

	public String getOnitemmousemove() {
		return onitemmousemove;
	}

	public void setOnitemmousemove(String onitemmousemove) {
		this.onitemmousemove = onitemmousemove;
	}

	public String getOnitemmouseout() {
		return onitemmouseout;
	}

	public void setOnitemmouseout(String onitemmouseout) {
		this.onitemmouseout = onitemmouseout;
	}

	public String getOnitemmouseover() {
		return onitemmouseover;
	}

	public void setOnitemmouseover(String onitemmouseover) {
		this.onitemmouseover = onitemmouseover;
	}

	public String getOnitemmouseup() {
		return onitemmouseup;
	}

	public void setOnitemmouseup(String onitemmouseup) {
		this.onitemmouseup = onitemmouseup;
	}

	public String getOnsetup() {
		return onsetup;
	}

	public void setOnsetup(String onsetup) {
		this.onsetup = onsetup;
	}

	public String getOnsave() {
		return onsave;
	}

	public void setOnsave(String onsave) {
		this.onsave = onsave;
	}

	public String getOnload() {
		return onload;
	}

	public void setOnload(String onload) {
		this.onload = onload;
	}

	public String getOnunload() {
		return onunload;
	}

	public void setOnunload(String onunload) {
		this.onunload = onunload;
	}

}