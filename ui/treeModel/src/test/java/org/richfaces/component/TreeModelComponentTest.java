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

package org.richfaces.component;

import java.io.InputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilderFactory;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;

/**
 * Unit test for simple Component.
 */
public class TreeModelComponentTest 
    extends AbstractAjax4JsfTestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TreeModelComponentTest( String testName )
    {
        super( testName );
    }

    private UIComponent createOutput(String vb) {
    	UIComponent component = application.createComponent(UITreeNode.COMPONENT_TYPE);
    	UIOutput output = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
    	output.setValueBinding("value", application.createValueBinding(vb));
    	
    	component.getChildren().add(output);
    	return component;
    }
    
    private Document document;
    
    private void clearDocument(Node node) {
    	Node child = node.getFirstChild();
    	while (child != null) {
    		Node sibling = child.getNextSibling();
    		if (child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().trim().length() == 0) {
    			child.getParentNode().removeChild(child);
    		} else {
    			clearDocument(child);
    		}
    		
    		child = sibling;
    	}
    }

    private Node findNode(String id, Node node) {
    	Node n = node.getFirstChild();
    	while (n != null) {
    		NamedNodeMap attributes = n.getAttributes();
    		if (attributes != null) {
    			if (id.equals(attributes.getNamedItem("id").getNodeValue())) {
    				return n;
    			}
    		}
    		
			Node result = findNode(id, n);
			if (result != null) {
				return result;
			}

			n = n.getNextSibling();
    	}
    	
    	return null;
    }
    
    public void setUp() throws Exception {
    	super.setUp();

    	facesContext.getExternalContext().getApplicationMap().put("XPathMap", new TreeModelComponentTestMap());
    	facesContext.getExternalContext().getApplicationMap().put("XPathStringMap", new TreeModelComponentTestMap());
    	InputStream stream = TreeModelComponentTest.class.getResourceAsStream("tree-model-data.xml");
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	document = factory.newDocumentBuilder().parse(stream);
		clearDocument(document);
		facesContext.getExternalContext().getApplicationMap().put("nodes", document.getDocumentElement());
    
		UIViewRoot viewRoot = facesContext.getViewRoot();
		UIComponent form = application.createComponent(UIForm.COMPONENT_TYPE);
		viewRoot.getChildren().add(form);
		UITree tree = (UITree) application.createComponent(UITree.COMPONENT_TYPE);
		tree.setSwitchType("client");
		tree.setId("tree");
		form.getChildren().add(tree);
		
		UITreeNodesAdaptor project = (UITreeNodesAdaptor) application.createComponent(UITreeNodesAdaptor.COMPONENT_TYPE);
		project.setVar("project");
		project.setValueBinding("nodes", application.createValueBinding("#{nodes}"));
		project.getChildren().add(createOutput("#{XPathStringMap['$project/@id']}"));
		project.setId("project");
		
		tree.getChildren().add(project);
    
		UIRecursiveTreeNodesAdaptor directories = (UIRecursiveTreeNodesAdaptor) application.createComponent(UIRecursiveTreeNodesAdaptor.COMPONENT_TYPE);
		directories.setVar("dir");
		directories.setValueBinding("roots", application.createValueBinding("#{XPathMap['$project/directory']}"));
		directories.setValueBinding("nodes", application.createValueBinding("#{XPathMap['$dir/directory']}"));
		directories.getChildren().add(createOutput("#{XPathStringMap['$dir/@id']}"));
		directories.setId("dir");
		
		project.getChildren().add(directories);

		UITreeNodesAdaptor files = (UITreeNodesAdaptor) application.createComponent(UITreeNodesAdaptor.COMPONENT_TYPE);
		files.setVar("file");
		files.setValueBinding("nodes", application.createValueBinding("#{XPathMap['$dir/file']}"));
		files.getChildren().add(createOutput("#{XPathStringMap['$file/@id']}"));
		files.setId("files");
		
		directories.getChildren().add(files);
		
		UIRecursiveTreeNodesAdaptor archives = (UIRecursiveTreeNodesAdaptor) application.createComponent(UIRecursiveTreeNodesAdaptor.COMPONENT_TYPE);
		archives.setVar("archiveEntry");
		archives.setValueBinding("roots", application.createValueBinding("#{XPathMap['$dir/archive']}"));
		archives.setValueBinding("nodes", application.createValueBinding("#{XPathMap['$archiveEntry/archiveEntry']}"));
		archives.getChildren().add(createOutput("#{XPathStringMap['$archiveEntry/@id']}"));
		archives.setId("archives");
		
		directories.getChildren().add(archives);
    }

    public void tearDown() throws Exception {
    	facesContext.getExternalContext().getApplicationMap().remove("XPathMap");
    	facesContext.getExternalContext().getApplicationMap().remove("XPathStringMap");
    	facesContext.getExternalContext().getApplicationMap().remove("nodes");

    	super.tearDown();

    	this.document = null;
    }
    
    public void testRender() throws Exception {
		HtmlPage renderedView = renderView();
		List htmlElementsByTagName = renderedView.getDocumentElement().getHtmlElementsByTagName("table");
		
		Iterator allHtmlChildElements = htmlElementsByTagName.iterator();
		int counter = 0;
		
		while (allHtmlChildElements.hasNext()) {
			HtmlTable table = (HtmlTable) allHtmlChildElements.next();
			HtmlTableCell handleCell = (HtmlTableCell) table.getCellAt(0, 0);
			HtmlTableCell iconCell = (HtmlTableCell) table.getCellAt(0, 1);
			HtmlTableCell textCell = (HtmlTableCell) table.getCellAt(0, 2);
			
			Element element = (Element) findNode(textCell.asText(), document);
			//skip text siblings
			Node nextSibling = element.getNextSibling();
			
			//System.out.println(table);
			//System.out.println(handleCell);
			//System.out.println(textCell.asText() + " " + textCell);
			//System.out.println(element);

			if (nextSibling == null) {
				assertTrue(handleCell.getAttributeValue("class").contains("dr-tree-h-ic-line-last"));
			} else {
				assertTrue(handleCell.getAttributeValue("class").contains("dr-tree-h-ic-line-node"));
			}
			
			HtmlElement handleElement = (HtmlElement) handleCell.getFirstChild().getFirstChild();
			String handleId = handleElement.getAttributeValue("id");
			
			if (element.getChildNodes().getLength() == 0) {
				assertTrue(handleId.endsWith(":handle:img"));
			} else {
				assertTrue(handleId.endsWith(":handle"));
			}
			
			counter++;
		}
		
		assertEquals(30, counter);
    }
}

class TreeModelComponentTestMap extends AbstractMap {

	public Object get(Object key) {
		String s = (String) key;
		int idx = s.indexOf('/');
		String varName = s.substring(1, idx);
		String path = s.substring(idx + 1);
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Object var = facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, varName);
		
		if (path.startsWith("@")) {
			return ((Node) var).getAttributes().getNamedItem(path.substring(1)).getNodeValue();
		} else {
			NodeList nodeList = (NodeList) var;
			final ArrayList nodes = new ArrayList();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (path.equals(node.getNodeName())) {
					nodes.add(node);
				}
			}
			
			return new NodeList() {

				public int getLength() {
					return nodes.size();
				}

				public Node item(int index) {
					return (Node) nodes.get(index);
				}
				
			};
		}
	}
	
	public Set entrySet() {
		return null;
	}
	
}
