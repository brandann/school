//system.h

#ifndef SYSTEM_H
#define SYSTEM_H
#include <fstream>
#include <string>
#include <iostream>
#include "library.h"
#include "commandfactory.h"
#include "periodical.h"

// System class
//      - handles file parsing
//      - contains the Library and CommandFactory

using namespace std;


class System
{
public:
    //--------------------------------------------------------------------------
    // constructor
    System();

    //--------------------------------------------------------------------------
    // destructor
    ~System();

    //--------------------------------------------------------------------------
    // parse a input files items, perosons, and commands
    void addFile(ifstream&);

protected:

private:
    
    //--------------------------------------------------------------------------
    // database container
    Library library;
    
    //--------------------------------------------------------------------------
    // command container
    CommandFactory factory;
};
#endif // SYSTEM_H
