/**
 * Weight class stores and manipulate imperial weights
 *      Assumptions:    zero parameter is a zero weight
 *                      single parameter applies to ounces
 *                      double parameter applies to pounds,ounces
 *                      all operators are to take only integer
 *                              or Weight objects
 * Tested on    Ubuntu 13.04 64bit, intel i5
 * @version 1.0 10-3-2013
 * @author Brandan Haertel, brandan@uw.edu
 * @file Weight.h
 */

#ifndef WEIGHT_H
#define WEIGHT_H
#include <iostream>

using namespace std;

class Weight
{
        //Overload os/is operators
	friend ostream& operator<<(ostream& os, Weight w);
	friend istream& operator>>(istream& is, Weight& w);
    public:
        //Constructor
        Weight(int o = 0);
        Weight(int p, int o);
        
        //get members
        int getPounds();
        int getOunces();
        
        //math operators
        Weight operator+(Weight& w1) const ;
        Weight operator+(int i) const ;
        Weight operator-(Weight& w1) const ;
        Weight operator-(int i) const ;
        Weight operator*(Weight& w1) const ;
        Weight operator*(int i) const ;
        Weight operator/(Weight& w1) const ;
        Weight operator/(int divider) const ;
        Weight operator-() const ;
        
        //comparison operators
        bool operator<=(Weight& w1) const ;
        bool operator>=(Weight& w1) const ;
        bool operator>(Weight& w1) const ;
        bool operator<(Weight& w1) const ;
        bool operator==(Weight& w1) const ;
        bool operator!=(Weight& w1) const ;
        
        //assignment operators
	Weight operator+=(Weight& w);
	Weight& operator=(Weight& w);
	Weight& operator=(int i);
	Weight& operator-=(Weight& w);
	Weight& operator-=(int i);
	Weight& operator*=(Weight& w);
	Weight& operator*=(int i);
	Weight& operator/=(Weight& w);
	Weight& operator/=(int i);
        
        //deconstructor
        virtual ~Weight();
    protected:
    private:
        void setWeight(int p, int o);
        int lb;
        int oz;
        static const int MAX_OZ = 16;
};

//ostream& operator<<(const Weight& w)
//{
//  return os << "ha!";
//}

#endif // WEIGHT_H
