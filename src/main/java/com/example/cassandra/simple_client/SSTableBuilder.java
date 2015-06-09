package com.example.cassandra.simple_client;

import static org.apache.cassandra.utils.ByteBufferUtil.bytes;

import java.io.*;


import org.apache.cassandra.db.marshal.AsciiType;
import org.apache.cassandra.dht.Murmur3Partitioner;


import org.apache.cassandra.io.sstable.SSTableSimpleUnsortedWriter;


public class SSTableBuilder {
       static String csvfilename = "firstdetector1.csv";

       public static void main(String[] args) {
             
              try {
                     buildSStables();
              } catch (Exception e) {
                     e.printStackTrace();
              }
       }
      
       public static void buildSStables() throws Exception {

              String keyspace = "Freeways";
              String table = "detectors";
              File directory = new File(keyspace + "/" + table);
             
              if (!directory.exists()) {
                    // directory.mkdirs();
           }
              long timestamp = System.currentTimeMillis() * 1000;
               SSTableSimpleUnsortedWriter bulkWriter = new SSTableSimpleUnsortedWriter(
                           directory, new Murmur3Partitioner(), keyspace, table,
                           AsciiType.instance, null, 64);
                

              BufferedReader reader = new BufferedReader(new FileReader(csvfilename));
             // CsvListReader csvReader = new CsvListReader(reader, CsvPreference.STANDARD_PREFERENCE));
              String line;
              int lineNumber = 1;
              CsvEntry entry = new CsvEntry();
             

              while ((line = reader.readLine()) != null) {

                     if (entry.parse(line, lineNumber)) {

                    	 bulkWriter.newRow(bytes(entry.detectorid));
                    	 
                         bulkWriter.addColumn(bytes("starttime"), bytes(entry.starttime),timestamp);                   
                         
                         bulkWriter.addColumn(bytes("speed"), bytes(entry.speed),timestamp);
                                           
                         bulkWriter.addColumn(bytes("indx"), bytes(entry.indx),timestamp);
                     }
                     lineNumber++;
              }

              reader.close();
              System.out.println("Success");
              bulkWriter.close();
              System.exit(0);
       }

       static class CsvEntry {   
    	   
    	   	String detectorid;
              String speed;
              String indx;
             String starttime;

              boolean parse(String line, int lineNumber) {
                    
                     String[] columns = line.split(",");
                     if (columns.length != 4) {
                           System.out.println(String.format(
                                         "Invalid inputs '%s' at line %d of %s", line,
                                         lineNumber, csvfilename));
                           return false;
                     }
                     try {
                           detectorid = columns[0].trim();
                           starttime = columns[1].trim();
                           speed = columns[2].trim();
                           indx = columns[3].trim();
                          
                           return true;
                     } catch (NumberFormatException e) {
                           System.out.println(String.format(
                                         "Invalid number in input '%s' at line %d of %s", line,
                                         lineNumber, csvfilename));
                           return false;
                     }
              }
       }

}
