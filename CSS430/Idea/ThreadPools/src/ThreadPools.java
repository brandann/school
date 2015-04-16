/**
 * Created by brandan on 4/15/15.
 */
import java.lang.Thread;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class ThreadPools
{
    public static void main(String[] args)
    {
        Thread t1 = new Thread(new PrintTask("Task 1"));
        Thread t2 = new Thread(new PrintTask("Task 2"));
        Thread t3 = new Thread(new PrintTask("Task 3"));
        Thread t4 = new Thread(new PrintTask("Task 4"));

        PrintTask t5 = new PrintTask("WOWOWOWOWOWO");

        ExecutorService threadExecutor = Executors.newCachedThreadPool();

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        threadExecutor.execute(t5);

        System.out.println("MAIN DONE");
    }

}
