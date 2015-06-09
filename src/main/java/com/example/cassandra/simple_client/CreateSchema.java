package com.example.cassandra.simple_client;

import com.datastax.driver.core.Session;

public class CreateSchema {

	public static void createSchema()
	{
		Session session = CassandraConnector.cluster.connect();
		session.execute("CREATE KEYSPACE IF NOT EXISTS freeways WITH replication " +
				"= {'class':'SimpleStrategy', 'replication_factor':2};");	
		session.execute("USE freeways");
		
		session.execute(
				"CREATE TABLE IF NOT EXISTS freeways.station_loopdata (" +
				"locationtext text ," +"detectorid int,"+"starttime timestamp,"+"volume int,"+"length float,"+
				"speed int," +
				"PRIMARY KEY (locationtext,detectorid,starttime)"+
				");");
		
		session.execute(
				"CREATE TABLE IF NOT EXISTS freeways.speed (" +
				"key uuid ," +
				"speed int," +
				"PRIMARY KEY (key,speed)"+
				");");
		
		session.execute(
				"CREATE TABLE IF NOT EXISTS freeways.detectorid (" +
				"detectorid int," +
				"locationtext text," +
				"PRIMARY KEY (locationtext,detectorid)"+
				");");
		session.execute(
				"CREATE TABLE IF NOT EXISTS freeways.traveltime (" +
				"starttime timestamp ," +
				"traveltime text," +
				"PRIMARY KEY (starttime,traveltime)"+
				");");
		session.execute(
                "CREATE TABLE IF NOT EXISTS freeways.highspeed (" +
                "Highspeed text," +
                "Count bigint," +
                "PRIMARY KEY (Highspeed)"+
                ");");
		session.execute(
                "CREATE TABLE IF NOT EXISTS freeways.volume (" +
                "locationtext text ," +"starttime text,"+"volume int," +
                "PRIMARY KEY (locationtext,starttime)"+
                ");");
		
		session.execute(
				"CREATE TABLE IF NOT EXISTS freeways.stations (" +
				"highwayname text ," +"direction text,"+"locationtext text,"+"downstream int,"+
				"stationid int," +
				"PRIMARY KEY ((highwayname),direction,locationtext)"+
				");");
		session.execute(
				"CREATE TABLE IF NOT EXISTS freeways.route (" +
				"highwayname text ," +"direction text,"+"startlocation text,"+"endlocation text,"+
				"route list<int>," +
				"PRIMARY KEY ((highwayname),direction,startlocation,endlocation)"+
				");");
		
		session.execute("CREATE INDEX IF NOT EXISTS stationid_index on freeways.stations(stationid);");
		
	}
	
}
