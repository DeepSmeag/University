#include "p1bellman.h"

typedef struct
{
    int i;
    int val;
} funcval;
void p2(std::string in, std::string out);

bool Johnson(muchie *m, node *nodes, funcval *h, int v, int e, int **mat);
