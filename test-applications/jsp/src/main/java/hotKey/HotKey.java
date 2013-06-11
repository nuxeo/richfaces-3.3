/**
 * 
 */
package hotKey;

import java.util.Collection;
import java.util.Collections;

import javax.faces.context.FacesContext;

import org.richfaces.component.html.HtmlHotKey;

import util.componentInfo.ComponentInfo;

/**
 * @author AYanul
 *
 */
public class HotKey {
	private String bindingInfo = "";
	private String handler = "alert('work')";
	private String key = "alt+q";
	private String selector = "";
	private String timing = "onload";
	private String type = "onkeydown";
	private boolean rendered = true;
	private boolean disableInInput = false;
	private boolean checkParent = false;
	private HtmlHotKey htmlHotKey = null;
	private String disableInInputTypes = "all";
	
	public String getDisableInInputTypes() {
		return disableInInputTypes;
	}

	public void setDisableInInputTypes(String disableInInputTypes) {
		this.disableInInputTypes = disableInInputTypes;
	}

	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlHotKey);
		return null;
	}
	
	public HtmlHotKey getHtmlHotKey() {
		return htmlHotKey;
	}
	public void setHtmlHotKey(HtmlHotKey htmlHotKey) {
		this.htmlHotKey = htmlHotKey;
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
		String b = htmlHotKey.getClientId(FacesContext.getCurrentInstance());
		if(b != null && b.length() != 0)	
			return "work";
		return "don't work";
	}
}
