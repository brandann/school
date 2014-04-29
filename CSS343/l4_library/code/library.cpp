//library.cpp

#include "library.h"
#include <iostream>
using namespace std;

Library::Library() {
    
    // set all to NULL
    for (int i = 0; i < SIZE; i++){
        forest[i] = NULL;
    }
    
    // hard code only the trees that are needed
    forest[hash('A')] = new BinTree; // patron
    forest[hash('F')] = new BinTree; // fiction
    forest[hash('P')] = new BinTree; // periodical
    forest[hash('Y')] = new BinTree; // youth
    
}

//---------------------------------------------------------------------------
// destructor
Library::~Library() {
    //delete all trees in the forest
    for (int i = 0; i < SIZE; i++) {
        delete forest[i];
        forest[i] = NULL;
    }
}

//---------------------------------------------------------------------------
// insert (Public)
bool Library::insert(Recordable* r) {
    forest[hash(r->getType())]->insert(r);
    return true;
}

//---------------------------------------------------------------------------
// hash (Private)
int Library::hash(char c) const {
    return c - 'A';
}

//---------------------------------------------------------------------------
// print (Public)
// used by print command
void Library::print(char c) const{
    if(forest[hash(c)] != NULL && !forest[hash(c)]->isEmpty()){
        forest[hash(c)]->inOrder();
    }
}

//---------------------------------------------------------------------------
// retreive (Public)
// validation that things being checked out are in the tree
Recordable* Library::retreive(Recordable* r) const {
    Recordable *ptr;
    if(forest[hash(r->getType())]->retrieve(*r,ptr)){
        return ptr;
    }
    else{
        ptr = NULL; // a special case where the tree was not returning NULL
        return ptr; // when it did not find the recordable
    }
    
}

