//itemFactory.cpp

#include "itemfactory.h"
using namespace std;

//----------------------------------------------------------------------------
// constructor
ItemFactory::ItemFactory()
{
    //sets every pointer to NULL
    for (int i = 0; i < size; i++) {
        hashTable[i] = NULL;
    }
    
    // buildable items in the factory that can be stored in Library
    hashTable['F'-'A'] = new Fiction();
    hashTable['P'-'A'] = new Periodical();
    hashTable['Y'-'A'] = new Youth();
    
    //special case because we get a number instead of a capital letter
    person = new Patron();
    
    // sets all non template pointers to errorcommand to handle building
    // objects that cannot exist
    for(int i = 0; i < size; i++){
        if(hashTable[i] == NULL){
            hashTable[i] = new ErrorCommand();
        }
    }
}

//----------------------------------------------------------------------------
// destructor
ItemFactory::~ItemFactory()
{
        //removes all items from the hash
       for (int i = 0; i < size; i++) {
          delete hashTable[i];
          hashTable[i] = NULL;
       }
       // removes the special case
       delete person;
       person = NULL;
       
}

//----------------------------------------------------------------------------
// hash (Private)
int ItemFactory:: hash(char c) const{
      return c-'A';
}

//----------------------------------------------------------------------------
// hash (Public)
Recordable* ItemFactory::createIt(string str) const {
    
   // get the first character in the string
   int subscript = hash(str[0]);
    
   // handles the person special case
    if (subscript < 0){
       return person->create();
    }
    
   //return an instance of the hashed class
   return hashTable[subscript]->create();
}


