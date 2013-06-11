package parser;

import java.util.ArrayList;

public class AttributesList extends ArrayList<Attribute> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3245089852351607636L;
	
	public AttributesList(){
		super();
	}
	
	public ArrayList<String> getNamesArray(){
		ArrayList<String> result = new ArrayList<String>();
		for(Attribute attr:this){
			result.add(attr.getName());
		}
		return result;		
	}
	
	public ArrayList<String> getDescriptionArray(){
		ArrayList<String> result = new ArrayList<String>();
		for(Attribute attr:this){
			result.add(attr.getDescription());
		}
		return result;		
	}
	
	public ArrayList<String> getTypeArray(){
		ArrayList<String> result = new ArrayList<String>();
		for(Attribute attr:this){
			result.add(attr.getType());
		}
		return result;		
	}
	
	public ArrayList<Status> getStatusArray(){
		ArrayList<Status> result = new ArrayList<Status>();
		for(Attribute attr:this){
			result.add(attr.getStatus());
		}
		return result;		
	}
	
	public ArrayList<Attribute> getHandlers(){		
		ArrayList<Attribute> result = new ArrayList<Attribute>();
		for(Attribute attr:this){
			if (attr.getName().startsWith("on")) {
				result.add(attr);
			}
		}		
		return result;
	}
	
	public ArrayList<Attribute> getStyles(){		
		ArrayList<Attribute> result = new ArrayList<Attribute>();
		for(Attribute attr:this){
			if (attr.getName().indexOf("tyle") != -1 || attr.getName().indexOf("lass") != -1) {
				result.add(attr);
			}
		}		
		return result;
	}
	
	public ArrayList<Attribute> getCommonAttributes(){				
		ArrayList<Attribute> result = new ArrayList<Attribute>();
		for(Attribute attr:this){
			if(!(attr.getName().startsWith("on") || (attr.getName().indexOf("tyle") != -1) || (attr.getName().indexOf("lass") != -1))){
				result.add(attr);
			}
		}		
		return result;
	}
	
}
