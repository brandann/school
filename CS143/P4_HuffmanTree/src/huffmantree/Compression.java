/*
 * Compression.java
 */
package huffmantree;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * File name:   Compression.java <br>      
 * Class Name:  Compression <br>
 * Description: Huffman Tree Compression class
 * @version     1.0 <br>
 * @author      Brandan Haertel, Bryce Fenske <br>
 * Environment: PC, Windows 8 64-bit, jdk 1.7 <br>
 * History:     05/27/2013 1.0 completed <br>
 */
public class Compression 
{
    private String fileName;
    private String[] data;
    private ArrayList<String> al;
    private File fileIn;
    private Node[] charFrequency;
    private int[] frequency;
    private SortedMap<Character, String> mapKChar;
    private SortedMap<String, Character> mapKStr;
    private Tree tree;
    
    private final int POSSIBLE_CHARS = 128;
    private final int CHARS_PER_LINE = 32;
    private final int BYTE_SIZE = 8;

    /**
     * constructor
     * @param file to be read from 
     */
    Compression(String file)
    {
        fileName = file;
        fileIn = new File(file);
        al = new ArrayList<>();
        al.ensureCapacity((int)(fileIn.length()/CHARS_PER_LINE));
        frequency = new int[POSSIBLE_CHARS];
    }
    
    /**
     * reads in file, sets up frequency
     * @return true if no errors occured, else false
     */
    public boolean getData()
    {
        
        try (Scanner in = new Scanner(fileIn)) {
            String temp;
            while(in.hasNext())
            {
                temp = in.nextLine() + "\n";
                for(int i = 0; i < temp.length(); i++)
                {
                    frequency[temp.charAt(i)]++;
                }
                al.add(temp);
            }
            data = new String[al.size()];
            data = al.toArray(data);
            al = null;
        }
        catch(Exception ex)
        {
            return false;
        }
        getFrequency();
        generateDatatypes();
        return true;
    }
    
    /**
     * Puts char and respective frequency into a Node[]
     */
    private void getFrequency()
    {
        ArrayList<Node> cfal = new ArrayList<>();
        for(char c = 0; c < frequency.length; c++)
        {
            if(frequency[c] > 0) {
                cfal.add(new Node((Character)c, frequency[c]));
            }
        }
        charFrequency = new Node[cfal.size()];
        charFrequency = cfal.toArray(charFrequency);
    }

    /**
     * initializes huffman tree and maps
     */
    private void generateDatatypes()
    {
        tree = new Tree(charFrequency);
        mapKStr = new TreeMap<>();
        mapKChar = new TreeMap<>();
        for(Node n:charFrequency)
        {
            mapKStr.put(tree.getMapValue((char)n.getData()),
                    (Character)n.getData());
            mapKChar.put((Character)n.getData(), 
                    tree.getMapValue((char)n.getData()));
        }
    }
    
    /**
     * outputs tree and compressed data to file
     * @return true if output successful, else false
     */
    public boolean encode()
    {
        String huffOut = fileName.substring(
                0, fileName.lastIndexOf(".")) + ".huf";
        System.out.println(huffOut);
        
        ArrayList<Byte> alByte = new ArrayList<>();
        
        try (ObjectOutput out = new ObjectOutputStream(
                    new BufferedOutputStream(
                    new FileOutputStream(huffOut))))
        {
            String bin = "";
            byte binOut = 0;
            for(String s:data)
            {
                for(int i = 0; i < s.length(); i++)
                {
                    bin += mapKChar.get(s.charAt(i));
                    if(bin.length() >= BYTE_SIZE)
                    {
                        for(int j = 0; j < BYTE_SIZE; j++)
                        {
                           binOut *= 2;
                           if(bin.charAt(j) == '1')
                           {
                               binOut++;
                           }
                        }
                        bin = bin.substring(BYTE_SIZE, bin.length());
                        alByte.add(binOut);
                        binOut = 0;
                    }
                }
            }
            out.writeObject(tree);
            byte[] temp = new byte[alByte.size()];
            int i = 0;
            for(Byte b:alByte)
            {
                temp[i++] = b;
            }
            out.writeObject(temp);
            out.close();
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }
}