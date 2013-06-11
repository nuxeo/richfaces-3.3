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
package org.richfaces.renderkit.html.images;

import java.awt.Color;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.util.HtmlColor;
import org.richfaces.renderkit.html.images.MenuNodeImage.MenuNodeImageData;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * @author Anton Belevich
 *
 */
public class MenuNodeImageTest extends AbstractAjax4JsfTestCase {

	public MenuNodeImageTest(String name) {
		super(name);
	}
	
	public void testSaveResources(){
		
		MenuNodeImage img = new MenuNodeImage();
	
		Skin skin = SkinFactory.getInstance().getSkin(facesContext);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(facesContext);
		
		String skinParameter1 = "generalTextColor";
		String generalTextColor = (String) skin.getParameter(facesContext, skinParameter1);
		
		if (null == generalTextColor || "".equals(generalTextColor))
			generalTextColor = (String) defaultSkin.getParameter(facesContext, skinParameter1);
		
		generalTextColor = generalTextColor == null ? "#4A75B5": generalTextColor;
				
		Color color1 = HtmlColor.decode(generalTextColor);
		
		String skinParameter2 = "tabDisabledTextColor";
		
		String textColor = (String) skin.getParameter(facesContext, skinParameter2);
		
		if (null == textColor || "".equals(textColor))
			textColor = (String) defaultSkin.getParameter(facesContext, skinParameter2);
		
		textColor = textColor == null ? "#6A92CF" : textColor;
			
		Color color2 = HtmlColor.decode(textColor);
			
		byte [] data = (byte []) img.getDataToStore(facesContext, null);
		
		assertNotNull(data);
		
		MenuNodeImageData nodeData = (MenuNodeImageData)img.deserializeData(data);
		
		assertNotNull(nodeData);
		assertEquals(color1, new Color(nodeData.getGeneralColor().intValue())); 
		assertEquals(color2, new Color(nodeData.getDisabledColor().intValue()));
	}	 

}
