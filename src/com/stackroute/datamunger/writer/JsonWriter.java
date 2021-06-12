package com.stackroute.datamunger.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWriter {
	/*
	 * this method will write the resultSet object into a JSON file. On successful
	 * writing, the method will return true, else will return false
	 */
	public boolean writeToJson(Map resultSet) {
		boolean isSuccessfullFileWriting = false;
		/*
		 * Gson is a third party library to convert Java object to JSON. We will use
		 * Gson to convert resultSet object to JSON
		 */
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String result = gson.toJson(resultSet);

		/*
		 * write JSON string to data/result.json file. As we are performing File IO,
		 * consider handling exception
		 * 
		 */
		//try-with-resources
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("data/result.json"))){
			bufferedWriter.write(result);
			/* return true if file writing is successful */
			isSuccessfullFileWriting = true;
		} catch (IOException e) {
			/* return false if file writing is failed */
			isSuccessfullFileWriting = false;
			e.printStackTrace();
		}
		return isSuccessfullFileWriting;
	}

}
