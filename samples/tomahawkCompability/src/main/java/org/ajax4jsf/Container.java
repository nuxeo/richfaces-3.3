/**
 * 
 */
package org.ajax4jsf;

/**
 * @author asmirnov
 *
 */
public class Container {
    private boolean available;
    private boolean changed;
    private int containerNumber;
    /**
     * @return the available
     */
    public boolean isAvailable() {
        return available;
    }
    /**
     * @param available the available to set
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
    /**
     * @return the changed
     */
    public boolean isChanged() {
        return changed;
    }
    /**
     * @param changed the changed to set
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    /**
     * @return the containerNumber
     */
    public int getContainerNumber() {
        return containerNumber;
    }
    /**
     * @param containerNumber the containerNumber to set
     */
    public void setContainerNumber(int containerNumber) {
        this.containerNumber = containerNumber;
    }

}
