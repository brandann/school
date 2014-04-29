/* 
 * File:   CheckinCommand.cpp
 * Author: brandan
 * 
 * Created on March 8, 2014, 6:16 PM
 */

#include "checkincommand.h"

using namespace std;

//------------------------------------------------------------------------------
// constructor
CheckinCommand::CheckinCommand() {
}

//------------------------------------------------------------------------------
// destructor
CheckinCommand::~CheckinCommand() {
}

//------------------------------------------------------------------------------
// takeCareofBusiness (public)
// command function that completes a checkin process
void CheckinCommand::takeCareofBusiness(Library& lib, string str){
    
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
    
    // determine if patron is in lib, return a pointer if true
    // return if pointers is null
    Recordable *patron = lib.retreive(tempRecordablePatron);
    
    delete tempRecordablePatron;
    tempRecordablePatron = NULL;
    tempPatron = NULL;
    
    //build a new item using item factory (pass a string)
    string data;
    getline(ss, data); //move whole string to data
    Recordable *RecordableItem = factory.createIt(data);
    Item *tempItem = dynamic_cast<Item*>(RecordableItem);
    
    // remove the F and the H, Call setTmpdata(string)
    stringstream ss2(data); //move data back to stringstream
    //cout << data << endl;
    
    //remove char, space, char, space.
    ss2.get(); ss2.get();
    
    // handle format
    // implentation only handles Hardcopies, so check to make sure
    // that the item is a hard copy (H)
    char c;
    ss2 >> c;
    if(c != 'H'){
        // invalid format
        delete tempItem;
        tempItem = NULL;
        cout << "invalid format: " << c << endl;
        return;
    }
    ss2.get();
    
    getline(ss2, data); //move whole string to data
    
    //handles the error when we try and set data to a NULL object
    // this happens when we hit error command in item factory
    // create in error command returns a NULL
    if (tempItem == NULL) {
        delete tempItem;
        tempItem = NULL;
        return;
    }
    tempItem->setTempData(data);
    
    Recordable *item = lib.retreive(tempItem); //item is now a Recordable
    delete tempItem;
    tempItem = NULL;
    if(item == NULL || patron == NULL){
        if(item == NULL){
            cout << "Sorry, that item does not exist"<<endl;
        }
        if(patron == NULL){
            cout << "Sorry, that patron does not exist"<<endl;
        }
        item = NULL;
        patron = NULL;
        return;
    }
    
    //make sure enough books are available
    //return if false
    Item *item_cast = dynamic_cast<Item*>(item);
    if(item_cast->getQuantity() == 5){
        item = NULL;
        patron = NULL;
        cout << "Sorry,"<<item_cast->toString()<<endl;
        cout<<"that item cannot be checked in";
        cout<<endl;
        return;
    }
    
    //make checkout object(item)
    Transaction *checkin = new CheckIn;
    checkin->setItem(item_cast);
    
    //add checkout object to patron history
    Patron *patron_again = dynamic_cast<Patron*>(patron);
    patron_again->checkIn(checkin);
}
