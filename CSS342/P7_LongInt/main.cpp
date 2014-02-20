#include <iostream>
#include "longint.h"

using namespace std;

int main( ) {

        string s1 = "1";
        string s2 = "6";
        string s3 = "3";
        string s4 = "-4";
        string s5 = "-5";
        string s6 = "6";
        string s7 = "-7";
        string s8 = "-7";
        string s9 = "344";
        string s10 = "344";

        LongInt	longint1(s1);
        LongInt	longint2(s2);
        LongInt	longint3(s3);
        LongInt	longint4(s4);
        LongInt	longint5(s5);
        LongInt	longint6(s6);
        LongInt	longint7(s7);
        LongInt	longint8(s8);
        LongInt	longint9(s9);
        LongInt	longint10(s10);
        LongInt longint;
        bool longintBOOL;

        longint = longint1 + longint2;
        cout << s1 << " + " << s2 << "   = " << longint << "\t== 7" << endl;

        longint = longint3 + longint4;
        cout << s3 << " + " << s4 << "  = " << longint << "\t== -1" << endl;

        longint = longint5 + longint6;
        cout << s5 << " + " << s6 << "  = " << longint << "\t== 1" << endl;

        longint = longint7 + longint8;
        cout << s7 << " + " << s8 << " = " << longint << "\t== -14" << endl;
/*
        longint = longint9 + longint10;
        cout << s9 << " + " << s10 << " = " << longint << endl;

        ////////////////////////////////////////////////////////
*/
        longint = longint1 - longint2;
        cout << s1 << " - " << s2 << "   = " << longint << "\t== -5" << endl;

        longint = longint3 - longint4;
        cout << s3 << " - " << s4 << "  = " << longint << "\t== 7" << endl;

        longint = longint5 - longint6;
        cout << s5 << " - " << s6 << "  = " << longint << "\t== -11" << endl;

        longint = longint7 - longint8;
        cout << s7 << " - " << s8 << " = " << longint << "\t== 0" << endl;
/*
        longint = longint9 - longint10;
        cout << s9 << " - " << s10 << " = " << longint << endl;

        ////////////////////////////////////////////////////////

        longintBOOL = longint1 == longint2;
        cout << s1 << " == " << s2 << " = " << longintBOOL << endl;

        longintBOOL = longint3 == longint4;
        cout << s3 << " == " << s4 << " = " << longintBOOL << endl;

        longintBOOL = longint5 == longint6;
        cout << s5 << " == " << s6 << " = " << longintBOOL << endl;

        longintBOOL = longint7 == longint8;
        cout << s7 << " == " << s8 << " = " << longintBOOL << endl;

        longintBOOL = longint9 == longint10;
        cout << s9 << " == " << s10 << " = " << longintBOOL << endl;

        ////////////////////////////////////////////////////////

        longintBOOL = longint1 != longint2;
        cout << s1 << " != " << s2 << " = " << longintBOOL << endl;

        longintBOOL = longint3 != longint4;
        cout << s3 << " != " << s4 << " = " << longintBOOL << endl;

        longintBOOL = longint5 != longint6;
        cout << s5 << " != " << s6 << " = " << longintBOOL << endl;

        longintBOOL = longint7 != longint8;
        cout << s7 << " != " << s8 << " = " << longintBOOL << endl;

        longintBOOL = longint9 != longint10;
        cout << s9 << " != " << s10 << " = " << longintBOOL << endl;

        ////////////////////////////////////////////////////////

        longintBOOL = longint1 <= longint2;
        cout << s1 << " <= " << s2 << " = " << longintBOOL << endl;

        longintBOOL = longint3 <= longint4;
        cout << s3 << " <= " << s4 << " = " << longintBOOL << endl;

        longintBOOL = longint5 <= longint6;
        cout << s5 << " <= " << s6 << " = " << longintBOOL << endl;

        longintBOOL = longint7 <= longint8;
        cout << s7 << " <= " << s8 << " = " << longintBOOL << endl;

        longintBOOL = longint9 <= longint10;
        cout << s9 << " <= " << s10 << " = " << longintBOOL << endl;

        ////////////////////////////////////////////////////////

        longintBOOL = longint1 >= longint2;
        cout << s1 << " >= " << s2 << " = " << longintBOOL << endl;

        longintBOOL = longint3 >= longint4;
        cout << s3 << " >= " << s4 << " = " << longintBOOL << endl;

        longintBOOL = longint5 >= longint6;
        cout << s5 << " >= " << s6 << " = " << longintBOOL << endl;

        longintBOOL = longint7 >= longint8;
        cout << s7 << " >= " << s8 << " = " << longintBOOL << endl;

        longintBOOL = longint9 >= longint10;
        cout << s9 << " >= " << s10 << " = " << longintBOOL << endl;

        ////////////////////////////////////////////////////////

        longintBOOL = longint1 > longint2;
        cout << s1 << " > " << s2 << " = " << longintBOOL << endl;

        longintBOOL = longint3 > longint4;
        cout << s3 << " > " << s4 << " = " << longintBOOL << endl;

        longintBOOL = longint5 > longint6;
        cout << s5 << " > " << s6 << " = " << longintBOOL << endl;

        longintBOOL = longint7 > longint8;
        cout << s7 << " > " << s8 << " = " << longintBOOL << endl;

        longintBOOL = longint9 > longint10;
        cout << s9 << " > " << s10 << " = " << longintBOOL << endl;

        ////////////////////////////////////////////////////////

        longintBOOL = longint1 < longint2;
        cout << s1 << " < " << s2 << " = " << longintBOOL << endl;

        longintBOOL = longint3 < longint4;
        cout << s3 << " < " << s4 << " = " << longintBOOL << endl;

        longintBOOL = longint5 < longint6;
        cout << s5 << " < " << s6 << " = " << longintBOOL << endl;

        longintBOOL = longint7 < longint8;
        cout << s7 << " < " << s8 << " = " << longintBOOL << endl;

        longintBOOL = longint9 < longint10;
        cout << s9 << " < " << s10 << " = " << longintBOOL << endl;
*/
}
