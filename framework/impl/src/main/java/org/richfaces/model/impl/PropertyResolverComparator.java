/*
 *  Copyright
 *      Copyright (c) Exadel,Inc. 2006
 *      All rights reserved.
 *  
 *  History
 *      $Source: /cvs-master/intralinks-jsf-comps/components/data-view-grid/src/component/com/exadel/jsf/model/impl/PropertyResolverComparator.java,v $
 *      $Revision: 1.2 $ 
 */

package org.richfaces.model.impl;

import java.util.Comparator;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyResolver;

import org.richfaces.model.SortField;
import org.richfaces.model.SortOrder;



/**
 * @author Maksim Kaszynski
 *
 */
public class PropertyResolverComparator implements Comparator {
	
	private static SortField [] EMPTY = {};
	
	private PropertyResolver resolver;

	private SortOrder sortOrder;
	
	public PropertyResolverComparator(SortOrder sortOrder) {
		ApplicationFactory factory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
		Application application = factory.getApplication();
		resolver = application.getPropertyResolver();
		this.sortOrder = sortOrder;
	}
	
	public int compare(Object o1, Object o2) {
		int result = 0;
		
		SortField [] fields = sortOrder == null ? EMPTY : sortOrder.getFields();
		
		for(int i = 0; i < fields.length && result == 0; i++) {
			SortField field = fields[i];
			String name = field.getName();
			Boolean asc = field.getAscending();
				
			if (name != null && asc != null) {
				Object property1 = null;
				Object property2 = null;
				
				property1 = resolver.getValue(o1, name);
				
				property2 = resolver.getValue(o2, name);
				
				if (property1 instanceof Comparable && property2 instanceof Comparable) {
					result = ((Comparable) property1).compareTo(property2);
				}
				
				if (!asc.booleanValue()) {
					result = -result;
				}
				
				
			}
			
		}
		
		
		return result;
	}

}
