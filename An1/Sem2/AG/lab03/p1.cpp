#include "p1.h"

void Initializare_s(node *nodes, int s, int v)
{
    for (int i = 0; i < v; i++)
    {
        nodes[i].i = i;
        nodes[i].d = INF;
        nodes[i].parinte = NIL;
    }
    nodes[s].d = 0;
}
void Relax(muchie *m, node *nodes, int x, int y, int ind_muchie)
{
    if (nodes[y].d > nodes[x].d + m[ind_muchie].w)
    {
        nodes[y].d = nodes[x].d + m[ind_muchie].w;
        nodes[y].parinte = x;
    }
}
void Dijkstra(muchie *m, node *nodes, int s, int v, int e)
{
    Initializare_s(nodes, s, v);
    qint Q(comp);
    for (int i = 0; i < v; i++)
    {
        Q.push(nodes[i]);
    }
    while (!Q.empty())
    {
        node u = Q.top();
        Q.pop();
        int i = u.i;
        // adaug u in sol
        for (int j = 0; j < e; j++)
        {
            if (m[j].x == i && m[j].w >= 0)
            {
                // Relax(mat, nodes, i, j);
                if (nodes[m[j].y].d > nodes[i].d + m[j].w)
                {
                    nodes[m[j].y].d = nodes[i].d + m[j].w;
                    nodes[m[j].y].parinte = i;
                    Q.push(nodes[m[j].y]);
                }
            }
        }
    }
}

void p1(string in, string out)
{
    ifstream fin(in);
    ofstream fout(out);

    int v, e, s;
    fin >> v >> e >> s;

    muchie *m = new muchie[e];
    // mat = new int *[v + 1];
    // for (int i = 0; i <= v; i++)
    // {
    //     mat[i] = new int[v + 1];
    // }
    // for (int i = 0; i <= v; i++)
    // {
    //     for (int j = 0; j <= v; j++)
    //     {
    //         mat[i][j] = 0;
    //     }
    // }
    for (int i = 0; i < e; i++)
    {
        int x, y, w;
        fin >> x >> y >> w;
        m[i].x = x;
        m[i].y = y;
        m[i].w = w;
    }
    node *nodes = new node[v];
    Dijkstra(m, nodes, s, v, e);
    for (int i = 0; i < v; i++)
    {
        if (nodes[i].d == INF)
            fout << "INF ";
        else
            fout << nodes[i].d << " ";
    }
    delete[] nodes;
    delete[] m;
    // for (int i = 0; i <= v; i++)
    // {
    //     delete mat[i];
    // }
    // delete[] mat;
    fin.close();
    fout.close();
}