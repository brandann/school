/* 
 * File:   bintree.cpp
 * Author: Erick House, Brandan Haertel
 */

#include "bintree.h"

using namespace std;

//---------------------------------------------------------------------------
// default constructor
// creates an empty tree
BinTree::BinTree() {
    root = NULL;
}

//---------------------------------------------------------------------------
// destructor
BinTree::~BinTree() {
    if(!isEmpty()){
        makeEmpty();
    }
        
}
// destructor helper
void BinTree::destroyTree(Node* d){
    if(d != NULL)
    {
        destroyTree(d->left);
        destroyTree(d->right);
        delete d->data;
        d->data = NULL;
        delete d;
        d = NULL;
    }
}

//---------------------------------------------------------------------------
// is Empty
bool BinTree::isEmpty() const{
    if(root == NULL){
    	return true;
    }
    return false;
}

//---------------------------------------------------------------------------
// make Empty
void BinTree::makeEmpty(){
    destroyTree(root);
    root = NULL;
}


//---------------------------------------------------------------------------
// insert data non-recursively
bool BinTree::insert(Recordable* ND){
    if (isEmpty()) {
        root = new Node;
        root->data = ND;
        root->right = root->left = NULL;
        return true;
    }
    Node *current = root;

    while (current->data != NULL) {
        if (*ND == *current->data){
            return false;
        }
        else if (*ND < *current->data) {
            if(current->left == NULL) {
                current->left = new Node;
                current->left->data = ND;
                current->left->left = current->left->right = NULL;
                return true;
            }
            current = current->left;
        }
        else if(*ND > *current->data){
            if(current->right == NULL) {
                current->right = new Node;
                current->right->data = ND;
                current->right->right = current->right->left = NULL;
                return true;
            }
            current = current->right;
        }
    }
    return false;
}

//---------------------------------------------------------------------------
// retrieve
// finds a given Recordable in the tree.
// return true if found, and set Recordable* to found Recordable Object.
bool BinTree::retrieve(const Recordable &ND, Recordable*&NDptr) const{
    if(!isEmpty()) {
        Node *current = root;
        while (current->data != NULL) {
            if(ND == *current->data) {
                NDptr = current->data;
                return true;
            }
            else if(ND < *current->data) {
                if (current->left == NULL)
                    return false;
                current = current->left;
            }
            else if(ND > *current->data) {
                if (current->right == NULL)
                    return false;
                current = current->right;
            }
        }
    }
    // Recordable not found
    NDptr = NULL;
    return false;
}


//---------------------------------------------------------------------------
// overloaded << operator
ostream& operator<<(ostream &output, const BinTree &T) {
    T.inOrder();
    output << endl;
    return output;
}

//---------------------------------------------------------------------------
// inOrder
// cout recursive call. recursively prints from root.
void BinTree::inOrder()const{
    if(root != NULL)
        inOrder(root) ;
}

//---------------------------------------------------------------------------
// inOrder
// cout recursive call. recursively prints from argument node.
void BinTree::inOrder(Node* n) const{
    if ( n != NULL ) {
       inOrder(n->left);
       cout << n->data->toString() << endl;
       inOrder(n->right);
    }
}
