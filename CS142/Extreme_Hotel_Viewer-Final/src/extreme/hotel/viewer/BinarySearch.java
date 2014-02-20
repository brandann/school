package extreme.hotel.viewer;

import java.util.*;
import java.util.concurrent.*;

/**
 * File: BinarySearch.Java
 * Class Name: BinarySearch
 * Description: Will do a binary search that searches for part of a name or
 *      the whole name.
 * @version 1.0.0
 * @author Alex Linville
 * NewBeans IDE 7.2.1 JDK 1.7
 * Windows 7 Professional
 * Log: Completed 3/8/13
 */
public class BinarySearch
{
    public BinarySearch()
    {
        
    }
    
    /**
     * Method: doSearch(Reservation[] array, String object, int low, 
     *       int high)
     * Description: Will search through the array passed to it for the string
     *      that was passed to it, will return an array of reservations that 
     *      start with the String passed to the method.
     * @param array1 an array of Reservations that we are searching through.
     * @param object a String that we are looking for.
     * @param low the low point of the search.
     * @param high the high point of the search.
     * @return An Array of Reservations or null if none were found.
     */
    public static Reservation[] doSearch(Comparable[] array1, String object, 
            int low, int high)
    {
        int test;
        int i = 0;
        int j =1;
        int mid = ((high - low) / 2) + low;
        Reservation[] foundReservations;
        Reservation[] array = (Reservation[])array1;
        //int[] tempArray;
        
        ArrayList list = new ArrayList();
        
        if( array[mid].getName().length() < object.length())
        {
            //We are checking letter by letter if the word matchs
            do
            {
                test = array[mid].getName().toUpperCase().substring(i, i+1)
                        .compareTo(object.toUpperCase().substring(i, ++i));
                
            }while(test == 0 && i < array[mid].getName().length());
            
        }
        else    
        {
            //We are checking letter by letter if the word matchs
            do
            {
                test = array[mid].getName().toUpperCase().substring(i, i+1)
                        .compareTo(object.toUpperCase().substring(i, ++i));
              
            }while(test == 0 && i < object.length());
        }
        
        //we have narrowed down our seach to 1 item and its not it so we need
        //to break out
        if(high - low <= 1 && test !=0)
            return null;
        
        if(test > 0)
            return doSearch(array,object,low,mid);
        
        if(test < 0)
            return doSearch(array,object,mid,high);
        
       if(test == 0)
       {
           
         /**
         * File: BinarySearch.Java
         * Class Name: CheckDown
         * Description: This class will become a thread, it will search 
         *      linearly toward the end of the array for names of reservations 
         *      that match the string that gets passed to the constructor of 
         *      this class. will stop searching at the end of the array or 
         *      when a name dose not match object.
         * @version 1.0.0
         * @author Alex Linville
         * NewBeans IDE 7.2.1 JDK 1.7
         * Windows 7 Professional
         * Log: Completed 3/10/13
         * @implements Runnable
         */
           class CheckDown implements Runnable
           {
               int j;
               int mid;
               int i;
               String object;
               Reservation[] array;
               ArrayList<Reservation> list = new ArrayList<Reservation>();
               /**
               * Method: CheckDown(Reservation[] array, String object, int j, 
               *         int i, int mid)
               * Description: Constructor for CheckDown class.
               * @param array an array of Reservations that we are searching 
               *        through.
               * @param object a String that we are looking for.
               * @param i the current index of the string we are looking at
               * @param j the variable that will move us through the array.
               * @param mid the element we are looking at.
               */
               public CheckDown(Comparable[] array, String object, int j, 
                        int i, int mid)
               {
                   this.array = (Reservation[])array;
                   this.object = object;
                   this.j = j;
                   this.i = i;
                   this.mid = mid;
               }
               /**
               * Method: run()
               * Description: Will search toward the bottom of the array 
               *        adding any matching words to the list.
               * @Override
               */
               @Override
               public void run()
               {
                   try
                   {
                       while((mid + j) < array.length && array[mid + j]
                               .getName().length() >= object.length() && 
                               array[mid + j].getName().toUpperCase()
                               .substring(i-1, i).compareTo(object
                               .toUpperCase().substring(i-1, i)) == 0)
                       {
                           list.add(array[mid + j++]);
                       }
                   }
                   catch(Exception e)
                   {
                       System.out.println("there was an error in the Check "
                               + "down");
                   }
               }
               /**
               * Method: getList(ArrayList list)
               * Description: Will copy the list generated by this class and
               *        add it to the list passed to the method.
               * @param list The list being modified.
               * @return ArrayList the new ArrayList.
               */
               public ArrayList getList(ArrayList list)
               {
                   for(int i = 0; i < this.list.size(); i++)
                   {
                       list.add(this.list.get(i));
                   }
                   
                   return list;
               }
               
           }
           /**
         * File: BinarySearch.Java
         * Class Name: CheckUp
         * Description: This class will become a thread, it will search 
         *      linearly toward the top of the array for names of reservations 
         *      that match the string that gets passed to the constructor of 
         *      this class. Will stop searching at the end of the array or 
         *      when a name dose not match object.
         * @version 1.0.0
         * @author Alex Linville
         * NewBeans IDE 7.2.1 JDK 1.7
         * Windows 7 Professional
         * Log: Completed 3/10/13
         * @implements Runnable
         */
           class CheckUp implements Runnable
           {
               int j;
               int mid;
               int i;
               String object;
               Reservation[] array;
               ArrayList<Reservation> list = new ArrayList<Reservation>();
               /**
               * Method: CheckUp(Reservation[] array, String object, int j, 
               *         int i, int mid)
               * Description: Constructor for CheckUp class.
               * @param array an array of Reservations that we are searching 
               *        through.
               * @param object a String that we are looking for.
               * @param i the current index of the string we are looking at
               * @param j the variable that will move us through the array.
               * @param mid the element we are looking at.
               */
               public CheckUp(Comparable[] array, String object, int j, 
                        int i, int mid)
               {
                   this.array = (Reservation[])array;
                   this.object = object;
                   this.j = j;
                   this.i = i;
                   this.mid = mid;
               }
               /**
               * Method: run()
               * Description: Will search toward the top of the array adding
               *        any matching words to the list.
               * @Override
               */
               @Override
               public void run()
               {
                   try
                   {
                       while((mid + j) >= 0 && array[mid + j].getName()
                               .length() >= object.length() && 
                            array[mid + j].getName().toUpperCase()
                           .substring(i-1, i).compareTo(object.toUpperCase()
                           .substring(i-1, i)) == 0)
                       {
                           list.add(array[mid + j--]);
                       }
                   }
                   catch(Exception e)
                   {
                       System.out.println("there was an error in the "
                               + "Check up");
                   }
               }
               /**
               * Method: getList(ArrayList list)
               * Description: Will copy the list generated by this class and
               *        add it to the list passed to the method.
               * @param list The list being modified.
               * @return ArrayList the new ArrayList.
               */
               public ArrayList getList(ArrayList list)
               {
                   for(int i = 0; i < this.list.size(); i++)
                   {
                       list.add(this.list.get(i));
                   }
                   
                   return list;
               }
               
           }
           
           CheckDown checkDown = new CheckDown(array, object, 1, i, mid);
           Thread d = new Thread(checkDown);
           CheckUp checkUp = new CheckUp(array, object, -1, i, mid);
           Thread u = new Thread(checkUp);
           d.start();
           u.start();
           while(u.isAlive() || d.isAlive()){}
           list = checkDown.getList(list);
           list = checkUp.getList(list);
           list.add(array[mid]);
           
           foundReservations = new Reservation[list.size()];
           
           for(i = 0; i < list.size(); i++)
           {
               
               foundReservations[i] = (Reservation)list.get(i);
           }
           
          return foundReservations;
       }
        
        return null;
    }
    
