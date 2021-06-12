package com.stackroute.datamunger.query;


import java.util.HashMap;

//header class containing a Collection containing the headers
public class Header extends HashMap<String, Integer> {
	
	/**
	 * key - header column name
	 * value - index
	 */
	HashMap<String, Integer> headerMap;
	/**
	 * constructor
	 * @param headerMap
	 */
	
	public Header(HashMap<String, Integer> headerMap) {
		this.headerMap = headerMap;
	}
	/**
	 * @return the headerMap
	 */
	public HashMap<String, Integer> getHeaderMap() {
		return headerMap;
	}
	/**
	 * @param headerMap the headerMap to set
	 */
	public void setHeaderMap(HashMap<String, Integer> headerMap) {
		this.headerMap = headerMap;
	}
	@Override
	public String toString() {
		return "Header [headerMap=" + headerMap + "]";
	}
	
}
