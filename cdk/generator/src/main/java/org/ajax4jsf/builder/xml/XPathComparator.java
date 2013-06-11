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

package org.ajax4jsf.builder.xml;

import java.util.Comparator;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;

/**
 * @author Maksim Kaszynski
 *
 */
public class XPathComparator implements Comparator<Node> {

	class XPathCompatorCriterion {
		private XPathExpression expression = null;
		
		public XPathCompatorCriterion(String xPath){
			try {
				expression = XPathFactory.newInstance().newXPath().compile(xPath);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public String getValue(Object node) throws XPathExpressionException {
			return expression == null ? null : expression.evaluate(node);
		}
	}

	private XPathCompatorCriterion [] criteria;
	
	public XPathComparator(String ... criteria) {
		this.criteria = new XPathCompatorCriterion[criteria.length];
		for(int i = 0; i < criteria.length; i++) {
			this.criteria[i] = new XPathCompatorCriterion(criteria[i]);
		}
	}
	
	public int compare(Node o1, Node o2) {
		int result = 0;
		
		for(int i = 0; i < criteria.length && result == 0; i++) {
			String s1 = null;
			String s2 = null;
			try {
				s1 = this.criteria[i].getValue(o1);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				s2 = this.criteria[i].getValue(o2);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (s1 != null) {
				if (s2 != null) {
					result = s1.compareTo(s2);
				} else {
					result = 1;
				}
			} else if (s2 != null) {
				result = -1; 
			}
		}
		
		return result;
	}

}
