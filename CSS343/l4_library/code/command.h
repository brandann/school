/* 
 * File:   Command.h
 * Author: brandan
 *
 * Created on March 7, 2014, 3:18 PM
 */

#ifndef COMMAND_H
#define	COMMAND_H
#include <string>
#include "library.h"

using namespace std;
// Command class:
//   - abstract class used for forcing the take care of buniness command

class Command {
    
    
public:
    Command();
    //-----------------------------------------------------------------------
    virtual ~Command();
    //-----------------------------------------------------------------------
    // a command that is basically telling the subclass to do it's intended
    // purpose: printcommand class.takecareofbusiness prints out the library
    virtual void takeCareofBusiness(Library& ,string) = 0;
private:

};

#endif	/* COMMAND_H */
