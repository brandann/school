//printcommand.h

#ifndef PRINTCOMMAND_H
#define	PRINTCOMMAND_H
#include "command.h"
#include <iomanip>

// PrintCommand class
//      - inherits from Command
//      - handles printing the Librarys current status

using namespace std;

class PrintCommand : public Command{
public:

    //--------------------------------------------------------------------------
    // constructor
    PrintCommand();
    
    //--------------------------------------------------------------------------
    // destructor
    virtual ~PrintCommand();
    
    //--------------------------------------------------------------------------
    // takeCareofBusiness (public)
    // method handles printing the library via cout
    virtual void takeCareofBusiness(Library&, string);
    
private:

};

#endif	/* PRINTCOMMAND_H */

