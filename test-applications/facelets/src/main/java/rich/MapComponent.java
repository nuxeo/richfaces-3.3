package rich;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapComponent {
	private Map<String, ArrayList<String>> m;
	
	public MapComponent() {
		m = new TreeMap<String, ArrayList<String>>();
	}
	
	public void add(String component, ArrayList<String> path) {
		m.put(component, path);
	}

	public ArrayList<String> get(String key){
		return m.get(key);
	}
	
	public Set getSet() {
		return m.keySet();
	}
}
