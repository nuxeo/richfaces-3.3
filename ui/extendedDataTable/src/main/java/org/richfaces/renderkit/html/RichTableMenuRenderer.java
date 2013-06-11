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
package org.richfaces.renderkit.html;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;

import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.resource.InternetResource;
import org.richfaces.component.UIColumn;
import org.richfaces.component.UIContextMenu;
import org.richfaces.component.UIMenuGroup;
import org.richfaces.component.UIMenuItem;
import org.richfaces.component.UIMenuSeparator;
import org.richfaces.component.html.ContextMenu;
import org.richfaces.component.html.HtmlMenuGroup;
import org.richfaces.component.html.HtmlMenuItem;
import org.richfaces.component.util.ComponentMessageUtil;

/**
 * Renderer class for table menu.
 * 
 * @author pawelgo
 * 
 */
public class RichTableMenuRenderer extends TableMenuRenderer {

    private static final long serialVersionUID = -6812995542681604002L;

    /*
     * Message key constants
     */
    private static final String MSG_COLUMNS = "org.richfaces.component.UIExtendedDataTable.Menu.Columns";
    private static final String MSG_SORT_ASC = "org.richfaces.component.UIExtendedDataTable.Menu.SortAscending";
    private static final String MSG_SORT_DESC = "org.richfaces.component.UIExtendedDataTable.Menu.SortDescending";
    private static final String MSG_GROUP_ON = "org.richfaces.component.UIExtendedDataTable.Menu.GroupByColumn";
    private static final String MSG_GROUP_OFF = "org.richfaces.component.UIExtendedDataTable.Menu.DisableGrouping";

    private int visibleColumnsCount;

    private UIContextMenu menu;

    /*
     * Icon URIs
     */
    private static String iconColumnsURI = InternetResource.RESOURCE_URI_PREFIX + "/org/richfaces/renderkit/html/images/columns.png";
    private static String iconSortAscURI = InternetResource.RESOURCE_URI_PREFIX + "/org/richfaces/renderkit/html/images/menu-sort-asc.png";
    private static String iconSortDescURI = InternetResource.RESOURCE_URI_PREFIX + "/org/richfaces/renderkit/html/images/menu-sort-desc.png";
    private static String iconGroupURI = InternetResource.RESOURCE_URI_PREFIX + "/org/richfaces/renderkit/html/images/group-by.png";
    private static String iconCheckedURI = InternetResource.RESOURCE_URI_PREFIX + "/org/richfaces/renderkit/html/images/checked.gif";
    private static String iconUncheckedURI = InternetResource.RESOURCE_URI_PREFIX + "/org/richfaces/renderkit/html/images/unchecked.gif";

    /*
     * (non-Javadoc)
     * 
     * @see org.richfaces.renderkit.html.TableMenuRenderer#render()
     */
    public String render() throws IOException {
        ensureMenuBuilt();
        String menuId = menu.getClientId(context);
        // render menu
        menu.encodeAll(context);
        return menuId;
    }// render

    /**
     * 
     * @throws IOException
     */
    protected void ensureMenuBuilt() throws IOException {
        menu = null;
        // try to get menu from children components
        for (UIComponent comp : table.getChildren()) {
            if ((comp.getId().equals(buildMenuId()))
                    && (comp instanceof UIContextMenu)) {
                menu = (UIContextMenu) comp;
                break;
            }
        }
        buildMenu();
    }

    /**
     * Builds menu id base on column for which menu is built.
     * 
     * @return menu id
     */
    protected String buildMenuId() {
        return column.getId() + "menu";
    }

    /**
     * Creates menu and ands it to table as child.
     * 
     * @throws IOException
     */
    protected void createMenu() throws IOException {
        menu = (UIContextMenu) context.getApplication().createComponent(
                UIContextMenu.COMPONENT_TYPE);
        menu.setId(buildMenuId());
        menu.setAttached(false);
        menu.setSubmitMode("none");
        menu.setEvent("onclick");
        menu.setDisableDefaultMenu(false);

        if (menu instanceof ContextMenu) {
            ((ContextMenu) menu)
                    .setStyle("z-index: 100; text-align: left; font-weight: normal;");

        }
        table.getChildren().add(menu);
    }

