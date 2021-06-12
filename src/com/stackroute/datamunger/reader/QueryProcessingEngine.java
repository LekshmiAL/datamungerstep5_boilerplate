package com.stackroute.datamunger.reader;



import java.io.IOException;

import com.stackroute.datamunger.query.DataSet;
import com.stackroute.datamunger.query.parser.QueryParameter;

public interface QueryProcessingEngine {

	public DataSet getResultSet(QueryParameter queryParameter) throws IOException;
	
}
