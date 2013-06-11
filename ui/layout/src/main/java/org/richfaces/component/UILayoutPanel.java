/**
 *
 */

package org.richfaces.component;

import javax.faces.component.UIComponentBase;

/**
 * JSF component class
 *
 */
public abstract class UILayoutPanel extends UIComponentBase {
	
	public static final String COMPONENT_TYPE = "org.richfaces.LayoutPanel";
	
	public static final String COMPONENT_FAMILY = "org.richfaces.LayoutPanel";
	
	/**
	 * Get placement position type name
	 * @return
	 */
	public abstract LayoutPosition getPosition();

	/**
	 * Set placement position type name
	 * @param newvalue
	 */
	public abstract void setPosition(LayoutPosition newvalue);


	/**
	 * Get Panel width.
	 * @return
	 */
	public abstract String getWidth();

	/**
	 * Set Panel width.
	 * @param newvalue
	 */
	public abstract void setWidth(String newvalue);


}
