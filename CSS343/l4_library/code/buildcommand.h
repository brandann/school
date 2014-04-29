/* 
 * File:   BuildCommand.h
 * Author: brandan
 *
 * Created on March 7, 2014, 3:38 PM
 */

#ifndef BUILDCOMMAND_H
#define	BUILDCOMMAND_H
#include "command.h"
#include "itemfactory.h"

//BuildCommand:inherits from Command
//   - When given the correct character, buildcommand calls item factory to
//         build a new object of the type defined in the string that is passed
//          to it
// Assumptions:
//   - the string passed to build command will have a character of a capital
//      letter or a character int

class BuildCommand: public Command {
    
public:
    
    BuildCommand();//constuctor
    //-----------------------------------------------------------------------
    virtual ~BuildCommand();// destructor
    //-----------------------------------------------------------------------
    // a method for inacting buildCommand's purpose, building objects
    // dynamically
    virtual void takeCareofBusiness(Library& ,string);
    
private:
    ItemFactory factory; //instance of itemfactory for building new objects
    

};

#endif	/* BUILDCOMMAND_H */

