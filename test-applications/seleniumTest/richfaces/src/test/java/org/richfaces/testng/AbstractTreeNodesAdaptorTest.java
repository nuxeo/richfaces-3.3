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

import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.bean.tree.TreeNodesAdaptorTestBean.Node;
import org.ajax4jsf.template.Template;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;


public abstract class AbstractTreeNodesAdaptorTest extends SeleniumTestBase {

	protected List<Node> nodes;

	protected String attrForm;

    private String ajaxSingle;

	private String includedNode;

	protected String tree;

	private String outputText;
	
	private String dataTable;
	
	protected void init(Template template) {
        renderPage(null, template, "#{treeNodesAdaptor.init}");
		nodes = new ArrayList<Node>(3);
		for (int i = 1; i <= 3; i++) {
			Node node = new Node(Integer.toString(i));
			nodes.add(node);
			for (int j = 1; j <= 3; j++) {
				Node node2 = new Node(Integer.toString(i * 10 + j));
				node.addChild(node2);
				for (int k = 1; k <= 3; k++) {
					node2.addChild(new Node(Integer.toString(i * 100 + j * 10 + k)));
				}
			}
		}
       attrForm = getParentId() + "attrForm";
       ajaxSingle = attrForm + ":ajaxSingle";
       includedNode = attrForm + ":includedNode";
       String mainForm = getParentId() + "mainForm";
       tree = mainForm + ":tree";
       outputText = getParentId() + "outputText";
       dataTable = getParentId() + "dataTable";
    }

    /**
     *  component builds right tree structure: data is fetched from nodes collection
     *  and assigned to var variable
     */
	@Test
	public void testTreeStructure(Template template) {
        init(template);
		String locator = "xpath=id('"+ tree + "')/div";
        for (int i = 0; i < nodes.size(); i++) {
        	Node node = nodes.get(i);
			String rootLocator = locator + "/table[" + (i + 1) + "]/tbody/tr";
			Assert.assertEquals(selenium.getValue(rootLocator + "/td[3]/input[1]"), node.getId());
			clickAjaxCommandAndWait(rootLocator + "/td[1]/div/a");
			String nodesLocator = locator + "/div[" + (i + 1) + "]";
			List<Node> children = node.getChildren();
	        for (int j = 0; j < children.size(); j++) {
				Assert.assertEquals(selenium.getValue(nodesLocator + "/table[" + (j + 1) + "]/tbody/tr/td[3]/input[1]"), children.get(j).getId());
			}
		}
	}
	
	private void checkModel(String nodesLocator, boolean ajaxSingle) {
		String dataTableLocator = "xpath=id('"+ dataTable + "')/tbody/tr[";
        for (int j = 1; j <= 3; j++) {
        	if (ajaxSingle && j != 2) {
        		Assert.assertFalse(selenium.getValue(nodesLocator + "/table[" + j + "]/tbody/tr/td[3]/input[1]").equals(selenium.getText(dataTableLocator + j + "]")));
        	} else {
        		Assert.assertEquals(selenium.getValue(nodesLocator + "/table[" + j + "]/tbody/tr/td[3]/input[1]"), selenium.getText(dataTableLocator + j + "]"));
        	}
        }		
	}
	
    /**
     *  check input and command components processing with ajaxSingle nodes
     */
	@Test
	public void testComponentsProcessing(Template template) {
        init(template);
		String locator = "xpath=id('"+ tree + "')/div";
		String titleLocator = locator + "/table[2]/tbody/tr";
		clickAjaxCommandAndWait(titleLocator + "/td[1]/div/a");
		String nodesLocator = locator + "/div[2]";
		checkModel(nodesLocator, false);
        for (int j = 1; j <= 3; j++) {
        	selenium.type(nodesLocator + "/table[" + j + "]/tbody/tr/td[3]/input[1]", "abc" + j);
		}		
		clickAjaxCommandAndWait(nodesLocator + "/table[1]/tbody/tr/td[1]/div/a");        
		checkModel(nodesLocator, false);
		clickAjaxCommandAndWait(ajaxSingle);
        for (int j = 1; j <= 3; j++) {
        	selenium.type(nodesLocator + "/table[" + j + "]/tbody/tr/td[3]/input[1]", "xyz" + j);
		}		
		clickAjaxCommandAndWait(nodesLocator + "/table[2]/tbody/tr/td[1]/div/a");        
		checkModel(nodesLocator, true);
		String buttonLocator = titleLocator + "/td[3]/input[";
		Assert.assertEquals(selenium.getText(outputText), "");
		selenium.click(buttonLocator + "2]");
		waitForPageToLoad();
		Assert.assertTrue(selenium.getText(outputText).indexOf("1::submit") != -1);
		selenium.click(buttonLocator + "3]");
        waitForAjaxCompletion();
		Assert.assertTrue(selenium.getText(outputText).indexOf("1::ajaxSubmit") != -1);
		selenium.click(buttonLocator + "4]");
        waitForAjaxCompletion();
		Assert.assertTrue(selenium.getText(outputText).indexOf("1::ajaxSingleSubmit") != -1);
	}
	
    /**
     *  includedNode expression is handled properly
     */
	@Test
	public void testIncludedNode(Template template) {
        init(template);
		String xpath = "id('"+ tree + "')/div";
		clickAjaxCommandAndWait("xpath=" + xpath + "/table[2]/tbody/tr/td[1]/div/a");
		String childrenXpath = "id('"+ tree + "')/div" + "/div[2]/table";
		Assert.assertEquals(selenium.getXpathCount(childrenXpath), nodes.get(1).getChildren().size());
		clickAjaxCommandAndWait(includedNode);
		Assert.assertEquals(selenium.getXpathCount(childrenXpath), 0);		
	}
	
}
