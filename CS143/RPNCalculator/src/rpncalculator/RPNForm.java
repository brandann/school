/*
 * RPNForm.java
 */
package rpncalculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * File name:   RPNForm.java <br>      
 * Class Name:  RPNForm <br>
 * Description: GUI for Reverse Polish Notation Calculator <br>
 * @version     1.0 <br>
 * @author      Brandan Haertel <br>
 * Environment: PC, Windows 8 64-bit, jdk 1.7 <br>
 * History:     05/27/2013 1.0 completed <br>
 */
public class RPNForm extends JFrame
{
     /**
     * Creates and displays a window of the class RPNClaculator.
     * @param args the command-line arguments
     */
    public static void main(String[] args)
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
                    RPNForm.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(
                    RPNForm.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(
                    RPNForm.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(
                    RPNForm.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        RPNForm gui = new RPNForm();
        gui.setVisible(true);
    }
    
    /**
     * constructor -- set up the form
     */
    public RPNForm()
    {  
        calc = new RPNCalculator();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Brandan Haertel RPN Calculator");
        setLocation(40, 40);
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        setDisplayPanel();
        
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
     * sets up the displayPanel
     */
    public final void setDisplayPanel()
    {
        /**
         * inner class -- listens for any button actions
         */
        class StatusListener implements ActionListener
        {
            /**
            * deal with an action
            * @param event --the actionEvent performed
            */
            @Override
            public void actionPerformed(ActionEvent event)
            { 
                //System.out.println(event.getActionCommand());
                dealWith(event.getActionCommand());
                displayTextField.requestFocusInWindow();
            }  
        }
        
        /**
         * inner class -- listens for any button actions
         */
        class DisplayListener implements KeyListener
        {
            /**
            * not implemented
            * @param event --the actionEvent performed
            */
            @Override
            public void keyPressed(KeyEvent event)
            {}
            /**
            * deal with a keystroke
            * @param event --the actionEvent performed
            */
            @Override
            public void keyReleased(KeyEvent event)
            {
                char c = event.getKeyChar();
                if(event.getKeyCode() == KeyEvent.VK_SHIFT)
                    return;
                if(event.getKeyCode() == KeyEvent.VK_BACK_SPACE ||
                        event.getKeyCode() == KeyEvent.VK_DELETE)
                {
                    displayString = displayTextField.getText();
                    return;
                }
                //brandan removed
                //displayTextField.setText(displayString); 
                if(validChar(c))
                    dealWith(String.valueOf(c)); 
                displayTextField.requestFocusInWindow();
            }
            /**
            * not implemented
            * @param event --the actionEvent performed
            */
            @Override
            public void keyTyped(KeyEvent event)
            {}   
        }
        KeyListener displayListener = new DisplayListener();
        ActionListener buttonListener = new StatusListener();

        
        displayPanel = new JPanel( );
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.X_AXIS));
        displayPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
                new Color(0XCC, 0X99, 0XFF)));   
        RPNLabel = new JLabel(" RPN ");
        RPNLabel.setBackground(new Color(0X33, 0X00, 0X66));
        RPNLabel.setFont(new Font("Courier New", 1, 36));
        RPNLabel.setForeground(new Color(0x66, 0x33, 0x66));      
        displayPanel.add(RPNLabel);

        displayTextField = new JTextField("");
        displayTextField.setFont(new Font("Courier New", 1, 24));
        displayTextField.setHorizontalAlignment(JTextField.RIGHT);
        displayTextField.setActionCommand("Enter");
        displayTextField.addKeyListener(displayListener);
        displayPanel.add(displayTextField);
        contentPane.add(displayPanel, BorderLayout.NORTH);        

