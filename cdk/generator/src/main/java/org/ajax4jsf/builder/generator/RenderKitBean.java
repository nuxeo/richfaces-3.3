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

package org.ajax4jsf.builder.generator;

/**
 * Child element for {@link org.ajax4jsf.builder.generator.FacesConfigGenerator}
 * &lt;facesconfig ... &gt;
 *  &lt;renderkit renderkitid="..." renderkitclass="..." [package="..."] &gt; for generate any of render-kits at time.
 *  .....
 *  &lt;/facesconfig&gt;
 * Properties
 *  renderkitid - name of render-kit, defaults to HTML_BASIC
 *  renderkitclass - full Java class name for custom render kit
 *  package - optional Java package name for override from configuration, used for create set of differern render-kits in one task
 *  frompackage - optional Java package name, if original renderer class starts with it, replase with "package" attribute
 * @author asmirnov@exadel.com (latest modification by $Author: ishabalov $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/02/20 20:58:01 $
 *
 */
public class RenderKitBean {

	private String _renderkitid = null;
	private String _renderkitclass = null;
	
	private String _package = null;
	
	private String _fromPackage = null;
	
	/**
	 * Build result renderer class for this renderer.
	 * Remove base package ( {@link #getFromPackage()} ) or full package name and
	 * and append new package name.
	 * @param fromClass
	 * @return
	 */
	public String rendererClass(String fromClass) {
		String className = fromClass;
		// Get short class name
		if (null != getPackage()) {
			if (null != getFromPackage()
					&& fromClass.startsWith(getFromPackage())) {
				className = fromClass.substring(getFromPackage().length() + 1);
			} else {
				int lastPoint = fromClass.lastIndexOf('.');
				if (lastPoint > 0) {
					className = fromClass.substring(lastPoint + 1);
				}
			}
			className = getPackage() + "." + className;
		}
		return className;
	}

	/**
	 * @return Returns the package.
	 */
	public String getPackage() {
		return _package;
	}
	/**
	 * @param package1 The package to set.
	 */
	public void setPackage(String package1) {
		_package = package1;
	}

	/**
	 * @return Returns the renderKitClass.
	 */
	public String getRenderkitclass() {
		return this._renderkitclass;
	}
	/**
	 * @param renderKitClass The renderKitClass to set.
	 */
	public void setRenderkitclass(String renderKitClass) {
		this._renderkitclass = renderKitClass;
	}
	/**
	 * @return Returns the renderKitId.
	 */
	public String getRenderkitid() {
		return this._renderkitid;
	}
	/**
	 * @param renderKitId The renderKitId to set.
	 */
	public void setRenderkitid(String renderKitId) {
		this._renderkitid = renderKitId;
	}
	/**
	 * @return Returns the fromPackage.
	 */
	public String getFromPackage() {
		return _fromPackage;
	}
	/**
	 * @param fromPackage The fromPackage to set.
	 */
	public void setFromPackage(String fromPackage) {
		_fromPackage = fromPackage;
	}

}
