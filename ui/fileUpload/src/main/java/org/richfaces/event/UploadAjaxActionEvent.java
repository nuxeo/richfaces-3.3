/**
 * 
 */
package org.richfaces.event;

import javax.faces.component.UIComponent;

import org.ajax4jsf.event.AjaxEvent;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */
public class UploadAjaxActionEvent extends AjaxEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9018152705741192920L;

	private String action;
	
	private String uploadId;
	
	public UploadAjaxActionEvent(UIComponent component, String action, String uploadId) {
		super(component);
		
		this.action = action;
		this.uploadId = uploadId;
	}
	
	public String getAction() {
		return action;
	}
	
	public String getUploadId() {
		return uploadId;
	}

}
