package extreme.hotel.viewer;

import Calendar.DateAD;
import java.util.ArrayList;

/**
 * Class: ExtremeFilter
 * File: ExtremeFilter.java
 * Applies various filters to an array of reservations. By creating an object of
 * this class, the array passed to the constructor will be narrowed down to a
 * specific set of reservations as determined by the filters applied through the
 * methods provided. To retrieve the array in its current state, call the
 * getResults method.
 * @version 1.0
 * @author Alex Yelle, Alex Linville
 * @OS Windows
 * 
 */
public class ExtremeFilter extends ExtremeDB {

    ArrayList<Reservation[]> queryHistory = new ArrayList<Reservation[]>();

    /**
     * Sets the reservation array to be filtered to the array that is passed.
     *
     * @param res the reservation array to be filtered.
     */
    public ExtremeFilter(Reservation[] res) {
        queryHistory.add(res);
    }

    /**
     * Returns the results of the filters applied to the base array as an array
     * of the size of the number of reservations meeting the set constraint
     * requirements
     *
     * @return the results of the filter as an array.
     */
    public Reservation[] getResults() {
        return queryHistory.get(queryHistory.size() - 1);
    }
    /**
     * Applies a filter on the array by matching the name as a whole with 
     * the string passed.
     *
     * @param string the string to be used to apply the filter.
     * @return this ExtremeFilter to allow for further filtering or to get the
     * results.
     */
    public Reservation[] byName(String string) {
        Reservation[] resToSearch = queryHistory.get(queryHistory.size() - 1);
        Reservation[] toReturn = BinarySearch.doSearchWhole(resToSearch, string, 0, resToSearch.length - 1);
        Sorts.quickSort(toReturn);
        queryHistory.add(toReturn);
        return toReturn;
    }

    /**
     * Applies a filter on the array by matching the beginning of the names of
     * the customers with the string passed.
     *
     * @param string the string to be used to apply the filter.
     * @return this ExtremeFilter to allow for further filtering or to get the
     * results.
     */
    public Reservation[] byNameBeginsWith(String string) {
        Reservation[] resToSearch = queryHistory.get(queryHistory.size() - 1);

        if (resToSearch.length == 0) {
            queryHistory.add(resToSearch);
            return resToSearch;
        }
        if (string.length() == 0) {
            return resToSearch;
        }
        Comparable[] toSearch = new String[resToSearch.length];
        int j = 0;
        for (Reservation res : resToSearch) {
            if(res.getName().length() > resToSearch[j].getName().length()){
            toSearch[j] = res.getName().toString().substring(0, string.length());
            }else{
                toSearch[j] = res.getName();
            }
            j++;
        }
        Reservation[] toReturn = BinarySearch.doSearch(resToSearch, string,
                0, resToSearch.length);
        if (toReturn != null) {
            Sorts.insertionSort(toReturn);
            queryHistory.add(toReturn);
            return toReturn;
        } else {
            toReturn = new Reservation[0];
            queryHistory.add(toReturn);
            return toReturn;
        }
    }

    /**
     * Applies a filter on the array by matching the date of arrival with the 
     * date passed as the parameter.
     *
     * @param date the date to be used to apply the filter
     * @return this ExtremeFilter to allow for further filtering or to get the
     * results.
     */
    public Reservation[] byArrivalDate(DateAD date) {
        Reservation[] resToSearch = getResults();
        Comparable[] toSearch = new DateAD[resToSearch.length];
        int j = 0;

        ExtremeSorts.quickSortByArrivalDate(resToSearch);
        for (int i = 0; i < resToSearch.length - 1; i++) {
            toSearch[i] = resToSearch[i].getArrivalDate();
        }

        j = 0;
        int[] result =
                Search.binarySearchForAll(toSearch,
                date);
        Reservation[] toReturn = new Reservation[result[1] - result[0] + 1];
        for (int i = result[0]; i <= result[1]; i++) {
            toReturn[i - result[0]] = queryHistory.get(queryHistory.size() - 1)[i];
        }
        queryHistory.add(toReturn);
        return toReturn;
    }
    /**
     * Applies a filter on the array by matching the date of departure with the 
     * date passed as the parameter.
     *
     * @param date the date to be used to apply the filter
     * @return this ExtremeFilter to allow for further filtering or to get the
     * results.
     */
    public Reservation[] byDepartureDate(DateAD date) {
        Reservation[] resToSearch = getResults();
        Comparable[] toSearch = new DateAD[resToSearch.length];
        int j = 0;

        ExtremeSorts.quickSortByDepartureDate(resToSearch);
        for (int i = 0; i < resToSearch.length - 1; i++) {
            toSearch[i] = resToSearch[i].getDepartureDate();
        }

        j = 0;
        int[] result =
                Search.binarySearchForAll(toSearch,
                date);
        Reservation[] toReturn = new Reservation[result[1] - result[0] + 1];
        for (int i = result[0]; i <= result[1]; i++) {
            toReturn[i - result[0]] = queryHistory.get(queryHistory.size() - 1)[i];
        }
        queryHistory.add(toReturn);
        return toReturn;
    }
    /**
     * Removes the last filter applied to the array. For example, if a filter of
     * 'byNameBeginsWith("Ale")' was applied followed by "byArrivalDate(DateAD
     * date)", calling this method will revert the filter to the
     * 'byNameBeginsWith("Ale") filter.
     */
    public void removeLastFilter() {
        if(queryHistory.size() != 1){
        queryHistory.remove(queryHistory.size() - 1);
        }
        }
}
