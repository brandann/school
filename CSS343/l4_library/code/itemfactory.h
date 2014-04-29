//itemFactory.h

//--------------------------------------------------------------------------
// HashTable
// Designed by: Brandan Haertel, Erick House, Jennifer Kowalsky,
// 		Gary Mixson, Binh Nguyen
// Implemented by:
// Performs hashing actions on Hashable Items.

#ifndef HASHTABLE_H
#define HASHTABLE_H

#include "recordable.h"
#include <typeinfo>
#include "patron.h"
#include "fiction.h"
#include "periodical.h"
#include "youth.h"
#include <stddef.h>
#include "errorcommand.h"
using namespace std;

//ItemFactory class:
//    - A class filled with instances of concrete base classes of recordable
//          the instances act as templates for create it to build from
//    - the hash method looks at the first character in the string passed
//          then hashtable looks it up to see if that character matches one
//          of the instances that have been hard coded
//    - create it returns a recordable pointer to the created object

//ItemFactory Assumptions:
//    - the string given, will always give a character that can be hashed
//    - the hashed character will not exceed 100
//    - accurate creating of objects depends on being given a Capital letter
//          or a character number



const int size = 100; //size of the array

class ItemFactory
{
    public:
        //-----------------------------------------------------------
        // constructor
        ItemFactory();

        //-----------------------------------------------------------
        // destructor
        ~ItemFactory();

        //-----------------------------------------------------------
        // createIt is a genaric method for calling the create method
        // on one of the concrete class of recordable. Thing string that is
        // pass is a full line from the inputfile, which is parsed later by
        // a different class
        Recordable* createIt(string) const;
    
    protected:

    private:
        // a hash methods that converts a char into an int
        int hash(char) const;
    
        // a hashtable filled with templates for all concrete classes of
        // recordable
        Recordable* hashTable[size];
    
        // an instance of patron used as a template. It does not hash like the
        // other recordables so it was given a special case. Building a patron
        // is done with an int character
        Patron *person;
				

};

#endif // HASHTABLE_H
