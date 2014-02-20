// File:   graphm.cpp
// Author: brandan

#include "graphm.h"
#include <iomanip>
using namespace std;

//---------------------------------------------------------------------------
// Default Constructor
GraphM::GraphM() {

    size = 0;
    pathfound = false;
}

//---------------------------------------------------------------------------
// initialize
void GraphM::initialize() {

    // set all C[][] to INF
    // reset T
    for(int i = 1; i <= size; i++)
        for (int j = 1; j <= size; j++) {
            C[i][j] = INF;
            T[i][j].visited = false;
            T[i][j].dist = INF;
            T[i][j].path = 0;
        }
    pathfound = false;
}

//---------------------------------------------------------------------------
// Deconstructor
GraphM::~GraphM() {}

//---------------------------------------------------------------------------
// buildGrapth
void GraphM::buildGraph(istream& infile){
    
    // from and to node ends of edge
    int fromNode, toNode, dist;         

    // read the number of nodes
    infile >> size;                     
    if(size >= MAXNODES){
        size = 0;
        return;
    }
    initialize();
    
    // stop if no more data
    if (infile.eof()) return;           

    // used to read through to end of line
    string s;                           
    getline(infile, s);

    // read graph node information
    for (int i=1; i <= size; i++) {
        data[i].setData(infile);
    }

    // read the edge data and add to the adjacency list
    for (;;) {
        infile >> fromNode >> toNode;
        infile >> dist;
        if (fromNode ==0 && toNode ==0) 
            return; // end of edge data
        insertEdge(fromNode,toNode,dist);
    }
}

//---------------------------------------------------------------------------
// insert Edge
bool GraphM::insertEdge(int from, int to, int dist) {
    
    // check of empty
    if(size == 0) return false;
    
    // check if nodes are out of bounds
    if(from > size || to > size) return false;
    
    // check if nodes are out of bounds
    if(from < 1 || to < 1 || dist < 1) return false;
    
    // check if self assigning
    if(from == to) return false;
    
    // set edge
    C[from][to] = dist;
    pathfound = false;
    return true;
}

//---------------------------------------------------------------------------
// Find Shortest Path
void GraphM::findShortestPath(){
    
    // v represents the current node, with the shortest total distance
    // from the source
    int v = 1;

    //calculate sortest path for every start node: 1 through n
    // x variable = our source or current x axis
    for (int x = 1; x <=size; x++) {

        T[x][x].dist = 0; //make sure our start value is 0: not INF

        // update "v" n-1 times
        for(int w = 1; w < size; w++){

            v = findMin(x); //smallest value after traversing the distance array
            T[x][v].visited = true;

            if(v != 0){ //do nothing if our findMin returned INF

                // "j" is arbitrary. this loop proceeds to update every value
                // that is adjacent to the current node and is not visited, 
                // with the new shortest path, if there is one
                for(int j = 1; j<= size; j++){
                    if(C[v][j] != INF && T[x][j].visited == false){

                        //only update v if we acutally went through a new path
                        if (T[x][v].dist+ C[v][j] < T[x][j].dist ) {
                            T[x][j].path = v;
                        }
                        T[x][j].dist =  min(T[x][j].dist,T[x][v].dist+ C[v][j]);
                    }
                 }
             }
        }
    }
    pathfound = true;
}

//---------------------------------------------------------------------------
// findMin
int GraphM::findMin(int x) const{
    
    int min = INF;
    int index = 0;
    
    for(int y = 1; y <= size ;y++){
        if(T[x][y].dist != INF && T[x][y].visited == false ){

            //find the min and update mins value
            if(T[x][y].dist < min  ){

                min = T[x][y].dist;
                if (min != INF) {
                     index = y;
                }
            }
        }
    }
    // if min is still INF, return 0, the function will catch it.
    if (min == INF)
        return 0;
 
    return index;
}

//---------------------------------------------------------------------------
// display all
void GraphM::displayAll(){
    
    if (size == 0)
        return;
    
    if(!pathfound)
        findShortestPath();
    
    cout << "Decription            ";
    cout << "From Node   ";
    cout << "To Node   ";
    cout << "Dijkstra's      ";
    cout << "Path" << endl;
    
    for(int j = 1; j <= size; j++) {
        cout << data[j] << endl;

        for (int k = 1; k <= size; k++) {
            if (j != k) {
                cout << setw(23) << j;
                cout << setw(12) << k;
               
            if(T[j][k].dist != INF) {
                cout << setw(15) << T[j][k].dist;
            }
            else{
                cout << setw(15) << "----";
            }

            cout << setw(10) << " ";
            displayPath(j, k);
            if(T[j][k].dist != INF)
                cout << " " << k;
            
            cout << endl;
            }
        }
    }
}
//---------------------------------------------------------------------------
// display
void GraphM::display(int j, int k){
    
    if(!pathfound)
        findShortestPath();
    
    if (j != k) {
        cout << setw(5) << j;
        cout << setw(5) << k;

    if(T[j][k].dist != INF) {
        cout << setw(5) << T[j][k].dist;
    }
    else{
        cout << setw(5) << "----" << endl;
        return;
    }

    cout<<setw(5);
    displayPath(j,k);
    if(T[j][k].dist != INF)
        cout << " " << k;
    cout << endl;
    }
    displayNodeData(j, k);
    cout << endl;
}

//---------------------------------------------------------------------------
// displayPath
void GraphM:: displayPath(int l, int n){
    
    if (T[l][n].path != 0) {
        displayPath(l, T[l][n].path);
        cout << " " << T[l][n].path;
    }
}

//---------------------------------------------------------------------------
// displayNodeData
void GraphM::displayNodeData(int l, int n){
    
    if (n != 0) {
        
        displayNodeData(l, T[l][n].path);
        cout << data[n] << endl;
    }
}

//---------------------------------------------------------------------------
// remove Edge
bool GraphM::removeEdge(int from, int to) {
    cout << "GraphM remove Edge" << endl; 
    return insertEdge(from, to, INF);
}