package extreme.hotel.viewer;

import Calendar.DateAD;
import extreme.hotel.viewer.ExtremeDB;

/**
 * Created with IntelliJ IDEA.
 * User: Zander
 * Date: 3/4/13
 * Time: 8:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExtremeDriver {
    public static void main(String[] args){
        ExtremeDB db = new ExtremeDB();
        ExtremeFilter filter = new ExtremeFilter(db.getReservations());
        filter.byNameBeginsWith("Paul");
        Reservation[] reservations = filter.getResults();
        ExtremeFilter filter2 = new ExtremeFilter(db.getReservations());
        filter2.byDepartureDate(new DateAD());
        
        for(Reservation r : reservations){
            System.out.println(r);
        }
        reservations = filter2.getResults();
        for(Reservation r : reservations){
            System.out.println(r);
        }
//        for(Reservation r : reservations){
//            System.out.println(r);
//        }
//        
        
        
    }
}
