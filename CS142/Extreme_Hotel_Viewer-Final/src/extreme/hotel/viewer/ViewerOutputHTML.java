/*
 * ViewerOutputHTML.java
 */
package extreme.hotel.viewer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ViewerOutputHTML
 * Automated class that outputs an array of Reservation to a HTML file
 * with built in code for web viewing, and lookup retention.
 * @version 1.0
 * @author Brandan Haertel
 * Compiler: jdk1.7.0_09
 * Tested on:   Windows 8 64bit AMD A8, 
 *              Windows 7 64bit Intel I7, 
 *              Ubuntu 12.10 64bit AMD 8
 * History:
 *      3/13/2013: Version 1.0 released to public!
 */
public class ViewerOutputHTML {
    
    public ViewerOutputHTML(Reservation[] myArray)
    {
        myFile = "Results.html";
        myOutputArray = myArray; 
        print();
    }
    private void print()
    {
        try
        {
            fileOutput = new PrintWriter(new FileOutputStream(myFile));
            fileOutput.println("<html><head><meta content=\"text/html; "
                    + "charset=ISO-8859-1\"http-equiv=\"Content-Type\">"
                    + "<title></title></head><body alink=\"#EE0000\" "
                    + "bgcolor=\"#666666\" link=\"#0000EE\" text=\"#000000"
                    + "\"vlink=\"#551A8B\"><div align=\"center\"><font color="
                    + "\"#ff0000\"><big><big><big>Extreme Hotel Viewer</big>"
                    + "</big></big></font><br><br>");
            
            for(int i = 0; i < myOutputArray.length; i++)
            {
                fileOutput.println("<font color=\"#cccccc\">" 
                        + myOutputArray[i].getName() + "<br>");
                fileOutput.println("<font color=\"#cccccc\">" 
                        + myOutputArray[i].getArrivalDate().toString()
                         + "<br>");
                fileOutput.println("<font color=\"#cccccc\">" 
                        + "to" + "<br>");
                fileOutput.println("<font color=\"#cccccc\">" 
                        + myOutputArray[i].getDepartureDate().toString()
                         + "<br>");
                fileOutput.println("<hr size=\"2\" width=\"80%\">");
            }
            
            fileOutput.println("<br><font color=\"#999999\">Thanks for "
                    + "using our program!<br>Bryce, Brandan, Alex, Matson, "
                    + "Alex</font><br></div></body></html>");
            fileOutput.close( );
        }
        catch(IOException e){}
    }
    private PrintWriter fileOutput;
    private String myFile;
    private Reservation[] myOutputArray;
}
