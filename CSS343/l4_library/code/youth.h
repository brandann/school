//youth.h

#ifndef YOUTH_H
#define YOUTH_H

#include "book.h"
#include <sstream>
#include <iomanip>

//Youth class: inherits from book
//    - comparison operators compare book title, then author
//    - a class for storing member variables inside a colletion class: Libaray
//    - a simple class with getters, setters, and comparisons

class Youth : public Book
{
public:

    //--------------------------------------------------------------------------
    // constructor
    Youth();
    
    //--------------------------------------------------------------------------
    // destructor
    ~Youth();
    
    //--------------------------------------------------------------------------
    // Returns the type of the book's publication.
    char getType() const; // always returns "Y"
    
    //--------------------------------------------------------------------------
    // Returns true is this and rhs are equal
    // Compares Title and then Author, used for sorting
    virtual bool operator==( const Recordable& rhs) const;
    
    //--------------------------------------------------------------------------
    // Returns true if this is greater then rhs
    // Compares Title and then Author, used for sorting
    virtual bool operator>( const Recordable& rhs) const;
    
    //--------------------------------------------------------------------------
    // Returns true if this is less than rhs
    // Compares Title and then Author, used for sorting
    virtual bool operator<( const Recordable& rhs) const;
    
    //--------------------------------------------------------------------------
    // Returns all member vaiables in a Youth Object to a string
    //  return "Y " + title + ", " + author + ", " + year;
    string toString() const;
    
    //--------------------------------------------------------------------------
    // takes a file line as a string, parses the line, and sets the data to
    // the correct member vaiables
    virtual void setData(string);
    
    //--------------------------------------------------------------------------
    // Used by the ItemFactory to dynamically create Objects based on the
    // Youth type character 'Y'
    virtual Recordable* create() const;
    
    //--------------------------------------------------------------------------
    // used for printing out a patron's history, using a specific format
    virtual string toStringHistory() const;
    
    //--------------------------------------------------------------------------
    // used in checkin and checkout. A temp object is created for searching,but
    // the data comes in backwards so the standard setData does not work. 
    virtual void setTempData(string);
    
protected:

private:
  
};

#endif // YOUTH_H
