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
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.org.w3c.tidy.EntityTable;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.component.UIColumn;
import org.richfaces.component.UIDataTable;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;
import org.richfaces.renderkit.DragIndicatorRendererBase;
import org.richfaces.renderkit.ScriptOptions;

/**
 * Renderer for support drag'n drop for
 * {@link org.richfaces.component.UIExtendedDataTable} component specialized for
 * changing table columns order event.
 * 
 * @author pawelgo
 * 
 */
public class TableDragDropRenderer implements Serializable {

    private static final long serialVersionUID = -8059307641808179967L;

    public final static String DRAG_SOURCE_SCRIPT_ID = "dnd_drag_script";

    public final static String DROP_TARGET_SCRIPT_ID = "dnd_drop_script";

    public final static String DROP_TARGET_BEFORE = "_left";

    public final static String DROP_TARGET_AFTER = "_right";

    private FacesContext context;

    private JSFunctionDefinition onAjaxCompleteFunctionDef;

    private JSFunction preSendAjaxRequestFunction;

	private TableDragDropRenderer(FacesContext context) {
        super();
        this.context = context;
    }

    /**
     * Creates new instance.
     * 
     * @param context
     *            faces context
     * @return instance object
     */
    public static TableDragDropRenderer getInstance(FacesContext context) {
        return new TableDragDropRenderer(context);
    }

    /**
     * Encodes child scripts. It uses
     * {@link org.richfaces.renderkit.DragIndicatorRendererBase} renderer and
     * overwrites marker for DEFAULT drag indicator state. Drag indicator image
     * is the same in DEFAULT and REJECT states.
     * 
     * @param context
     *            faces context
     * @param component
     *            table component
     * @throws IOException
     */
    public void encodeChildScripts(FacesContext context, UIDataTable component)
            throws IOException {
        // new DragIndicatorRendererBase().encodeChildScripts(context,
        // component);
        new DragIndicatorRendererBase() {
            public void encodeChildScripts(FacesContext context,
            		UIDataTable component) throws IOException {
            	
            	ResponseWriter responseWriter = context.getResponseWriter();
            	responseWriter.write("\nvar elt = $(\""+ component.getBaseClientId(context) +":dataTable_indicator\");\n");
            	responseWriter.write("elt.markers = {};\n");
            	responseWriter.write("elt.indicatorTemplates = {};\n");
            	
                super.encodeChildScripts(context, component);
                // redefine DEFAULT marker to be like REJECT marker
                
                responseWriter.write("elt.markers['" + DEFAULT + "'] = \"");
                responseWriter.write(getPredefinedMarker(context, REJECT));
                responseWriter.write("\";\n");
                responseWriter.write("createExtDragIndicator(elt, '', '');\n");
                
            }
        }.encodeChildScripts(context, component);
    }

    /**
     * Renders support for drag operation specialized for changing table columns
     * order event.
     * 
     * @param column
     *            table column
     * @param dragSourceId
     *            drag source HTML element id
     * @param indicatorId
     *            drag indicator id
     * @param dragLabel
     *            label to be displayed in indicator
     * @throws IOException
     */
    public void renderDragSupport(UIColumn column, String dragSourceId,
            String indicatorId, String dragLabel) throws IOException {
    	String varName = "DnD_ExtSimpleDraggable_"+column.getId().replaceAll("[^A-Za-z0-9_]", "_");
        StringBuffer buffer = new StringBuffer("delete " + varName + ";\nvar " + varName + " = ");
        JSFunction function = new JSFunction("new DnD.ExtSimpleDraggable");
        function.addParameter(dragSourceId);
        String dragSourceScriptId = column.getClientId(context) + ":"
                + DRAG_SOURCE_SCRIPT_ID;
        ExtDraggableRendererContributor contributor = ExtDraggableRendererContributor
                .getInstance();
        ScriptOptions dragOptions = contributor.buildOptions(context, column,
                dragSourceScriptId, indicatorId);

        JSONObject dndParams = new JSONObject();
        try {
            dndParams.put("label", dragLabel == null ? "" : dragLabel);
        } catch (JSONException e) {
        }
        dragOptions.addOption("dndParams", dndParams.toString());

        function.addParameter(dragOptions);
        function.appendScript(buffer);

        String scriptContribution = contributor.getScriptContribution(context,
                column);
        if (scriptContribution != null && scriptContribution.length() != 0) {
            buffer.append(scriptContribution);
        }

        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(HTML.SCRIPT_ELEM, column);
        writer.writeAttribute("id", dragSourceScriptId, "id");
        writer.write(escapeHtmlEntities(buffer));
        writer.endElement(HTML.SCRIPT_ELEM);
    }// renderDragSupport

