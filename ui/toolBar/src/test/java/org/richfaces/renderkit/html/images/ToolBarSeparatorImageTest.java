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
import org.richfaces.component.html.HtmlToolBar;
import org.richfaces.renderkit.html.images.ToolBarSeparatorImage.SeparatorData;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * @author Anton Belevich
 *
 */
public class ToolBarSeparatorImageTest extends AbstractAjax4JsfTestCase {

	public ToolBarSeparatorImageTest(String name) {
		super(name);
	}
	
	public void testSaveResources(){
		
		ToolBarSeparatorImage img = new DotSeparatorImage();
		
		Skin skin = SkinFactory.getInstance().getSkin(facesContext);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(facesContext);
		
		String skinParameter1 = "headerBackgroundColor";
		String headerBackgroundColor = (String) skin.getParameter(facesContext, skinParameter1);
		
		if (null == headerBackgroundColor || "".equals(headerBackgroundColor))
			headerBackgroundColor = (String) defaultSkin.getParameter(facesContext, skinParameter1);
		
		headerBackgroundColor = headerBackgroundColor == null ? "#224986" : headerBackgroundColor;
					
		Color color1 = HtmlColor.decode(headerBackgroundColor);
		
		String skinParameter2 = "headerGradientColor";
		String headerGradientColor = (String) skin.getParameter(facesContext, skinParameter2);
		
		if (null == headerGradientColor || "".equals(headerGradientColor))
			headerGradientColor = (String) defaultSkin.getParameter(facesContext, skinParameter2);
		
		headerGradientColor = headerGradientColor == null ? "#CCCCFF" : headerGradientColor;
		
		Color color2 = HtmlColor.decode(headerGradientColor);
		
		String skinParameter3 = "headerTextColor";
		String headerTextColor = (String) skin.getParameter(facesContext, skinParameter3);
		
		if (null == headerTextColor || "".equals(headerTextColor))
			headerTextColor = (String) defaultSkin.getParameter(facesContext, skinParameter3);
		
		headerTextColor = headerTextColor == null ? "#000000" : headerTextColor;
		
		Color color3 = HtmlColor.decode(headerTextColor);
		
		String skinParameter4 = "headerSizeFont";
		String headerSizeFont = (String) skin.getParameter(facesContext, skinParameter4);
		
		if (null == headerSizeFont || "".equals(headerSizeFont))
			headerSizeFont = (String) defaultSkin.getParameter(facesContext, skinParameter4);
		
		headerSizeFont = headerSizeFont == null ? "11px" : headerSizeFont;
		
		headerSizeFont = headerSizeFont.substring(0, headerSizeFont.indexOf("px"));
		
		HtmlToolBar toolBar = new HtmlToolBar();
		
		byte [] data = (byte []) img.getDataToStore(facesContext, toolBar);
		assertNotNull(data);
		SeparatorData separatorData = (SeparatorData)img.deserializeData(data);		
		
		assertNotNull(separatorData);
		assertEquals(color1, new Color(separatorData.getHeaderBackgroundColor()));
		assertEquals(color2, new Color(separatorData.getHeaderGradientColor()));
		assertEquals(color3, new Color(separatorData.getHeaderTextColor()));
		assertEquals(Integer.parseInt(headerSizeFont), separatorData.getFontSize());
		
	}
}
