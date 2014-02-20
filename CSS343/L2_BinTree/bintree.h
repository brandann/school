/* 
 * File:   bintree.h
 * Author: Erick House, Brandan Haertel
 */

#ifndef BINTREE_H
#define BINTREE_H
#include <iostream>
#include "nodedata.h"


using namespace std;
//---------------------------------------------------------------------------
//BinTree class:
// -Binary search tree, allows searching and inserting NodeDatas.

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
    // format: (NodeData, space) for each item in tree.
    friend ostream& operator<<( ostream&, const BinTree& );
    
public:
    
    // Constructor
    // creates an empty tree, sets root to null
    BinTree();
    
    // copy constructor
    BinTree(const BinTree &); 

    // destructor, calls makeEmpty
    ~BinTree(); 

    // Public Methods
    // true if tree is empty, otherwise false
    bool isEmpty() const; 
    
    // makes the tree empty, so isEmpty returns true
    void makeEmpty(); 
    
    // inserts a nodedata by definition of a binary tree
    bool insert(NodeData*); 
    
    // finds the given nodedata, if true, it sets the passed pointer to the 
    // the found nodedata
    bool retrieve(const NodeData &, NodeData*&) const; 
    
    // provided, displays the tree sideways
    void displaySideways() const; 
    
    // if the object is in the tree, it returns the distance from the 
    // furthest child of the given object. If not found, returns 0.
    int getHeight(const NodeData &) const;
    
    // takes all the elements in the tree and moves them to an array, sorted
    // then destroys the tree
    void bstreeToArray(NodeData* []);
    
    // takes a sorted list of nodedata pointers and creates a balanced tree
    void arrayToBSTree(NodeData* []);
    
    // cout<< recursive call initializer. calls inOder(Node*)
    void inOrder() const;
    
    // Assignment operators
    BinTree& operator=(const BinTree &);
    
    // Boolean Comparison Operators
    // overloaded == only returns true when structure and contents are exactly
    //      the same
    bool operator==(const BinTree &) const;
    
    // overloaded != only returns true when there is any variation of 
    //structure or contents
    bool operator!=(const BinTree &) const;


private:
    
    struct Node { 
        NodeData* data = NULL;  // pointer to data object 
        Node* left = NULL;      // left subtree pointer 
        Node* right = NULL;     // right subtree pointer 
    }; 
    Node* root;                 // root of the tree
    
    
    // utility functions 
    // recursive helper function to build array.
    void bstreeToArrayHelper(Node* current, NodeData* bltaPtr[]) const; 
    
    // recursive helper function to print tree sideways
    // provided, helper for displaySideways()
    void sideways(Node*, int) const; 
    
    // recursive copy constructor and assignment operator helper.
    void buildTree(Node* n, const Node* o);
    
    // recursive makeEmpty helper, deletes NodeData & Nodes.
    void destroyTree(Node* d);
    
    // recursive inOrder() helper,
    void inOrder(Node*) const;
    
    // recursive arrayToBSTree helper, builds BinTree from NodeData[].
    void arrayBuild(int, int, NodeData* []);
    
    // recursive isEqual helper, preforms parallel equivalencies between nodes
    bool isEqualH(const Node* lhs,const Node* rhs)const;
    
    // traversal search for Node in tree. sets Node* to node if found.
    // otherwise sets Node* to NULL.
    void findNodePtr(Node*, const NodeData&, Node*&) const;
    
    // recursive height count from Node position.
    int count(Node*) const;
};      

#endif // BINTREE_H