//-----------------------------------------------------------------------------
// poly.cpp
// Class to use mathmatical operations on polynomials.
//
// Assumptions:  
//    -- Format of data is correct.
//    -- 
//-----------------------------------------------------------------------------
 
#include "poly.h"

//*********************************************************
// Operator>>
// takes input and makes polynomial based off pairs of 
// numbers entered. use (-1 -1) to exit loop.
istream& operator>>( istream& in, Poly& rhs ) {
    int a;
    int e;
    bool run = true;
    while(run) {
        in >> a;
        in >> e;
        if(a == -1 && e == -1)
            run = false;
        else
            rhs.setCoeff(a, e);
    }
    return in;
}

//*********************************************************
// Operator<<
// outputs polynomial in text format.
// does not print 0 coefficients, prints 0 if polynomial
// has no degrees.
ostream& operator<<( ostream& os, const Poly& rhs ) {

    bool empty = true;
    for (int i = 0; i < rhs.size; i++) {
        if (rhs.polynomial[i] != 0)
            empty = false;
    }
    if (empty) {
        os << " 0";
        return os;
    }
    
    for (int i = rhs.size - 1; i >= 0; i--) {
        if(i > 1) {
            if(rhs.polynomial[i] != 0) {
                os << (rhs.polynomial[i] > 0)? " +":" ";
                os << rhs.polynomial[i] << "x^" << i;
            }
        }
        else if (i == 1) {
            if(rhs.size >= 1 && rhs.polynomial[i] != 0) {
                os << (rhs.polynomial[i] > 0)? " +":" ";
                os << rhs.polynomial[i] << "x";
            }
        }
        else if (i == 0) {
            if(rhs.polynomial[i] != 0) {
                os << (rhs.polynomial[i] > 0)? " +":" ";
                os << rhs.polynomial[i];
            }
        }
    }
    return os;
}

//*********************************************************
// Poly(int, int)
// Constructor
// first parameter is the coefficient of the highest degree
// second parameter is the exponent of the highest degree.
// makes 0^0 if exponent is negative, or parameters are
// missing
Poly::Poly(int a, int e) {
    if( e < 0 )
        a = e = 0;
    size = e + 1;
    polynomial = new int[size]();
    polynomial[e] = a;
}

//*********************************************************
// Poly()
// Constructor
// Constructs a new polynomial copied from the parameter
// polynomial. first parameter is the polynomial class
// to copy.
Poly::Poly(const Poly &p) {
    polynomial = new int[p.size]();
    size = p.size;
    
    for (int i = 0; i < size; i++)
        polynomial[i] = p.polynomial[i];
}

//*********************************************************
// ~Poly()
// Deconstructor
// Deletes the polynomial pointer, freeing the memory.
Poly::~Poly() {
    delete [] polynomial;
}

//*********************************************************
// getCoeff()
// returns the coefficient of an exponent. First parameter
// is the exponent of the coefficient to be returned.
// Reuturns 0 if parameter is out of bounds.
int Poly::getCoeff(int p) {
    // check boundries
    if (p < 0 || p >= size)
        return 0;
    return polynomial[p];
}

//*********************************************************
// setCoeff()
// Sets the coefficient of the exponent parameter.
// First parameter is the new coefficient to be set. Second
// parameter is the exponent of the parameter to be replaced.
bool Poly::setCoeff(int a, int e) {
    // check boundries
    if (e < 0)
        return false;
    if(e+1 > size){
        int largest = (size >= (e + 1))? size:(e+1);
        int* p = new int[largest]();
        for(int i = 0; i < size; i++)
            p[i] = 0;

        for(int i = 0; i < size; i++)
            p[i] = polynomial[i];

        delete [] polynomial;
        polynomial = p;
        size = largest;
    }
    polynomial[e] = a;
    
    return true;
}

//*********************************************************
// operator+
// Add two polynomials.
// Returns a new polynomial object of the sum.
// Parameter is the polynomial to add.
Poly Poly::operator+(const Poly& rhs) {
    int largest = (size>rhs.size)? size:rhs.size;
    Poly temp(0,largest - 1);
   
    for(int i = 0; i < temp.size; i++)
        temp.polynomial[i] = 0;

    for(int i = 0; i < size; i++)
        temp.polynomial[i] = polynomial[i];
    
    for(int i = 0; i < rhs.size; i++)
        temp.polynomial[i] = temp.polynomial[i] + rhs.polynomial[i];
    
    return temp;
}

//*********************************************************
// operator-
// subtract two polynomials.
// Returns a new polynomial object of the sum.
// Parameter is the polynomial to subtract.
Poly Poly::operator-(const Poly& rhs) {
    int largest = (size>rhs.size)? size:rhs.size;
    Poly temp(0,largest - 1);
    
    for(int i = 0; i < temp.size; i++)
        temp.polynomial[i] = 0;

    for(int i = 0; i < size; i++)
        temp.polynomial[i] = polynomial[i];
    
    for(int i = 0; i < rhs.size; i++)
        temp.polynomial[i] = temp.polynomial[i] - rhs.polynomial[i];

    return temp;
}

//*********************************************************
// operator*
// multiply two polynomials.
// Return a new polynomial object of the product.
// Parameter is the polynomial to multiply by.
Poly Poly::operator*(const Poly& rhs) {
    int largest_lhs = size - 1;
    int largest_rhs = rhs.size - 1;
    int temp_size = largest_lhs + largest_rhs + 1;
    
    Poly temp(0, temp_size-1);
    
    for(int i = 0; i < temp.size; i++)
        temp.polynomial[i] = 0;
    
    for(int i = 0; i < size; i++) {
	    for(int j = 0; j < rhs.size; j++)
        	temp.polynomial[i + j] += (polynomial[i] * rhs.polynomial[j]);
    }
    return temp;
}

