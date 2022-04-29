
#include "p4.h"
void BFS(graph G, int s)
{
    varf V[G.noduri];
    for (int i = 0; i < G.noduri; i++)
    {
        V[i].color = 0;
        V[i].d = inf;
        V[i].p = -1;
    }
    V[s].color = 1;
    V[s].d = 0;
    V[s].p = -1;
    cout << s << " -> " << V[s].d << endl;
    qint Q;
    Q.push(s);
    while (!Q.empty())
    {
        int u = Q.front();
        Q.pop();
        for (int i = 0; i < G.noduri; i++)
        {
            if (G.matAdj[u][i] == 1)
            {
                if (V[i].color == 0)
                {
                    V[i].color = 1;
                    V[i].d = V[u].d + 1;
                    cout << i << " -> " << V[i].d << endl;
                    V[i].p = u;
                    Q.push(i);
                }
            }
        }
        V[u].color = 2;
    }
}
void convertUnordered(graph *G)
{
    for (int i = 0; i < G->noduri; i++)
    {
        for (int j = 0; j < G->noduri; j++)
        {
            if (G->matAdj[i][j] == 1)
                G->matAdj[j][i] = 1;
        }
    }
}
void p4(int s)
{
    graph G = readGraph();
    convertUnordered(&G);
    BFS(G, s);
}