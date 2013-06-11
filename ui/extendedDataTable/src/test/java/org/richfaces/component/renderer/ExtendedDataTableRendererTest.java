/**
* License Agreement.
*
* JBoss RichFaces - Ajax4jsf Component Library
*
* Copyright (C) 2008 CompuGROUP Holding AG
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

package org.richfaces.component.renderer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlOutputText;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIColumn;
import org.richfaces.component.UIExtendedDataTable;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


/**
 * 
 * @author pbuda
 *
 */
public class ExtendedDataTableRendererTest extends AbstractAjax4JsfTestCase {
    
    private UIExtendedDataTable table;
    
    private Set<String> javascripts = new HashSet<String>();
    
    
    public ExtendedDataTableRendererTest(String name) {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        
        //Create data table
        table = (UIExtendedDataTable) application.createComponent(UIExtendedDataTable.COMPONENT_TYPE);
        table.setId("table");
        
        UIColumn column = (UIColumn) createComponent(
                UIColumn.COMPONENT_TYPE,
                "org.richfaces.component.Column",
                null, null, null
        );
        
        column.setFilterValue("field");
        
        UIOutput output = (UIOutput) createComponent(
                UIOutput.COMPONENT_TYPE,
                HtmlOutputText.class.getName(),
                null, null, null
        );
        
        column.getChildren().add(output);
        
        
        table.getChildren().add(column);
        
        facesContext.getViewRoot().getChildren().add(table);
        
        //javascripts initialization
        javascripts.add("org/richfaces/renderkit/html/scripts/extended-data-table.js");
    }

    @Override
    public void tearDown() throws Exception {
        table = null;
        super.tearDown();
    }
    
    @SuppressWarnings("unchecked")
    public void testLoadingStyles() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        List links = page.getDocumentHtmlElement().getHtmlElementsByTagName("link");
        assertNotNull(links);
        //find extendedDataTable.xcss
        HtmlElement link = null;
        for (Object l : links){
        	if (((HtmlElement)l).getAttributeValue("href").contains("css/extendedDataTable.xcss")){
        		link = (HtmlElement)l;
        		break;
        	}
        }
        assertNotNull(link);
    }
    
    public void testLoadingScripts() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        assertTrue(getCountValidScripts(page, javascripts, false).intValue() == javascripts.size());
    }
}
