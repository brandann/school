/*
 * BinSearch.java
 */
package wordjumbler;

/**
 * Binary Search searches for a word in a array
 *
 * @version 1.0 <br />
 * @author Brandan Haertel<br />
 * 
 * Test Environment: JDK 1.7.0_03 on Windows 7, Intel i7 CPU <br>
 * Test Environment: JDK 1.7.0_03 on Windows 8, AMD A8 CPU <br>
 * 
 * Completed: Feburary 11, 2013<br>
 */
public class BinSearch {

    /**
     * Binary Static String Search.
     * @param myStringArray String[] to be searched.
     * @param first int starting index.
     * @param upto int ending index.
     * @param myGuess String word to search for.
     * @return boolean
     */
    public static boolean recBinarySearch(String[] myStringArray, 
                                          int first, 
                                          int upto, 
                                          String myGuess) 
    {
        int mid = (first + upto) / 2;  // Compute mid point.
        int k = myStringArray[mid].compareTo(myGuess) * -1;
        if (first <= upto) 
        {
            if (k == 0) 
            {
                return true;   // Found it.
            }            
            else if (k < 0) 
            {
                return recBinarySearch(
                        myStringArray, first, (mid - 1), myGuess);
            } 
            else if (k > 0) 
            {
                return recBinarySearch(
                        myStringArray, (mid + 1), upto , myGuess);
            } 
        }
        return false;  // Failed to find key
    }
}