//recordable.h


#ifndef RECORDABLE_H
#define RECORDABLE_H
#include <iostream>

using namespace std;

//Recordable class:
//    - an abstract class
//    - all all items stored in a tree, inherit from recordable
//    - a simple class, all methods are pure virtual
//    - contains no member variables

class Recordable
{
public:  
    //------------------------------------------------------------------------
    // constructor
    Recordable();

    //------------------------------------------------------------------------
    // destructor
    virtual ~Recordable();

    //------------------------------------------------------------------------
    // comparison operators
    virtual bool operator==(const Recordable&)const = 0;
    virtual bool operator<(const Recordable&)const = 0;
    virtual bool operator>(const Recordable&)const = 0;

    //------------------------------------------------------------------------
    // pure virtual functions for getting and setting data, they are all
    // required for other classes to work properly such as build command and
    // item factory
    virtual string toString() const = 0;
    virtual void setData(string) = 0;
    virtual Recordable* create() const = 0;
    virtual char getType() const = 0;
   
protected:

private:
    
};

#endif // RECORDABLE_H
