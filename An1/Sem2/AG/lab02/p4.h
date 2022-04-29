#include "p3.h"

typedef struct
{
    int color;
    int d;
    int p;
} varf;

void p4(int s);
void convertUnordered(graph *G);
void BFS(graph G, int s);