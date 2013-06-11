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
package org.richfaces.renderkit.html.iconimages;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.util.HtmlColor;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * @author Anton Belevich
 *
 */
public class CalendarIconTest extends AbstractAjax4JsfTestCase {

	public CalendarIconTest(String name) {
		super(name);
	}
	
	public void testSaveResources(){
		
		CalendarIcon icon = new CalendarIcon();
	
		Skin skin = SkinFactory.getInstance().getSkin(facesContext);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(facesContext);
		
		String skinParameter = "headerBackgroundColor";
		String headerTextColor = (String) skin.getParameter(facesContext, skinParameter);
		
		if (null == headerTextColor || "".equals(headerTextColor))
			headerTextColor = (String) defaultSkin.getParameter(facesContext, skinParameter);
		
		assertNotNull(headerTextColor);
		
		Color color1 = HtmlColor.decode(headerTextColor);
		
		skinParameter = "selectControlColor";
		
		String headerBackgroundColor = (String) skin.getParameter(facesContext, skinParameter);
		if (null == headerBackgroundColor || "".equals(headerBackgroundColor))
			headerBackgroundColor = (String) defaultSkin.getParameter(facesContext, skinParameter);
		
		assertNotNull(headerBackgroundColor);
		
		Color color2 = HtmlColor.decode(headerBackgroundColor);
				
		byte [] data = (byte []) icon.getDataToStore(facesContext, null);
		
		assertNotNull(data);
		
		Object [] results = (Object [])icon.deserializeData(data);
		
		assertNotNull(results);
		
		assertEquals(color1, results[0]);
		assertEquals(color2, results[1]);
		BufferedImage img = icon.paintImage(results);
		assertNotNull(img);
	}
	
}
