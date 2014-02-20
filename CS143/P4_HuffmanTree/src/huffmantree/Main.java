/*
 * Main.java
 */
package huffmantree;

import java.io.IOException;

/**
 * File name:   Main.java <br>      
 * Class Name:  Main <br>
 * Description: Huffman Tree Main class. <br>
 * @version     1.0 <br>
 * @author      Brandan Haertel, Bryce Fenske <br>
 * Environment: PC, Windows 8 64-bit, jdk 1.7 <br>
 * History:     06/09/2013 1.0 completed <br>
 */
public class Main {

    /**
     * Main class determines program funtion based on arguments passed to
     * the program, defaults to compression.
     * @param args String[] startup arguments.
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) 
            throws ClassNotFoundException, IOException {
        Driver d;

        if(args.length == EMPTY_ARRAY)
            d = new Driver(EMPTY_STRING, EMPTY_STRING);
        else if(args[0].equals(DECODE_TOKEN))
            if(args.length > 1)
                d = new Driver(args[1], args[0]);
            else
                d = new Driver(EMPTY_STRING, args[0]);
        else
            d = new Driver(args[0], EMPTY_STRING);
    }
    
    private static final int EMPTY_ARRAY = 0;
    private static final String EMPTY_STRING = "";
    private static final String DECODE_TOKEN = "-d";

}
