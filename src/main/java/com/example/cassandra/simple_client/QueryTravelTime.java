package com.example.cassandra.simple_client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;


public class QueryTravelTime {

        public static void DeleteTravelTime()
        {
                Session session = CassandraConnector.cluster.connect();
                session.execute("TRUNCATE freeways.traveltime;");
                System.out.println("\n\n Single-­Day Station Travel Times for station Foster NB for 5-­‐minute intervals for Sept 22, 2011 have been dropped!\n\n");
        }
        public static void querytravelTime() throws ParseException {
        	 Session session = CassandraConnector.cluster.connect();
        	 ResultSet result = session.execute("SELECT * FROM freeways.traveltime;");
        	 
        	 if (result.all().size() > 0)
        	 {
            	 ResultSet results = session.execute("SELECT starttime,traveltime FROM freeways.traveltime "
                         +"limit 15;");
            	 System.out.println("\n\nQuery 3: Single-­Day Station Travel Times for station Foster NB for 5-­‐minute intervals for Sept 22, 2011:");
                         System.out.println(String.format("%-30s\t%-20s\n%s", "\nstarttime",
                                         "traveltime",
                                         "-------------------------------+---------------------------"));
                                         for (Row row : results) {
                                         System.out.println(String.format("%-30s\t%-20s",
                                         row.getDate("starttime"),
                                         row.getString("traveltime")));
                                         }
                         System.out.println("\n\n");
                         
                      		 
        	 }
   
        	 else
        	 {
        	
        		int speed;
                int sum = 0;
                double length = 1.6;

                 String finalendtime = "2011-09-22 23:59:40";
                 SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                 java.util.Date utilDate1 = df1.parse(finalendtime);
                 utilDate1.getTime();
                 java.sql.Date finaltime = new java.sql.Date(utilDate1.getTime());

                int dec[] = new int[3];
                String loc = "Foster NB";
                //Session session = CassandraConnector.cluster.connect();
                ResultSet results1 = session.execute("SELECT detectorid FROM freeways.detectorid WHERE locationtext = '"+loc+"' allow filtering;");

                int i =0;
                for (Row row : results1) {
                         int val =row.getInt("detectorid");
                         dec[i] = val;
                         i++;   }

                //int cnt = 0;

                for( i = 0;i<3;i++)
                {
                        int detectorid = dec[i];
                        //System.out.println(detectorid);

                String starttime = "2011-09-22 00:00:00";

                String endtime;
                java.sql.Date sqld;

                do
                {
                        sum=0;

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date utilDate = df.parse(starttime);
                utilDate.getTime();
                java.sql.Date sqlStartDate = new java.sql.Date(utilDate.getTime());
                Calendar cal = Calendar.getInstance();
                cal.setTime(sqlStartDate);
                cal.add(Calendar.MINUTE, 5);
                endtime = df.format(cal.getTime());


                        ResultSet results = session.execute("SELECT * FROM freeways.station_loopdata WHERE detectorid ="+detectorid+" and starttime >= '" +starttime+ "' and starttime <= '"+endtime+"' allow filtering;");


                        for (Row row : results) {
                                 speed=row.getInt("speed");
                                 sum = sum + speed;
                                }

                        //cnt++;

                        float avgSpeed = sum/15;
                        double travelTime;
                        if (avgSpeed!= 0){
                        travelTime = ((length/avgSpeed)*3600);
                        }
                        else travelTime = 0;
                        //System.out.println(travelTime);

                        session.execute("INSERT INTO freeways.traveltime (starttime, traveltime) "
                                        + "VALUES ( '"
                                        + starttime +
                                        "', '"
                                        + travelTime+
                                        "');");

                        java.util.Date d = df.parse(endtime);
                        d.getTime();
                         sqld = new java.sql.Date(d.getTime());
                        Calendar cald = Calendar.getInstance();
                        cald.setTime(sqld);
                        cald.add(Calendar.SECOND, 20);
                        endtime = df.format(cald.getTime());
                        starttime = endtime;
                        }while (sqld.before(finaltime));
                }


                ResultSet results = session.execute("SELECT starttime,traveltime FROM freeways.traveltime "
                                +"limit 15;");
                System.out.println("\n\nQuery 3: Single-­Day Station Travel Times for station Foster NB for 5-­‐minute intervals for Sept 22, 2011:");
                                System.out.println(String.format("%-30s\t%-20s\n%s", "\nstarttime",
                                                "traveltime",
                                                "-------------------------------+---------------------------"));
                                                for (Row row : results) {
                                                System.out.println(String.format("%-30s\t%-20s",
                                                row.getDate("starttime"),
                                                row.getString("traveltime")));
                                                }
                                System.out.println("\n\n");

        }
        }
}
