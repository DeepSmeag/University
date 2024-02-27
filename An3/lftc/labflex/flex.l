%option yylineno
%{
// ANSI color escape codes
#define RED_TEXT "\033[1;31m"
#define RESET_COLOR "\033[0m"

#include "test.h"

#include <stdio.h>
#include <cstring>
#include <iostream> 
#include <iomanip>
#include <vector>
#include <tuple>
using namespace std;



extern "C" void yyerror(const char *s, const char* text);  // Declare yyerror as C function

BinarySearchTree idBst;
BinarySearchTree constBst;
BinarySearchTree allBst;


%}

WS      [ \t\n]
INTEGER [1-9]+[0-9]*|0
FLOAT   [1-9]+[0-9]*\.[0-9]*[1-9]|0\.[0-9]*[1-9]



%%
\/\/[^\n]*   { cout<<setw(50)<<"COMMENT "<<setw(50)<<yytext<<endl; }
\#include   { cout<<setw(50)<<"HEADER "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "HEADER");}
\<         { cout<<setw(50)<<"OPENING ANGLE BRACKET "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "OPENING ANGLE BRACKET");}
\>         { cout<<setw(50)<<"CLOSING ANGLE BRACKET "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "CLOSING ANGLE BRACKET");}
"using namespace std;" { cout<<setw(50)<<"NAMESPACE"<<setw(50)<<yytext<<endl; allBst.insert(yytext, "NAMESPACE"); }
iostream|cmath { cout<<setw(50)<<"LIBRARY "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "LIBRARY"); }
int|float|double|char|void { cout<<setw(50)<<"DATATYPE "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "DATATYPE"); }
main        { cout<<setw(50)<<"MAIN "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "MAIN"); }
\(         { cout<<setw(50)<<"OPENING PARANTHESES "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "OPENING PARANTHESES");}
\)         { cout<<setw(50)<<"CLOSING PARANTHESES "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "CLOSING PARANTHESES");}
if          { cout<<setw(50)<<"IF "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "IF"); }
then { cout<<setw(50)<<"THEN "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "THEN"); }
endif      { cout<<setw(50)<<"ENDIF "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "ENDIF"); }
else        { cout<<setw(50)<<"ELSE "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "ELSE"); }
for         { cout<<setw(50)<<"FOR "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "FOR"); }
while       { cout<<setw(50)<<"WHILE "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "WHILE"); }
do          { cout<<setw(50)<<"DO "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "DO"); }
return      { cout<<setw(50)<<"RETURN "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "RETURN"); }
{INTEGER}|{FLOAT}      { cout<<setw(50)<<"NUMBER "<<setw(50)<<yytext<<endl; constBst.insert(yytext, "NUMBER"); allBst.insert(yytext, "CONST"); }
\{          { cout<<setw(50)<<"OPENING BRACE "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "OPENING BRACE"); }
\}          { cout<<setw(50)<<"CLOSING BRACE "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "CLOSING BRACE"); }
\;          { cout<<setw(50)<<"SEMICOLON "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "SEMICOLON"); }
\,          { cout<<setw(50)<<"COMMA "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "COMMA"); }
\=          { cout<<setw(50)<<"EQUALS "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "EQUALS"); }
\+          { cout<<setw(50)<<"PLUS "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "PLUS"); }
\-          { cout<<setw(50)<<"MINUS "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "MINUS"); }
\*          { cout<<setw(50)<<"MULTIPLY "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "MULTIPLY"); }
\/          { cout<<setw(50)<<"DIVIDE "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "DIVIDE"); }
\%          { cout<<setw(50)<<"MODULUS "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "MODULUS"); }
{WS}        {}
cin        { cout<<setw(50)<<"INPUT "<<setw(50)<<yytext<<endl; allBst.insert(yytext, "INPUT"); }
[a-zA-Z]{1}[a-zA-Z0-9]{8,} {yyerror("IDENTIFIER too long", yytext);}
[a-zA-Z]{1}[a-zA-Z0-9]{0,7} { cout<<setw(50)<<"IDENTIFIER "<<setw(50)<<yytext<<endl; idBst.insert(yytext, "IDENTIFIER"); allBst.insert(yytext, "IDENTIFIER");}



%%

int yywrap(){return 1;}
int main(int argc, char **argv) {
    FILE *fp;
    
    fp = fopen(argv[1],"r");
    yyin = fp;
    

    yylex();

    cout<<endl<<endl<<"--------------------------------------------------------------------"<<endl<<endl;
    // category, value, idSymbol, '---' if not const or id, const or id value if const or id
    vector<tuple<string, string, int, string, int>> FIP;
    vector<tuple<string, string, int, string, int>> dummy;
    allBst.inorderTraversal(FIP);
    idBst.inorderTraversal(dummy);
    constBst.inorderTraversal(dummy);
    for (auto i : FIP) {
        // if category is const, search in the const bst
        if (get<0>(i) == "CONST") {
            if (constBst.search(get<1>(i))) {
                get<3>(i) = "CONST";
                get<4>(i) = constBst.search(get<1>(i))->getId();
            }
        }
        // if category is id, search in the id bst
        else if (get<0>(i) == "IDENTIFIER") {
            if (idBst.search(get<1>(i))) {
                get<3>(i) = "IDENTIFIER";
                get<4>(i) = idBst.search(get<1>(i))->getId();
            }
        }
        cout<<setw(30)<<get<0>(i)<<setw(30)<<get<1>(i)<<setw(20)<<get<2>(i)<<setw(20)<<get<3>(i)<<setw(20);
        if(get<4>(i) == -1) cout<<"---"<<endl;
        else cout<<get<4>(i)<<endl;
    }

    return 0;
}

void yyerror(const char *s, const char *text) {
    std::cerr<< setw(50) << RED_TEXT << "Error: " << s << " - " << text << " AT LINE " << yylineno << RESET_COLOR << std::endl;
}