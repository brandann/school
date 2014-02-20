/*
 * StarInYourOwnBook.java 
 */
package starinyourownbook;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.*;

/**
 * File name:   StarInYourOwnBook.java       
 * Class Name:  StarInYourOwnBook
 * Description: Main Gui for StarInYourOwnBook
 * @version     1.0
 * @author      Brandan Haertel
 * Environment: PC, Windows 8 64-bit, jdk 1.7
 * History:     04/17/2013 1.0 completed
 *              05/09/2013 1.1 completed
 *                  added CMDline options
 */
public class StarInYourOwnBook extends JFrame{

    /**
     * Main Constructs and launches StarInYourOwnBook GUI or CMD:
     * If args.length >= 3, CMDline is attempted,
     * If args.length less than 3, GUI is created.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length >= 3)
        {
            if(args.length == 3){
                runCMD(args[0],args[1],args[2],DEFAULT_FONT);
            }
            else{
                runCMD(args[0],args[1],args[2],args[3]);
            }
        }
        else
        {
            runGUI();
        }
        
        

    }
    
    /**
     * Runs the GUI constructor to graphically create your own book
     * if args is 0 or invalid runGui is instantiated.
     */
    public static void runGUI()
    {
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
                    StarInYourOwnBook.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(
                    StarInYourOwnBook.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(
                    StarInYourOwnBook.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(
                    StarInYourOwnBook.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }

        JFrame frame = new StarInYourOwnBook();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Star In Your Own Book");
    }
    
    /**
     * Runs a CMDline program version if this program, ir all parameters are
     * valid the program will automatically create the book and shut
     * down, if any parameters are invalid or incorrect for any reason
     * the program will execute the Gui version to create the book
     * @param file String of the file path of this book to be converted.
     * @param name String of person to be stared in this book.
     * @param replacedName String of the name to be replaced in the book.
     * @param font String of the "-L" for the optional font size.
     */
    public static void runCMD(  String file, 
                                String name, 
                                String replacedName, 
                                String font)
    {
        File filePath = new File(file);
        int fontSize = SMALL_FONT_INT;
        if(filePath.exists() && filePath.canRead())
        {
            driver = new BookDriver(filePath);
            if(name.equalsIgnoreCase(replacedName))
            {
                runGUI();
            }
            else if(!driver.doesNameExist(replacedName))
            {
                runGUI();
            }
            else
            {
                driver.setNewName(name);
                driver.setOldName(replacedName);
                if(font.toUpperCase().trim().equals(LARGE_TOKEN))
                    {fontSize = LARGE_FONT_INT;}
                driver.setFontSize(String.valueOf(fontSize));
                if(driver.makeBook())
                {
                    //message showing sucessfull book
                    driver.infoBox("Your book was successfully created."
                            + "\nFile Name: " 
                            + driver.getRevisedFileName()
                            + "\nLines In Original File: " 
                            + driver.getOriginalFileLength()
                            + "\nNumber Of Paragraphs: " 
                            + driver.getParagraphCount()
                            + "\nName Substitutions: " 
                            + driver.getNameReplacments()
                            + "\nParagraphs With Substitutions: " 
                            + driver.getParagraphReplacmentCount()
                            ,"Congratulations!");
                    driver.resetCount();
                            try
                            {
                                //Executes a file
                                Desktop d = Desktop.getDesktop();
                                d.open(new File(
                                        driver.getRevisedFileName()));
                            }
                            catch(Exception e)
                            {
                                System.out.println(
                                        "Error: StarInYourOwnBook."
                                        + "FileExecutionMethod");
                            }
                    }
            }
            
            
        }
        else
        {
            runGUI();
        }
    }
    
    /**
     * Main constructor - Star In Your Own Book GUI.
     */
    public StarInYourOwnBook(){
        setLayout(new BorderLayout());
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setBackground(Color.WHITE);
        init();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(new Point((screenSize.width  - FRAME_WIDTH)  / 2,
                              (screenSize.height - FRAME_HEIGHT) / 2));
    }
    
    /**
     * Initializes all GUI objects and Listeners.
     */
    public void init(){
        
        //Init
        menuBar = new JMenuBar();
        menuFile = new JMenu();
        menuLoadFile = new JMenuItem();
        menuClose = new JMenuItem();
        okButton = new JButton("OK");
        exitButton = new JButton("Exit");
        newnameTextField = new JTextField(EMPTY_STRING,TEXT_FIELD_WIDTH);
        oldnameTextField = new JTextField(EMPTY_STRING,TEXT_FIELD_WIDTH);
        fontComboBox = new JComboBox(TEXT_SIZE);
        resultsTextArea = new JTextArea(TEXT_FIELD_WIDTH,TEXT_AREA_HEIGHT);
        openCheckBox = new JCheckBox("Open HTML Automatically");
        headerLabel = new JLabel("Star In Your Own Book!");
        subHeaderLabel = new JLabel(FILE_NOT_LOADED);
        fileLabel = new JLabel("Please Choose a File");
        newnameLabel = new JLabel("Name To Use:");
        oldnameLabel = new JLabel("Name To Replace:");
        fontLabel = new JLabel("Font Size:");
        resultsLabel = new JLabel("Results");
        headerPanel = new JPanel(new GridLayout(2,1));
        termsPanel = new JPanel(new GridLayout(4,2));
        buttonPanel = new JPanel();
        headerLabelPanel = new JPanel(new FlowLayout());
        subHeaderLabelPanel = new JPanel(new FlowLayout());
        newNameLabelPanel = new JPanel(new FlowLayout());
        newNameTextPanel = new JPanel(new FlowLayout());
        oldNameLabelPanel = new JPanel(new FlowLayout());
        oldNameTextPanel = new JPanel(new FlowLayout());
        fontSizeLabelPanel = new JPanel(new FlowLayout());
        fontSizeComboPanel = new JPanel(new FlowLayout());
        
        headerPanel.setBackground(BACKGROUND_COLOR);
        termsPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBackground(BACKGROUND_COLOR);
        newNameLabelPanel.setBackground(BACKGROUND_COLOR);
        newNameTextPanel.setBackground(BACKGROUND_COLOR);
        oldNameLabelPanel.setBackground(BACKGROUND_COLOR);
        oldNameTextPanel.setBackground(BACKGROUND_COLOR);
        fontSizeLabelPanel.setBackground(BACKGROUND_COLOR);
        fontSizeComboPanel.setBackground(BACKGROUND_COLOR);
        headerLabelPanel.setBackground(BACKGROUND_COLOR);
        subHeaderLabelPanel.setBackground(BACKGROUND_COLOR);
    
        //Set file menu
        menuFile.setText("File");
        menuFile.setFont(new Font(FONT, Font.BOLD, FONT_SMALL));
        
        //set load File menu item
        menuLoadFile.setText("Load Book File");
        menuLoadFile.setFont(new Font(FONT, Font.BOLD, FONT_SMALL));
        menuLoadFile.addActionListener(new java.awt.event.ActionListener(){
            /**
             * Action listener handles the load file menu option
             * loads Book file.
             */
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(null) 
                        == JFileChooser.APPROVE_OPTION)
                {
                    File selectedFile = chooser.getSelectedFile();
                    try 
                    {
                        if (selectedFile.exists() & selectedFile.canRead()) 
                        { 
                            driver = new BookDriver(selectedFile);
                            subHeaderLabel.setText(
                                    driver.getOrginalFileName());
                        }
                    }
                    catch (Exception ex) 
                    {
                        subHeaderLabel.setText(FILE_NOT_LOADED);      
                    }
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
        
        //set Labels font
        headerLabel.setFont(new Font(FONT, Font.BOLD, FONT_XLARGE));
        subHeaderLabel.setFont(new Font(FONT, Font.PLAIN, FONT_SMALL));
        newnameLabel.setFont(new Font(FONT, Font.PLAIN, FONT_SMALL));
        oldnameLabel.setFont(new Font(FONT, Font.PLAIN, FONT_SMALL));
        fontLabel.setFont(new Font(FONT, Font.PLAIN, FONT_SMALL));
        
        //set ComboBox font
        fontComboBox.setFont(new Font(FONT, Font.PLAIN, FONT_SMALL));
        
        //set TextArea Font
        newnameTextField.setFont(new Font(FONT, Font.PLAIN, FONT_SMALL));
        oldnameTextField.setFont(new Font(FONT, Font.PLAIN, FONT_SMALL));
        
        //set exit button
        exitButton.setFont(new Font(FONT, Font.BOLD, FONT_SMALL));
        exitButton.setToolTipText("Exit The Program");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            /**
             * Action Listener Handles Exit Button
             * closes the form.
             */
            @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
        }});
        
        newnameTextField.addKeyListener(new java.awt.event.KeyListener() {
            /**
             * Action listener handles Typed on oldnameTextField
             * This Method is not in use.
             */
            @Override
            public void keyTyped(KeyEvent e) {}
            /**
             * Action listener handles keypressed on oldnameTextField
             * blocks invalid values from being put into the textfield.
             */
            @Override
            public void keyPressed(KeyEvent e){
                //int tempStringLength = searchTextField.getText().length();
                for(char c = '!'; c <= '/'; c++)
                {
                    oldnameTextField.getInputMap().put(
                            KeyStroke.getKeyStroke(c),"none");
                }
                for(char c = ':'; c <= '@'; c++)
                {
                    oldnameTextField.getInputMap().put(
                            KeyStroke.getKeyStroke(c),"none");
                }
                for(char c = '['; c <= '`'; c++)
                {
                    newnameTextField.getInputMap().put(
                            KeyStroke.getKeyStroke(c),"none");
                }
                for(char c = '{'; c <= '~'; c++)
                {
                    newnameTextField.getInputMap().put(
                            KeyStroke.getKeyStroke(c),"none");
                }
            }
            /**
             * Action listener handles Released on oldnameTextField
             * This Method is not in use.
             */
            @Override
            public void keyReleased(KeyEvent e) {}
        });
                
        oldnameTextField.addKeyListener(new java.awt.event.KeyListener() {
            /**
             * Action listener handles Typed on oldnameTextField
             * This Method is not in use.
             */
            @Override
            public void keyTyped(KeyEvent e) {}
            /**
             * Action listener handles keypressed on newnameTextField
             * blocks invalid values from being put into the textfield.
             */
            @Override
            public void keyPressed(KeyEvent e){
                //int tempStringLength = searchTextField.getText().length();
                for(char c = '!'; c <= '/'; c++)
                {
                    oldnameTextField.getInputMap().put(
                            KeyStroke.getKeyStroke(c),"none");
                }
                for(char c = ':'; c <= '@'; c++)
                {
                    oldnameTextField.getInputMap().put(
                            KeyStroke.getKeyStroke(c),"none");
                }
                for(char c = '['; c <= '`'; c++)
                {
                    oldnameTextField.getInputMap().put(
                            KeyStroke.getKeyStroke(c),"none");
                }
                for(char c = '{'; c <= '~'; c++)
                {
                    oldnameTextField.getInputMap().put(
                            KeyStroke.getKeyStroke(c),"none");
                }
            }
            /**
             * Action listener handles Released on oldnameTextField
             * This Method is not in use.
             */
            @Override
            public void keyReleased(KeyEvent e) {}
        });
                
        
        //set OK button
        okButton.setFont(new Font(FONT, Font.BOLD, FONT_SMALL));
        okButton.setToolTipText("Make My Book");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            /**
             * Action Listener handles OK Button
             * Varifys that a book file is loaded, the new name inserted
             * into the book does not match the name being replaced,
             * verifys the name being replaced exists in the book<br>
             * Sets the BookDriver properties (names/fontSize)<br>
             * Creates the book<br>
             * if any information is missing or errors occur, message boxes
             * will inform the user.
             */
            @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                boolean passed = true;
                String newName = newnameTextField.getText().trim();
                String oldName = oldnameTextField.getText().trim();
               
                if(subHeaderLabel.getText().equals(FILE_NOT_LOADED))
                {
                    //message showing blank cells found
                    driver.errorBox("Please select a book file."
                            ,"Missing File");
                    passed = false;
                }
                else if((newName.isEmpty() || newName.equals("") || 
                        (oldName.isEmpty() || oldName.equals(""))))
                {
                    //message showing blank cells found
                    driver.errorBox("One or more text fields are empty.\n"
                            + "Make sure BOTH fields are filled in."
                            , "Empty Field");
                    passed = false;
                }
                else if(newName.equalsIgnoreCase(oldName))
                {
                    //message showing names cannot match
                    driver.errorBox("Your name cannot match the name you are"
                            + " replacing.", "Name Match");
                    passed = false;
                }
                /* Finds if the name is in the book already - Removed for now.
                else if(driver.doesNameExist(newName))
                {
                    driver.errorBox("Your name cannot be a characters name"
                            + " in the book.", "Name Already Exists");
                    passed = false;
                }
                */
                else if(!driver.doesNameExist(oldName))
                {
                    driver.errorBox("The name you wish to replace does "
                            + "not exist in the book, Sorry."
                            , "Name Does Not Exist");
                    passed = false;
                }

                if(passed)
                {
                    driver.setNewName(newName);
                    driver.setOldName(oldName);
                    driver.setFontSize(String.valueOf(
                            fontComboBox.getSelectedItem()));
                    
                    if(driver.makeBook())
                    {
                        //message showing sucessfull book
                        driver.infoBox("Your book was successfully created."
                                + "\nFile Name: " 
                                + driver.getRevisedFileName()
                                + "\nLines In Original File: " 
                                + driver.getOriginalFileLength()
                                + "\nNumber Of Paragraphs: " 
                                + driver.getParagraphCount()
                                + "\nName Substitutions: " 
                                + driver.getNameReplacments()
                                + "\nParagraphs With Substitutions: " 
                                + driver.getParagraphReplacmentCount()
                                ,"Congratulations!");
                        driver.resetCount();
                        if(openCheckBox.isSelected())
                        {
                            try
                            {
                                //Executes a file
                                Desktop d = Desktop.getDesktop();
                                d.open(new File(
                                        driver.getRevisedFileName()));
                            }
                            catch(Exception e)
                            {
                                System.out.println(
                                        "Error: StarInYourOwnBook."
                                        + "FileExecutionMethod");
                            }
                        }
                    }
                }
        }});
        
        //set Panels to form
        headerLabelPanel.add(headerLabel);
        subHeaderLabelPanel.add(subHeaderLabel);
        headerPanel.add(headerLabelPanel);
        headerPanel.add(subHeaderLabelPanel);
        newNameLabelPanel.add(newnameLabel);
        newNameTextPanel.add(newnameTextField);
        oldNameLabelPanel.add(oldnameLabel);
        oldNameTextPanel.add(oldnameTextField);
        fontSizeLabelPanel.add(fontLabel);
        fontSizeComboPanel.add(fontComboBox);
        termsPanel.add(newNameLabelPanel);
        termsPanel.add(newNameTextPanel);
        termsPanel.add(oldNameLabelPanel);
        termsPanel.add(oldNameTextPanel);
        termsPanel.add(fontSizeLabelPanel);
        termsPanel.add(fontSizeComboPanel);
        termsPanel.add(new JLabel(""));
        termsPanel.add(openCheckBox);
        buttonPanel.add(exitButton);
        buttonPanel.add(okButton);
        
        //set menubar to form
        menuFile.add(menuLoadFile);
        menuFile.add(menuClose);
        menuBar.add(menuFile);
        
        setJMenuBar(menuBar);
        add(headerPanel, BorderLayout.NORTH);
        add(termsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
    }
    static BookDriver driver;
    private final Color BACKGROUND_COLOR = new Color(0xffffff);
    private final String FONT = "Comic Sans MS";
    private final String EMPTY_STRING = "";
    private final String FILE_NOT_LOADED = "File Not Loaded";
    private final int FONT_SMALL = 12;
    private final int FONT_XLARGE = 22;
    private final int TEXT_FIELD_WIDTH = 20;
    private final int TEXT_AREA_HEIGHT = 0;
    private final int FRAME_HEIGHT = 400;
    private final int FRAME_WIDTH = 400;
    private final String[] TEXT_SIZE = new String[]{"14","18"};
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuLoadFile;
    private JMenuItem menuClose;
    private JPanel headerPanel;
    private JPanel termsPanel;
    private JPanel buttonPanel;
    private JPanel newNameLabelPanel;
    private JPanel newNameTextPanel;
    private JPanel oldNameLabelPanel;
    private JPanel oldNameTextPanel;
    private JPanel fontSizeLabelPanel;
    private JPanel fontSizeComboPanel;
    private JPanel headerLabelPanel;
    private JPanel subHeaderLabelPanel;
    private JButton okButton;
    private JButton exitButton;
    private JTextField newnameTextField;
    private JTextField oldnameTextField;
    private JComboBox fontComboBox;
    private JTextArea resultsTextArea;
    private JCheckBox openCheckBox;
    private JLabel headerLabel;
    private JLabel subHeaderLabel;
    private JLabel fileLabel;
    private JLabel newnameLabel;
    private JLabel oldnameLabel;
    private JLabel fontLabel;
    private JLabel resultsLabel;
    
    private String name;
    private String replacedName;
    private String font;
    private static final String LARGE_TOKEN = "-L";
    private static final int LARGE_FONT_INT = 18;
    private static final int SMALL_FONT_INT = 14;
    private static final String DEFAULT_FONT = "14";
    private String fileName;
}
