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
package org.ajax4jsf.bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.model.DataComponentState;
import org.richfaces.model.Ordering;

@SuppressWarnings("unchecked")
public class DataTableBean {

	private int rows = 6;
	
	private Ordering ordering = Ordering.UNSORTED;

	private List model;
	
	private ArrayList<String> prioritList = new ArrayList<String>();
	
	private boolean filterMethodTurnOn = false;
	
	private boolean rowConverterTurnOn = false; 
	
	private Set<Integer> ajaxKeys = new HashSet<Integer>();
	
	private Object componentState;
	

	private final Comparator<Data> comparator = new Comparator<Data>(){

		public int compare(Data o1, Data o2) {
			return o1.v3.compareTo(o2.v3);
		};
	};
	
	public class RowKeyConverter implements Converter {

		public Object getAsObject(FacesContext context, UIComponent component,
				String value) {
			// TODO Auto-generated method stub
			return null;
		}

		public String getAsString(FacesContext context, UIComponent component,
				Object value) {
			if (!rowConverterTurnOn) {
				return value.toString();
			}else {
				return value.toString() + value.toString();
			}
		}
		
	};
	
	private RowKeyConverter rowKeyConverter = new RowKeyConverter();
	
	public boolean filterMethod(Object o) {
		if (!filterMethodTurnOn) {
			return true;
		}
		Data data = (Data)o;
		return data.v5 <= 5;
	}
	
	public class Data {
		Integer v1;
		Integer v2;
		Integer v3;
		Integer v4;
		Integer v5;
		
		String input;
		
		public Data(Integer v1, Integer v2,Integer v3,Integer v4, Integer v5) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.v4 = v4;
			this.v5 = v5;
		}

		/**
		 * @return the v1
		 */
		public Integer getV1() {
			return v1;
		}

		/**
		 * @param v1 the v1 to set
		 */
		public void setV1(Integer v1) {
			this.v1 = v1;
		}

		/**
		 * @return the v2
		 */
		public Integer getV2() {
			return v2;
		}

		/**
		 * @param v2 the v2 to set
		 */
		public void setV2(Integer v2) {
			this.v2 = v2;
		}

		/**
		 * @return the v3
		 */
		public Integer getV3() {
			return v3;
		}

		/**
		 * @param v3 the v3 to set
		 */
		public void setV3(Integer v3) {
			this.v3 = v3;
		}

		/**
		 * @return the v4
		 */
		public Integer getV4() {
			return v4;
		}

		/**
		 * @param v4 the v4 to set
		 */
		public void setV4(Integer v4) {
			this.v4 = v4;
		}

		/**
		 * @return the v5
		 */
		public Integer getV5() {
			return v5;
		}

		/**
		 * @param v5 the v5 to set
		 */
		public void setV5(Integer v5) {
			this.v5 = v5;
		}

		/**
		 * @return the input
		 */
		public String getInput() {
			return input;
		}

		/**
		 * @param input the input to set
		 */
		public void setInput(String input) {
			this.input = input;
		}
		
		
		
	}
	
	public DataTableBean() {
		init();
	}
	
	public void init(ActionEvent event) {
		init();
	}
	
	public void reset() {
		rows = 6;
		filterMethodTurnOn = false;
		rowConverterTurnOn = false;
		prioritList = new ArrayList<String>();
		ordering = Ordering.UNSORTED;
		init();
	}
	
	public String testASCSorting() {
		prioritList.clear();
		prioritList.add("Column1");
		ordering = Ordering.ASCENDING;
		return null;
	}
	
	public String testDESCSorting() {
		prioritList.clear();
		prioritList.add("Column1");

		ordering = Ordering.DESCENDING;
		return null;
	}
	
	public String testFilterMethod() {
		filterMethodTurnOn = true;
		return null;
	}
	
	public String testRowKeyConverter() {
		rowConverterTurnOn = true;
		return null;
	}
	
	public String testComponentState() throws Exception {
		if (!(componentState instanceof DataComponentState)) {
			throw new Exception("");
		}
		return null;
	}
	
	
	public String getDate() {
		return new Date().toLocaleString();
	}

	private void init() {
		model = new ArrayList();
		for (int i = 0; i < rows; i++) {
			Data data = new Data(i, i + 1, i + 2, i + 3, i + 4);
			model.add(data);
		}
		ajaxKeys.add(0);
		ajaxKeys.add(2);
		ajaxKeys.add(4);
	}


	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * @return the model
	 */
	public List getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(List model) {
		this.model = model;
	}

	/**
	 * @return the comparator
	 */
	public Comparator<Data> getComparator() {
		return comparator;
	}

	/**
	 * @return the ordering
	 */
	public Ordering getOrdering() {
		return ordering;
	}

	/**
	 * @param ordering the ordering to set
	 */
	public void setOrdering(Ordering ordering) {
		this.ordering = ordering;
	}

	/**
	 * @return the prioritList
	 */
	public ArrayList<String> getPrioritList() {
		return prioritList;
	}

	/**
	 * @param prioritList the prioritList to set
	 */
	public void setPrioritList(ArrayList<String> prioritList) {
		this.prioritList = prioritList;
	}

	
	/**
	 * @return the ajaxKeys
	 */
	public Set<Integer> getAjaxKeys() {
		return ajaxKeys;
	}

	/**
	 * @param ajaxKeys the ajaxKeys to set
	 */
	public void setAjaxKeys(Set<Integer> ajaxKeys) {
		this.ajaxKeys = ajaxKeys;
	}

	/**
	 * @return the componentState
	 */
	public Object getComponentState() {
		return componentState;
	}

	/**
	 * @param componentState the componentState to set
	 */
	public void setComponentState(Object componentState) {
		this.componentState = componentState;
	}

	
	/**
	 * @return the rowKeyConverter
	 */
	public RowKeyConverter getRowKeyConverter() {
		return rowKeyConverter;
	}

	/**
	 * @param rowKeyConverter the rowKeyConverter to set
	 */
	public void setRowKeyConverter(RowKeyConverter rowKeyConverter) {
		this.rowKeyConverter = rowKeyConverter;
	}

}
