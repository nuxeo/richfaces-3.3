/**
 * 
 */
package util.componentInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author user
 *
 */
public class ComponentAttribute {
	private Map<String, String> attribute = new HashMap<String, String>();
	private Map<String, String> event = new HashMap<String, String>();
	private Map<String, String> style = new HashMap<String, String>();
	
	public String putAttribute(String attribute, String value) {
		return this.attribute.put(attribute, value);
	}
	
	public String putEvent(String event, String value){
		return this.event.put(event, value);
	}
	
	public String putStyle(String style, String value) {
		return this.style.put(style, value);
	}
	
	public String removeAttribute(String attribute) {
		return this.attribute.remove(attribute);
	}
	
	public String removeEvent(String event) {
		return this.attribute.remove(event);
	}
	
	public String removeStyle(String style) {
		return this.attribute.remove(style);
	}
	
	public void cleanAll() {
		this.attribute.clear();
		this.event.clear();
		this.style.clear();
	}
	
	public Set<Map.Entry<String, String>> getAttributeEntry() {
		return attribute.entrySet();
	}
	
	public Set<Map.Entry<String, String>> getEventEntry(){
		return event.entrySet();
	}
	
	public Set<Map.Entry<String, String>> getStyleEntry(){
		return style.entrySet();
	}
}
