//system.cpp

#include "system.h"

//------------------------------------------------------------------------------
// constructor
System::System()
{
    // do nothing
}

//------------------------------------------------------------------------------
// destructor
System::~System()
{
    // do nothing
}

//------------------------------------------------------------------------------
// addFile (public)
// parses a file for commands
void System:: addFile(ifstream& infile){
    
    // check if file is valid
    if(!infile){
        cout << "cannot open file" << endl;
        return;
    }
    string s;

    // parse file
    while(!infile.eof()) {
        getline(infile, s);
        
        if(s.length() > 0){
            // execute each line of commands
            factory.parseCommand(library, s);
        }
    }
}
