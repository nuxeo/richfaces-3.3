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
package org.richfaces.taglib;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentClassicTagBase;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.IterationTag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.component.UIColumn;
import org.richfaces.component.html.HtmlColumn;
import org.richfaces.el.ELBuilder;
import org.richfaces.iterator.ForEachIterator;
import org.richfaces.iterator.SimpleForEachIterator;
import org.richfaces.renderkit.CellRenderer;

@SuppressWarnings("unused")
public class ColumnsTag extends UIComponentClassicTagBase implements
		IterationTag {

	private static final Log log = LogFactory.getLog(ColumnsTag.class);
	
	/** Data table */
	private UIComponent dataTable;

	private static final String J_GENERATION_SERIES_ATTRIBUTE = "org.richfaces.J_COLUMNS_GENERATION_SERIES";
	
	/** Prefix before id to be assigned for column */
	private static final String COLUMN_ID_PREFIX = "rf";

	/** Flag indicates if columns are already created */
	private boolean created = false;

	/** Current column counter */
	private Integer index = -1;

	/** Iterator for columns's tag value attribute */
	protected ForEachIterator items; // our 'digested' items

	/** Value attribute value */
	protected Object rawItems; // our 'raw' items

	/** End attribute - defines count of column if value attr hasn't been defined */
	private ValueExpression __columns;

	/** Begin attribute - defines the first iteration item */
	private ValueExpression __begin;

	/** Begin attribute - defines the last iteration item */
	private ValueExpression __end;

	/** Index attr - defines page variable for current column counter */
	private ValueExpression _index;

	/** Var attr - defines page variable for current item */
	private String indexId;

	/** Integer value of end attr. */
	private Integer columns;

	/** Integer value of begin attr. */
	private Integer begin;

	/** Integer value of end attr. */
	private Integer end;

	/** String value of var attr */
	private String itemId = null;

	/** Expression for var item */
	private IteratedExpression iteratedExpression;

	/** Column incrementer */
	private Integer counter = 0;
	
	private String jspId;
	
	private String pageId;
	
	/**
	 * style CSS style(s) is/are to be applied when this component is rendered
	 */
	private ValueExpression _style;

	/*
	 * sortBy Attribute defines a bean property which is used for sorting of a
	 * column
	 */
	private ValueExpression _sortBy;

	/**
	 * sortOrder SortOrder is an enumeration of the possible sort orderings.
	 */
	private ValueExpression _sortOrder;

	/**
	 * value The current value for this component
	 */
	private ValueExpression __value;

	/**
	 * var A request-scope attribute via which the data object for the current
	 * row will be used when iterating
	 */
	private ValueExpression __var;

	/**
	 * breakBefore if "true" next column begins from the first row
	 */
	private ValueExpression _breakBefore;

	/**
	 * colspan Corresponds to the HTML colspan attribute
	 */
	private ValueExpression _colspan;

	/**
	 * Dir attr
	 */
	private ValueExpression _dir;

	/*
	 * filterDefaultLabel
	 * 
	 */
	private ValueExpression _filterDefaultLabel;

	/**
	 * comparator
	 * 
	 */
	private ValueExpression _comparator;

	/*
	 * filterEvent Event for filter input that forces the filtration (default =
	 * onchange)
	 */
	private ValueExpression _filterEvent;

	/*
	 * filterExpression Attribute defines a bean property which is used for
	 * filtering of a column
	 */
	private ValueExpression _filterExpression;

	/*
	 * filterMethod
	 * 
	 */
	private MethodExpression _filterMethod;

	/*
	 * filterValue
	 * 
	 */
	private ValueExpression _filterValue;

	/*
	 * filterBy
	 * 
	 */
	private ValueExpression _filterBy;

	/**
	 * footerClass Space-separated list of CSS style class(es) that are be
	 * applied to any footer generated for this table
	 */
	private ValueExpression _footerClass;

	/**
	 * headerClass Space-separated list of CSS style class(es) that are be
	 * applied to any header generated for this table
	 */
	private ValueExpression _headerClass;

	/**
	 * rowspan Corresponds to the HTML rowspan attribute
	 */
	private ValueExpression _rowspan;

	/**
	 * selfSorted
	 * 
	 */
	private ValueExpression _selfSorted;

	/**
	 * sortable Boolean attribute. If "true" it's possible to sort the column
	 * content after click on the header. Default value is "true"
	 */
	private ValueExpression _sortable;

	/**
	 * sortExpression Attribute defines a bean property which is used for
	 * sorting of a column
	 */
	private ValueExpression _sortExpression;

	/**
	 * styleClass Corresponds to the HTML class attribute
	 */
	private ValueExpression _styleClass;

	/*
	 * width Attribute defines width of column. Default value is "100px".
	 */
	private ValueExpression _width;
	
	/**
	 * Attribute defines if component should be rendered.
	 */
	private ValueExpression _rendered;
	
	private ValueExpression _sortIcon;

	private ValueExpression _sortIconAscending;
	
	private ValueExpression _sortIconDescending;

	/**
	 * SortOrder is an enumeration of the possible sort orderings. Setter for
	 * sortOrder
	 * 
	 * @param sortOrder -
	 *            new value
	 */
	public void setSortOrder(ValueExpression __sortOrder) {
		this._sortOrder = __sortOrder;
	}

	/**
	 * Attribute defines a bean property which is used for sorting of a column
	 * Setter for sortBy
	 * 
	 * @param sortBy -
	 *            new value
	 */
	public void setSortBy(ValueExpression __sortBy) {
		this._sortBy = __sortBy;
	}

	/**
	 * CSS style(s) is/are to be applied when this component is rendered Setter
	 * for style
	 * 
	 * @param style -
	 *            new value
	 */
	public void setStyle(ValueExpression __style) {
		this._style = __style;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.webapp.UIComponentClassicTagBase#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		pageId = getId();
		
		prepare();

		if (hasNext()) {
			next();
			exposeVariables();
			super.doStartTag();
		} else {
			return SKIP_BODY;
		}

		return EVAL_BODY_BUFFERED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.webapp.UIComponentClassicTagBase#doAfterBody()
	 */
	@Override
	public int doAfterBody() throws JspException {
		try {
			if (hasNext()) {
				super.doAfterBody();
				super.doEndTag();
				
				setId(pageId);
				
				next();
				exposeVariables();
				super.doStartTag();
				return EVAL_BODY_AGAIN;
			} else {
				super.doAfterBody();
				exposeVariables();
				return EVAL_BODY_INCLUDE;
			}

		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.webapp.UIComponentClassicTagBase#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		if (!atFirst()) {
			return super.doEndTag();
		}
		return EVAL_PAGE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.webapp.UIComponentClassicTagBase#doInitBody()
	 */
	@Override
	public void doInitBody() throws JspException {

	}

	@Override
	protected UIComponent createComponent(FacesContext context, String newId)
			throws JspException {
		UIComponent c = getFacesContext().getApplication().createComponent(
				getComponentType());
		c.setId(newId);
		c.setTransient(false);
		setProperties(c);
		c.getAttributes().put(J_GENERATION_SERIES_ATTRIBUTE, RequestUniqueIdGenerator.generateId(context));
		return c;
	}

	@Override
	protected boolean hasBinding() {
		return false;
	}

	@Override
	protected void setProperties(UIComponent component) {
		ELContext elContext = getContext(pageContext.getELContext());
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				Object o = field.get(this);
				if (o != null && o instanceof ValueExpression) {
					String fieldName = field.getName();
					if (fieldName != null && fieldName.startsWith("_")) {
						String attributeName = fieldName.replace("_", "");
						if (ColumnsAttributes.FILTER_ATTRIBUTES.indexOf(attributeName) == -1 && 
								ColumnsAttributes.SORT_ATTRIBUTES.indexOf(attributeName) == -1) {
							ValueExpression ex = (ValueExpression) o;
							ex = createValueExpression(elContext, ex);
							component.setValueExpression(attributeName, ex);
						} else {
							ValueExpression ex = (ValueExpression) o;
							ValueExpression expr = null;
							if (!ex.isLiteralText()) {
								expr = ELBuilder.createValueExpression(ex.getExpressionString(), ex.getExpectedType(),
										getFacesContext().getApplication().getExpressionFactory(), pageContext.getELContext(), itemId, indexId,
										getVarReplacement(), String.valueOf(index));
							}else {
								expr = ex;
							}
								component.setValueExpression(attributeName, expr);
							}
					}

				}

			} catch (Exception e) {
				continue;
			}
		}  
		// Set filterMethod attribute
		if (_filterMethod != null) {
			MethodExpression mexpr = getFacesContext().getApplication()
					.getExpressionFactory().createMethodExpression(elContext,
							_filterMethod.getExpressionString(), Object.class,
							new Class[] { Object.class });
			((HtmlColumn) component).setFilterMethod(mexpr);
		}
		// Set SortExpression attribute especially for scrollable data table
		if (_sortExpression != null) {
			ValueExpression expr = ELBuilder.createValueExpression(_sortExpression.getExpressionString(), _sortExpression.getExpectedType(),
					getFacesContext().getApplication().getExpressionFactory(), pageContext.getELContext(), itemId, indexId,
					getVarReplacement(), String.valueOf(index));
			component.setValueExpression("sortExpression", expr);
		}
		
		
	}
	
	private String getVarReplacement() {
		if (this.__value == null) {
			return String.valueOf(index);
		} else if (items.getVarReplacement() != null) {
			return items.getVarReplacement();
		}
		String varReplacement = ELBuilder.getVarReplacement(this.__value.getExpressionString())
				+ "[" + index + "]";
		return varReplacement;
	}
	
	/**
	 * Evaluates expression string for SortExpression attribute using resolved vars nodes.   
	 * @return 
	 */
	private String createSortExpression() {
		String expr = null;
		try {
			String orig = _sortExpression.getExpressionString()
					.replaceAll("[\\#\\{\\}]", "").trim();
			String varReplacement = __value.getExpressionString()
					.replaceAll("[#{}]", "")+ "[" + index + "]";

			if (itemId.equals(orig)) {
				expr = varReplacement;
			} else {
				String indexReplacement = String.valueOf(index);
				String varPatter1 = "^" + itemId + "([\\.\\[\\]])";
				String varPatter2 = "([\\s\"$%#\\^|]+)" + itemId
						+ "([\\.\\[\\]])";
				String indexPattern = "([\\[\\]]{1})" + indexId
						+ "([\\[\\]]{1})";

				orig = orig.replaceAll(varPatter1, varReplacement + "$1");
				orig = orig
						.replaceAll(varPatter2, "$1" + varReplacement + "$2");
				orig = orig.replaceAll(indexPattern, "$1" + indexReplacement
						+ "$2");
				expr = orig;
			}
		} catch (Exception e) {
		}
		expr = "#{" + expr + "}";
		return expr;
	}

	/**
	 * Creates value expression to be out into column
	 * 
	 * @param context
	 * @param orig
	 * @return
	 */
	private ValueExpression createValueExpression(ELContext context,
			ValueExpression orig) {
		ValueExpression vexpr = getFacesContext().getApplication()
				.getExpressionFactory().createValueExpression(context,
						orig.getExpressionString(), orig.getExpectedType());
		return vexpr;
	}

	/**
	 * Deletes dynamic rich columns created before
	 */
	private void deleteRichColumns() {
		List<UIComponent> children = dataTable.getChildren();
		Integer generatedId = RequestUniqueIdGenerator.generateId(getFacesContext());
		
		Iterator<UIComponent> it = children.iterator();
		while (it.hasNext()) {
			UIComponent child = it.next();
			
			Object generationSeries = child.getAttributes().get(J_GENERATION_SERIES_ATTRIBUTE);
			if (generationSeries != null && !generationSeries.equals(generatedId)) {
				it.remove();
			}
		}
	}

	/**
	 * Method prepares all we need for starting of tag rendering
	 * 
	 * @throws JspTagException
	 */
	private void prepare() throws JspTagException {
		dataTable = getParentUIComponentClassicTagBase(pageContext)
				.getComponentInstance();
		created = (dataTable.getChildCount() > 0);

		if (created) {
			deleteRichColumns();
		}

		initVariables();

		// produce the right sort of ForEachIterator
		if (__value != null) {
			// If this is a deferred expression, make a note and get
			// the 'items' instance.
			if (__value instanceof ValueExpression) {
				rawItems = __value.getValue(pageContext.getELContext());
			}
			// extract an iterator over the 'items' we've got
			items = SimpleForEachIterator
					.supportedTypeForEachIterator(rawItems);
		} else {
			// no 'items', so use 'begin' and 'end'
			items = SimpleForEachIterator.beginEndForEachIterator(columns - 1);
		}

		correctFirst();

		/*
		 * ResultSet no more supported in <c:forEach> // step must be 1 when
		 * ResultSet is passed in if (rawItems instanceof ResultSet && step !=
		 * 1) throw new JspTagException(
		 * Resources.getMessage("FOREACH_STEP_NO_RESULTSET"));
		 */

	}

	/**
	 * Extracts tags attributes values
	 */
	private void initVariables() {
		initColumnsCount();
		initIndex();
		initVar();
		initBegin();
		initEnd();
	}

	private void initItarationId() {
		jspId = (jspId == null) ? getJspId() : jspId;
		if (jspId != null) {
			setJspId(jspId); // We need it twice!
			setJspId(jspId);
		}
	}

	/**
	 * Extracts integer value from end attr
	 */
	private void initColumnsCount() {
		if (__columns != null) {
			if (__columns instanceof ValueExpression)
				try {
					String t = (String) __columns.getValue(getELContext());
					columns = Integer.parseInt(t);
					if (columns < 0) {
						columns = 0; // If end is negative set up zero
					}
				} catch (Exception e) {
					columns = 0;
				}
		} else {
			columns = 0;
		}
	}

	/**
	 * Extracts string value from var attr
	 */
	private void initVar() {
		if (__var != null) {
			try {
				itemId = (String) __var.getValue(getELContext());
			} catch (ClassCastException e) {
				itemId = null;
			}

		}
	}

	/**
	 * Extracts string value from index attr
	 */
	private void initIndex() {
		if (_index != null) {
			try {
				indexId = (String) _index.getValue(getELContext());
			} catch (ClassCastException e) {
				indexId = null;
			}

		}
	}

	/**
	 * Extracts string value from index attr
	 */
	private void initBegin() {
		begin = 0;
		if (__begin != null) {
			try {
				Object o = __begin.getValue(getELContext());
				if (o instanceof Number) {
					begin = ((Number) o).intValue();
				} else if (o instanceof String) {
					begin = Integer.parseInt((String) o);
				}
				begin--; // correct begin value
				if (begin < 0) {
					begin = 0;
				}
			} catch (ClassCastException e) {
				begin = 0;
			}

		}
	}

	/**
	 * Extracts string value from index attr
	 */
	private void initEnd() {
		end = 0;
		if (__end != null) {
			try {
				Object o = __end.getValue(getELContext());
				if (o instanceof Number) {
					end = ((Number) o).intValue();
				} else if (o instanceof String) {
					end = Integer.parseInt((String) o);
				}
				if (end < 0) {
					end = 0;
				}
			} catch (ClassCastException e) {
				end = 0;
			}

		}
	}

	/**
	 * Inits first iteration item
	 */
	private void correctFirst() {
		try {
			if (items != null) {
				if (begin > 0 && (index < (begin - 1))) {
					while ((index < (begin - 1)) && hasNext()) {
						next();
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			// TODO: handle exception
		}
	}
	
	/**
	 * Return true if we didn't complete column's count
	 * 
	 * @return
	 * @throws JspTagException
	 */
	private boolean hasNext() throws JspTagException {
		if (end != 0) {
			return (index < (end - 1)) ? items.hasNext() : false;
		} else {
			return items.hasNext();
		}
	}

	/**
	 * Iterate to next column
	 * 
	 * @return
	 * @throws JspTagException
	 */
	private Object next() throws JspTagException {
		Object o = items.next();
		initItarationId();
		index++;
		return o;
	}

	/**
	 * Returns true if this is the first loop of columns tag
	 * 
	 * @return
	 */
	private boolean atFirst() {
		return (index == begin - 1);
	}

	private String generateColumnId() {
		String id = getId();
		if (id == null) {
			id = getFacesJspId();
		}
		
		return id;
	}

	/**
	 * Create custom context with VariableMapper override.
	 * 
	 * @param context
	 * @return
	 */
	private ELContext getContext(final ELContext cont) {
		return new ELContext() {

			@Override
			public Object getContext(Class key) {
				return cont.getContext(key);
			}

			@Override
			public ELResolver getELResolver() {
				return cont.getELResolver();
			}

			@Override
			public FunctionMapper getFunctionMapper() {
				return cont.getFunctionMapper();
			}

			@Override
			public VariableMapper getVariableMapper() {
				return new VariableMapper() {

					@Override
					public ValueExpression resolveVariable(String variable) {
						if (variable.equals(itemId)) {
							return new IndexedValueExpression(__value, index);
						} else if (variable.equals(indexId)) {
							return new IteratedIndexExpression(index);
						}
						return cont.getVariableMapper().resolveVariable(
								variable);
					}

					@Override
					public ValueExpression setVariable(String variable,
							ValueExpression expression) {
						return cont.getVariableMapper().setVariable(variable,
								expression);
					}

				};
			}

		};
	}

	@Override
	public String getComponentType() {
		return UIColumn.COMPONENT_TYPE;
	}

	@Override
	public String getRendererType() {

		return CellRenderer.class.getName();
	}

	/**
	 * Sets page request variables
	 * 
	 * @throws JspTagException
	 */
	private void exposeVariables() throws JspTagException {

		/*
		 * We need to support null items returned from next(); we do this simply
		 * by passing such non-items through to the scoped variable as
		 * effectively 'null' (that is, by calling removeAttribute()).
		 * 
		 * Also, just to be defensive, we handle the case of a null 'status'
		 * object as well.
		 * 
		 * We call getCurrent() and getLoopStatus() (instead of just using
		 * 'item' and 'status') to bridge to subclasses correctly. A subclass
		 * can override getCurrent() or getLoopStatus() but still depend on our
		 * doStartTag() and doAfterBody(), which call this method
		 * (exposeVariables()), to expose 'item' and 'status' correctly.
		 */

		// Set up var variable
		if (itemId != null) {
			if (index == null)
				pageContext.removeAttribute(itemId, PageContext.PAGE_SCOPE);
			else if (__value != null) {
				VariableMapper vm = pageContext.getELContext()
						.getVariableMapper();
				if (vm != null) {
					ValueExpression ve = getVarExpression(__value);
					vm.setVariable(itemId, ve);
				}
			} else
				pageContext.setAttribute(itemId, index);
		}

		// Set up index variable

		if (indexId != null) {
			if (index == null)
				pageContext.removeAttribute(indexId, PageContext.PAGE_SCOPE);
			else {
				IteratedIndexExpression indexExpression = new IteratedIndexExpression(
						index);
				VariableMapper vm = pageContext.getELContext()
						.getVariableMapper();
				if (vm != null) {
					vm.setVariable(indexId, indexExpression);
				}
			}
		}

	}

	/**
	 * Return expression for page variables
	 * 
	 * @param expr
	 * @return
	 */
	private ValueExpression getVarExpression(ValueExpression expr) {
		Object o = expr.getValue(pageContext.getELContext());
		if (o.getClass().isArray() || o instanceof List) {
			return new IndexedValueExpression(__value, index);
		}

		if (o instanceof Collection || o instanceof Iterator
				|| o instanceof Enumeration || o instanceof Map
				|| o instanceof String) {

			if (iteratedExpression == null) {
				iteratedExpression = new IteratedExpression(__value,
						getDelims());
			}
			return new IteratedValueExpression(iteratedExpression, index);
		}

		throw new ELException("FOREACH_BAD_ITEMS: [" + o.getClass().getName() + "] is not iterable item. Only [List, Array, Collection, Enumeration, Map, String] are supported.");
	}

	/*
	 * Get the delimiter for string tokens. Used only for constructing the
	 * deferred expression for it.
	 */
	protected String getDelims() {
		return ",";
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(ValueExpression end) {
		this.__end = end;
	}

	/**
	 * @param begin
	 *            the begin to set
	 */
	public void setBegin(ValueExpression begin) {
		this.__begin = begin;
	}

	/**
	 * @param columns
	 *            the columns to set
	 */
	public void setColumns(ValueExpression columns) {
		this.__columns = columns;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(ValueExpression index) {
		this._index = index;
	}

	/**
	 * The current value for this component Setter for value
	 * 
	 * @param value -
	 *            new value
	 */
	public void setValue(ValueExpression __value) {
		this.__value = __value;
	}

	/**
	 * A request-scope attribute via which the data object for the current row
	 * will be used when iterating Setter for var
	 * 
	 * @param var -
	 *            new value
	 */
	public void setVar(ValueExpression __var) {
		this.__var = __var;
	}

	/**
	 * Corresponds to the HTML colspan attribute Setter for colspan
	 * 
	 * @param colspan -
	 *            new value
	 */
	public void setColspan(ValueExpression __colspan) {
		this._colspan = __colspan;
	}

	/**
	 * Corresponds to the HTML class attribute Setter for styleClass
	 * 
	 * @param styleClass -
	 *            new value
	 */
	public void setStyleClass(ValueExpression __styleClass) {
		this._styleClass = __styleClass;
	}

	/**
	 * Attribute defines width of column. Default value is "100px". Setter for
	 * width
	 * 
	 * @param width -
	 *            new value
	 */
	public void setWidth(ValueExpression __width) {
		this._width = __width;
	}

	/**
	 * if "true" next column begins from the first row Setter for breakBefore
	 * 
	 * @param breakBefore -
	 *            new value
	 */
	public void setBreakBefore(ValueExpression __breakBefore) {
		this._breakBefore = __breakBefore;
	}

	/**
	 * 
	 * Setter for comparator
	 * 
	 * @param comparator -
	 *            new value
	 */
	public void setComparator(ValueExpression __comparator) {
		this._comparator = __comparator;
	}

	/**
	 * Sets dir attr
	 * 
	 * @param dir
	 */
	public void setDir(ValueExpression dir) {
		_dir = dir;
	}

	/**
	 * 
	 * Setter for filterBy
	 * 
	 * @param filterBy -
	 *            new value
	 */
	public void setFilterBy(ValueExpression __filterBy) {
		this._filterBy = __filterBy;
	}

	/**
	 * 
	 * Setter for filterDefaultLabel
	 * 
	 * @param filterDefaultLabel -
	 *            new value
	 */
	public void setFilterDefaultLabel(ValueExpression __filterDefaultLabel) {
		this._filterDefaultLabel = __filterDefaultLabel;
	}

	/**
	 * Event for filter input that forces the filtration (default = onchange)
	 * Setter for filterEvent
	 * 
	 * @param filterEvent -
	 *            new value
	 */
	public void setFilterEvent(ValueExpression __filterEvent) {
		this._filterEvent = __filterEvent;
	}

	/**
	 * Attribute defines a bean property which is used for filtering of a column
	 * Setter for filterExpression
	 * 
	 * @param filterExpression -
	 *            new value
	 */
	public void setFilterExpression(ValueExpression __filterExpression) {
		this._filterExpression = __filterExpression;
	}

	/**
	 * 
	 * Setter for filterMethod
	 * 
	 * @param filterMethod -
	 *            new value
	 */
	public void setFilterMethod(MethodExpression __filterMethod) {
		this._filterMethod = __filterMethod;
	}

	/**
	 * 
	 * Setter for filterValue
	 * 
	 * @param filterValue -
	 *            new value
	 */
	public void setFilterValue(ValueExpression __filterValue) {
		this._filterValue = __filterValue;
	}

	/**
	 * Space-separated list of CSS style class(es) that are be applied to any
	 * footer generated for this table Setter for footerClass
	 * 
	 * @param footerClass -
	 *            new value
	 */
	public void setFooterClass(ValueExpression __footerClass) {
		this._footerClass = __footerClass;
	}

	/**
	 * Space-separated list of CSS style class(es) that are be applied to any
	 * header generated for this table Setter for headerClass
	 * 
	 * @param headerClass -
	 *            new value
	 */
	public void setHeaderClass(ValueExpression __headerClass) {
		this._headerClass = __headerClass;
	}

	/**
	 * Corresponds to the HTML rowspan attribute Setter for rowspan
	 * 
	 * @param rowspan -
	 *            new value
	 */
	public void setRowspan(ValueExpression __rowspan) {
		this._rowspan = __rowspan;
	}

	/**
	 * 
	 * Setter for selfSorted
	 * 
	 * @param selfSorted -
	 *            new value
	 */
	public void setSelfSorted(ValueExpression __selfSorted) {
		this._selfSorted = __selfSorted;
	}

	/**
	 * Boolean attribute. If "true" it's possible to sort the column content
	 * after click on the header. Default value is "true" Setter for sortable
	 * 
	 * @param sortable -
	 *            new value
	 */
	public void setSortable(ValueExpression __sortable) {
		this._sortable = __sortable;
	}

	/**
	 * Attribute defines a bean property which is used for sorting of a column
	 * Setter for sortExpression
	 * 
	 * @param sortExpression -
	 *            new value
	 */
	public void setSortExpression(ValueExpression __sortExpression) {
		this._sortExpression = __sortExpression;
	}
	
	/**
	 * Attribute defines whether to render component or not
	 * Setter for rendered
	 * 
	 * @param __rendered - new value
	 */
	public void setRendered(ValueExpression __rendered) {
		this._rendered = __rendered;
	}
	
	/**
	 * Defines sort icon. The value for the attribute is context related
	 * @param icon
	 */
	public void setSortIcon(ValueExpression icon) {
		_sortIcon = icon;
	}
	
	/**
	 * Defines sort icon for ascending order. The value for the attribute is context related
	 * @param iconAscending
	 */
	public void setSortIconAscending(ValueExpression iconAscending) {
		_sortIconAscending = iconAscending;
	}
	
	/**
	 * Defines sort icon for descending order. The value for the attribute is context related
	 * @param iconDescending
	 */
	public void setSortIconDescending(ValueExpression iconDescending) {
		_sortIconDescending = iconDescending;
	}
}
