%{
#include <iostream>
#include "parser.tab.h"
int no_of_lines = 1;
%}

%option noyywrap

%%
"while" { return WHILE; }
"for" { return FOR; }
"if" { return IF; }
"else" { return ELSE; }
"int" { return INT; }
"float" { return FLOAT; }
"double" { return DOUBLE; }
"#include" { return INCLUDE; }
"using" { return USING; }
"namespace" { return NAMESPACE; }
"return 0" { return RETURN; }
"main" { return MAIN; }
"cout" { return COUT; }
"cin" { return CIN; }
"endl" {return ENDL; }
";" {return SEMICOLM; }
"{" {return LBRACE; }
"}" {return RBRACE; }
"(" {return LPAREN; }
")" {return RPAREN; }
"," {return COMMA; }
"<" {return LESS; }
">" {return GREATER; }
"<<" {return INSERTION; }
">>" {return EXTRACTION; }
"=" {return EQUALS; }
"+" {return PLUS; }
"-" {return MINUS; }
"*" {return MULTIPLY; }
"/" {return DIVIDE; }
"%" {return MODULO; }
[a-zA-Z_][a-zA-Z0-9_]{0,7}                                                                  { yylval.string = strdup(yytext); return IDENTIFIER; }
[a-zA-Z_][a-zA-Z0-9_]{8,}                                                                   { fprintf(stderr, "Error: identifier %s on line %d is longer than 8 characters\n", yytext, no_of_lines); exit(1); }
[0-9]+(\.[0-9]+)?                                                                           { yylval.string = strdup(yytext); return CONSTANT; }
"+"|"-"|"*"|"/"|"="|"=="|"!="|"<"|"<="|">"|">="|"%"                                         { return OPERATOR;  }
[\n]                                                                                        { no_of_lines++; }
[ \t\n]                                                                                     { /* ignore whitespace */ }
.                                                                                           { fprintf(stderr, "Error: identifier %s on line %d is not recognised\n", yytext, no_of_lines); exit(1); }
%%