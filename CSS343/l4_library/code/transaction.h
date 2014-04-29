//transaction.h

#ifndef TRANSACTION_H
#define TRANSACTION_H

#include <iostream>
#include "item.h"

// Transaction class
//      - Base class for transactionable items (Checkin, Checkout, etc.)

class Transaction
{
public:
    //--------------------------------------------------------------------------
    // constructor
    Transaction();

    //--------------------------------------------------------------------------
    // destructor
    virtual ~Transaction();

    //--------------------------------------------------------------------------
    // Returns the type of the transaction object
    virtual char getType() const = 0;

    //--------------------------------------------------------------------------
    // returns a string of the transaction object
    virtual string toString()const = 0;

    //--------------------------------------------------------------------------
    // sets item member
    virtual void setItem(Item*) = 0;

    //--------------------------------------------------------------------------
    // item member
    Item *item;

protected:

private:
    
    //--------------------------------------------------------------------------
    // returns a new instance of transaction*
    virtual Transaction* create() = 0;
};

#endif // TRANSACTION_H
