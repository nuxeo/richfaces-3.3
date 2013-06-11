package org.ajax4jsf.bean;

public class AjaxLogBean {

    private boolean popup = false;

    /**
     * Gets value of popup field.
     * 
     * @return value of popup field
     */
    public boolean isPopup() {
        return popup;
    }

    /**
     * Set a new value for popup field.
     * 
     * @param popup
     *            a new value for popup field
     */
    public void setPopup(boolean popup) {
        this.popup = popup;
    }

    public void initPopupMode() {
        popup = true;
    }

    public void reset() {
        popup = false;
    }

}