        buttonPanel = new JPanel( );
        buttonPanel.setBackground(new Color(0xff, 0xef, 0xdf));
        buttonPanel.setLayout(new GridLayout(RPNCalculator.ROWS,
                RPNCalculator.COLS));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
                new Color(0X99, 0XFF, 0XFF)));
        buttonGrid = new JButton[RPNCalculator.ROWS][RPNCalculator.COLS];

        for(int i = 0; i < RPNCalculator.ROWS; i++)
        {
            for(int j = 0; j < RPNCalculator.COLS; j++)
            {
                buttonGrid[i][j]  = new JButton();
                buttonGrid[i][j].setFont(new Font("Courier New", 1, 16));
                buttonGrid[i][j].addActionListener(buttonListener);
                buttonGrid[i][j].setBorder(BorderFactory.createBevelBorder(1));
                buttonPanel.add(buttonGrid[i][j]);
            }
        }
        buttonGrid[0][0].setText("Exit");
        buttonGrid[0][1].setText("C");
        buttonGrid[0][2].setText("CE");
        buttonGrid[0][3].setFont(new Font("Courier New", 1, 20));
        buttonGrid[0][3].setBackground(new Color(0xf0, 0xf6, 0xff));
        buttonGrid[0][3].setForeground(new Color(0x99, 0x00, 0x66));
        buttonGrid[0][3].setText("7");
        buttonGrid[0][4].setFont(new Font("Courier New", 1, 20));
        buttonGrid[0][4].setBackground(new Color(0xf0, 0xf6, 0xff));
        buttonGrid[0][4].setForeground(new Color(0x99, 0x00, 0x66));
        buttonGrid[0][4].setText("8");
        buttonGrid[0][5].setFont(new Font("Courier New", 1, 20));
        buttonGrid[0][5].setBackground(new Color(0xf0, 0xf6, 0xff));
        buttonGrid[0][5].setForeground(new Color(0x99, 0x00, 0x66));
        buttonGrid[0][5].setText("9");
        buttonGrid[0][6].setText("+");
        buttonGrid[0][7].setText("x");
        buttonGrid[1][0].setText("Set");
        buttonGrid[1][1].setText("Get");
        buttonGrid[1][2].setText("Up");
        buttonGrid[1][3].setFont(new Font("Courier New", 1, 20));
        buttonGrid[1][3].setBackground(new Color(0xf0, 0xf6, 0xff));
        buttonGrid[1][3].setForeground(new Color(0x99, 0x00, 0x66));
        buttonGrid[1][3].setText("4");
        buttonGrid[1][4].setFont(new Font("Courier New", 1, 20));
        buttonGrid[1][4].setBackground(new Color(0xf0, 0xf6, 0xff));
        buttonGrid[1][4].setForeground(new Color(0x99, 0x00, 0x66));
        buttonGrid[1][4].setText("5");
        buttonGrid[1][5].setFont(new Font("Courier New", 1, 20));
        buttonGrid[1][5].setBackground(new Color(0xf0, 0xf6, 0xff));
        buttonGrid[1][5].setForeground(new Color(0x99, 0x00, 0x66));
        buttonGrid[1][5].setText("6");
        buttonGrid[1][6].setText("-");
        buttonGrid[1][7].setText("/");
        buttonGrid[2][0].setText("Load");
        buttonGrid[2][1].setText("Save");
        buttonGrid[2][2].setText("Down");
        buttonGrid[2][3].setFont(new Font("Courier New", 1, 20));
        buttonGrid[2][3].setBackground(new Color(0xf0, 0xf6, 0xff));
        buttonGrid[2][3].setForeground(new Color(0x99, 0x00, 0x66));
        buttonGrid[2][3].setText("1");
        buttonGrid[2][4].setFont(new Font("Courier New", 1, 20));
        buttonGrid[2][4].setBackground(new Color(0xf0, 0xf6, 0xff));
        buttonGrid[2][4].setForeground(new Color(0x99, 0x00, 0x66));
        buttonGrid[2][4].setText("2");
        buttonGrid[2][5].setFont(new Font("Courier New", 1, 20));
        buttonGrid[2][5].setBackground(new Color(0xf0, 0xf6, 0xff));
        buttonGrid[2][5].setForeground(new Color(0x99, 0x00, 0x66));
        buttonGrid[2][5].setText("3");
        buttonGrid[2][6].setText("^");
        buttonGrid[2][7].setText("%");
        buttonGrid[3][0].setText("Rec");
        buttonGrid[3][1].setText("Run");
        buttonGrid[3][2].setText("?");
        buttonGrid[3][3].setText("+/-");
        buttonGrid[3][4].setFont(new Font("Courier New", 1, 20));
        buttonGrid[3][4].setBackground(new Color(0xf0, 0xf6, 0xff));
        buttonGrid[3][4].setForeground(new Color(0x99, 0x00, 0x66));
        buttonGrid[3][4].setText("0");
        buttonGrid[3][5].setText(".");
        buttonGrid[3][6].setText("1/n");
        buttonGrid[3][7].setFont(new Font("Courier New", 1, 15));
        buttonGrid[3][7].setBackground(new Color(0xf6, 0xf0, 0xff));
        buttonGrid[3][7].setForeground(new Color(0x99, 0x00, 0x66));
        buttonGrid[3][7].setText("Enter");

        contentPane.add(buttonPanel, BorderLayout.CENTER);
        disableAlpha(); 
    }
    
    /**
     * deal with an action
     * @param actionCommand --the actionEvent performed
     */
    public void dealWith(String actionCommand)
    {
        if(msgOn)
        {
            msgOn = false;
            displayTextField.setForeground(Color.BLACK);
            if(recordMode)
                displayTextField.setForeground(Color.MAGENTA);
            displayTextField.setText("");
        }
        try
        {
            if(helpMode)
            {
                displayHelp(actionCommand);
                helpMode = false;
                return;
            }
            else
                inString = displayTextField.getText();
            if(recordMode)
            {
                //Done//
                if(actionCommand.equals("Rec")){
                    buttonGrid[3][0].setForeground(Color.BLACK);
                    displayTextField.setForeground(Color.BLACK);
                    recordMode = false;
                    actionCommand = "";
                }
                else if(actionCommand.equals("Save")){
                }
                else{
                    //make sure record button and display text is pink
                    buttonGrid[3][0].setForeground(Color.MAGENTA);
                    displayTextField.setForeground(Color.MAGENTA);
                    calc.Instructions.add(actionCommand);
                    System.out.println(actionCommand);
                }
                    
            }
            if(getOn)
            {
                //Done
                getOn = false;
                calc.theStack.addFirst(calc.getReg(actionCommand));
                displayTextField.setText(String.valueOf(
                        calc.theStack.getFirst()));
                actionCommand = "";
            }
            if(setOn)
            {
                //Done
                setOn = false;
                calc.addReg(actionCommand);
                actionCommand = "";
            }
            if(actionCommand.equals("?"))
            {
                //Done
                helpMode = true;
                displayTextField.setForeground(new Color(0, 0X99, 0X66));
                displayTextField.setText(inString = "?");
            }
            else if(actionCommand.equals("Exit"))
            {
                //Done
                System.exit(0);
            }
            else if(actionCommand.equals("Save"))
            {
                //Done//
                File macro = new File(MacroFile);
                PrintWriter fileOutput;
                try {
                    fileOutput = new PrintWriter(macro);
                    Iterator<String> itr = calc.Instructions.iterator();
                    while(itr.hasNext()) {
                        fileOutput.println(itr.next());
                    }
                    fileOutput.close();
                    calc.Instructions.clear();
                }
                catch(IOException e) {
                    System.out.println("Error: write");
                }
            }
            else if(actionCommand.equals("Load"))
            {
                //Done//
                File macro = new File(MacroFile);
                Scanner fileInputScanner;
                if(macro.exists() && macro.canRead()){
                    try {
                        calc.Instructions.clear();
                        fileInputScanner = new Scanner(macro);
                        while(fileInputScanner.hasNextLine()) {
                            calc.Instructions.add(
                                    fileInputScanner.nextLine());
                        }
                        fileInputScanner.close();
                    }
                    catch(IOException e) {
                        System.out.println("Error: BookDriver.loadFile");
                    }
                }
            }
            else if(actionCommand.equals("Rec"))
            {
                //Done
                recordMode = true;
                buttonGrid[3][0].setForeground(Color.MAGENTA);
                displayTextField.setForeground(Color.MAGENTA);
            }
            else if(actionCommand.equals("Run"))
            {
                //Done
                if(!calc.Instructions.isEmpty()){
                    Iterator<String> itr = calc.Instructions.iterator();
                    while(itr.hasNext()) {
                        dealWith(itr.next());
                    }
                }
            }
            else if(actionCommand.equals("C")
                    || actionCommand.equals("c"))
            {
                //Done
                calc.theStack.removeFirst();
                displayString = "";
                displayTextField.setText("");
                if(calc.theStack.size() > 0) {
                    displayTextField.setText(
                            String.valueOf(calc.theStack.getFirst()));
                }
            }
            else if(actionCommand.equals("CE"))
            {
                //Done
                calc.theStack.clear();
                displayString = "";
                displayTextField.setText("");
            }
            else if(actionCommand.equals("Get"))
            {
                //Done
                getOn = true;
            }
            else if(actionCommand.equals("Set"))
            {           
                //Done
                setOn = true;
            }
            else if(actionCommand.equals("Enter")
                    || actionCommand.equals("\n"))
            {
                //Done
                calc.theStack.addFirst(Double.valueOf(displayString));
                displayTextField.setText(
                        String.valueOf(calc.theStack.peek()));
                displayString = "";
            }
            else if (actionCommand.equals("1/n"))                 
            {
                //Done
                double x = Double.valueOf(displayString);
                x = 1/x;
                displayString = String.valueOf(x);
                displayTextField.setText(displayString);
                dealWith("Enter");
            }
            else if(actionCommand.equals("+/-"))
            {
                //Done
                double x = Double.valueOf(displayString);
                x = x*(-1);
                displayString = String.valueOf(x);
                displayTextField.setText(displayString);
            }
            else if (actionCommand.equals("."))                 
            {
                //Done
                displayString = displayString + actionCommand;
                displayTextField.setText(displayString);
            }
            else if(Character.isDigit(actionCommand.charAt(0)))    
            {
                displayString = displayString + actionCommand;
                displayTextField.setText(displayString);
            }
            else if(actionCommand.equals("Up"))
            {
                //Done
                double x = calc.theStack.pollFirst();
                calc.theStack.add(x);
                displayTextField.setText(
                        String.valueOf(calc.theStack.peek()));
            }
            else if(actionCommand.equals("Down"))
            {
                //Done
                double x = calc.theStack.pollLast();
                calc.theStack.addFirst(x);
                displayTextField.setText(
                        String.valueOf(calc.theStack.peek()));
            }
            else
            {
                //Done
                if(calc.theStack.size() <= 1) {
                    System.out.println("empty stack #428");
                    displayTextField.setText("Error");
                    return;
                }
                char c = actionCommand.charAt(0);
                double first = calc.theStack.pop();
                double second = calc.theStack.pop();
                double computed = 0.0;
                switch(c)
                {
                    case '+':
                        computed = first + second;
                        break;
                    case '-':
                        computed = second - first;
                        break;
                    case '*':
                        computed = first * second;
                        break;
                    case 'x':
                        computed = first * second;
                        break;
                    case 'X':
                        computed = first * second;
                        break;
                    case '/':
                        computed = second / first;
                        break;
                    case '^':
                        computed = Math.pow(second, first);
                        break;
                    case '%':
                        computed = second % first;
                        break;
                }
                displayString = String.valueOf(computed);
                displayTextField.setText(displayString);
                dealWith("Enter");
            }
        }
        catch(Exception e)
        {
            //displayTextField.setText("Error");
            //Program uses my own Error Checking
            displayString = "";
        }
    } 

    /**
     * disables non-numeric-related keys
     */
    private void disableAlpha()
    {
        for(char c = '\0'; c < '%'; c++)
            displayTextField.getInputMap().put(KeyStroke.getKeyStroke(c),
                            "none");
        for(char c = '&'; c < '*'; c++)
            displayTextField.getInputMap().put(KeyStroke.getKeyStroke(c),
                            "none");
        for(char c = ':'; c <= '?'; c++)
            displayTextField.getInputMap().put(KeyStroke.getKeyStroke(c),
                            "none");
        for(char c = '@'; c <= 'C'; c++)
            displayTextField.getInputMap().put(KeyStroke.getKeyStroke(c),
                            "none");
        for(char c = 'D'; c <= 'X'; c++)
            displayTextField.getInputMap().put(KeyStroke.getKeyStroke(c),
                            "none");
        for(char c = 'Y'; c <= '^'; c++)
            displayTextField.getInputMap().put(KeyStroke.getKeyStroke(c),
                            "none");
        for(char c = '_'; c <= 'c'; c++)
            displayTextField.getInputMap().put(KeyStroke.getKeyStroke(c),
                            "none");
        for(char c = 'd'; c <= 'x'; c++)
            displayTextField.getInputMap().put(KeyStroke.getKeyStroke(c),
                            "none");
        for(char c = 'y'; c <= '~'; c++)
            displayTextField.getInputMap().put(KeyStroke.getKeyStroke(c),
                            "none");
        
        displayTextField.getInputMap().put(KeyStroke.getKeyStroke('/'),
                            "none");
    }
    
    /**
     * Verifies to see if inputed character is valid
     * @param c the character to test
     * @return true is c is valid, false otherwise
     */
    private boolean validChar(char c)
    {
        if(Character.isDigit(c))
            return true;
        switch(c)
        {
            case '+':
            case '-':
            case '*':
            case 'x':
            case 'X':
            case '/':
            case 'C':
            case 'c':
            case '^':
            case '%':
            case '?':
            case '.':
            case '\r':
            case '\n':
            return true;
        }
        return false;
    }
    
    /**
     * displays the appropriate help
     * @param actionCommand the command from the triggering event
     */
    private void  displayHelp(String actionCommand)
    {
        msgOn = true;
        if(actionCommand.equals("Exit"))
                displayTextField.setText("Exit: Exits program");
        else if(actionCommand.equals("C")
                || actionCommand.equals("c"))
                displayTextField.setText("C: Clears top element");
        else if(actionCommand.equals("CE"))
                displayTextField.setText("CE: Clears entire stack");
        else if(actionCommand.equals("+"))
                displayTextField.setText("+: adds top  2 elements");
        else if(actionCommand.equals("x")
                || actionCommand.equals("X")
                || actionCommand.equals("*"))
                displayTextField.setText("x: multiplies top 2 elements");
        else if(actionCommand.equals("Set"))
                displayTextField.setText("Set: Sets register (0-9)");
        else if(actionCommand.equals("Get"))
                displayTextField.setText("Get: gets register (0-9)");
        else if(actionCommand.equals("Up"))
                displayTextField.setText("Up: Rotates stack up");
        else if(actionCommand.equals("-"))
                displayTextField.setText("-: subtracts top 2 elements");
        else if(actionCommand.equals("/"))
                displayTextField.setText("/: divides top 2 elements");
        else if(actionCommand.equals("Load"))
                displayTextField.setText("Load: Loads program from file");
        else if(actionCommand.equals("Save"))
                displayTextField.setText("Save: Saves program to file");
        else if(actionCommand.equals("Down"))
                displayTextField.setText("Down: Rotates stack down");
        else if(actionCommand.equals("^"))
                displayTextField.setText("^: exponent");
        else if(actionCommand.equals("%"))
                displayTextField.setText("%: Mods top 2 elements");
        else if(actionCommand.equals("Rec"))
                displayTextField.setText("Rec: Program mode on/off");
        else if(actionCommand.equals("Run"))
                displayTextField.setText("Run: Runs program");
        else if(actionCommand.equals("?"))
                displayTextField.setText("?: press ? then key for help");
        else if(actionCommand.equals("+/-"))
                displayTextField.setText("+/-: changes sign of number");
        else if(actionCommand.equals("1/n"))
                displayTextField.setText("1/n: inverts the number");
        else if(actionCommand.equals("Enter"))
                displayTextField.setText("Enter: element to stack");
        else
                displayTextField.setText("");
    }
    
    public static final int FRAME_WIDTH = 660;
    public static final int FRAME_HEIGHT = 330;
    public static final String MacroFile = "macroFile.txt";
    private final String ICON = "/rpncalculator/Brandan.png";//
    private Container contentPane;
    private JPanel displayPanel;
    private JLabel RPNLabel; 
    private JTextField displayTextField;
    private JPanel buttonPanel;
    private JButton[][] buttonGrid;
    
    private RPNCalculator calc;
    private boolean helpMode = false;
    private boolean recordMode = false;
    private boolean msgOn = false;
    private boolean commandPerformed = false;
    private boolean getOn = false;
    private boolean setOn = false;
    private String inString = "";
    private String displayString = "";
}