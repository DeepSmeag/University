#include "bits/stdc++.h"
#include <queue>
using namespace std;
#define INF 9999999
#define NIL -1

typedef struct
{
    int x, y, w;
} muchie;

typedef struct
{
    int i;
    int d;
    int parinte;
} node;

static bool comp(node &a, node &b) // overloading both operators
{
    return a.d > b.d; // if you want increasing order;(i.e increasing for minPQ)
}

typedef priority_queue<node, vector<node>, function<bool(node &, node &)>> qint;

void p1(string in, string out);
void Dijkstra(muchie *m, node *nodes, int s, int v, int e);
void Initializare_s(node *nodes, int s, int v);
void Relax(muchie *m, node *nodes, int x, int y, int ind_muchie);