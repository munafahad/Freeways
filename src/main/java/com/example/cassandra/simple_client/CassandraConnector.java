package com.example.cassandra.simple_client;

import java.text.ParseException;
import java.util.Scanner;

import com.datastax.driver.core.*;


public class CassandraConnector {

        public static Cluster cluster;
        public static Session session;
        public static Scanner scanner;

        public void connect(String node)
        {
                cluster= Cluster.builder().addContactPoint(node).withPort(9042).build();

                Metadata metadata = cluster.getMetadata();
                System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());

                for ( Host host : metadata.getAllHosts() )
                {
                System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
                host.getDatacenter(), host.getAddress(), host.getRack());
                }
                session = cluster.connect();
        }

         public static void close() {
                session.close();
                cluster.close();
                System.out.println("Conncetion Closed..");
                }

        public static void main(String[] argrs) throws Exception
        {
                CassandraConnector client = new CassandraConnector();
                client.connect("52.26.29.30");
                CreateSchema.createSchema();
                scanner = new Scanner(System.in);
                int choice = 0;

                while (choice != 7) {
            System.out.println("MAIN MENU");
            System.out.println("1-Count high speed");
            System.out.println("2-Find total volume");
            System.out.println("3-Find single-day station travel times");
            System.out.println("4-Drop single-day station travel times");
            System.out.println("5-Find route");
            System.out.println("6-Drop route");
            System.out.println("7-Exit");
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();


            switch(choice) {
            case 1: ;
                        QueryHighSpeed.querySpeed();
                        break;
            case 2: QueryVolume.queryVolume();
                        break;
            case 3: try {
                                QueryTravelTime.querytravelTime();
                                }
                        catch (ParseException e) {
                                e.printStackTrace();
                                }
                        break;
            case 4: QueryTravelTime.DeleteTravelTime();
                        break;
            case 5: FindRoute.findRoute("I-205", "NORTH", "Johnson Cr NB", "Columbia to I-205 NB");
                break;
            case 6: FindRoute.dropRoute("I-205", "NORTH", "Johnson Cr NB", "Columbia to I-205 NB");
                break;
            case 7: close();
                        break;
            default: System.out.println("Invalid choice");
                        break;
            }


       }
        }
}
