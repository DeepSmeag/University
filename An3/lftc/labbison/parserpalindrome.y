%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

extern int yylineno;
int yylex(void);
void yyerror(const char *s);

void process_palindrome(int digit1, int digit2, int digit3, int digit4, char colon);


%}

%define parse.error verbose
%locations


%token DIGIT COLON


%%

palindrome: DIGIT DIGIT COLON DIGIT DIGIT {
    process_palindrome($1, $2, $4, $5, $3);
};

%%

void yyerror(const char *s) {
    fprintf(stderr, "Error: %s --- at %d\n", s, yylineno);
    exit(1);
}

void process_palindrome(int digit1, int digit2, int digit3, int digit4, char colon) {
    
    if(digit1 == digit4 && digit2 == digit3 && colon == ':') {
        printf("Palindrome\n");
    } else {
        printf("Not a palindrome\n");
    }
}

int main(void) {
    yyparse();
    return 0;
}