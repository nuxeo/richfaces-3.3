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
package org.richfaces.renderkit;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.ComponentVariables;
import org.ajax4jsf.renderkit.ComponentsVariableResolver;
import org.richfaces.component.Draggable;
import org.richfaces.component.Dropzone;
import org.richfaces.component.UITree;
import org.richfaces.component.UITreeNode;
import org.richfaces.component.state.TreeState;
import org.richfaces.component.state.events.CollapseNodeCommandEvent;
import org.richfaces.component.state.events.ExpandNodeCommandEvent;
import org.richfaces.component.util.ViewUtil;
import org.richfaces.event.AjaxExpandedEvent;
import org.richfaces.event.AjaxSelectedEvent;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeRowKey;

/**
 * @author Nick - mailto:nbelaevski@exadel.com created 23.11.2006
 * 
 */
public abstract class NodeRendererBase extends CompositeRenderer {

	protected static final String NODE_EXPANDED_INPUT_SUFFIX = "NodeExpanded";
	protected static final String AJAX_EXPANDED_SUFFIX = "AjaxExpanded";

	private static class TreeNodeOptions extends ScriptOptions {

		public TreeNodeOptions(UIComponent component) {
			super(component);
		}

	}

	public NodeRendererBase() {
		super();

		addContributor(DropzoneRendererContributor.getInstance());
		addContributor(DraggableRendererContributor.getInstance());

		addParameterEncoder(DnDParametersEncoder.getInstance());
	}

	protected String getToggleScript(FacesContext context, UITreeNode treeNode, String to) {
		UITree tree = treeNode.getUITree();
		if ("handle".equals(to) && tree.isToggleOnClick() ||
				"mainRow".equals(to) && !tree.isToggleOnClick())
			return "";

		String id = treeNode.getClientId(context);

		StringBuilder script = new StringBuilder();
		script.append("var c = Tree.Item.findComponent(this); if (!c) return; c.");
		boolean expanded = tree.isExpanded();
		if (expanded) {
			script.append("fireCollapsionEvent();");
		} else {
			script.append("fireExpansionEvent();");
		}
		
		if (UITree.SWITCH_AJAX.equals(tree.getSwitchType())) {
			UITreeNode nodeFacet = tree.getNodeFacet();
			JSFunction function = AjaxRendererUtils.buildAjaxFunction(nodeFacet,
					context);
			Map<String, Object> eventOptions = AjaxRendererUtils.buildEventOptions(context,
					nodeFacet);
			Map<Object, Object> parameters = (Map<Object, Object>) eventOptions.get("parameters");
			parameters.remove(id);

			parameters.put(id + NODE_EXPANDED_INPUT_SUFFIX, String.valueOf(!expanded));
			parameters.put(id + AJAX_EXPANDED_SUFFIX, Boolean.TRUE);
			
			function.addParameter(eventOptions);

			StringBuffer buffer = new StringBuffer();
			buffer.append(script);
			buffer.append(";");
			function.appendScript(buffer);
			buffer.append(";");
			return buffer.toString();
			
		} else if (UITree.SWITCH_SERVER.equals(tree.getSwitchType())) {
			String paramName = id + NODE_EXPANDED_INPUT_SUFFIX;
			
			StringBuffer result = new StringBuffer();
			result.append(script);
			result.append(";");
			result.append('{');
			result.append("var form = A4J.findForm(this);");
			result.append("var params = new Object();");
			result.append("params['");
			result.append(paramName);
			result.append("'] = ");
			result.append(!expanded);
			result.append(';');
			result.append("Richfaces.jsFormSubmit('");
			result.append(id);
			result.append("', ");
			result.append("form.id, ");
			result.append("'', ");
			result.append("params);};");

			return result.toString();
		}

		return "";
	}

