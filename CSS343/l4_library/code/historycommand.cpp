//HistoryCommand.cpp

#include "historycommand.h"


using namespace std;

//------------------------------------------------------------------------------
// constructor
HistoryCommand::HistoryCommand() {
}

//------------------------------------------------------------------------------
// destructor
HistoryCommand::~HistoryCommand() {
}

//------------------------------------------------------------------------------
// takeCareofBusiness (public)
// method that handles printing out a Patrons history
void HistoryCommand::takeCareofBusiness(Library &lib, string str){
    
    cout<<endl; // for output separation
    
    Patron *p = new Patron;
    std::stringstream ss(str);
    int id;
    //get command
    ss.get();
    
    //get space
    ss.get();
    
    //get ID
    ss >> id;
    //find the patron in the library
    p->setID(id);
    Recordable *patronTemp = lib.retreive(p);
    delete p;
    p = NULL;
        

    //if patron is found in the library
    if(patronTemp != NULL){
        Patron *patron = dynamic_cast<Patron*>(patronTemp);
        
        //print header
        cout << "*** Patron ID = ";
        cout << patron->getId() << "  ";
        cout << patron->getName() << endl;
        
        patron->printHistory();
        cout << "\n" << endl;
    }
    
}
