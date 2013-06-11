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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RulesBase;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 16.11.2006
 * 
 */
public class XmlTreeDataBuilder {
	
	private final static class Rule extends org.apache.commons.digester.Rule {
		private int level = -1;
		private List idsList = new ArrayList();
		private List treeNodesList = new ArrayList();
		private List exclusionSets = new ArrayList();
		private TreeNode treeNode = new TreeNodeImpl(); //add empty node to serve as root
		
		public void begin(String namespace, String name, Attributes attributes)
				throws Exception {
			super.begin(namespace, name, attributes);

			level++;
			
			XmlNodeData xmlNodeData = new XmlNodeData();
			xmlNodeData.setName(name);
			xmlNodeData.setNamespace(namespace);

			String id = null;
			
			if (attributes != null) {
				int length = attributes.getLength();
				for (int i = 0; i < length; i++) {
					xmlNodeData.setAttribute(attributes.getQName(i),
							attributes.getValue(i));
					
				}
				
				id = attributes.getValue("id");
			}
			
			if (exclusionSets.size() == level) {
				exclusionSets.add(null);
			}

			if (id == null || id.length() == 0) {
				int currentId = 0;

				if (idsList.size() <= level) {
					for (int i = idsList.size(); i <= level; i++) {
						idsList.add(null);
					}
				} else {
					Integer integer = (Integer) idsList.get(level);
					currentId = integer.intValue() + 1;
				}

				Set exclusions = (Set) exclusionSets.get(level);
				
				while (exclusions != null && exclusions.contains(Integer.toString(currentId))) {
					currentId++;
				}
				
				idsList.set(level, new Integer(currentId));

				id = Integer.toString(currentId);
			} else {
				Set exclusions = (Set) exclusionSets.get(level);
				if (exclusions == null) {
					exclusions = new HashSet();
				
					exclusionSets.set(level, exclusions);
				}
				
				exclusions.add(id);
			}
			
			TreeNode node = new TreeNodeImpl();
			node.setData(xmlNodeData);
		
			this.treeNode.addChild(id, node);
			this.treeNodesList.add(this.treeNode);
			this.treeNode = node;
		}
		
		public void body(String namespace, String name, String text)
				throws Exception {
			super.body(namespace, name, text);
			
			if (text != null) {
				((XmlNodeData) this.treeNode.getData()).setText(text.trim());
			}
		}
		
		public void end(String namespace, String name) throws Exception {
			super.end(namespace, name);
			
			level--;
			
			if (idsList.size() - 1 > level + 1) {
				//idsList grew larger than we really need
				idsList.remove(idsList.size() - 1);
			}

			if (exclusionSets.size() - 1 > level + 1) {
				//the same condition as above
				exclusionSets.remove(exclusionSets.size() - 1);
			}

			this.treeNode = (TreeNode) this.treeNodesList.remove(this.treeNodesList.size() - 1);
		}
	}
	
	public static TreeNode build(InputSource inputSource) throws SAXException, IOException {
		Digester digester = new Digester(); 
		Rule rule = new Rule();
		final List rulesList = new ArrayList(1);
		rulesList.add(rule);
		
		RulesBase rulesBase = new RulesBase() {
			protected List lookup(String namespace, String name) {
				return rulesList;
			}
		};
		digester.setRules(rulesBase);
		digester.setNamespaceAware(true);
		digester.parse(inputSource);
		
		return rule.treeNode;
	}
}
