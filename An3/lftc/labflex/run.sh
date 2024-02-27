
# Run flex on flex.l
flex flex.l;

# Compile the file using g++ into flex.o
g++ lex.yy.c test.cpp -lfl -o flex.o;

# Run flex.o with code.txt as a parameter
./flex.o code.txt
