/* 
 * File:   ErrorCommand.h
 * Author: brandan
 *
 * Created on March 8, 2014, 7:25 AM
 */

#ifndef ERRORCOMMAND_H
#define	ERRORCOMMAND_H
#include "command.h"
#include <string>
#include "recordable.h"

using namespace std;
// ErrorCommand class: inherits from command and recordable

//   - purpose it to make sure that bad data given in the input file does not
//          hash to a location that does not have anything in it
//          the error commands take the bad data, does nothing with it, and
//          prints an error
//   - error command is put in all locaitons in a hash the has a blank spot
//   - error command inherits from abstract classes so it is forced to have
//          methods headers that it does not use
class ErrorCommand : public Command, public Recordable{
public:
    
    ErrorCommand(); //constructor
    virtual ~ErrorCommand();// destructor
    
    // error command inherits from abstract classes so it is forced to have
    // methods headers that it does not use
    
    //prints an error when command hash is passed bad data
    virtual void takeCareofBusiness(Library&, string);
    
    virtual bool operator==(const Recordable&)const;
    virtual bool operator<(const Recordable&)const;
    virtual bool operator>(const Recordable&)const;
    virtual string toString() const;
    virtual void setData(string);
    
    //prints an error when item factory is passed bad data
    virtual Recordable* create() const;
    virtual char getType() const;
    
private:

};

#endif	/* ERRORCOMMAND_H */

