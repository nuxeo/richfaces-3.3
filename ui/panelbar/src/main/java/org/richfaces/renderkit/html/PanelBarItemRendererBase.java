/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces.renderkit.html;

import org.ajax4jsf.renderkit.RendererBase;
import org.richfaces.component.UIPanelBarItem;

public abstract class PanelBarItemRendererBase extends RendererBase {

	protected Class getComponentClass() {
		return UIPanelBarItem.class;
	}

	//TODO by nick - nick - is it a must?
	public boolean getRendersChildren() {
		return true;
	}

	//TODO by nick - nick - review sacral 1.82 number
/*	public String headerHeight(FacesContext context, UIComponent component) throws IOException {
		int h = (HtmlDimensions.decode((String)getSkin(context).getParameter(context, 
				"headerSizeFont"))).intValue();
				return Integer.toString((int)(h*1.82));
	}*/
	
/*	public String headerSpacer(FacesContext context, UIComponent component) throws IOException {
		Skin skin = SkinFactory.getInstance().getSkin(context);
		String hs = (String)skin.getParameter(context,"panelbarHeaderSpacer");
		if (hs != null) {
			return "margin-top: "+hs;
		} else return "margin-top: 1px"; 
	}*/
}
