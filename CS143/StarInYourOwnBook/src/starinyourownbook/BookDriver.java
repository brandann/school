/*
 * BookDriver.java
 */
package starinyourownbook;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * File name:   BookDriver.java       
 * Class Name:  BookDriver
 * Description: Driver class for Star In Your Own Book
 * @version     1.0
 * @author      Brandan Haertel
 * Environment: PC, Windows 8 64-bit, jdk 1.7
 * History:     04/17/2013 1.0 completed
 *              05/09/2013 1.1 completed
 *                  Fixed extra new line after each paragraph problem.
 */
public class BookDriver {
     /**
     * Main constructor - Book Driver<br>
     * Initilize all variables<br>
     * Load book file.
     */
    public BookDriver(File myFile)
    {
        originalFileName = "";
        newName = "";
        oldName = "";
        fontSize = "";
        originalFileLength = 0;
        nameSub = 0;
        paragraphCnt = 0;
        paragraphSubCount = 0;
        loaded = false;
        loadFile(myFile);
    }
    
    /**
     * Loads a book file into a String[].
     * @param myFile 
     */
    private void loadFile(File myFile)             
    {
        ArrayList<String> inList = new ArrayList<>();
        
        Scanner fileInputScanner;
        try
        {
           fileInputScanner = new Scanner(myFile);
           while(fileInputScanner.hasNextLine())
           {
               String s = fileInputScanner.nextLine();
               inList.add(s);
               System.out.println(s);
           }
           originalBook = new String[inList.size()];
           originalBook = inList.toArray(originalBook);
           inList = null;
           originalFileLength = originalBook.length;
           setOriginalFileName(myFile.getName());
           loaded = true;
        }
        catch(IOException e)
        {
            System.out.println("Error: BookDriver.loadFile");
        }
    }
    
    /**
     * Public accessor for creating the book file.
     * @return boolean true if the new book file is sucessfully created.
     */
    public boolean makeBook()
    {
        if(loaded)
        {
            compileBook();
            printFile(revisedBook);
        }
        return true;
    }

    /**
     * Complile the Book String[] into a HTML Formated book, replacing
     * oldName with newName, replace quotes with &quot, compine lines 
     * into a single lined paragraph with <p> or <pre> HTML notation, 
     * Set font size.
     * @return String[] of formated book.
     */
    private String[] compileBook()
    {
        ArrayList<String> list = new ArrayList();
        String paragraph = "";
        String line = "";
        Pattern pattern = null;
        Matcher matcher = null;
        boolean specialParagraph = false;
        boolean newParagraph = false;
        boolean insideParagraph = false;
        boolean ReplacmentMade = false;
        
        list.add("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML "
                + "4.01 Transitional//EN\">");
        list.add("<html>");
        list.add("<head>");
        list.add("<meta http-equiv=\"Content-Type\" content=\"text/html; "
                + "charset=us-ascii\">");
        list.add("<title>" + originalFileName + " for " + newName 
                + "</title>");
        list.add("<style type = \"text/css\">");
        list.add("body {font-family: \"Times New Roman, serif\"; font-size:" 
                + fontSize + "; text-align: justify;};");
        list.add("p { margin-left: 1%; margin-right: 1%; }");
        list.add("</style>");
        list.add("</head>");
        list.add("<body>");
        
        for(int i = 0; i < originalBook.length; i++)
        {
            line = originalBook[i];
            pattern = Pattern.compile("^\\s*$");
            matcher = pattern.matcher(line);
            if(matcher.find())
            {
                //paragraph.replaceFirst("&nbsp", "");
                if(ReplacmentMade)
                {
                    paragraphSubCount++;
                }
                if(!specialParagraph && (paragraph.length() > 0)) 
                {
                    paragraph = paragraph + "</p>";
                }
                else if(specialParagraph && (paragraph.length() > 0)) 
                {
                    paragraph = paragraph + "</pre>";
                    specialParagraph = false;
                }
                list.add(paragraph);
                paragraph = "";
                newParagraph = true;
                ReplacmentMade = false;
            }
            else
            {
                pattern = Pattern.compile("^\\s{1,}");
                matcher = pattern.matcher(line);
                if(matcher.find()) 
                {
                    paragraph = "<pre>";
                    newParagraph = false;
                    specialParagraph = true;
                    insideParagraph = true;
                    paragraphCnt++;
                }
                if(newParagraph && !specialParagraph)
                {
                    paragraph = "<p>";
                    newParagraph = false;
                    insideParagraph = true;
                    paragraphCnt++;
                }
                pattern = Pattern.compile("\\b" + oldName);
                matcher = pattern.matcher(line);
                while(matcher.find())
                {
                    nameSub++;
                    ReplacmentMade = true;
                }
                line = matcher.replaceAll(newName);
                
                pattern = Pattern.compile("\\b" + oldName.toUpperCase());
                matcher = pattern.matcher(line);
                while(matcher.find())
                {
                    nameSub++;
                    ReplacmentMade = true;
                }
                line = matcher.replaceAll(newName.toUpperCase());
                
                pattern = Pattern.compile("\\b" + oldName.toLowerCase());
                matcher = pattern.matcher(line);
                while(matcher.find())
                {
                    nameSub++;
                    ReplacmentMade = true;
                }
                line = matcher.replaceAll(newName.toLowerCase());
                
                pattern = Pattern.compile("\"");
                matcher = pattern.matcher(line);
                line = matcher.replaceAll("&quot");
                
                pattern = Pattern.compile("^'|'$");
                matcher = pattern.matcher(line);
                line = matcher.replaceAll("&quot");
                
                pattern = Pattern.compile("\\s'");
                matcher = pattern.matcher(line);
                line = matcher.replaceAll(" &quot");
                
                pattern = Pattern.compile("'\\s");
                matcher = pattern.matcher(line);
                line = matcher.replaceAll("&quot ");

                paragraph = paragraph + "&nbsp" + line;
            }
        }
        list.add("</body>");
        list.add("</html>");

        revisedBook = new String[list.size()];
        revisedBook = list.toArray(revisedBook);
        list = null;
        return revisedBook;
    }
    
