package com.example.cassandra.simple_client;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class QueryHighSpeed {
		
	public static void querySpeed() 
	{
		long highspeed=0;
		Session session = CassandraConnector.cluster.connect();
		//query
		ResultSet results = session.execute("SELECT count(*) As highspeed FROM freeways.speed "
		+"WHERE speed > 100 allow filtering;");
		System.out.println("\n\nQuery 1: Number of Speeds >100 in the dataset\n");
		System.out.println(String.format("Highspeed Count"+"\n"+"--------------"));
		for (Row row : results) {
			highspeed=row.getLong("highspeed");
			System.out.println(highspeed + "\n\n");
		}
		//insert result to highspeed table
		session.execute("INSERT INTO freeways.highspeed (highspeed, count) "
				+ "VALUES (' "
				+ ">100" +
				"', " 
				+ highspeed+
				");");
				
		System.out.println();
	}
	
}

