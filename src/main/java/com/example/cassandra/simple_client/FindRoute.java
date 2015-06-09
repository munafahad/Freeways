package com.example.cassandra.simple_client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;


public class FindRoute {
	
	public static void dropRoute(String highwayName, String direction, String startLoc, String endLoc) throws Exception
	{
		Connection con = null;
		 try {
			 
			  Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			   con = DriverManager.getConnection("jdbc:cassandra://52.26.29.30/freeways");
			   Statement stm = con.createStatement();
		String drop_q = "DELETE FROM freeways.route WHERE highwayname = '"+highwayName+"' and direction = '"+direction+"' and startlocation = '"+startLoc+"' and endlocation = '"+endLoc+"';";
		stm.executeUpdate(drop_q);
		System.out.println("\n\nThe route from Johnson Creek to Columbia Blvd on I-205 NB has been dropped!" + "\n\n");
		
		}
	    catch (SQLException e) {
		   e.printStackTrace();
		  } 
		 finally
		 {
			 con.close();
		 }
	}
	
	public static void findRoute(String highwayName, String direction, String startLoc, String endLoc) throws Exception
	{
		Connection con = null;
		 try {
			 
			   Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			   con = DriverManager.getConnection("jdbc:cassandra://52.26.29.30/freeways");
			   Statement stm = con.createStatement();
		   
		   stm.execute(
		   "CREATE TABLE IF NOT EXISTS freeways.route (" +
		   "highwayname text," +
		   "direction text," +
		   "startlocation text," +
		   "endlocation text," +
		   "route list<int>," +
		   "PRIMARY KEY (highwayname, direction, startlocation, endlocation)" +
		   ");");
		   
		   
		   //to check if there is a route for the same route int the route table
		   String route_q = "select route from route where highwayname = '"+highwayName+"' and direction = '"+direction+"' and startlocation = '"+startLoc+"' and endlocation = '"+endLoc+"';";
		   ResultSet r = stm.executeQuery(route_q);
		   
		  if (r.next())
		   {
			  System.out.println("\n\nQuery 4: Route from Johnson Creek to Columbia Blvd on I-205 NB\n");
			  System.out.println(r.getString(1) + "\n\n");  
			   
		   }
		   
		   else
		   {
		   
		   //to get the stationid of the source
		   String source_q = "select stationid from stations where highwayname = '"+highwayName+"' and direction = '"+direction+"' and locationtext = '"+startLoc+"';";
		   ResultSet source = stm.executeQuery(source_q);
		   
		   
		   //to get the stationid of the destination
		   String dest_q = "select stationid from stations where highwayname = '"+highwayName+"' and direction = '"+direction+"' and locationtext = '"+endLoc+"';";
		   ResultSet dest = stm.executeQuery(dest_q);

		   //Insert a record
		   if (source.next() && dest.next())
		   {
		   stm.execute("INSERT INTO freeways.route (highwayname, direction, startlocation, endlocation, route)"
		   		+ "VALUES ('"+ highwayName +"','"+direction+"','"+ startLoc+"','"+endLoc+"',["+source.getInt(1)+"]);");
		   }
		   
		   
		   //downstream
		   String downStrm_q = "select downstream from stations where  stationid ="+source.getInt(1)+";";
		   ResultSet downStrm = stm.executeQuery(downStrm_q);

		   while (downStrm.next() && downStrm != dest) {
				
			    //next stationid
				String station_q = "select stationid from stations where  stationid = "+downStrm.getInt(1)+";";        
				ResultSet station =stm.executeQuery(station_q);
				
				
				 
				while(station.next())
				{

					//update
					stm.execute("UPDATE freeways.route SET route = route + ["+station.getInt(1)+"]"+
							"WHERE highwayname ='"+highwayName+"'and direction ='"+direction+"'and startlocation ='"+startLoc+"'and endlocation='"+endLoc+"';");
					
					
					
					//next downstream
					String downStrm_q2 = "select downstream from stations where  stationid = "+station.getInt(1)+";";        
					downStrm =stm.executeQuery(downStrm_q2);
				}
				
			}

		  	
		   //the route
		    String Finalroute = "select route FROM freeways.route WHERE highwayname ='"+highwayName+"'and direction ='"+direction+"'and startlocation ='"+startLoc+"'and endlocation='"+endLoc+"';";
			ResultSet rt =stm.executeQuery(Finalroute);
			
			System.out.println("\n\nQuery 4: Route from Johnson Creek to Columbia Blvd on I-205 NB\n");
		    System.out.println(rt.getString(1) + "\n\n");
		    
		   } 
		   
		 
		   } catch (SQLException e) {
		   e.printStackTrace();
		  } 
		 
		 finally
		 {
			 con.close();
		 }
		  
	}
	
	   
	   	
	   
	public static void main(String[] args) throws Exception{

	  }
	 }
