import java.net.*;
import java.io.*;
import java.util.concurrent.*;

package DS
{
    public class DateServer
    {
        public static void main(String[] args)
        {
            try {
                ServerSocket sock = new ServerSocket(6013);
                
                
                // now listen for connections
                while(true)
                {
                    ExecutorService pool = Executors.newCachedThreadPool();
                    pool.execute(new helper(sock.accept()));
                    pool.shutdown();
                }
            } catch(IOException ioe) {
                System.err.println(ioe);
            }
        }   
    }
}