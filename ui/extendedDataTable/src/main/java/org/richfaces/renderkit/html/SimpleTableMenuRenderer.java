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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.context.ResponseWriter;

import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.richfaces.component.UIColumn;

/**
 * @author pawelgo
 * 
 */
public class SimpleTableMenuRenderer extends TableMenuRenderer {

    private static final long serialVersionUID = -3907452284006250197L;

    private int visibleColumnsCount;
    private ResponseWriter writer;

    /*
     * (non-Javadoc)
     * 
     * @see org.richfaces.renderkit.html.TableMenuRenderer#render()
     */
    public String render() throws IOException {

        String menuId = table.getClientId(context) + ":tm";

        Iterator<UIColumn> columns = table.getSortedColumns();
        visibleColumnsCount = table.getVisibleColumnsCount();

        writer = context.getResponseWriter();
        // print main menu DIV element
        writer.startElement(HTML.DIV_ELEM, table);

        writer.writeAttribute(HTML.id_ATTRIBUTE, menuId, null);
        writer.writeAttribute(HTML.class_ATTRIBUTE, "dt-menu", null);
        writer.writeAttribute(HTML.style_ATTRIBUTE,
                "z-index: 15005; visibility: visible; left: 0px; top: 0px;",
                null);
        // writer.writeAttribute(HTML.style_ATTRIBUTE, "position: absolute;
        // z-index: 15005; visibility: visible; left: 0px; top: 0px;", null);
        writer.startElement("ul", table);
        writer.writeAttribute(HTML.class_ATTRIBUTE, "dt-menu-list", null);
        for (; columns.hasNext();) {
            renderMenuItem(columns.next());
        }// for
        writer.endElement("ul");
        writer.endElement(HTML.DIV_ELEM);
        return menuId;
    }// render

    protected void renderMenuItem(UIColumn column) throws IOException {
        if (column instanceof UIColumn) {
            UIColumn dataColumn = (UIColumn) column;
            Boolean v = dataColumn.isVisible();
            boolean columnVisible = (v == null ? Boolean.TRUE : v);
            String actionScript = null;
            if ((!columnVisible) || (visibleColumnsCount > 1)) {
                boolean ajaxSingle = true;
                Map<String, Object> requestOpts = AjaxRendererUtils
                        .buildEventOptions(context, dataColumn);

                if (onAjaxCompleteFunction != null)
                    requestOpts.put(AjaxRendererUtils.ONCOMPLETE_ATTR_NAME,
                            onAjaxCompleteFunction);

                @SuppressWarnings("unchecked")
                Map<String, Object> parameters = (Map<String, Object>) requestOpts
                        .get("parameters");
                if (parameters == null) {
                    parameters = new HashMap<String, Object>();
                    requestOpts.put("parameters", parameters);
                }
                if (ajaxSingle) {
                    if (!parameters
                            .containsKey(AjaxRendererUtils.AJAX_SINGLE_PARAMETER_NAME))
                        parameters.put(
                                AjaxRendererUtils.AJAX_SINGLE_PARAMETER_NAME,
                                dataColumn.getParent().getClientId(context));
                    if (!requestOpts.containsKey("control"))
                        requestOpts.put("control", JSReference.THIS);
                }
                parameters.put(dataColumn.getParent().getClientId(context)
                        + ":" + CHANGE_COL_VISIBILITY, dataColumn.getId());

                JSFunction dropFunction = AjaxRendererUtils.buildAjaxFunction(
                        dataColumn, context);
                dropFunction.addParameter(requestOpts);
                actionScript = dropFunction.toScript();
            }// if

            writer.startElement("li", dataColumn);
            writer.writeAttribute(HTML.class_ATTRIBUTE, "dt-menu-list-item"
                    + (columnVisible ? " dt-menu-item-checked" : ""), null);

            writer.startElement(HTML.a_ELEMENT, dataColumn);
            writer.writeAttribute(HTML.HREF_ATTR, "#", null);
            writer.writeAttribute(HTML.class_ATTRIBUTE,
                    "dt-menu-item dt-menu-check-item", null);
            actionScript = (actionScript == null ? "return false;"
                    : actionScript + ";return false;");
            writer.writeAttribute(HTML.onclick_ATTRIBUTE, actionScript, null);
            writer.startElement(HTML.IMG_ELEMENT, dataColumn);
            writer.writeAttribute(HTML.class_ATTRIBUTE, "dt-menu-item-icon",
                    null);
            InternetResource res = InternetResourceBuilder.getInstance().getResource("/org/richfaces/renderkit/html/images/s.gif");
            writer.writeAttribute(HTML.src_ATTRIBUTE, InternetResourceBuilder
					.getInstance().getUri(res, context, null), null);
            writer.endElement(HTML.IMG_ELEMENT);
            String label = (String)dataColumn.getAttributes().get("label");
            writer.writeText(label == null ? "" : label, null);
            writer.endElement(HTML.a_ELEMENT);
            writer.endElement("li");
        }
    }// encodeMenuItem

    public JSFunctionDefinition createShowMenuEventFunction() {
        return new JSFunctionDefinition();
    }

}
