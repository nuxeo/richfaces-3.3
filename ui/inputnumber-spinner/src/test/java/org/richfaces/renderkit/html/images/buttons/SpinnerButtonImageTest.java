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

package org.richfaces.renderkit.html.images.buttons;

import java.awt.Color;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.util.HtmlColor;
import org.richfaces.renderkit.html.images.SpinnerButtonImage;
import org.richfaces.renderkit.html.images.SpinnerButtonUp;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * @author Anton Belevich
 *
 */
public class SpinnerButtonImageTest extends AbstractAjax4JsfTestCase {

	
	public SpinnerButtonImageTest(String name) {
		super(name);
	}
	
	public void testSaveResources(){
		
		SpinnerButtonImage img = new SpinnerButtonUp();
	
		Skin skin = SkinFactory.getInstance().getSkin(facesContext);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(facesContext);
		
		String skinParameter = "headerTextColor";
		String headerTextColor = (String) skin.getParameter(facesContext, skinParameter);
		
		if (null == headerTextColor || "".equals(headerTextColor))
			headerTextColor = (String) defaultSkin.getParameter(facesContext, skinParameter);
		
		headerTextColor = headerTextColor == null ? "#000000" : headerTextColor;
		
		Color color1 = HtmlColor.decode(headerTextColor);
		
/*		byte [] data = (byte []) img.getDataToStore(facesContext, null);
		
		assertNotNull(data);
		
		Integer results = (Integer)img.deserializeData(data);
	
		assertNotNull(results);
		
		Color color2 = new Color(results.intValue());
			
		assertEquals(color1, color2);
*/
	}	

}
