//Youth.cpp

#include "youth.h"

using namespace std;

// Youth Constructor
// Inherits from Book
Youth::Youth():Book()
{
	//Youth takes same parameters as book
}

//--------------------------------------------------------------------------
// Destructor
Youth::~Youth()
{
	// do nothing
}

//--------------------------------------------------------------------------
// getType (Public)
char Youth::getType()const{
    return 'Y';
}

//--------------------------------------------------------------------------
// operator== (Public)
// compares a youth to a recordable
bool Youth::operator==(const Recordable& rhs) const{
    
    //check to make sure the class types are the same
    if (typeid(*this) != typeid(rhs)) {return false;}
 
    //if they are the same, cast the recordable to the correct base class
    Recordable *r = const_cast<Recordable*>(&rhs);
    Youth *p = dynamic_cast<Youth*>(r);
    
    //compares the member variables depending on how the class must be sorted
	if (this == p) {cout<<"object is comparing self to self";}
    
	if (title != p->getTitle()) {return false;}
    
    if (author != p->getAuthor()) {return false;}
		
	return true;
}

//--------------------------------------------------------------------------
// operator> (Public)
// compares a concrete class to a recordable
bool Youth::operator>( const Recordable& rhs) const{
    
    //check to make sure the class types are the same
    if (typeid(*this) != typeid(rhs)) {return false;}
    
    //if they are the same, cast the recordable to the correct base class
    Recordable *r = const_cast<Recordable*>(&rhs);
    Youth *p = dynamic_cast<Youth*>(r);
    
    //compares the member variables depending on how the class must be sorted
    if(title > p->getTitle()){
        return true;
    
    }else if(title == p->getTitle()){
        
        if (author > p->getAuthor()){
            return true;
        }
    }
    return false;
}

//--------------------------------------------------------------------------
// operator< (Public)
// compares a youth to a recordable
bool Youth::operator<( const Recordable& rhs) const{
    
     //check to make sure the class types are the same
    if (typeid(*this) != typeid(rhs)) {return false;}
    
     //if they are the same, cast the recordable to the correct base class
    Recordable *r = const_cast<Recordable*>(&rhs);
    Youth *p = dynamic_cast<Youth*>(r);
    
     //compares the member variables depending on how the class must be sorted
    if(title < p->getTitle()){
        return true; 
    }
    else if(title == p->getTitle()){
        
        if (author < p->getAuthor()){
            return true;
        }
    }
    return false;
}

//--------------------------------------------------------------------------
// toString (Public)
string Youth:: toString()const{
    stringstream ss;
    string str;
    ss << left << setw(6) << quantity << setw(40) << title << setw(30) 
            << author << setw(4) << year;
    getline(ss, str);
    return str;
}

//--------------------------------------------------------------------------
// setData (Public)
void Youth::setData(string str){
    
    std::stringstream ss(str);
    
    ss.get(); //remove type
    ss.get(); //remove space
    getline(ss, author, ',');
    
    ss.get(); //remove space
    getline(ss, title, ',');
    
    ss.get(); //remove space
    ss >> year;
}

//--------------------------------------------------------------------------
// Create Youth Object (Public)
// Used by the ItemFactory to dynamically create Objects based on the
// character 'Y'
Recordable* Youth::create() const{
    Youth *y = new Youth();
    return y;
}

//--------------------------------------------------------------------------
// used for outputting a specific format when printing a patron's history
// toStringHistory (Public)
string Youth::toStringHistory() const{
    stringstream ss;
    string str;
    ss << left << setw(40) << title << setw(20) 
            << author << setw(4) << year;
    getline(ss, str);
    return str;
}

//--------------------------------------------------------------------------
// a special version of setData to handle data that comes in backwards
// setTempData (Public)
void Youth::setTempData(string str) {
    std::stringstream ss(str);
    
    getline(ss, title, ',');
    ss.get(); //remove space
    getline(ss, author, ',');
    year = "";
}
