 package com.stackroute.datamunger.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import com.stackroute.datamunger.query.parser.Restriction;

//this class contains methods to evaluate expressions
public class Filter {
	final static String EQUAL_OPERATOR = "=";
	final static String NOT_EQUAL_OPERATOR = "!=";
	final static String GREATER_THAN_EQUAL = ">=";
	final static String LESS_THAN_EQUAL = "<=";
	final static String GREATER_THAN = ">";
	final static String LESS_THAN = "<";
	final static String STRING_DATATYPE = "java.lang.String";
	final static String INTEGER_DATATYPE = "java.lang.Integer";
	final static String DOUBLE_DATATYPE = "java.lang.Double";
	final static String DATE_DATATYPE = "java.util.Date";
	
	SimpleDateFormat fileDateFormat = new SimpleDateFormat("MM/dd/yyy");
	SimpleDateFormat queryDateFormat= new SimpleDateFormat("dd-MM-YYYY");
	Date fileDate = null;
	Date queryDate = null;
	/* 
	 * the evaluateExpression() method of this class is responsible for evaluating 
	 * the expressions mentioned in the query. It has to be noted that the process 
	 * of evaluating expressions will be different for different data types. there 
	 * are 6 operators that can exist within a query i.e. >=,<=,<,>,!=,= This method 
	 * should be able to evaluate all of them. 
	 * Note: while evaluating string expressions, please handle uppercase and lowercase 
	 * 
	 */
	
	public boolean evaluateExpression(Row rowObject, Restriction restriction, String datatype) {
		boolean isConditionPassed = false;
		String condition = restriction.getCondition();
		String propertyValue = restriction.getPropertyValue();
		String rowValue = rowObject.getRowMap().get(restriction.getPropertyName());
		try {
			if(EQUAL_OPERATOR.equals(condition)){
				isConditionPassed = isEqualTo(rowValue,propertyValue,datatype);
			}else if(NOT_EQUAL_OPERATOR.equals(condition)) {
				isConditionPassed = isNotEqualTo(rowValue,propertyValue,datatype);
			}else if(GREATER_THAN_EQUAL.equals(condition)) {
				isConditionPassed = isGreaterThanOrEqualTo(rowValue,propertyValue,datatype);
			}else if(LESS_THAN_EQUAL.equals(condition)) {
				isConditionPassed = isLessThanOrEqualTo(rowValue,propertyValue,datatype);
			}else if(GREATER_THAN.equals(condition)) {
				isConditionPassed = isGreaterThan(rowValue,propertyValue,datatype);
			}else if(LESS_THAN.equals(condition)) {
				isConditionPassed = isLessThan(rowValue,propertyValue,datatype);
			}
		}catch (ParseException parseException) {
			parseException.printStackTrace();
		}
		return isConditionPassed;
	}
	
	
	//method containing implementation of equalTo operator
	public boolean isEqualTo(String rowValue, String propertyValue, String datatype) throws ParseException {
		Boolean isEqual = false;
		if(datatype.equals(STRING_DATATYPE)) {
			isEqual = rowValue.equalsIgnoreCase(propertyValue);
		}else if(datatype.equals(INTEGER_DATATYPE) || datatype.equals(DOUBLE_DATATYPE)) {
			isEqual = (Integer.valueOf(rowValue) == Integer.valueOf(propertyValue)) ;
		}else if(datatype.equals(DATE_DATATYPE)) {
			// Get the two dates to be compared
	        fileDate = fileDateFormat.parse(rowValue);
	        queryDate = queryDateFormat.parse(propertyValue);
	        if (fileDate.compareTo(queryDate) == 0) {
	        	isEqual = true;
	        }
		}
		return isEqual;
		
	}
	
	//method containing implementation of notEqualTo operator
	public Boolean isNotEqualTo(String rowValue, String propertyValue, String datatype) throws ParseException {
		Boolean isNotEqual = false;
		if(datatype.equals(STRING_DATATYPE)) {
			isNotEqual  = !rowValue.equalsIgnoreCase(propertyValue);
		}else if(datatype.equals(INTEGER_DATATYPE) || datatype.equals(DOUBLE_DATATYPE)) {
			isNotEqual = (Integer.valueOf(rowValue) != Integer.valueOf(propertyValue)) ;
		}else if(datatype.equals(DATE_DATATYPE)) {
			// Get the two dates to be compared
	        fileDate = fileDateFormat.parse(rowValue);
	        queryDate = queryDateFormat.parse(propertyValue);
	        if (fileDate.compareTo(queryDate) != 0) {
	        	isNotEqual = true;
	        }
		}
		return isNotEqual;
	}
		
