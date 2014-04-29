//checkin.h

#ifndef CHECKIN_H
#define CHECKIN_H

#include <iostream>
#include "transaction.h"

// CheckIn class
//      - inherits from Transaction
//      - is a container for an Item pointer to be assigned to a Patrons
//          history

class CheckIn: public Transaction
{
public:

    //--------------------------------------------------------------------------
    // constructor
    CheckIn();
    
    //--------------------------------------------------------------------------
    // destructor
    ~CheckIn();
    
    //--------------------------------------------------------------------------
    // returns a Char type identifier for this class
    virtual char getType() const;
    
    //--------------------------------------------------------------------------
    // returs a string representation of this object
    string toString() const;
    
    //--------------------------------------------------------------------------
    // sets the objects member item
    virtual void setItem(Item*);
    
    //--------------------------------------------------------------------------
    // returns a new instance of this object
    Transaction* create();
    
protected:

private:

    
};

#endif // CHECKIN_H
