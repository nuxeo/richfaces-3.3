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
package org.ajax4jsf.template;

/**
 * Kids of templates that can be put in harness.
 * @author carcasser
 *
 */
public enum Template implements TestConstants {
    SIMPLE (SIMPLE_TEMPLATE_NAME, COMPONENT_PREFIX_SIMPLE, SIMPLE_TEMPLATE_DESC),
    DATA_TABLE (DATA_TABLE_TEMPLATE_NAME, COMPONENT_PREFIX_INSIDE_TABLE1, DATA_TABLE_TEMPLATE_DESC1),
    DATA_TABLE2 (DATA_TABLE_TEMPLATE_NAME, COMPONENT_PREFIX_INSIDE_TABLE2, DATA_TABLE_TEMPLATE_DESC2),
    MODAL_PANEL (MODAL_PANEL_TEMPLATE_NAME, COMPONENT_PREFIX_SIMPLE, MODAL_PANEL_TEMPLATE_DESC);

    private String name;

    private String prefix;

    private String desc;

    /**
     * Private constructor.
     * 
     * @param name
     * @param prefix
     */
    private Template(String name, String prefix, String desc) {
        this.name = name;
        this.prefix = prefix;
        this.desc = desc;
    }

    /**
     * Gets value of name field.
     * @return value of name field
     */
    public String getName() {
        return name;
    }

    /**
     * Gets value of prefix field.
     * @return value of prefix field
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Gets value of desc field.
     * @return value of desc field
     */
    public String getDesc() {
        return desc;
    }

}
