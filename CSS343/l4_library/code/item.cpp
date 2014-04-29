//item.cpp

#include "item.h"

using namespace std;

//Constructor
Item::Item():Recordable()
{
	quantity = 5;
}

//--------------------------------------------------------------------------
// Destructor
Item::~Item()
{
	// do nothing
}

//--------------------------------------------------------------------------
// getQuantity (Public)
int Item::getQuantity() const{
	return quantity;
}

//--------------------------------------------------------------------------
// getTitle (Public)
string Item::getTitle() const{
	return title;
}

//--------------------------------------------------------------------------
// getYear (Public)
string Item::getYear() const
{
	return year;
}

//--------------------------------------------------------------------------
// subtract quantity by 1 (Public)
void Item :: quantityMinusMinus(){
    if(quantity > 0){
        quantity--;
    }
}

//--------------------------------------------------------------------------
// add 1 to quantity (Public)
void Item :: quantityPlusPlus(){
    if(quantity < 5){
        quantity++;
    }
}