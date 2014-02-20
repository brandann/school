package extreme.hotel.viewer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 * Result.java
 * Result
 * Hotel Viewer search result GUI
 * @version 1.0
 * @author Matson Ovstedal
 * Compiler: jdk1.7.0_09
 * Tested on:   Windows Vista 64bit, 
 *              Windows 7 64bit Intel I7, 
 * History: 
 *         3/13/2013: Version 1.0 released to public!
 */
public class ViewerResults extends JFrame
{
    private Reservation[] display;
    private final String CARD1 = "nameCard";
    private final String CARD2 = "arrivalCard";
    private final String CARD3 = "departureCard";
    CardLayout card= new CardLayout();
    /**
     * Overridden constructor, builds JFrame and contained components. 
     * Instantiates and adds action listeners. Populates result view cards
     * with accordingly sorted Reservation arrays.
     * @param res Reservation array to be displayed
     */
    public ViewerResults(Reservation[] res )
    {
        display = res;
        setVisible(true);
        setLayout( new BorderLayout() );
        setTitle("Search Results");
        setResizable(false);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(new Point((screenSize.width  - FRAME_WIDTH)  / 2,
                              (screenSize.height - FRAME_HEIGHT) / 2));
        //Make action listeners
        /**
         * exitListener
         * Action listener of exit button. Exits on close.
         */
        class exitListener implements ActionListener
        {
            @Override 
            /**
             * Exit program on click event.
             * @param event mouse click on exitButton.
             * @Override
             */
            public void actionPerformed(ActionEvent event)
            {
              exit();
            }   
        }
        /**
         * nameListener
         * Shows CARD1, results in order of name
         */
        class nameListener implements ActionListener
        {
            @Override 
            /**
             * Displays CARD1 with results ordered by name.
             * @param event mouse click on exitButton.
             * @Override
             */
            public void actionPerformed(ActionEvent event)
            {
                card.show(cardPanel, CARD1);
            }   
        }
        /**
         * arrivalListener
         * Shows CARD2, results in order of arrival date.
         */
        class arrivalListener implements ActionListener
        {
            @Override 
            /**
             * Displays CARD2 with results ordered by arrival date.
             * @param event mouse click on arrivalButton.
             * @Override
             */
            public void actionPerformed(ActionEvent event)
            {
                card.show(cardPanel, CARD2);
            }   
        }
        /**
         * departureListener
         * Shows CARD3, results in order of departure date.
         */
        class departureListener implements ActionListener
        {
            @Override 
            /**
             * Displays CARD3 with results ordered by departure date.
             * @param event mouse click on departurelButton.
             * @Override
             */
            public void actionPerformed(ActionEvent event)
            {
                card.show(cardPanel, CARD3);
            }   
        }
        //Build everything with build methods
        buildArrival();
        buildName();
        buildDeparture();
        buildCardHolder();
        buildButtons();
        
        //Add buttons to NORTH
        this.add(buttonPanel, BorderLayout.SOUTH );
        //Add carholder to, apply CardLayout.
        this.add(cardPanel, BorderLayout.CENTER);
        cardPanel.setLayout(card);
        //Add all panels to cardlayout
        cardPanel.add(namePanel, CARD1 );
        cardPanel.add(arrivalPanel, CARD2 );
        cardPanel.add(departurePanel, CARD3 );
        //Add action listeners
        nameButton.addActionListener( new nameListener() );
        arrivalButton.addActionListener( new arrivalListener() );
        departureButton.addActionListener( new departureListener() );
        exitButton.addActionListener( new exitListener() );
        //Default display in name order
        nameTextArea.setText( writer() );
        ExtremeSorts.quickSortByArrivalDate(display);
        arrivalTextArea.setText( writer() );
        ExtremeSorts.quickSortByDepartureDate(display);
        departureTextArea.setText( writer() );
        //Set default frame button
        this.getRootPane().setDefaultButton(exitButton);
    }
    /**
     * Turns display, the specified Reservation array to be displayed,
     * and turns in into a String displayable in the JTextArea's
     * @return written Reservation array search result in String form.
     */
    private String writer()
    {
        String written = "";
        
        for ( int i =0; i < display.length; i++)
        {
            written +=  display[i].toString()  + "\n";
        }
        return written;
    }
    /**
     * Hides this instance of Result, ends run.
     */
    private void exit()
    {
        this.setVisible(false);
        this.dispose();
    }
    /**
     * Builds buttonPanel as well as components that will populate display area,
     * adds GridLayout.
     */
    private void buildButtons()
    {
        buttonPanel = new JPanel();
        nameButton = new JButton("Name");
        arrivalButton = new JButton("Arrival");
        departureButton = new JButton("Departure");
        exitButton =  new JButton("Close");
        displayLabel = new JLabel("Display results by:");
        blankLabel = new JLabel();
        GridLayout grid = new GridLayout(2,3);
        grid.setVgap(BUTTON_VGAP);
        grid.setVgap(BUTTON_HGAP);
        buttonPanel.setLayout(grid);
        //Assign display values
        buttonPanel.add(displayLabel);
        buttonPanel.add(nameButton);
        buttonPanel.add(arrivalButton);
        buttonPanel.add(blankLabel);
        buttonPanel.add(departureButton);
        buttonPanel.add(exitButton);
    }
    /**
     * Builds namePanel and populating components, adds to GridLayout
     */
    private void buildName()
    {
        namePanel = new JPanel(new GridLayout(1,1));
        nameTextArea  = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(nameTextArea);
        namePanel.add(scrollPane);
        nameTextArea.setEditable(false);
    }
    /**
     * Builds arrivalPanel and populating components, adds to GridLayout.
     */
    private void buildArrival()
    {
        arrivalPanel = new JPanel(new GridLayout(1,1));
        arrivalTextArea  = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(arrivalTextArea);
        arrivalPanel.add(scrollPane);
        arrivalTextArea.setEditable(false);
    }
    /**
     * Builds departurePanel and populating components, adds to GridLayout.
     */
    private void buildDeparture()
    {
        departurePanel = new JPanel(new GridLayout(1,1));
        departureTextArea  = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(departureTextArea);
        departurePanel.add(scrollPane);
        departureTextArea.setEditable(false);
    }
    /**
     * Builds cardPanel.
     */
    private void buildCardHolder()
    {
        cardPanel = new JPanel();
    }
    //Initialize components
    //Initialize and assign constants
    private final int FRAME_HEIGHT = 500;
    private final int FRAME_WIDTH = 350;
    private final int BUTTON_HGAP = 5;
    private final int BUTTON_VGAP = 2;  
    //Initialize JPanels
    private JPanel namePanel;
    private JPanel arrivalPanel;
    private JPanel departurePanel;
    private JPanel buttonPanel;
    private JPanel cardPanel;
    //Initialize JButtons
    private JButton nameButton;
    private JButton arrivalButton;
    private JButton departureButton;
    private JButton exitButton;
    //Initialize JTextAreas
    private JTextArea nameTextArea;
    private JTextArea arrivalTextArea;
    private JTextArea departureTextArea;
    //Initialize JLabels
    private JLabel displayLabel;
    private JLabel blankLabel; 
}
