/**
 * Created by brandan on 4/15/15.
 */
import java.util.Random;

public class PrintTask implements Runnable{
    private final int SLEEP_TIME;
    private final String TASK_NAME;
    private final static Random generator = new Random();

    public PrintTask(String name)
    {
        TASK_NAME = name;
        SLEEP_TIME = generator.nextInt(5000);
    }

    public void run()
    {
        try
        {

            System.out.println(TASK_NAME + " will sleep for " + SLEEP_TIME);
            Thread.sleep(SLEEP_TIME);
        }
        catch (InterruptedException e) {}
        System.out.println("done");
    }
}
