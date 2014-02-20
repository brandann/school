#include "longint.h"

////////////////////////// STREAM OPERATORS //////////////////////////
istream& operator>>(istream &in, LongInt &rhs){
    string str;
    in >> str;
    LongInt temp(str);
    rhs = temp;
    return in;
}

ostream& operator<<(ostream &os, LongInt &rhs){
    string temp;
    string str;

    // create string of deque
    for(int i = 0; i < rhs.digits.size(); i++)
    {
        int c = rhs.digits.removeFront() - '0';
        temp = temp + string(1, (char)(((int)'0')+c) );
        rhs.digits.addBack((char)(((int)'0')+c));
    }

    //check for 0 and negatives
    if(temp.length() == 0)
    {
        os << "0";
        return os;
    }
    else if(temp.length() == 1)
    {
        if(temp[0] == '0')
        {
            os << "0";
            return os;
        }
        else if(rhs.negative)
        {
            os << "-";
            os << temp;
            return os;
        }
        {
            os << temp;
            return os;
        }
    }
    else
    {
        if(rhs.negative)
        {
            os << "-";
            os << temp;
            return os;
        }
        else
        {
            os << temp;
            return os;
        }
    }
    os << temp;
    return os;
}

////////////////////////// CONSTRUCTOR //////////////////////////
LongInt::LongInt( const LongInt & rhs ) {
    //make this objects equal to rhs objects
    digits = rhs.digits;
    negative = rhs.negative;
}

LongInt::LongInt(const string str){

    digits.clear();
    int i = 0;
    if(str[0] == '-')
    {
        negative = true;
        i++; //step past str[0]
    }
    else
    {
        negative = false;
    }

    for(i; i < str.length(); i++){
        char c = str[i];
        int ci = c - '0';
        if (ci > -1 && ci < 10) //make sure ci is 0 < ci < 9
        {
            digits.addBack(str[i]);
        }
    }
}

LongInt::LongInt() {
    //no initilizer is zero
    LongInt temp("0");
    digits = temp.digits;
    negative = temp.negative;
}

////////////////////////// DECONSTRUCTOR //////////////////////////
LongInt::~LongInt() {
    //destroy deque
    while(!digits.isEmpty())
        digits.removeFront();
}

////////////////////////// MATH OPERATORS //////////////////////////
LongInt LongInt::operator+(const LongInt &rhs){
    //ADD OPERATORS
    //  A  +   B  =   A  + B  (ADD)
    //  A  + (-B) =   A  - B  (SUB)
    //(-A) +   B  =   B  - A  (SUB)
    //(-A) + (-B) = (-A) - B  (SUB)

    LongInt temp;
    string str = "";

    if(negative && rhs.negative) //(-A) + (-B) = (-A) - B  (SUB)
    {
        LongInt lhs;
        LongInt newrhs;

        //make LHS
        lhs.negative = true;
        lhs.digits = digits;

        //make RHS
        newrhs.negative = false;
        newrhs.digits = rhs.digits;

        temp = lhs - newrhs;
        return temp;
    }
    else if (!negative && rhs.negative) //  A  + (-B) =   A  - B  (SUB)
    {
        LongInt lhs;
        LongInt newrhs;

        //make LHS
        lhs.negative = false;
        lhs.digits = digits;

        //make RHS
        newrhs.negative = false;
        newrhs.digits = rhs.digits;

        temp = lhs - newrhs;
        return temp;
    }
    else if (negative && !rhs.negative) //(-A) +   B  =   B  - A  (SUB)
    {
        LongInt lhs;
        LongInt newrhs;

        //make LHS
        lhs.negative = false;
        lhs.digits = digits;

        //make RHS
        newrhs.negative = false;
        newrhs.digits = rhs.digits;

        temp = newrhs - lhs;
        return temp;
    }

    Deque<char> temprhs = rhs.digits;
    Deque<char> tempDigits = digits;

    //make temp make tempDigits length
    for(int i = temprhs.size(); i < tempDigits.size(); i++)
        temprhs.addFront('0');
    //make tempDigits match temp length
    for(int i = tempDigits.size(); i < temprhs.size(); i++)
        tempDigits.addFront('0');

    int carry = 0;
    for(int i = 0; i < tempDigits.size(); i++)
    {
        //get int values
        int i1 = tempDigits.removeBack() - '0';
        int i2 = temprhs.removeBack() - '0';

        int sum = i1 + i2 + carry;
        carry = 0;
        if(sum>=10) // deal with carrying values
        {
            carry = 1;
            sum -= 10;
        }
        str = string(1, (char)(((int)'0')+sum) ) + str;
        tempDigits.addFront((char)(((int)'0')+i1));
        temprhs.addFront((char)(((int)'0')+i2));
    }
    //if loop is over and carry has a value, add to string
    if(carry > 0)
        str = "1" + str;
    //make string into a deuqe and set to temp
    Deque<char> returnDeque;
    for (int i = 0; i < str.length(); i++)
        returnDeque.addBack(str[i]);
    temp.digits = returnDeque;
    temp.negative = false;
    return temp;
}

