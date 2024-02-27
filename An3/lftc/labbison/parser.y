%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "parser.tab.h"
extern int yylineno;
int yylex(void);
void yyerror(const char *s);

int main(int argc, char **argv) {
    yyparse();
    return 0;
}

%}

%define parse.error verbose
%locations  // Enable line number tracking

%token HASHTAG INCLUDE LANGLE RANGLE LIBRARY NAMESPACE
%token NUMBER DIGIT
%token IDENTIFIER
%token TYPE MAIN RETURN PRINTF STRING 
%token COLON
%token LPAREN RPAREN LBRACE RBRACE PLUS SEMICOLON COMMA DOT EQUALS MINUS DIVIDE TIMES
%token CIN COUT LSHIFT RSHIFT ENDL FOR

%type palindrome program_declaration main_declaration statement 
%type expression assignment

%%

palindrome: DIGIT DIGIT COLON DIGIT DIGIT { if (is_palindrome($1)) $$ = $1; else $$ = NULL; }
    ;

is_palindrome: /* Function to check if a string is a palindrome */
    {
        int len = strlen($1);
        for (int i = 0; i < len / 2; i++) {
            if ($1[i] != $1[len - 1 - i]) {
                $$ = 0; /* Not a palindrome */
                return;
            }
        }
        $$ = 1; /* Palindrome */
    }
    ;


%%

void yyerror(const char *s) {
    fprintf(stderr, "Error: %s --- at %d\n", s, yylineno);
    exit(1);
}
