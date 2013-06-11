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

package org.ajax4jsf.renderkit.compiler;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.ajax4jsf.Messages;

/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:49 $
 * 
 */
public class ChildrensElement extends ElementBase {
	public static final String CURRENT_CHILD = "child_to_render";

	private String iteratorName;
	
	
	public String getIteratorName() {
		return iteratorName;
	}

	public void setIteratorName(String iteratorVar) {
		this.iteratorName = iteratorVar;
		
	}

	public class IteratorWrapper implements Iterator{
		private Iterator iterator;
		private int index = -1;
		
		public IteratorWrapper(Iterator iterator) {
			this.iterator = iterator;
		}
		public Iterator getIterator() {
			return iterator;
		}
		public boolean hasNext() {
			return iterator.hasNext();
		}
		public Object next() {
			Object o = iterator.next();
			index++;
			return o;
		}
		public void remove() {
			iterator.remove();
		}
		public boolean isLast(){
			return !iterator.hasNext();
		}
		public int getIndex() {
			return index;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#encode(org.ajax4jsf.renderkit.compiler.TemplateContext)
	 */
	public void encode(TemplateContext context) throws IOException {
		UIComponent stored = (UIComponent) context.getParameter(CURRENT_CHILD);
//		Collection children = context.getComponent().getChildren();
		IteratorWrapper iter =new IteratorWrapper(Collections.EMPTY_LIST.iterator());
		Object value = getValue(context);
		if(value != null){
			if (value instanceof UIComponent) {
				UIComponent comp = (UIComponent) value;
				iter = new IteratorWrapper(comp.getChildren().iterator());
			} else if (value instanceof Collection) {
				iter = new IteratorWrapper( ((Collection) value).iterator() );
			} else if (value instanceof String) {
				String componentId = (String) value;
				UIComponent comp = context.getComponent().findComponent(componentId);
				if (null != comp) {
					iter = new IteratorWrapper(comp.getChildren().iterator());
				}
			} else if (value instanceof Iterator) {
				iter = new IteratorWrapper((Iterator) value);
			}
		} else {
			iter = new IteratorWrapper(context.getComponent().getChildren().iterator());
		}
		// For all childrens, iterate over template.
//		IteratorWrapper iter = new IteratorWrapper(children.iterator());
		if(iteratorName != null){
			context.putParameter(iteratorName, iter);
		}
		while (iter.hasNext()) {
			UIComponent child = (UIComponent) iter.next();

			if ( child.isRendered()) {
				if (getChildren().size()>0) {
					// Store facet to render in <u:child> element.
					// TODO - create new templatecontext with current iteration component ?
					context.putParameter(CURRENT_CHILD, child);
					// In fact, render all childrens.
					super.encode(context);
				} else {
					// empty element - render children as-is
					context.getRenderer().renderChild(context.getFacesContext(),child);					
				}
			}
		}
		if(iteratorName != null){
			context.removeParameter(iteratorName);
		}
		if (null != stored) {
			context.putParameter(CURRENT_CHILD, stored);
		} else {
			context.removeParameter(CURRENT_CHILD);
		}
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#encode(org.ajax4jsf.renderkit.compiler.TemplateContext, java.lang.String)
	 */
	public void encode(TemplateContext context, String breakPoint) throws IOException {
		// TODO Auto-generated method stub
		throw new FacesException(Messages.getMessage(Messages.BREAKPOINTS_UNSUPPORTED_ERROR_2));
	}

	public String getTag() {
		return HtmlCompiler.NS_UTIL_PREFIX+HtmlCompiler.CHILDREN_TAG;
	}

}
