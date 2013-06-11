package org.docs.tree;

import java.util.Set;
import java.util.TreeSet;

import javax.faces.context.FacesContext;

import org.richfaces.component.xml.XmlTreeDataBuilder;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.xml.sax.InputSource;

public class TreeBean {
	private TreeNode<String> data;

	public TreeBean() {
		
		String[] components = {"< a4j:ajaxListener >", "< a4j:keepAlive >", "< a4j:actionparam >" };
		String[][] attributes = {{"type"},
								 {"ajaxOnly", "beanName"},
								 {"actionListener", "assignTo", "binding", "converter", "id", "name", "noEscape", "value"}};
		
		if (data == null) {
			data = new TreeNodeImpl<String>();
			
			for (int i = 0; i < components.length; i++) {
				TreeNode<String> child = new TreeNodeImpl<String>();
				child.setData(components[i]);
				data.addChild(components[i], child);
				
				for (int j = 0; j < attributes[i].length; j++) {
					TreeNode<String> grandChild = new TreeNodeImpl<String>();
					grandChild.setData(attributes[i][j]);
					child.addChild(attributes[i][j], grandChild);
				}
			}
		}
	}
	
	public TreeNode<String> getData() {
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
	
}
