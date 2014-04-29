//patron.cpp

#include "patron.h"

//--------------------------------------------------------------------------
// constructor
Patron::Patron() : Person(){
    // do nothing
}

//--------------------------------------------------------------------------
// destructor
Patron::~Patron() {
    // do nothing
}

//--------------------------------------------------------------------------
// create (public)
// returns a new instance of this class
Recordable* Patron::create() const{
    Patron *p = new Patron();
    return p;
}

//--------------------------------------------------------------------------
//getType (public)
// returns a Char representation of this class
char Patron::getType() const {
    return 'A';
}
