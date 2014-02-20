#include <iostream>
#include <vector>
#include <math.h>
#include <sys/time.h>
using namespace std;

// you may need to define an array as a global variable or as a
// local variable in main( )/sieve( )

int sieve( int n, vector<int> &primes ) {

	primes.clear();

  int cost = 0;
  bool array[n + 1];

  for (int i = 0; i < n; i++)
  {
      array[i] = true;
  }
  int max = int(sqrt(double(n)));
  for(int prime = 2; prime <= max; prime++)
  {
      if(!array[prime])
      {
          for (int k = prime * prime; k <= n; k += prime)
          {
              array[k] = false;
              cost++;
          }
      }
      else
      {
      	  primes.push_back(prime);
          cost++;
      }
  }
  return cost;
}

main( ) {
  int n = 0;
  struct timeval startTime, endTime;

  while ( n < 2 ) {
    cout << "Enter n ";
    cin >> n;
  }

  vector<int> primes;

  char printOption = 'n';
  cout << "print all primes (y|n)? ";
  cin >> printOption;

  for ( int i = 2; i <= n; i++ ) {
    gettimeofday( &startTime, NULL );

    int cost = sieve( i, primes );

    gettimeofday( &endTime, NULL );
    cout << "primes[1.." << i << "] took " << cost
	 << " sweeping operations...time required = "
	 << ( endTime.tv_sec - startTime.tv_sec ) * 1000000 +
            ( endTime.tv_usec - startTime.tv_usec ) << endl;

    if ( printOption == 'y' ) {
    	for (int i = 0; i < primes.size(); i++)
    	{
    		cout << primes[i] << " ";
    	}
    }

    cerr << i << " " << cost << endl;
  }
}

