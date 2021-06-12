package com.stackroute.datamunger.query;

import java.util.HashMap;

//contains the row object as ColumnName/Value. Hence, HashMap is being used
public class Row extends HashMap<String, String>{
	/**
	 * key - columnName
	 * value - Column value
	 */
	private HashMap<String, String> rowMap;

	/**
	 * @param rowMap
	 */
	public Row(HashMap<String, String> rowMap) {
		this.rowMap = rowMap;
	}

	/**
	 * @return the rowMap
	 */
	public HashMap<String, String> getRowMap() {
		return rowMap;
	}

	/**
	 * @param rowMap the rowMap to set
	 */
	public void setRowMap(HashMap<String, String> rowMap) {
		this.rowMap = rowMap;
	}

	@Override
	public String toString() {
		return "Row [rowMap=" + rowMap + "]";
	}
	
}
