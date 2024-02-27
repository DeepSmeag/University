flex lexerpalindrome.l
bison -d parserpalindrome.y
gcc lex.yy.c parserpalindrome.tab.c -lfl -o myparser
./myparser < palindrome.txt
