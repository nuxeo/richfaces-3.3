/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.ajax4jsf.builder.config;

/**
 * JavaBean to hold renderer configuration properties
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.6 $ $Date: 2007/02/28 21:02:44 $
 *
 */
public class RendererBean extends JsfBean {

	/**
	 * 
	 */
	private String _family;
	
	/**
	 * 
	 */
	private String _facet;
	
	/**
	 * If true ( default ) generate template for renderer
	 */
	private boolean _generate = true;
	
	/**
	 * If true, override existing renderer classes ( Be careful !)
	 */
	private boolean _override = false;
	
	/**
	 * Path to XML template for compile renderer class.
	 */
	private String _template; 
	
	/**
	 * Name of render-kit of this renderer.
	 */
	private String _renderKit = "HTML_BASIC";
	
	/**
	 * 
	 */
	public RendererBean() {
		super();
		setSuperclass("org.ajax4jsf.renderkit.RendererBase");
	}

	/**
	 * @return Returns the facet.
	 */
	public String getFacet() {
		return _facet;
	}

	/**
	 * @param facet The facet to set.
	 */
	public void setFacet(String facet) {
		_facet = facet;
	}

	/**
	 * @return Returns the family.
	 */
	public String getFamily() {
		return _family;
	}

	/**
	 * @param family The family to set.
	 */
	public void setFamily(String family) {
		_family = family;
	}

	/**
	 * @return Returns the generate.
	 */
	public boolean isGenerate() {
		return _generate;
	}

	/**
	 * @param generate The generate to set.
	 */
	public void setGenerate(boolean generate) {
		_generate = generate;
	}

	/**
	 * @return Returns the override.
	 */
	public boolean isOverride() {
		return _override;
	}

	/**
	 * @param override The override to set.
	 */
	public void setOverride(boolean override) {
		_override = override;
	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return this._template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(String template) {
		this._template = template;
	}

	/**
	 * @return the renderKit
	 */
	public String getRenderKit() {
		return this._renderKit;
	}

	/**
	 * @param renderKit the renderKit to set
	 */
	public void setRenderKit(String renderKit) {
		this._renderKit = renderKit;
	}

	/**
	 * Check renderer properties. If nessesary, compile template for remderer class.
	 */
	public void checkProperties()  throws ParsingException {
		if(isGenerate() && null != getTemplate()){
			getLog().debug("Renderer must be compiled from template "+getTemplate());
		}
	}

}
