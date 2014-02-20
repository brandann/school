#include "Distance.h"
#include <iostream>
using namespace std;

int main( )
{
  Distance d1( -5, 30 ), d2(5, -31 ), d3( 2, 5 ), d4( 1 ), d5;

  cout << "d1 = " << d1 << endl;
  cout << "d2 = " << d2 << endl;
  cout << "d3 = " << d3 << endl;
  cout << "d4 = " << d4 << endl;
  cout << "d5 = " << d5 << endl;

  cout << "Sqfeet( d2 * d3 ) = " << d2 * d3 << endl;
  cout << "d1 / d2 = " << d1 / d2 << endl;
  cout << "( d1 += d2 ) = " << ( d1 += d2 ) << endl;

  cout << "d1 <= d2 is " << ( ( d1 <= d2 ) ? "true" : "false" ) << endl;
  cout << "d2 >= d3 is " << ( ( d2 >= d3 ) ? "true" : "false" ) << endl;
}