	//method containing implementation of greaterThan operator
	public Boolean isGreaterThan(String rowValue, String propertyValue, String datatype) throws ParseException {
		Boolean isGreaterThan = false;
		if(datatype.equals(STRING_DATATYPE)) {
			if(rowValue.compareTo(propertyValue) > 0) {
				isGreaterThan =  true;
			}
		}else if(datatype.equals(INTEGER_DATATYPE) || datatype.equals(DOUBLE_DATATYPE)) {
			isGreaterThan = (Integer.valueOf(rowValue) > Integer.valueOf(propertyValue));
		}else if(datatype.equals(DATE_DATATYPE)) {
			// Get the two dates to be compared
	        fileDate = fileDateFormat.parse(rowValue);
	        queryDate = queryDateFormat.parse(propertyValue);
	        if (fileDate.compareTo(queryDate) > 0) {
	        	isGreaterThan = true;
	        }
		}
		return isGreaterThan;
	}
		
	//method containing implementation of greaterThanOrEqualTo operator
	public boolean isGreaterThanOrEqualTo(String rowValue, String propertyValue, String datatype) throws ParseException {
		boolean isGreaterThanEqual = false;
		
		if(datatype.equals(STRING_DATATYPE)) {
			if(rowValue.compareTo(propertyValue) >= 0) {
				isGreaterThanEqual = true;
			}
		}else if(datatype.equals(INTEGER_DATATYPE) || datatype.equals(DOUBLE_DATATYPE)) {
			isGreaterThanEqual = (Integer.valueOf(rowValue) >= Integer.valueOf(propertyValue));
		}else if(datatype.equals(DATE_DATATYPE)) {
			// Get the two dates to be compared
	        fileDate = fileDateFormat.parse(rowValue);
	        queryDate = queryDateFormat.parse(propertyValue);
	        if (fileDate.compareTo(queryDate) >= 0) {
	        	isGreaterThanEqual = true;
	        }
		}
		return isGreaterThanEqual;
	}
	
	//method containing implementation of lessThan operator
	public boolean isLessThan(String rowValue, String propertyValue, String datatype) throws ParseException {
		boolean isLessThan = false;
		if(datatype.equals(STRING_DATATYPE)) {
			if(rowValue.compareTo(propertyValue) < 0 ) {
				isLessThan = true;
			}
		}else if(datatype.equals(INTEGER_DATATYPE) || datatype.equals(DOUBLE_DATATYPE)) {
			isLessThan = (Integer.valueOf(rowValue) < Integer.valueOf(propertyValue));
		}else if(datatype.equals(DATE_DATATYPE)) {
			// Get the two dates to be compared
	        fileDate = fileDateFormat.parse(rowValue);
	        queryDate = queryDateFormat.parse(propertyValue);
	        if (fileDate.compareTo(queryDate) < 0) {
	        	isLessThan = true;
	        }
		}
		return isLessThan;
	}	
	
	//method containing implementation of lessThanOrEqualTo operator
	public boolean isLessThanOrEqualTo(String rowValue, String propertyValue, String datatype) throws ParseException {
		boolean isLessThanEqual =false;
		if(datatype.equals(STRING_DATATYPE)) {
			if(rowValue.compareTo(propertyValue) <= 0 ) {
				isLessThanEqual = true;
			}
		}else if(datatype.equals(INTEGER_DATATYPE) || datatype.equals(DOUBLE_DATATYPE)) {
			isLessThanEqual = (Integer.valueOf(rowValue) <= Integer.valueOf(propertyValue));
		}else if(datatype.equals(DATE_DATATYPE)) {
			// Get the two dates to be compared
	        fileDate = fileDateFormat.parse(rowValue);
	        queryDate = queryDateFormat.parse(propertyValue);
	        if (fileDate.compareTo(queryDate) <= 0) {
	        	isLessThanEqual = true;
	        }
		}
		return isLessThanEqual;
	}
}
