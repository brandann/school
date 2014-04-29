//person.cpp

#include "person.h"

using namespace std;

//------------------------------------------------------------------------------
// constructor
Person::Person() : Recordable(){
  // do nothing
}

//------------------------------------------------------------------------------
// destructor
Person::~Person(){ //virtual
    // clear history
    int s = history.size();
    for(int i = 0; i < s; i++){
        delete history.back();
        history.pop_back();
    }
}

//------------------------------------------------------------------------------
// getName (Public)
string Person::getName() const {
  return name;
}

//------------------------------------------------------------------------------
// getId (Public)
int Person::getId() const {
  return ID;
}

//------------------------------------------------------------------------------
// setName (public)
void Person::setName(string str){
    name = str;
}

//------------------------------------------------------------------------------
// setID (public)
void Person::setID(int id){
    ID = id;
}

//-----------------------------------------------------------------------------
// toString(Public)
string Person::toString() const { //virtual
    stringstream ss;
    ss << ID;
    string str = ss.str() + " " + name;
    return str;
}

//------------------------------------------------------------------------------
// operator== (Public)
bool Person::operator==(const Recordable& rhs) const{
    
    if (typeid(*this) != typeid(rhs)) {
        return false;
    }
    Recordable *r = const_cast<Recordable*>(&rhs);
    Person *p = dynamic_cast<Person*>(r);
    
    return ID == p->getId();
}
   
//------------------------------------------------------------------------------
// operator> (Public)
bool Person::operator>( const Recordable& rhs) const{
    
    if (typeid(*this) != typeid(rhs)) {
        return false;
    }
    Recordable *r = const_cast<Recordable*>(&rhs);
    Person *p = dynamic_cast<Person*>(r);
    
    return ID > p->getId();
}
   
//------------------------------------------------------------------------------
// operator< (Public)
bool Person::operator<( const Recordable& rhs) const{
    
  if (typeid(*this) != typeid(rhs)) {
    return false;
  }
  Recordable *r = const_cast<Recordable*>(&rhs);
  Person *p = dynamic_cast<Person*>(r);

  return ID < p->getId();
}

//------------------------------------------------------------------------------
// setData (public)
void Person::setData(string str) {
    deque<string> temp;
    string s;
    std::stringstream ss(str);
    
    //set ID
    ss >> ID;
    
    //build deque
    while (!ss.eof()){
        ss >> s;
        temp.push_back(s);
    }
    
    //build name
    while(temp.size() > 0)
    {
        name += temp.front(); temp.pop_front();
        if (temp.size() > 0)
            name += " ";
    }
}

//------------------------------------------------------------------------------
// printHistory (public)
void Person::printHistory() const{
    
    for(int i = 0; i < history.size(); i++){
        Transaction* trans = history.at(i);
        cout << left << setw(10) << trans->toString();
        cout << trans->item->toStringHistory();
        cout<<endl;
    }
}

//------------------------------------------------------------------------------
// checkOut (public)
void Person::checkOut(Transaction *trans){
    
    history.push_back(trans);
    checkout.push_back(trans->item);
    trans->item->quantityMinusMinus();
}

//------------------------------------------------------------------------------
// checkIn (public)
void Person::checkIn(Transaction* trans){
    bool found = false;
    for(int i = 0; i < checkout.size(); i++){
        if (checkout.at(i) == trans->item && found == false){
            checkout.erase(checkout.begin() + i);
            found = true;
        }
    }
    if(!found){
        delete trans;
        cout << "Patron does not have this book checked out."<<endl;
        return;
    }
    history.push_back(trans);
    trans->item->quantityPlusPlus();
}
