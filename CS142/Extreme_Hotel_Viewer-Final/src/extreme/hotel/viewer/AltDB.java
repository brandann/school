package extreme.hotel.viewer;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * File name:   AltDB.java       
 * Class Name:  AltDB
 * Description: Preference database for
 *              absolute filepaths
 *              Includes a file chooser and
 *              access to keys
 * @version     1.0
 * @author      Bryce Fenske
 * Environment: PC, Windows XP Pro, jdk 7.1
 * History:     3/11/13 1.0 completed
 */
public class AltDB 
{
    static JFrame selectKeyFrame;
    static JComboBox selectKeyBox;
    static JButton selectKeyButton;
    private static Preferences altDatabase;
    private static String[] altKeys;
    public final String DEFAULT_KEY = "USER_DATABASE";

   /** 
    * Method name: AltDB()
    * Description: Default constructor. Initializes preference database
    *              and other fields, as well as gui components
    *              used in getDBFile
    */
    AltDB()
    {
        altDatabase = Preferences.userNodeForPackage(AltDB.class);
        setKeys();
    }
    /** Method:      getKeys
     * Description: Returns valid keys for database
     * @return:     String[] altKeys
     */
    public String[] getKeys()
    {
        setKeys();
        return altKeys;
    }
    /** Method:     getPreference
     * Description: Returns valid keys for database
     *              Returns null if no value associated
     *              w/provided key, and null if key is null
     * @param:      String key of stored data
     * @return:     String filepath
     */
    public String getPreference(String key)
    {
        if(key != null)
        {
            return altDatabase.get(key, null);
        }
        else
        {
            return null;
        }
    }
    /** Method:     setPreference
     * Description: Sets a preference with key being the key
     *              and filepath being the item persisted
     * @param:      String filepath to store
     * @param:      String key of stored data
     * @return:     String[] altKeys
     */
    public void setPreference(String key, String filepath)
    {
        altDatabase.put(key, filepath);
    }
    /** Method:     reset
     * Description: Resets preference database
     */
    public void reset()
    {
        try {
            altDatabase.clear();
        } catch (BackingStoreException ex) {
            Logger.getLogger(AltDB.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    /** Method:     getUserFile
     * Description: gets the user's file
     *              returns null if no file selected by user
     * @see:        getUserPreference
     * @see:        getAbsFilePath
     * @return:     File file selected by the user
     */
    public File getUserFile()
    {
        String pref;
        pref = getUserPreference();
        if(pref == null)
        {
            return null;
        }
        else
        {
            setKeys();
            return new File(pref);
        }
        
    }
     /** Method:    setKeys
     * Description: sets the altKeys field to the preference
     *              database's current keys
     */
    private void setKeys()
    {
        try
        {
            altKeys = altDatabase.keys();
        }
        catch (BackingStoreException ex)
        {
            Logger.getLogger(AltDB.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    /** Method:     getUserPreference
     * Description: Gets chosen file from getAbsFilepath
     *              Parses the filepath to get the key
     *              and stores filepath under key, returning
     *              the key as a String
     *              returns null if no file selected by user
     * @return:     String pref key file stored as
     */
    public String getUserPreference()
    {
        String pref = getAbsFilepath();
        if(pref == null)
        {
            return null;
        }
        else
        {
            //String key = parseKey(pref);
            altDatabase.put(DEFAULT_KEY, pref);
            setKeys();
            return pref;
        }
    }
    /** Method:     getAbsFilepath
     * Description: JFileChooser gets file to be stored
     *              by user, returns absolute filepath
     *              of selected file
     *              returns null if no file selected by user
     * @return:     String absolute filepath
     */
    private String getAbsFilepath()
    {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) 
                == JFileChooser.APPROVE_OPTION)
        {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        else 
        {
            return null;
        }
    }
    /** Method:     parseKey
     * Description: Parses absolute filepath to
     *              a key (uppercase name of file, no extensions)
     * @param:      String str path to parse
     * @return:     String key of passed filepath
     */
    private static String parseKey(String str)
    {
        return str.substring(str.lastIndexOf("\\") + 1, str.lastIndexOf("."))
                .toUpperCase().replaceAll(" ", "_");
    }
    /** Method:     hasData
     * Description: Returns true if database has data,
     *              else false
     * @return:     boolean
     */
    public boolean hasData()
    {
        setKeys();
        if(altKeys.length == 0)
        {
            return false;
        }
        return true;
    }
    /** Method:     flush
     * Description: Flushes and persists any changes to node
     *              via Preferences.flush()
     * @see:        Preferences.flush()
     */
    public void flush()
    {
        try {
            altDatabase.flush();
        } catch (BackingStoreException ex) {
            Logger.getLogger(AltDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}