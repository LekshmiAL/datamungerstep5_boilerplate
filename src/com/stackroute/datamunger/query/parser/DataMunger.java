package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataMunger {


	/*
	 * Extract the name of the file from the query. File name can be found after a
	 * space after "from" clause. Note: ----- CSV file can contain a field that
	 * contains from as a part of the column name. For eg: from_date,from_hrs etc.
	 * 
	 * Please consider this while extracting the file name in this method.
	 */

	public String getFileName(String queryString) {
		String subString="";
		String fileName="";
		int fromIndex = queryString.indexOf(" from ")+1;
		//substring starting from 'from' 
		subString = queryString.substring(fromIndex);
		if(subString.indexOf(" ")>0) {
			//substring starting from next word of 'from'
			subString = subString.substring(subString.indexOf(" ")+1);
		}
		if(subString.indexOf(" ")>0) {//contains other clause after from clause
			fileName = subString.substring(0, subString.indexOf(" ")).trim();
		}else {
			fileName = subString.trim();
		}
		return fileName;
	}

	/*
	 * This method is used to extract the baseQuery from the query string. BaseQuery
	 * contains from the beginning of the query till the where clause
	 * 
	 * Note: ------- 1. The query might not contain where clause but contain order
	 * by or group by clause 2. The query might not contain where, order by or group
	 * by clause 3. The query might not contain where, but can contain both group by
	 * and order by clause
	 */
	
	public String getBaseQuery(String queryString) {
		String baseQuery="";
		int endIndex;
		if(queryString.contains(" where ")) {
			endIndex = queryString.indexOf(" where ");
		}else {
			if(queryString.contains(" group by ")) {
				endIndex = queryString.indexOf(" group by ");
			}else if(queryString.contains(" order by ")){
				endIndex = queryString.indexOf(" order by ");
			}else {
				endIndex= -1;
			}
		}
		if(endIndex == -1) {
			baseQuery = queryString;
		}else {
			baseQuery = queryString.substring(0,endIndex);
		}
		return baseQuery;
	}

	/*
	 * This method will extract the fields to be selected from the query string. The
	 * query string can have multiple fields separated by comma. The extracted
	 * fields will be stored in a String array which is to be printed in console as
	 * well as to be returned by the method
	 * 
	 * Note: 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The field
	 * name can contain '*'
	 * 
	 */
	
	public List<String> getFields(String queryString) {
		String fieldsPart = queryString.substring(7, queryString.indexOf(" from "));
		List<String> fieldsList = new ArrayList<>(Arrays.asList(fieldsPart.split(",")));
		List<String> finalList = new ArrayList<>();
		for(String str : fieldsList) {
			finalList.add(str.trim());
		}
		return finalList;
	}

	/*
	 * This method is used to extract the conditions part from the query string. The
	 * conditions part contains starting from where keyword till the next keyword,
	 * which is either group by or order by clause. In case of absence of both group
	 * by and order by clause, it will contain till the end of the query string.
	 * Note:  1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */
	
	public String getConditionsPartQuery(String queryString) {
		//queryString = queryString.toLowerCase();
		String subString="";
		String conditionClause = null;
		int endIndex;
		if(queryString.contains(" where ")) {
			subString = queryString.substring(queryString.indexOf(" where ")+7);
			if(subString.contains(" group by ")) {
				endIndex = subString.indexOf(" group by ");
			}else if(subString.contains(" order by ")){
				endIndex = subString.indexOf(" order by ");
			}else {
				endIndex= -1;
			}
			if(endIndex == -1) {
				conditionClause = subString;
			}else {
				conditionClause =subString.substring(0, endIndex);
			}
		}
		return conditionClause;
	}

	/*
	 * This method will extract condition(s) from the query string. The query can
	 * contain one or multiple conditions. In case of multiple conditions, the
	 * conditions will be separated by AND/OR keywords. for eg: Input: select
	 * city,winner,player_match from ipl.csv where season > 2014 and city
	 * ='Bangalore'
	 * 
	 * This method will return a string array ["season > 2014","city ='bangalore'"]
	 * and print the array
	 * 
	 * Note: ----- 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */

	public List<Restriction> getRestrictions(String queryString) {
		String conditionalClause = getConditionsPartQuery(queryString);
		List<Restriction> restrictions = null;
		List<String> conditions =new ArrayList<>();
		String conditionOperator ="";
		String value ="";
		if(conditionalClause!=null && !conditionalClause.isEmpty()) {
			restrictions = new ArrayList<>();
			//split the condition clause
			//add each conditions to arraylist
			conditions.addAll(Arrays.asList(conditionalClause.split(" and | or ")));
			//split each restriction to name,value and condition
			for(String condition : conditions) {
				String[] splitArray = condition.split(">=|<=|=|>|<|!=");
				//splitArray[0] - name
				//splitArray[1] -value
				//find condition (between name and value)
				conditionOperator = condition.substring(condition.indexOf(splitArray[0])+splitArray[0].length(),
						condition.indexOf(splitArray[1]));
				if(splitArray[1].contains("'")) {
					String[] split = splitArray[1].trim().split("'");
					value = split[1];
				}else {
					value = splitArray[1].trim();
				}
				Restriction restriction = new Restriction(splitArray[0].trim(), value, conditionOperator);
				restrictions.add(restriction);
			}
		}
		
		return restrictions;
	}
	/*
	 * This method will extract logical operators(AND/OR) from the query string. The
	 * extracted logical operators will be stored in a String array which will be
	 * returned by the method and the same will be printed Note:  1. AND/OR
	 * keyword will exist in the query only if where conditions exists and it
	 * contains multiple conditions. 2. AND/OR can exist as a substring in the
	 * conditions as well. For eg: name='Alexander',color='Red' etc. Please consider
	 * these as well when extracting the logical operators.
	 * 
	 */

	public List<String> getLogicalOperators(String queryString) {
		String conditionsClause = getConditionsPartQuery(queryString);
		String splitRegex = "";
		List<String> conditionalOperator = null;
		if(conditionsClause != null && !conditionsClause.isBlank()) {
			conditionalOperator = new ArrayList<>();
			String[] restrictions = conditionsClause.split(" and | or ");
			for(String strItr : restrictions) {
				if(splitRegex.equals("")) {
					splitRegex = strItr;
				}else {
					splitRegex = splitRegex+"|"+strItr;
				}
			}
			String[] logicalOptr = conditionsClause.split(splitRegex);
			for(String optr : logicalOptr) {
				if(!optr.isBlank()) {
					conditionalOperator.add(optr.trim());
				}
			}
		}
		return conditionalOperator;
	}

	/*
	 * This method extracts the order by fields from the query string. Note: 
	 * 1. The query string can contain more than one order by fields. 2. The query
	 * string might not contain order by clause at all. 3. The field names,condition
	 * values might contain "order" as a substring. For eg:order_number,job_order
	 * Consider this while extracting the order by fields
	 */

	public List<String> getOrderByFields(String queryString) {
		String[] orderByFields = null;
		String orderByClause ="";
		List<String> orderByList = new ArrayList<>();
 		if(queryString.contains(" order by ")) {
			//get String after order by keyword
			orderByClause = queryString.substring(queryString.indexOf(" order by ")+10);
			orderByFields = orderByClause.split(",");
			orderByList.addAll(Arrays.asList(orderByFields));
		}
		return orderByList;
	}

	/*
	 * This method extracts the group by fields from the query string. Note:
	 * 1. The query string can contain more than one group by fields. 2. The query
	 * string might not contain group by clause at all. 3. The field names,condition
	 * values might contain "group" as a substring. For eg: newsgroup_name
	 * 
	 * Consider this while extracting the group by fields
	 */

	public List<String> getGroupByFields(String queryString) {
		String groupByClause ="";
		String[] groupByFields = null;
		List<String> groupByList = new ArrayList<>();
		int endIndex;
		if(queryString.contains(" group by ")) {
			//get String after group by keyword
			groupByClause = queryString.substring(queryString.indexOf(" group by ")+10);
			//if also have order by clause
			if(groupByClause.contains(" order by ")) {
				endIndex = groupByClause.indexOf(" order by ");
				groupByClause = groupByClause.substring(0, endIndex);
			}
			groupByFields = groupByClause.split(",");
			groupByList.addAll(Arrays.asList(groupByFields));
		}
		return groupByList;
	}

	/*
	 * This method extracts the aggregate functions from the query string. Note:
	 *  1. aggregate functions will start with "sum"/"count"/"min"/"max"/"avg"
	 * followed by "(" 2. The field names might
	 * contain"sum"/"count"/"min"/"max"/"avg" as a substring. For eg:
	 * account_number,consumed_qty,nominee_name
	 * 
	 * Consider this while extracting the aggregate functions
	 */

	public List<AggregateFunction> getAggregateFunctions(String queryString) {
		List<String> fields = getFields(queryString);
		List<AggregateFunction> aggregateFunctions = new ArrayList<>();
		String aggregateField ="";
		String function ="";
		if(!fields.contains("*")) {
			//iterate through each field
			for(String field : fields) {
				Matcher matcher = Pattern.compile("\\(([^)]+)\\)").matcher(field);
				if(matcher.find()) { //checking for aggregate functions
					//aggregate function
					function = field.substring(0, field.indexOf("("));
					//field inside the parenthesis
					aggregateField = matcher.group(1);
					AggregateFunction aggregateFunction = new AggregateFunction(aggregateField, function);
					aggregateFunctions.add(aggregateFunction);
				}
			}
		}
		return aggregateFunctions;
	}

}