//*********************************************************
// operator=
// Sets this polynomial to the parameter.
// Returns a pointer to this class.
// Parameter is the polynomial to make equal to
Poly& Poly::operator=(const Poly& rhs) {
    if (this == &rhs)
        return *this;
    
    if (size < rhs.size) {
        delete [] polynomial; 
        polynomial = new int[rhs.size](); 
        size = rhs.size;
    }
    else if (size > rhs.size) {
        for(int i = 0; i < size; i++)
            polynomial[i] = 0;
    }
    
    for(int i = 0; i < rhs.size; i++)
        polynomial[i] = rhs.polynomial[i];
    
    return *this;
}

//*********************************************************
// operator+=
// Add this and parameter polynomial.
// Returns a pointer to this class.
// Parameter is the polynomial to add.
Poly& Poly::operator+=(const Poly& rhs) {
    if (size >= rhs.size) {
        for(int i = 0; i < rhs.size; i++)
            polynomial[i] = polynomial[i] + rhs.polynomial[i];
        return *this;
    }
    else {//(size < rhs.size) {
        int* temp = new int[rhs.size]();
        for(int i = 0; i < rhs.size; i++)
            temp[i] = 0;

        for(int i = 0; i < size; i++)
            temp[i] = polynomial[i];

        for(int i = 0; i < rhs.size; i++)
            temp[i] += rhs.polynomial[i];

        delete [] polynomial; 
        polynomial = temp;
        size = rhs.size;
        return *this;
    }
}

//*********************************************************
// operator-=
// subtract this and parameter polynomial.
// Returns a pointer to this class.
// Parameter is the polynomial to subtract.
Poly& Poly::operator-=(const Poly& rhs) {
    if (size >= rhs.size) {
        for(int i = 0; i < rhs.size; i++)
            polynomial[i] = polynomial[i] - rhs.polynomial[i];
        return *this;
    }
    else {//(size < rhs.size) {
        int* temp = new int[rhs.size]();
        for(int i = 0; i < rhs.size; i++)
            temp[i] = 0;

        for(int i = 0; i < size; i++)
            temp[i] = polynomial[i];

        for(int i = 0; i < rhs.size; i++)
            temp[i] -= rhs.polynomial[i];

        delete [] polynomial; 
        polynomial = temp;
        size = rhs.size;
        return *this;
    }
}

//*********************************************************/////////////////////////////////////////////
// operator*=
// multiply this by parameter polynomial.
// Returns a pointer to this class.
// Parameter is the polynomial to multiply by.
Poly& Poly::operator*=(const Poly& rhs) {
    int sum_of_powers = size - 1 + rhs.size - 1 + 1;
    if(size < sum_of_powers) {
        int* temp = new int[sum_of_powers]();
    
        for(int i = 0; i < sum_of_powers; i++)
            temp[i] = 0;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < rhs.size; j++)
                temp[i + j] += (polynomial[i] * rhs.polynomial[j]);
        }

        delete [] polynomial; 
        polynomial = temp;
        size = sum_of_powers;
    }
    else {
        for(int i = 0; i < size; i++)
            polynomial[i] = 0;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < rhs.size; j++)
                polynomial[i + j] += (polynomial[i] * rhs.polynomial[j]);
        }
    }
    return *this;
}

//*********************************************************
// operator==
// Equal to.
// Return true if the polynomials are the same.
// Parameter is the polynomial to compare to.
bool Poly::operator==( const Poly& rhs) const {
    if (size == rhs.size) {
        for (int i = 0; i < size; i++) {
            if (polynomial[i] != rhs.polynomial[i])
                return false;
        }
    }
    else { // (size != rhs.size)
        int smallest = (size<rhs.size)? size:rhs.size;
        for (int i = 0; i < smallest; i++) {
            if (polynomial[i] != rhs.polynomial[i])
                return false;
        }
        
        if (size > smallest) {
            for (int i = 0; i < size; i++) {
                if (polynomial[i] != 0)
                    return false;
            }
        }
        else if (rhs.size > smallest) {
            for (int i = 0; i < rhs.size; i++) {
                if (rhs.polynomial[i] != 0)
                    return false;
            }
        }
    }
    return true;
}

//*********************************************************
// operator!=
// Not equal to.
// Return true if the polynomials are different from eachoter.
// Parameter is the polynomial to compare to.
bool Poly::operator!=(const Poly& rhs) const {
    if (size == rhs.size) {
        for (int i = 0; i < size; i++) {
            if (polynomial[i] != rhs.polynomial[i])
                return true;
        }
    }
    else { // (size != rhs.size)
        int smallest = (size<rhs.size)? size:rhs.size;
        for (int i = 0; i < smallest; i++) {
            if (polynomial[i] != rhs.polynomial[i])
                return true;
        }
        
        if (size > smallest) {
            for (int i = 0; i < size; i++) {
                if (polynomial[i] != 0)
                    return true;
            }
        }
        else if (rhs.size > smallest) {
            for (int i = 0; i < rhs.size; i++) {
                if (rhs.polynomial[i] != 0)
                    return true;
            }
        }
    }
    return false;
}
