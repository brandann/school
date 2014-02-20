// File:   graphm.h
// Author: Erick House, Brandan Haertel

//---------------------------------------------------------------------------
// GraphM class:
// - Graph Class using Matrix.

// Assumptions:
// - basic graph class, does not contain many of the "standard" functions
//      such as a copy constructor
// - if finsShortestPath is called twice the function does not operate
//      correctly because the bools do not reset to their default, thus
//      giving an incorrect result the second time.
// - the class requires very specific files in order to work properly
//      data needs to be set up exactly for it to do work as expected
// - the subscripts on the matrix array start at 1, [0] is not used.
//---------------------------------------------------------------------------

#ifndef GRAPHM_H
#define GRAPHM_H
#include <iostream>
#include <climits>
#include "nodedata.h"

using namespace std;

const int MAXNODES = 100;
const int INF = INT_MAX;

class GraphM {
	
public:
    //-------------------------------------------------------------------
    // Constructor()
    // Default constructor for class GraphM
    // Preconditions: None
    // Postconditions: None
    GraphM();

    //-------------------------------------------------------------------
    // Deconstructor()
    // Deconstructor for class GraphM
    // Preconditions: None
    // Postconditions: Graph Nodes are deallocated, graph is deallocated.
    ~GraphM();

    //-------------------------------------------------------------------
    // buildGraph()
    // automated graph builder, sets size, verticies, and edges.
    // Preconditions: istream& files must be formated correctly:
    // size, data, vertice-adjacent vertice-edge value.
    // istream& file cannot contain decimal numbers.
    // Postconditions: istream file remains unchanged, the graph matix
    // values are inserted.
    void buildGraph(istream& infile);

    //-------------------------------------------------------------------
    // insertEdge()
    // sets the edge value of two adjacent verticies.
    // first paramter is the source node index, second parameter is the
    // destination node index, third parameter is the edge distance.
    // Preconditions: source node and destination node indexies are
    // valid, non negative numbers. edge distance is a non negative
    // intiger < INT_MAX
    // Postconditions: edge between adjacent verticies is set.
    bool insertEdge(int,int,int);

    //-------------------------------------------------------------------
    // removeEdge()
    bool removeEdge(int,int);

    //-------------------------------------------------------------------
    // finsShorestPath()
    // algorithm uses modified Dijkstras algorithm to traverse through
    // the graph and find all posible shortest paths.
    // Preconditions: GraphM should have a graph pre built.
    // Postconditions: the shortest paths and distanaces are created
    // and held foe use in the display() and displayAll() methods.
    void findShortestPath();

    //-------------------------------------------------------------------
    // displayAll()
    // displays all shortest paths, and the path traversal for every
    // possible path on the graph.
    // Preconditions: displayAll relies on a pre-build graph.
    // Postconditions: all information is sent to the standard output,
    // the graph and all path information remains unchanged.
    void displayAll();

    //-------------------------------------------------------------------
    // display()
    // display the shortest path, and path traversal for two
    // adjacent verticies. includes the shortest path represented
    // by the data name.
    // Preconditions: paramters must be valid verticies, from 1 to
    // <= size of graph.
    // Postconditions: the path information is sent to the standard output,
    // the graph and all path information remains unchanged.
    void display(int, int);

private:
    //-------------------------------------------------------------------
    // struct TableType
    struct TableType {
        bool visited; // whether node has been visited
        int dist;     // currently known shortest distance from source
        int path;     // previous node in path of min dist
    };
        
    //-------------------------------------------------------------------
    // initialize()
    // GraphM from 0 to MAXNODES is set to INF
    void initialize();

    //-------------------------------------------------------------------
    // displayPath()
    // helper function for the display and display all methods, uses
    // cout to print the shortest path between two adjacent verticies
    void displayPath(int,int);
    
    //-------------------------------------------------------------------
    // displayNodeData()
    // prints the the path's nodes actual loactions, instead of a number
    // prints in order, from the starting source to the end
    void displayNodeData(int,int);

    //-------------------------------------------------------------------
    // findMin()
    // findShortestPath helper, finds the next adjacent verticie with
    // the shortest distance, for computation of Dijkstras algorithm.
    int  findMin(int) const;

    bool pathfound;
    NodeData data[MAXNODES];	// data for graph nodes information
    int C[MAXNODES][MAXNODES];	// Cost array, the adjacency matrix
    int size;		// number of nodes in the graph
    TableType T[MAXNODES][MAXNODES];// stores visited, distance, path
};

#endif // GRAPHM_H
