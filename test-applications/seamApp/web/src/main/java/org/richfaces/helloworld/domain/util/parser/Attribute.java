package org.richfaces.helloworld.domain.util.parser;

public class Attribute {
	private String name;
	private String type;
	private String description;
	private Status status;	
	
	public Attribute(){		
		this.description = "";
	}
	
	public Attribute(String name){
		this.name = name;	
		this.description = "";
	}
	
	public Attribute(String name, String type, String desc, Status status){
		this.name = name;
		this.type = type;
		this.description = desc;
		this.status = status;		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	public String toString(){
		return "[" + "Name: " + name + "\r\n" + "Description: " + description + "\r\n" +
		"Type: " + type + "\r\n" + "Status: " + status + "]";
	}
}

	enum Status {NOT_READY, IMPLEMENTED, FAILED, PASSED}
