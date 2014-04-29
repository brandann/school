// main.cpp

#include <iostream>
#include "system.h"
#include <fstream>

using namespace std;

int main()
{
    System s;
    
    ifstream books_infile("data4books.txt");
    s.addFile(books_infile);
    
    ifstream patrons_infile("data4patrons.txt");
    s.addFile(patrons_infile);
    
    ifstream commands_infile("data4commands.txt");
    s.addFile(commands_infile);
}
