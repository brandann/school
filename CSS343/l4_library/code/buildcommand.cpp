//buildcommnad.cpp

#include "buildcommand.h"

BuildCommand::BuildCommand():Command() {
    // do nothing
}

//----------------------------------------------------------------------------
BuildCommand::~BuildCommand() {
    // do nothing
}

//----------------------------------------------------------------------------
 void BuildCommand:: takeCareofBusiness(Library& lib ,string str){
     
     Recordable *r = factory.createIt(str); //return an instance of the object
     // in itemfactory
     
     r->setData(str);// set the data required for that object
     lib.insert(r);  // insert the object into the tree
}



