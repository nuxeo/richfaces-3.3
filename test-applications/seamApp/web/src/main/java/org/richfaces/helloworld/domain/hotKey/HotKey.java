/**
 * 
 */
package org.richfaces.helloworld.domain.hotKey;

import javax.faces.context.FacesContext;
import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.html.HtmlHotKey;

/**
 * @author AYanul
 *
 */
@Name("hotKey")
@Scope(ScopeType.SESSION)
public class HotKey {
	private HtmlHotKey binding = null;
	private String bindingInfo = "";
	private String handler = "alert('work')";
	private String key = "alt+q";
	private String selector = "";
	private String timing = "onload";
	private String type = "onkeydown";
	private boolean rendered = true;
	private boolean disableInInput = false;
	private boolean checkParent = false;
	
	/**
	 * @return the binding
	 */
	public HtmlHotKey getBinding() {
		return binding;
	}
	/**
	 * @param binding the binding to set
	 */
	public void setBinding(HtmlHotKey binding) {
		this.binding = binding;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(binding);
		return null;
	}
	
	/**
	 * @return the handler
	 */
	public String getHandler() {
		return handler;
	}
	/**
	 * @param handler the handler to set
	 */
	public void setHandler(String handler) {
		this.handler = handler; 
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the selector
	 */
	public String getSelector() {
		return selector;
	}
	/**
	 * @param selector the selector to set
	 */
	public void setSelector(String selector) {
		this.selector = selector;
	}
	/**
	 * @return the timing
	 */
	public String getTiming() {
		return timing;
	}
	/**
	 * @param timing the timing to set
	 */
	public void setTiming(String timing) {
		this.timing = timing;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the rendered
	 */
	public boolean isRendered() {
		return rendered;
	}
	/**
	 * @param rendered the rendered to set
	 */
	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}
	/**
	 * @return the disableInInput
	 */
	public boolean isDisableInInput() {
		return disableInInput;
	}
	/**
	 * @param disableInInput the disableInInput to set
	 */
	public void setDisableInInput(boolean disableInInput) {
		this.disableInInput = disableInInput;
	}
	/**
	 * @return the checkParent
	 */
	public boolean isCheckParent() {
		return checkParent;
	}
	/**
	 * @param checkParent the checkParent to set
	 */
	public void setCheckParent(boolean checkParent) {
		this.checkParent = checkParent;
	}
	/**
	 * @return the bindingInfo
	 */
	public String getBindingInfo() {
		String b = binding.getClientId(FacesContext.getCurrentInstance());
		if(b != null && b.length() != 0)	
			return "work";
		return "don't work";
	}
}
