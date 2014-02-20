/*
 * RPNCalculator.java
 */
package rpncalculator;
import java.util.Deque;
import java.util.LinkedList;

/**
 * File name:   RPNCalculator.java <br>       
 * Class Name:  RPNCalculator <br>
 * Description: Driver class RPN Calculator <br>
 * @version     1.0 <br>
 * @author      Brandan Haertel <br>
 * Environment: PC, Windows 8 64-bit, jdk 1.7 <br>
 * History:     05/27/2013 1.0 completed <br>
 */
public class RPNCalculator
{
    public static final int ROWS = 4;
    public static final int COLS = 8;
    public static final int NUMBER_OF_REGISTERS = 10;
    public Deque<Double> theStack;   
    public LinkedList<String> Instructions;
    public double[] register;
    
    /**
     * Creates a new instance of RPNCalculator
     */
    public RPNCalculator()
    {
       register = new double[NUMBER_OF_REGISTERS];
       theStack = new LinkedList<>();
       Instructions = new LinkedList<>();
       for(int i = 0; i<NUMBER_OF_REGISTERS;i++)
           register[i] = 0;
    }
    
    /**
     * Adds the top object from theStack to the selected register array slot.
     * @param c the array position to be amended.
     * @return true if assignment was successful.
     */
    public boolean addReg(String c){
        try{
            int x = Integer.valueOf(c);
            register[x] = theStack.peek();
        }
        catch(Exception e){
            return false;
        }
        return true;
    }
    
    /**
     * gets the selected array slot value from the register.
     * @param c the array position to get from.
     * @return double of the array position value.
     */
    public double getReg(String c){
        int index = Integer.valueOf(c);
        return register[index];
    }   
}