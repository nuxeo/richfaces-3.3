/**
 * 
 */
package util.componentInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;

/**
 * @author user
 *
 */
public class Info {
	private ComponentInfo info = ComponentInfo.getInstance(); 
	private ComponentAttribute componentAttribute = info.getComponentAttribute();;
	private HtmlPanelGrid panelEvent = null;
	
	public HtmlPanelGrid getPanelEvent() {
		return panelEvent;
	}

	public void setPanelEvent(HtmlPanelGrid panelEvent) {
		this.panelEvent = panelEvent;
	}

	public Info() {
	}
	
	public String  getName() {
		return info.getName();
	}
	
	public List<Pair> getAttribute(){
		List<Pair> list = new ArrayList<Pair>();
		Set<Entry<String, String>> set = componentAttribute.getAttributeEntry();
		Iterator<Map.Entry<String, String>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String str = Character.toUpperCase(entry.getKey().charAt(0)) + entry.getKey().substring(1);
//			Object obj = null;
//			if(entry.getValue().equals("boolean"))
//				obj = info.invoke("is" + str, new Class[]{}, new Object[]{});
//			else obj = info.invoke("get" + str, new Class[]{}, new Object[]{});
			list.add(new Pair(entry.getKey(), entry.getValue()));
		}
		return list;
	}

	public List<Pair> getEvent(){
		FacesContext context = FacesContext.getCurrentInstance();
		List<Pair> list = new ArrayList<Pair>();
		Set<Entry<String, String>> set = componentAttribute.getEventEntry();
		Iterator<Map.Entry<String, String>> iterator = set.iterator();
		panelEvent.getChildren().clear();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String key = entry.getKey();

			HtmlOutputText out = new HtmlOutputText();
			out.setValue(key);
			panelEvent.getChildren().add(out);
	
			HtmlInputText input = new HtmlInputText();
			input.setValue("don't work");
			input.setStyle("color:red");
			input.setId(key + "InputID");
			panelEvent.getChildren().add(input);

			list.add(new Pair(key,"showEvent('" + input.getClientId(context) + "', '" + key + "work!')"));
		}
		return list;
	}
	
	public List<Pair> getStyle(){
		List<Pair> list = new ArrayList<Pair>();
		Set<Entry<String, String>> set = componentAttribute.getStyleEntry();
		Iterator<Map.Entry<String, String>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			list.add(new Pair(entry.getKey(), entry.getValue()));
		}
		return list;
	}
	
	public class Pair {
		private String key;
		private String value;
		
		public Pair(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
