/*
 * CalendarDriver.java
 */
package calendar;

import Calendar.DateAD;



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
public class CalendarDriver {
    DateAD aDay = new DateAD();
    private static final int ROWS = 7;
    private static final int COLUMNS = 7;
    private int[][] calendarArray = new int[ROWS][COLUMNS];
    private int[][] blankArray = new int[ROWS][COLUMNS];
    String[] months = { "Janurary", "Feburary", "March", "April", 
                        "May", "June", "July", "August", "September", 
                        "October", "Novemeber", "December"};
    
    String currentDate = aDay.toString();
    
    /**
     * Creates a new CalendarDriver using no parameters
     * Sets the CalendarDriver to the current date.
     */
    public CalendarDriver()
    {
        ChangeDate(aDay.getDayOfMonth(), aDay.getMonth(), aDay.getYear());
    }
    
    /**
     * Creates a CalendarDriver using an inputed date.
     * @param day day of month to set.
     * @param month month of year to set.
     * @param year year to set.
     */
    public CalendarDriver(short day, short month, short year)
    {
        ChangeDate(day, month, year);
    }
    
    /**
     * Updates the DateAD to the inputed date.
     * @param day Day to set.
     * @param month Month to set.
     * @param year Year to set.
     */
    public void ChangeDate(short day, short month, short year)
    {
        DateAD firstDay = new DateAD((short)1, month, year);
        calendarArray = blankArray;
        aDay.setDayOfMonth(day);
        aDay.setMonth(month);
        aDay.setYear(year);
        int daysInMonth = aDay.daysInMonth(aDay.getMonth(), aDay.getYear());
        int dayOfWeek = 1 - (int) firstDay.getDayOfWeek();
        
        for(int j=0;j<ROWS;j++)
        {
            for(int k=0;k<COLUMNS;k++)
            {
                if(dayOfWeek<1 || dayOfWeek>daysInMonth)
                {
                    calendarArray[j][k] = 0;
                }
                else
                {
                    calendarArray[j][k] = dayOfWeek;
                }
                dayOfWeek++;
            }
        }
    }
    
    /**
     * Gets the date set in DateAD.
     * @return String the date string in the form: Monday, December 1, 2012.
     */
    public String GetDate()
    {
        String dayString = aDay.toString();
        return dayString;
    }
    
    /**
     * Gets the day of month set in DateAD.
     * @return String Day of month.
     */
    public String GetDay()
    {
        String dayString = String.valueOf(aDay.getDayOfMonth());
        return dayString;
    }
    
    /**
     * Gets the month set in DateAD
     * @return short month
     */
    public short GetMonth()
    {
        short monthShort = aDay.getMonth();
        return monthShort;
    }
    
    /**
     * Gets the year set in DateAD
     * @return short year
     */
    public short GetYear()
    {
        short yearShort = aDay.getDayOfYear();
        return yearShort;
    }
    
    /**
     * gets the calendar for the text box converted from Array.
     * @return String calendar
     */
    @Override
    public String toString()
    {
        String toString = "";
        toString = " " + months[aDay.getMonth()] 
                + ",   " + aDay.getYear() + "\n";
        toString = toString + "  S  M  T  W TH  F  S " + "\n";
        for(int j=0;j<ROWS;j++)
        {
            for(int k=0;k<COLUMNS;k++)
            {
                if(calendarArray[j][k] == 0)
                {
                    toString = toString + " " + " " + " ";
                }
                else
                {
                    toString = toString 
                            + String.format("%3d", calendarArray[j][k]);
                }
                
            }
            toString = toString + "\n";
        }
        
        return toString;
    }
}
