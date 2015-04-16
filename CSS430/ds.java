import java.io.*;
import java.util.*;

public class ds
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket sock = new ServerSocket(6013);
            
            while(true)
            {
                Socket client = sock.accept();
                PrintWriter pout = new PrintWriter(client, get(OutputStream(), true);
                pout.println(new java.util.Date().toString());
                client.close();
            }
        } catch(IOException e) {}
    }
}
