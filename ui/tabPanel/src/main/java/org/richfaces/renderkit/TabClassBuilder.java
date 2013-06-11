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

package org.richfaces.renderkit;

import org.richfaces.component.UITab;
import org.richfaces.component.UITabPanel;

import javax.faces.component.UIComponent;

/**
 * Let's build tab style class of:
 * 'renderer-specific' classes
 *
 * @author Maksim Kaszynski
 */

public class TabClassBuilder {

    public static final TabClassBuilder activeTabClassBuilder = new TabClassBuilder() {

        protected String getSpecificClassForAllTabs() {
            //dr-tb-act
            return "dr-tbpnl-tb-act rich-tab-active";
        }

        protected String getSpecificTabClassFromPane(UITab tab,
                                                     UITabPanel pane) {
            return TabClassBuilder.getStringAttributeOrEmptyString(pane,
                    "activeTabClass");
        }

    };

    public static final TabClassBuilder disabledTabClassBuilder = new TabClassBuilder() {

        protected String getSpecificClassForAllTabs() {
            //dr-tb-dsbld
            return "dr-tbpnl-tb-dsbl rich-tab-disabled";
        }

        protected String getSpecificTabClassFromPane(UITab tab,
                                                     UITabPanel pane) {
            return TabClassBuilder.getStringAttributeOrEmptyString(pane,
                    "disabledTabClass");
        }

    };

    public static final TabClassBuilder inactiveTabClassBuilder = new TabClassBuilder() {

        protected String getSpecificClassForAllTabs() {
            //dr-tb-inact
            return "dr-tbpnl-tb-inact rich-tab-inactive";
        }

        protected String getSpecificTabClassFromPane(UITab tab,
                                                     UITabPanel pane) {
            return TabClassBuilder.getStringAttributeOrEmptyString(pane,
                    "inactiveTabClass");
        }

    };


    public static String getStringAttributeOrEmptyString(UIComponent component, String attributeName) {
        String attributeValue = (String) component.getAttributes().get(attributeName);

        if (null == attributeValue) {
            attributeValue = "";
        }

        return attributeValue;
    }


    /**
     * gather cumulative class
     *
     * @param tab
     * @return
     */
    public String buildTabClass(UITab tab) {
        UITabPanel pane = tab.getPane();
        StringBuffer labelClass = new StringBuffer();
        labelClass
                .append(getCommonClassForAllTabs())
                .append(" ")
                .append(getSpecificClassForAllTabs())
                .append(" ")
                .append(getCommonTabClassFromPane(pane))
                .append(" ")
                .append(getSpecificTabClassFromPane(tab, pane))
                .append(" ")
                .append(getSpecificClassFromTab(tab))
                .append(" ")
                .append(getClassFromTab(tab));

        return labelClass.toString();
    }

    /**
     * common prefix for all tabs (renderer-specific)
     *
     * @return
     */
    protected String getCommonClassForAllTabs() {
        //dr-tb-cntrl
        return "dr-tbpnl-tb rich-tab-header";
    }

    /**
     * class common class for different kinds of tabs (e.g. active, disabled, etc)
     * renderer-specific
     *
     * @return
     */
    protected String getSpecificClassForAllTabs() {
        return "";
    }

    /**
     * usually styleClass attribute specified directly on tab
     *
     * @param tab
     * @return
     */
    protected String getClassFromTab(UITab tab) {
        return getStringAttributeOrEmptyString(tab, "styleClass");
    }

    /**
     * no known implements
     *
     * @param tab
     * @return
     */

    protected String getSpecificClassFromTab(UITab tab) {
        return "";
    }

    /**
     * general class of all tabs specified on pane
     *
     * @param pane
     * @return
     */
    protected String getCommonTabClassFromPane(UITabPanel pane) {
        return getStringAttributeOrEmptyString(pane, "tabClass");
    }

    /**
     * get class from pane depending on tab kind
     * @param tab
     * @param pane
     * @return
     */
    protected String getSpecificTabClassFromPane(UITab tab, UITabPanel pane) {
        return "";
    }
}
