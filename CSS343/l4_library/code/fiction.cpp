//fiction.cpp

#include "fiction.h"


using namespace std;

// Fiction Constructor
// Inherits from Book
Fiction::Fiction():Book()
{
	//fiction takes the same parameters as book
}

//--------------------------------------------------------------------------
// Destructor
Fiction::~Fiction()
{
	// do nothing
}

//--------------------------------------------------------------------------
// getType (Public)
char Fiction::getType()const{
    return 'F';
}

//--------------------------------------------------------------------------
// operator== (Public)
// compares a ficiton to a recordable
bool Fiction::operator==(const Recordable& rhs) const{
    
    //check to make sure the class types are the same
    if (typeid(*this) != typeid(rhs)) {return false;}
    
    //if they are the same, cast the recordable to the correct base class
    Recordable *r = const_cast<Recordable*>(&rhs);
    Fiction *p = dynamic_cast<Fiction*>(r);
    
    //compares the member variables depending on how the class must be sorted
	if (this == p) {cout<<"object is comparing self to self";}
    
	if (author != p->getAuthor()) {return false;}
    
    if (title != p->getTitle()) {return false;}
    
	return true;
}

//--------------------------------------------------------------------------
// operator> (Public)
// compares a fiction to a recordable
bool Fiction::operator>( const Recordable& rhs) const{
    
    //check to make sure the class types are the same
    if (typeid(*this) != typeid(rhs)) {return false;}
    
    //if they are the same, cast the recordable to the correct base class
    Recordable *r = const_cast<Recordable*>(&rhs);
    Fiction *p = dynamic_cast<Fiction*>(r);
    
    //compares the member variables depending on how the class must be sorted
    if(author > p->getAuthor()){
        return true;
        
    }else if(author == p->getAuthor()){
        
        if (title > p->getTitle()){
            return true;
        }
    }
    return false;
}

//--------------------------------------------------------------------------
// operator< (Public)
// compares a fiction to a recordable
bool Fiction::operator<( const Recordable& rhs) const{
    
    //check to make sure the class types are the same
    if (typeid(*this) != typeid(rhs)) {return false;}
    
    //if they are the same, cast the recordable to the correct base class
    Recordable *r = const_cast<Recordable*>(&rhs);
    Fiction *p = dynamic_cast<Fiction*>(r);
    
    //compares the member variables depending on how the class must be sorted
    if(author < p->getAuthor()){
        return true;
        
    }else if(author == p->getAuthor()){
        
        if (title < p->getTitle()){
            return true;
        }
    }
    return false;
}

//--------------------------------------------------------------------------
// toString (Public)
string Fiction::toString()const{
    stringstream ss;
    string str;
    ss << left << setw(6) << quantity << setw(20) << author << setw(40) 
            << title << setw(4) << year;
    getline(ss, str);
    return str;
}

//--------------------------------------------------------------------------
// setData (Public)
void Fiction::setData(string str){
    
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
// Create (Public)
// Create Fiction Object (Public)
// Used by the ItemFactory to dynamically create Objects based on the
// character 'F'
Recordable* Fiction::create() const{
    Fiction *f = new Fiction();
    return f;
}

//--------------------------------------------------------------------------
// used for outputting a specific format when printing a patron's history
// toStringHistory (Public)
string Fiction::toStringHistory() const{
    stringstream ss;
    string str;
    ss << left << setw(20) << author << setw(40) 
            << title << setw(4) << year;
    getline(ss, str);
    return str;
}

//--------------------------------------------------------------------------
// a special version of setData to handle data that comes in backwards
// setTempData (Public)
void Fiction::setTempData(string str) {
    std::stringstream ss(str);
    getline(ss, author, ',');
    ss.get(); //remove space
    getline(ss, title, ',');
    year = "";
}