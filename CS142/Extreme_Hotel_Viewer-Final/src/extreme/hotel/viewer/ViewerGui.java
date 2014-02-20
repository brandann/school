/*
 * ViewerGui.java
 */
package extreme.hotel.viewer;

import Calendar.DateAD;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * ViewerGui
 * Hotel Viewer GUI
 * @version 1.0
 * @author Brandan Haertel
 * Compiler: jdk1.7.0_09
 * Tested on:   Windows 8 64bit AMD A8, 
 *              Windows 7 64bit Intel I7, 
 *              Ubuntu 12.10 64bit AMD 8
 * History:
 *      3/13/2013: Version 1.0 released to public!
 */
public class ViewerGui extends JFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       try {
            javax.swing.UIManager.LookAndFeelInfo[] installedLookAndFeels=
                    javax.swing.UIManager.getInstalledLookAndFeels();
            for (int idx=0; idx<installedLookAndFeels.length; idx++)
                if ("Nimbus".equals(installedLookAndFeels[idx].getName())) {
                    javax.swing.UIManager.setLookAndFeel(
                            installedLookAndFeels[idx].getClassName());
                    break;
                }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(
                    ViewerGui.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(
                    ViewerGui.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(
                    ViewerGui.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(
                    ViewerGui.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }

        JFrame frame = new ViewerGui();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Extreme Hotel Viewer");
        frame.setMaximumSize(null);  
    }
    
    /**
     * Main constructor - Hotel Reservation Viewer GUI.
     */
    public ViewerGui()
    {
        tempDate = new DateAD();
        
        setLayout(new BorderLayout());
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setBackground(Color.WHITE);
        initComponents();
        getRootPane().setDefaultButton(searchButton);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(new Point((screenSize.width  - FRAME_WIDTH)  / 2,
                              (screenSize.height - FRAME_HEIGHT) / 2));
        try
        {
            URL url = this.getClass().getResource(ICON);
            Image img = ImageIO.read(url);
            ImageIcon icon = new ImageIcon(img);
            setIconImage(icon.getImage());
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    /**
     * Initializes all GUI objects and Listeners.
     */
    private void  initComponents()
    {
        reservationMatches = new Reservation[0];
	databaseLoaded = false;
        altDatabase = new AltDB();
        tempDate = new DateAD();
        menuBar = new JMenuBar();
        menuFile = new JMenu();
        menuLoadDatabase = new JMenuItem();
        menuResetForm = new JMenuItem();
        menuResetDatabase = new JMenuItem();
        menuSave = new JMenuItem();
        menuClose = new JMenuItem();
        menuLoadPreviousDatabase = new JMenuItem();
        gutsPanel = new JPanel();
        searchPanel = new JPanel();
        criteriaPanel = new JPanel();
        matchPanel = new JPanel();
        butterPanel = new JPanel();
        blankPanel1 = new JPanel();
        titlePanel = new JPanel();
        namePanel = new JPanel();
        searchTextField = new JTextField("",TEXT_FIELD_WIDTH);
        criteriaComboBox = new JComboBox(criteria);
        searchButton = new JButton();
        titleLabel = new JLabel();
        matchLabel = new JLabel();
        nameLabel = new JLabel();
        dayComboBox = new JComboBox(getDayArray());
        monthComboBox = new JComboBox(months);
        yearComboBox = new JComboBox(getYearArray());
        rand = new Random();
        
        //set Panels
        gutsPanel.setLayout(new GridLayout(GRID_ROWS,GRID_COLUMNS));
        criteriaPanel.setLayout(new FlowLayout());
        titlePanel.setLayout(new FlowLayout());
        searchPanel.setLayout(new FlowLayout());
        matchPanel.setLayout(new FlowLayout());
        butterPanel.setLayout(new BorderLayout());
        namePanel.setLayout(new BorderLayout());
        
        //set Panels to Background Color = WHITE
        gutsPanel.setBackground(Color.WHITE);
        criteriaPanel.setBackground(Color.WHITE);
        titlePanel.setBackground(Color.WHITE);
        searchPanel.setBackground(Color.WHITE);
        matchPanel.setBackground(Color.WHITE);
        butterPanel.setBackground(Color.WHITE);
        namePanel.setBackground(Color.WHITE);
        blankPanel1.setBackground(Color.WHITE);
        
        //Set file menu
        menuFile.setText("File");
        menuFile.setFont(new Font(FONT, Font.BOLD, FONT_SMALL));
        
        //set load database menu item
        menuLoadDatabase.setText("Load Database");
        menuLoadDatabase.setFont(new Font(FONT, Font.BOLD, FONT_SMALL));
        menuLoadDatabase.addActionListener(new java.awt.event.ActionListener(){
            /**
             * Action listener handles the load database menu option
             * loads database file.
             */
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                File selectedFile = altDatabase.getUserFile();
                if(selectedFile != null)
                {
                    menuLoadPreviousDatabase.setEnabled(true);
                    menuResetDatabase.setEnabled(true);
                    loadDatabaseFile(selectedFile);
                }
            }
        });
        
        if(!altDatabase.hasData())
        {
            menuLoadPreviousDatabase.setEnabled(false);
            menuResetDatabase.setEnabled(false);
            altDatabase.reset();
        }
        menuLoadPreviousDatabase.setText("Previous User Database");
        menuLoadPreviousDatabase.setToolTipText("Loads the last user database"
                + " selected");
        menuLoadPreviousDatabase.setFont(new Font(FONT, Font.BOLD, FONT_SMALL));
        menuLoadPreviousDatabase.addActionListener(
                new java.awt.event.ActionListener()
                {
                    /**
                     * Action listener handles the altDatabase menu option
                     * loads altDatabase file.
                     */
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        loadDatabaseFile(
                                new File(altDatabase.getPreference(
                                altDatabase.DEFAULT_KEY)));
                    }
                });
        
        menuResetDatabase.setText("Reset Database");
        menuResetDatabase.setFont(new Font(FONT, Font.BOLD, FONT_SMALL));
        menuResetDatabase.addActionListener(new java.awt.event.ActionListener(){
            /**
             * Action listener handles the altDatabse Reset menu option
             * Loads database file.
             */
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                File selectedFile = altDatabase.getUserFile();
                if(selectedFile != null)
                {
                    menuLoadPreviousDatabase.setEnabled(false);
                    menuResetDatabase.setEnabled(false);
                    loadDatabaseFile(selectedFile);
                }
            }
        });
        
        //set reset menu item
        menuResetForm.setText("Reset Form");
        menuResetForm.setFont(new Font(FONT, Font.BOLD, FONT_SMALL));
        menuResetForm.addActionListener(new java.awt.event.ActionListener() {
            /**
             * Action listener handles Resting the form menu option.
             */
            @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetForm();
        }});
        //outHTML
        
        menuSave.setText("Save results");
        menuSave.setFont(new Font(FONT, Font.BOLD, FONT_SMALL));
        menuSave.addActionListener(new java.awt.event.ActionListener(){
            /**
             * Action Listener handles HTML Output menu option
             * creates a new Instance Of ViewerOutputHTML.
             */
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                if(reservations.length > 0)
                {
                    outHTML = new ViewerOutputHTML(reservationMatches);
                }
            }
        });
        
