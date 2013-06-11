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

import java.util.Iterator;

import javax.faces.component.UIComponent;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.iterators.FilterIterator;
import org.apache.commons.collections.iterators.IteratorChain;
import org.apache.commons.collections.iterators.TransformIterator;

/**
 * @author shura
 *
 */
public abstract class UIDataDefinitionList extends UIDataList {
	
	/**
	 * @author shura
	 *
	 */
	private final class FacetTransformer implements Transformer {
		public Object transform(Object input) {
			// TODO Auto-generated method stub
			return getFacet((String) input);
		}
	}

	private static final Predicate fixedPredicate = new Predicate(){

		public boolean evaluate(Object input) {
			// TODO Auto-generated method stub
			return !"term".equals(input);
		}
		
	};

	private static final Predicate termsPredicate = new Predicate(){

		public boolean evaluate(Object input) {
			// TODO Auto-generated method stub
			return "term".equals(input);
		}
		
	};
	/* (non-Javadoc)
	 * @see org.ajax4jsf.ajax.repeat.UIRepeat#fixedChildren()
	 */
	protected Iterator<UIComponent> fixedChildren() {
		return filteredFacets(fixedPredicate);
	}

	/**
	 * Create iterafor on component facets, filtered by Predicate
	 * @param predicate - filter for select facets names.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Iterator<UIComponent> filteredFacets(Predicate predicate) {
		FilterIterator fixed= new FilterIterator(getFacets().keySet().iterator(),predicate);
		
		return new TransformIterator(fixed,new FacetTransformer());
	}
	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.ajax.repeat.UIRepeat#dataChildren()
	 */
	@SuppressWarnings("unchecked")
	protected Iterator<UIComponent> dataChildren() {
		// TODO Auto-generated method stub
		return new IteratorChain(getChildren().iterator(),filteredFacets(termsPredicate));
	}

}