    /**
     * Builds menu for current column. Creates menu if not exist yet and adds
     * items.
     * 
     * @throws IOException
     */
    protected void buildMenu() throws IOException {
        if (menu == null) {// menu does not exist yet
            // create menu
            createMenu();
        } else {// menu already exists
            // clear menu children
            menu.getChildren().clear();
        }

        // add menu items for sorting
        buildSortMenuItem(menu, true);
        buildSortMenuItem(menu, false);

        UIMenuSeparator sep = (UIMenuSeparator) context.getApplication()
                .createComponent(UIMenuSeparator.COMPONENT_TYPE);
        menu.getChildren().add(sep);

        // add menu item for grouping
        buildGroupMenuItem(menu);

        sep = (UIMenuSeparator) context.getApplication().createComponent(
                UIMenuSeparator.COMPONENT_TYPE);
        menu.getChildren().add(sep);

        // add menu items for changing column visibility
        UIMenuGroup group = (HtmlMenuGroup) context.getApplication()
                .createComponent(UIMenuGroup.COMPONENT_TYPE);
        group.setValue(ComponentMessageUtil.getMessage(context, MSG_COLUMNS,
                new Object[] {}).getSummary());
        group.setIcon(iconColumnsURI);
        menu.getChildren().add(group);

        Iterator<UIColumn> columns = table.getSortedColumns();
        visibleColumnsCount = table.getVisibleColumnsCount();

        while (columns.hasNext()) {
            buildMenuItem(group, columns.next());
        }// while
    }// buildMenu

    /**
     * Builds menu item for sorting.
     * 
     * @param parent
     *            parent component for created item
     * @param asc
     *            sort direction
     * @throws IOException
     */
    protected void buildSortMenuItem(UIComponent parent, boolean asc)
            throws IOException {
        UIMenuItem menuItem = (UIMenuItem) context.getApplication()
                .createComponent(UIMenuItem.COMPONENT_TYPE);

        menuItem.setSubmitMode("none");
        String actionScript = null;
        StringBuilder actionScriptBuilder = new StringBuilder();
        if ((Boolean) column.getAttributes().get("sortable")) {
            if (sortFunction != null) {
                if (prepareFunction != null) {
                    actionScriptBuilder.append(prepareFunction.toScript())
                            .append("; ");
                }

                actionScriptBuilder.append(sortFunction.toScript());
                actionScript = actionScriptBuilder.toString();
                if (actionScript.contains("{columnId}")) {
                    String columnClientId = (String) column.getAttributes()
                            .get("columnClientId");
                    if (columnClientId == null)
                        columnClientId = column.getClientId(context);
                    actionScript = actionScript.replace("{columnId}",
                            columnClientId);
                }
                if (actionScript.contains("{sortDirection}"))
                    actionScript = actionScript.replace("{sortDirection}",
                            asc ? "asc" : "desc");
            }
        } else {
            menuItem.setDisabled(true);
        }
        menuItem.setValue(ComponentMessageUtil.getMessage(context,
                (asc ? MSG_SORT_ASC : MSG_SORT_DESC), new Object[] {})
                .getSummary());
        menuItem.setIcon((asc ? iconSortAscURI : iconSortDescURI));
        if (menuItem instanceof HtmlMenuItem) {
            ((HtmlMenuItem) menuItem)
                    .setOnclick(actionScript == null ? "return false;"
                            : actionScript);
        }

        // add item to parent
        parent.getChildren().add(menuItem);
    }// buildSortMenuItem

