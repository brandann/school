/*
 * WordJumbler.java
 */
package wordjumbler;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * WordJumbler Gui Builder.<br>
 * WordJumbler takes a random word from a dictionary file, jumbles it<br>
 * and displays the scrammbled word to the user to guess the correct word.
 *
 * @version 1.0 <br />
 * @author Brandan Haertel<br />
 * 
 * Test Environment: JDK 1.7.0_03 on Windows 7, Intel i7 CPU <br>
 * Test Environment: JDK 1.7.0_03 on Windows 8, AMD A8 CPU <br>
 * 
 * Completed: Feburary 11, 2013<br>
 */
public class WordJumbler extends JFrame
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Try to set the Project "Look and Feel" to "Nimbus"
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
            java.util.logging.Logger.getLogger(WordJumbler.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WordJumbler.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WordJumbler.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WordJumbler.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }

        JFrame frame = new WordJumbler();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Word Jumbler ~ Brandan Haertel");
    }
    /**
     * WordJumbler Default Constructor
     */
    public WordJumbler() {
        setLayout(new BorderLayout());
        setSize(FRAME_WIDTH, FRAME_HEIGHT);

        initComponents();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(new Point((screenSize.width  - FRAME_WIDTH)  / 2,
                              (screenSize.height - FRAME_HEIGHT) / 2));
        
        add(gamePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    /**
     * Initiate Form Components
     */
    private void initComponents() {
        wordDriver = new WordJumblerDriver();
        
        gamePanel = new JPanel();
        scrambledWordPanel = new JPanel();
        dictionaryPanel = new JPanel();
        notificationPanel = new JPanel();
        gameLabelPanel = new JPanel();
        comboBoxPanel = new JPanel();
        buttonPanel = new JPanel();
        bottomPanel = new JPanel();
        menuBar = new JMenuBar();
        menuFile = new JMenu();
        menuOpen = new JMenuItem();
        dictionaryLabel = new JLabel();
        scrambledWordLabel = new JLabel();
        solutionsLabel = new JLabel();
        notificationLabel = new JLabel();
        charComboBox = new JComboBox[0];
        getJumbleButton = new JButton();
        testAnswerButton = new JButton();
        seeAnswerButton = new JButton();
        
        menuFile.setText("File");
        menuFile.setFont(new Font(EMPTY_STRING, Font.BOLD, FONT_SMALL));
        menuOpen.setText("Open Dictionary");
        menuOpen.setFont(new Font(EMPTY_STRING, Font.BOLD, FONT_SMALL));
        menuFile.add(menuOpen);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
        menuOpen.addActionListener(new java.awt.event.ActionListener() {
            @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(null) 
                        == JFileChooser.APPROVE_OPTION)
                {
                    File selectedFile = chooser.getSelectedFile();
                    loadDictionaryFile(selectedFile);
                }
        }});

        // Set up scrambled word label & panel
        dictionaryLabel.setText(EMPTY_STRING);
        dictionaryLabel.setFont(new Font(
                EMPTY_STRING, Font.ITALIC, FONT_SMALL));
        dictionaryLabel.setForeground(Color.GRAY);
        dictionaryPanel.setLayout(new FlowLayout());
        dictionaryPanel.add(dictionaryLabel);
        dictionaryPanel.setBackground(backgroundColor);
        
        // Set up scrambled word label & panel
        scrambledWordLabel.setText(EMPTY_STRING);
        scrambledWordLabel.setFont(new Font(
                EMPTY_STRING, Font.BOLD, FONT_LARGE));
        scrambledWordLabel.setForeground(Color.BLUE);
        scrambledWordPanel.setLayout(new BorderLayout());
        scrambledWordPanel.add(scrambledWordLabel, BorderLayout.WEST);
        scrambledWordPanel.setBackground(backgroundColor);
        
        // Set up solutions label and add to panel
        solutionsLabel.setText(EMPTY_STRING);
        solutionsLabel.setFont(new Font(
                EMPTY_STRING, Font.BOLD, FONT_SMALL));
        dictionaryPanel.add(solutionsLabel);
        
        // Set up game label panel and add other panels to it
        gameLabelPanel.setLayout(new BorderLayout());
        gameLabelPanel.add(dictionaryPanel, BorderLayout.NORTH);
        gameLabelPanel.add(scrambledWordPanel, BorderLayout.SOUTH);
        
        // Set up notification label & panel
        notificationLabel.setText(EMPTY_STRING);
        notificationLabel.setFont(new Font(
                EMPTY_STRING, Font.BOLD, FONT_SMALL));
        notificationLabel.setToolTipText(NOTIFICATION_TOOL_TIP);
        notificationPanel.setLayout(new BorderLayout());
        notificationPanel.add(notificationLabel, BorderLayout.WEST);
        notificationPanel.setBackground(backgroundColor);
        
        // Set up get new jumbled word button
        getJumbleButton.setMnemonic(GET_JUMBLE_BUTTON_CHAR);
        getJumbleButton.setText(GET_JUMBLE_BUTTON_TEXT);	
        getJumbleButton.setToolTipText(GET_JUMBLE_BUTTON_TOOL_TIP);
        getJumbleButton.addActionListener(new java.awt.event.ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt){
                setResetgame();
                wordDriver.setNewWord();
                String newWord = wordDriver.getScrambledWord(
                        wordDriver.getWord());
                scrambledWordLabel.setText(newWord);
                setGameComboBoxes(newWord);
                String[] wordPermutations 
                        = wordDriver.getPossibleSolutions(newWord);
        
                solutionsLabel.setText(
                        "Number of solutioins: " + wordPermutations.length);
        }});
        buttonPanel.add(getJumbleButton);
        
        // Set up test answer button
        testAnswerButton.setMnemonic(TEST_ANSWER_BUTTON_CHAR);
        testAnswerButton.setText(TEST_ANSWER_TEXT);	
        testAnswerButton.setToolTipText(TEST_ANSWER_BUTTON_TOOL_TIP);
        testAnswerButton.addActionListener(new java.awt.event.ActionListener()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(!(scrambledWordLabel.getText() == ""))
                {
                    notificationLabel.setText(
                            wordDriver.isGuessCorrect(getGuess()));
                }
                else
                {
                    notificationLabel.setText(
                            "I strongly recomend that you press "
                            + "\"Get Jumble\" first...");
                }
                
        }});
        buttonPanel.add(testAnswerButton);
        
        // Set up see answer button
        seeAnswerButton.setMnemonic(SEE_ANSWER_BUTTON_CHAR);
        seeAnswerButton.setText(SEE_ANSWER_TEXT);	
        seeAnswerButton.setToolTipText(SEE_ANSWER_BUTTON_TOOL_TIP);
        seeAnswerButton.addActionListener(new java.awt.event.ActionListener()
        {
            @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                if(!(scrambledWordLabel.getText() == ""))
                {
                    String[] permutationMatchArray 
                       = wordDriver.getPossibleSolutions
                            (wordDriver.getWord());
                    System.out.println("Permutations: " 
                            + permutationMatchArray.length);
                    String list = "";
                    for(int i = 0; i < permutationMatchArray.length; i++)
                    {
                        list = list + ", " + permutationMatchArray[i];
                    }
                    notificationLabel.setText(list.substring
                            (2, list.length()));
                }
                else
                {
                    notificationLabel.setText(
                            "You should probably press "
                            + "\"Get Jumble\" first...");
                }
                
        }});
        buttonPanel.add(seeAnswerButton);

        // Confiqure Game Panel
        gamePanel.setLayout(new BorderLayout());
        gamePanel.add(gameLabelPanel, BorderLayout.NORTH);
        gamePanel.add(comboBoxPanel, BorderLayout.CENTER);
        
        // Confiqure Bottom Panel
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(notificationPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        loadDictionaryFile(new File(dictionaryFile));

    }
    
    /**
     * LoadDictionary from this file
     * @param myFile dat file.
     */
    private void loadDictionaryFile(File myFile) {
        //if(myFile.toString().contains(FILE_TYPE))
        //{
            try {

                if (myFile.exists() & myFile.canRead()) 
                { 
                    wordDriver.setDictionary(myFile);
                    dictionaryFile = myFile.toString();
                    setResetgame();
                }
                else { setLockGame(); }
            } catch (Exception ex) {
                setLockGame();
                dictionaryFile = EMPTY_STRING;
                System.out.println("WordJumbler//loadDictionaryFile");
                System.out.println(ex);
            }
        //}
    }   //Done

    /**
     * Builds combo boxes and combo box arrays, and displays them.
     * @param myWord String of game word, or scrambled word.
     */
    public void setGameComboBoxes(String myWord) {
        charComboBox = new JComboBox[myWord.length()];
        comboBoxPanel = new JPanel();
        
        String[] sampleArray = new String[3];
        sampleArray[0] = " ";
        sampleArray[1] = "2";
        sampleArray[2] = "3";
        for (int i = 0; i < myWord.length(); i++)
        {
            charComboBox[i] = new JComboBox(sampleArray);
            charComboBox[i] 
                    = new JComboBox(wordDriver.getWordRotationArray(i));
            comboBoxPanel.add(charComboBox[i]);
        }
        
        gamePanel.add(comboBoxPanel, BorderLayout.CENTER);
    }   //Done
    
    /**
     * Lock the form if a dictionary is not available then the game locks.
     */
    public void setLockGame() {
        getJumbleButton.setEnabled(false);
        testAnswerButton.setEnabled(false);
        seeAnswerButton.setEnabled(false);
        dictionaryLabel.setText("Dictionary: " + dictionaryFile);
        notificationLabel.setText(EMPTY_STRING);
        gamePanel.remove(comboBoxPanel);
    }   //Done
    
    /**
     * Resets the form to default.
     */
    public void setResetgame() {
        getJumbleButton.setEnabled(true);
        testAnswerButton.setEnabled(true);
        seeAnswerButton.setEnabled(true);
        dictionaryLabel.setText("Dictionary: " + dictionaryFile);
        notificationLabel.setText(EMPTY_STRING);
        gamePanel.remove(comboBoxPanel);
    }   //Done
    
    /**
     * Gets the chars from the active combo boxes.
     * @return String indicating if answer is correct
     */
    public String getGuess() {
        String guess = "";
        for(int i = 0; i < charComboBox.length; i++)
        {
            if(!(charComboBox[i].getSelectedItem() == " "))
            {
                guess = guess + charComboBox[i].getSelectedItem();
            }
        }
        return guess;
    }

