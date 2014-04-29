//Library.h

#ifndef LIBRARY_H
#define	LIBRARY_H
#include "bintree.h"

//Library class:
//    - a  intermediate container class that stores all the recordables
//    - stores a hash of binary trees
//    - has a few methods that are used by other classes
//    - methods are intermediate, most of the logic is stored somewhere else

//Assumptions
//    - things will not hash outside the size of the hashtable
 


const int SIZE = 26;

class Library {
public:
    //constructor
    Library();
    
    //----------------------------------------------------------------------
    // destructor
    ~Library();
    
    //----------------------------------------------------------------------
    // inserts a recordable item into the hashed tree
    bool insert(Recordable*);
    
    //----------------------------------------------------------------------
    // use by print command to print all of the trees in the "forest"
    // aka the hashtable
    void print(char) const;
    
    //----------------------------------------------------------------------
    // returns a pointer to a recordable, if the passed pointer is found in the
    // tree 
    Recordable* retreive(Recordable*) const;
    
private:
    
    // hashes the char into an int
    int hash(char) const;
    
    // an array of pointers to root nodes of binary trees
    BinTree* forest[SIZE];

};

#endif	/* CATTABLE_H */

