%{
#include <iostream>
#include <cstring>
#include <fstream>
extern int yylex();
extern FILE* yyin;
extern void yyerror(const char*);
extern int no_of_lines;
extern char* yytext;
extern char* strdup(const char*);

std::ofstream asmFile;

void generateASM(const std::string& asmCode) {
    asmFile << asmCode << std::endl;
}

%}

%union {
    char* string;
    int integer;
}


%token WHILE FOR IF ELSE INT FLOAT DOUBLE INCLUDE USING NAMESPACE RETURN MAIN COUT CIN ENDL IDENTIFIER OPERATOR CONSTANT SEMICOLM
%token LBRACE RBRACE LPAREN RPAREN COMMA GREATER LESS EQUALS INSERTION EXTRACTION
%token PLUS MINUS MULTIPLY DIVIDE MODULO

%left GREATER LESS EQUALS OPERATOR PLUS MINUS MULTIPLY DIVIDE MODULO

%type <string> IDENTIFIER expression factor term CONSTANT declaration list_identifiers type data_extraction



%start program

%%

program:    header block_main
            ;

header:     block_library block_namespace
            ;

block_library:  library_definition
                | library_definition block_library
                ;

library_definition: INCLUDE LESS IDENTIFIER GREATER
                    ;

block_namespace:    namespace_definition
                    | namespace_definition block_namespace
                    ;

namespace_definition:   USING NAMESPACE IDENTIFIER SEMICOLM {
                            generateASM("SYS_READ   equ     0\nSYS_WRITE  equ     1\nSYS_EXIT   equ     60\nSTDIN      equ     0\nSTDOUT     equ     1\nNEWLINE    db     10\n");
}
                        ;

block_main: INT MAIN LPAREN RPAREN LBRACE block_instructions RETURN SEMICOLM RBRACE {                    
                    generateASM("int 80h\nmov eax,1\nmov ebx,0\nint 80h;");
                }
            ;

block_instructions:  instruction
                    | instruction block_instructions
                    ;

instruction:    declaration SEMICOLM
                | assignment SEMICOLM
                | read SEMICOLM
                | write SEMICOLM
                ;

declaration:    type {generateASM("section .data");} list_identifiers {generateASM("section .text");
                    generateASM("global _start");
                    generateASM("_start:");}
                ;

type:   INT { $$ = strdup(""); }
        | FLOAT { $$ = strdup(""); }
        | DOUBLE { $$ = strdup(""); }
        ;

list_identifiers:   IDENTIFIER {
                        std::string asmCode = $1;
                        asmCode += " dd 0";
                        generateASM(asmCode);
                    }
                    | IDENTIFIER COMMA list_identifiers {
                        std::string asmCode = $1;
                        asmCode += " dd 0";
                        generateASM(asmCode);
                    }
                    ;

assignment: IDENTIFIER EQUALS expression {
                std::string asmCode = "mov ";
                asmCode += "[";
                asmCode += $1;
                asmCode += "], eax";
                generateASM(asmCode);}
            ;

expression:  factor {
                    
                }
            | expression PLUS factor {
                std::string asmCode = "add eax, " ;  
                asmCode += $3;
                generateASM(asmCode);}
            | expression MINUS factor {
                std::string asmCode = "sub eax, " ;  
                asmCode += $3;
                generateASM(asmCode);}
            ;

factor:     term
            | factor MULTIPLY term {
                generateASM("xor edx, edx");
                std::string asmCode = "mov ebx, ";
                asmCode += $3;
                generateASM(asmCode);
                asmCode = "mul ebx" ;  
                generateASM(asmCode);}
            | factor DIVIDE term {
                generateASM("xor edx, edx");
                std::string asmCode = "mov ebx, ";
                asmCode += $3;
                generateASM(asmCode);
                generateASM("div ebx");}
            ;

term:   IDENTIFIER { char* temp = strdup("[");
                    strcat(temp, $1);
                    strcat(temp, "]");
                    $$ = temp;
                    std::string asmCode = "mov eax, ";
                    asmCode += $$;
                    generateASM(asmCode);

        }
        | CONSTANT 
        ;

read:   CIN data_extraction 
        ;

data_extraction:    EXTRACTION IDENTIFIER {
                        std::string asmCode = "mov rdx, 16\nmov rsi, ";
                        asmCode += $2;
                        asmCode += "\nmov rdi, STDIN\nmov rax, SYS_READ\nsyscall";
                        generateASM(asmCode);
                    }
                    | EXTRACTION IDENTIFIER data_extraction {
                        std::string asmCode = "mov rdx, 16\nmov rsi, ";
                        asmCode += $2;
                        asmCode += "\nmov rdi, STDIN\nmov rax, SYS_READ\nsyscall";
                        generateASM(asmCode);
                    }
                    ;

write:  COUT data_insertion
        ;
    
data_insertion: INSERTION IDENTIFIER {
                    std::string asmCode = "mov rdx, 1\nmov rsi, ";
                    asmCode += $2;
                    asmCode += "\nmov rdi, STDOUT\nmov rax, SYS_WRITE\nsyscall";
                    generateASM(asmCode);
                }
                | INSERTION CONSTANT
                | INSERTION IDENTIFIER data_insertion {
                    std::string asmCode = "mov rdx, 1\nmov rsi, ";
                    asmCode += $2;
                    asmCode += "\nmov rdi, STDOUT\nmov rax, SYS_WRITE\nsyscall";
                    generateASM(asmCode);
                }
                | INSERTION CONSTANT data_insertion
                | INSERTION ENDL {
                    std::string asmCode = "mov rdx, 1\nmov rsi, ";
                    asmCode += "NEWLINE";
                    asmCode += "\nmov rdi, STDOUT\nmov rax, SYS_WRITE\nsyscall";
                    generateASM(asmCode);
                }
                ;

%%

void yyerror(const char* s) {
    std::cerr << "Error: " << s << " at line " << no_of_lines << std::endl;
}

int main(int argc, char **argv) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s filename\n", argv[0]);
        return 1;
    }

    // Open a file for writing assembly code
    asmFile.open("file.asm", std::ofstream::out);
    if (!asmFile.is_open()) {
        std::cerr << "Error: Unable to open output file for writing" << std::endl;
        return 1;
    }

    FILE *file = fopen(argv[1], "r");
    if (!file) {
        perror(argv[1]);
        return 1;
    }
    yyin = file;
    yyparse();
    fclose(file);
    // Close the file after parsing
    asmFile.close();
    return 0;
}
