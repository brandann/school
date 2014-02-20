/**
 * Distance class stores and manipulate imperial distances
 *      Assumptions:    zero parameter is a zero distance
 *                      single parameter applies to inches
 *                      double parameter applies to feet, inches
 *                      all operators are to take only integer
 *                              or Distance objects
 * 
 *      I was unable to implement const into my functions,
 *      my program would crash every time saying:
 *              Distance.cpp:xxx:xx: error: passing 'const Distance' as 'this' argument of 'int Distance::getFeet(); discards qualifiers [-fpermissive]
 *      any assistance in fixing this problem would be greatly appreciated.
 * 
 * Tested on    Ubuntu 13.04 64bit, intel i5
 * @version 1.0 10-9-2013
 * @author Brandan Haertel, brandan(at)uw.edu
 * @file Distance.h
 */

#ifndef WEIGHT_H
#define WEIGHT_H
#include <iostream>

using namespace std;

class Distance
{
        //Overload os/is operators
	friend ostream& operator<<(ostream& os, Distance distance);
	friend istream& operator>>(istream& is, Distance& distance);
    public:
        //Constructor
        Distance(int in = 0);
        Distance(int ft, int in);
        
        //get members
        int getFeet();
        int getInches();
        
        //math operators
        Distance operator+(Distance& distance) const ;
        Distance operator+(int i) const ;
        Distance operator-(Distance& distance) const ;
        Distance operator-(int i) const ;
        Distance operator*(Distance& distance) const ;
        Distance operator*(int i) const ;
        Distance operator/(Distance& distance) const ;
        Distance operator/(int divider) const ;
        Distance operator-() const ;
        
        //comparison operators
        bool operator<=(Distance& distance) const ;
        bool operator>=(Distance& distance) const ;
        bool operator>(Distance& distance) const ;
        bool operator<(Distance& distance) const ;
        bool operator==(Distance& distance) const ;
        bool operator!=(Distance& distance) const ;
        
        //assignment operators
	Distance operator+=(Distance& distance);
	Distance& operator=(Distance& distance);
	Distance& operator=(int i);
	Distance& operator-=(Distance& distance);
	Distance& operator-=(int i);
	Distance& operator*=(Distance& distance);
	Distance& operator*=(int i);
	Distance& operator/=(Distance& distance);
	Distance& operator/=(int i);
        
        //deconstructor
        virtual ~Distance();
    protected:
    private:
        void setDistance(int ft, int in);
        int feet;
        int inch;
        static const int MAX_INCHES = 12;
};

#endif // WEIGHT_H