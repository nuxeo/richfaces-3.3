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

/*
 * ColumnsHandler.java		Date created: 07.12.2007
 * Last modified by: $Author$
 * $Revision$	$Date$
 */

package org.richfaces.taglib;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.JspTagException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.el.ELBuilder;
import org.richfaces.iterator.ForEachIterator;
import org.richfaces.iterator.SimpleForEachIterator;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletHandler;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.MetaTagHandler;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.Tag;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagAttributes;
import com.sun.facelets.tag.jsf.ComponentConfig;



/**
 * TODO Class description goes here.
 * 
 * @author "Andrey Markavtsov"
 * 
 */
public class ColumnsHandler extends MetaTagHandler {

	private static final Log log = LogFactory.getLog(ColumnsHandler.class);
	
	com.sun.facelets.tag.jsf.ComponentHandler handler;

	private static final String ITERATION_INDEX_VARIABLE = "__richfaces_iteration_index_variable";
	
	private static final String ITERATION_INDEX_EXPRESSION = "#{" + ITERATION_INDEX_VARIABLE + "}";

	private static final String F_GENERATION_SERIES_ATTRIBUTE = "org.richfaces.F_COLUMNS_GENERATION_SERIES";

	/** value attribute */
	private TagAttribute value;

	/** end attribute */
	private TagAttribute columns;

	/** begin attribute */
	private TagAttribute begin;

	/** var attribute */
	private TagAttribute var;

	/** index attribute */
	private TagAttribute index;

	/** end attribute */
	private TagAttribute end;

	class IterationContext {

		/** Iterator for columns's tag value attribute */
		public ForEachIterator items; // our 'digested' items

		/** Value attribute value */
		public Object rawItems; // our 'raw' items

		/** Var attr - defines page variable for current item */
		public String _indexId;

		/** Integer value begin attr */
		public Integer _begin;

		/** Integer value end attr */
		public Integer _end;

		/** Integer value of end attr. */
		public Integer _columns;

		/** String value of var attr */
		public String _itemId = null;

		/** Current column counter */
		public Integer _index = 0;

		/** Expression for var item */
		public IteratedExpression iteratedExpression;

		public String valueExpr;

		public String getVarReplacement() {
			if (valueExpr == null) {
				return String.valueOf(index);
			}else if (items.getVarReplacement() != null) {
				return items.getVarReplacement();
			}
			return valueExpr + "[" + _index + "]";
		}

		public String getIndexReplacement() {
			return String.valueOf(_index); 
		}
	};

	ThreadLocal<IterationContext> iterationContextLocal = new ThreadLocal<IterationContext>();

	public IterationContext getIterationContext() {
		return iterationContextLocal.get();
	}

	/**
	 * TODO Description goes here.
	 * 
	 * @param config
	 */
	public ColumnsHandler(final ComponentConfig config) {
		super(config);
		
		final ComponentConfig columnConfig;
		
		TagAttribute idAttribute = config.getTag().getAttributes().get("id");
		if (idAttribute != null && idAttribute.isLiteral()) {
			columnConfig = new ComponentConfig() {

				private Tag tag;

				{
					Tag initialTag = config.getTag();
					TagAttribute[] allInitialAttributes = initialTag.getAttributes().getAll();
					TagAttribute[] attributes = new TagAttribute[allInitialAttributes.length];
					for (int i = 0; i < allInitialAttributes.length; i++) {
						TagAttribute initialAttribute = allInitialAttributes[i];
						String localName = initialAttribute.getLocalName();
						String attributeValue = initialAttribute.getValue();
						
						if ("id".equals(localName)) {
							attributeValue += ITERATION_INDEX_EXPRESSION;
						}
						
						attributes[i] = new TagAttribute(initialAttribute.getLocation(),
								initialAttribute.getNamespace(), 
								localName, 
								initialAttribute.getQName(), 
								attributeValue);
					}
					
					TagAttributes tagAttributes = new TagAttributes(attributes);
					this.tag = new Tag(initialTag, tagAttributes);
				}
				
				public String getComponentType() {
					return config.getComponentType();
				}

				public String getRendererType() {
					return config.getRendererType();
				}

				public FaceletHandler getNextHandler() {
					return config.getNextHandler();
				}

				public Tag getTag() {
					return tag;
				}

				public String getTagId() {
					return config.getTagId();
				}
				
			};
		} else {
			columnConfig = config;
		}
		
		handler = new ColumnTagHandler(columnConfig) {
			
			@Override
			protected MetaRuleset createMetaRuleset(Class type) {
				MetaRuleset ruleset = super.createMetaRuleset(type);
				ruleset.addRule(new MetaRule() {

					@Override
					public Metadata applyRule(final String name,
							final TagAttribute attribute, MetadataTarget meta) {
						if (ColumnsAttributes.FILTER_ATTRIBUTES.indexOf(name) != -1 || 
							ColumnsAttributes.SORT_ATTRIBUTES.indexOf(name) != -1) {
							
							return new Metadata() {

								@Override
								public void applyMetadata(FaceletContext ctx,
										Object instance) {
									if (!attribute.isLiteral()) {
										String expr = attribute.getValue();
										IterationContext itContext = iterationContextLocal.get();

										ValueExpression ve = ELBuilder.createValueExpression(expr, Object.class, ctx.getExpressionFactory(), ctx, 
												itContext._itemId, itContext._indexId, 
												itContext.getVarReplacement(), itContext.getIndexReplacement());
										((UIComponent)instance).setValueExpression(name, ve);
									}else {
										((UIComponent)instance).getAttributes().put(name, attribute.getValue());
									}
								}

							};
						}
						return null;
					}

				});
				return ruleset;
			}

			@Override
			protected void applyNextHandler(FaceletContext ctx, UIComponent c)
			throws IOException, FacesException, ELException {
				c.getAttributes().put(F_GENERATION_SERIES_ATTRIBUTE, RequestUniqueIdGenerator.generateId(ctx.getFacesContext()));
				super.applyNextHandler(ctx, c);
			}
		};
	}