LongInt LongInt::operator-(const LongInt &rhs){
    //SUB OPERATORS
    //  A  -   B  = SUB  -> A - B
    //  A  - (-B) = ADD  -> A + B
    //(-A) -   B  = SUB  -> (-A) - B
    //(-A) - (-B) = SUB  -> B - A
    LongInt temp;
    Deque<char> temprhs;
    Deque<char> templhs;

    if(!negative && rhs.negative) //  A  - (-B) = ADD  -> A + B
    {
        LongInt lhs;
        LongInt newrhs;

        //make LHS
        lhs.negative = false;
        lhs.digits = digits;

        //make RHS
        newrhs.negative = false;
        newrhs.digits = rhs.digits;

        temp = lhs + newrhs;
        return temp;
    }
    else if (negative && !rhs.negative) //(-A) -   B  = SUB  -> -( A + B )
    {
        LongInt lhs;
        LongInt newrhs;

        //make LHS
        lhs.negative = false;
        lhs.digits = digits;

        //make RHS
        newrhs.negative = false;
        newrhs.digits = rhs.digits;

        temp = lhs + newrhs;
        temp.negative = true;
        return temp;
    }
    else if (negative && rhs.negative) //(-A) - (-B) = SUB  -> B - A
    {
        LongInt lhs;
        LongInt newrhs;

        //make LHS
        lhs.negative = false;
        lhs.digits = digits;

        //make RHS
        newrhs.negative = false;
        newrhs.digits = rhs.digits;

        if (lhs > rhs)
        {
            temp = lhs - newrhs;
            temp.negative = true;
            return temp;
        }
        else if(lhs < rhs)
        {
            temp = newrhs - lhs;
            temp.negative = false;
            return temp;
        }
        else // zeros out
        {
            Deque<char> returnDeque;
            returnDeque.addBack('0');
            temp.digits = returnDeque;
            temp.negative = false;
            return temp;
        }
    }
    else if (!negative && !rhs.negative) //  A  -   B  = SUB  -> A - B
    {
        LongInt lhs;
        LongInt newrhs;

        //make LHS
        lhs.negative = false;
        lhs.digits = digits;

        //make RHS
        newrhs.negative = false;
        newrhs.digits = rhs.digits;

        if (lhs > rhs)
        {
            templhs = lhs.digits;
            temprhs = rhs.digits;
            temp.negative = false;
        }
        else if(lhs < rhs)
        {
            templhs = rhs.digits;
            temprhs = lhs.digits;
            temp.negative = true;
        }
        else // zeros out
        {
            Deque<char> returnDeque;
            returnDeque.addBack('0');
            temp.digits = returnDeque;
            temp.negative = false;
            return temp;
        }
    }

    string str = "";

    //make temp make templhs length
    for(int i = temprhs.size(); i < templhs.size(); i++)
        temprhs.addFront('0');
    //make templhs match temp length
    for(int i = templhs.size(); i < temprhs.size(); i++)
        templhs.addFront('0');

    int borrow = 0;
    for(int i = 0; i < templhs.size(); i++)
    {
        //pop digit
        int i1 = templhs.removeBack() - '0';
        int i2 = temprhs.removeBack() - '0';
        templhs.addFront((char)(((int)'0')+i1));
        temprhs.addFront((char)(((int)'0')+i2));

        int sum = i1 - i2 - borrow;
        borrow = 0;

        //manage borrowing
        if (sum < 0)
        {
            sum = sum + 10;
            borrow = 1;
        }
        str = string(1, (char)(((int)'0')+sum) ) + str;
    }
    //make string into deque and return temp
    Deque<char> returnDeque;
    for (int i = 0; i < str.length(); i++)
        returnDeque.addBack(str[i]);
    temp.digits = returnDeque;
    return temp;
}

