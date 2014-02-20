/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extreme.hotel.viewer;


/**
 * Performs various sorts on an array of reservations.
 * @author Alex Yelle, Alex ???
 * @version 1.0
 */
public class ExtremeSorts extends Sorts{
    /**
     * Sorts the reservation array by arrival date, departure date and name
     * in order. Only sorts between the specified indices.
     * @param array the array to sort.
     * @param lPos the left-most index to be sorted.
     * @param rPos the right-most index to be sorted.
     * @throws ArrayIndexOutOfBoundsException 
     */
        public static void insertionSortByArrivalDate(Reservation[] array, 
            int lPos, int rPos) 
    throws ArrayIndexOutOfBoundsException{
        
        int startPos = lPos;
        int endPos = rPos;
        
        lPos++;

        Reservation value;
        for (; lPos <= endPos; lPos++) {
            value = array[lPos];
            rPos = lPos;
            while (rPos > startPos && array[rPos - 1].compareToByArrivalDate(value) > 0) {
                array[rPos] = array[rPos - 1];
                rPos--;
            }
            array[rPos] = value;
        }
    }
        /**
         * Performs a quicksort on the reservation array by arrival date, 
         * departure date and name in order. Only sorts between the arrays
         * specified.
         * @param array the array to be sorted
         * @param left the left-most index to be sorted.
         * @param right the right-most index to be sorted.
         */
    public static void sortPartByArrivalDate(Reservation[] array, int left, int right){
        int lPos = left;
        int rPos = right;
        if (rPos - lPos <= 5) {
            insertionSortByArrivalDate(array, lPos, rPos);
        } else {
            Comparable pVal = array[(left + right) / 2];
            while (lPos <= rPos) {
                while ((array[lPos]).compareToByArrivalDate(pVal) < 0) {
                    lPos++;
                }
                while ((array[rPos]).compareToByArrivalDate(
                        pVal) > 0) {
                    rPos--;
                }
                if (lPos <= rPos) {
                    swap(array, lPos, rPos);
                    lPos++;
                    rPos--;
                }
            }
            if (left < rPos) {
                sortPartByArrivalDate(array, left, rPos);
            }
            if (lPos < right) {
                sortPartByArrivalDate(array, lPos, right);
            }
        }
    }
    /**
     * Performs a quicksort on the array by sorting the array by arrival date,
     * departure date, and name in order.
     * @param array the array to be sorted.
     * @return the array sorted.
     */
    public static void quickSortByArrivalDate(Reservation[] array){
        sortPartByArrivalDate(array, 0, array.length - 1);
       
    }
    /**
     * Performs an insertion sort on the reservation array by departure date, 
     * arrival date, and name in order.
     * @param array the array to be sorted
     * @param lPos the left-most position to be sorted
     * @param rPos the right-most position to be sorted
     * @throws ArrayIndexOutOfBoundsException 
     */
    public static void insertionSortByDepartureDate(Reservation[] array, 
            int lPos, int rPos) 
    throws ArrayIndexOutOfBoundsException{
        int startPos = lPos;
        int endPos = rPos;
        
        lPos++;

        Reservation value;
        for (; lPos <= endPos; lPos++) {
            value = array[lPos];
            rPos = lPos;
            while (rPos > startPos && array[rPos - 1].compareToByDepartureDate(value) > 0) {
                array[rPos] = array[rPos - 1];
                rPos--;
            }
            array[rPos] = value;
        }
    }
    /**
     * Sorts the reservation array by the date of departure, date of arrival
     * and name in order.
     * @param array the array to be sorted
     * @param left the left-most index to be sorted
     * @param right the right-most index to be sorted
     */
    public static void sortPartByDepartureDate(Reservation[] array, int left,
            int right){
        int lPos = left;
        int rPos = right;
        if (rPos - lPos <= 5) {
            insertionSortByDepartureDate(array, lPos, rPos);
        } else {
            Comparable pVal = array[(left + right) / 2];
            while (lPos <= rPos) {
                while ((array[lPos]).compareToByDepartureDate(pVal) < 0) {
                    lPos++;
                }
                while ((array[rPos]).compareToByDepartureDate(
                        pVal) > 0) {
                    rPos--;
                }
                if (lPos <= rPos) {
                    swap(array, lPos, rPos);
                    lPos++;
                    rPos--;
                }
            }
            if (left < rPos) {
                sortPartByDepartureDate(array, left, rPos);
            }
            if (lPos < right) {
                sortPartByDepartureDate(array, lPos, right);
            }
        }
    }
    /**
     * Sorts the reservation array by the date of arrival, date of departure
     * and name in order.
     * @param array the array to be sorted
     * @return the sorted array.
     */
    public static void quickSortByDepartureDate(Reservation[] array){
        sortPartByDepartureDate(array, 0, array.length - 1);
    }
}
