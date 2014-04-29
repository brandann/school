//checkin.cpp

#include "checkin.h"

using namespace std;

//------------------------------------------------------------------------------
// constructor
CheckIn::CheckIn(){
    // do nothing
}

//------------------------------------------------------------------------------
// destructor
CheckIn::~CheckIn(){
    item = NULL;
}

//------------------------------------------------------------------------------
// getType (public)
// returns a type Char
char CheckIn::getType() const{ // virtual
  return 'R';
}

//------------------------------------------------------------------------------
// create (public)
// returns a new instance of this class
Transaction* CheckIn::create(){
    return new CheckIn;
}

//------------------------------------------------------------------------------
// toString (public)
// returns a string representation of this object
string CheckIn::toString()const{
    return "Return";
}

//------------------------------------------------------------------------------
// setItem (public)
// sets item member to it
void CheckIn::setItem(Item* it){
    item = it;
}
