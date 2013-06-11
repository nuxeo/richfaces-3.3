package org.ajax4jsf.bean;

import java.util.Date;

import org.ajax4jsf.event.AjaxEvent;

public class AjaxPageBean {

    private String value = "value";

    /**
     * Gets value of value field.
     * @return value of value field
     */
    public String getValue() {
        return value;
    }

    /**
     * Set a new value for value field.
     * @param value a new value for value field
     */
    public void setValue(String value) {
        this.value = value;
    }

    private String status = "";

    /**
     * Gets value of status field.
     * @return value of status field
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set a new value for status field.
     * @param status a new value for status field
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return String.valueOf(new Date().getTime());
    }

    public void ajaxListener(AjaxEvent event) {
        setStatus("AjaxEvent");
    }

}
