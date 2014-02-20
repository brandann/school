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

#include "Distance.h"
#include <iostream>

using namespace std;

/**
 * InStream operator overload.
 * @param is instream
 * @param distance Distance class to apply input to
 * @return istream pointer
 */
istream& operator>>(istream& is, Distance& distance)
{
    int ft, in;

    is>>ft;
    is>>in;

    Distance distance_return(ft, in);
    distance = distance_return;
    return is;
}

/**
 * ostream operator overload.
 * @param os ostram
 * @param distance Distance class to be displayed in ostream
 * @return ostream pointer
 */
ostream& operator<<(ostream& os, Distance distance)
{
    int ft = distance.getFeet();
    int in = distance.getInches();
    
    if(in == 0 && ft == 0)
        return os << "0 inch";
    
    if(ft != 0)
    {
        os << ft;
        if(ft > 1 || ft < -1)
            os << " feet";
        else
            os << " foot";
    }
    
    if(ft != 0 && in != 0)
        os << " ";
    
    if(in != 0)
    {
        os << in;
        if(in > 1 || in < 1)
            os << " inches";
        else
            os << " inch";
    }
    
    return os;
}

/**
 * Default Distance constructor taking 0 or 1 arguments
 * one argument defaults to 0ft 0in.
 * @param in inch value, feet set to 0
 */
Distance::Distance(int in)
{
    setDistance(0, in);
}

/**
 * double Distance constructor, takes two parameters
 * @param ft feet
 * @param in inches
 */
Distance::Distance(int ft, int in)
{
    setDistance(ft, in);
}

/**
 * sets the feet and inches for the class, adjusts values
 * to keep ft and inch similar signs, and keeps inches under
 * 12in and over -12in
 * @param ft feet to set to class
 * @param in inches to set to class
 */
void Distance::setDistance(int ft, int in)
{
    int distance = ft * MAX_INCHES + in;
    feet = distance/MAX_INCHES;
    inch = distance - ((distance/MAX_INCHES) * MAX_INCHES);
}

/**
 * get method returns feet value
 * @return feet
 */
int Distance::getFeet()
{
    return feet;
}

/**
 * get method returns inches
 * @return inches
 */
int Distance::getInches()
{
    return inch;
}

/**
 * add operator overload
 * @param distance Distance object to add
 * @return Distance object with the value of this+distance
 */
Distance Distance::operator+(Distance& distance) const
{
    int ft = feet + distance.getFeet();
    int in = inch + distance.getInches();
    Distance distance_return(ft, in);
    return distance_return;
}

/**
 * add operator overload
 * @param i integer value of inches to add
 * @return Distance object with the value of this+i
 */
Distance Distance::operator+(int i) const
{
    int in = inch + i;
    int ft = feet;
    Distance distance_return(ft, in);
    return distance_return;
}

/**
 * subtraction operator overload
 * @param distance Distance object to subtract
 * @return Distance object with value of this-distance
 */
Distance Distance::operator-(Distance& distance) const 
{
    int ft = feet - distance.getFeet();
    int in = inch - distance.getInches();
    Distance distance_return(ft, in);
    return distance_return;
}

/**
 * subtraction operator overload
 * @param i integer value of inches to subtract
 * @return Distance object with value of this-i
 */
Distance Distance::operator-(int i) const 
{
    int in = inch - i;
    int ft = feet;
    Distance distance_return(ft, in);
    return distance_return;
}

/**
 * multiply operator overload
 * @param distance Distance object to multiply by
 * @return Distance object with value of this*distance
 */
Distance Distance::operator*(Distance& distance) const 
{
    int ft = feet * distance.getFeet();
    int in;
    if(inch == 0)
        in = distance.getInches();
    else if(distance.getInches() == 0)
        in = inch;
    else
        in = inch * distance.getInches();
    Distance distance_return(ft, in);
    return distance_return;
}

/**
 * multiply operator overload
 * @param i value to multiply by
 * @return Distance object with value of this*i
 */
Distance Distance::operator*(int i) const 
{
    int ft = feet * i;
    int in = inch * i;
    Distance distance_return(ft, in);
    return distance_return;
}

/**
 * division operator overload
 * @param distance value to divide by
 * @return Distance object with value of this/distance
 */
Distance Distance::operator/(Distance& distance) const 
{

    float distance1 = feet * MAX_INCHES + inch;
    float distance2 = distance.getFeet() * MAX_INCHES + distance.getInches();
    if(distance2 == 0.0f)
    {
    	Distance distance_return(feet, inch);
    	return distance_return;
    }
    float fdistance = distance1 / distance2;
    int idistance = fdistance;
    int ft = idistance;
    int in = (fdistance - idistance) * MAX_INCHES;
    Distance distance_return(ft, in);
    return distance_return;
}

/**
 * division operator overload
 * @param i value to divide by
 * @return Distance object with value of this/i
 */
Distance Distance::operator/(int divider) const 
{
    if(divider != 0)
    {
        int ft = feet / divider;
        int in = inch / divider;
        Distance distance_return(ft, in);
        return distance_return;
    }
    Distance distance_return(feet, inch);
    return distance_return;
}

/**
 * Unary subtraction operator overload
 * @return negative value of this
 */
Distance Distance::operator-() const 
{
    Distance distance_return(feet * -1, inch * -1);
    return distance_return;
}

