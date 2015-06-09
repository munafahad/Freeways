# Freeways
# Freeways

CS510: Cloud and Cluster Data Management
-------------------------------

Project Goal and Description:
-------------------------------
  The goal of this project is to design and implement a small Freeways system using data from "Multimodal Test Data Set Clean-up for Portland Oregon Metropolitan Region", and using NoSQL database. We used Cassandra NoSQL database in this project. In this project, we designed and implemented six queries that help in performing different operations on the Freeways' systems. 
  The implemented queries are:
    
    - Count high speeds: By founding the number of speeds > 100 in the data set
    - Find the total volume for the station Foster NB for Sept 21, 2011
    - Single-Day Station Travel Times: Find travel time for station Foster NB for 5-­‐minute intervals for Sept 22, 2011. Report travel time in seconds
    - Route Finding: Find a route from Johnson Creek to Columbia Blvd on I-205 NB using the upstream and downstream fields
  
  This project is a part of "CS410/510-Cloud and Cluster Data Management" in Portland State University (PSU).


Needed Software:
-------------------------------
 - Cassandra 
 - Java 6, 7 or 8
 - Eclipse


Using the Application:
-------------------------------
  * To run the application using Eclipse:
    1. Setup Cassandra by following the instructions in this URL: http://wiki.apache.org/cassandra/GettingStarted
    2. Create a keyspace and column families using CreateSchema.java file
    3. Upload the data into the tables using SSTableBuilder.java or insert statements. (The following files contains the data: detectorid.csv, firstdetector1.csv, firstdetector2.csv, speed.csv and station_loopdata.csv)
    4. Download the GitHub project and import it in eclips. (Main files are: CassandraConnector.java, CreateSchema.java, FindRoute.java, QueryHighSpeed.java, QueryTravelTime.java, QueryVolume.java and SSTableBuilder.java)
    5. Add the needed libraries, which are: 
      - junit-3.8.1.jar
      - com.datastax.cassandra-2.0.1.jar
      - net.sf.supercsv-2.1.0.jar
      - org.apache.cassandra-2.1.3.jar
      - org.apache.thrift-0.9.2.jar
      - org.apache-extras.cassandra-jdbc-1.2.1.jar
      - org.apache.cassandra-2.1.5.jar
      - org.apache.cassandra-2.1.5.jar
      - org.slf4j-1.7.12.jar
      - org.slf4j-1.7.12.jar
      - log4j-1.2.17.jar
      - org.antlr-3.2.jar
    
  * To run the application using the .jar file (without using Eclipse)
    1. Download Freeways.jar
    2. Run the .jar file using this command in Terminal: java -jar Freeways.jar



This system is designed and developed by:
-----------------------------------------
  - Kamakshi Nagar
  - Muna Alfahad
  - Renu Thottan
  - Srividya Sundaram
  
  

Acknowledgments:
-----------------
We would like to thank Professor Kristin Tufte and Professor David Maier for their help and guidance in the Cloud and Cluster Data Management course and project.

