package org.ajax4jsf.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class DataListBean implements Validator{
	
	public class Row {
		
		private String cell1;
		private String cell2;
		private String cell3;

		public Row(String index) {
			this.cell1 = index + "A";
			this.cell2 = index + "B";
			this.cell3 = index + "C";
		}

		public String getCell1() {
			return cell1;
		}

		public void setCell1(String cell1) {
			this.cell1 = cell1;
		}

		public String getCell2() {
			return cell2;
		}

		public void setCell2(String cell2) {
			this.cell2 = cell2;
		}

		public String getCell3() {
			return cell3;
		}

		public void setCell3(String cell3) {
			this.cell3 = cell3;
		}		
	}

	private List<Row> model;
	private int rows;
	private String trace;
	private Set<Integer> ajaxKeys;
	
	public DataListBean() {
		init();
	}

	public void init() {
		model = new ArrayList<Row>(10);
		for (int i = 0; i < 10; i++) {
			model.add(new Row(Integer.toString(i)));
		}
		rows = 0;
		trace = "";
		ajaxKeys = new HashSet<Integer>(10);
		for (int i = 0; i < 10; i++) {
			if (i != 2 && i != 6) {
				ajaxKeys.add(i);
			}
		}
	}

	public void submit(ActionEvent event) {
		trace = event.getComponent().getClientId(FacesContext.getCurrentInstance());
	}

	public void reRender(ActionEvent event) {
		for (Row element : model) {
			element.setCell2("XXX");
		}
	}

	public void setModel(List<Row> model) {
		this.model = model;
	}

	public List<Row> getModel() {
		return model;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRows() {
		return rows;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getTrace() {
		return trace;
	}

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if ("fail".equals(value)) {
			throw new ValidatorException(new FacesMessage("validation failure"));
		}
	}

	public void setAjaxKeys(Set<Integer> ajaxKeys) {
		this.ajaxKeys = ajaxKeys;
	}

	public Set<Integer> getAjaxKeys() {
		return ajaxKeys;
	}

	public Converter getRowKeyConverter() {
		return new Converter(){

			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				return new Integer(value.substring(2));
			}

			public String getAsString(FacesContext context,
					UIComponent component, Object value) {
				return "c_" + value.toString();
			}
			
		};
	}
}
