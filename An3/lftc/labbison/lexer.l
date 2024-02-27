%option yylineno
%{
#include "parser.tab.h"
%}



%%
[ \t\n]               ;/* Ignore whitespace */
[0-9]                { return DIGIT; }
"int"|"void"|"char"|"string"|"float"          { return TYPE; }
"main"                { return MAIN; }
"return"              { return RETURN; }
"include"            { return INCLUDE; }
"stdio.h"|"math.h"|"iostream" {return LIBRARY;}
"using namespace std;" {return NAMESPACE;}
\/\/[^\n]*            ;/* Ignore comments */
[0-9]+                { return NUMBER; }
"printf"              { return PRINTF; }
"cin"                 {return CIN;}
"cout"                {return COUT;}
"<<"                  {return LSHIFT;}
">>"                  {return RSHIFT;}
"endl"                {return ENDL;}
"for"                 {return FOR;}
"\""[^"]*"\""         { return STRING; }
\(                    { return LPAREN; }
\)                    { return RPAREN; }
\{                    { return LBRACE; }
\}                    { return RBRACE; }
\+                    { return PLUS; }
\-                    { return MINUS; }
\*                    { return TIMES; }
\/                    { return DIVIDE; }
\=                    { return EQUALS; }
\;                    { return SEMICOLON; }
":"                    { return COLON; }
\,                   { return COMMA; }
\.                  { return DOT; }
[a-zA-Z_][a-zA-Z0-9_]* { return IDENTIFIER; }
\#               { return HASHTAG; }


\<                   { return LANGLE; }
\>                   { return RANGLE; }

.                     { fprintf(stderr, "Unexpected character: %s\n", yytext); }

%%

int yywrap(void) {
    return 1;
}
