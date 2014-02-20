#include <stdlib.h>
#include <iostream>
#include <vector>
#include "midXquick.cpp"

extern void initArray( vector<int> &array, int rand_max );
extern void printArray( vector<int> &array, const string &name );

int main( int argc, char* argv[] ) {
  // verify arguments
  if ( argc != 3 ) {
    cerr << "usage: quicksort size midX" << endl;
    return -1;
  }

  // verify an array size
  int size = atoi( argv[1] );
  if ( size <= 0 ) {
    cerr << "array size must be positive" << endl;
    return -1;
  }

  // verify bot X
  int midX = atoi( argv[2] );
  if ( midX <= 0 ) {
    cerr << "midX must be positive" << endl;
    return -1;
  }

  // array generation
  srand( 1 );
  vector<int> items( size );
  initArray( items, size );
  cout << "initial:" << endl;
  printArray( items, "items" );

  // quicksort
  midXquick( items, midX );
  cout << "sorted:" << endl;
  printArray( items, "items" );

  return 0;
}
