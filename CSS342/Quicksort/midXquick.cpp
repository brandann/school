#include <vector>

using namespace std;

template <class Comparable>
void objectSwap( Comparable &lhs, Comparable &rhs ) {
  Comparable tmp = lhs;
  lhs = rhs;
  rhs = tmp;
}

template <class Comparable>
void choosePivot( vector<Comparable> &a, int first, int last ) {
  int middle = ( first + last ) / 2;
  objectSwap( a[first], a[middle] );
}

template <class Comparable>
void partition( vector<Comparable> &a, int first, int last, int &pivotIndex ){
  // place the pivot in a[first]
  choosePivot( a, first, last );
  Comparable pivot = a[first];
  int lastS1 = first;
  int firstUnknown = first + 1;
  
  for ( ; firstUnknown <= last; ++firstUnknown ) {
    if ( a[firstUnknown] < pivot ) {
      ++lastS1;
      objectSwap( a[firstUnknown], a[lastS1] );
    }
    // else item from unknown belongs in S2
  }
  // decide a new pivot
  objectSwap( a[first], a[lastS1] );
  pivotIndex = lastS1;
}

template <class Comparable>
void quicksort( vector<Comparable> &a, int first, int last, int midX ) { 
  int pivotIndex;
  
  //if array is partitioned:
  if (last - first +1 != a.size()){
    //do a mock botXquick and topXquick on partitioned array
    if (last < ((a.size() - midX)/2) || first >= ((a.size() + midX)/2))
      return;
  }

  if ( first < last ) {
    partition( a, first, last, pivotIndex );
    quicksort( a, first, pivotIndex - 1, midX );
    quicksort( a, pivotIndex + 1, last, midX );
  }
}

template <class Comparable>
void midXquick( vector<Comparable> &a, int midX ) {
  quicksort( a, 0, a.size( ) - 1, midX);
}


