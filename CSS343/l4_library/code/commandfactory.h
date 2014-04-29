//commandfactory.h

#ifndef COMMANDFACTORY_H
#define	COMMANDFACTORY_H
#include "library.h"
#include "command.h"
#include "buildcommand.h"
#include "errorcommand.h"
#include "historycommand.h"
#include "printcommand.h"
#include "checkincommand.h"
#include "checkoutcommand.h"

//CommandFactory class
//    - hashes and retreives a command for execution
//    - Assumptions:
//          any numerial commands are ment as the begining of a new
//          Patron command, so the hash function accounts for this and
//          gives a new person object instead of an ErrorCommand

const int CF_SIZE = 100;

class CommandFactory {
public:
    //--------------------------------------------------------------------------
    // constructor
    CommandFactory();
    
    //--------------------------------------------------------------------------
    // destructor
    virtual ~CommandFactory();
    
    //--------------------------------------------------------------------------
    // parseCommand
    // parses and executes the command of the string parameter using the
    // library parameter
    void parseCommand(Library&, string);
    
private:

    //--------------------------------------------------------------------------
    // hashes a char for commandHash index
    int hash(char) const;
    
    //--------------------------------------------------------------------------
    // contains all command objects
    Command* commandHash[CF_SIZE];
};

#endif	/* COMMANDFACTORY_H */

