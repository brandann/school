//printcommand.cpp

#include "printcommand.h"

using namespace std;

//------------------------------------------------------------------------------
// constructor
PrintCommand::PrintCommand() {
}

//------------------------------------------------------------------------------
// destructor
PrintCommand::~PrintCommand() {
}

//------------------------------------------------------------------------------
// takeCareofBusiness (public)
void PrintCommand::takeCareofBusiness(Library& lib, string str){
    // print fiction, periodical, youth
    
    cout << "Fiction:" << endl;
    cout << left << setw(6) << "AVAIL" << setw(20) << "AUTHOR" << setw(40) 
            << "TITLE" << setw(4) << "YEAR" << endl;
    lib.print('F');
    cout << "\n" << endl;
    
    cout << "Periodicals:" << endl;
    cout << left << setw(6) << "AVAIL" << setw(6) << "YEAR" << setw(6) 
            << "MONTH" << setw(40) << "TITLE" << endl;
    lib.print('P');
    cout << "\n" << endl;
    
    cout << "Youth:" << endl;
    cout << left << setw(6) << "AVAIL" << setw(40) << "TITLE" << setw(30) 
            << "AUTHOR" << setw(4) << "YEAR" << endl;
    lib.print('Y');
}
