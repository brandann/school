/*
 * Calendar.java
 */
package calendar;

import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import javax.swing.*;

/**
 * CalendarGUI <br>
 * <br>
 * A simple program that shows a calendar based on the <br>
 * inputed date from a user <br>
 * <br>
 * Test Environment: JDK 1.7.2 on Windows 8, AMD CPU <br>
 * <br>
 * December 2nd 2012 <br>
 * 	Completed v 1.0 <br>
 * @author Brandan Haertel <br>
 * @version 1.0 <br>
 */
public class Calendar extends JFrame
{
    CalendarDriver calendarDriver = new CalendarDriver();
    Font courierBold = new Font("monospaced", Font.BOLD, 17);
    private static final int FRAME_WIDTH = 250;
    private static final int FRAME_HEIGHT = 320;
    private static final int TEXT_WIDTH = 15;
    private static final int TEXT_HEIGHT = 20;
    String[] years = {"2012", "2013", "2014", "2015", 
                      "2016", "2017", "2018", "2019", "2020"};
    String[] months = { "Janurary", "Feburary", "March", "April", 
                        "May", "June", "July", "August", "September", 
                        "October", "Novemeber", "December"};
    int lastSuccessfulday;
    
    /**
     * Starts the GUI
     * @param args Command line arguments.
     */
    public static void main(String[] args)
    {
       JFrame frame = new Calendar();
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
       frame.setTitle("Brandan Calendar GUI");
    }
    
    /**
     * Builds the calendar form.
     */
    public Calendar()
    {
        setLayout(new BorderLayout());
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        dayStuff();
        comboStuff();
        dateLabel();
        calendarBox();
        add(northPanel, BorderLayout.NORTH);
        add(calendarBoxPanel, BorderLayout.CENTER);
        add(dateLabelPanel, BorderLayout.SOUTH);
    }

    /**
     * Builds the date label & panel.
     */
    private void dateLabel()
    {
        dateLabel = new JLabel(calendarDriver.GetDate());
        dateLabelPanel = new JPanel();
        dateLabelPanel.add(dateLabel);
    }
    
    /**
     * Builds the calendar textArea and panel.
     */
    private void calendarBox()
    {
        calendarTextBox = new JTextArea(TEXT_HEIGHT, TEXT_WIDTH);
        calendarTextBox.setFont(courierBold);
        calendarTextBox.setEditable(false);
        calendarTextBox.setText(calendarDriver.toString());
        
        calendarBoxPanel = new JPanel();
        calendarBoxPanel.add(calendarTextBox);
    }
    
    /**
     * Builds the day text box and panel.
     */
    private void dayStuff()
    {
        datePanel = new JPanel();
        dayText = new JTextField(calendarDriver.GetDay(), 4);
        datePanel.add(dayText);
        northPanel.add(datePanel);
        
        /**
         * Day textbox ActionListener.
         */
        class changeStatusListener implements ActionListener
        {
            /**
             * sets the background when a button is clicked
             * @param event the triggering event object
             */
            @Override 
            public void actionPerformed(ActionEvent event)
            {
                changeStuff();
            }
        }
        dayText.addActionListener(new changeStatusListener());
    }
    
    /**
     * Builds the combo boxes and panel.
     */
    private void comboStuff()
    {
        comboPanel = new JPanel();
        monthCombo = new JComboBox(months);
        yearCombo = new JComboBox(years);
        monthCombo.setSelectedIndex(calendarDriver.GetMonth());
        yearCombo.setSelectedItem(calendarDriver.GetYear());
        
        comboPanel.add(monthCombo);
        comboPanel.add(yearCombo);
        
        northPanel.add(datePanel);
        northPanel.add(comboPanel);

        /**
         * Builds the combo box ActionListener.
         */
        class changeStatusListener implements ActionListener
        {
            /**
             * sets the background when a button is clicked
             * @param event the triggering event object
             */
            @Override 
            public void actionPerformed(ActionEvent event)
            {
                changeStuff();
            }
        }
        monthCombo.addActionListener(new changeStatusListener());
        yearCombo.addActionListener(new changeStatusListener());
    }
    
    /**
     * Adjusts the form per the selected date using
     * the CalendarDriver.
     */
    private void changeStuff()
    {
        short inDay = Short.parseShort(dayText.getText());
        short inMonth = (short) monthCombo.getSelectedIndex();
        short inYear = (short) (2012 + (short)yearCombo.getSelectedIndex());
        if(inDay>0)
            {
                calendarDriver.ChangeDate(inDay, inMonth, inYear);
                calendarTextBox.setText(calendarDriver.toString());
                dateLabel.setText(calendarDriver.GetDate());
            }
    }

    
    private JPanel northPanel = new JPanel();
    private JPanel comboPanel;
    private JPanel datePanel;
    private JPanel dateLabelPanel;
    private JPanel calendarBoxPanel;
    private JTextField dayText;
    private JComboBox monthCombo;
    private JComboBox yearCombo;
    private JTextArea calendarTextBox;
    private JLabel dateLabel;

    ButtonGroup group; 
    JScrollPane AddressScroll;
    JComboBox colorCombo;
}

