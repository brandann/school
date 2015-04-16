/**
 * Created by brandan on 4/15/15.
 */
public class test extends Thread
{

    public void run()
    {
        SysLib.cout("boo!");
        SysLib.exit();
    }

    public void start()
    {

    }
}
