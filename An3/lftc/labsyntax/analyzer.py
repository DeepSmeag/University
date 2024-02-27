# -----------------------------------------------------------------------------
# example.py
#
# Example of using PLY To parse the following simple grammar.
#
#   expression : term PLUS term
#              | term MINUS term
#              | term
#
#   term       : factor TIMES factor
#              | factor DIVIDE factor
#              | factor
#
#   factor     : NUMBER
#              | NAME
#              | PLUS factor
#              | MINUS factor
#              | LPAREN expression RPAREN
#
# -----------------------------------------------------------------------------

from ply.lex import lex
from ply.yacc import yacc

# --- The EBNF syntax to follow
"""
program = whitespace
					[includes]
					whitespace
					 "using namespace std;"
					whitespace
					{[declaration]}
					whitespace
					 "int main()" whitespace
							"{" 
							[list_instructions] 
							"return" whitespace (integer | "0") whitespace ";" whitespace
							"}";
(*General usage*)
whitespace = {" " | "\t" | "\n" | "\r"};
comments = {whitespace "//" [" "] any_character "\n" whitespace};
any_character = {[letter | digit | character]};
includes = ("#include " "<" library ">" "\n")+;
library = "iostream" | "cmath";
list_instructions = whitespace {instruction} whitespace;
instruction = declaration | attribution | input | output | if_statement | loopfor | loopwhile | comments;

(*Defining names / types and attributing values*)
declaration = whitespace type ["const "] identifier [" = " {expression}] { ", " identifier [" = " expression]}  ";";
type = "int " | "float " | "char " | "double " | "custom ";
identifier = letter { letter | digit };
letter = "A" | "B" | "C" | "D" | "E" | "F" | "G"
       | "H" | "I" | "J" | "K" | "L" | "M" | "N"
       | "O" | "P" | "Q" | "R" | "S" | "T" | "U"
       | "V" | "W" | "X" | "Y" | "Z" | "a" | "b"
       | "c" | "d" | "e" | "f" | "g" | "h" | "i"
       | "j" | "k" | "l" | "m" | "n" | "o" | "p"
       | "q" | "r" | "s" | "t" | "u" | "v" | "w"
       | "x" | "y" | "z";
attribution = identifier " = " (constant | expression) ";";
constant = integer | decimal | character | "0";
integer = ["+"|"-"] digit_nonzero {digit};
digit_nonzero = "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9";
digit = "0" | digit_nonzero;
decimal = integer ["." digit+];
character = letter | "_" | "?" | "!" | " " | "=" | "" | "+" | "-" | "*" | "/" | "^";

(*I/O operations*)
input = whitespace "cin" (" >> " identifier)+ ";";
output = whitespace "cout" (" << " ('"'stringliteral'"' | expression | "endl"))+ ";";
stringliteral = {character | digit};
expression = whitespace (constant | identifier | arithmetic_expression | logical_expression) whitespace;
arithmetic_expression = whitespace term { ( " + " | " - " ) term} whitespace;
logical_expression = whitespace arithmetic_expression ( " != " | " == " | " < " | " > " ) arithmetic_expression whitespace;
term = whitespace factor { ( " * " | " / " ) factor whitespace} ;
factor = whitespace (identifier | constant) | " ( " expression " ) " whitespace ;


(*IF cond*)
if_statement = whitespace "if" " ( " expression " )" whitespace (block | instruction) whitespace [ "else" whitespace ( block | if_statement | instruction) ];
block = "{" whitespace list_instructions whitespace "}";

(*For loop*)
loopfor = whitespace "for" " ( " [declarationfor] whitespace ";" whitespace expression whitespace ";" whitespace attributionfor whitespace " )" whitespace block;
declarationfor = whitespace type ["const "] identifier [" = " {expression}] { ", " identifier [" = " expression]} ;
attributionfor = identifier " = " (constant | expression) ;
(*While loop*)
loopwhile = whitespace "while" whitespace " ( " whitespace expression whitespace " )" whitespace block;
"""


# --- Tokenizer

# All tokens must be named in advance.

tokens = (
    "INCLUDE",
    "LIBRARY",
    "LANGLEBRACKET",
    "RANGLEBRACKET",
    "NEWLINE",
    "SLCOMMENT",
    "MLCOMMENT",
    "USINGNAMESPACE",
    "MAIN",
    "LBRACE",
    "RBRACE",
    "RETURN",
    "SEMICOLON",
    "ANYCHARACTER",
    "CONST",
    "EQUALS",
    "COMMA",
    "TYPE",
    "DIGIT",
    "INTEGER",
    "DECIMAL",
    "CHARACTER",
    "STRING",
    "PLUS",
    "MINUS",
    "TIMES",
    "DIVIDE",
    "LPAREN",
    "RPAREN",
    "IFLOW",
    "OFLOW",
    "CIN",
    "COUT",
    "IF",
    "ELSE",
    "WHILE",
    "FOR",
    "NUMBER",
    "DOUBLE_QUOTE_STRING",
    "SINGLE_QUOTE_STRING",
    "IDENTIFIER",
)


