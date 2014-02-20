/* 
 * File:   euclid.cpp
 * Author: brandan Haertel
 *         brandan@uw.edu
 *
 * Created on October 11, 2013, 2:17 PM
 */

#include <cstdlib>
#include <iostream>
#include <sys/time.h>

using namespace std;
int a, b, c, m, t;

/**
* Calculate the gcd of two numbers, if x and y are a pair
* that creates the greatest amount of modual operations, set
* x and y to global variables a and b, also set the number
* of mod operations to m, and the cost of operations.
*/
void gcd(int x, int y)
{
    int x_val = x, y_val = y;
    int r, cost;
    cost = 0;
    while (y != 0)
    {
        r = x % y;
        x = y;
        y = r;
        cost++;
        t++;
    }
    
    if (cost > c)
    {
        m = x;
        a = x_val;
        b = y_val;
        c = cost;
    }
}

/**
* gets a number n >= 8 and loops through all combinations of 8~n
 */
int main(int argc, char** argv) {
    struct timeval startTime, endTime;
    int n = 0;
    
    while (n < 8)
    {
        cout << "Enter a positive number greater or equal to 8: ";
        cin >> n;
    }
    
    gettimeofday( &startTime, NULL );
    
    for (int i = 8; i <= n; i++)
    {
        for (int j = 8; j <= n; j++)
        {
            gcd(i, j);
        }
    }
    
    gettimeofday( &endTime, NULL );

    cout << "Greatest = " << a << ", " << b << " GCD = " << m
                << "\nmod operations: " << c 
                << "\nTotal Operations: " << t
                << "\ntime required = "
                << (endTime.tv_sec - startTime.tv_sec) *1000000 + 
                ( endTime.tv_usec - startTime.tv_usec ) << endl;
    
    return 0;
}

