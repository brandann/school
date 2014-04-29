//checkout.cpp

#include "checkout.h"

using namespace std;

//------------------------------------------------------------------------------
// constructor
CheckOut::CheckOut(){
}

//------------------------------------------------------------------------------
// destructor
CheckOut::~CheckOut(){
    item = NULL;
}

//------------------------------------------------------------------------------
// getType (public)
// returns a Char identifier of this class
char CheckOut::getType() const{ // virtual
    return 'C';
}

//------------------------------------------------------------------------------
// create (public)
// returns a new instance of this class
Transaction* CheckOut::create(){
    return new CheckOut;
}

//------------------------------------------------------------------------------
// toString (public)
// returns a string representation of this object
string CheckOut::toString() const{
    return "CheckOut";
}

//------------------------------------------------------------------------------
// setItem (public)
// sets the member item to it
void CheckOut::setItem(Item* it){
    item = it;
}
