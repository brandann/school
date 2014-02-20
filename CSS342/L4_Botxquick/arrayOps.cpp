#include <vector>
#include <iostream>
#include <stdlib.h>

using namespace std;


void initArray( vector<int> &array, int randMax ) {
  int size = array.size( );

  for ( int i = 0; i < size; ) {
    int tmp = ( randMax == -1 ) ? rand( ) : rand( ) % randMax;
    bool hit = false;
    for ( int j = 0; j < i; j++ ) {
      if ( array[j] == tmp ) {
        hit = true;
        break;
      }
    }
    if ( hit )
      continue;
    array[i] = tmp;
    i++;
  }
}

void printArray( vector<int> &array, const string &arrayName ) {
  int size = array.size( );

  for ( int i = 0; i < size; i++ )
    cout << arrayName << "[" << i << "] = " << array[i] << endl;
}
