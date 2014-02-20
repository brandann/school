/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extreme.hotel.viewer;

import java.io.*;

/**
 * This class simply allows for the loading of an array of Reservations saved
 * as a binary file.
 * @author Alex Yelle, Alex ??
 */
public class ExtremeDB {
    private static final String DEFAULT_DB_PATH = "Reservations.dat";
    protected Reservation[] reservations = null;

    /**
     * Class: ExtremeDB
     * File: ExtremeDB.java
     * Description: Default constructor. This class allows for the access and
     * modification of a ExtremeBooking DB file. The default db "Bookings.dat",
     * located at the root of the application, will attempted to be loaded.
     */
    public ExtremeDB() {
        File file = new File(DEFAULT_DB_PATH);
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            reservations = (Reservation[])ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            System.out.println(e);  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    /**
     * Class: ExtremeDB
     * File: ExtremeDB.java
     * Description: This constructor will set the ExtremeBooking DB file to
     * the path specified in the parameter.
     */
    public ExtremeDB(String dbPath) {
        try{
            FileInputStream fis = new FileInputStream(new File(dbPath));
            ObjectInputStream ois = new ObjectInputStream(fis);
            reservations = (Reservation[])ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            System.err.println(e.toString());  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    /**
     * Returns all of the reservations within the database.
     *
     * @return an array of all the reservations within the database.
     */
    public Reservation[] getReservations() {
        return this.reservations;
    }
}
