
#include "p1.h"

// #define INF 9999999
// #define NIL -1

typedef struct
{
    int x, y, w;
} muchieBellman;

typedef struct
{
    int i;
    int d;
    int parinte;
} nodeBellman;
bool Bellman(muchie *m, node *nodes, int v, int e, int s);
void p1bellman(string fin, string fout);
// void Initializare_s(node *nodes, int s, int v);
// void Relax(muchie *m, node *nodes, int x, int y, int ind_muchie);