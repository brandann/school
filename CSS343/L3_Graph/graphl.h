//  File:   graphl.cpp
//  Author: Brandan, Erick

//---------------------------------------------------------------------------
// GraphL class:
// - Graph Class using an array of lists.
//
// Assumptions:
// - making a graph node adjacent to itself will give an incorrect output
// - basic graph class, does not contain many of the "standard" functions
//      such as a copy constructor
// - cannot change any of the data in the graph after the initial build
//      graph is called
// - if depth first search is called twice the function does not operate
//      correctly because the bools do not reset to their default, thus
//      giving an incorrect result the second time.
// - the class requires very specific files in order to work properly
//      data needs to be set up exactly for it to do work as expected
// - the subscripts on the array start at 1, [0] is not used.
//---------------------------------------------------------------------------

#ifndef GRAPHL_H
#define	GRAPHL_H
#include <iostream>
#include <climits>
#include "nodedata.h"

using namespace std;

    struct EdgeNode;//needed for complier

	//---------------------------------------------------------------------------
    // an array is filled with graphNodes A->[GN][GN][GN][GN]
    // each graphNode representing a location on a graph
    struct GraphNode {
        EdgeNode* edgeHead = NULL;
        NodeData data;
        bool visited;
    };

	//---------------------------------------------------------------------------
    // each graphNode contains a list of edgeNodes representing which
    // other graphNodes are adjacent to it by a value stored in the edgeNode
    //      Array
    //      |
    //      V
    // 1[[GN|EHead]]->[EN]->[EN]->[EN]
    // 2[[GN|EHead]]
    // 3[[GN|EHead]]->[EN]
    // 4[[GN|EHead]]->[EN]->[EN]->[EN]->[EN]
    //
    // example: graphNode 1 is adjacent to 2, 3, and 4
    // 1[]->[2]->[3]->[4]
    struct EdgeNode {
        int adjGraphNode;
        EdgeNode* nextEdge = NULL;
    };

class GraphL {
public:
	//---------------------------------------------------------------------------
    // default constructor, if no file is given
    GraphL();
    
	//---------------------------------------------------------------------------
    // deconstructor, calls make empty
    ~GraphL();
    
	//---------------------------------------------------------------------------
    // build graph reads a file, line by line, builds an array of graph nodes.
    // in each graph node there is a list of edgeNodes that contains data about
    // which nodes are adjacent to the source node
    void buildGraph(istream& infile);
    
	//---------------------------------------------------------------------------
    // depth-first search goes as deep as possible, into the graph, by connecting
    // the source node to it's next node, and then that next node's, next node
    // and so on. Continues until it hits a null edge
    void depthFirstSearch();
    
	//---------------------------------------------------------------------------
    // displays the graph by printing the node's data. Then printing all of the
    // edges surrounding that node
    void displayGraph()const;
    
private:
    
	//---------------------------------------------------------------------------
    // called in destructor, deletes the array of graphNodes & edgeNodes
    void makeEmpty();
    
	//---------------------------------------------------------------------------
    // recursive makeEmpty helper, deletes the edgeNode objects
    void remove(EdgeNode* p);
    
	//---------------------------------------------------------------------------
    // recursive helper for depth first search
    void searchHelper(EdgeNode*,int);
    
	//---------------------------------------------------------------------------
    // recursive helper for display Graph
    void displayEdge(const EdgeNode*, const int)const;
    
    GraphNode* adjList = NULL; // GraphNode array
    int size;           	// array size
};

#endif	// GRAPHL_H