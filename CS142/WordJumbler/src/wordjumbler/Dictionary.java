/*
 * Dictionary.java
 */
package wordjumbler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Dictionary<br>
 * loads a .dic file and converts it to a string[]<br>
 *
 * @version 1.0 <br />
 * @author Brandan Haertel<br />
 * 
 * Test Environment: JDK 1.7.0_03 on Windows 7, Intel i7 CPU <br>
 * Test Environment: JDK 1.7.0_03 on Windows 8, AMD A8 CPU <br>
 * 
 * Completed: Feburary 11, 2013<br>
 */
public class Dictionary {
    
    /**
     * Constructor - sets the dictionary array
     * @param myFile File Dictionary file.
     */
    public Dictionary(File myFile)
    {
        dictionary = myFile;
        
        wordList = new ArrayList<String>();
        //makeArrayList();
        wordArray = new String[wordList.size()];
        wordArray = wordList.toArray(wordArray);
        
    }
    
    /**
     * returns Dictionary array
     * @return wordArray
     */
    public String[] getDictionaryArray()
    {
        return wordArray;
    }
    
    /**
     * returns word in dictionary at index
     * @param idx
     * @return wordArray String
     */
    public String getDictionaryWord(int idx)
    {
        return wordArray[idx];
    }
    
    /**
     * returns dictionary array length
     * @return wordArray.length
     */
    public int getDictionaryLength()
    {
        return wordArray.length;
    }
    
private String[] wordArray;
private ArrayList<String> wordList;
private File dictionary;
}
