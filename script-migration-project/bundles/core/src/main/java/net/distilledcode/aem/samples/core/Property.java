package net.distilledcode.aem.samples.core;

public class Property {

	String name;
	String type;
	String value;
	
	
	public Property() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Property(String name, String type, String value) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