    /**
     * Builds menu item for sorting.
     * 
     * @param parent
     *            parent component for created item
     * @throws IOException
     */
    protected void buildGroupMenuItem(UIComponent parent) throws IOException {
        UIMenuItem menuItem = (UIMenuItem) context.getApplication()
                .createComponent(UIMenuItem.COMPONENT_TYPE);

        menuItem.setSubmitMode("none");
        String actionScript = null;
        StringBuilder actionScriptBuilder = new StringBuilder();
        boolean isGroupingColumn = column.getId().equalsIgnoreCase(
                table.getGroupByColumnId());
        if ((Boolean) column.getAttributes().get("sortable")) {
            if (groupFunction != null) {
                if (prepareFunction != null) {
                    actionScriptBuilder.append(prepareFunction.toScript())
                            .append("; ");
                }
                actionScriptBuilder.append(groupFunction.toScript());
                actionScript = actionScriptBuilder.toString();
                if (actionScript.contains("{columnId}")) {
                    String columnClientId = (String) column.getAttributes()
                            .get("columnClientId");
                    if (columnClientId == null)
                        columnClientId = column.getClientId(context);
                    if (isGroupingColumn) {
                        columnClientId = "";
                    }
                    actionScript = actionScript.replace("{columnId}",
                            columnClientId);
                }
            }
        } else {
            menuItem.setDisabled(true);
        }

        menuItem.setValue(ComponentMessageUtil.getMessage(context,
                (isGroupingColumn ? MSG_GROUP_OFF : MSG_GROUP_ON),
                new Object[] {}).getSummary());
        menuItem.setIcon(iconGroupURI);
        if (menuItem instanceof HtmlMenuItem) {
            ((HtmlMenuItem) menuItem)
                    .setOnclick(actionScript == null ? "return false;"
                            : actionScript);
        }

        // add item to menu
        parent.getChildren().add(menuItem);
    }// buildSortMenuItem

    /**
     * Builds menu items for changing column visibility.
     * 
     * @param parent
     *            parent component for created item
     * @param col
     *            column component correspond to created item
     * @throws IOException
     */
    protected void buildMenuItem(UIComponent parent, UIColumn col)
            throws IOException {
    	
        if (col.isRendered(false)) {
            UIMenuItem menuItem = (UIMenuItem) context.getApplication()
                    .createComponent(UIMenuItem.COMPONENT_TYPE);

            menuItem.setSubmitMode("none");
            boolean columnVisible = col.isVisible();
            String actionScript = null;
            StringBuilder actionScriptBuilder = new StringBuilder();

            menuItem.setStyle("text-align: left;");

            if ((!columnVisible) || (visibleColumnsCount > 1)) {
                if (changeColumnVisibilityFunction != null) {
                    if (prepareFunction != null) {
                        actionScriptBuilder.append(prepareFunction.toScript())
                                .append("; ");
                    }
                    actionScriptBuilder.append(changeColumnVisibilityFunction
                            .toScript());
                    actionScript = actionScriptBuilder.toString();
                    if (actionScript.contains("{columnId}"))
                        actionScript = actionScript.replace("{columnId}",
                        		col.getId());
                }
            }// if
            String label = (String)col.getAttributes().get("label");
            menuItem.setValue(label == null ? "" : label);
            menuItem.setIcon(columnVisible ? iconCheckedURI : iconUncheckedURI);
            if (menuItem instanceof HtmlMenuItem) {
                ((HtmlMenuItem) menuItem)
                        .setOnclick(actionScript == null ? "return false;"
                                : actionScript);
            }

            // add item to menu
            parent.getChildren().add(menuItem);
        }
    }// buildMenuItem

    /*
     * (non-Javadoc)
     * 
     * @see org.richfaces.renderkit.html.TableMenuRenderer#createShowMenuEventFunction()
     */
    public JSFunctionDefinition createShowMenuEventFunction() {
        JSFunctionDefinition definition = new JSFunctionDefinition();
        definition.addParameter("event");
        definition.addParameter("columnId");
        definition.addParameter("menuId");
        JSFunction invocation = new JSFunction(
                "Richfaces.componentControl.performOperation");
        invocation.addParameter(new JSReference("event"));
        invocation.addParameter(new JSReference("menuId"));
        invocation.addParameter("show");
        // invocation.addParameter(new JSReference("{'columnId':columnId}"));
        invocation.addParameter(new JSReference("{}"));
        invocation.addParameter(Boolean.FALSE);
        definition.addToBody(invocation.toScript()).addToBody(";\n");
        return definition;
    }

}
