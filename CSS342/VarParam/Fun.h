/* 
 * File:   Fun.h
 * Author: brandan
 *
 * Created on October 2, 2013, 3:06 PM
 */
#include <cstdarg>

#ifndef FUN_H
#define	FUN_H

class Fun {
public:
    Fun();
    int sum(int n, ...);
    Fun(const Fun& orig);
    virtual ~Fun();
private:

};

#endif	/* FUN_H */

