/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extreme.hotel.viewer;

/**
 * file: Search.java
 * class: Search
 * This class contains static methods that performs searches on an array.
 * @version 1.0
 * @author Alex Yelle
 * compiler: JDK 1.6
 * test platform: Windows 7, AMD Phenom II X4 965, 4.00 GB using NetBeans IDE
 *  7.2
 * 
 */
public class Search {
    /**
     * This performs a binary search on the inputted array to see if the 
     * inputted comparable object exists within the array. In order for the
     * search to be accurate, the array must be pre-sorted.
     * @param cArray the inputted array to be searched
     * @param c a comparable object that will be used to search for within the
     * array
     * @return the position of the object within the array, if found. Returns -1
     * if the object could not be found.
     */
    public static int binarySearch(Comparable[] cArray, Comparable c) {
        return binarySearch(cArray, c, 0, cArray.length - 1);
    }
    /**
     * Performs a binary search of the array. Assumes that the array is pre-
     * sorted.
     * @param cArray the array to search
     * @param c the object to search for
     * @return an int[] of size 2, int[0] being the first index of where the
     * object being searched exists, int[1] being the last index.
     */
    public static int[] binarySearchForAll(Comparable[] cArray, Comparable c){
        
        int[] toReturn = new int[2];

        int firstResult = binarySearch(cArray, c);
        int firstIndex = firstResult;
        int lastIndex = firstResult;
        if(firstResult != -1){
            int i = 1, j = 1;
            while(firstResult - i >= 0 && cArray[firstResult - i].compareTo(c) == 0){
                firstIndex= firstResult - i;
                i++;
            }
            while(firstResult + j < cArray.length && cArray[firstResult + j].compareTo(c) == 0){
                lastIndex= firstResult + j;
                j++;
            }
        }
        toReturn[0] = firstIndex;
        toReturn[1] = lastIndex;
        return toReturn;
    }
    /**
     * This method performs a search on the inputted array in a linear fashion
     * to see if the inputted comparable object exists within the array. The
     * array does not have to be pre-sorted.
     * @param cArray the inputted array to be searched
     * @param c a comparable object that will be used to search for within the
     * array
     * @return the position of the object within the array, if found. Returns -1
     * if the object could not be found.
     */
    public static int linearSearch(Comparable[] cArray, Comparable c){
        for(int i = 0; i < cArray.length; i++){
            
            if(cArray[i].equals(c)){
                return i;
            }
        }
        return -1;
    }
    /**
     * This method is the recursive portion of the binary search. It takes the
     * inputted array and checks which portion of the array that the comparable 
     * object could exist in.
     * @param cArray the array to be searched
     * @param c the comparable object being used for the search
     * @param ind1 the beginning index of the array to be searched
     * @param ind2 the ending index of the array to be searched.
     * @return the position of the object within the array, if found. Returns -1
     * if the object could not be found.
     */
    private static int binarySearch(Comparable[] cArray, Comparable c, int ind1
            , int ind2) {
        int mid = (ind1 + ind2) / 2;
        int dir = c.compareTo(cArray[mid]);
        if (ind1 > ind2) {
            return -1;
        }
            if (dir < 0) {
                return binarySearch(cArray, c, ind1, mid-1);
            } else if (dir > 0) {
                return binarySearch(cArray, c, mid+1, ind2);
            }
                //System.out.println("result = " + result);
                return mid;
            }
        }
    