# Ignored characters
t_ignore = " \t"

# Token matching rules are written as regexs
t_INCLUDE = r"\#include"
t_LIBRARY = r"(iostream|cmath)"
t_LANGLEBRACKET = r"<"
t_RANGLEBRACKET = r">"
t_NEWLINE = r"\n"
t_SLCOMMENT = r"[.]*//.*"
t_MLCOMMENT = r"[.]*/\*[.\n]*"
# t_USINGNAMESPACE = r"using\s+namespace\s+std;"
# t_MAIN = r"int\s+main\(\)"
t_LBRACE = r"{"
t_RBRACE = r"}"
t_RETURN = r"return"
t_SEMICOLON = r";"
t_CONST = r"const"
t_EQUALS = r"="
t_COMMA = r","
# t_IDENTIFIER = r"[a-zA-Z_][a-zA-Z0-9_]*"
# t_TYPE = r"(int|float|char|double|string|custom)"
# t_DIGIT = r"[0-9]"
# t_INTEGER = r"[-+]?[1-9][0-9]*"
# t_DECIMAL = r"[-+]?[0-9]*\.[0-9]+"
t_CHARACTER = r"\'[a-zA-Z0-9_?!=+\-*/^]\'"
t_STRING = r"\"[a-zA-Z0-9_?!=+\-*/^]*\""
t_PLUS = r"\+"
t_MINUS = r"-"
t_TIMES = r"\*"
t_DIVIDE = r"/"
t_LPAREN = r"\("
t_RPAREN = r"\)"
t_IFLOW = r">>"
t_OFLOW = r"<<"
t_CIN = r"cin"
t_COUT = r"cout"
t_IF = r"if"
t_ELSE = r"else"
t_WHILE = r"while"
t_FOR = r"for"
t_NUMBER = r"\d+(\.\d+)?"
t_DOUBLE_QUOTE_STRING = r'\"[^"]*\"'
t_SINGLE_QUOTE_STRING = r"\'[^\']*\'"

# A function can be used if there is an associated action.


# Define a rule so we can track line numbers
def t_newline(t):
    r"\n+"
    t.lexer.lineno += len(t.value)


# Ignored token with an action associated with it
def t_ignore_newline(t):
    r"\n+"
    t.lexer.lineno += t.value.count("\n")


# Error handler for illegal characters
def t_error(t):
    print(f"Illegal character {t.value[0]!r}")
    t.lexer.skip(1)


def t_USINGNAMESPACE(t):
    r"using\s+namespace\s+std;"
    return t


def t_MAIN(t):
    r"int\s+main\(\)"
    return t


def t_TYPE(t):
    r"(int|float|char|double|string|custom)"
    return t


def t_IDENTIFIER(t):
    r"[a-zA-Z_][a-zA-Z_0-9]*"
    t.type = {
        "if": "IF",
        "else": "ELSE",
        "while": "WHILE",
        "for": "FOR",
        "return": "RETURN",
        "cin": "CIN",
        "cout": "COUT",
        "endl": "ENDL",
        "iostream": "LIBRARY",
        "cmath": "LIBRARY",
    }.get(
        t.value, "IDENTIFIER"
    )  # Check for reserved words
    return t


# Build the lexer object
lexer = lex()

# --- Parser

# Write functions for each grammar rule which is
# specified in the docstring.

# defining number as integer or decimal


# def p_program(p):
#     """
#     program : includes USINGNAMESPACE comments
#     """
#     p[0] = ("program", p[1], p[2], p[3], p[4], p[5], p[6], p[7], p[8])


# def p_comments(p):
#     """
#     comments : SLCOMMENT
#              | MLCOMMENT
#     """
#     p[0] = ("comments", p[1])


# def p_includes(p):
#     """
#     includes : INCLUDE LANGLEBRACKET LIBRARY RANGLEBRACKET
#              | INCLUDE LANGLEBRACKET LIBRARY RANGLEBRACKET includes
#     """
#     if len(p) == 6:
#         p[0] = ("includes", p[1], p[2], p[3], p[4], p[5])
#     else:
#         p[0] = ("includes", p[1], p[2], p[3], p[4], p[5], p[6])


# def p_number(p):
#     """
#     number : INTEGER
#            | DECIMAL
#     """
#     p[0] = p[1]


