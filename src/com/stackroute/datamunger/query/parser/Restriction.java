package com.stackroute.datamunger.query.parser;

/*
 * This class is used for storing name of field, condition and value for 
 * each conditions
 * */
public class Restriction {
	
	private String propertyName;
	private String propertyValue;
	private String condition;
	
	// Write logic for constructor
	public Restriction(String propertyName, String propertyValue, String condition) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.condition = condition;
	}
	
	/**
	 * @param propertyName the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * @param propertyValue the propertyValue to set
	 */
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	/**
	 * 
	 * @return propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}
	/**
	 * 
	 * @return propertyValue
	 */
	public String getPropertyValue() {
		return propertyValue;
	}
	/**
	 * 
	 * @return condition
	 */
	public String getCondition() {
		return condition;
	}

	@Override
	public String toString() {
		return "Restriction [propertyName=" + propertyName + ", propertyValue=" + propertyValue + ", condition="
				+ condition + "]";
	}
		
}
