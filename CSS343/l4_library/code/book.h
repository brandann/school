//book.h

#ifndef BOOK_H
#define BOOK_H

#include "item.h" 
#include <string>
#include <iostream>
#include <typeinfo>


class Book : public Item
{
public:
    //--------------------------------------------------------------------------
    // constructor
    Book();

    //--------------------------------------------------------------------------
    // destructor
    ~Book();

    //--------------------------------------------------------------------------
    // Returns the author of the book.
    string getAuthor();

protected:
    string author; // author of the book
    
private:
};

#endif // BOOK_H
