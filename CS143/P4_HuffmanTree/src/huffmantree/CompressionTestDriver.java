/*
 * CompressionTestDriver.java
 */
package huffmantree;

/**
 * Tests Compression.java
 * (success|failure)1 outcome of read in and data generation process
 * (success|failure)2 outcome of encode and write process
 * if success2: see proj directory for .huf and .cod files
 * @author Bryce Fenske
 */
public class CompressionTestDriver
{
    private static final String BOOK = "Alice_In_Wonderland.txt";
    
    public static void main (String[] args)
    {
        Compression comp = new Compression(BOOK);
        if(comp.getData())
        {
            System.out.println("success1");
        }
        else
        {
            System.out.println("failure1");
            System.exit(-1);
        }
        if(comp.encode())
        {
            System.out.println("success2");
        }
        else
        {
            System.out.println("failure2");
        }
    }
}