    /**
     * Renders support for drop operation specialized for changing table columns
     * order event.
     * 
     * @param column
     *            table column
     * @param dropTargetId
     *            drop target HTML element id
     * @param before
     *            true if target is positioned before column
     * @throws IOException
     */
    public void renderDropSupport(UIColumn column, String dropTargetId,
            boolean before) throws IOException {
        // RendererContributor contributor =
        // DropzoneRendererContributor.getInstance();
        ExtDropzoneRendererContributor contributor = ExtDropzoneRendererContributor
                .getInstance();
        String varName = "DnD_ExtSimpleDropZone_"+column.getId().replaceAll("[^A-Za-z0-9_]", "_") + (before?"L":"R");
        StringBuffer buffer = new StringBuffer("delete " + varName + ";\nvar " + varName + " = ");
        JSFunction function = new JSFunction("new DnD.ExtSimpleDropZone");
        function.addParameter(dropTargetId);
        ScriptOptions dropOptions = contributor.buildOptions(context, column);
        JSONObject dndParams = new JSONObject();
        dropOptions.addOption("dndParams", dndParams.toString());

        function.addParameter(dropOptions);
        function.appendScript(buffer);

        String dropTargetScriptId = column.getClientId(context) + ":"
                + DROP_TARGET_SCRIPT_ID
                + (before ? DROP_TARGET_BEFORE : DROP_TARGET_AFTER);
        String scriptContribution = contributor.getScriptContribution(context,
                column, dropTargetScriptId, preSendAjaxRequestFunction,
                onAjaxCompleteFunctionDef);
        if (scriptContribution != null && scriptContribution.length() != 0) {
            buffer.append(scriptContribution);
        }

        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(HTML.SCRIPT_ELEM, column);
        writer.writeAttribute("id", dropTargetScriptId, "id");
        writer.write(escapeHtmlEntities(buffer));
        writer.endElement(HTML.SCRIPT_ELEM);
    }// renderDropSupport

    /**
     * Help method for escaping HTML entities.
     * 
     * @param orig
     *            string to escape
     * @return string with escaped HTML entities
     */
    protected String escapeHtmlEntities(CharSequence orig) {
        StringBuffer buff = new StringBuffer(orig);
        EntityTable defaultEntityTable = EntityTable.getDefaultEntityTable();
        Matcher matcher = Pattern.compile("\\&\\w+\\;").matcher(orig);
        int delta = 0;
        while (matcher.find()) {
            String name = matcher.group().substring(0,
                    matcher.group().length() - 1);
            int code = defaultEntityTable.entityCode(name);
            if (0 != code) {
                String replacement = "&#" + code + ";";
                buff.replace(matcher.start() - delta, matcher.end() - delta,
                        replacement);
                delta = delta + matcher.group().length() - replacement.length();
            }
        }
        return buff.toString();
    }

    public JSFunctionDefinition getOnAjaxCompleteFunctionDef() {
        return onAjaxCompleteFunctionDef;
    }

    /**
     * Set JavaScript function to be called on AJAX request complete.
     * 
     * @param onAjaxCompleteFunction
     *            JavaScriot function to set
     */
    public void setOnAjaxCompleteFunctionDef(
            JSFunctionDefinition onAjaxCompleteFunctionDef) {
        this.onAjaxCompleteFunctionDef = onAjaxCompleteFunctionDef;
    }

    public void setPreSendAjaxRequestFunction(
            JSFunction preSendAjaxRequestFunction) {
        this.preSendAjaxRequestFunction = preSendAjaxRequestFunction;
    }

}
