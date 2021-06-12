package com.stackroute.datamunger.query.parser;

/* This class is used for storing name of field, aggregate function for 
 * each aggregate function
 * */
public class AggregateFunction {
	private String field;
	private String function;
	// Write logic for constructor
	public AggregateFunction(String field, String function) {
		
		this.field = field;
		this.function = function;
	}
	
	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(String function) {
		this.function = function;
	}
	/**
	 * 
	 * @return function
	 */
	public String getFunction() {
		// TODO Auto-generated method stub
		return function;
	}
	/**
	 * 
	 * @return field
	 */
	public String getField() {
		// TODO Auto-generated method stub
		return field;
	}
	
	

}
