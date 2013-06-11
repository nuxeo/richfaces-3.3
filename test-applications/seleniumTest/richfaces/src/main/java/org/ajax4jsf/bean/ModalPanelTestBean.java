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

/**
 * rich:modalPannel component test bean
 * @author Alexandr Levkovsky
 *
 */
public class ModalPanelTestBean {

    private Boolean rendered = true;
    private Boolean showWhenRendered = false;
    private Boolean moveable = true;
    private Boolean resizeable = true;
    private Boolean autosized = false;
    private Boolean keepVisualState = false;
    private Boolean trimOverlayedElements = true; 
    private String input;
    /**
     * @return the rendered
     */
    public Boolean getRendered() {
        return rendered;
    }
    /**
     * @param rendered the rendered to set
     */
    public void setRendered(Boolean rendered) {
        this.rendered = rendered;
    }
    /**
     * @return the showWhenRendered
     */
    public Boolean getShowWhenRendered() {
        return showWhenRendered;
    }
    /**
     * @param showWhenRendered the showWhenRendered to set
     */
    public void setShowWhenRendered(Boolean showWhenRendered) {
        this.showWhenRendered = showWhenRendered;
    }
    /**
     * @return the moveable
     */
    public Boolean getMoveable() {
        return moveable;
    }
    /**
     * @param moveable the moveable to set
     */
    public void setMoveable(Boolean moveable) {
        this.moveable = moveable;
    }
    /**
     * @return the resizeable
     */
    public Boolean getResizeable() {
        return resizeable;
    }
    /**
     * @param resizeable the resizeable to set
     */
    public void setResizeable(Boolean resizeable) {
        this.resizeable = resizeable;
    }
    
    public String changeAttributes(){
	showWhenRendered = true;
	moveable = false;
	resizeable = false;
	return null;
    }
    
    public String changeRendered(){
	rendered = false;
	return null;
    }
    
    public String setAutosized() {
    	resizeable = false;
    	autosized = true;
    	showWhenRendered = true;
    	return null;
    }
    
    public String changeKeepVisualState() {
    	keepVisualState = true;
    	showWhenRendered = false;
    	return null;
    }
    
    public String changeTrimOverlayed() {
    	trimOverlayedElements = false;
    	return null;
    }
    
    public String reset(){
    	rendered = true;
    	showWhenRendered = false;
    	moveable = true;
    	resizeable = true;
    	input = null;
    	autosized = false;
    	keepVisualState = false;
    	trimOverlayedElements = true;
    	return null;
    }
    
	/**
	 * @return the input
	 */
	public String getInput() {
		return input;
	}
	/**
	 * @param input the input to set
	 */
	public void setInput(String input) {
		this.input = input;
	}
	/**
	 * @return the autosized
	 */
	public Boolean getAutosized() {
		return autosized;
	}
	/**
	 * @param autosized the autosized to set
	 */
	public void setAutosized(Boolean autosized) {
		this.autosized = autosized;
	}
	/**
	 * @return the keepVisualState
	 */
	public Boolean getKeepVisualState() {
		return keepVisualState;
	}
	/**
	 * @param keepVisualState the keepVisualState to set
	 */
	public void setKeepVisualState(Boolean keepVisualState) {
		this.keepVisualState = keepVisualState;
	}
	/**
	 * @return the trimOverlayedElements
	 */
	public Boolean getTrimOverlayedElements() {
		return trimOverlayedElements;
	}
	/**
	 * @param trimOverlayedElements the trimOverlayedElements to set
	 */
	public void setTrimOverlayedElements(Boolean trimOverlayedElements) {
		this.trimOverlayedElements = trimOverlayedElements;
	}
    
}