    /**
     * Resets the subsitution and paragraph counts.
     */
    public void resetCount()
    {
        nameSub = 0;
        paragraphCnt = 0;
        paragraphSubCount = 0;
        originalFileLength = 0;
    }
    
    /**
     * Outputs a String[] to a HTML file with the file name being the
     * original file name -extenstion and replaced with _4_replacment
     * name.html<br>
     * File is saved in the current directory.
     * @param array formated book Array
     */
    public void printFile(String[] array)
    {
        PrintWriter fileOutput;
        try
        {
            revisedFileName = originalFileName.substring
                    (0,originalFileName.indexOf(".")) 
                    + "_4_" + newName + ".html";
            fileOutput = new PrintWriter(
                    new FileOutputStream(revisedFileName));
            for(int i = 0; i < array.length; i++)
            {
                fileOutput.println(array[i]+ "<br/>");
            }
            fileOutput.close();
        }
        catch(IOException e){System.out.println("Error: write");}

    }
    
    /**
     * Checks to see if a name exists in the loaded book.
     * Returns true if the name is found
     */
    public boolean doesNameExist(String name)
    {
        Pattern pattern = null;
        Matcher matcher = null;

        for(int i = 0; i < originalBook.length; i++)
        {
                pattern = Pattern.compile("\\b" + name);
                matcher = pattern.matcher(originalBook[i]);
                while(matcher.find())
                {
                    return true;
                }
                pattern = Pattern.compile("\\b" + name.toUpperCase());
                matcher = pattern.matcher(originalBook[i]);
                while(matcher.find())
                {
                    return true;
                }
                
                pattern = Pattern.compile("\\b" + name.toLowerCase());
                matcher = pattern.matcher(originalBook[i]);
                while(matcher.find())
                {
                    return true;
                }
        }
        return false;
    }
    
    /**
     * Sets the Original File Name.
     * @param s String of the Original File Name.
     */
    private void setOriginalFileName(String s){originalFileName = s;}
    
    /**
     * Sets the New Name to be the replacement in the book.
     * @param s String Name to be the replacement in the book.
     */
    public void setNewName(String s){newName = s;}
    
    /**
     * Set the name to be replaced.
     * @param s String of the name to be replaced.
     */
    public void setOldName(String s){oldName = s;}
    
    /**
     * Set the Font Size of the book.
     * @param s String of the Font Size of the book.
     */
    public void setFontSize(String s){fontSize = s;}
    
    /**
     * Get the Original File Name of the book.
     * @return String of the Original File Name of the Book.
     */
    public String getOrginalFileName(){return originalFileName;}
    
    /**
     * Get the Revised File Name of the book.
     * @return String of the Revised File Name of the book.
     */
    public String getRevisedFileName(){return revisedFileName;}
    
    /**
     * Get the name to be the replacement in the book.
     * @return String of the name to be the replacement in the book.
     */
    public String getNewName(){return newName;}
    
    /**
     * Get the name to be replaced in the book.
     * @return String of the name to be replaced in the book.
     */
    public String getOldName(){return oldName;}
    
    /**
     * Get the Font Size of the HTML File.
     * @return String of the Font Size of the HTML File.
     */
    public String getFontSize(){return fontSize;}
    
    /**
     * Gets the Loaded Status of the book.
     * @return boolean true if a book is correctly loaded into the class.
     */
    public boolean getLoaded(){return loaded;}
    
    /**
     * Get the number of name replacement made in the conversion.
     * @return int the number of name replacments made in the conversion.
     */
    public int getNameReplacments(){return nameSub;}
    
    /**
     * Get The number of Paragraphs in the HTML File.
     * @return int of the number of paragraphs in the HTML File.
     */
    public int getParagraphCount(){return paragraphCnt;}
    
    /**
     * Get the number of paragraphs in the HTML File that included
     * name subsitutions.
     * @return int of the number of paragraphs in the HTML File that included
     * name subsitutions.
     */
    public int getParagraphReplacmentCount(){return paragraphSubCount;}
    
    /**
     * Get the number of lines in the original book file.
     * @return int of the number of lines in the original book file.
     */
    public int getOriginalFileLength(){return originalFileLength;}
    
    /**
     * Creates and displays a new Error Dialog Box.
     * @param errorMessage The message to display to the user.
     * @param title The Title of the Dialog Box.
     */
    public static void errorBox(String errorMessage, String title)
    {
        JOptionPane.showMessageDialog(null, errorMessage, "Error: " 
                + title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Creates and displays a new Info Dialog Box.
     * @param infoMessage The message to display to the user.
     * @param title The Title of the Dialog Box.
     */
    public static void infoBox(String infoMessage, String title)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "Info: " 
                + title, JOptionPane.INFORMATION_MESSAGE);
    }

    private String[] originalBook;
    private String[] revisedBook;
    private String originalFileName;
    private String revisedFileName;
    private String newName;
    private String oldName;
    private String fontSize;
    private boolean loaded;
    private int nameSub;
    private int paragraphCnt;
    private int paragraphSubCount;
    private int originalFileLength;
}
