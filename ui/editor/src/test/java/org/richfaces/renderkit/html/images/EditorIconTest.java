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
import java.awt.image.BufferedImage;

import javax.faces.context.FacesContext;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.util.HtmlColor;
import org.richfaces.renderkit.html.images.EditorAdvancedThemeIcons;
import org.richfaces.renderkit.html.images.EditorIcons;
import org.richfaces.renderkit.html.images.EditorSimpleThemeIcons;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * Editor icon images resource classes test
 * 
 * @author Alexandr Levkovsky
 *
 */
public class EditorIconTest extends AbstractAjax4JsfTestCase{

	public EditorIconTest(String name) {
		super(name);
	}
	
	/**
	 * Method to test resource classes saving of parameters functionality
	 */
	public void testSaveResources(){
		
		EditorAdvancedThemeIcons advIcon = new EditorAdvancedThemeIcons();
		EditorSimpleThemeIcons simpleIcon = new EditorSimpleThemeIcons();
	
		Skin skin = SkinFactory.getInstance().getSkin(facesContext);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(facesContext);
		
		String additionalBackgroundColor = getSkinParameter(skin, defaultSkin, facesContext, EditorIcons.ADDITIONAL_BACKGROUND_COLOR);
		String selectControlColor = getSkinParameter(skin, defaultSkin, facesContext, 
				EditorIcons.SELECT_CONTROL_COLOR);
		String panelBorderColor = getSkinParameter(skin, defaultSkin, facesContext,  EditorIcons.PANEL_BORDER_COLOR);
		String generalTextColor = getSkinParameter(skin, defaultSkin, facesContext,  EditorIcons.GENERAL_TEXT_COLOR);
		
		assertNotNull(additionalBackgroundColor);
		assertNotNull(selectControlColor);
		assertNotNull(panelBorderColor);
		assertNotNull(generalTextColor);
		
		Color color1 = HtmlColor.decode(additionalBackgroundColor);
		Color color2 = HtmlColor.decode(selectControlColor);
		Color color3 = HtmlColor.decode(panelBorderColor);
		Color color4 = HtmlColor.decode(generalTextColor);
				
		byte [] data = (byte []) advIcon.getDataToStore(facesContext, null);
		
		assertNotNull(data);	
		Object [] results = (Object [])advIcon.deserializeData(data);		
		assertNotNull(results);
		
		assertEquals(color1, results[0]);
		assertEquals(color2, results[1]);
		assertEquals(color3, results[2]);
		assertEquals(color4, results[3]);
		
		data = (byte []) simpleIcon.getDataToStore(facesContext, null);
		
		assertNotNull(data);	
		results = (Object [])simpleIcon.deserializeData(data);		
		assertNotNull(results);
		
		assertEquals(color1, results[0]);
		assertEquals(color2, results[1]);
		assertEquals(color3, results[2]);
		assertEquals(color4, results[3]);
		
		BufferedImage image = advIcon.paintFirstTriangleBlock(results, true);
		assertNotNull(image);
		image = advIcon.paintMainBlock(results, true, true);
		assertNotNull(image);
		image = advIcon.paintSecondTriangleBlock(results, true);
		assertNotNull(image);
		image = advIcon.paintSeparatorBlock(results);
		assertNotNull(image);
		
	}
	
	/**
	 * Method to get skin parameter value by parameter name
	 * 
	 * @param skin - current component skin
	 * @param defaultSkin - default richfaces skin
	 * @param context - faces context instance 
	 * @param parameterName - name of the skin parameter
	 * @return string value of parameter
	 */
	private String getSkinParameter(Skin skin, Skin defaultSkin, FacesContext context, String parameterName) {	
		String value = (String) skin.getParameter(context, parameterName);
		if (null == value || "".equals(value)) {
			value = (String) defaultSkin.getParameter(context, parameterName);
		}
		return value;

	}
}
