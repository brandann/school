//periodical.h


#ifndef PERIODICAL_H
#define PERIODICAL_H

#include <iostream>
#include "item.h"
#include <typeinfo>

//Periodical class: inherits from Item
//    - comparison operators compare year, month, and then title
//    - a class for storing member variables inside a colletion class: Libaray
//    - a simple class with getters, setters, and comparisons

class Periodical : public Item
{
public:
    //--------------------------------------------------------------------------
    // constructor
    Periodical();

    //--------------------------------------------------------------------------
    // destructor
    ~Periodical();

    //--------------------------------------------------------------------------
    //returns month
    string getMonth() const;

    //--------------------------------------------------------------------------
    // always returns "P"
    char getType() const;

    //--------------------------------------------------------------------------
    // Returns true is this and rhs are equal
    // Compares year, month, then title
    virtual bool operator==( const Recordable& rhs) const;

    //--------------------------------------------------------------------------
    // Returns true if this is greater then rhs

    virtual bool operator>( const Recordable& rhs) const;

    //--------------------------------------------------------------------------
    // Returns true if this is less than rhs
    // Compares year, month, then title
    virtual bool operator<( const Recordable& rhs) const;

    //--------------------------------------------------------------------------
    // adds all member variables together into a string for printing
    virtual string toString() const;

    //--------------------------------------------------------------------------
    // Used by the ItemFactory to dynamically create Objects based on the
    // Periodical type character 'P'
    virtual Recordable* create() const;

    //--------------------------------------------------------------------------
    // takes a file line as a string, parses the line, and sets the data to
    // the correct member vaiables
    virtual void setData(string str);

    //--------------------------------------------------------------------------
    // used for printing out a patron's history, using a specific format
    virtual string toStringHistory() const;

    //--------------------------------------------------------------------------
    // used in checkin and checkout. A temp object is created for searching,but
    // the data comes in backwards so the standard setData does not work.
    virtual void setTempData(string);

protected:

private:

	string month;       // periodical's month of publication

};

#endif // PERIODICAL_H
