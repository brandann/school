//CheckoutCommand.cpp


#include "checkoutcommand.h"

using namespace std;

//------------------------------------------------------------------------------
// constructor
CheckoutCommand::CheckoutCommand() {
}

//------------------------------------------------------------------------------
// destructor
CheckoutCommand::~CheckoutCommand() {
}

//------------------------------------------------------------------------------
// takeCareofBusiness (public)
// command method handles checking out an item
void CheckoutCommand::takeCareofBusiness(Library& lib, string str){
    
    // remove char C
    stringstream ss(str);
    ss.get();
    ss.get();
    
    // build a new patron
    // get the next string
    // set the patron ID
    int id;
    ss >> id; //get ID
    ss.get(); //space after ID
    Person *tempPatron = new Patron;
    
    tempPatron->setID(id);
    Recordable *tempRecordablePatron = tempPatron;
    
    
    //patron != NULL even when retreive doesnt find anything in the tree
    Recordable *patron = lib.retreive(tempRecordablePatron);
    
    delete tempPatron;
    tempRecordablePatron = NULL;
    tempPatron = NULL;   
    
    string data;
    getline(ss, data);
    Recordable *RecordableItem = factory.createIt(data);
    Item *tempItem = dynamic_cast<Item*>(RecordableItem);
    
    // remove the F and the H, Call setTmpdata(string)
    // we dont handle the case where H could be something else
    stringstream ss2(data); 
    ss2.get(); ss2.get();
    
    // handle format
    // implentation only handles Hardcopies, so check to make sure
    // that the item is a hard copy (H)
    char c;
    ss2 >> c;
    if(c != 'H'){
        // invalid format
        delete tempItem;
        RecordableItem = NULL;
        tempItem = NULL;
        cout << "invalid format: " << c << endl;
        return;
    }
    ss2.get();
    
    getline(ss2, data);
    
    //handles the error when we try and set data to a NULL object
    // this happens when we hit error command in item factory
    // create, in error command returns a NULL
    if (tempItem == NULL) {
        delete tempItem;
        tempItem = NULL;
        return;
    }
    tempItem->setTempData(data);   
   
    //item != NULL even when retreive doesnt find anything in the tree
    Recordable *item = lib.retreive(tempItem); //item is now a Recordable
    delete tempItem;
    tempItem = NULL;
    RecordableItem = NULL;
    
    if(item == NULL || patron == NULL){
        if(item == NULL){
            cout << "Sorry, that item does not exist" << endl;
        }
        if(patron == NULL){
            cout << "Sorry, that patron does not exist" << endl;
        }
        item = NULL;
        patron = NULL;
        return;
    }
    
    //make sure enough books are available
    //return if false
    Item *item_cast = dynamic_cast<Item*>(item);
    
    if(item_cast->getQuantity() == 0){
        item = NULL;
        patron = NULL;
        cout << "Sorry, that item is no longer available" << endl;
        return;
    }
    
    //make checkout object(item)
    Transaction *checkout = new CheckOut;
    checkout->setItem(item_cast);
    //add checkout object to patron history
    Patron *patron_again = dynamic_cast<Patron*>(patron);
    patron_again->checkOut(checkout);
    
}
