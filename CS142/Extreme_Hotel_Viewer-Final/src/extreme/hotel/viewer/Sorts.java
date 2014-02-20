package extreme.hotel.viewer;

import java.io.File;
  /** 
  * Contains all sorting methods in project. Primarily an optimized quicksort
  * that calls insertionSort() after sorted file is down to sort span of 8.
  * 
  * @author Matson Ovstedal
  */
public class Sorts
{
  /** 
  * This is the main class for the FileSort method. It controls all overarching
  * logic as well as saving and reading text and binary files.
  * 
  * @author Matson Ovstedal
  */
  public static void quickSort(Comparable[] array)
  {
  quickSort(array, 0, array.length - 1);
  }
  public static void quickSort(Comparable[] array, int from, int to)
    throws ArrayIndexOutOfBoundsException
  {
    int midPoint = 0;
    
    if (from < to - 1 && (to - from) > 8)
    {
      midPoint = partition(array, from, to);
      sortFirstMiddleLast(array, from, midPoint, to);
      quickSort(array, from, midPoint);
      quickSort(array, midPoint + 1, to);
    }
    else if((to - from) < 6){
        insertionSort(array);
        
    }
  }
  /** 
  * This is the partition method called by QuickSort(). Partitions and reorders
  * array in specified indices.
  * @param array
  * @param from
  * @param to
  * @return int
  */
  private static int partition(Comparable[] array, int from, int to)
    throws ArrayIndexOutOfBoundsException
  {
    int front = from;
    int back = to - 1;
    Comparable compare = array[front];

    front++;
    while (front < back)
    {
      while ((front < back) && (compare.compareTo(array[front]) > 0)) 
      {
        front++;
      }
      while ((front < back) && (compare.compareTo(array[back]) <= 0)) 
      {
        back--;
      }
      if (front != back) {
        swap(array, front, back);
      }
    }
    if (compare.compareTo(array[front]) >= 0)
    {
      swap(array, front, from);
      return front;
    }

    if (front - 1 > from){
      swap(array, front - 1, from);
    }
    return front - 1;
  }
  /** 
  * Called by quickSort(), swaps given indices.
  * @param array
  * @param index1
  * @param index2
  */
  public static void swap(Comparable[] array, int index1, int index2)
    throws ArrayIndexOutOfBoundsException
  {
    if (index1 == index2)
    {
      return;
    }
    Comparable temp = array[index1];
    array[index1] = array[index2];
    array[index2] = temp;
  }
  public static void insertionSort(Comparable[] array)
     {
          Comparable temp;
          for(int i = 1; i < array.length; i++) // ar[i] is element to insert
          {
               temp = array[i];
               int j = 0;
               for(j = i; j > 0; j--)
               {
                    if(temp.compareTo(array[j - 1]) < 0)
                    {
                         array[j] = array[j - 1];
                    }
                    else{
                        break;
                    }
               }
               array[j] = temp;
          }
     }
  /** 
  * Sorts given indices into order.
  * @param array
  * @param from
  * @param mid
  * @param to
  */
  private static void sortFirstMiddleLast(Comparable[] array, int from, int mid, int to)
  {
      while(!(array[from].compareTo(array[mid]) <= 0) || !(array[mid].compareTo(array[to]) <= 0))
      {
          if(array[from].compareTo(to) <= 0)
          {
              if(array[from].compareTo( array[mid] ) > 0)
              {
                  swap(array, from, mid);
              }
          }
          else if( array[from].compareTo( array[to]) > 0)
          {
              swap( array, from, to);
          }
      }
  }
}
