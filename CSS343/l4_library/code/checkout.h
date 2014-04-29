//checkout.h

#ifndef CHECKOUT_H
#define CHECKOUT_H

#include <iostream>
#include "transaction.h"

// CheckOut class
//      - inherits from Transaction
//      - is a container for an Item pointer to be assigned to a Patrons
//          history

class CheckOut : public Transaction
{
public:
    
    //------------------------------------------------------------------------------
    // constructor
    CheckOut();
    
    //------------------------------------------------------------------------------
    // destructor
    ~CheckOut();
    
    //------------------------------------------------------------------------------
    // returns a Char identifier of this class
    virtual char getType() const;
    
    //------------------------------------------------------------------------------
    // returns a string representation of this object
    string toString() const;
    
    //------------------------------------------------------------------------------
    // sets the member item to it
    virtual void setItem(Item*);
    
    //------------------------------------------------------------------------------
    // returns a new instance of this class
    Transaction* create();
    
protected:
    
private: 
   
    
};

#endif // CHECKOUT_H
