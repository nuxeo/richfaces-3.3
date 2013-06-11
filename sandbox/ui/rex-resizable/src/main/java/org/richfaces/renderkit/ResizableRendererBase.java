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
package org.richfaces.renderkit;

import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.richfaces.component.UIResizable;
import javax.faces.component.UIComponent;

public class ResizableRendererBase extends HeaderResourcesRendererBase {
	protected Class getComponentClass() {
		return UIResizable.class;
	}

	protected void formParamList(UIComponent component,String[] vals,
									StringBuffer strb, boolean quoted) {
		
		for (int i=0; i < vals.length; i++) {
       	 String val = (String)component.getAttributes().get(vals[i]); 
       	 if (! "".equals(val) ) {
       		 if (strb.length()!=0)strb.append(",");
       		 	strb.append(vals[i]);
       		 	strb.append(":");
       		 	if (quoted) strb.append("'"); 
       		 	strb.append(val);
       		 	if (quoted) strb.append("'"); 
       	 }
        }

	}

}
