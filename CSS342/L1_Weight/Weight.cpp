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

#include "weight.h"
#include <iostream>

using namespace std;

/**
 * InStream operator overload.
 * @param is instream
 * @param w weight class to apply input to
 * @return istream pointer
 */
istream& operator>>(istream& is, Weight& w)
{
    int p, o;

    is>>p;
    is>>o;

    Weight weight(p,o);
    w = weight;
    return is;
}

/**
 * ostream operator overload.
 * @param os ostram
 * @param w weight class to be displayed in ostream
 * @return ostream pointer
 */
ostream& operator<<(ostream& os, Weight w)
{
    if(w.getPounds() != 0)
    {
        os << w.getPounds() << " lb";
        if (w.getPounds() != 1)
            os << "s";
        if (w.getOunces() != 0)
            os << " ";
    }
    if (w.getOunces() != 0)
    {
        os << w.getOunces() << " oz";
        if(w.getOunces() != 1)
            os << "s";
    }
    if(w.getOunces() == 0 && w.getPounds() == 0)
        os << "0 oz";
    return os;
}

/**
 * Default Weight constructor taking 0 or 1 arguments
 * one argument defaults to 0lb 0oz.
 * @param o ounce value, pounds set to 0
 */
Weight::Weight(int o)
{
    setWeight(0, o);
}

/**
 * double Weight constructor, takes two parameters
 * @param p pounds
 * @param o ounces
 */
Weight::Weight(int p, int o)
{
    setWeight(p,o);
}

/**
 * sets the pounds and ounces for the class, adjusts values
 * to keep lbs and oz similar signs, and keeps ounces under
 * 16oz and over -16oz
 * @param p pounds to set to class
 * @param o ounces to set to class
 */
void Weight::setWeight(int p, int o)
{
    int weight = p*MAX_OZ + o;
    lb = weight/MAX_OZ;
    oz = weight - ((weight/MAX_OZ)*MAX_OZ);
}

/**
 * get method returns pounds value
 * @return pounds
 */
int Weight::getPounds()
{
    return lb;
}

/**
 * get method returns ounces
 * @return ounces
 */
int Weight::getOunces()
{
    return oz;
}

/**
 * add operator overload
 * @param w1 Weight object to add
 * @return Weight object with the value of this+w1
 */
Weight Weight::operator+(Weight& w1) const
{
    int wlb = lb + w1.getPounds();
    int woz = oz + w1.getOunces();
    Weight w(wlb, woz);
    return w;
}

/**
 * add operator overload
 * @param i integer value of ounces to add
 * @return Weight object with the value of this+i
 */
Weight Weight::operator+(int i) const
{
    int o = oz + i;
    int p = lb;
    Weight w(p, o);
    return w;
}

/**
 * subtraction operator overload
 * @param w1 Weight object to subtract
 * @return Weight object with value of this-w1
 */
Weight Weight::operator-(Weight& w1) const 
{
    int wlb = lb - w1.getPounds();
    int woz = oz - w1.getOunces();
    Weight w(wlb, woz);
    return w;
}

/**
 * subtraction operator overload
 * @param i integer value of ounces to subtract
 * @return Weight object with value of this-i
 */
Weight Weight::operator-(int i) const 
{
    int o = oz - i;
    int p = lb;
    Weight w(p,o);
    return w;
}

/**
 * multiply operator overload
 * @param w1 Weight object to multiply by
 * @return Weight object with value of this*w1
 */
Weight Weight::operator*(Weight& w1) const 
{
    int wlb = lb * w1.getPounds();
    int woz;
    if(oz == 0)
        woz = w1.getOunces();
    else if(w1.getOunces() == 0)
        woz = oz;
    else
        woz = oz * w1.getOunces();
    Weight w(wlb, woz);
    return w;
}

/**
 * multiply operator overload
 * @param i value to multiply by
 * @return Weight object with value of this*i
 */
Weight Weight::operator*(int i) const 
{
    int p = lb * i;
    int o = oz * i;
    Weight w(p,o);
    return w;
}

/**
 * division operator overload
 * @param w1 value to divide by
 * @return Weight object with value of this/w1
 */
Weight Weight::operator/(Weight& w1) const 
{

    float weight1 = lb * MAX_OZ + oz;
    float weight2 = w1.getPounds() * MAX_OZ + w1.getOunces();
    if(weight2 == 0.0f)
    {
    	Weight w(lb, oz);
    	return w;
    }
    float fweight = weight1 / weight2;
    int iweight = fweight;
    int wlb = iweight;
    int woz = (fweight - iweight) * MAX_OZ;
    Weight w(wlb, woz);
    return w;
}

/**
 * division operator overload
 * @param i value to divide by
 * @return Weight object with value of this/i
 */
Weight Weight::operator/(int divider) const 
{
    if(divider != 0)
    {
        int p = lb / divider;
        int o = oz / divider;
        Weight w(p,o);
        return w;
    }
    Weight w(lb, oz);
    return w;
}

/**
 * Unary subtraction operator overload
 * @return negative value of this
 */
Weight Weight::operator-() const 
{
    Weight w(lb*-1,oz*-1);
    return w;
}