	public void initializeLines(FacesContext context, UITreeNode treeNode)
	throws IOException {
		ComponentVariables variables = ComponentsVariableResolver.getVariables(
				this, treeNode);

		UITree tree = treeNode.getUITree();

		if (tree.isShowConnectingLines()) {
			if (Boolean.TRUE.equals(treeNode.getAttributes().get("isLastElement"))) {
				variables.setVariable("line", "dr-tree-h-ic-line-last");
			} else {
				variables.setVariable("line", "dr-tree-h-ic-line-node");
			}
			if (tree.isExpanded() && !tree.isLeaf()) {
				variables
				.setVariable("lineFirst", "dr-tree-h-ic-line-exp");
			} else {
				variables.setVariable("lineFirst",
				"dr-tree-h-ic-line-clp");
			}
		}
	}

	public void initializeResources(FacesContext context, UITreeNode treeNode)
	throws IOException {
		ComponentVariables variables = ComponentsVariableResolver.getVariables(
				this, treeNode);

		UITree tree = treeNode.getUITree();
		String resource;
		String cssClass;
		resource = UITree.DEFAULT_HIGHLIGHTED_CSS_CLASS;
		resource += " rich-tree-node-highlighted";
		cssClass = tree.getHighlightedClass();
		if (cssClass != null) {
			resource += " " + cssClass;
		}
		cssClass = treeNode.getHighlightedClass();
		if (cssClass != null) {
			resource += " " + cssClass;
		}
		variables.setVariable("highlightedClass", resource);

		resource = UITree.DEFAULT_SELECTED_CSS_CLASS;
		resource += " rich-tree-node-selected";
		cssClass = tree.getSelectedClass();
		if (cssClass != null) {
			resource += " " + cssClass;
		}
		cssClass = treeNode.getSelectedClass();
		if (cssClass != null) {
			resource += " " + cssClass;
		}
		variables.setVariable("selectedClass", resource);


		resource = ViewUtil.getResourceURL(treeNode.getIcon());			
		if (resource == null) {
			resource = ViewUtil.getResourceURL(tree.getIcon());			
		}

		if (resource != null) {
			variables.setVariable("folderIcon", resource);
		}

		resource = ViewUtil.getResourceURL(treeNode.getIconCollapsed());
		if (resource == null) {
			resource = ViewUtil.getResourceURL(tree.getIconCollapsed());
		}

		if (resource != null) {
			variables.setVariable("collapsed", resource);
		}

		resource = ViewUtil.getResourceURL(treeNode.getIconExpanded());
		if (resource == null) {
			resource = ViewUtil.getResourceURL(tree.getIconExpanded());
		}

		if (resource != null) {
			variables.setVariable("expanded", resource);
		}

		resource = ViewUtil.getResourceURL(treeNode.getIconLeaf());
		if (resource == null) {
			resource = ViewUtil.getResourceURL(tree.getIconLeaf());
		}

		if (resource != null) {
			variables.setVariable("leafIcon", resource);
		}
	}

	private String getHandleDisplayStyle(FacesContext context, UITreeNode node, boolean expandHandle) {
		if (node.getUITree().isExpanded() ^ expandHandle) {
			return "display: none;";
		}

		return "";
	}

	public String getHandleCollapsedDisplayStyle(FacesContext context, UITreeNode node) {
		return getHandleDisplayStyle(context, node, false);
	}

	public String getHandleExpandedDisplayStyle(FacesContext context, UITreeNode node) {
		return getHandleDisplayStyle(context, node, true);
	}

	public String getExpandedValue(FacesContext context, UITreeNode node) {
		return Boolean.toString(node.getUITree().isExpanded());
	}

