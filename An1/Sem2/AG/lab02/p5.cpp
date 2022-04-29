#include "p5.h"
int Ttime = -1;
void DFS_VISIT(graph G, varf *V, int u)
{
    Ttime++;
    V[u].d = Ttime;
    cout << u << " -> " << V[u].d << endl;
    V[u].color = 1;
    for (int i = 0; i < G.noduri; i++)
    {
        if (G.matAdj[u][i] == 1)
        {
            if (V[i].color == 0)
            {
                V[i].p = u;
                DFS_VISIT(G, V, i);
            }
        }
    }
    V[u].color = 2;
    Ttime++;
}
void DFS(graph G, int s)
{
}
void p5(int s)
{
    graph G = readGraph();
    convertUnordered(&G);
    varf V[G.noduri];
    for (int i = 0; i < G.noduri; i++)
    {
        V[i].color = 0;
        V[i].d = inf;
        V[i].p = -1;
    }
    DFS_VISIT(G, V, s);
}