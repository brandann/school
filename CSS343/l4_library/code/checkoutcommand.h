//CheckoutCommand.h

#ifndef CHECKOUTCOMMAND_H
#define	CHECKOUTCOMMAND_H
#include "command.h"
#include "itemfactory.h"
#include "recordable.h"
#include "item.h"
#include "patron.h"
#include "checkout.h"

// CheckoutCommand class
//      - inherits from Command
//      - handles checking out an item.

using namespace std;

class CheckoutCommand : public Command{
public:

    //--------------------------------------------------------------------------
    // constructor
    CheckoutCommand();
    
    //--------------------------------------------------------------------------
    // destructor
    virtual ~CheckoutCommand();
    
    //--------------------------------------------------------------------------
    // command method handles checking out an item
    virtual void takeCareofBusiness(Library& ,string);
    
private:

    //--------------------------------------------------------------------------
    // itemFactory member
    ItemFactory factory;

};

#endif	/* CHECKOUTCOMMAND_H */

