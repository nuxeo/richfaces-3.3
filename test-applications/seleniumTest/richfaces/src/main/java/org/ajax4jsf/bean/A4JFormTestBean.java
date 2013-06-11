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

public class A4JFormTestBean {

    private String name = "before submit";

    private Boolean ajaxSubmit;

    private Boolean ignoreDupResponses;

    private Boolean prependId;

    private String rerender = "";

    private Boolean rendered = false;

    public String getRerender() {
        return rerender;
    }

    public void setRerender(String rerender) {
        this.rerender = rerender;
    }

    public Boolean getAjaxSubmit() {
        return ajaxSubmit;
    }

    public void setAjaxSubmit(Boolean ajaxSubmit) {
        this.ajaxSubmit = ajaxSubmit;
    }

    public Boolean getIgnoreDupResponses() {
        return ignoreDupResponses;
    }

    public void setIgnoreDupResponses(Boolean ignoreDupResponses) {
        this.ignoreDupResponses = ignoreDupResponses;
    }

    public Boolean getPrependId() {
        return prependId;
    }

    public void setPrependId(Boolean prependId) {
        this.prependId = prependId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRendered() {
        return rendered;
    }

    public void setRendered(Boolean rendered) {
        this.rendered = rendered;
    }

    public void changeName() {
        this.name = "after submit";
    }

    public String reset() {
        name = "before submit";
        ajaxSubmit = false;
        ignoreDupResponses = false;
        prependId = false;
        rerender = "";
        rendered = false;
        return null;
    }

}
