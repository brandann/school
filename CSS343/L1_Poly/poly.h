#ifndef POLY_H
#define POLY_H
#include <iostream>

using namespace std;

class Poly
{
    friend istream& operator>>( istream&, Poly& );
    friend ostream& operator<<( ostream&, const Poly& );

    public:
        // Constructor
        Poly(int a = 0, int e = 0);
        Poly(const Poly &p);

        // Destructor
        virtual ~Poly();

        // Get Methods
        int getCoeff(int);
        
        // Set Methods
        bool setCoeff(int, int);

        // Overload Operators
        Poly operator+( const Poly& );
        Poly operator-( const Poly& );
        //Poly operator-( const int );
        Poly operator*( const Poly& );

        // Assignment operators
        Poly& operator=( const Poly& );
        Poly& operator+=( const Poly& );
        Poly& operator-=( const Poly& );
        Poly& operator*=( const Poly& );

        // Boolean Comparison Operators
        bool operator==( const Poly& ) const;
        bool operator!=( const Poly& ) const;

    protected:
    	
    private:
    	int size;
        int* polynomial;                                               
        
};

#endif // POLY_H
