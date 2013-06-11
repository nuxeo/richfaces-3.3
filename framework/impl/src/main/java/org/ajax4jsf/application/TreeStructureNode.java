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
package org.ajax4jsf.application;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author asmirnov
 * 
 */
final class TreeStructureNode implements Externalizable {
	/**
	 * TODO - implement Externalizable to reduce serialized state.
	 */
	private static final long serialVersionUID = -9038742487716977912L;

	private static final String NULL_ID = "";

	private List<FacetEntry> facets = null;

	private List<TreeStructureNode> children = null;

	private String type;

	private String id;

	public TreeStructureNode() {
	}

	public void apply(FacesContext context, UIComponent component,
			Set<String> uniqueIds) {
		type = component.getClass().getName();
		id = component.getId();
		String clientId = component.getClientId(context);
		if (!uniqueIds.add(clientId)) {
			throw new IllegalStateException("duplicate Id for a component "
					+ clientId);
		}
		Map<String, UIComponent> componentFacets = component.getFacets();
		for (Iterator<Entry<String, UIComponent>> i = componentFacets
				.entrySet().iterator(); i.hasNext();) {
			Entry<String, UIComponent> element = i.next();
			UIComponent f = element.getValue();
			if (!f.isTransient()) {
				TreeStructureNode facet = new TreeStructureNode();
				facet.apply(context, f, uniqueIds);
				if (null == facets) {
					facets = new ArrayList<FacetEntry>(componentFacets.size());
				}
				facets.add(new FacetEntry(element.getKey(), facet));

			}
		}
		List<UIComponent> componentChildren = component.getChildren();
		for (Iterator<UIComponent> i = componentChildren.iterator(); i
				.hasNext();) {
			UIComponent child = i.next();
			if (!child.isTransient()) {
				TreeStructureNode t = new TreeStructureNode();
				t.apply(context, child, uniqueIds);
				if (null == children) {
					children = new ArrayList<TreeStructureNode>(
							componentChildren.size());
				}
				children.add(t);

			}
		}
	}

	public UIComponent restore(ComponentsLoader loader) {
		UIComponent component;
		component = loader.createComponent(type);
		component.setId(id);
		if (null != facets) {
			for (Iterator<FacetEntry> i = facets.iterator(); i.hasNext();) {
				FacetEntry element = i.next();
				UIComponent facet = (element.getNode()).restore(loader);
				component.getFacets().put(element.getName(), facet);
			}

		}
		if (null != children) {
			for (Iterator<TreeStructureNode> i = children.iterator(); i
					.hasNext();) {
				TreeStructureNode node = i.next();
				UIComponent child = node.restore(loader);
				component.getChildren().add(child);
			}

		}
		return component;
	}

	/**
	 * @return the facets
	 */
	public List<FacetEntry> getFacets() {
		return facets;
	}


	/**
	 * @return the children
	 */
	public List<TreeStructureNode> getChildren() {
		return children;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		type = in.readUTF();
		id = in.readUTF();
		if (NULL_ID.equals(id)) {
			id = null;
		}
		int facetsSize = in.readInt();
		if (facetsSize > 0) {
			facets = new ArrayList<FacetEntry>(facetsSize);
			for (int i = 0; i < facetsSize; i++) {
				String facetName = in.readUTF();
				TreeStructureNode facet = new TreeStructureNode();
				facet.readExternal(in);
				facets.add(new FacetEntry(facetName, facet));
			}
		}
		int childrenSize = in.readInt();
		if (childrenSize > 0) {
			children = new ArrayList<TreeStructureNode>(childrenSize);
			for (int i = 0; i < childrenSize; i++) {
				TreeStructureNode child = new TreeStructureNode();
				child.readExternal(in);
				children.add(child);
			}
		}
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(type);
		out.writeUTF(null == id ? NULL_ID : id);
		if (null != facets) {
			out.writeInt(facets.size());
			for (Iterator<FacetEntry> i = facets.iterator(); i.hasNext();) {
				FacetEntry entry = i.next();
				out.writeUTF(entry.getName());
				TreeStructureNode node = entry.getNode();
				node.writeExternal(out);
			}

		} else {
			out.writeInt(0);
		}
		if (null != children) {
			out.writeInt(children.size());
			for (Iterator<TreeStructureNode> i = children.iterator(); i
					.hasNext();) {
				TreeStructureNode child = i.next();
				child.writeExternal(out);
			}

		} else {
			out.writeInt(0);
		}
	}

	@SuppressWarnings("serial")
	static final class FacetEntry implements Externalizable {
		private String name;
		private TreeStructureNode node;

		public String getName() {
			return name;
		}

		public TreeStructureNode getNode() {
			return node;
		}

		/**
		 * @param name
		 * @param node
		 */
		public FacetEntry(String name, TreeStructureNode node) {
			super();
			this.name = name;
			this.node = node;
		}

		public void readExternal(ObjectInput in) throws IOException,
				ClassNotFoundException {
			this.name = in.readUTF();
			this.node = new TreeStructureNode();
			this.node.readExternal(in);
		}

		public void writeExternal(ObjectOutput out) throws IOException {
			out.writeUTF(name);
			node.writeExternal(out);
		}
	}
}