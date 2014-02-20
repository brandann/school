/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extreme.hotel.viewer;

import Calendar.DateAD;
import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * This class represents a Reservation at the Extreme Hotel
 * @author
 * @version 1.0
 */
public class Reservation implements Serializable, Comparable {
    /**
     * Default constructor. Does not take an parameters.
     */
    public Reservation(){
        
    }
    /**
     * Creates an instance using the parameters defined.
     * @param myName the name of the customer
     * @param myArrival the arrival date of the customer
     * @param myDeparture the departure date of the customer
     */
    public Reservation(String myName, DateAD myArrival, DateAD myDeparture) {
        name = myName;
        arrivalDate = myArrival.clone();
        departureDate = myDeparture.clone();
    }

    /**
     * Returns the name of the customer
     * @return the name of the customer
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the arrival date of the customer
     * @return the arrival date of the customer
     */
    public DateAD getArrivalDate() {
        return arrivalDate;
    }
    /**
     * Returns the departure date of the customer
     * @return the departure date of the customer
     */
    public DateAD getDepartureDate() {
        return departureDate;
    }

    /**
     * Returns a string representation of this object.
     * @return the string representation
     */
    @Override
    public String toString() {
        return name + "\n"
                + arrivalDate.toString() + "\n"
                + departureDate.toString() + "\n";
    }

    /**
     * Performs a check on this object with the object passed.
     * @param reservationIn the object to be checked
     * @return true if the two objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object reservationIn) {
        Reservation compReservationIn = (Reservation) reservationIn;
        if (name.equals(compReservationIn.getName())) {
            if (arrivalDate.equals(compReservationIn.getArrivalDate())) {
                if (departureDate.equals(compReservationIn.getDepartureDate())) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Compares the two objects.
     * @param reservationIn the object to check
     * @return integer less than zero if less than, greater than 0 if greater
     * than and 0 if equal.
     * @throws java.lang.ClassCastException 
     */
    @Override
    public int compareTo(Object reservationIn)
            throws java.lang.ClassCastException {
        Reservation compReservationIn = (Reservation) reservationIn;

        //Quick check to see if equal:
        int arrivalCompare = this.getArrivalDate().compareTo(compReservationIn
                .getArrivalDate());
        int departureCompare = this.getDepartureDate()
                .compareTo(compReservationIn.getDepartureDate());
        int nameCompare = this.getName().compareTo(compReservationIn.getName());
        //we know that it isnt equal, so what is it??
        if (nameCompare != 0) {
            return nameCompare;
        } else if (arrivalCompare != 0) {
            return arrivalCompare;
        } else {
            return departureCompare;
        }
    }
        /**
     * Compares the two objects. Checks arrival date, departure date and name in
     * order
     * @param reservationIn the object to check
     * @return integer less than zero if less than, greater than 0 if greater
     * than and 0 if equal.
     * @throws java.lang.ClassCastException 
     */
    public int compareToByArrivalDate(Object reservationIn) throws ClassCastException {
        Reservation compReservationIn = (Reservation) reservationIn;
        //Quick check to see if equal:
        int arrivalCompare = this.getArrivalDate().compareTo(compReservationIn
                .getArrivalDate());
        int departureCompare = this.getDepartureDate()
                .compareTo(compReservationIn.getDepartureDate());
        int nameCompare = this.getName().compareTo(compReservationIn.getName());
        //we know that it isnt equal, so what is it??
        if (arrivalCompare != 0) {
            return arrivalCompare;
        } else if (departureCompare != 0) {
            return departureCompare;
        } else {
            return nameCompare;
        }

    }    /**
     * Compares the two objects. Compares departure date, arrival date and name,
     * in order.
     * @param reservationIn the object to check
     * @return integer less than zero if less than, greater than 0 if greater
     * than and 0 if equal.
     * @throws java.lang.ClassCastException 
     */
    public int compareToByDepartureDate(Object reservationIn) throws ClassCastException {
        Reservation compReservationIn = (Reservation) reservationIn;
        //Quick check to see if equal:
        int arrivalCompare = this.getArrivalDate().compareTo(compReservationIn
                .getArrivalDate());
        int departureCompare = this.getDepartureDate()
                .compareTo(compReservationIn.getDepartureDate());
        int nameCompare = this.getName().compareTo(compReservationIn.getName());
        //we know that it isnt equal, so what is it??
        if (departureCompare != 0) {
            return departureCompare;
        } else if (arrivalCompare != 0) {
            return arrivalCompare;
        } else {
            return nameCompare;
        }
    }
    private String name;
    private DateAD arrivalDate;
    private DateAD departureDate;
    private final int BEFORE = -1;
    private final int AFTER = 1;
    private final int EQUALS = 0;
    /**
     * Unused, only here for future implementation.
     * @return na
     */
    Object getCustomer() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
