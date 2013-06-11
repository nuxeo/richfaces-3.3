/**
 * 
 */
package org.richfaces.samples;

import org.richfaces.component.LayoutPosition;

/**
 * @author asmirnov
 *
 */
public class LayoutBean {
	
	private LayoutPosition position;
	
	/**
	 * @param position
	 */
	public LayoutBean(LayoutPosition position) {
		this.position = position;
	}

	private boolean rendered = true;
	
	private String width;

	/**
	 * @return the rendered
	 */
	public boolean isRendered() {
		return rendered;
	}

	/**
	 * @param rendered the rendered to set
	 */
	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return the position
	 */
	public LayoutPosition getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(LayoutPosition position) {
		this.position = position;
	}

}
