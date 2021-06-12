package com.stackroute.datamunger.query;

import java.util.HashMap;

//this class will be used to store the column data types as columnIndex/DataType
public class RowDataTypeDefinitions extends HashMap<Integer, String>{
	/**
	 * key - index of field
	 * value - datatype of the field
	 */
	HashMap<Integer, String> rowDataTypeDefinitionMap;
	/**
	 * 
	 * @param rowDataTypeDefMap
	 */
	public RowDataTypeDefinitions(HashMap<Integer, String> rowDataTypeDefinitionMap) {
		this.rowDataTypeDefinitionMap = rowDataTypeDefinitionMap;
	}
	
	/**
	 * @return the rowDataTypeDefinitionMap
	 */
	public HashMap<Integer, String> getRowDataTypeDefinitionMap() {
		return rowDataTypeDefinitionMap;
	}

	/**
	 * @param rowDataTypeDefinitionMap the rowDataTypeDefinitionMap to set
	 */
	public void setRowDataTypeDefinitionMap(HashMap<Integer, String> rowDataTypeDefinitionMap) {
		this.rowDataTypeDefinitionMap = rowDataTypeDefinitionMap;
	}
	
	@Override
	public String toString() {
		return "RowDataTypeDefinitions [rowDataTypeDefinitionMap=" + rowDataTypeDefinitionMap + "]";
	}
	
}