/**
 * less then or equal to operator overload
 * @param distance Distance to compare to
 * @return true if this is less than or equal distance
 */
bool Distance::operator<=(Distance& distance) const 
{
    int this_distance = feet * MAX_INCHES + inch;
    int inref_distance = distance.getFeet() * MAX_INCHES + distance.getInches();
    return (this_distance <= inref_distance);
}

/**
 * greater than or equal to operator overload
 * @param distance Distance to compare to
 * @return true if this is greater than or equal distance
 */
bool Distance::operator>=(Distance& distance) const 
{
    int this_distance = feet * MAX_INCHES + inch;
    int inref_distance = distance.getFeet() * MAX_INCHES + distance.getInches();
    return (this_distance >= inref_distance);
}

/**
 * greater than to operator overload
 * @param distance Distance to compare to
 * @return true if this is greater than distance
 */
bool Distance::operator>(Distance& distance) const 
{
    int this_distance = feet * MAX_INCHES + inch;
    int inref_distance = distance.getFeet() * MAX_INCHES + distance.getInches();
    return (this_distance > inref_distance);
}

/**
 * less than operator overload
 * @param distance Distance to compare to
 * @return true if this is less than distance
 */
bool Distance::operator<(Distance& distance) const 
{
    int this_distance = feet * MAX_INCHES + inch;
    int inref_distance = distance.getFeet() * MAX_INCHES + distance.getInches();
    return (this_distance < inref_distance);
}

/**
 * equal to operator overload
 * @param distance Distance to compare to
 * @return true if this equal distance
 */
bool Distance::operator==(Distance& distance) const 
{
    int this_distance = feet * MAX_INCHES + inch;
    int inref_distance = distance.getFeet() * MAX_INCHES + distance.getInches();
    return (this_distance == inref_distance);
}

/**
 * not equal to operator overload
 * @param distance Distance to compare to
 * @return true if this is not equal distance
 */
bool Distance::operator!=(Distance& distance) const 
{
    int this_distance = feet * MAX_INCHES + inch;
    int inref_distance = distance.getFeet() * MAX_INCHES + distance.getInches();
    return (this_distance != inref_distance);
}

/**
 * add assignment operator overload
 * @param distance Distance to add to this
 * @return Distance of this + distance
 */
Distance Distance::operator+=(Distance& distance)
{
	int ft = feet + distance.getFeet();
	int in = inch + distance.getInches();
	setDistance(ft, in);
	return *this;
}

/**
 * equal assignment operator overload
 * @param distance Distance to set to this
 * @return this Distance
 */
Distance& Distance::operator=(Distance& distance)
{
	setDistance(distance.getFeet(), distance.getInches());
	return *this;
}

/**
 * equal assignment operator overload
 * @param i inch to set this to
 * @return this Distance
 */
Distance& Distance::operator=(int i)
{
	setDistance(0,i);
	return *this;
}

/**
 * subtraction assignment operator overload
 * @param distance Distance to subtract from this
 * @return Distance of this - distance
 */
Distance& Distance::operator-=(Distance& distance)
{
	setDistance(feet - distance.getFeet(), inch - distance.getInches());
	return *this;
}

/**
 * subtraction assignment operator overload
 * @param i inch to subtract from this
 * @return Distance of this - i
 */
Distance& Distance::operator-=(int i)
{
	setDistance(feet, inch - i);
	return *this;
}

/**
 * multiply assignment operator overload
 * @param distance Distance to multiply by this
 * @return Distance of this * distance
 */
Distance& Distance::operator*=(Distance& distance)
{
	int ft = feet * distance.getFeet();
    	int in;
    	if(inch == 0)
    	    in = distance.getInches();
    	else if(distance.getInches() == 0)
    	    in = inch;
    	else
    	    in = inch * distance.getInches();
    	setDistance(ft, in);
    	return *this;
}

/**
 * multiply assignment operator overload
 * @param i value to multiply by this
 * @return Distance of this * i
 */
Distance& Distance::operator*=(int i)
{
	int ft = feet * i;
    	int in = inch * i;
    	setDistance(ft, in);
	return *this;
}

/**
 * divider assignment operator overload
 * @param distance Distance to divide by this
 * @return Distance of this / distance
 */
Distance& Distance::operator/=(Distance& distance)
{
    	float distance1 = feet * MAX_INCHES + inch;
    	float distance2 = distance.getFeet() * MAX_INCHES + distance.getInches();
    	if(distance2 == 0.0f)
    		return *this;
    	float fdistance = distance1 / distance2;
    	int idistance = fdistance;
    	int ft = idistance;
    	int in = (fdistance - idistance) * MAX_INCHES;
    	setDistance(ft, in);
	return *this;
}

/**
 * divider assignment operator overload
 * @param i value to divide by this
 * @return Distance of this / i
 */
Distance& Distance::operator/=(int i)
{
	if(i == 0)
		return *this;
        
	int distance = feet * MAX_INCHES + inch;
        int neinref_distance = distance/i;
        int ft = neinref_distance/MAX_INCHES;
        int in = neinref_distance - ((neinref_distance/MAX_INCHES) * MAX_INCHES);
    	setDistance(ft, in);
	return *this;
}

/**
 * Deconstructor (not in use)
 */
Distance::~Distance()
{
    //dtor
}
