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

package org.richfaces.rf4351;

import java.util.Set;
import java.util.TreeSet;

import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */

public class Bean {

	private static final String[] escapedChars = new String[] {
		"<", ">", "&", "\"", "'", ":", "_"
	};
	
	private TreeNode data;
	
	public TreeNode getData() {
		if (data == null) {
			data = new TreeNodeImpl();
			
			for (int i = 0; i < escapedChars.length; i++) {
				TreeNode child = new TreeNodeImpl();
				child.setData("Node " + escapedChars[i]);
				data.addChild(escapedChars[i] + " node", child);

				TreeNode grandChild = new TreeNodeImpl();
				grandChild.setData("GrandChild");
				child.addChild("grand & child", grandChild);
			}
		}
		return data;
	}
	
	public static boolean[] checkNameChars() {
		Set<Integer> result = new TreeSet<Integer>();
		boolean[] chars = new boolean[0xFFFF + 1];
		
		result.add(Character.getType(':'));
		chars[':'] = true;
		for (char i = 'A'; i <= 'Z'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		result.add(Character.getType('_'));
		for (char i = 'a'; i <= 'z'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		
		for (char i = '\u00c0'; i <= '\u00d6'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '\u00d8'; i <= '\u00f6'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '\u00f8'; i <= '\u02ff'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '\u0370'; i <= '\u037d'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '\u037f'; i <= '\u1fff'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '\u200c'; i <= '\u200d'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '\u2070'; i <= '\u218f'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '\u2c00'; i <= '\u2fef'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '\u3001'; i <= '\ud7ff'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '\uf900'; i <= '\ufdcf'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '\ufdf0'; i <= '\ufffd'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '-'; i <= '-'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '.'; i <= '.'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '0'; i <= '9'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '\u00b7'; i <= '\u00b7'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '\u0300'; i <= '\u036f'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}
		for (char i = '\u203f'; i <= '\u2040'; i++) {
			result.add(Character.getType(i));
			chars[i] = true;
		}

		System.out.println(result);
		
		return chars;
	}
	
	public static void checkChars(boolean[] skipSet) {
		Set<Integer> result = new TreeSet<Integer>();
		for (char c = '\u0000'; c < '\uffff'; c++) {
			if (!skipSet[c]) {
				result.add(Character.getType(c));
			}
		}
		System.out.println(result);
	}
	
	public static void main(String[] args) {
		boolean[] skipChars = checkNameChars();
		System.out.println("Bean.main()");
		checkChars(skipChars);
	}
	
//	4]   	NameStartChar	   ::=   	":" | [A-Z] | "_" | [a-z] | [#xC0-#xD6] | 
//	[#xD8-#xF6] | [#xF8-#x2FF] | [#x370-#x37D] | [#x37F-#x1FFF] | [#x200C-#x200D] | 
//	[#x2070-#x218F] | [#x2C00-#x2FEF] | [#x3001-#xD7FF] | [#xF900-#xFDCF] |
//	[#xFDF0-#xFFFD] | [#x10000-#xEFFFF]
//	     	             	         	                                                                                                                                                                                                                
//	                   [4a]   	NameChar	   ::=   	NameStartChar | "-" | "." | [0-9] | 
//	                   #xB7 | [#x0300-#x036F] | [#x203F-#x2040]
}
