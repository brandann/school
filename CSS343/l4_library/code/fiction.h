    //fiction.h

    #ifndef FICTION_H
    #define FICTION_H

    #include "book.h"
    #include <iostream>
    #include <sstream>
    #include <iomanip>

//Fiction class: inherits from book
//    - comparison operators compare book author, then title
//    - a class for storing member variables inside a colletion class: Libaray
//    - a simple class with getters, setters, and comparisons

class Fiction : public Book
{
public:
    
    //--------------------------------------------------------------------------
    // constructor
    Fiction();

    //--------------------------------------------------------------------------
    // destructor
    ~Fiction();

    //--------------------------------------------------------------------------
    // Returns the type of the book's publication.
    char getType()const; // always returns "F"

    //--------------------------------------------------------------------------
    // Returns true is this and rhs are equal
    // Compares Author and then Title, used for sorting in Bintree class
    virtual bool operator==( const Recordable& rhs) const;
    
    //--------------------------------------------------------------------------
    // Returns true if this is greater then rhs
    // Compares Author and then Title, used for sorting in Bintree class
    virtual bool operator>( const Recordable& rhs) const;
    
    //--------------------------------------------------------------------------
    // Returns true if this is less than rhs
    // Compares Author and then Title, used for sorting in Bintree class
    virtual bool operator<( const Recordable& rhs) const;
    
    //--------------------------------------------------------------------------
    // Returns all member vaiables in a Youth Object to a string
    //  return "Y " + author + ", " + title + ", " + year;
    string toString() const;
    
    //--------------------------------------------------------------------------
    // takes a file line as a string, parses the line, and sets the data to
    // the correct member vaiables
    virtual void setData(string);
    
    //--------------------------------------------------------------------------
    // Used by the ItemFactory to dynamically create Objects based on the
    // Youth type character 'F'
    virtual Recordable* create() const;
    
    //--------------------------------------------------------------------------
    // used for printing out a patron's history, using a specific format
    string toStringHistory() const;
    
    //--------------------------------------------------------------------------
    // used in checkin and checkout. A temp object is created for searching,but
    // the data comes in backwards so the standard setData does not work.
    virtual void setTempData(string);

protected:

private:

    };

    #endif // FICTION_H
