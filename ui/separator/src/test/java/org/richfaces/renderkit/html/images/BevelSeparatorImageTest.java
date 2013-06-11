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
import org.richfaces.component.html.HtmlSeparator;
import org.richfaces.renderkit.html.images.BevelSeparatorImage.SeparatorData;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * @author Anton Belevich
 *
 */
public class BevelSeparatorImageTest extends AbstractAjax4JsfTestCase {

	public BevelSeparatorImageTest(String name) {
		super(name);
	}
	
	public void testSaveResources(){
	
		BevelSeparatorImage img = new BevelSeparatorImage();
		
		Skin skin = SkinFactory.getInstance().getSkin(facesContext);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(facesContext);
				
		String skinParameter1 = "interfaceLevel3D";
		String interfaceLevel = (String) skin.getParameter(facesContext, skinParameter1);
		interfaceLevel = interfaceLevel == null ? "5" : interfaceLevel;
		
		int level3D = Integer.parseInt(interfaceLevel);
		
		String skinParameter2 = "headerBackgroundColor";
		String headerBackgroundColor = (String) skin.getParameter(facesContext, skinParameter2);
		
		if (null == headerBackgroundColor || "".equals(headerBackgroundColor))
			headerBackgroundColor = (String) defaultSkin.getParameter(facesContext, skinParameter2);
		
		headerBackgroundColor = headerBackgroundColor == null ? "#4169E1" : headerBackgroundColor;
					
		Color color1 = HtmlColor.decode(headerBackgroundColor);
		
		String skinParameter3 = "overAllBackground";
		String overAllBackgroundColor = (String) skin.getParameter(facesContext, skinParameter3);
		if (null == overAllBackgroundColor || "".equals(overAllBackgroundColor))
			overAllBackgroundColor = (String) defaultSkin.getParameter(facesContext, skinParameter3);
		overAllBackgroundColor = overAllBackgroundColor == null ? "#FFFFFF" : overAllBackgroundColor;
		
		Color color2 = HtmlColor.decode(overAllBackgroundColor);
		
		HtmlSeparator separator = new HtmlSeparator(); 
		
		byte [] data = (byte []) img.getDataToStore(facesContext, separator);
		assertNotNull(data);
		
		SeparatorData separatorData = (SeparatorData)img.deserializeData(data);
		
		assertNotNull(separatorData);
		
		assertEquals(level3D, separatorData.getLevel3D());
		assertEquals(color1, new Color(separatorData.getBgColor()));  
		assertEquals(color2, new Color(separatorData.getGradColor()));
	}

}
