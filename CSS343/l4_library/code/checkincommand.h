// checkinC=command.h

#ifndef CHECKINCOMMAND_H
#define	CHECKINCOMMAND_H
#include "command.h"
#include "itemfactory.h"
#include "recordable.h"
#include "item.h"
#include "patron.h"
#include "checkin.h"

// CheckinCommand class
//      - inherits from Command
//      - handles checkin in processs

using namespace std;

class CheckinCommand : public Command{
public:

    //--------------------------------------------------------------------------
    // constructor
    CheckinCommand();
    
    //--------------------------------------------------------------------------
    // destructor
    virtual ~CheckinCommand();
    
    //--------------------------------------------------------------------------
    // command function that completes a checkin process
    virtual void takeCareofBusiness(Library& ,string);
    
private:

    //--------------------------------------------------------------------------
    // item factory member
    ItemFactory factory;

};

#endif	/* CHECKINCOMMAND_H */

