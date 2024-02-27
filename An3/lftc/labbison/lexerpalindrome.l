%option yylineno
%{
#include "parserpalindrome.tab.h"
%}


%{
#include "parserpalindrome.tab.h"
%}

DIGIT [0-9]
COLON :

%%
{DIGIT}     { yylval = atoi(yytext); return DIGIT; }
{COLON}     { yylval = atoi(yytext); return COLON; }
%%

int yywrap(void) {
    return 1;
}
