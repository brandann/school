#include <iostream>
#include "longint.h"

using namespace std;

int main( ) {
  LongInt a, b, c, d, e( "-0099999999999999999999999999999999x" ), ans;
  cout << "enter for a: ";
  cin >> a;
  cout << "enter for b: ";
  cin >> b;
  cout << "enter for c: ";
  cin >> c;
  cout << "enter for d: ";
  cin >> d;
  cout << "a = " << a << endl;
  cout << "b = " << b << endl;
  cout << "c = " << c << endl;
  cout << "d = " << d << endl;
  cout << "e = " << e << endl;
  /*
    Compile Note:
    cout << a + b << endl;
    does not run, the assignment compiles, and the add compiles, but not both together
  */
  LongInt f = e;
  cout << "f = " << f << endl;
  cout << a << " + " << b << " =  ";
  ans = ( a + b );
  cout << ans << endl;
  cout << a << " - " << b << " =  ";
  ans =  ( a - b );
  cout << ans << endl;
  cout << b << " - " << a << " =  ";
  ans =  ( b - a );
  cout << ans << endl;
  cout << b << " + " << a << " =  ";
  ans =  ( b + a );
  cout << ans << endl;
  cout << a << " == " << c << " =  ";
  cout << ( a == c );
  cout << ans << endl;
  cout << a << " != " << c << " =  ";
  cout << ( a != c );
  cout << ans << endl;
  cout << a << " > " << d << " =  ";
  cout << ( a > d );
  cout << ans << endl;
  cout << a << " >= " << d << " =  ";
  cout << ( a >= d );
  cout << ans << endl;
  cout << a << " < " << d << " =  ";
  cout << ( a < d );
  cout << ans << endl;
  cout << a << " <= " << d << " =  ";
  cout << ( a <= d );
  cout << ans << endl;

  cout << "f = f + 1 = ";
  f = f + *( new LongInt( "-1" ) );
  cout << f << endl;
  cout << "e = " << e << endl;
  cout << ans << endl;
}
