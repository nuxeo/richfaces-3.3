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

package org.ajax4jsf.util.style;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Maksim Kaszynski
 *
 */
public class FontFamily {
	public static final String CSS_SANS_SERIF = "SANS-SERIF";
	public static final String JAVA_SANS_SERIF = "SANSSERIF";
	public static final String CSS_MONOSPACED = "MONOSPACE";
	public static final String JAVA_MONOSPACED = "MONOSPACED";
	
	private static final String[] fontFamilies = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	static{
		for (int i = 0; i < fontFamilies.length; i++) {
			fontFamilies[i] = fontFamilies[i].toUpperCase();
			//System.out.println(fontFamilies[i]);
		}
		Arrays.sort(fontFamilies);
	}
	
	public static Font getFont(String fontFamily, int style, int size){
		String fontUsed = null;
		StringTokenizer tokenizer = new StringTokenizer(fontFamily, ",");
		while(tokenizer.hasMoreElements()){
			String fontName = tokenizer.nextToken().trim().toUpperCase();
			if(fontName.equals(CSS_SANS_SERIF)){
				fontName = JAVA_SANS_SERIF;
			} else if(fontName.equals(CSS_MONOSPACED)){
				fontName = JAVA_MONOSPACED;
			}
			
			if(Arrays.binarySearch(fontFamilies, fontName) >= 0){
				fontUsed = fontName;
			}
		}
		if(fontUsed == null){
			fontUsed = JAVA_SANS_SERIF;
		}
		Font f = new Font(fontUsed, style, size);
		return f;
	}
}
