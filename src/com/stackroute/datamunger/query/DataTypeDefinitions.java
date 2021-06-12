package com.stackroute.datamunger.query;


/*
 * implementation of DataTypeDefinitions class. This class contains a method getDataTypes() 
 * which will contain the logic for getting the datatype for a given field value. This
 * method will be called from QueryProcessors.   
 * In this assignment, we are going to use Regular Expression to find the 
 * appropriate data type of a field. 
 * Integers: should contain only digits without decimal point 
 * Double: should contain digits as well as decimal point 
 * Date: Dates can be written in many formats in the CSV file. 
 * However, in this assignment,we will test for the following date formats('dd/mm/yyyy',
 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm-dd')
 */
public class DataTypeDefinitions {

	//method stub
	public static Object getDataType(String input) {
		
		String dataType;
		// checking for Integer
		if(input.matches("[+-]?[0-9]+")) {
			dataType = "java.lang.Integer";
		}// checking for floating point numbers
		else if(input.matches("[+-]?[0-9]+[.][0-9]+")) {
			dataType = "java.lang.Double";
		}// checking for string
		else if(input.matches("[a-zA-Z0-9\s]+")) {
			dataType = "java.lang.String";
		}// checking for date format dd/mm/yyyy
		else if(input.matches("([0-2][0-9]|(3)[0-1])[/]((0)[1-9]|(1)[1-2])[/]([0-9]{4})") ||
				// checking for date format mm/dd/yyyy
				input.matches("((0)[1-9]|(1)[1-2])[/]([0-2][0-9]|(3)[0-1])[/]([0-9]{4})") ||
				// checking for date format dd-mon-yy
				input.matches("([0-2][0-9]|(3)[0-1])[-]([a-z]{3})[-]([0-9]{2})") ||
				// checking for date format dd-month-yy
				input.matches("([0-2][0-9]|(3)[0-1])[-]([a-z]+)[-]([0-9]{2})") ||
				// checking for date format dd-month-yyyy
				input.matches("([0-2][0-9]|(3)[0-1])[-]([a-z]+)[-]([0-9]{4})") ||
				// checking for date format yyyy-mm-dd
				input.matches("([0-9]{4})[-]((0)[1-9]|(1)[1-2])[-]([0-2][0-9]|(3)[0-1])") ||
				// checking for date format dd-mon-yyyy
				input.matches("([0-2][0-9]|(3)[0-1])[-]([a-z]{3})[-]([0-9]{4})")) {
			dataType ="java.util.Date";
		} else {
			dataType ="java.lang.Object";
		}		
		return dataType;
	}
	

	
}
