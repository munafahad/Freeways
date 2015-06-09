package com.example.cassandra.simple_client;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class QueryVolume {

	public static void queryVolume(){
		
		int sum = 0;
		Session session = CassandraConnector.cluster.connect();
		ResultSet result = session.execute("select * from freeways.station_loopdata;");
	
		for (Row row : result)
		{
			 sum += row.getInt("volume");			
		}
		session.execute("INSERT INTO freeways.volume (locationtext, starttime, volume) "
				+ "VALUES (' "
				+ "FosterNB" +
				"', '" 
				+ "Sept 21,2011" +
				"', " 
				+ sum+
				");");
		System.out.println("\n\nQuery 2: Find the total volume for the station Foster NB for Sept 21,2011");
		System.out.println("\nTotal Volume"+"\n"+"--------------");
		System.out.println(sum+"\n\n");
	}
	
	
	
	
}
