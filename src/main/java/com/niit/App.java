package com.niit;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;


/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String fromCity,destination;
        int count = 0, row = 0;
        String lineno;
        System.out.format("%30s %30s %30s ", "From", "To", "Distance in Km");
        System.out.format("%30s %30s", "Travel time", "Typical AirFare\n\n");
        String file = "src/main/resources/routes.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.mark(100000);
            lineno = br.readLine();
            while (lineno != null) {
                count++;
                lineno = br.readLine();
            }
            br.reset();
            String[] routes = new String[count];
            while ((lineno = br.readLine()) != null) {
                routes[row] = lineno;
                String words[] = routes[row].split(", ");
                for (int i = 0; i < words.length; i++) {
                    System.out.format("%30s", words[i]);
                }
                System.out.println();
                if (row < count) {
                    row++;
                }
            }
            System.out.println("\n\nEnter the source place to display flight details to different places");
            fromCity = sc.nextLine();
            String city[]=showDirectFlights(routes, fromCity);
            if(city.length>0){
                System.out.format("%30s %30s %30s ", "From", "To", "Distance in Km");
                System.out.format("%30s %30s", "Travel time", "Typical AirFare\n\n");}
            for (int k=0;k<city.length;k++){
                 String words[] =city[k].split(", ");
                 for(int j=0;j< words.length;j++){
                     System.out.format("%30s", words[j]);
                 }
                System.out.println();
            }
            if (city.length==0) {
                System.out.println("We are sorry.At this point of time, we do not have any information on flights originationg from " + fromCity);
            }
            sortDirectFlights(city);
            System.out.println("Enter the destination place");
            destination= sc.nextLine();
            showIndirectFlights(routes,fromCity,destination);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] showDirectFlights(String[] routeinfo, String fromCity) {
        String[] words;
        int city_index=0, size = 0;
        for (int k = 0; k < routeinfo.length; k++) {
            words = routeinfo[k].split(", ");
            if (words[0].equalsIgnoreCase(fromCity)) {
                size++;
            }
        }
        String[] city = new String[size];
        for (int k = 0; k < routeinfo.length; k++) {
            words = routeinfo[k].split(", ");
            if (words[0].equalsIgnoreCase(fromCity)) {
                System.out.println();
                city[city_index] = routeinfo[k];
                city_index++;
            }
        }
        return city;
    }
    public static void sortDirectFlights(String[] city) {
        System.out.println("In Sorted order");
        int count=0,size=0;
        String[] destination=new String[city.length];
        String line[];
        String sortedcities[]=new String[city.length];
        for(int k=0;k<city.length;k++){
            line=city[k].split(", ");
            destination[count]=line[1];
            count++;
        }
        Arrays.sort(destination);
        for(int p=0;p<city.length;p++){
        for(int k=0;k<city.length;k++){
            line=city[k].split(", ");
            if(line[1].equalsIgnoreCase(destination[size])){
                sortedcities[size] =city[k];
                size++;
                break;
            }
        }}
        System.out.format("%30s %30s %30s ", "From", "To", "Distance in Km");
        System.out.format("%30s %30s", "Travel time", "Typical AirFare\n");

        for(int k=0;k<sortedcities.length;k++){
            line=sortedcities[k].split(", ");
            for (int i = 0; i < line.length; i++) {
                System.out.format("%30s", line[i]);
            }
            System.out.println();
        }
    }
    public static void showIndirectFlights(String[] routeinfo, String fromCity,String destination){
        String flights[]=showDirectFlights(routeinfo,fromCity);
        System.out.println("Direct flights");
        System.out.println(destination);
        System.out.format("%30s %30s %30s ", "From", "To", "Distance in Km");
        System.out.format("%30s %30s", "Travel time", "Typical AirFare\n\n");
        for(int b=0;b<flights.length;b++){
            String[] findingDirectFlights=flights[b].split(", ");
            if(findingDirectFlights[1].equalsIgnoreCase(destination)) {
                for(String k:findingDirectFlights)
                    System.out.format("%30s",k);
            }
        }
        System.out.println();
        System.out.println("Indirect flights");
        for(int c=0;c< flights.length;c++){
            String[] indirectFlight=flights[c].split(", ");
            if(!(indirectFlight[1].equalsIgnoreCase(destination))){
                String middleFlight[]=showDirectFlights(routeinfo,indirectFlight[1]);
                for(int d=0;d<middleFlight.length;d++){
                    String[] words=middleFlight[d].split(", ");
                    if(words[1].equalsIgnoreCase(destination)){
                        for(String k:indirectFlight)
                            System.out.format("%30s",k);
                        System.out.println();
                        for(String j:words)
                            System.out.format("%30s",j);
                        System.out.println();
                        break;
                    }
                }
            }
        }


    }
}

