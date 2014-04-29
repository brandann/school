/* 
 * File:   errorcommand.cpp
 * Author: brandan
 * 
 * Created on March 8, 2014, 7:25 AM
 */

#include "errorcommand.h"

using namespace std;

//--------------------------------------------------------------------------
ErrorCommand::ErrorCommand() {
    //do nothing
}

//--------------------------------------------------------------------------
ErrorCommand::~ErrorCommand() {
    //do nothing
}

//--------------------------------------------------------------------------
void ErrorCommand::takeCareofBusiness(Library &lib, string str){
    cout << "Command: " << str[0] << " not available" << endl;
    return;
}

//--------------------------------------------------------------------------
Recordable* ErrorCommand::create() const{
    Recordable *r = NULL;
    cout << "Tried creating item or person that does not exist" << endl;
    return r;
}

//--------------------------------------------------------------------------
// does not use any methods below this line
bool ErrorCommand::operator==(const Recordable&)const{
    //do nothing
    return false;
}

bool ErrorCommand::operator<(const Recordable&)const{
    //do nothing
    return false;
}

bool ErrorCommand::operator>(const Recordable&)const{
    //do nothing
    return false;
}

string ErrorCommand::toString() const{
    //do nothing
    return "";
}

void ErrorCommand::setData(string){
    //do nothing
}



char ErrorCommand::getType() const{
    //do nothing
    return '\0';
}