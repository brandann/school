/*
 * Decode.java
 */
package huffmantree;

import java.io.*;
import java.util.ArrayList;

/**
 * File name:   Decode.java <br>      
 * Class Name:  Decode <br>
 * Description: Huffman Tree Decompression class - Decompiles a file
 * from a stored huffman tree.<br>
 * @version     1.0 <br>
 * @author      Brandan Haertel, Bryce Fenske <br>
 * Environment: PC, Windows 8 64-bit, jdk 1.7 <br>
 * History:     06/09/2013 1.0 completed <br>
 */
public class Decode {
    
    /**
     * Main Constructor - File Decompression.
     * @param path to be read from
     * @param d Driver instance to be used
     * @throws ClassNotFoundException 
     */
    public Decode(String path, Driver d) throws ClassNotFoundException{
        this.path = path;
        readIn();
        driver = d;
    }
    
    /**
     * Loads Tree and bytes from file.
     * @throws ClassNotFoundException 
     */
    private void readIn() throws ClassNotFoundException{
        byte[] bytes;
        String[] strings = null;
        ObjectInputStream in;
        File inFile = new File(path);
        Object temp = null;
        
        try{
            in = new ObjectInputStream(new FileInputStream(inFile));
            temp = in.readObject();
            tree = (Tree) temp;
            bytes = (byte[]) in.readObject();
            in.close();
            strings = new String[bytes.length];
            for(int i = 0; i < bytes.length; i++){
                String byteString = String.format("%8s", 
                        Integer.toBinaryString((bytes[i] + BYTE_LENGTH) 
                        % BYTE_LENGTH)).replace(BLANK_CHAR, BIN_ZERO_CHAR);
                strings[i] = byteString;
            }
            decompress(strings);
        }
        catch(IOException e){
            System.out.println("Error: Decode/readIn");
        }
    }
    
    /**
     * Decompresses bytes to string characters.
     * @param array String[] of byte characters to be decompressed.
     * @throws NullPointerException if array is empty or null
     */
    private void decompress(String[] array) throws NullPointerException, 
            IOException{
        ArrayList<String> lineList = new ArrayList<>();
        ArrayList<String> stringList = new ArrayList<>();
        Node current = tree.head;
        String line = EMPTY_STRING;
        
        for(String s : array){
            for(int i = 0; i < s.length(); i++){
                if(s.charAt(i) == LEFT_TOKEN)
                    current = tree.getLeftNode();
                else
                    current = tree.getRightNode();
                if(current.isLeaf()){
                    String inString = String.valueOf(current.getData());
                    if(inString.equals("\n")){
                        line = EMPTY_STRING;
                        for(String c : stringList)
                            line += c;
                        lineList.add(line);
                        stringList.clear();
                    }
                    else
                        stringList.add(inString);
                    current = tree.head;
                }
            }
        }
        line = EMPTY_STRING;
        for(String c : stringList)
            line += c;
        lineList.add(line);
        writeLine(lineList);
    }
    
    /**
     * Appends decompiled string characters to end of file.
     * @param inStringList Single line of character to be appended to file.
     */
    private void writeLine(ArrayList<String> inStringList) 
            throws IOException{
        String outPath = path.substring(0, 
                path.indexOf(EXTENSION_PERIOD_TOKEN)) + TXT_TOKEN;
        File outFile = new File(outPath);
        if(!outFile.exists())
            outFile.createNewFile();
        try {
            PrintWriter fileOutput = new PrintWriter(outFile);
            if(inStringList.size() > EMPTY_ARRAY){
                for(String s : inStringList)
                    fileOutput.println(s);
            }
            fileOutput.close();
        }
        catch(IOException e) {
            System.out.println("Your decompression was UNSUCCSESSFUL, "
                    + "please try again.");
        }
        System.out.println("Your decompression was SUCCSESSFUL, your "
                + "file is saved as:\n" + outPath);
    }
    
    private String path;
    private Driver driver;
    private Tree tree;
    private final int BYTE_LENGTH = 256;
    private final int EMPTY_ARRAY = 0;
    private final char LEFT_TOKEN = '0';
    private final char BLANK_CHAR = ' ';
    private final char BIN_ZERO_CHAR = '0';
    private final String EXTENSION_PERIOD_TOKEN = ".";
    private final String EMPTY_STRING = "";
    private final String TXT_TOKEN = "x.txt";
}
