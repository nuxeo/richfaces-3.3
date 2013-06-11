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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.ajax4jsf.builder.config.ParsingException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author Maksim Kaszynski
 *
 */
public class XMLBodyMerge implements NodeList{
	
	private String xpath;
	
	private List<Node> nodes = new ArrayList<Node>();
	
	private Document document = null;
	
	private XPathExpression keyXpath = null;
	
	private StringBuffer content = new StringBuffer();
	
	private Set<String> keys = new HashSet<String>();
	
	
	
	public XMLBodyMerge(String xpath) {
		super();
		this.xpath = xpath;
	}
	
	public XMLBodyMerge(String xpath, String keyXpath) {
		this(xpath);
		if (keyXpath != null) {
			try {
				XPath newXPath = XPathFactory.newInstance().newXPath();
				this.keyXpath = newXPath.compile(keyXpath);
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void add(Node node) {
		
		if (keyXpath != null) {
			String key = getKey(node);
			if (key == null || keys.contains(key)) {
				return;
			}
		}
		
		if (document == null) {
			document = node.getOwnerDocument();
		} else {
			node = document.importNode(node, true);
		}
		nodes.add(node);
	}
	
	public void add(XMLBody xmlBody) throws ParsingException {
		
		if (xpath != null) {
			NodeList nodeList = xmlBody.getByXpath(xpath);
			if (nodeList != null) {
				for(int i = 0; i < nodeList.getLength(); i++) {
					add(nodeList.item(i));
				}
			}
		} else {
			content.append(xmlBody.getContent());
		}
		
	}
	
	public int getLength() {
		return nodes.size();
	}
	
	public Node item(int index) {
		if (index < nodes.size()) {
			return nodes.get(index);
		}
		return null;
	}
	
	
	public void sort(Comparator<Node> comparator) {
		Collections.sort(nodes, comparator);
	}
	
	public String getContent() throws Exception{
		StringBuilder buf = new StringBuilder();
		if (content != null) {
			buf.append(content);
		}
		if (document != null) {
			buf.append(new XMLBodySerializer().serialize(this, document));
		}
		
		return buf.toString();
	}
	
	private String getKey(Node node) {
		try {
			NodeList list = (NodeList) keyXpath.evaluate(node, XPathConstants.NODESET);
			return new XMLBodySerializer().serialize(list, node.getOwnerDocument());
		} catch (Exception e) {
		}
		return null;
	}
}
