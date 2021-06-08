///////////////////////////////////
//
// A simple SLANG script
//
// Sample #1
//

NUMERIC a ;
a = -1 ;
a = -a;
PRINTLINE a;

NUMERIC a;
STRING b;
a = ---1;
PRINTLINE a*4 + 10;

NUMERIC a; // Declare a Numeric variable
a = 2*3+5* 30 + -(4*5+3); // Assign
PRINTLINE a; // Dump a
//----- String concatenation
PRINT "Hello " + "World";
//-------------- Write a new line
PRINTLINE "";
//-------------- string data type
STRING c;
c = "Hello "; // assignment to string
//---------------- assignment and concatenation
c = c + "World";
PRINTLINE c;
//-------------- boolean variable
BOOLEAN d;
d= TRUE;
PRINTLINE d;

d= FALSE;
PRINTLINE d;