    /**
     * Method: doWholeSearch(Reservation[] array, String object, int low, 
     *       int high)
     * Description: Will search through the array passed to it for the string
     *      that was passed to it, will return an array of reservations that 
     *      exactly match the String passed to the method.
     * @param array1 an array of Reservations that we are searching through.
     * @param object a String that we are looking for.
     * @param low the low point of the search.
     * @param high the high point of the search.
     * @return An Array of Reservations or null if none were found.
     */
    public static Reservation[] doSearchWhole(Comparable[] array1, String 
            object, int low, int high)
    {
        int test;
        int i = 0;
        int j =1;
        int mid = ((high - low) / 2) + low;
        Reservation[] foundReservations;
        Reservation[] array = (Reservation[])array1;
        
        ArrayList list = new ArrayList();
        
        if(array[mid].getName().length() < object.length())
        {
            //We are checking letter by letter if the word matchs
            do
            {
                test = array[mid].getName().toUpperCase().substring(i, i+1)
                        .compareTo(object.toUpperCase().substring(i, ++i));
                
            }while(test == 0 && i < array[mid].getName().length());
            
        }
        else    
        {
            //We are checking letter by letter if the word matchs
            do
            {
                test = array[mid].getName().toUpperCase().substring(i, i+1)
                        .compareTo(object.toUpperCase().substring(i, ++i));
              
            }while(test == 0 && i < object.length());
        }
        
