import ply.lex as lex

# Token definition
tokens = ("VARIABLE_NAME",)

# Regular expression for valid C++ identifiers
def t_VARIABLE_NAME(t):
    r"[a-zA-Z_][a-zA-Z0-9_]*"
    return t


# Ignore whitespace
t_ignore = " \t"

# Build the lexer
lexer = lex.lex()

# Test the lexer
code = "int main() { int x = 10; double y = 3.14; return 0; }"

lexer.input(code)

for token in lexer:
    print(token)
