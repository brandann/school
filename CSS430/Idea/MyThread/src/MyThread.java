/**
 * Created by brandan on 4/15/15.
 */
public class MyThread {
    public static void main(String[] args)
    {
        Stirng arg = args[0];
        ThreadFunc child = new ThreadFunc();
        child.start();

        for(int i = 0; i < 10; i++)
        {
            try
            {
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
