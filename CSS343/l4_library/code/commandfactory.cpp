/* 
 * File:   Command.cpp
 * Author: brandan
 * 
 * Created on March 7, 2014, 3:01 PM
 */

#include "commandfactory.h"

//------------------------------------------------------------------------------
// constructor
CommandFactory::CommandFactory() {

    // initilize all elements to NULL to protect against garbage memory
    for(int i = 0; i < CF_SIZE; i++){
        commandHash[i] = NULL;
    }
    
    // initilize all commands according to their hash value
    commandHash[hash('A')] = new BuildCommand;
    commandHash[hash('C')] = new CheckoutCommand;
    commandHash[hash('D')] = new PrintCommand;
    commandHash[hash('F')] = new BuildCommand;
    commandHash[hash('H')] = new HistoryCommand;
    commandHash[hash('P')] = new BuildCommand;
    commandHash[hash('R')] = new CheckinCommand;
    commandHash[hash('Y')] = new BuildCommand;
    
    // set all NULL indexes to ErrorCommand to handle any "miss hashing"
    for(int i = 0; i < CF_SIZE; i++){
        if(commandHash[i] == NULL){
            commandHash[i] = new ErrorCommand;
        }
    }
}

//------------------------------------------------------------------------------
// destructor
CommandFactory::~CommandFactory() {
    // delete contents of array
    for(int i = 0; i < CF_SIZE; i++){
        delete commandHash[i];
    }
}

//------------------------------------------------------------------------------
// parseCommand (public)
void CommandFactory::parseCommand(Library& lib, string str){
    // get command Char
    char c = str[0];
    
    // if command is a number, assume that a new patron is being added to
    // the library and set its command to 'A'
    if (c - 'A' < 0) {
        c = 'A';
    }
    commandHash[hash(c)]->takeCareofBusiness(lib,str);
}

//------------------------------------------------------------------------------
// hash (private)
int CommandFactory::hash(char c) const{
    return c - 'A';
}
