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

package org.ajax4jsf.renderkit;

import org.ajax4jsf.javascript.AjaxScript;
import org.ajax4jsf.resource.InternetResource;


/**
 * Base class for all Ajax enabled components. Perform common task -
 * output javasript for component.
 * @author shura (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:58:49 $
 *
 */
public abstract class AjaxComponentRendererBase extends HeaderResourcesRendererBase implements HeaderResourceProducer2 {

//	private InternetResource ajaxScript = getResource("/com/exadel/vcp/framework/ajax/scripts/AJAX.js");
	private static final String AJAX_SCRIPT = AjaxScript.class.getName();
	
	private InternetResource[] scripts = null;

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.HeaderResourceProducer#getHeaderScripts(javax.faces.context.FacesContext)
	 */
//	public LinkedHashSet getHeaderScripts(FacesContext context, UIComponent component) {
//		LinkedHashSet scripts = new LinkedHashSet() ; // Collections.singleton(ajaxScript.getUri(context, null));
//		scripts.add(ajaxScript.getUri(context, component));
//		String[] additionalScripts = getAdditionalScripts();
//		if (null != additionalScripts) {
//			for (int i = 0; i < additionalScripts.length; i++) {
//				String resource = additionalScripts[i];
//				scripts.add(getResource(resource).getUri(context, component));
//			}
//		}
//		return scripts;
//	}
	
	

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.HeaderResourcesRendererBase#getScripts()
	 */
	protected  InternetResource[] getScripts() {
		// Lazy create
		synchronized (this) {
			if (scripts == null) {
				InternetResource[] additionalScripts = getAdditionalScripts();
				// Custom scripts
				if (null != additionalScripts) {
					scripts = new InternetResource[additionalScripts.length+1];
					System.arraycopy(additionalScripts,0,scripts,1,additionalScripts.length);
				} else {
					scripts = new InternetResource[1];				
				}
				// Ajax script
				scripts[0] = getResource(AJAX_SCRIPT);			
			}
		}

		return scripts;
	}



	/**
	 * Hoock method for append custom ajax scripts 
	 * @return
	 */
	protected InternetResource[] getAdditionalScripts() {
		return null;
	}


}
