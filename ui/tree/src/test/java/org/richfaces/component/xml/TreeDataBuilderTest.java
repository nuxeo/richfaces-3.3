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
package org.richfaces.component.xml;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import org.richfaces.model.TreeNode;
import org.xml.sax.InputSource;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 30.03.2007
 * 
 */
public class TreeDataBuilderTest extends TestCase {
	private TreeNode treeNode = null;
	
	protected void setUp() throws Exception {
		super.setUp();

		InputStream stream = this.getClass().getResourceAsStream("/org/richfaces/component/xml/XmlTreeDataBuilderTest.xml");
		try {
			treeNode = XmlTreeDataBuilder.build(new InputSource(stream));
		} finally {
			stream.close();
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		treeNode = null;
	}

	public void testIds() {
		assertNotNull(treeNode);
		
		new Visitor() {
			public void visit(Object key, TreeNode node) {
				XmlNodeData data = (XmlNodeData) node.getData();
				if (data == null) {
					return ;
				}
				
				if ("servlet-name-id".equals(data.getName())) {
					assertEquals("1", key);
				} else if ("servlet-class-id".equals(data.getName())) {
					assertEquals("0", key);
				} else if ("load-on-startup-id".equals(data.getName())) {
					assertEquals("2", key);
				} else if ("description".equals(data.getName())) {
					assertEquals("Tree demo", data.getText());
				} else if ("web-app".equals(data.getName())) {
					assertNotNull(node.toString());
					assertEquals("", data.getText());
					assertEquals("urn:testing", data.getNamespace());
					
					Map attributes = data.getAttributes();
					assertEquals("2.4", attributes.get("version"));
					assertEquals("testId", attributes.get("id"));
					assertEquals(2, attributes.size());
				}

				assertNotNull(data.toString());
			}
		}.accept(null, treeNode);
	}
}

abstract class Visitor {
	public void accept(Object key, TreeNode node) {
		this.visit(key, node);
		Iterator iterator = node.getChildren();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			
			this.accept(entry.getKey(), (TreeNode) entry.getValue()); 
		}
	}
	
	public abstract void visit(Object key, TreeNode node);
}
