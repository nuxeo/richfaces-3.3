/**
 *
 */

package org.richfaces.component;

import javax.faces.component.UIPanel;

import org.richfaces.skin.SkinFactory;

/**
 * JSF component class
 *
 */
public abstract class UIPage extends UIPanel {
	
	public static final String COMPONENT_TYPE = "org.richfaces.Page";
	
	public static final String COMPONENT_FAMILY = "org.richfaces.Page";
	
	
	/**
	 * Get Page theme name
	 * @return
	 */
	public abstract String getTheme();

	/**
	 * Set Page theme name
	 * @param newvalue
	 */
	public abstract void setTheme(String newvalue);


	
	@Override
	public String getRendererType() {
		String theme = getTheme();
		String rendererType = null;
		if(null != theme && theme.length()>0){
			rendererType = SkinFactory.getInstance().getTheme(getFacesContext(), theme).getRendererType();
		} 
		if(null == rendererType){
			rendererType = super.getRendererType();
		}
		return rendererType;
	}
	
	
}
