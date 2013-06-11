/**
 * License Agreement.
 *
 *  JBoss RichFaces 3.0 - Ajax4jsf Component Library
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.model.ListDataModel;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.collections.Predicate;
import org.richfaces.model.Ordering;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for DataTable component.
 */
public class DataTableComponentTest extends AbstractAjax4JsfTestCase {
	
	private static final String SORT_FILTER_PARAMETER = "fsp";
	private static final String FILTER_INPUT_FACET_NAME = "filterValueInput";

    private UIDataTable dataTable;
    private UIColumn column1;
    private UIColumn column2;
    private UIColumn column3;
    private UIColumn column4;
    private UIForm form = null;
    private UIColumnGroup columnGroup;

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public DataTableComponentTest(String testName) {
        super(testName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();
        
        form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);
        dataTable = (UIDataTable) application
                .createComponent("org.richfaces.DataTable");
        dataTable.setId("dataTable");

        List<Date> list = new ArrayList<Date>();
        for (int i = 1; i <= 5; i++) {
            list.add(new Date((long) Math.random()));
        }
        dataTable.setValue(new ListDataModel(list));

        columnGroup = (UIColumnGroup) application
                .createComponent("org.richfaces.ColumnGroup");
        dataTable.getChildren().add(columnGroup);

        column1 = (UIColumn) application
                .createComponent("org.richfaces.Column");
        UIOutput cellElement1 = (UIOutput) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
                null, null, null);
        cellElement1.setValueExpression("value", new ColumnValueExpression());
        column1.getChildren().add(cellElement1);
        columnGroup.getChildren().add(column1);

        column2 = (UIColumn) application
                .createComponent("org.richfaces.Column");
        UIOutput cellElement2 = (UIOutput) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputLink.class.getName(),
                null, null, null);
        cellElement2.setValueExpression("value", new ColumnValueExpression());
        column2.getChildren().add(cellElement2);
        columnGroup.getChildren().add(column2); 
        
        column3 = (UIColumn) application.createComponent("org.richfaces.Column");
		UIOutput cellElement3 = (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
				null, null, null);
		cellElement3.setValueExpression("value", new ColumnValueExpression());
		column3.getChildren().add(cellElement1);
		dataTable.getChildren().add(column3);
		
		column4 = (UIColumn) application.createComponent("org.richfaces.Column");
		UIOutput cellElement4 = (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
				null, null, null);
		cellElement4.setValueExpression("value", new ColumnValueExpression());
		column4.getChildren().add(cellElement1);
		dataTable.getChildren().add(column4);
        
        form.getChildren().add(dataTable);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();

        column1 = null;
        column2 = null;
        column3 = null;
        column4 = null;
        columnGroup = null;
        dataTable = null;
    }

    /**
     * Test SubTable component rendering.
     * 
     * @throws Exception
     */
    public void testSubTable() throws Exception {

        UISubTable subTable = (UISubTable) application
                .createComponent("org.richfaces.SubTable");
        subTable.setId("subTable");
       
        UIColumnGroup subTableColumnGroup = (UIColumnGroup) application
                .createComponent("org.richfaces.ColumnGroup");

        UIColumn column3 = (UIColumn) application
                .createComponent("org.richfaces.Column");
                 
        subTableColumnGroup.getChildren().add(column3);
        UIOutput text = (UIOutput) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
                null, null, null);
        text.setValue("Column1");
        column3.getChildren().add(text);

        UIColumn column4 = (UIColumn) application
                .createComponent("org.richfaces.Column");
        subTableColumnGroup.getChildren().add(column4);
        UIOutput text2 = (UIOutput) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
                null, null, null);
        text.setValue("Column2");
        column4.getChildren().add(text2);

        UIColumn headerColumn = (UIColumn) application
                .createComponent("org.richfaces.Column");
        UIOutput header = (UIOutput) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
                null, null, null);
        header.setValue("Header");
        headerColumn.getChildren().add(header);
        subTable.getFacets().put("header", headerColumn);

        column3.getFacets().put(
                "header",
                (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE,
                        HtmlOutputText.class.getName(), null, null, null));
        column4.getFacets().put(
                "header",
                (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE,
                        HtmlOutputText.class.getName(), null, null, null));

        subTable.getAttributes().put("columnClasses", "sub1,sub2");
        dataTable.getChildren().add(subTable);
        
        assertTrue(subTable.isBreakBefore());

        try {
            subTable.setBreakBefore(false);
            assertTrue(false);
        } catch (IllegalStateException e) {

        }

        HtmlPage page = renderView();
        assertNotNull(page);
    }

    /**
     * Test for UIColumnGroup class.
     * 
     * @throws Exception
     */
    public void testUIColumnGroup() throws Exception {

        try {
            columnGroup.setBreakBefore(true);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    
    /**
     * Tests if component accepts request parameters and stores them
     * 
     * @throws Exception
     */
    public void testDecode() throws Exception {
    	dataTable.setVar("var");
    	
    	UIInput input = new UIInput();
		input.setId("input");
		column3.getFacets().put(FILTER_INPUT_FACET_NAME, input);
		
		externalContext.addRequestParameterMap(dataTable.getClientId(facesContext), SORT_FILTER_PARAMETER);
    	externalContext.addRequestParameterMap(SORT_FILTER_PARAMETER, column3.getClientId(facesContext));
		externalContext.addRequestParameterMap(input.getClientId(facesContext), FILTER_INPUT_FACET_NAME);
		
    	column4.setSortOrder(Ordering.ASCENDING);
    	
    	dataTable.processDecodes(facesContext);
    	assertEquals(Ordering.UNSORTED, column4.getSortOrder());
    	assertEquals(FILTER_INPUT_FACET_NAME, column3.getFilterValue());
    	
    }
    
    protected class ColumnValueExpression extends ValueExpression {

		private static final long serialVersionUID = -4572752019634445014L;

		public Class<?> getExpectedType() {
			return null;
		}

		public Class<?> getType(ELContext context) {
			return String.class;
		}

		public Object getValue(ELContext context) {
			return Long.toString(((Date) dataTable.getValue()).getTime());
		}

		public boolean isReadOnly(ELContext context) {
			return false;
		}

		public void setValue(ELContext context, Object value) {
			
		}

		public boolean equals(Object obj) {
			return false;
		}

		public String getExpressionString() {
			return null;
		}

		public int hashCode() {
			return 0;
		}

		public boolean isLiteralText() {
			return false;
		}
    	
    }
    
}
