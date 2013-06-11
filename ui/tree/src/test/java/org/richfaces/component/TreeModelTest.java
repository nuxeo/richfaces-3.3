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
package org.richfaces.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.ajax4jsf.model.DataVisitor;
import org.richfaces.component.xml.XmlNodeData;
import org.richfaces.component.xml.XmlTreeDataBuilder;
import org.richfaces.model.AbstractTreeDataModel;
import org.richfaces.model.ClassicTreeDataModel;
import org.richfaces.model.LastElementAware;
import org.richfaces.model.ListRowKey;
import org.richfaces.model.TreeNode;
import org.xml.sax.InputSource;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 03.04.2007
 * 
 */
public class TreeModelTest extends TestCase {
	private TreeNode node = null;
	private AbstractTreeDataModel model = new ClassicTreeDataModel();

	protected void setUp() throws Exception {
		super.setUp();
		InputStream stream = this.getClass().getResourceAsStream("/org/richfaces/component/xml/XmlTreeDataBuilderTest.xml");
		try {
			node = XmlTreeDataBuilder.build(new InputSource(stream));
		} finally {
			stream.close();
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		node = null;
		model = null;
	}

	public void testBaseProperties() {
		assertNull(model.getWrappedData());
		model.setWrappedData(node);
		assertSame(model.getWrappedData(), node);

		assertEquals(-1, model.getRowCount());
		assertEquals(-1, model.getRowIndex());

		try {
			model.setRowIndex(1);

			fail();
		} catch (IllegalArgumentException e) {

		}

		model.setRowIndex(-1);
	}

	public void testWalk() throws Exception {
		final Set set = new HashSet();
		set.add("web-app");
		set.add("login-config");
		set.add("auth-method");
		set.add("url-pattern");
		set.add("load-on-startup-id");
		set.add("dispatcher");
		set.add("filter-class");
		set.add("param-value");
		
		model.setWrappedData(node);
		
		model.walk(null, new Walker1(model, set) {
			public void process(FacesContext context, Object rowKey, Object argument)
			throws IOException {
				model.setRowKey(rowKey);
				
				XmlNodeData data = (XmlNodeData) model.getRowData();
				if (data == null || set.contains(data.getName())) {
					assertTrue(isLast());
				} else {
					assertFalse(isLast());
				}
			}
		}, null, null);
		
		List<String> list = new ArrayList<String>();
		list.add("testId");
		list.add("displayName");
		model.setRowKey(new ListRowKey<String>(list));
		assertEquals("tree-demo", ((XmlNodeData) model.getRowData()).getText());
	
		List<String> list1 = new ArrayList<String>();
		list1.add("testId");
		list1.add("1");
		list1.add("1");
		model.setRowKey(new ListRowKey<String>(list1));
		assertEquals(".xhtml", ((XmlNodeData) model.getRowData()).getText());
	}

	public void testWalkConstrained() throws Exception {
		model.setWrappedData(node);
		
		List<String> list = new ArrayList<String>();
		list.add("testId");
		list.add("4");
		
		ListRowKey<String> key = new ListRowKey<String>(list);
		
		Walker2 walker = new Walker2(model);
		model.walk(null, walker, null, key, null, false);
	
		List walked = walker.getWalked();
		assertEquals(3, walked.size());
		
		model.setRowKey(key);
		assertSame(walked.get(0), model.getTreeNode());

		model.setRowKey(new ListRowKey<String>(key, "0"));
		assertSame(walked.get(1), model.getTreeNode());

		model.setRowKey(new ListRowKey<String>(key, "1"));
		assertSame(walked.get(2), model.getTreeNode());
	}

}

abstract class Walker1 implements DataVisitor, LastElementAware {

	private boolean last = false;
	private AbstractTreeDataModel model;
	private Set lastElementNames = null;

	public Walker1(AbstractTreeDataModel model, Set lastElementNames) {
		super();
		this.model = model;
		this.lastElementNames = lastElementNames;
	}

	public void resetLastElement() {
		this.last = false;
	}

	public void setLastElement() {
		this.last = true;
	}

	public boolean isLast() {
		return last;
	}
}

class Walker2 implements DataVisitor {
	private List walked = new ArrayList();
	private AbstractTreeDataModel model;
	
	public Walker2(AbstractTreeDataModel model) {
		super();
		this.model = model;
	}

	public void process(FacesContext context, Object rowKey, Object argument)
			throws IOException {

		model.setRowKey(rowKey);
		walked.add(model.getTreeNode());
	}

	public List getWalked() {
		return walked;
	}
}
