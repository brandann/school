/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extreme.hotel.viewer;

import Calendar.DateAD;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Zander
 */
public class RandomDBGenerator {
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException{
        ArrayList<String> firstNames = new ArrayList<String>();
        ArrayList<String> lastNames = new ArrayList<String>();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        File file = new File("randomNames.csv");
        File textFile = new File("Reservations.txt");
        FileReader reader = new FileReader(file);
        FileOutputStream fos = new FileOutputStream(textFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        BufferedReader br = new BufferedReader(reader);
        while(br.ready()){
            String[] line = br.readLine().split(",");
            firstNames.add(line[0]);
            lastNames.add(line[1]);
        }
        String name = "";
        DateAD arrival;
        DateAD departure;
        Reservation res;
        
            Random random = new Random();
        for(int i = 0; i < 5000; i++){
            
            name = firstNames.get((int)random.nextInt(firstNames.size() - 1)) + " " + 
                    lastNames.get((int)random.nextInt(lastNames.size() - 1));
            names.add(name);
        }
        for(int i = 0; i < 10000; i++){
            short month = (short)random.nextInt(12);
            short day = (short)random.nextInt(30);
            short year = (short)(random.nextInt(2) + 2013);
            DateAD date = new DateAD(day,month,year);
            DateAD dateDepart = date.getTomorrow().clone();
            for(int j = 0; j < random.nextInt(10); j++){
                dateDepart = dateDepart.getTomorrow();
            }
            
            res = new Reservation(names.get((int)random.nextInt(names.size() - 1)), 
                    date,dateDepart);
            reservations.add(res);
        }
        StringBuffer out = new StringBuffer();

        for(Reservation r : reservations){
            out.append(r.getName()).append("\r\n");
            out.append("Arrival: ").append(r.getArrivalDate()).append("\r\n");
            out.append("Departure: " + r.getDepartureDate() + "\r\n\r\n");
        }
        FileWriter fw = new FileWriter(textFile);
        fw.write(out.toString());
        fw.close();
//        oos.writeObject(reservations.toArray());
        
    }
}