        //set close menu item
        menuClose.setText("Close");
        menuClose.setFont(new Font(FONT, Font.BOLD, FONT_SMALL));
        menuClose.addActionListener(new java.awt.event.ActionListener() {
            /**
             * Action Listener Handles Close menu option
             * closes the form.
             */
            @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
        }});
        
        //set menubar to form
        menuFile.add(menuLoadDatabase);
        menuFile.add(menuLoadPreviousDatabase);
        menuFile.add(menuResetDatabase);
        menuFile.add(menuResetForm);
        menuFile.add(menuSave);
        menuFile.add(menuClose);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
        
        //set blankpanel1
         gutsPanel.add(blankPanel1);
        
        //set title label
        titleLabel.setText("Extreme Hotel Lookup");
        titleLabel.setFont(new Font(
                FONT, Font.BOLD, FONT_LARGE));
        titlePanel.add(titleLabel);
        gutsPanel.add(titlePanel);

        //set search text field
        searchTextField.setText("");
        searchTextField.setFont(new Font(FONT, Font.BOLD, FONT_SMALL));
        searchTextField.setToolTipText("Enter a name to search");
        searchPanel.add(searchTextField);
        searchPanel.add(dayComboBox);
        searchPanel.add(monthComboBox);
        searchPanel.add(yearComboBox);
        showTextPanel();
        gutsPanel.add(searchPanel);
        searchTextField.addKeyListener(new java.awt.event.KeyListener() {
            /**
             * Action listener handles keypressed on searchTextField
             * blocks invalid values from being put into the textfield
             * dynamically searches database based on keypresses.
             */
            @Override
            public void keyTyped(KeyEvent e) {}
            public void keyPressed(KeyEvent e){
                //int tempStringLength = searchTextField.getText().length();
                for(char c = '!'; c <= '@'; c++)
                {
                    searchTextField.getInputMap().put(
                            KeyStroke.getKeyStroke(c),"none");
                }
                for(char c = '['; c <= '`'; c++)
                {
                    searchTextField.getInputMap().put(
                            KeyStroke.getKeyStroke(c),"none");
                }
                for(char c = '{'; c <= '~'; c++)
                {
                    searchTextField.getInputMap().put(
                            KeyStroke.getKeyStroke(c),"none");
                }
                nameLabel.setForeground(new Color(
                rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
                
            }
            public void keyReleased(KeyEvent e)
            {
                String searchTerm = searchTextField.getText().trim();                
                if(searchTerm.length() > 0)
                {
                    reservationMatches = search();
                }
                if(searchTerm.length() == 0)
                {
                    matchLabel.setText("" + 0 + MATCH_STRING);
                }
            }
        });

        monthComboBox.addActionListener(new java.awt.event.ActionListener() {
            /**
             * Action Listener handles MonthComboBox
             * updates the daysComboBox based on the month and year selected.
             */
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matchLabel.setText(EMPTY_STRING);
                if(databaseLoaded)
		{
                    updateDayComboBox();
		}
		else
		{
                    matchLabel.setText("Database not found");
		}
        }});
        
        yearComboBox.addActionListener(new java.awt.event.ActionListener() {
            /**
             * Action Listener handles YearComboBox
             * updates the daysComboBox based on the month and year selected.
             */
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matchLabel.setText(EMPTY_STRING);
                if(databaseLoaded)
		{
                    updateDayComboBox();
		}
		else
		{
                    matchLabel.setText("Database not found");
		}
        }});
        
        //lock the seach fields untill a database is loaded
        searchTextField.setEnabled(false);
        dayComboBox.setEnabled(false);
        monthComboBox.setEnabled(false);
        yearComboBox.setEnabled(false);
        
        //set criteria combo box
        criteriaComboBox.setFont(new Font(
                FONT, Font.PLAIN, FONT_SMALL));
        criteriaComboBox.setToolTipText("How would you like to search?");
        criteriaPanel.add(criteriaComboBox);
        criteriaComboBox.addActionListener(new java.awt.event.ActionListener(){
            /**
             * Action Listener handles criteriaComboBox
             * changes access between search by text or search by date.
             */
            @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(criteriaComboBox.getSelectedIndex() <= SEARCH_TYPE_TOKEN)
                {
                    showTextPanel();
                }
                else if(criteriaComboBox.getSelectedIndex() > SEARCH_TYPE_TOKEN)
                {
                    matchLabel.setText(EMPTY_STRING);
                    showDatePanel();
                }
        }});
        
        //set search button
        searchButton.setText("Search");
        searchButton.setFont(new Font(
                FONT, Font.BOLD, FONT_SMALL));
        searchButton.setToolTipText("Ready?");
        criteriaPanel.add(searchButton);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            /**
             * Action Listener handles searchButton
             * searches database based on current criteriaComboBox index
             * selected, makes a new search to handle and previous missed
             * results not shown on dynamic search - Also makes the search
             * for the non dynamic date search.
             */
            @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if(databaseLoaded)
            {
                if(criteriaComboBox.getSelectedIndex() <= SEARCH_TYPE_TOKEN)
                {
                    String searchTerm = searchTextField.getText().trim();
                    if(searchTerm.length() > 0)
                    {
                        reservationMatches = search();
                        if(reservationMatches.length > RESERVATIONS_MIN)
                        {
                            results = new ViewerResults(reservationMatches);
                        }
                        else
                        {
                            matchLabel.setText("Sorry, no matches were found");
                        }
                    }
                    else
                    {
                        matchLabel.setText("Sorry, no matches were found");
                    }
                }
                else if (criteriaComboBox.getSelectedIndex() > SEARCH_TYPE_TOKEN)
                {
                    reservationMatches = search();
                    if(reservationMatches.length > RESERVATIONS_MIN)
                    {
                        results = new ViewerResults(reservationMatches);
                    }
                    else
                    {
                        matchLabel.setText("Sorry, no matches were found");
                    }
                }
            }
            else
            {
                matchLabel.setText("Database not found");
            }
        }});
        
        gutsPanel.add(criteriaPanel);
        
        //set matches label
        matchLabel.setText("" + NO_MATCH + MATCH_STRING);
        matchLabel.setFont(new Font(FONT, Font.BOLD, FONT_SMALL));
        matchPanel.add(matchLabel);
        gutsPanel.add(matchPanel);
        
        //set butter image
        try
        {
            URL url = this.getClass().getResource(BUTTER);
            ImageIcon icon = new ImageIcon();
            Image img = ImageIO.read(url);
            Image newImg = img.getScaledInstance(
                    IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImg);
            JLabel image = new JLabel(icon);
            image.setToolTipText("Smooth Like Butter");
            
            butterPanel.add(image, BorderLayout.WEST);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        //set names label
        nameLabel.setText("Bryce, Brandan, Alex, Matson, Alex   ");
        nameLabel.setFont(new Font(
                FONT, Font.BOLD, FONT_SMALL));
        nameLabel.setForeground(new Color(
                rand.nextInt(RAND_COLOR_TOKEN),rand.nextInt(RAND_COLOR_TOKEN),
                rand.nextInt(RAND_COLOR_TOKEN)));
        namePanel.add(nameLabel, BorderLayout.SOUTH);
        butterPanel.add(namePanel, BorderLayout.EAST);
        
        gutsPanel.add(butterPanel);
        
        //add(butterPanel, BorderLayout.SOUTH);
        add(gutsPanel, BorderLayout.CENTER);
        
        File databaseFile = new File(databasePath);
        if(databaseFile.exists() && databaseFile.canRead())
        {
            loadDatabaseFile(databaseFile);
        }
        else
        {
            matchLabel.setText(DATABASE_ERROR);
        }
    }
    
    /**
     * Constructs an array of days for the current month.
     * @return String[] days of the current month
     */
    private String[] getDayArray()
    {
        tempDate = new DateAD();
        String[] days = new String[DateAD.daysInMonth(
                tempDate.getMonth(), tempDate.getYear())];
        for(int i = 0; i < days.length; i++)
        {
            days[i] = "" + (i + 1);
        }
        return days;
    }
    
    /**
     * Constructs an array of years beginning from the current year.
     * @return String[] years from the current year
     */
    private String[] getYearArray()
    {
        tempDate = new DateAD();
        String[] years = new String[YEARS_TOKEN];
        for(int i = 0; i < years.length; i++)
        {
            years[i] = "" + (i + tempDate.getYear());
        }
        return years;
    }
    
    /**
     * updates the days combo box based on what month/year is selected.
     */
    private void updateDayComboBox()
    {
        int previousDay = dayComboBox.getSelectedIndex();
        short month = (short) monthComboBox.getSelectedIndex();
        short year = Short.valueOf((String)yearComboBox.getSelectedItem());
        int daysInMonth = tempDate.daysInMonth(month, year);
        dayComboBox.removeAllItems();
        
        for(int i = 0; i < daysInMonth; i++)
        {
            dayComboBox.addItem(i + 1);
        }
        
        if(previousDay > daysInMonth)
        {
            dayComboBox.setSelectedIndex(daysInMonth - 1);
        }
        else
        {
            dayComboBox.setSelectedIndex(previousDay);
        }
    }
    
    /**
     * searches for reservations based on type of search.
     * @return Reservation[] of matching results.
     */
    private Reservation[] search()
    {
        try
        {
            if(criteriaComboBox.getSelectedIndex() == INDEX_FULL)
            {
                filter.byName(searchTextField.getText().trim());
                reservationMatches = filter.getResults();
                filter.removeLastFilter();
            }
            else if(criteriaComboBox.getSelectedIndex() == INDEX_PART)
            {
                filter.byNameBeginsWith(searchTextField.getText().trim());
                reservationMatches = filter.getResults();
                filter.removeLastFilter();
            }
            else if(criteriaComboBox.getSelectedIndex() == INDEX_ARRIVAL)
            {
                filter.byArrivalDate(new DateAD(
                    (short) (dayComboBox.getSelectedIndex() + 1),
                    (short) monthComboBox.getSelectedIndex(),
                    Short.valueOf((String)yearComboBox.getSelectedItem())));
                reservationMatches = filter.getResults();
                filter.removeLastFilter();
            }
            else if(criteriaComboBox.getSelectedIndex() == INDEX_DEPARTURE)
            {
                filter.byDepartureDate(new DateAD(
                    (short) (dayComboBox.getSelectedIndex() + 1),
                    (short) monthComboBox.getSelectedIndex(),
                    Short.valueOf((String)yearComboBox.getSelectedItem())));
                reservationMatches = filter.getResults();
                filter.removeLastFilter();
            }
            if(!(reservationMatches.length > RESERVATIONS_MIN))
            {
                reservationMatches = new Reservation[0];
            }
            matchLabel.setText(reservationMatches.length + MATCH_STRING);
            return reservationMatches;
            }
        catch(Exception e)
        {
            matchLabel.setText("Name not Found");
            return reservationMatches = new Reservation[0];
        }
    }
    
    /**
     * Toggles search fields
     * Hides comboBoxes and shows textField.
     */
    private void showTextPanel()
    {
        dayComboBox.setVisible(false);
        monthComboBox.setVisible(false);
        yearComboBox.setVisible(false);
        searchTextField.setVisible(true);
    }
    
    /**
     * Toggles search fields
     * Hides textField and shows comboBoxes.
     */
    private void showDatePanel()
    {
        searchTextField.setVisible(false);
        dayComboBox.setVisible(true);
        monthComboBox.setVisible(true);
        yearComboBox.setVisible(true);
    }
   
    /**
     * Resets the form back to default state.
     */
    private void resetForm()
    {
        matchLabel.setText("" + 0 + MATCH_STRING);
        criteriaComboBox.setSelectedIndex(0);
        searchTextField.setText("");
        dayComboBox.setSelectedIndex(tempDate.getDayOfMonth() - 1);
        monthComboBox.setSelectedIndex(tempDate.getMonth());
        yearComboBox.setSelectedIndex(0);
        showTextPanel();
    }
    
    /**
     * verifies file exists and can read
     * sets driver database to selected file
     * @param selectedFile database file
     */
    private void loadDatabaseFile(File selectedFile)
    {
        try 
        {
            if ((selectedFile.exists() & selectedFile.canRead()) && 
                    (selectedFile.getName().contains(".dat"))) 
            {
                //driver.loadDatabase(selectedFile);
                getDatabase(selectedFile);
                filter = new ExtremeFilter(reservations);
                databaseLoaded = true;
                matchLabel.setText(EMPTY_STRING);
                searchTextField.setEnabled(true);
                dayComboBox.setEnabled(true);
                monthComboBox.setEnabled(true);
                yearComboBox.setEnabled(true);
                databaseFile = selectedFile;
                resetForm();
            }
            else
            {
                //Show error message
                matchLabel.setText(DATABASE_ERROR);
		databaseLoaded = false;
                searchTextField.setEnabled(false);
                dayComboBox.setEnabled(false);
                monthComboBox.setEnabled(false);
                yearComboBox.setEnabled(false);
            }
        }
        catch (Exception ex) 
        {
            //Show error message
            matchLabel.setText(DATABASE_ERROR);
            databaseLoaded = false;
            searchTextField.setEnabled(false);
            dayComboBox.setEnabled(false);
            monthComboBox.setEnabled(false);
            yearComboBox.setEnabled(false);
        }
    }
    
        /**
     * loads the database into an Arraylist for processing.
     * @param path database file path
     * @throws ClassNotFoundException
     * @throws IOException 
     */
    private void getDatabase(File file) 
            throws ClassNotFoundException, IOException
    {
        ObjectInputStream in;
        Reservation[] temp = null;
        ArrayList<Reservation> databaseReservationList = new ArrayList();
        Object temp2 = null;
        
        if(file.exists() && file.canRead())
        {
            in = new ObjectInputStream(new FileInputStream(file));
        
            try
            {
                temp2 = in.readObject();
                temp = (Reservation[]) temp2;

                in.close();

                if(temp != null)
                {
                    for(int i = 0; i < temp.length; i++)
                    {
                        databaseReservationList.add(temp[i]);
                    }
                }
                reservations = new Reservation[databaseReservationList.size()];
                reservations = databaseReservationList.toArray(reservations);
            }
            catch(IOException e)
            {
                in.close();
                reservations = new Reservation[0];
            }
        }
    }
    
    private ExtremeFilter filter;
    private DateAD tempDate;
    private ViewerResults results;
    private Reservation[] reservations;
    private Reservation[] reservationMatches;
    private AltDB altDatabase;
    private ViewerOutputHTML outHTML;
    private File databaseFile;
    private final int TEXT_FIELD_WIDTH = 30;
    private final int GRID_ROWS = 6;
    private final int GRID_COLUMNS = 1;
    private final int SEARCH_TYPE_TOKEN = 1;
    private final int RESERVATIONS_MIN = 0;
    private final int NO_MATCH = 0;
    private final int RAND_COLOR_TOKEN = 255;
    private final int YEARS_TOKEN = 20;
    private final int IMG_WIDTH = 480/4;
    private final int IMG_HEIGHT = 324/4;
    private final int FRAME_WIDTH = 600;
    private final int FRAME_HEIGHT = 500;
    private final int FONT_SMALL = 12;
    private final int FONT_LARGE = 24;
    private final int INDEX_FULL = 1;
    private final int INDEX_PART = 0;
    private final int INDEX_ARRIVAL = 2;
    private final int INDEX_DEPARTURE = 3;
    private final String EMPTY_STRING = "";
    private final String FONT = "Comic Sans MS";
    private final String MATCH_STRING = " Matches Found!";
    private final String DATABASE_ERROR = "Database File Not Found";
    private final String BUTTER = "/extreme/hotel/viewer/butter.jpg";
    private final String ICON = "/extreme/hotel/viewer/dark_skull@2x.png";
    private final String DEFAULT_DATABASE = "Reservations.dat";
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuLoadDatabase;
    private JMenuItem menuLoadPreviousDatabase;
    private JMenuItem menuResetDatabase;
    private JMenuItem menuResetForm;
    private JMenuItem menuSave;
    private JMenuItem menuClose;
    private JPanel gutsPanel;
    private JPanel searchPanel;
    private JPanel criteriaPanel;
    private JPanel matchPanel;
    private JPanel butterPanel;
    private JPanel blankPanel1;
    private JPanel titlePanel;
    private JPanel namePanel;
    private JTextField searchTextField;
    private JComboBox dayComboBox;
    private JComboBox monthComboBox;
    private JComboBox yearComboBox;
    private JComboBox criteriaComboBox;
    private JButton searchButton;
    private JLabel titleLabel;
    private JLabel matchLabel;
    private JLabel nameLabel;
    private boolean databaseLoaded;
    private Random rand;
    private final String databasePath = "Reservations.dat";
    private String[] criteria = new String[]
        {
            "Search by \"begins with\"",
            "Search by whole name",
            "Search by Arrival Date",
            "Search by Departure Date"
        };
    private String[] months = new String[]
        {
            "January", "Feburary", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"
        };
}