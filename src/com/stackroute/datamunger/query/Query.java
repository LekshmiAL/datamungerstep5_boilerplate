package com.stackroute.datamunger.query;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.QueryParser;
import com.stackroute.datamunger.reader.CsvQueryProcessor;


public class Query {

	/*
	 * This method will: 
	 * 1.parse the query and populate the QueryParameter object
	 * 2.Based on the type of query, it will select the appropriate Query processor.
	 * 
	 * In this example, we are going to work with only one Query Processor which is
	 * CsvQueryProcessor, which can work with select queries containing zero, one or
	 * multiple conditions
	 */
	public HashMap executeQuery(String queryString) {
		
		HashMap resultDataSet = new LinkedHashMap<>();
		/* instantiate QueryParser class */
		QueryParser queryParser = new QueryParser();
		CsvQueryProcessor queryProcesser = new CsvQueryProcessor();
		
		/*
		 * call parseQuery() method of the class by passing the queryString which will
		 * return object of QueryParameter
		 */
		QueryParameter queryParmeter = queryParser.parseQuery(queryString);
		
		/*
		 * Check for Type of Query based on the QueryParameter object. In this
		 * assignment, we will process only queries containing zero, one or multiple
		 * where conditions i.e. conditions without aggregate functions, order by clause
		 * or group by clause
		 */
		
		int a=0;
		Collection<String> types =  Arrays.asList(queryParmeter.getQUERY_TYPE().split(","));
		Collection<String> checkTypes = Arrays.asList("aggregateFunctions", "groupBy", "orderBy");
		if(Collections.disjoint(types, checkTypes)) { 
			//query doesn't contains aggregate functions, order by clause
			//or group by clause

			/*
			 * call the getResultSet() method of CsvQueryProcessor class by passing the
			 * QueryParameter Object to it. This method is supposed to return resultSet
			 * which is a HashMap
			 */
			try {
				LinkedHashMap<Long, Row> dataSet = queryProcesser.getResultSet(queryParmeter).getDataSetMap();
				for(Map.Entry<Long, Row> itr: dataSet.entrySet()) {
					resultDataSet.put(itr.getKey(), itr.getValue().getRowMap());
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return resultDataSet;
	}

}
