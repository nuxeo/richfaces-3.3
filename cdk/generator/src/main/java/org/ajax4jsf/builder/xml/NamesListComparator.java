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

package org.ajax4jsf.builder.xml;

import java.util.Comparator;

import org.w3c.dom.Node;

/**
 * @author Nick Belaevski
 * @since 3.2.2
 */

public class NamesListComparator implements Comparator<Node> {

	private Comparator<Node> comparator;
	private String[] names;
	
	public NamesListComparator(Comparator<Node> comparator, String[] names) {
		super();
		this.comparator = comparator;
		this.names = names;
	}

	private final int getOrder(String s) {
		for (int i = 0; i < names.length; i++) {
			if (names[i].equals(s)) {
				return i;
			}
		}
		
		return -1;
	}

	private String getNodeName(Node node) {
		String name = node.getLocalName();
		if (name == null) {
			name = node.getNodeName();
		}
		
		return name;
	}
	
	public int compare(Node o1, Node o2) {
		String name1 = getNodeName(o1);
		String name2 = getNodeName(o2);
		
		if (name1 != null && name2 != null && !name1.equals(name2)) {
			int order1 = getOrder(name1);
			if (order1 < 0) {
				System.out.println("Tag: " + name1 + " is unknown!");
			}
			
			int order2 = getOrder(name2);
			if (order2 < 0) {
				System.out.println("Tag: " + name2 + " is unknown!");
			}

			if (order1 < order2) {
				return -1;
			} else if (order1 > order2) {
				return 1;
			}
		}
		
		return comparator.compare(o1, o2);
	}

}
