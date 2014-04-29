/* 
 * File:   bintree.h
 * Author: Erick House, Brandan Haertel
 */

#ifndef BINTREE_H
#define BINTREE_H
#include <iostream>
#include "recordable.h"


using namespace std;
//---------------------------------------------------------------------------
//BinTree class:
// -Binary search tree, allows searching and inserting Recordables.

//Assumptions: 
// -array size is set to 100, every unused element is set to null
// -bintree to array, number of elements in the tree will not go over size
//      of the array
// -user must allocate memory to the heap when passing in pointers to nodes
//      and node datas
// -user is responsible for freeing memory in the array after using the 
//      bsToArray method
// -build tree is not part of the binTree class. User must create their own
//      method using the public function, insert.
// -it is assumed that the array being passed into arraytoBSTree is sorted
//      from smallest to largest
// -when binTree is initially built, it is not guaranteed balanced, 
//      unless using the arrayToBSTree method.
//---------------------------------------------------------------------------


class BinTree {
    
    // cout << displays contents of tree in sorted order.
    // format: (Recordable, space) for each item in tree.
    friend ostream& operator<<( ostream&, const BinTree& );
    
public:
    
    // Constructor
    // creates an empty tree, sets root to null
    BinTree();

    // destructor, calls makeEmpty
    ~BinTree(); 

    // Public Methods
    // true if tree is empty, otherwise false
    bool isEmpty() const; 
    
    // makes the tree empty, so isEmpty returns true
    void makeEmpty(); 
    
    // inserts a nodedata by definition of a binary tree
    bool insert(Recordable*); 
    
    // finds the given nodedata, if true, it sets the passed pointer to the 
    // the found nodedata
    bool retrieve(const Recordable &, Recordable*&) const;
    
    // cout<< recursive call initializer. calls inOder(Node*)
    void inOrder() const;

private:
    
    struct Node { 
        Recordable* data;  // pointer to data object 
        Node* left;     // left subtree pointer
        Node* right;     // right subtree pointer
    }; 
    
    Node* root;                 // root of the tree
        
    // recursive makeEmpty helper, deletes Recordable & Nodes.
    void destroyTree(Node* d);
    
    // recursive inOrder() helper,
    void inOrder(Node*) const;
};      

#endif // BINTREE_H