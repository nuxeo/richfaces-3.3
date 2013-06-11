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
import java.util.List;

import org.ajax4jsf.template.Template;

public class Configurator {
    
    private static final String[] LOAD_STRATEGIES = new String[] {"DEFAULT", "ALL"};
    
    private static String loadScriptStrategy = LOAD_STRATEGIES[0];
    
    private static String loadStyleStrategy = LOAD_STRATEGIES[0];
    
    private static int currentStrategyIndex = 0;
    
    private static Object[][] templates = null;
    
    public Configurator() {}
    
    public String getLoadScriptStrategy() {
        return loadScriptStrategy;
    }

    public static void setLoadScriptStrategy(String strategy) {
        loadScriptStrategy = strategy;
    }

    public String getLoadStyleStrategy() {
        return loadStyleStrategy;
    }

    public static void setLoadStyleStrategy(String strategy) {
        loadStyleStrategy = strategy;
    }

	/**
	 * @return the templates
	 */
	public static Object[][] getTemplates() {
		return templates;
	}

	/**
	 * @param templates the templates to set
	 */
	public static void setTemplates(String templateExpr) {
		if (templateExpr != null) {
		 String[] array = new String[]{};
	        if(null != templateExpr) {
	            array = templateExpr.split(",");
	        }

	        List<Object[]> list = new ArrayList<Object[]>();
	        for (String string : array) {
	            Object[] elem = new Object[] {Template.valueOf(string.trim().toUpperCase())};
	            list.add(elem);
	        }

	        templates = (Object[][]) list.toArray(new Object[0][0]);
		}else {
			templates = new Object[][] { { Template.SIMPLE }, { Template.DATA_TABLE }, { Template.DATA_TABLE2 }, { Template.MODAL_PANEL } };
		}
		
	}
    
}