WordJumblerDriver wordDriver;
File dictionary;

private final int FRAME_WIDTH = 500;
private final int FRAME_HEIGHT = 220;
private final int FONT_SMALL = 10;
private final int FONT_LARGE = 15;
private final int FONT_NORMAL = 12;

private final String EMPTY_STRING = "";
private final String GET_JUMBLE_BUTTON_TEXT = "Get Jumble";
private final String TEST_ANSWER_TEXT = "Test Answer";
private final String SEE_ANSWER_TEXT = "See Answer";
private final String MISSING_DICTIONARY = "none";
private final String FILE_TYPE = ".dic";

private final char GET_JUMBLE_BUTTON_CHAR = 'G';
private final char TEST_ANSWER_BUTTON_CHAR = 'T';
private final char SEE_ANSWER_BUTTON_CHAR = 'S';

private final String GET_JUMBLE_BUTTON_TOOL_TIP 
        = "Get A New Word Problem";
private final String TEST_ANSWER_BUTTON_TOOL_TIP 
        = "Think Your Answer Is Correct? Try Pressing Me!";
private final String SEE_ANSWER_BUTTON_TOOL_TIP 
        = "Give Up? See The Solution";
private final String NOTIFICATION_TOOL_TIP 
        = "Important Game Information Is Displayed Here...";

private String dictionaryFile = "Jumble-en-US.dic";

private JPanel gamePanel;
private JPanel scrambledWordPanel;
private JPanel dictionaryPanel;
private JPanel gameLabelPanel;
private JPanel comboBoxPanel;
private JPanel notificationPanel;
private JPanel buttonPanel;
private JPanel bottomPanel;

public JMenuBar menuBar;
private JMenu menuFile;
private JMenuItem menuOpen;

private JLabel dictionaryLabel;
private JLabel scrambledWordLabel;
private JLabel solutionsLabel;
private JLabel notificationLabel;

private JComboBox[] charComboBox;

private JButton getJumbleButton;
private JButton testAnswerButton;
private JButton seeAnswerButton;

//private Color backgroundColor = new Color(0xFF3030);
private Color backgroundColor = Color.PINK;
    
}