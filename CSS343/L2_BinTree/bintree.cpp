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
//copy constructor
BinTree::BinTree(const BinTree& orig) {
    if(orig.isEmpty()){    
        root == NULL;
        return;
    }
    root = new Node;
    root->data = new NodeData(*orig.root->data);
    root->left = root->right = NULL;
    buildTree(root,orig.root);
}
//helper for copy constructor & assignment operator
 void BinTree::buildTree(Node* n,const Node* o){
    if(o->left != NULL){
        n->left = new Node;
        n->left->data = new NodeData(*o->left->data);
        n->left->left = n->left->right = NULL;
        buildTree(n->left,o->left);
    }
     if(o->right != NULL){
        n->right = new Node;
        n->right->data = new NodeData(*o->right->data);
        n->right->left = n->right->right = NULL; 
        buildTree(n->right,o->right);
    }
 }

//---------------------------------------------------------------------------
// destructor
BinTree::~BinTree() {
    if(!isEmpty())
        makeEmpty();
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
// overloaded == operator
bool BinTree::operator==(const BinTree& T) const {
    return isEqualH(this->root, T.root);
}
// overloaded == recursive helper
bool BinTree::isEqualH( const Node* lhs,const Node* rhs)const{ 
    if(lhs == NULL && rhs == NULL)return true;
    if(lhs != NULL && rhs == NULL)return false;
    if(lhs == NULL && rhs != NULL)return false;
    return ((*lhs->data == *rhs->data) && isEqualH(lhs->left,  rhs->left )
                    && isEqualH(lhs->right, rhs->right));
}

//---------------------------------------------------------------------------
// overloaded != operator
bool BinTree::operator!=(const BinTree &T) const{
    return !(*this == T);
}

//---------------------------------------------------------------------------
// overloaded = operator
BinTree& BinTree::operator=(const BinTree &T){
    if(this == &T)
        return *this;
    makeEmpty();
    //build tree
    root = new Node;
    root->data = new NodeData(*T.root->data);
    root->left = root->right = NULL;
    buildTree(root, T.root);
    return *this;
}

//---------------------------------------------------------------------------
// insert data non-recursively
bool BinTree::insert(NodeData* ND){
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
// finds a given NodeData in the tree.
// return true if found, and set NodeData* to found NodeData Object.
bool BinTree::retrieve(const NodeData &ND, NodeData*&NDptr) const{
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
    // NodeData not found
    NDptr = NULL;
    return false;
}

//---------------------------------------------------------------------------
// get height
//does it exist
//if it does then pass back the nodeptr
//count its children
int BinTree::getHeight(const NodeData &ND) const{
    Node* ptr = NULL;
    findNodePtr(root, ND, ptr);
    if(ptr != NULL){
        return count(ptr);
    }
    return 0;
}

//helper that counts the children
int BinTree::count(Node* n) const {
    if(!n) 
        return 0;
    return 1 + max(count(n->left), count(n->right));
}

//returns a pointer to a node if it exists
// recursive traversal
void BinTree::findNodePtr(Node* n, const NodeData &ND, Node*& TPtr) const{
    if ( n != NULL ) {
       findNodePtr(n->left, ND, TPtr);
       if(*n->data == ND){
           TPtr = n;
       }
       findNodePtr(n->right, ND, TPtr);
    }
}


//---------------------------------------------------------------------------
// bintree to array
void BinTree::bstreeToArray(NodeData* NDAptr[]){
    bstreeToArrayHelper(root, NDAptr);
    makeEmpty();
}

//---------------------------------------------------------------------------
// helper for in order traversal
void BinTree::bstreeToArrayHelper(Node* current, NodeData* NDAptr[]) const{
    if(current != NULL){
        bstreeToArrayHelper(current->left, NDAptr);
        for (int i = 0; i < 100; i++){
            if(NDAptr[i] == NULL){
                NDAptr[i] = current->data;
                current->data = NULL;
                i = 100;
            }
        }
        bstreeToArrayHelper(current->right, NDAptr);
    }
}

//---------------------------------------------------------------------------
// array to bintree
void BinTree::arrayToBSTree(NodeData* NDAptr[] ){
    int min = 0;
    int max = 0;
    for(int i = 0; i < 100; i++) {
        if(NDAptr[i] != NULL)
            max = i;
    }
    arrayBuild(min, max, NDAptr);
}

// array to bintree helper
void BinTree::arrayBuild(int min, int max, NodeData* NDAptr[]){
    int mid = (min + max)/2;
    if(min <= max){
        insert(NDAptr[mid]);
        arrayBuild(min, mid-1 , NDAptr);
        arrayBuild(mid + 1, max, NDAptr);
    }
}

//---------------------------------------------------------------------------
// display sideways public method
void BinTree::displaySideways() const{
        sideways(root, 0);
}

//---------------------------------------------------------------------------
// display sideways helper
void BinTree::sideways(Node* current, int level) const{
    if (current != NULL) {
        level++;
        sideways(current->right, level);
        
        // indent for readability, 4 spaces per depth level
        for(int i = level; i >= 0; i--) {
            cout << "    ";
        }
        
        cout << *current->data << endl;      // display information of object
        sideways(current->left, level);
    }
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
    inOrder(root) ;
}

//---------------------------------------------------------------------------
// inOrder
// cout recursive call. recursively prints from argument node.
void BinTree::inOrder(Node* n) const{
    if ( n != NULL ) {
       inOrder(n->left);
       cout << *n->data << " ";
       inOrder(n->right);
    }
}

