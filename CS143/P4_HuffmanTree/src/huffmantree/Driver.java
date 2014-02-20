/*
 * Driver.java
 */
package huffmantree;

import java.io.File;
import javax.swing.JFileChooser;

/**
 * File name:   Driver.java <br>      
 * Class Name:  Driver <br>
 * Description: Huffman Tree Driver class - executes either compression
 * or decompression based off user arguments, checks validity of file
 * paths.
 * program. <br>
 * @version     1.0 <br>
 * @author      Brandan Haertel, Bryce Fenske <br>
 * Environment: PC, Windows 8 64-bit, jdk 1.7 <br>
 * History:     06/09/2013 1.0 completed <br>
 */
public class Driver {
    
    /**
     * Main Constructor - Driver Class
     * @param path String file path to be used
     * @param parameter String parameter to determine program function
     * @throws ClassNotFoundException 
     */
    public Driver(String path, String parameter) 
            throws ClassNotFoundException{
        if(parameter.equals(DECODE_TOKEN)){
            if(path.contains(HUF_TOKEN)){
                if(validFile(path))
                    decode();
                else
                    if(tryAgain())
                        decode();
            }
            else
                if(tryAgain())
                    decode();
        }
        else{
            if(path.contains(TXT_TOKEN)){
                if(validFile(path))
                    compress();
                else
                    if(tryAgain())
                        compress();
            }
            else
                if(tryAgain())
                    compress();
        }
    }
    
    /**
     * Checks to see if file exists and can be read from.
     * @param path String file to be checked
     * @return true if file is funtioning correctly
     */
    private boolean validFile(String path){
        File file = new File(path);
        if(file.exists()){
            this.path = path;
            return true;
        }
        return false;
    }
    
    /**
     * presents user with a file chooser if the original file path
     * was invalid, the file could not be read, or improper parameters.
     * @return true if new selected file is valid.
     */
    private boolean tryAgain(){
        System.out.println("Invalid File, "
                + "Please enter a new File path to compress");
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File selectedFile = chooser.getSelectedFile();
            try {
                if (selectedFile.exists() && selectedFile.canRead()) { 
                    path = selectedFile.getAbsolutePath();
                    return true;
                }     
            }
            catch (Exception ex) {}
        }
        return false;
    }
    
    /**
     * Decode Constructor - creates and runs decode class.
     * @throws ClassNotFoundException 
     */
    public void decode() throws ClassNotFoundException{
        Decode d = new Decode(path, this);
    }
    
    /**
     * Compress Constructor - creates and runs compress class.
     */
    public void compress(){
        Compression comp = new Compression(path);
        if(!comp.getData())
        {
            System.out.println("an error occured while reading the file");
        }
        else if(!comp.encode())
        {
            System.out.println("an error occured while writing the file");
        }
    }
    
    Tree tree;
    private String path;
    private final String DECODE_TOKEN = "-d";
    protected final String TXT_TOKEN = ".txt";
    protected final String HUF_TOKEN = ".huf";
}
