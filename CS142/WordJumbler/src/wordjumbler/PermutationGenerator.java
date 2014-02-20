/*
 * PermutationGenerator.java
 */
package wordjumbler;

import java.util.ArrayList;

/**
 * PermutationGenerator<br>
 * Permutation Generator finds all the permutations of a string.<br>
 *
 * @version 1.0 <br />
 * @author Brandan Haertel<br />
 * 
 * Test Environment: JDK 1.7.0_03 on Windows 7, Intel i7 CPU <br>
 * Test Environment: JDK 1.7.0_03 on Windows 8, AMD A8 CPU <br>
 * 
 * Completed: Feburary 11, 2013<br>
 */
public class PermutationGenerator {
    
    /**
     * Constructor - word to permutate.
     * @param myWord 
     */
    public PermutationGenerator(String myWord)
    {
        word = myWord;
    }

    /**
     * returns a ArrayList of permutations
     * @return ArrayList
     */
    public ArrayList<String> getPermutation()
    {
        ArrayList<String> arrayListPermutation = new ArrayList<String>();
        
        if (word.length() == 0)
        {
            arrayListPermutation.add(word);
            return arrayListPermutation;
        }
        
        for(int i = 0; i < word.length(); i++)
        {
            String shorterWord = word.substring(0,i) 
                    + word.substring(i + 1);
            
            PermutationGenerator shorterPermutaion 
                    = new PermutationGenerator(shorterWord);
            ArrayList<String> shorterWordPermutations
                    = shorterPermutaion.getPermutation();
            
            for(String s : shorterWordPermutations)
            {
                arrayListPermutation.add(word.charAt(i) + s);
            }
        }
        return arrayListPermutation;
    }
    private String word;
}
