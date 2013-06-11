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

package org.ajax4jsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.ajax4jsf.template.Template;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TemplateBean {

    private static final Log log = LogFactory.getLog(TemplateBean.class);

    static final List<String> dataTableModel = new ArrayList<String>();
    static {
        dataTableModel.add("1");
        dataTableModel.add("2");
    }

    public static final String PARAM_NAME = "t";

    private Template template = Template.SIMPLE;

    private Template templatePath = Template.SIMPLE;

    private String methodName;

    private Integer dataTableRowIndex = 0;

    public String getTemplateId() {
        return template.toString();
    }

    public void setTemplateId(String template) {
        this.template = Template.valueOf(template);
        if (this.template.equals(Template.DATA_TABLE)) {
            dataTableRowIndex = 0;
        } else if (this.template.equals(Template.DATA_TABLE2)) {
            dataTableRowIndex = 1;
        }

    }

    public String getParentId() {
        return template.getPrefix();
    }

    /**
     * @return the template
     */
    public String getTemplate() {
        return "/template/" + template.getName() + ".xhtml";
    }

    /**
     * @return the template
     */
    public String getAutoTestTemplate() {
        return "/template/autotest/" + template.getName() + ".xhtml";
    }

    /**
     * @return the template
     */
    public String getTemplatePath() {
        return template.getName() + ".xhtml";
    }

    /**
     * @param template
     *                the template to set
     */
    public void setTemplate(Template template) {
        this.template = template;
    }

    public void setTemplatePath(Template templatePath) {
        this.templatePath = templatePath;
    }

    public List<SelectItem> getAvailableTemplates() {
        List<SelectItem> retVal = new ArrayList<SelectItem>();
        for (Template template : Template.values()) {
            retVal.add(new SelectItem(template.toString(), template.getDesc()));
        }

        return retVal;
    }

    /**
     * Invokes reset method of appropriate backing bean
     * 
     * @param actionEvent
     */
    public void reset(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            if (methodName != null && methodName.trim().length() > 0 && !"null".equals(methodName)) {
                    ExpressionFactory factory = context.getApplication().getExpressionFactory();
                    if (factory != null) {
                        MethodExpression methodExpression = factory.createMethodExpression(context.getELContext(),
                                methodName, Void.class, new Class[] {});
                        methodExpression.invoke(context.getELContext(), new Object[] {});
                    }
            }
        }

    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @param methodName
     *                the methodName to set
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * @return the dataTableRowIndex
     */
    public Integer getDataTableRowIndex() {
        return dataTableRowIndex;
    }

    /**
     * @return the dataTableModel
     */
    public List<String> getDataTableModel() {
        return dataTableModel;
    }

}