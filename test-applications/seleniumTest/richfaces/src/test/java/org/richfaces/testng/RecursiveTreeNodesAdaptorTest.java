/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.richfaces.testng;

import org.ajax4jsf.template.Template;
import org.testng.Assert;
import org.testng.annotations.Test;



public class RecursiveTreeNodesAdaptorTest extends AbstractTreeNodesAdaptorTest {

	private String includedRoot;
	
	@Override
	protected void init(Template template) {
		super.init(template);
		includedRoot = attrForm + ":includedRoot";
	}
    /**
     *  includedRoot expression is handled properly
     */
	@Test
	public void testIncludedRoot(Template template) {
        init(template);
		String xpath = "id('"+ tree + "')/div";
		clickAjaxCommandAndWait("xpath=" + xpath + "/table[2]/tbody/tr/td[1]/div/a");
		String childrenXpath = "id('"+ tree + "')/div/table";
		Assert.assertEquals(selenium.getXpathCount(childrenXpath), nodes.size());
		clickAjaxCommandAndWait(includedRoot);
		Assert.assertEquals(selenium.getXpathCount(childrenXpath), 0);		
	}
	
	@Override
	public String getTestUrl() {
		return "pages/treeNodesAdaptor/recursiveTreeNodesAdaptor.xhtml";
	}

}
