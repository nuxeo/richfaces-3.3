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
    private String onorderchange;
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
    private String ondbclick;
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
    private String onpagechange;
    private String onsetup;
    private String onsave;
    private String onsizeexceeded;
    private String onlistchanged;
    private String onlistchange;
    private String onload;
    private String onunload;
    private String oncopyclick;
    private String oncopyallclick;
    private String onremoveclick;
    private String onremoveallclick;

	public String getOnlistchanged() {
		return onlistchanged;
	}

	public void setOnlistchanged(String onlistchanged) {
		this.onlistchanged = onlistchanged;
	}
	
	public String getOnlistchange() {
		return onlistchange;
	}

	public void setOnlistchange(String onlistchange) {
		this.onlistchange = onlistchange;
	}

	
	public String getOncopyclick() {
		return oncopyclick;
	}

	public void setOncopyclick(String oncopyclick) {
		this.oncopyclick = oncopyclick;
	}
	
	public String getOncopyallclick() {
		return oncopyallclick;
	}

	public void setOncopyallclick(String oncopyallclick) {
		this.oncopyallclick = oncopyallclick;
	}
	
	public String getOnremoveclick() {
		return onremoveclick;
	}

	public void setOnremoveclick(String onremoveclick) {
		this.onremoveclick = onremoveclick;
	}

	public String getOnremoveallclick() {
		return onremoveallclick;
	}

	public void setOnremoveallclick(String onremoveallclick) {
		this.onremoveallclick = onremoveallclick;
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

	public String getOnsizeexceeded() {
		return onsizeexceeded;
	}

	public void setOnsizeexceeded(String onsizeexceeded) {
		this.onsizeexceeded = onsizeexceeded;
	}

	public String getOpened() {
		return opened;
	}

	public void setOpened(String opened) {
		this.opened = opened;
	}

	// showEvent('onkeypressInputID', 'onkeypress work!')
    public Event() {
    	/* Events for toolBar */
    	onlistchanged = "showEvent('infoFormID:infoSubview:onlistchangedInputID', 'onlistchanged work!')";
    	onlistchange = "showEvent('infoFormID:infoSubview:onlistchangeInputID', 'onlistchange work!')";
    	oncopyclick = "showEvent('infoFormID:infoSubview:oncopyclickInputID', 'oncopyclick work!')";
    	oncopyallclick = "showEvent('infoFormID:infoSubview:oncopyallclickInputID', 'oncopyallclick work!')";
    	onremoveclick = "showEvent('infoFormID:infoSubview:onremoveclickInputID', 'onremoveclick work!')";
    	onremoveallclick = "showEvent('infoFormID:infoSubview:onremoveallclickInputID', 'onremoveallclick work!')";
    	onsave = "showEvent('infoFormID:infoSubview:onsaveInputID', 'onsave work!')";
    	onsetup = "showEvent('infoFormID:infoSubview:onsetupInputID', 'onsetup work!')";
    	onitemclick = "showEvent('infoFormID:infoSubview:onitemclickInputID', 'onitemclick work!')";
    	onpagechange = "showEvent('infoFormID:infoSubview:onpagechangeInputID', 'onpagechange work!')";
    	onitemdblclick = "showEvent('infoFormID:infoSubview:onitemdblclickInputID', 'onitemdblclick work!')";
    	onitemkeydown = "showEvent('infoFormID:infoSubview:onitemkeydownInputID', 'onitemkeydown work!')";
    	onitemkeypress = "showEvent('infoFormID:infoSubview:onitemkeypressInputID', 'onitemkeypress work!')";
    	onitemkeyup = "showEvent('infoFormID:infoSubview:onitemkeyupInputID', 'onitemkeyup work!')";
    	onitemmousedown = "showEvent('infoFormID:infoSubview:onitemmousedownInputID', 'onitemmousedown work!')";
    	onitemmousemove = "showEvent('infoFormID:infoSubview:onitemmousemoveInputID', 'onitemmousemove work!')";
    	onitemmouseout = "showEvent('infoFormID:infoSubview:onitemmouseoutInputID', 'onitemmouseout work!')";
    	onitemmouseover = "showEvent('infoFormID:infoSubview:onitemmouseoverInputID', 'onitemmouseover work!')";
    	onitemmouseup = "showEvent('infoFormID:infoSubview:onitemmouseupInputID', 'onitemmouseup work!')";
    	/* --- */
		onobjectchange = "showEvent('infoFormID:infoSubview:onobjectchangeInputID', 'onobjectchange work!')";
    	onLoadMap = "showEvent('infoFormID:infoSubview:onLoadMapInputID', 'onLoadMap work!')";
    	opened = "showEvent('infoFormID:infoSubview:openedInputID', 'opened work!')";
    	onRowClick = "showEvent('infoFormID:infoSubview:onRowClickInputID', 'onRowClick work!')";
    	onRowDblClick = "showEvent('infoFormID:infoSubview:onRowDblClickInputID', 'onRowDblClick work!')";
    	onRowMouseDown = "showEvent('infoFormID:infoSubview:onRowMouseDownInputID', 'onRowMouseDown work!')";
    	onRowMouseMove = "showEvent('infoFormID:infoSubview:onRowMouseMoveInputID', 'onRowMouseMove work!')";
    	onRowMouseOut = "showEvent('infoFormID:infoSubview:onRowMouseOutInputID', 'onRowMouseOut work!')";
    	onRowMouseOver = "showEvent('infoFormID:infoSubview:onRowMouseOverInputID', 'onRowMouseOver work!')";
    	onRowMouseUp = "showEvent('infoFormID:infoSubview:onRowMouseUpInputID', 'onRowMouseUp work!')";
    	onbeforedomupdate = "showEvent('infoFormID:infoSubview:onbeforedomupdateInputID', 'onbeforedomupdate work!')";
    	onblur = "showEvent('infoFormID:infoSubview:onblurInputID', 'onblur work!')";
    	onbottomclick = "showEvent('infoFormID:infoSubview:onbottomclickInputID', 'onbottomclick work!')";
    	onchange = "showEvent('infoFormID:infoSubview:onchangeInputID', 'onchange work!')";
    	onchanged = "showEvent('infoFormID:infoSubview:onchangedInputID', 'onchanged work!')";
    	onclick = "showEvent('infoFormID:infoSubview:onclickInputID', 'onclick work!')";
    	oncollapse = "showEvent('infoFormID:infoSubview:oncollapseInputID', 'oncollapse work!')";
    	oncomplete = "showEvent('infoFormID:infoSubview:oncompleteInputID', 'oncomplete work!')";
    	oncontextmenu = "showEvent('infoFormID:infoSubview:oncontextmenuInputID', 'oncontextmenu work!')";
    	onclear = "showEvent('infoFormID:infoSubview:onclearInputID', 'onclear work!')";
    	oncurrentdateselect = "showEvent('infoFormID:infoSubview:oncurrentdateselectInputID', 'oncurrentdateselect work!')";
    	ondatemouseout = "showEvent('infoFormID:infoSubview:ondatemouseoutInputID', 'ondatemouseout work!')";
    	ondatemouseover = "showEvent('infoFormID:infoSubview:ondatemouseoverInputID', 'ondatemouseover work!')";
    	ondateselect = "showEvent('infoFormID:infoSubview:ondateselectInputID', 'ondateselect work!')";
    	ondateselected = "showEvent('infoFormID:infoSubview:ondateselectedInputID', 'ondateselected work!')";
    	ondblclick = "showEvent('infoFormID:infoSubview:ondblclickInputID', 'ondblclick work!')";
    	ondownclick = "showEvent('infoFormID:infoSubview:ondownclickInputID', 'ondownclick work!')";
    	ondragend = "showEvent('infoFormID:infoSubview:ondragendInputID', 'ondragend work!')";
    	ondragenter = "showEvent('infoFormID:infoSubview:ondragenterInputID', 'ondragenter work!')";
    	ondragexit = "showEvent('infoFormID:infoSubview:ondragexitInputID', 'ondragexit work!')";
    	ondragstart = "showEvent('infoFormID:infoSubview:ondragstartInputID', 'ondragstart work!')";
    	ondrop = "showEvent('infoFormID:infoSubview:ondropInputID', 'ondrop work!')";
    	ondropend = "showEvent('infoFormID:infoSubview:ondropendInputID', 'ondropend work!')";
    	ondropout = "showEvent('infoFormID:infoSubview:ondropoutInputID', 'ondropout work!')";
    	ondropover = "showEvent('infoFormID:infoSubview:ondropoverInputID', 'ondropover work!')";
    	onerror = "showEvent('infoFormID:infoSubview:onerrorInputID', 'onerror work!')";
    	onexpand = "showEvent('infoFormID:infoSubview:onexpandInputID', 'onexpand work!')";
    	onfocus = "showEvent('infoFormID:infoSubview:onfocusInputID', 'onfocus work!')";
    	ongroupactivate = "showEvent('infoFormID:infoSubview:ongroupactivateInputID', 'ongroupactivate work!')";
    	ongroupexpand = "showEvent('infoFormID:infoSubview:ongroupexpandInputID', 'ongroupexpand work!')";
    	onheaderclick = "showEvent('infoFormID:infoSubview:onheaderclickInputID', 'onheaderclick work!')";
    	onhide = "showEvent('infoFormID:infoSubview:onhideInputID', 'onhide work!')";
    	oninit = "showEvent('infoFormID:infoSubview:oninitInputID', 'oninit work!')";
    	oninputblur = "showEvent('infoFormID:infoSubview:oninputblurInputID', 'oninputblur work!')";
    	oninputchange = "showEvent('infoFormID:infoSubview:oninputchangeInputID', 'oninputchange work!')";
    	oninputclick = "showEvent('infoFormID:infoSubview:oninputclickInputID', 'oninputclick work!')";
    	oninputfocus = "showEvent('infoFormID:infoSubview:oninputfocusInputID', 'oninputfocus work!')";
    	oninputkeydown = "showEvent('infoFormID:infoSubview:oninputkeydownInputID', 'oninputkeydown work!')";
    	oninputkeypress = "showEvent('infoFormID:infoSubview:oninputkeypressInputID', 'oninputkeypress work!')";
    	oninputkeyup = "showEvent('infoFormID:infoSubview:oninputkeyupInputID', 'oninputkeyup work!')";
    	oninputselect = "showEvent('infoFormID:infoSubview:oninputselectInputID', 'oninputselect work!')";
    	onitemchange = "showEvent('infoFormID:infoSubview:onitemchangeInputID', 'onitemchange work!')";
    	onitemhover = "showEvent('infoFormID:infoSubview:onitemhoverInputID', 'onitemhover work!')";
    	onitemselect = "showEvent('infoFormID:infoSubview:onitemselectInputID', 'onitemselect work!')";
    	onitemselected = "showEvent('infoFormID:infoSubview:onitemselectedInputID', 'onitemselected work!')";
    	onkeydown = "showEvent('infoFormID:infoSubview:onkeydownInputID', 'onkeydown work!')";
    	onkeypress = "showEvent('infoFormID:infoSubview:onkeypressInputID', 'onkeypress work!')";
    	onkeyup = "showEvent('infoFormID:infoSubview:onkeyupInputID', 'onkeyup work!')";
    	onlistcall = "showEvent('infoFormID:infoSubview:onlistcallInputID', 'onlistcall work!')";
    	onmaskclick = "showEvent('infoFormID:infoSubview:onmaskclickInputID', 'onmaskclick work!')";
    	onmaskcontextmenu = "showEvent('infoFormID:infoSubview:onmaskcontextmenuInputID', 'onmaskcontextmenu work!')";
    	onmaskdblclick = "showEvent('infoFormID:infoSubview:onmaskdblclickInputID', 'onmaskdblclick work!')";
    	onmaskmousedown = "showEvent('infoFormID:infoSubview:onmaskmousedownInputID', 'onmaskmousedown work!')";
    	onmaskmousemove = "showEvent('infoFormID:infoSubview:onmaskmousemoveInputID', 'onmaskmousemove work!')";
    	onmaskmouseout = "showEvent('infoFormID:infoSubview:onmaskmouseoutInputID', 'onmaskmouseout work!')";
    	onmaskmouseover = "showEvent('infoFormID:infoSubview:onmaskmouseoverInputID', 'onmaskmouseover work!')";
    	onmaskmouseup = "showEvent('infoFormID:infoSubview:onmaskmouseupInputID', 'onmaskmouseup work!')";
    	onmousedown = "showEvent('infoFormID:infoSubview:onmousedownInputID', 'onmousedown work!')";
    	onmousemove = "showEvent('infoFormID:infoSubview:onmousemoveInputID', 'onmousemove work!')";
    	onmouseout = "showEvent('infoFormID:infoSubview:onmouseoutInputID', 'onmouseout work!')";
    	onmouseover = "showEvent('infoFormID:infoSubview:onmouseoverInputID', 'onmouseover work!')";
    	onmouseup = "showEvent('infoFormID:infoSubview:onmouseupInputID', 'onmouseup work!')";
    	onmove = "showEvent('infoFormID:infoSubview:onmoveInputID', 'onmove work!')";
    	onorderchanged = "showEvent('infoFormID:infoSubview:onorderchangedInputID', 'onorderchanged work!')";
    	onorderchange = "showEvent('infoFormID:infoSubview:onorderchangeInputID', 'onorderchange work!')";
    	onresize = "showEvent('infoFormID:infoSubview:onresizeInputID', 'onresize work!')";
    	onselect = "showEvent('infoFormID:infoSubview:onselectInputID', 'onselect work!')";
    	onselected = "showEvent('infoFormID:infoSubview:onselectedInputID', 'onselected work!')";
    	onselectionchange = "showEvent('infoFormID:infoSubview:onselectionchangeInputID', 'onselectionchange work!')";
    	onshow = "showEvent('infoFormID:infoSubview:onshowInputID', 'onshow work!')";
    	onslide = "showEvent('infoFormID:infoSubview:onslideInputID', 'onslide work!')";
    	onsubmit = "showEvent('infoFormID:infoSubview:onsubmitInputID', 'onsubmit work!')";
    	ontabenter = "showEvent('infoFormID:infoSubview:ontabenterInputID', 'ontabenter work!')";
    	ontableave = "showEvent('infoFormID:infoSubview:ontableaveInputID', 'ontableave work!')";
    	ontimeselect = "showEvent('infoFormID:infoSubview:ontimeselectInputID', 'ontimeselect work!')";
    	ontimeselected = "showEvent('infoFormID:infoSubview:ontimeselectedInputID', 'ontimeselected work!')";
    	ontopclick = "showEvent('infoFormID:infoSubview:ontopclickInputID', 'ontopclick work!')";
    	onupclick = "showEvent('infoFormID:infoSubview:onupclickInputID', 'onupclick work!')";
    	ongroupcollapse = "showEvent('infoFormID:infoSubview:ongroupcollapseInputID', 'ongroupcollapse work!')";
    	onSlideSubmit = "showEvent('infoFormID:infoSubview:onSlideSubmitInputID', 'onSlideSubmit work!')";
    	oneditactivated = "showEvent('infoFormID:infoSubview:oneditactivatedInputID', 'oneditactivated work!')";
    	oneditactivation = "showEvent('infoFormID:infoSubview:oneditactivationInputID', 'oneditactivation work!')";
    	oninputdblclick = "showEvent('infoFormID:infoSubview:oninputdblclickInputID', 'oninputdblclick work!')";
    	oninputmousedown = "showEvent('infoFormID:infoSubview:oninputmousedownInputID', 'oninputmousedown work!')";
    	oninputmousemove = "showEvent('infoFormID:infoSubview:oninputmousemoveInputID', 'oninputmousemove work!')";
    	oninputmouseout = "showEvent('infoFormID:infoSubview:oninputmouseoutInputID', 'oninputmouseout work!')";
    	oninputmouseover = "showEvent('infoFormID:infoSubview:oninputmouseoverInputID', 'oninputmouseover work!')";
    	oninputmouseup = "showEvent('infoFormID:infoSubview:oninputmouseupInputID', 'oninputmouseup work!')";
    	onsizerejected = "showEvent('infoFormID:infoSubview:onsizerejectedInputID', 'onsizerejected work!')";
    	ontyperejected = "showEvent('infoFormID:infoSubview:ontyperejectedInputID', 'ontyperejected work!')";    	
    	onupload = "showEvent('infoFormID:infoSubview:onuploadInputID', 'onupload work!')";
    	onuploadcanceled = "showEvent('infoFormID:infoSubview:onuploadcanceledInputID', 'onuploadcanceled work!')";
    	onuploadcomplete = "showEvent('infoFormID:infoSubview:onuploadcompleteInputID', 'onuploadcomplete work!')";    	
    	onviewactivated = "showEvent('infoFormID:infoSubview:onviewactivatedInputID', 'onviewactivated work!')";
    	onviewactivation = "showEvent('infoFormID:infoSubview:onviewactivationInputID', 'onviewactivation work!')";
    	onbeforehide = "showEvent('infoFormID:infoSubview:onbeforehideInputID', 'onbeforehide work!')";
    	onbeforeshow = "showEvent('infoFormID:infoSubview:onbeforeshowInputID', 'onbeforeshow work!')";
    	onadd = "showEvent('infoFormID:infoSubview:onaddInputID', 'onadd work!')";
    	ondbclick = "showEvent('infoFormID:infoSubview:ondbclickID', 'ondbclick work!')";
    	onsizeexceeded = "showEvent('infoFormID:infoSubview:onsizeexceededID', 'onsizeexceeded work!')";
    	onload = "showEvent('infoFormID:infoSubview:onloadID', 'onload work!')";
    	onunload = "showEvent('infoFormID:infoSubview:onunloadID', 'onunload work!')";
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
    
    public ArrayList<SelectItem> getOncopyclickSI() {
        return getEvent("oncopyclick");
    }
    
    public ArrayList<SelectItem> getOncopyallclickSI() {
        return getEvent("oncopyallclick");
    }
    
    public ArrayList<SelectItem> getOnremoveclickSI() {
        return getEvent("onremoveclick");
    }
    
    public ArrayList<SelectItem> getOnremoveallclickSI() {
        return getEvent("onremoveallclick");
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
	
	public String getOnorderchange() {
		return onorderchange;
	}

	public void setOnorderchange(String onorderchange) {
		this.onorderchange = onorderchange;
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

	/**
	 * @return the onSlideSubmit
	 */
	public String getOnSlideSubmit() {
		return onSlideSubmit;
	}

	/**
	 * @param onSlideSubmit the onSlideSubmit to set
	 */
	public void setOnSlideSubmit(String onSlideSubmit) {
		this.onSlideSubmit = onSlideSubmit;
	}

	public String getOninputmousedown() {
		return oninputmousedown;
	}

	public void setOninputmousedown(String oninputmousedown) {
		this.oninputmousedown = oninputmousedown;
	}

	public String getOninputmousemove() {
		return oninputmousemove;
	}

	public void setOninputmousemove(String oninputmousemove) {
		this.oninputmousemove = oninputmousemove;
	}

	public String getOninputmouseout() {
		return oninputmouseout;
	}

	public void setOninputmouseout(String oninputmouseout) {
		this.oninputmouseout = oninputmouseout;
	}

	public String getOninputmouseover() {
		return oninputmouseover;
	}

	public void setOninputmouseover(String oninputmouseover) {
		this.oninputmouseover = oninputmouseover;
	}

	public String getOninputmouseup() {
		return oninputmouseup;
	}

	public void setOninputmouseup(String oninputmouseup) {
		this.oninputmouseup = oninputmouseup;
	}

	public String getOnviewactivated() {
		return onviewactivated;
	}

	public void setOnviewactivated(String onviewactivated) {
		this.onviewactivated = onviewactivated;
	}

	public String getOnviewactivation() {
		return onviewactivation;
	}

	public void setOnviewactivation(String onviewactivation) {
		this.onviewactivation = onviewactivation;
	}

	public String getOneditactivated() {
		return oneditactivated;
	}

	public void setOneditactivated(String oneditactivated) {
		this.oneditactivated = oneditactivated;
	}

	public String getOneditactivation() {
		return oneditactivation;
	}

	public void setOneditactivation(String oneditactivation) {
		this.oneditactivation = oneditactivation;
	}

	public String getOninputdblclick() {
		return oninputdblclick;
	}

	public void setOninputdblclick(String oninputdblclick) {
		this.oninputdblclick = oninputdblclick;
	}

	public String getOnclear() {
		return onclear;
	}

	public void setOnclear(String onclear) {
		this.onclear = onclear;
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

	public String getOnpagechange() {
		return onpagechange;
	}

	public void setOnpagechange(String onpagechange) {
		this.onpagechange = onpagechange;
	}

	public String getOndbclick() {
		return ondbclick;
	}

	public void setOndbclick(String ondbclick) {
		this.ondbclick = ondbclick;
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