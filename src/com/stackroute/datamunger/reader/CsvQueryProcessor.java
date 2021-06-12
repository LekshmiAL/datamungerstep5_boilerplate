package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.stackroute.datamunger.query.DataSet;
import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Filter;
import com.stackroute.datamunger.query.Header;
import com.stackroute.datamunger.query.Row;
import com.stackroute.datamunger.query.RowDataTypeDefinitions;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.Restriction;




public class CsvQueryProcessor implements QueryProcessingEngine {
	/*
	 * This method will take QueryParameter object as a parameter which contains the
	 * parsed query and will process and populate the ResultSet
	 */
	public DataSet getResultSet(QueryParameter queryParameter) throws IOException {
		int index;
		String dataTypeOfParam = "";
		List<String> logicalOptr = queryParameter.getLogicalOperators();
		/*
		 * initialize BufferedReader to read from the file which is mentioned in
		 * QueryParameter. Consider Handling Exception related to file reading.
		 */
		BufferedReader bufferedReader = new BufferedReader(new FileReader(queryParameter.getFileName()));
		
		/*
		 * read the first line which contains the header. Please note that the headers
		 * can contain spaces in between them. For eg: city, winner
		 */
		String headerLine = bufferedReader.readLine();
		String[] headerNames  = headerLine .split(",");
		
		/*
		 * read the next line which contains the first row of data. We are reading this
		 * line so that we can determine the data types of all the fields. Please note
		 * that ipl.csv file contains null value in the last column. If you do not
		 * consider this while splitting, this might cause exceptions later
		 */
		String line = bufferedReader.readLine();
		String[] rowArray = line.split(",");
		/*
		 * populate the header Map object from the header array. header map is having
		 * data type <String,Integer> to contain the header and it's index.
		 */
		HashMap<String, Integer> headerMap = new HashMap<>();
		int headerCount = 0;
		for(String headerCol : headerNames ) {
			headerMap.put(headerCol, headerCount++);
		}
		Header header = new Header(headerMap);
		
		/*
		 * We have read the first line of text already and kept it in an array. Now, we
		 * can populate the RowDataTypeDefinition Map object. RowDataTypeDefinition map is
		 * having data type <Integer,String> to contain the index of the field and it's
		 * data type. To find the dataType by the field value, we will use getDataType()
		 * method of DataTypeDefinitions class
		 */
		HashMap<Integer, String> rowDataTypeDefMap = new HashMap<>();
		String datatype ="";
		for(int col=0;col<headerCount;col++) {
			try {
					datatype = (String) DataTypeDefinitions.getDataType(rowArray[col]);
					rowDataTypeDefMap.put(col, datatype);
			}catch(ArrayIndexOutOfBoundsException ex) {
				rowDataTypeDefMap.put(col, "java.lang.Object");
			}
		}
		RowDataTypeDefinitions rowDataTypeDefinition = new RowDataTypeDefinitions(rowDataTypeDefMap);
		/*
		 * once we have the header and dataTypeDefinitions maps populated, we can start
		 * reading from the first line. We will read one line at a time, then check
		 * whether the field values satisfy the conditions mentioned in the query,if
		 * yes, then we will add it to the resultSet. Otherwise, we will continue to
		 * read the next line. We will continue this till we have read till the last
		 * line of the CSV file.
		 */
		List<Restriction> restrictions =queryParameter.getRestrictions();
		
		/* reset the buffered reader so that it can start reading from the first line */
		
		
		/*
		 * skip the first line as it is already read earlier which contained the header
		 */
		
		
		/* read one line at a time from the CSV file till we have any lines left */
		
		
		/*
		 * once we have read one line, we will split it into a String Array. This array
		 * will continue all the fields of the row. Please note that fields might
		 * contain spaces in between. Also, few fields might be empty.
		 */
		//DataSet dataSet = new data
		LinkedHashMap<Long, Row> dataSetMap = new LinkedHashMap<>();
		long dataSetMapKey = 1;
		HashMap<String, String> newRow = null;
		String[] nextRowArray= null;
		Row rowObject = null;
		boolean isRestrictionPassed = false;
		ArrayList<Boolean> isRestrictionSatisfiedList = null;
		Filter filter = new Filter();
		while(line != null) {
			//to add to row - columnname/column value
			newRow = new HashMap<>();
			nextRowArray  = line.split(",");
			//set the Row
			for(int col=0;col<headerCount;col++) {
				for(Map.Entry<String, Integer> headerCol: header.getHeaderMap().entrySet()) {
					if(col == headerCol.getValue()) {
						try {
							newRow.put(headerCol.getKey(), nextRowArray[col]);
						}catch(ArrayIndexOutOfBoundsException ex) {
							newRow.put(headerCol.getKey(), "");
						}
					}
				}
			}
			rowObject = new Row(newRow);
			isRestrictionPassed = false;
			isRestrictionSatisfiedList = new ArrayList<>();
			/*
			 * if there are where condition(s) in the query, test the row fields against
			 * those conditions to check whether the selected row satisfies the conditions
			 */
			if(restrictions != null && !restrictions.isEmpty()) {
				/*
				 * from QueryParameter object, read one condition at a time and evaluate the
				 * same. For evaluating the conditions, we will use evaluateExpressions() method
				 * of Filter class. Please note that evaluation of expression will be done
				 * differently based on the data type of the field. In case the query is having
				 * multiple conditions, you need to evaluate the overall expression i.e. if we
				 * have OR operator between two conditions, then the row will be selected if any
				 * of the condition is satisfied. However, in case of AND operator, the row will
				 * be selected only if both of them are satisfied.
				 */
				
				for(Restriction restriction : restrictions) {
					//get index from header map
					index = header.getHeaderMap().get(restriction.getPropertyName());
					//get datatype from rowDataTypeDefinition map
					dataTypeOfParam = rowDataTypeDefinition.getRowDataTypeDefinitionMap().get(index);
					isRestrictionSatisfiedList.add(filter.evaluateExpression(rowObject,restriction,dataTypeOfParam));
					
				}
			}else {
				isRestrictionPassed = true; //no restrictions
			}
			/*if the overall condition expression evaluates to true, then we 
			 * need to check if all columns are to be selected(select *) or few
			 * columns are to be selected(select col1,col2).
			 * In either of the cases, we will have to populate the row map object.
			 * Row Map object is having type <String,String> to contain field Index
			 * and field value for the selected fields.
			 * Once the row object is populated, add it to DataSet Map Object.
			 * DataSet Map object is having type <Long,Row> to hold the rowId
			 * (to be manually generated by incrementing a Long variable) and
			 * it's corresponding Row Object. */
			/*
			 * check for multiple conditions in where clause for eg: where salary>20000 and
			 * city=Bangalore for eg: where salary>20000 or city=Bangalore and dept!=Sales
			 */
			String optr ="";
			if(isRestrictionSatisfiedList.size() == 1) {
				isRestrictionPassed = isRestrictionSatisfiedList.get(0);
			}else if (isRestrictionSatisfiedList.size() > 1) {
				int andOrIndex;
				List<String> tempLogicalOptr = new ArrayList<String>();
				tempLogicalOptr.addAll(logicalOptr);
				while(tempLogicalOptr.contains("and")) {
					andOrIndex = tempLogicalOptr.indexOf("and");
					tempLogicalOptr.remove(andOrIndex);
					isRestrictionPassed = (isRestrictionSatisfiedList.get(andOrIndex) && isRestrictionSatisfiedList.get(andOrIndex+1));
					isRestrictionSatisfiedList.remove(andOrIndex);
					isRestrictionSatisfiedList.set(andOrIndex, isRestrictionPassed);
				}
				while(tempLogicalOptr.contains("or")) {
					andOrIndex = tempLogicalOptr.indexOf("or");
					tempLogicalOptr.remove(andOrIndex);
					isRestrictionPassed = (isRestrictionSatisfiedList.get(andOrIndex) || isRestrictionSatisfiedList.get(andOrIndex+1));
					isRestrictionSatisfiedList.remove(andOrIndex);
					isRestrictionSatisfiedList.set(andOrIndex, isRestrictionPassed);
				}
			}
			
			if(isRestrictionPassed == true) {
				if(queryParameter.getFields().contains("*")) {
					dataSetMap.put(dataSetMapKey++, rowObject);
				}else {
					//add only the required field
					HashMap<String, String> selectedFieldsRowMap = new HashMap<>();
					for (Map.Entry<String, String> column : rowObject.getRowMap().entrySet()) {
					    if(queryParameter.getFields().contains(column.getKey())){
					    	selectedFieldsRowMap.put(column.getKey(), column.getValue());
					    }
					}
					dataSetMap.put(dataSetMapKey++, new Row(selectedFieldsRowMap));
				}
				
			}
			line = bufferedReader.readLine();//next row
		}
				
		
		DataSet finalDataSet = new DataSet(dataSetMap);
		
		/*return dataset object*/
		return finalDataSet;
	}
	
	
	
	
	
}