	protected void doDecode(FacesContext context, UIComponent component) {
		UITreeNode node = (UITreeNode) component;
		UITree tree = node.getUITree();
		TreeRowKey<?> key = (TreeRowKey<?>) tree.getRowKey();
		Map<String, String> requestMap = context.getExternalContext().getRequestParameterMap();
		String id = node.getClientId(context);
		TreeState componentState = (TreeState) tree.getComponentState();

		String nodeExpandedId = id + NODE_EXPANDED_INPUT_SUFFIX;
		Object nodeExpandedValue = requestMap.get(nodeExpandedId);
		if (nodeExpandedValue != null) {
			boolean nodeExpanded = Boolean.valueOf(nodeExpandedValue.toString()).booleanValue();
			if (tree.isExpanded() ^ nodeExpanded) {
				if (nodeExpanded) {
					new ExpandNodeCommandEvent(tree, key).queue();
				} else {
					new CollapseNodeCommandEvent(tree, key).queue();
				}

				if (Boolean.valueOf((String) requestMap.get(id + AJAX_EXPANDED_SUFFIX)).booleanValue()) {
					new AjaxExpandedEvent(node).queue();
					new AjaxExpandedEvent(tree).queue();
				} else {
					new NodeExpandedEvent(node).queue();
					new NodeExpandedEvent(tree).queue();
				}
			}
		}

		if (id.equals(tree.getAttributes()
				.get(UITree.SELECTION_INPUT_ATTRIBUTE))) {
			if (!componentState.isSelected(key)) {

				if (tree.getAttributes().get(
						UITree.SELECTED_NODE_PARAMETER_NAME) == null) {

					new NodeSelectedEvent(tree, componentState.getSelectedNode()).queue();
					new NodeSelectedEvent(node, componentState.getSelectedNode()).queue();
				} else {
					new AjaxSelectedEvent(tree, componentState.getSelectedNode()).queue();
					new AjaxSelectedEvent(node, componentState.getSelectedNode()).queue();
				}
			}

			tree.getAttributes().remove(UITree.SELECTION_INPUT_ATTRIBUTE);
		}
		
		super.doDecode(context, component);
	}

	public String getAjaxSelectedListenerFlag(FacesContext context,
			UITreeNode treeNode) throws IOException {
		if (treeNode.hasAjaxSubmitSelection()) {
			return "ajax_selected_listener_flag";
		}

		return null;
	}
	
	private void convertOptions(Map<String, Object> opts) {
		//converts JSFunctionDefinition to string
		Iterator<Entry<String, Object>> iterator = opts.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			Object value = entry.getValue();
			if (value instanceof JSFunctionDefinition) {
				JSFunctionDefinition definition = (JSFunctionDefinition) value;
				entry.setValue(definition.toScript());
			}
		}
	}

	private ScriptOptions createOptions(FacesContext context, UITreeNode component, Class<?> clazz) {
		ScriptOptions options = new TreeNodeOptions(component);
		mergeScriptOptions(options, context, component, clazz);
		convertOptions(options.getMap());
		return options;
	}
	
	protected static final class DnDOptionsHolder {
		private String dragOptions;
		private String dropOptions;

		private String dragCursorOptions;
		private String dropCursorOptions;
		
		public DnDOptionsHolder(String dragOptions, String dragCursorOptions,
				String dropOptions, String dropCursorOptions) {
			super();
			this.dragOptions = dragOptions;
			this.dragCursorOptions = dragCursorOptions;
			this.dropOptions = dropOptions;
			this.dropCursorOptions = dropCursorOptions;
		}
		
		public String getDragOptions() {
			return dragOptions;
		}
		public String getDropOptions() {
			return dropOptions;
		}
		public String getDragCursorOptions() {
			return dragCursorOptions;
		}
		public String getDropCursorOptions() {
			return dropCursorOptions;
		}
	};
	
	protected DnDOptionsHolder getScriptOptions(FacesContext context,
			UITreeNode component) {
		
		ScriptOptions dragOptions = createOptions(context, component, Draggable.class);
		ScriptOptions dropOptions = createOptions(context, component, Dropzone.class);

		return new DnDOptionsHolder(ScriptUtils.toScript(dragOptions), null,
				ScriptUtils.toScript(dropOptions), null);
	}
}
