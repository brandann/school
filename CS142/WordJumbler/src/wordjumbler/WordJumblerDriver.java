/*
 * WordJumblerDriver.java
 */
package wordjumbler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * WordJumbler Gui Driver.<br>
 * WordJumbler takes a random word from a dictionary file, jumbles it<br>
 * and displays the scrammbled word to the user to guess the correct word.
 *
 * @version 1.0 <br />
 * @author Brandan Haertel<br />
 * 
 * Test Environment: JDK 1.7.0_03 on Windows 7, Intel i7 CPU <br>
 * Test Environment: JDK 1.7.0_03 on Windows 8, AMD A8 CPU <br>
 * 
 * Completed: Feburary 11, 2013<br>
 */
public class WordJumblerDriver 
{
    
    /**
     * Constructor - default constructor.
     */
    public WordJumblerDriver()          
    {

    }
    
    /**
     * gets current game word
     * @return word
     */
    public String getWord()                                    
    {
        return word;
    }
    
    /**
     * gets the dictionary array
     * @return wordArray
     */
    public String[] getDictionaryArray()
    {
        return wordArray;
    }
    
    /**
     * gets possable solutions
     * @param myWord
     * @return Solutions[]
     */
    public String[] getPossibleSolutions(String myWord)
    {
        PermutationGenerator Permutation = new PermutationGenerator(myWord);
        ArrayList<String> permList = Permutation.getPermutation();
        
        
        for(int i = 0; i < permList.size(); i++)
        {
            int j = 0;
            boolean done = false;
            while(!done)
            {
                if(!(i == j))
                {
                    if(j == permList.size())
                    {
                        done = true;
                    }
                    else if(permList.get(i).equals(permList.get(j)))
                    {
                        permList.remove(j);
                        j = 0;
                    }
                    else
                    {
                        j++;
                    }
                }
                else
                {
                    j++;
                }
            }
        }
        
        String[] permutationArray = new String[permList.size()];
        permutationArray = permList.toArray(permutationArray);
        
        int permutations = 0;
        ArrayList<String> permutationMatch = new ArrayList<>();
        
        for(int i = 0; i < permutationArray.length; i++)
        {
            if(BinSearch.recBinarySearch(wordArray, 0, 
                    (wordArray.length - 1), permutationArray[i]))
            {
                permutationMatch.add(permutationArray[i]);
                permutations++;
            }
        }
        
        String[] permutationMatchArray 
                = new String[permutationMatch.size()];
        permutationMatchArray 
                = permutationMatch.toArray(permutationMatchArray);
        
        
        
        return permutationMatchArray;
    }
    
    /**
     * scrambles word a random number of times
     * @param myWord
     * @return scrambledWord
     */
    public String getScrambledWord(String myWord) 
    {
        Random rand = new Random();
        return scrambleWord(myWord, 0, rand.nextInt(666));
    }
    
    /**
     * gets the length of the current game word
     * @return word.length()
     */
    public int getWordLength() 
    {
        return word.length();
    }
    
    /**
     * returns character arrays for use in combo boxes
     * rotating by 1 digit each time.
     * @param myRotationFactor
     * @return wordRotationArray
     */
    public String[] getWordRotationArray(int myRotationFactor) 
    {
        wordRotationArray = new String[scrambledWord.length() + 1];
    
        wordRotationArray[0] = " ";
        int arrayInt;
        int charInt;
        int y;
        int r = myRotationFactor;
        for(int i = 0; i < scrambledWord.length(); i ++)
        {
            charInt = i;
            arrayInt = charInt - myRotationFactor + 1;
            if (arrayInt < 1)
            {
                arrayInt = arrayInt + wordRotationArray.length - 1;
                if(arrayInt == 0)
                {
                    arrayInt = arrayInt - 1;
                }
            }
            wordRotationArray[arrayInt] 
                    = String.valueOf(scrambledWord.charAt(charInt));
        }
        return wordRotationArray;
    }
    
    /**
     * dictionary length
     * @return wordArray.length
     */
    public int getDictionaryLength()
    {
        return wordArray.length;
    }
    
    /**
     * sets the dictionary to myFile
     * @param myFile 
     */
    public void setDictionary(File myFile)             
    {
        dictionary = myFile;
        ArrayList<String> wordList = new ArrayList<>();
        
        Scanner fileInputScanner;
        try
        {
           fileInputScanner = new Scanner(dictionary);
           while(fileInputScanner.hasNextLine())
           {
               wordList.add(fileInputScanner.nextLine());
           }       
        }
        catch(IOException e)
        {      }
        
        wordArray = new String[wordList.size()];
        wordArray = wordList.toArray(wordArray);
    }

    /**
     * gets a new random word from the dictionary.
     */
    public void setNewWord()
    {
        Random rand = new Random();
        word = wordArray[rand.nextInt(wordArray.length)];
    }
    
    /**
     * does a binary search for myGuess in the dictionary array
     * @param myGuess
     * @return String Correct, Incorrecct, or incomplete.
     */
    public String isGuessCorrect(String myGuess)
    {
        if(myGuess.length() == word.length())
        {
            if(BinSearch.recBinarySearch(wordArray, 0, 
                    (wordArray.length - 1), myGuess))
            {
                return "Correct";
            }
            return "Incorrect";
        }
        return "Incomplete";
    }
    
    /**
     * scrambles a word
     * @param myString
     * @param n index to be swapped
     * @param r recursion anchor
     * @return scrambledWord
     */
    private String scrambleWord(String myString, int n, int r) 
    {
        Random rand = new Random();
        wordRotationArray = new String[myString.length()];
        
        if((r != 0) & (n < myString.length()))
        {
            String[] stringArray = new String[myString.length()];
        
            for(int i = 0; i < stringArray.length; i++)
            {
                stringArray[i] = String.valueOf(myString.charAt(i));
            }
        
            String a = stringArray[n];
            String b = stringArray[1];
            stringArray[n] = b;
            stringArray[1] = a;
        
            scrambledWord = "";
        
            for(int j = 0; j < myString.length(); j++)
            {
                scrambledWord = scrambledWord + stringArray[j];
            }
            scrambleWord(scrambledWord, (n+1), (r-1));
        }
        return scrambledWord;
    }

    /**
     * swap not used
     * @param array
     * @param index1
     * @param index2
     * @return 
     */
    private String[] swap(String[] array, int index1, int index2)
    {
          String temp = array[index1];
          array[index1] = array[index2];
          array[index2] = temp;
          return array;
     }

    private String[] wordArray;
    private File dictionary;

    private String word;
    private String scrambledWord;
    private String[] wordRotationArray;
    //Dictionary dictionary;
}