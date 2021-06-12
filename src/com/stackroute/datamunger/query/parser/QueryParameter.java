package com.stackroute.datamunger.query.parser;

import java.util.List;
import java.util.Map;

/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */
public class QueryParameter {
	private String fileName;
	private List<String> fields;
	private List<Restriction> restrictions;
	private String baseQuery;
	private List<AggregateFunction> aggregateFunctions;
	private List<String> logicalOperators;
	private List<String> orderByFields;
	private List<String> groupByFields;
	private String QUERY_TYPE;
	

	/**
	 * @param fileName the filename to set
	 */
	public void setFilename(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	/**
	 * @param restrictions the restrictions to set
	 */
	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	/**
	 * @param baseQuery the baseQuery to set
	 */
	public void setBaseQuery(String baseQuery) {
		this.baseQuery = baseQuery;
	}

	/**
	 * @param aggregateFunctions the aggregateFunctions to set
	 */
	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;
	}

	/**
	 * @param logicalOperators the logicalOperators to set
	 */
	public void setLogicalOperators(List<String> logicalOperators) {
		this.logicalOperators = logicalOperators;
	}

	/**
	 * @param orderByFields the orderByFields to set
	 */
	public void setOrderByFields(List<String> orderByFields) {
		this.orderByFields = orderByFields;
	}

	/**
	 * @param groupByFields the groupByFields to set
	 */
	public void setGroupByFields(List<String> groupByFields) {
		this.groupByFields = groupByFields;
	}

	/**
	 * @param qUERY_TYPE the QUERY_TYPE to set
	 */
	public void setQUERY_TYPE(String QUERY_TYPE) {
		this.QUERY_TYPE = QUERY_TYPE;
	}
	/**
	 * 
	 * @return filename
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 
	 * @return
	 */
	public List<String> getFields() {
		return fields;
	}
	/**
	 * 
	 * @return
	 */
	public List<Restriction> getRestrictions() {
		return restrictions;
	}
	/**
	 * 
	 * @return
	 */
	public String getBaseQuery() {
		return baseQuery;
	}
	/**
	 * 
	 * @return
	 */
	public List<AggregateFunction> getAggregateFunctions() {
		return aggregateFunctions;
	}
	/**
	 * 
	 * @return logicalOperators
	 */
	public List<String> getLogicalOperators() {
		return logicalOperators;
	}
	/**
	 * 
	 * @return groupByFields
	 */
	public List<String> getGroupByFields() {
		return groupByFields;
	}
	/**
	 * 
	 * @return orderByFields
	 */
	public List<String> getOrderByFields() {
		return orderByFields;
	}
	/**
	 * 
	 * @return QUERY_TYPE
	 */
	public String getQUERY_TYPE() {
		return QUERY_TYPE;
	}

	@Override
	public String toString() {
		return "QueryParameter [filename=" + fileName + ", fields=" + fields + ", restrictions=" + restrictions
				+ ", baseQuery=" + baseQuery + ", aggregateFunctions=" + aggregateFunctions + ", logicalOperators="
				+ logicalOperators + ", orderByFields=" + orderByFields + ", groupByFields=" + groupByFields
				+ ", QUERY_TYPE=" + QUERY_TYPE + "]";
	}
	
}
