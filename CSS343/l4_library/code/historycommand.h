// historycommand.h

#ifndef HISTORYCOMMAND_H
#define	HISTORYCOMMAND_H
#include "command.h" 
#include "patron.h"
#include <sstream>
#include <iomanip>

// HistoryCommand class
//      - inherits from Command
//      - handles printing out a Patrons history

class HistoryCommand : public Command{
public:

    //--------------------------------------------------------------------------
    // constructor
    HistoryCommand();
    
    //--------------------------------------------------------------------------
    // destructor
    virtual ~HistoryCommand();
    
    //--------------------------------------------------------------------------
    // takeCareofBusiness (public)
    // method that handles printing out a Patrons history
    virtual void takeCareofBusiness(Library&, string);

private:

};

#endif	/* HISTORYCOMMAND_H */

