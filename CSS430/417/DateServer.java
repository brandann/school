import java.net.*;
import java.io.*;

public class DateServer
{
    public static void main(String[] args)
    {
        try {
            ServerSocket sock = new ServerSocket(6013);
            
            // now listen for connections
            while(true)
            {
                Thread thread = new Thread(new helper(sock.accept()));
                thread.start();
            }
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }   
}