	/**
	 * Extracts tags attributes values
	 */
	private void initVariables(FaceletContext ctx) {
		initColumnsCount(ctx);
		initIndex(ctx);
		initVar(ctx);
		initBegin(ctx);
		initEnd(ctx);
	}

	/**
	 * Method prepares all we need for starting of tag rendering
	 * 
	 * @throws JspTagException
	 */
	private void prepare(FaceletContext ctx) {

		initVariables(ctx);

		IterationContext itContext = getIterationContext();

		try {

			this.value = getAttribute("value");

			// produce the right sort of ForEachIterator
			if (this.value != null) {
				itContext.valueExpr = ELBuilder.getVarReplacement(this.value.getValue());

				// If this is a deferred expression, make a note and get
				// the 'items' instance.

				itContext.rawItems = this.value.getObject(ctx);

				// extract an iterator over the 'items' we've got
				itContext.items = SimpleForEachIterator
				.supportedTypeForEachIterator(itContext.rawItems);
			} else {
				// no 'items', so use 'begin' and 'end'
				itContext.items = SimpleForEachIterator.beginEndForEachIterator(itContext._columns - 1);
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			// TODO: handle exception
		}

		correctFirst(ctx);
	}

	/**
	 * Inits first iteration item
	 */
	private void correctFirst(FaceletContext ctx) {
		IterationContext itContext = getIterationContext();
		if (itContext.items != null) {
			if (itContext._begin > 0 && (itContext._index < itContext._begin)) {
				while ((itContext._index < itContext._begin && hasNext())) {
					next(ctx);
				}
				if (!hasNext()) {
					itContext._index = 0;
				}
			}
		}
	}

	/**
	 * Return true if we didn't complete column's count
	 * 
	 * @return
	 * @throws JspTagException
	 */
	private boolean hasNext() {
		IterationContext itContext = getIterationContext();
		try {
			if (itContext._end != 0) {
				return (itContext._index < itContext._end) ? itContext.items.hasNext() : false;
			} else {
				return itContext.items.hasNext();
			}
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Iterate to next column
	 * 
	 * @return
	 * @throws JspTagException
	 */
	private Object next(FaceletContext ctx) {
		IterationContext itContext = getIterationContext();
		try {
			Object o = itContext.items.next();
			itContext._index++;
			return o;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Extracts integer value from end attr
	 */
	private void initColumnsCount(FaceletContext ctx) {
		IterationContext itContext = getIterationContext();
		this.columns = getAttribute("columns");
		if (columns != null) {
			try {
				itContext._columns = Integer.parseInt((String) columns.getObject(ctx));
				if (itContext._columns < 0) {
					itContext._columns = 0; // If end is negative set up zero
				}
			} catch (Exception e) {
				itContext._columns = 0;
			}
		} else {
			itContext._columns = 0;
		}
	}

	/**
	 * Extracts integer value from begin attr
	 */
	private void initBegin(FaceletContext ctx) {
		IterationContext itContext = getIterationContext();
		this.begin = getAttribute("begin");
		if (begin != null) {
			try {
				Object o = begin.getObject(ctx);
				if (o instanceof Number) {
					itContext._begin = ((Number)o).intValue();
				}else if (o instanceof String) {
					itContext._begin = Integer.parseInt((String) o);
				}
				itContext._begin--;
				if (itContext._begin < 0) {
					itContext._begin = 0; // If end is negative set up zero
				}
			} catch (Exception e) {
				itContext._begin = 0;
			}
		} else {
			itContext._begin = 0;
		}
	}

	/**
	 * Extracts integer value from end attr
	 */
	private void initEnd(FaceletContext ctx) {
		IterationContext itContext = getIterationContext();
		this.end = getAttribute("end");
		if (end != null) {
			try {
				Object o = end.getObject(ctx);
				if (o instanceof Number) {
					itContext._end = ((Number)o).intValue();
				}else if ( o instanceof String) {
					itContext._end = Integer.parseInt((String) o);
				}
				if (itContext._end < 0) {
					itContext._end = 0; // If end is negative set up zero
				}
			} catch (Exception e) {
				itContext._end = 0;
			}
		} else {
			itContext._end = 0;
		}
	}

	/**
	 * Extracts string value from var attr
	 */
	private void initVar(FaceletContext ctx) {
		IterationContext itContext = getIterationContext();
		this.var = getAttribute("var");
		if (var != null) {
			try {
				itContext._itemId = (String) var.getObject(ctx);
			} catch (ClassCastException e) {
				itContext._itemId = null;
			}

		}
	}

	/**
	 * Extracts string value from index attr
	 */
	private void initIndex(FaceletContext ctx) {
		IterationContext itContext = getIterationContext();
		this.index = getAttribute("index");
		if (index != null) {
			try {
				itContext._indexId = (String) index.getObject(ctx);
			} catch (ClassCastException e) {
				itContext._indexId = null;
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richfaces.taglib.ComponentHandler#apply(com.sun.facelets.FaceletContext,
	 *      javax.faces.component.UIComponent)
	 */
	//@Override
	public void apply(FaceletContext ctx, UIComponent parent)
	throws IOException, FacesException, ELException {

		IterationContext iterationContext = new IterationContext();
		iterationContextLocal.set(iterationContext);

		clearOldColumns(ctx.getFacesContext(), parent);
		prepare(ctx);  // prepare data 

		try {
			while (hasNext()) {  // for each
				exposeVariables(ctx);
				handler.apply(ctx, parent);
				next(ctx);
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			// TODO: handle exception
		} finally {
			release();
			unExposeVariables(ctx);
		}

	}


	private void clearOldColumns(FacesContext context, UIComponent parent) {
		if (parent.getChildCount() > 0) {
			Integer generatedId = RequestUniqueIdGenerator.generateId(context);
			
			Iterator<UIComponent> childrenIt = parent.getChildren().iterator();
			while (childrenIt.hasNext()) {
				UIComponent c = childrenIt.next();

				Object generationSeries = c.getAttributes().get(F_GENERATION_SERIES_ATTRIBUTE);
				if (generationSeries != null && !generationSeries.equals(generatedId)) {
					childrenIt.remove();
				}
			}
		}
	}

	/**
	 * Sets page request variables
	 * 
	 * @throws JspTagException
	 */
	private void exposeVariables(FaceletContext ctx) {
		IterationContext itContext = getIterationContext();
		VariableMapper vm = ctx.getVariableMapper();
		int k = itContext._index;

		if (itContext._itemId != null) {
			if (vm != null) {
				if (value != null) {
					ValueExpression srcVE = value.getValueExpression(ctx,
							Object.class);
					ValueExpression ve = getVarExpression(ctx, srcVE);
					vm.setVariable(itContext._itemId, ve);
				}
			}

		}

		// Set up index variable

		if (itContext._indexId != null) {
			if (vm != null) {
				ValueExpression ve = new IteratedIndexExpression(k);
				vm.setVariable(itContext._indexId, ve);
			}

		}

		int componentsCount = itContext._index - itContext._begin;
		if (componentsCount != 0) {
			ValueExpression ve = ctx.getExpressionFactory().createValueExpression(UIViewRoot.UNIQUE_ID_PREFIX + componentsCount, String.class);
			vm.setVariable(ITERATION_INDEX_VARIABLE, ve);
		}
	}

	/**
	 * Removes page attributes that we have exposed and, if applicable, restores
	 * them to their prior values (and scopes).
	 */
	private void unExposeVariables(FaceletContext ctx) {
		IterationContext itContext = getIterationContext();
		VariableMapper vm = ctx.getVariableMapper();
		// "nested" variables are now simply removed
		if (itContext._itemId != null) {
			if (vm != null)
				vm.setVariable(itContext._itemId, null);
		}
		if (itContext._indexId != null) {
			if (vm != null)
				vm.setVariable(itContext._indexId, null);
		}
		
		vm.setVariable(ITERATION_INDEX_VARIABLE, null);
	}

	/**
	 * Return expression for page variables
	 * 
	 * @param expr
	 * @return
	 */
	private ValueExpression getVarExpression(FaceletContext ctx,
			ValueExpression expr/*, IterationContext itContext*/) {
		IterationContext itContext = getIterationContext();
		Object o = expr.getValue(ctx.getFacesContext().getELContext());
		int k = itContext._index;
		if (o.getClass().isArray() || o instanceof List) {
			return new IndexedValueExpression(expr, k);
		}

		if (o instanceof Collection || o instanceof Iterator
				|| o instanceof Enumeration || o instanceof Map
				|| o instanceof String) {

			if (itContext.iteratedExpression == null) {
				itContext.iteratedExpression = new IteratedExpression(expr, ",");
			}
			return new IteratedValueExpression(itContext.iteratedExpression, k);
		}

		throw new ELException("FOREACH_BAD_ITEMS");
	}

	/**
	 * Release iteration variables
	 */
	private void release() {
		IterationContext itContext = getIterationContext();
		itContext.items = null;
		itContext._index = 0;
	}

}
