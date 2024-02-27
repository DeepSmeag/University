bison -d parser.y
flex lexer.l
g++ -o run parser.tab.c lex.yy.c -lfl
./run src.cpp