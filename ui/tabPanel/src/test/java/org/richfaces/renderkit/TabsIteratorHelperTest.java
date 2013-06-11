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

package org.richfaces.renderkit;

import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UITab;
import org.richfaces.renderkit.TabPanelRendererBase.TabsIteratorHelper;

public class TabsIteratorHelperTest extends AbstractAjax4JsfTestCase {

	public TabsIteratorHelperTest(String name) {
		super(name);
	}

	private List tabsList;
	
	public void setUp() throws Exception {
		super.setUp();
		
		tabsList = new ArrayList();
		
		for (int i = 0; i < 3; i++) {
			UITab tab = (UITab) application.createComponent(UITab.COMPONENT_TYPE);
			tab.setName("tab" + i);
			tabsList.add(tab);
		}
	}

	public void tearDown() throws Exception {
		super.tearDown();
		tabsList = null;
	}

	public void testFindByName() throws Exception {
		TabsIteratorHelper helper = new TabsIteratorHelper(tabsList.iterator(), "tab2");
		assertSame(tabsList.get(2), helper.getTab());
		assertFalse(helper.isFallback());
	}
	
	public void testFindByName1() throws Exception {
		TabsIteratorHelper helper = new TabsIteratorHelper(tabsList.iterator(), "taba");
		assertSame(tabsList.get(0), helper.getTab());
		assertTrue(helper.isFallback());
	}

	public void testFindAnyTabDefault() throws Exception {
		TabsIteratorHelper helper = new TabsIteratorHelper(tabsList.iterator(), null);
		assertSame(tabsList.get(0), helper.getTab());
		assertFalse(helper.isFallback());
	}

	public void testFindAnyTab() throws Exception {
		((UITab) tabsList.get(0)).setDisabled(true);
		((UITab) tabsList.get(1)).setDisabled(true);

		TabsIteratorHelper helper = new TabsIteratorHelper(tabsList.iterator(), "taba");
		assertSame(tabsList.get(2), helper.getTab());
		assertTrue(helper.isFallback());
	}

	public void testFindAnyTabUnnamed() throws Exception {
		((UITab) tabsList.get(0)).setDisabled(true);

		TabsIteratorHelper helper = new TabsIteratorHelper(tabsList.iterator(), null);
		assertSame(tabsList.get(1), helper.getTab());
		assertFalse(helper.isFallback());
	}

	public void testFindAnyTabAllDisabled() throws Exception {
		((UITab) tabsList.get(0)).setDisabled(true);
		((UITab) tabsList.get(1)).setDisabled(true);
		((UITab) tabsList.get(2)).setDisabled(true);

		TabsIteratorHelper helper = new TabsIteratorHelper(tabsList.iterator(), null);
		assertSame(tabsList.get(0), helper.getTab());
		assertFalse(helper.isFallback());
	}

	public void testFindTabByNameDisabled() throws Exception {
		((UITab) tabsList.get(0)).setDisabled(true);
		((UITab) tabsList.get(1)).setDisabled(true);
		((UITab) tabsList.get(2)).setDisabled(true);

		TabsIteratorHelper helper = new TabsIteratorHelper(tabsList.iterator(), "tab1");
		assertSame(tabsList.get(1), helper.getTab());
		assertFalse(helper.isFallback());
	}

	public void testFindTabByNameBeforeDisabled() throws Exception {
		((UITab) tabsList.get(0)).setDisabled(true);
		((UITab) tabsList.get(2)).setDisabled(true);

		TabsIteratorHelper helper = new TabsIteratorHelper(tabsList.iterator(), "tab2");
		assertSame(tabsList.get(1), helper.getTab());
		assertTrue(helper.isFallback());
	}
}
