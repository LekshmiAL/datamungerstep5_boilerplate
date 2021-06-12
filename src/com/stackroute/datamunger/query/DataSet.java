package com.stackroute.datamunger.query;

import java.util.LinkedHashMap;

//this class will be acting as the DataSet containing multiple rows
public class DataSet extends LinkedHashMap<Long, Row> {
	LinkedHashMap<Long, Row> dataSetMap;

	/**
	 * @param dataSetMap
	 */
	public DataSet(LinkedHashMap<Long, Row> dataSetMap) {
		this.dataSetMap = dataSetMap;
	}

	/**
	 * @return the dataSetMap
	 */
	public LinkedHashMap<Long, Row> getDataSetMap() {
		return dataSetMap;
	}

	/**
	 * @param dataSetMap the dataSetMap to set
	 */
	public void setDataSetMap(LinkedHashMap<Long, Row> dataSetMap) {
		this.dataSetMap = dataSetMap;
	}

	@Override
	public String toString() {
		return "DataSet [dataSetMap=" + dataSetMap + "]";
	}
	
}
