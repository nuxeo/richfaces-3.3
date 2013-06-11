package org.ajax4jsf.bean;

public class InputNumberSpinnerBean {

    private Integer value = 20;

    private boolean disabled = false;

    private boolean enableManualInput = true;

    /**
     * Gets value of value field.
     * @return value of value field
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Set a new value for value field.
     * @param value a new value for value field
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * Gets value of disabled field.
     * @return value of disabled field
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Set a new value for disabled field.
     * @param disabled a new value for disabled field
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * Gets value of enableManualInput field.
     * @return value of enableManualInput field
     */
    public boolean isEnableManualInput() {
        return enableManualInput;
    }

    /**
     * Set a new value for enableManualInput field.
     * @param enableManualInput a new value for enableManualInput field
     */
    public void setEnableManualInput(boolean enableManualInput) {
        this.enableManualInput = enableManualInput;
    }

    public void reset() {
        this.value = 20;
        this.disabled = false;
        this.enableManualInput = true;
    }

}
