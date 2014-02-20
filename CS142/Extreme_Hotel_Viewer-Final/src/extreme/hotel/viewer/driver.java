package extreme.hotel.viewer;


import java.io.File;

/**
 * File name:   driver.java       
 * Class Name:  driver
 * Description: Driver for tests on AltDB
 *              Initial tests should return null
 *              ie, no unhandled exceptions
 * @version     1.0
 * @author      Bryce Fenske
 * Environment: PC, Windows XP Pro, jdk 7.1
 * History:     3/11/13 1.0 completed
 */
public class driver 
{
    public static void main (String[] args)
    {
        AltDB db = new AltDB();
        String[] keys = db.getKeys();
        File pref;
        
        if(db.hasData())
        {
            for(String s: keys)
            {
                System.out.println("Key:\n" + s);
                System.out.println("File persisted:");
                System.out.println(db.getPreference(s));
            }
        }
        //returns null
        db.reset();
        System.out.println(db.getPreference(null));
        System.out.println(db.getKeys());
        
        //get user files. will persist on next run.
        System.out.println("This is your key. Don't lose it.\n"
                + db.getUserPreference());
        pref = db.getUserFile(); 
        db.flush();
    }
}