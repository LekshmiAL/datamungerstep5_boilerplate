package com.stackroute.datamunger.main;

import java.util.HashMap;
import java.util.Scanner;

import com.stackroute.datamunger.query.Query;
import com.stackroute.datamunger.writer.JsonWriter;

public class DataMungerStep5 {

	public static void main(String[] args) {
		
		Scanner scannerObj = new Scanner(System.in);
		System.out.println("Enter Query :\n");
		String readQuery = scannerObj.nextLine();
		Query queryExecution = new Query();
		HashMap resultSet = queryExecution.executeQuery(readQuery);
		JsonWriter jsonWriter = new JsonWriter();
		Boolean isWrriten = jsonWriter.writeToJson(resultSet);
		if(isWrriten) {
				System.out.println("DataSet written to json file");
		}else {
			System.out.println("Failed to write DateSet to json file");
		}
		
	}

}
