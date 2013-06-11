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

import javax.faces.context.FacesContext;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.util.HtmlColor;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * Unit test for Progress bar component.
 */
public class ProgressBarImagesTest extends AbstractAjax4JsfTestCase {

    
    public ProgressBarImagesTest(String name) {
    	super(name);
    }


    public void testColorUtils() throws Exception {
    	float[] hsl = ColorUtils.RGBtoHSL(100, 160, 120);
    	assertEquals(ColorUtils.HSLtoRGB(hsl[0], hsl[1], hsl[2]), new Color(101, 159, 120));
    	hsl = ColorUtils.RGBtoHSL(0, 0, 0);
    	assertEquals(ColorUtils.HSLtoRGB(hsl[0], hsl[1], hsl[2]), new Color(0, 0, 0));
    	hsl = ColorUtils.RGBtoHSL(201, 203, 1);
    	assertEquals(ColorUtils.HSLtoRGB(hsl[0], hsl[1], hsl[2]), new Color(168, 169, 35));
    	assertNotNull(ColorUtils.adjustBrightness(Color.BLACK, 1));
    	assertNotNull(ColorUtils.adjustLightness(Color.BLACK, 1));
    	assertNotNull(ColorUtils.overwriteAlpha(Color.BLACK, 1));
    }
    
    public void testProgressBarBg() throws Exception {
    	ProgressBarBg bg = new ProgressBarBg();
	
		Skin skin = SkinFactory.getInstance().getSkin(facesContext);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(facesContext);
		
		String backgroundColor = getSkinParameter(skin, defaultSkin, facesContext, "selectControlColor");		
		assertNotNull(backgroundColor);
				
		byte [] data = (byte []) bg.getDataToStore(facesContext, null);
		
		assertNotNull(data);	
		byte[] results = (byte[])bg.deserializeData(data);		
		assertNotNull(results);
		
		assertEquals(data, results);
    }
    
    public void testProgressBarAnimatedBg() throws Exception {
    	ProgressBarAnimatedBg bg = new ProgressBarAnimatedBg();
	
		Skin skin = SkinFactory.getInstance().getSkin(facesContext);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(facesContext);
		
		String backgroundColor = getSkinParameter(skin, defaultSkin, facesContext, "selectControlColor");		
		assertNotNull(backgroundColor);
				
		byte [] data = (byte []) bg.getDataToStore(facesContext, null);
		
		assertNotNull(data);	
		byte[] results = (byte[])bg.deserializeData(data);		
		assertNotNull(results);
		
		assertEquals(data, results);
		assertEquals(0, bg.getRepeat());
		assertEquals(12, bg.getNumberOfFrames());
    }
    
    private String getSkinParameter(Skin skin, Skin defaultSkin, FacesContext context, String parameterName) {	
		String value = (String) skin.getParameter(context, parameterName);
		if (null == value || "".equals(value)) {
			value = (String) defaultSkin.getParameter(context, parameterName);
		}
		return value;

	}

}
