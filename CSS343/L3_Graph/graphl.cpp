
// File:   graphl.cpp
// Author: Brandan, Erick

#include "graphl.h"
#include <iomanip>


//---------------------------------------------------------------------------
//constructor
GraphL::GraphL() {
    adjList = NULL;
    size = 0;
}

//---------------------------------------------------------------------------
//destuctor
GraphL::~GraphL() {
    makeEmpty();
}

//---------------------------------------------------------------------------
//build graph
//builds a graph from the given file, line by line
void GraphL::buildGraph(istream& infile) {

   int fromNode, toNode;              // from and to node ends of edge

   makeEmpty();                       // clear the graph of memory 

   infile >> size;                    // read the number of nodes
   if (infile.eof()) return;          // stop if no more data
   
   adjList = new GraphNode[size+1];

   string s;                          // used to read through to end of line
   getline(infile, s);

   // read graph node information
   for (int i=1; i <= size; i++) {
      adjList[i].data.setData(infile);
   }

   // read the edge data and add to the adjacency list
   for (;;) {
      infile >> fromNode >> toNode;
      if (fromNode ==0 && toNode ==0) return;     // end of edge data

        // insert the edge into the adjacency list for fromNode
      if( fromNode <= size && toNode <= size){
          if(adjList[fromNode].edgeHead == NULL){
              adjList[fromNode].edgeHead = new EdgeNode;
              adjList[fromNode].edgeHead->adjGraphNode = toNode;
              adjList[fromNode].edgeHead->nextEdge = NULL;
          }
          else{
              EdgeNode* p = adjList[fromNode].edgeHead;
              adjList[fromNode].edgeHead = new EdgeNode;
              adjList[fromNode].edgeHead->adjGraphNode = toNode;
              adjList[fromNode].edgeHead->nextEdge = p;
          }
      }
   }
}

//---------------------------------------------------------------------------
//depth-first search
void GraphL::depthFirstSearch(){
    
    //reset all the visited bools before searching
    for(int i = 1; i <= size; i++){
        adjList[i].visited = false;
    }
    
    // start the search
    cout << "Depth-first ordering: ";
    
    for(int v = 1; v <= size; v++){
        if ( adjList[v].visited == false) {
            searchHelper(adjList[v].edgeHead, v);
        }
    }
    cout << endl;
}

//helper for depth-first search
void GraphL::searchHelper(EdgeNode* p, int v){
    
    if (adjList[v].visited == false) {
        adjList[v].visited = true;
        cout << v << " ";
    }
   
    if(p != NULL){
        if(adjList[p->adjGraphNode].visited == false){
            cout << p->adjGraphNode << " ";
            adjList[p->adjGraphNode].visited = true;
            searchHelper(adjList[p->adjGraphNode].edgeHead,v);
        }
        searchHelper(p->nextEdge,v);
    }
}

//---------------------------------------------------------------------------
//display graph
void GraphL::displayGraph()const{
   
    for(int i = 1; i <= size; i++){
        cout  << "Node " << i << "\t" << adjList[i].data << endl;
		if(adjList[i].edgeHead != NULL ){
            displayEdge(adjList[i].edgeHead, i);
        }
    }
    
}
//recursive helper for display graph
void GraphL::displayEdge(const EdgeNode* p, const int i)const{
    if(p != NULL){
        cout << "  " << "edge " << i << " " << p->adjGraphNode << endl;
        displayEdge(p->nextEdge, i);
    }
}

//---------------------------------------------------------------------------
//make empty
void GraphL::makeEmpty(){
    if(adjList == NULL)
        return;
    
    for(int v = 1; v <= size; v++){
        if(adjList[v].edgeHead != NULL)
            remove(adjList[v].edgeHead);
    }
    
    delete [] adjList;
    size = 0;
    adjList = NULL;
}

//---------------------------------------------------------------------------
//recursive helper to remove all nodes edgeNodes
void GraphL::remove(EdgeNode* p){
    if(p != NULL){
        remove(p->nextEdge);
        delete p;
        p = NULL;
    }
}
