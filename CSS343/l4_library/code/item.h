// item.h



#ifndef ITEM_H
#define ITEM_H

#include <iostream>
#include "recordable.h"
using namespace std;

//Item class: inherits from Recordable
//    - an abstract class
//    - all items inherit from item class giving them seveal protected variables
//    - a simple class with getters, setters, and pure virtual methods
//    - there are several containers that use item for the container type

class Item : public Recordable
{
public:
    //-------------------------------------------------------------------------
    // constructor
    Item();

    //-------------------------------------------------------------------------
    // destructor
    ~Item();

    //-------------------------------------------------------------------------
    // Returns the number of copies of an Item.
    int getQuantity() const;

    //-------------------------------------------------------------------------
    // Returns the title of the periodical.
    string getTitle() const;

    //-------------------------------------------------------------------------
    // Returns the year of the periodical's publication.
    string getYear() const;

    //-------------------------------------------------------------------------
    // interface to make sure all items have a toString method
    virtual string toString() const = 0;
    
    //-------------------------------------------------------------------------
    // interface to make sure all items have a toStringHistory method
    virtual string toStringHistory() const = 0;
    
    //-------------------------------------------------------------------------
    // a method to subtract quantity by 1, but also make sure quantity stays
    // a protected variable
    void  quantityMinusMinus();
    
    //-------------------------------------------------------------------------
    // a method to add quantity by 1, but also make sure quantity stays
    // a protected variable
    void quantityPlusPlus();
    
    //-------------------------------------------------------------------------
    // interface to make sure all items have a setTempData method
    virtual void setTempData(string) = 0;

protected:
    int quantity;  // number of copies of the item
    string title;  // item's title
    string year;   // item's year of publication
    
private:

};

#endif // ITEM_H