/**
 * less then or equal to operator overload
 * @param w Weight to compare to
 * @return true if this is less than or equal to w
 */
bool Weight::operator<=(Weight& w) const 
{
    int this_weight = lb * MAX_OZ + oz;
    int w_weight = w.getPounds() * MAX_OZ + w.getOunces();
    return (this_weight <= w_weight);
}

/**
 * greater than or equal to operator overload
 * @param w Weight to compare to
 * @return true if this is greater than or equal to w
 */
bool Weight::operator>=(Weight& w) const 
{
    int this_weight = lb * MAX_OZ + oz;
    int w_weight = w.getPounds() * MAX_OZ + w.getOunces();
    return (this_weight >= w_weight);
}

/**
 * greater than to operator overload
 * @param w Weight to compare to
 * @return true if this is greater than w
 */
bool Weight::operator>(Weight& w) const 
{
    int this_weight = lb * MAX_OZ + oz;
    int w_weight = w.getPounds() * MAX_OZ + w.getOunces();
    return (this_weight > w_weight);
}

/**
 * less than operator overload
 * @param w Weight to compare to
 * @return true if this is less than w
 */
bool Weight::operator<(Weight& w) const 
{
    int this_weight = lb * MAX_OZ + oz;
    int w_weight = w.getPounds() * MAX_OZ + w.getOunces();
    return (this_weight < w_weight);
}

/**
 * equal to operator overload
 * @param w Weight to compare to
 * @return true if this equal to w
 */
bool Weight::operator==(Weight& w) const 
{
    int this_weight = lb * MAX_OZ + oz;
    int w_weight = w.getPounds() * MAX_OZ + w.getOunces();
    return (this_weight == w_weight);
}

/**
 * not equal to operator overload
 * @param w Weight to compare to
 * @return true if this is not equal to w
 */
bool Weight::operator!=(Weight& w) const 
{
    int this_weight = lb * MAX_OZ + oz;
    int w_weight = w.getPounds() * MAX_OZ + w.getOunces();
    return (this_weight != w_weight);
}

/**
 * add assignment operator overload
 * @param w Weight to add to this
 * @return Weight of this + w
 */
Weight Weight::operator+=(Weight& w)
{
	int wlb = lb + w.getPounds();
	int woz = oz + w.getOunces();
	setWeight(wlb, woz);
	return *this;
}

/**
 * equal assignment operator overload
 * @param w Weight to set to this
 * @return this Weight
 */
Weight& Weight::operator=(Weight& w)
{
	setWeight(w.getPounds(), w.getOunces());
	return *this;
}

/**
 * equal assignment operator overload
 * @param i ounce to set this to
 * @return this Weight
 */
Weight& Weight::operator=(int i)
{
	setWeight(0,i);
	return *this;
}

/**
 * subtraction assignment operator overload
 * @param w Weight to subtract from this
 * @return Weight of this - w
 */
Weight& Weight::operator-=(Weight& w)
{
	setWeight(lb-w.getPounds(), oz-w.getOunces());
	return *this;
}

/**
 * subtraction assignment operator overload
 * @param i ounce to subtract from this
 * @return Weight of this - i
 */
Weight& Weight::operator-=(int i)
{
	setWeight(lb, oz-i);
	return *this;
}

/**
 * multiply assignment operator overload
 * @param w Weight to multiply by this
 * @return Weight of this * w
 */
Weight& Weight::operator*=(Weight& w)
{
	int wlb = lb * w.getPounds();
    	int woz;
    	if(oz == 0)
    	    woz = w.getOunces();
    	else if(w.getOunces() == 0)
    	    woz = oz;
    	else
    	    woz = oz * w.getOunces();
    	setWeight(wlb, woz);
    	return *this;
}

/**
 * multiply assignment operator overload
 * @param i value to multiply by this
 * @return Weight of this * i
 */
Weight& Weight::operator*=(int i)
{
	int p = lb * i;
    	int o = oz * i;
    	setWeight(p,o);
	return *this;
}

/**
 * divider assignment operator overload
 * @param w Weight to divide by this
 * @return Weight of this / w
 */
Weight& Weight::operator/=(Weight& w)
{
    	float weight1 = lb * MAX_OZ + oz;
    	float weight2 = w.getPounds() * MAX_OZ + w.getOunces();
    	if(weight2 == 0.0f)
    		return *this;
    	float fweight = weight1 / weight2;
    	int iweight = fweight;
    	int wlb = iweight;
    	int woz = (fweight - iweight) * MAX_OZ;
    	setWeight(wlb, woz);
	return *this;
}

/**
 * divider assignment operator overload
 * @param i value to divide by this
 * @return Weight of this / i
 */
Weight& Weight::operator/=(int i)
{
	if(i == 0)
		return *this;
        
	int w1 = lb * MAX_OZ + oz;
        int w2 = i;
        int weight = w1/w2;
        int pounds = weight/MAX_OZ;
        int ounces = weight - ((weight/MAX_OZ) * MAX_OZ);
    	setWeight(pounds, ounces);
	return *this;
}

/**
 * Deconstructor (not in use)
 */
Weight::~Weight()
{
    //dtor
}