        //we have narrowed down our seach to 1 item and its not it so we need
        //to break out
        if(high - low <= 1 && test !=0)
            return null;
        
        if(test > 0)
            return doSearchWhole(array,object,low,mid);
        
        if(test < 0)
            return doSearchWhole(array,object,mid,high);
        
       if(test == 0)
       {
         /**
         * File: BinarySearch.Java
         * Class Name: CheckDown
         * Description: This class will become a thread, it will search 
         *      linearly toward the end of the array for names of reservations 
         *      that match the string that gets passed to the constructor of 
         *      this class. will stop searching at the end of the array or 
         *      when a name dose not match object.
         * @version 1.0.0
         * @author Alex Linville
         * NewBeans IDE 7.2.1 JDK 1.7
         * Windows 7 Professional
         * Log: Completed 3/10/13
         * @implements Runnable
         */
           class CheckDown implements Runnable
           {
               int j;
               int mid;
               int i;
               String object;
               Reservation[] array;
               ArrayList<Reservation> list = new ArrayList<Reservation>();
               /**
               * Method: CheckDown(Reservation[] array, String object, int j, 
               *         int i, int mid)
               * Description: Constructor for CheckDown class.
               * @param array an array of Reservations that we are searching 
               *        through.
               * @param object a String that we are looking for.
               * @param i the current index of the string we are looking at
               * @param j the variable that will move us through the array.
               * @param mid the element we are looking at.
               */
               public CheckDown(Comparable[] array, String object, int j, 
                        int i, int mid)
               {
                   this.array = (Reservation[])array;
                   this.object = object;
                   this.j = j;
                   this.i = i;
                   this.mid = mid;
               }
               /**
               * Method: run()
               * Description: Will search toward the bottom of the array 
               *        adding any matching words to the list.
               * @Override
               */
               @Override
               public void run()
               {
                   try
                   {
                       while((mid + j) < array.length && array[mid + j]
                               .getName().length() >= object.length() 
                               && array[mid + j].getName()
                               .toUpperCase().substring(i-1, i).
                               compareTo(object.toUpperCase()
                               .substring(i-1, i)) == 0)
                       {
                           if(array[mid + j].getName().toUpperCase().equals(
                                   object.toUpperCase()))
                           {
                                list.add(array[mid + j++]);
                           }
                           else
                           {
                               j++;
                           }
                       }
                   }
                   catch(Exception e)
                   {
                       System.out.println("there was an error in the Check "
                               + "down");
                   }
               }
               /**
               * Method: getList(ArrayList list)
               * Description: Will copy the list generated by this class and
               *        add it to the list passed to the method.
               * @param list The list being modified.
               * @return ArrayList the new ArrayList.
               */
               public ArrayList getList(ArrayList list)
               {
                   for(int i = 0; i < this.list.size(); i++)
                   {
                       list.add(this.list.get(i));
                   }
                   
                   return list;
               }
               
           }
         /**
         * File: BinarySearch.Java
         * Class Name: CheckUp
         * Description: This class will become a thread, it will search 
         *      linearly toward the top of the array for names of reservations 
         *      that match the string that gets passed to the constructor of 
         *      this class. Will stop searching at the end of the array or 
         *      when a name dose not match object.
         * @version 1.0.0
         * @author Alex Linville
         * NewBeans IDE 7.2.1 JDK 1.7
         * Windows 7 Professional
         * Log: Completed 3/10/13
         * @implements Runnable
         */
           class CheckUp implements Runnable
           {
               int j;
               int mid;
               int i;
               String object;
               Reservation[] array;
               ArrayList<Reservation> list = new ArrayList<Reservation>();
               /**
               * Method: CheckUp(Reservation[] array, String object, int j, 
               *         int i, int mid)
               * Description: Constructor for CheckUp class.
               * @param array an array of Reservations that we are searching 
               *        through.
               * @param object a String that we are looking for.
               * @param i the current index of the string we are looking at
               * @param j the variable that will move us through the array.
               * @param mid the element we are looking at.
               */
               public CheckUp(Comparable[] array, String object, int j, 
                        int i, int mid)
               {
                   this.array = (Reservation[])array;
                   this.object = object;
                   this.j = j;
                   this.i = i;
                   this.mid = mid;
               }
               /**
               * Method: run()
               * Description: Will search toward the top of the array adding
               *        any matching words to the list.
               * @Override
               */
               @Override
               public void run()
               {
                   try
                   {
                       while((mid + j) >= 0 && array[mid + j].getName()
                               .length() >= object.length() && 
                            array[mid + j].getName().toUpperCase()
                           .substring(i-1, i).compareTo(object.toUpperCase()
                           .substring(i-1, i)) == 0)
                       {
                           if(array[mid + j].getName().toUpperCase().equals(
                                   object.toUpperCase()))
                           {
                                list.add(array[mid + j--]);
                           }
                           else
                           {
                               j--;
                           }
                       }
                   }
                   catch(Exception e)
                   {
                       System.out.println("there was an error in the Check"
                               + " down");
                   }
               }
               /**
               * Method: getList(ArrayList list)
               * Description: Will copy the list generated by this class and
               *        add it to the list passed to the method.
               * @param list The list being modified.
               * @return ArrayList the new ArrayList.
               */
               public ArrayList getList(ArrayList list)
               {
                   for(int i = 0; i < this.list.size(); i++)
                   {
                       list.add(this.list.get(i));
                   }
                   
                   return list;
               }
               
           }
           
           CheckDown checkDown = new CheckDown(array, object, 1, i, mid);
           Thread q = new Thread(checkDown);
           CheckUp checkUp = new CheckUp(array, object, -1, i, mid);
           Thread k = new Thread(checkUp);
           q.start();
           k.start();
           while(q.isAlive() || k.isAlive()){}
           list = checkDown.getList(list);
           list = checkUp.getList(list);
           if(array[mid].getName().toUpperCase().equals(object.toUpperCase()))
                 list.add(array[mid]);
           
           foundReservations = new Reservation[list.size()];
           
           for(i = 0; i < list.size(); i++)
           {
               
               foundReservations[i] = (Reservation)list.get(i);
           }
           
          return foundReservations;
       }
        
        return null;
    }
        
}

