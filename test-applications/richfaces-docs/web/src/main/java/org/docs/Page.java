/**
 * 
 */
package org.docs;

/**
 * @author Michael Sorokin
 *
 */
public class Page {
	
	private String markupType;
	private String theme;
private String pageTitle;


	
	
	public String getPageTitle() {
	return pageTitle;
}

public void setPageTitle(String pageTitle) {
	this.pageTitle = pageTitle;
}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}



	public String getMarkupType() {
		return markupType;
	}

	public void setMarkupType(String markupType) {
		this.markupType = markupType;
	}
	
	

}
