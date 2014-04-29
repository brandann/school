//periodical.cpp

#include "periodical.h"
#include <sstream>
#include <iomanip>


using namespace std;

//--------------------------------------------------------------------------
// constructor
Periodical::Periodical() : Item()
{

}

//--------------------------------------------------------------------------
// destructor
Periodical::~Periodical(){
	// do nothing
}

//--------------------------------------------------------------------------
// getMonth (Public)
string Periodical::getMonth() const{
	return month;
}

//--------------------------------------------------------------------------
// getType (Public)
char Periodical::getType()const{
	return 'P';
}
   
//--------------------------------------------------------------------------
// operator== (Public)
// compares a Periodical to a recordable
bool Periodical::operator==(const Recordable& rhs) const{
    
    //check to make sure the class types are the same
    if (typeid(*this) != typeid(rhs)) {
        return false;
    }
    //if they are the same, cast the recordable to the correct base class
    Recordable *r = const_cast<Recordable*>(&rhs);
    Periodical *p = dynamic_cast<Periodical*>(r);
    
    //compares the member variables depending on how the class must be sorted
    if (this == p)
            return true;
    if (year != p->getYear())
            return false;
    if (month != p->getMonth())
            return false;
    if (title != p->getTitle())
            return false;
    return true;
}
   
//--------------------------------------------------------------------------
// operator> (Public)
// compares a Periodical class to a recordable
bool Periodical::operator>( const Recordable& rhs) const{
    
    //check to make sure the class types are the same
    if (typeid(*this) != typeid(rhs)) {
        return false;
    }
    
    //if they are the same, cast the recordable to the correct base class
    Recordable *r = const_cast<Recordable*>(&rhs);
    Periodical *p = dynamic_cast<Periodical*>(r);
    
    //compares the member variables depending on how the class must be sorted
    if (*this == *p)
            return false;

    if(year > p->getYear())
        return true;
    else if(year == p->getYear()){
        if (month > p->getMonth())
            return true;
        else if (month == p->getMonth()){
            if (title > p->getTitle())
                return true;
            else if (title == p->getTitle())
                return true;
        }
    }
    return false;
}
   
//--------------------------------------------------------------------------
// operator< (Public)
// compares a Periodical to a recordable
bool Periodical::operator<( const Recordable& rhs) const{
    
     //check to make sure the class types are the same
    if (typeid(*this) != typeid(rhs)) {
        return false;
    }
    
    //if they are the same, cast the recordable to the correct base class
    Recordable *r = const_cast<Recordable*>(&rhs);
    Periodical *p = dynamic_cast<Periodical*>(r);
    
    //compares the member variables depending on how the class must be sorted
    if (*this == *p)
            return false;
    
    if(year < p->getYear())
        return true;
    else if(year == p->getYear()){
        if (month < p->getMonth())
            return true;
        else if (month == p->getMonth()){
            if (title < p->getTitle())
                return true;
            else if (title == p->getTitle())
                return true;
        }
    }
    return false;
}

//--------------------------------------------------------------------------
// toString (Public)
string Periodical::toString() const{
    stringstream ss;
    ss << left << setw(6) << quantity << setw(6) << year << setw(6) 
            << month << setw(40)<< title;
    string str;
    getline(ss, str);
    return str;
}

//--------------------------------------------------------------------------
// setData (Public)
void Periodical::setData(string str) {

    std::stringstream ss(str);
    
    ss.get(); //remove type
    ss.get(); //remove space
    getline(ss, title, ',');
    
    ss.get(); //remove space
    ss >> month;
    
    if(month.length() == 1){
        month = "0" + month;
    }
    
    ss.get(); //remove space
    ss >> year;
}

//--------------------------------------------------------------------------
// Create Youth Object (Public)
// Used by the ItemFactory to dynamically create Objects based on the
// character 'P'
Recordable* Periodical::create() const {
    Periodical *p = new Periodical();
    return p;
}

//--------------------------------------------------------------------------
// used for outputting a specific format when printing a patron's history
// toStringHistory (Public)
string Periodical::toStringHistory() const{
    stringstream ss;
    ss << left << setw(6) << year << setw(6) 
            << month << setw(40) << title << endl;
    string str;
    getline(ss, str);
    return str;
}

//--------------------------------------------------------------------------
// a special version of setData to handle data that comes in backwards
// setTempData (Public)
void Periodical::setTempData(string str) {
    std::stringstream ss(str);
    
    ss >> year;
    ss.get(); //remove space
    
    ss >> month;
    
    if(month.length() == 1){
        month = "0" + month;
    }
       
    ss.get(); //remove space
    
    getline(ss, title, ',');
}