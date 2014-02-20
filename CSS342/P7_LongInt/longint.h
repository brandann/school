#ifndef LONGINT_H
#define LONGINT_H
#include <iostream>
#include "deque.h"

using namespace std;

class LongInt {
    public:

        friend istream& operator>>(istream &in, LongInt &rhs);
        friend ostream& operator<<(ostream &os, LongInt &rhs);
        // Constructor
        LongInt( const string str );                                        //DONE
        LongInt( const LongInt & rhs );                                     //
        LongInt( );                                                         //DONE
        // Destructor
        ~LongInt( );                                                        //DONE

        // Arithmetic binary operators
        LongInt operator+( const LongInt &rhs );                            //DONE
        LongInt operator-( const LongInt &rhs );                            //

        // assignment operators
        const LongInt &operator=( const LongInt &rhs );                     //DONE

        // Logical binary operators
        bool operator< ( const LongInt & rhs ) const;                       //DONE
        bool operator<=( const LongInt & rhs ) const;                       //DONE
        bool operator> ( const LongInt & rhs ) const;                       //DONE
        bool operator>=( const LongInt & rhs ) const;                       //DONE
        bool operator==( const LongInt & rhs ) const;                       //DONE
        bool operator!=( const LongInt & rhs ) const;                       //DONE

    private:
        Deque<char> digits;
        bool negative; //true if neg
};
#include "LongInt.cpp"
#endif
