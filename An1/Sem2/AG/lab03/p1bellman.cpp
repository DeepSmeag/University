#include "p1bellman.h"

// void Initializare_s(node *nodes, int s, int v)
// {
//     for (int i = 0; i < v; i++)
//     {
//         nodes[i].i = i;
//         nodes[i].d = INF;
//         nodes[i].parinte = NIL;
//     }
//     nodes[s].d = 0;
// }
// void Relax(muchie *m, node *nodes, int x, int y, int ind_muchie)
// {
//     if (nodes[y].d > nodes[x].d + m[ind_muchie].w)
//     {
//         nodes[y].d = nodes[x].d + m[ind_muchie].w;
//         nodes[y].parinte = x;
//     }
// }

bool Bellman(muchie *m, node *nodes, int v, int e, int s)
{
    Initializare_s(nodes, s, v);
    for (int i = 0; i < v; i++)
    {
        for (int j = 0; j < e; j++)
        {
            Relax(m, nodes, m[j].x, m[j].y, j);
        }
    }
    for (int j = 0; j < e; j++)
    {
        if (nodes[m[j].y].d > nodes[m[j].x].d + m[j].w)
        {
            return false;
        }
    }
    return true;
}

void p1bellman(string in, string out)
{
    ifstream fin(in);
    ofstream fout(out);

    int v, e, s;
    fin >> v >> e >> s;
    muchie *m;
    m = new muchie[e];
    for (int i = 0; i < e; i++)
    {
        int x, y, w;
        fin >> x >> y >> w;
        m[i].x = x;
        m[i].y = y;
        m[i].w = w;
    }

    node *nodes = new node[v];
    if (Bellman(m, nodes, v, e, s) == true)
    {
        for (int i = 0; i < v; i++)
        {
            if (nodes[i].d == INF)
                fout << "INF ";
            else
                fout << nodes[i].d << " ";
        }
    }
    delete[] m;
    delete[] nodes;
    fin.close();
    fout.close();
}