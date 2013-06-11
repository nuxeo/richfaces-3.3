package message;

import org.richfaces.component.html.HtmlRichMessage;
import org.richfaces.component.html.HtmlRichMessages;

import util.componentInfo.ComponentInfo;

public class Message {
	private String msgs;
	private String layout;
	private String title;
	private boolean showDetail;
	private boolean showSummary;
	private boolean tooltip;
	private String select1;
	private String select2;
	private String select3;
	private String select4;
	private String select5;
	private HtmlRichMessage htmlMessage = null;
	private HtmlRichMessages htmlMessages = null;
	
	public void addHtmlMessages(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlMessage);
		info.addField(htmlMessages);
	}
	
	public HtmlRichMessage getHtmlMessage() {
		return htmlMessage;
	}

	public void setHtmlMessage(HtmlRichMessage htmlMessage) {
		this.htmlMessage = htmlMessage;
	}

	public HtmlRichMessages getHtmlMessages() {
		return htmlMessages;
	}

	public void setHtmlMessages(HtmlRichMessages htmlMessages) {
		this.htmlMessages = htmlMessages;
	}

	public Message() {
		msgs = "select1";
		layout = "table";
		title = "Title";
		showDetail = true;
		showSummary = false;
		tooltip = true;
		select1 = "error";
		select2 = "error";
		select3 = "error";
		select4 = "error";
		select5 = "error";
	}

	public String getMsgs() {
		return msgs;
	}

	public void setMsgs(String msg) {
		this.msgs = msg;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public boolean isShowDetail() {
		return showDetail;
	}

	public void setShowDetail(boolean showDetail) {
		this.showDetail = showDetail;
	}

	public boolean isShowSummary() {
		return showSummary;
	}

	public void setShowSummary(boolean showSummary) {
		this.showSummary = showSummary;
	}

	public boolean isTooltip() {
		return tooltip;
	}

	public void setTooltip(boolean tooltip) {
		this.tooltip = tooltip;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSelect1() {
		return select1;
	}

	public void setSelect1(String select1) {
		this.select1 = select1;
	}

	public String getSelect2() {
		return select2;
	}

	public void setSelect2(String select2) {
		this.select2 = select2;
	}

	public String getSelect3() {
		return select3;
	}

	public void setSelect3(String select3) {
		this.select3 = select3;
	}

	public String getSelect4() {
		return select4;
	}

	public void setSelect4(String select4) {
		this.select4 = select4;
	}

	public String getSelect5() {
		return select5;
	}

	public void setSelect5(String select5) {
		this.select5 = select5;
	}
	
	public void bTest1(){
		setLayout("table");
		setMsgs("fatal");
		setShowDetail(true);
		setShowSummary(true);
		setTitle("Test1");
		setTooltip(true);
		setSelect1("error");
		setSelect2("fatal");
		setSelect3("warn");
		setSelect4("info");
		setSelect5("passed");
	}

	public void bTest2(){
		setLayout("table");
		setMsgs("error");
		setShowDetail(false);
		setShowSummary(true);
		setTitle("Test2");
		setTooltip(true);
		setSelect1("error");
		setSelect2("fatal");
		setSelect3("fatal");
		setSelect4("passed");
		setSelect5("passed");		
	}
	
	public void bTest3(){
		setLayout("table");
		setMsgs("passed");
		setShowDetail(true);
		setShowSummary(false);
		setTitle("Test3");
		setTooltip(true);
		setSelect1("error");
		setSelect2("error");
		setSelect3("passed");
		setSelect4("error");
		setSelect5("passed");		
	}
	
	public void bTest4(){
		setLayout("table");
		setMsgs("passed");
		setShowDetail(true);
		setShowSummary(true);
		setTitle("Test4");
		setTooltip(false);
		setSelect1("warn");
		setSelect2("warn");
		setSelect3("warn");
		setSelect4("warn");
		setSelect5("warn");		
	}
	
	public void bTest5(){
		setLayout("table");
		setMsgs("warn");
		setShowDetail(false);
		setShowSummary(false);
		setTitle("Test5");
		setTooltip(false);
		setSelect1("passed");
		setSelect2("fatal");
		setSelect3("error");
		setSelect4("passed");
		setSelect5("warn");		
	}
}
