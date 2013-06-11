/**
 * 
 */
package org.richfaces.samples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.richfaces.component.LayoutPosition;

/**
 * @author asmirnov
 *
 */
public class Bean {
	
	private String position="left";
	
	private String theme = "simple";
	
	private String contentType = "text/html";
	
	private String encoding = "UTF-8";
	
	private int width=960;
	
	private int sidebarWidth=260;
	
	private final Map<String, LayoutBean> layouts;
	private final  List<LayoutBean> layoutValues;
	
	public Bean() {
		Map<String, LayoutBean> layouts = new HashMap<String, LayoutBean>(5);
		for(LayoutPosition lp:LayoutPosition.values()){
			layouts.put(lp.toString(), new LayoutBean(lp));
		}
		this.layouts = Collections.unmodifiableMap(layouts);
		layoutValues = Collections.unmodifiableList(new ArrayList<LayoutBean>(layouts.values()));
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
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
	 * @return the sidebarWidth
	 */
	public int getSidebarWidth() {
		return sidebarWidth;
	}

	/**
	 * @param sidebarWidth the sidebarWidth to set
	 */
	public void setSidebarWidth(int sidebarWidth) {
		this.sidebarWidth = sidebarWidth;
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
		if("".equals(theme)){
			theme = null;
		}
		this.theme = theme;
	}

	/**
	 * @return the layouts
	 */
	public Map<String, LayoutBean> getLayouts() {
		return layouts;
	}

	public List<LayoutBean> getLayoutValues(){
		return layoutValues;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
