import java.net.*;
import java.io.*;

public class helper implements Runnable
{
    private Socket client;
    public helper(Socket client)
    {
        this.client = client;
    }

    public void run()
    {
        try {
            PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
            
            // write the data to the socket
            pout.println(new java.util.Date().toString());
            
            // close the socket and resume
            // listening for connections
            client.close();
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
        
    }
}