# def p_expression(p):
#     """
#     expression : term PLUS term
#                | term MINUS term
#     """
#     # p is a sequence that represents rule contents.
#     #
#     # expression : term PLUS term
#     #   p[0]     : p[1] p[2] p[3]
#     #
#     p[0] = ("binop", p[2], p[1], p[3])


# def p_expression_term(p):
#     """
#     expression : term
#     """
#     p[0] = p[1]


# def p_term(p):
#     """
#     term : factor TIMES factor
#          | factor DIVIDE factor
#     """
#     p[0] = ("binop", p[2], p[1], p[3])


# def p_term_factor(p):
#     """
#     term : factor
#     """
#     p[0] = p[1]


# def p_factor_number(p):
#     """
#     factor : NUMBER
#     """
#     p[0] = ("number", p[1])


# def p_factor_name(p):
#     """
#     factor : IDENTIFIER
#     """
#     p[0] = ("identifier", p[1])


# def p_factor_unary(p):
#     """
#     factor : PLUS factor
#            | MINUS factor
#     """
#     p[0] = ("unary", p[1], p[2])


# def p_factor_grouped(p):
#     """
#     factor : LPAREN expression RPAREN
#     """
#     p[0] = ("grouped", p[2])


# def p_error(p):
#     print(f"Syntax error at {p.value!r}")


# Build the parser
# parser = yacc()

# # Parse an expression
# # read from file ccode.cpp
# file = open("ccode.cpp", "r")
# code = file.read()
# ast = parser.parse(code)
# print(ast)

# lexer
# read from file ccode.cpp
# ----------------------------
# Compute column.
#     input is the input text string
#     token is a token instance
def find_column(input, token):
    line_start = input.rfind("\n", 0, token.lexpos) + 1
    return (token.lexpos - line_start) + 1


# ----------------------------


file = open("ccode.cpp", "r")
code = file.read()
lexer.input(code)

# iterate through tokens with lexer
from mytree import BinarySearchTree

list_const = BinarySearchTree()
list_id = BinarySearchTree()

listCategories = {}
lastKey = -1
toks = []
fip = []

for tok in lexer:
    # Show like in a table, separated by |
    # print with table format, keep spacing and separate by |
    # print things in the middle of that space
    if (
        tok.type == "NUMBER"
        or tok.type == "CHARACTER"
        or tok.type == "DOUBLE_QUOTE_STRING"
        or tok.type == "SINGLE_QUOTE_STRING"
    ):
        tok.type = "CONSTANT"
        list_const.insert(tok)
    elif tok.type == "IDENTIFIER":
        if len(tok.value) > 8:
            # raise exception and show where specifically the token is, line and position
            # extract column from lexpos and lineno

            raise Exception(
                f"Identifier name | {tok.value} | too long, max 8 characters. line {tok.lineno} : col {find_column(code, tok)}"
            )
        list_id.insert(tok)
    if tok.type not in listCategories.keys():
        # last category in the dictionary + 1
        listCategories[tok.type] = lastKey + 1
        lastKey = lastKey + 1
    toks.append(tok)
    print(f"{tok.type:^20}|{tok.value:^20}|{tok.lineno:^20}|{tok.lexpos:^20}")

# IDENTIFIER is the category for all variable names, so that's a table
def printConstants():
    list_const.inorder_traversal(print_result=True, category="CONSTANT")


def printId():
    list_id.inorder_traversal(print_result=True, category="IDENTIFIER")


# def uniq(list):
#     # clear duplicates from given list based on tok.value
#     uniq_list = []
#     for tok in list:
#         if tok.value not in uniq_list:
#             uniq_list.append(tok.value)
list_const.assign_id()
list_id.assign_id()


def printCategories():
    # print as a table
    print(
        "\n\n\n",
        "------------------------------------------------",
        sep="",
    )
    print(f"{'CATEGORY':^20}|{'CODE':^20}")
    print("------------------------------------------------")
    for key, value in listCategories.items():
        print(f"{key:^20}|{value:^20}")


# printCategories()
# the main loop to construct the fip
for tok in toks:
    # get code from tok category
    atomCode = listCategories[tok.type]
    pair = (atomCode, 1)
    symbolCode = None
    if tok.type == "IDENTIFIER":
        symbolCode = list_id.search(tok).id
    elif tok.type == "CONSTANT":
        symbolCode = list_const.search(tok).id
    pair = (atomCode, symbolCode)
    fip.append(pair)


def printFip():
    print(
        "\n\n\n",
        "------------------------------------------------",
        sep="",
    )
    print(f"{'ATOM CODE':^20}|{'SYMBOL CODE':^20}")
    print("------------------------------------------------")
    for pair in fip:
        if pair[1] == None:
            print(f"{pair[0]:^20}|{'--':^20}")
        else:
            print(f"{pair[0]:^20}|{pair[1]:^20}")


printConstants()
printId()

printCategories()

printFip()