////////////////////////// ASSIGNMENT OPERATORS //////////////////////////
const LongInt& LongInt::operator=( const LongInt &rhs ){
    //make this objects equal to rhs objects
    digits = rhs.digits;
    negative = rhs.negative;
    return *this;
}

bool LongInt::operator< ( const LongInt & rhs ) const{
    LongInt temp;
    temp.digits = digits;
    temp.negative = negative;

    if (temp == rhs)
        return false;
    if (negative && !rhs.negative) // neg < pos = true
        return true;
    else if (!negative && rhs.negative) // pos < neg = false
        return false;
    else  //both same sign
    {
        bool returnstate = false;
        if(digits.size() != rhs.digits.size())
        {
            if (digits.size() < rhs.digits.size())
                returnstate = true;
            else if (digits.size() > rhs.digits.size())
                returnstate = false;

            if (!negative && !rhs.negative) //both pos
                return returnstate;
            else //(negative && rhs.negative) // both neg
                return (!returnstate);
        }
        else
        {
            Deque<char> temp_lhs = digits;
            Deque<char> temp_rhs = rhs.digits;
            for (int i = 0; i < temp_lhs.size(); i++)
            {
                char c1 = temp_lhs.removeFront();
                char c2 = temp_rhs.removeFront();
                temp_lhs.addBack(c1);
                temp_rhs.addBack(c2);
                if (c1 < c2)
                {
                    if (!negative && !rhs.negative) //both pos
                        if(c1 < c2)
                            returnstate = true;
                    else //(negative && rhs.negative) // both neg
                        if(c1 > c2)
                            returnstate = false;
                }
            }
        }
        return (returnstate);
    }
}

////////////////////////// LOGICAL OPERATORS //////////////////////////
bool LongInt::operator<=( const LongInt & rhs ) const{
    LongInt temp;
    temp.digits = digits;
    temp.negative = negative;

    if (temp < rhs)
        return true;
    if (temp == rhs)
        return true;
    else
        return false;
}

bool LongInt::operator> ( const LongInt & rhs ) const{
    LongInt temp;
    temp.digits = digits;
    temp.negative = negative;

    if (temp < rhs)
        return false;
    if (temp == rhs)
        return false;
    else
        return true;
}

bool LongInt::operator>=( const LongInt & rhs ) const{
    LongInt temp;
    temp.digits = digits;
    temp.negative = negative;

    if (temp > rhs)
        return true;
    if (temp == rhs)
        return true;
    else
        return false;
}

bool LongInt::operator==( const LongInt & rhs ) const{
    if(!negative && rhs.negative || negative && !rhs.negative) //if not same sign, not ==
    {
        return false;
    }
    else  //both same sign
    {
        bool returnstate = true;
        if(digits.size() != rhs.digits.size())
        {
            return false;
        }
        else
        {
            Deque<char> temp_lhs = digits;
            Deque<char> temp_rhs = rhs.digits;
            for (int i = 0; i < temp_lhs.size(); i++)
            {
                char c1 = temp_lhs.removeFront();
                char c2 = temp_rhs.removeFront();
                temp_lhs.addBack(c1);
                temp_rhs.addBack(c2);

                if (c1 != c2)
                    returnstate = false;
            }
            return returnstate;
        }
    }
}

bool LongInt::operator!=( const LongInt & rhs ) const{

    LongInt temp;
    temp.digits = digits;
    temp.negative = negative;

    return !(temp == rhs);
}
