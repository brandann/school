//person.h

#ifndef PERSON_H
#define PERSON_H

#include "recordable.h"
#include "transaction.h"
#include "item.h"
#include <sstream>
#include <deque>
#include <typeinfo>
#include <iomanip>
#include <string>
#include <vector>

// Person class
//      - Creates a Person object, representing the parent class if a 
//          user of the Library.

using namespace std;

class Person : public Recordable {
public:

    //--------------------------------------------------------------------------
    // constructor
    Person();

    //--------------------------------------------------------------------------
    // destructor
    virtual ~Person();

    //--------------------------------------------------------------------------
    // getName (Public)
    // Returns the name of the Person.
    string getName() const;

    //--------------------------------------------------------------------------
    // getID(Public)
    // Returns the ID of the Person.
    int getId() const;

    //--------------------------------------------------------------------------
    // toString(Public)
    // Returns the string representation of the person
    virtual string toString() const;

    //--------------------------------------------------------------------------
    // operator== (Public)
    // Returns true is this and rhs are equal
    virtual bool operator==( const Recordable& rhs) const;

    //--------------------------------------------------------------------------
    // operator> (Public)
    // Returns true if this is greater then rhs
    virtual bool operator>( const Recordable& rhs) const;

    //--------------------------------------------------------------------------
    // operator< (Public)
    // Returns true if this is less than rhs
    virtual bool operator<( const Recordable& rhs) const;

    //--------------------------------------------------------------------------
    // setData (public)
    // sets the persons name and ID number
    virtual void setData(string);

    //--------------------------------------------------------------------------
    // setName (public)
    // sets the persons name
    void setName(string);
    
    //--------------------------------------------------------------------------
    // setId (public)
    // sets the persons ID
    void setID(int);
        
    //--------------------------------------------------------------------------
    // getHistory()
    // return container of pointers to all transactions
    void printHistory() const;
        
    //--------------------------------------------------------------------------
    // checkOut (public)
    // handles checkout process, adds transaction to history and adds item 
    // to checkout
    void checkOut(Transaction*);
    
    //--------------------------------------------------------------------------
    // checkIn (public)
    // handles checkin process, adds transaction to history and removes
    // item from checkout
    void checkIn(Transaction*);   
  
protected:

    //--------------------------------------------------------------------------
    // persons name
    string name;
    
    //--------------------------------------------------------------------------
    // persons ID
    int ID;
  
private:    
        
        
    //--------------------------------------------------------------------------
    // holds all the transaction objects that are associated with this person
    vector<Transaction*> history;
    
    //--------------------------------------------------------------------------
    // holds pointers to all the items this user currently has checked out
    vector<Item*> checkout;
  
};

#endif
