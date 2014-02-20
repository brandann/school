#include <vector>
#include <math.h>
#include <iostream>
using namespace std;

template <class Comparable>
void mergesort( vector<Comparable> &a ) {

    int first1, last1, first2, last2;
    int size = a.size( );
    int steps = log2(size);
    
    //creat the only temorary array
    vector<Comparable> b( size );  
   
    //creat pointers for array manipulation
    vector<Comparable>* a_ptr = &a;
    vector<Comparable>* b_ptr = &b;
    vector<Comparable>* t_ptr;
    
    //if 2^steps is < size, increment steps
    //if size is > 2^n - 2^n/2 increment steps by 2
    if(pow(2,steps) < size)
        if(size  > pow(2,steps) - pow(2,steps)/2)
            steps+=2;
        else
            steps++;

    //start mergesort algorithm
    for (int i = 1; i <= steps; i++)
    {
        int twoi = pow(2,i);
        
        //for each mergesort
        for (int pair = 0; pair < size; pair += twoi)
        {
            //set firsts and lasts
            first1 = pair;              last1  = pair + twoi/2 -1;
            first2 = pair + twoi/2;     last2  = pair + twoi - 1;
            
            //set index
            int index = first1;
            
            //make sure that lasts are not out of bounds
            if(last1 >= size)
                last1 = size-1;
            if(last2 >= size)
                last2 = size-1;

            //merging
            for (; first1 <= last1 && first2 <= last2; ++index)
                    b_ptr->at(index) = ( a_ptr->at(first1) < a_ptr->at(first2) ) ? a_ptr->at(first1++) : a_ptr->at(first2++);

            for ( ; first1 <= last1; ++first1, ++index )      // exhaust the left array
                    b_ptr->at(index) = a_ptr->at(first1);

            for ( ; first2 <= last2; ++first2, ++index )      // exhaust the right array
                    b_ptr->at(index) = a_ptr->at(first2);
        }
        
        //swap arrays
        t_ptr = b_ptr;
        b_ptr = a_ptr;
        a_ptr = t_ptr;

    }
    if(steps%2!=0)
    {
        for ( int index = 0; index <= size - 1; ++index )     // write them back to original
                a_ptr->at(index) = b_ptr->at(index);
    }
    //return;
}
