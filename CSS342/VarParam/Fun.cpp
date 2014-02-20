/* 
 * File:   Fun.cpp
 * Author: brandan
 * 
 * Created on October 2, 2013, 3:06 PM
 */

#include "Fun.h"
#include <cstdarg>
#include <cstdio>
#include <iostream>

using namespace std;

Fun::Fun() {
}

int Fun::sum(int num, ...)
{
    va_list arguments;
    int i = 0;
    int &pt = *arguments
    cout << pt << endl;
    
    va_start(arguments, num);
    for(int x = 0; x < num; x++)
        i += va_arg(arguments, int);
    return i;
    
}


Fun::Fun(const Fun& orig) {
}

Fun::~Fun() {
}

