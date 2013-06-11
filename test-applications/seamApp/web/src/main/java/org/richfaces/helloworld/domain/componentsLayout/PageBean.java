package org.richfaces.helloworld.domain.componentsLayout;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.UIPage;

import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;

@Name("page")
@Scope(ScopeType.EVENT)
public class PageBean {
	
	private UIPage htmlPage;
	private String bodyClass;
	private String contentType;//mime content type
	private String dir;
	private String footerClass;
	private String headerClass;
	private String lang;
	private String markupType;
	private String namespace;
	private String pageTitle;
	private boolean rendered;
	private String sidebarClass;
	private String sidebarPosition;
	private int sidebarWidth;
	private String style;
	private String styleClass;
	private String theme;
	private String title;
	private int width;
	private String pageLabel;
	
	public String getPageLabel() {
		return pageLabel;
	}

	public void setPageLabel(String pageLabel) {
		this.pageLabel = pageLabel;
	}

	public PageBean(){
		bodyClass = "pageBodyClass";
		contentType = "text/html";//"text/html", "image/png", "image/gif", "video/mpeg", "text/css", and "audio/basic"
		dir = "ltr";//ltr or rtl
		footerClass = "pageFooterClass";
		headerClass = "pageHeaderClass";
		lang = "en";
		markupType = "jsp";
		namespace = "http://java.sun.com/JSP/Page";
		pageTitle = "Layout components";
		rendered = true;
		sidebarClass = "sideBarClass";
		sidebarPosition = "left";//left,right
		sidebarWidth = 1400;
		style = "font-style:normal";
		styleClass = "pageStyleClass";
		theme = "simple";
		title = "Richfaces";
		width = 1400;	
		pageLabel = "";
	}
	
	public void addPage(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlPage);
	}
	
	public void checkPage(ActionEvent event){
		FacesContext context = FacesContext.getCurrentInstance();
		pageLabel = htmlPage.getClientId(context);
	}
	
	public UIPage getHtmlPage() {
		return htmlPage;
	}
	public void setHtmlPage(UIPage htmlPage) {
		this.htmlPage = htmlPage;
	}
	public String getBodyClass() {
		return bodyClass;
	}
	public void setBodyClass(String bodyClass) {
		this.bodyClass = bodyClass;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getFooterClass() {
		return footerClass;
	}
	public void setFooterClass(String footerClass) {
		this.footerClass = footerClass;
	}
	public String getHeaderClass() {
		return headerClass;
	}
	public void setHeaderClass(String headerClass) {
		this.headerClass = headerClass;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getMarkupType() {
		return markupType;
	}
	public void setMarkupType(String markupType) {
		this.markupType = markupType;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	public boolean isRendered() {
		return rendered;
	}
	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}
	public String getSidebarClass() {
		return sidebarClass;
	}
	public void setSidebarClass(String sidebarClass) {
		this.sidebarClass = sidebarClass;
	}
	public String getSidebarPosition() {
		return sidebarPosition;
	}
	public void setSidebarPosition(String sidebarPosition) {
		this.sidebarPosition = sidebarPosition;
	}
	public int getSidebarWidth() {
		return sidebarWidth;
	}
	public void setSidebarWidth(int sidebarWidth) {
		this.sidebarWidth = sidebarWidth;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
}
