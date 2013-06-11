package org.richfaces.helloworld.domain.richPanels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapComponent {
	private Map<String, ArrayList<String>> m;
	
	public MapComponent() {
		m = new HashMap<String, ArrayList<String>>();
	}
	
	public void add(String component, ArrayList<String> path) {
		m.put(component, path);
	}

	public ArrayList<String> get(String key){
		return m.get(key);
	}
}
