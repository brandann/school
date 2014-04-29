//patron.h

#ifndef PATRON_H
#define	PATRON_H
#include "person.h"

// Patron class
//      - inherits from Person
//      - Patron object for the library, able to checkin and checkout items
//          from the Library.
//      - Patron contains a history, inherited from person, of all 
//          Library activity.

using namespace std;

class Patron : public Person{
public:

    //--------------------------------------------------------------------------
    // constructor
    Patron();
    
    //--------------------------------------------------------------------------
    // destructor
    virtual ~Patron();
    
    //--------------------------------------------------------------------------
    // create (public)
    // returns a new instance of this class
    virtual Recordable* create() const;
    
    //--------------------------------------------------------------------------
    // getType (public)
    // returns a Char representation of this class
    virtual char getType() const;
    
private:

};

#endif	/* PATRON_H */

