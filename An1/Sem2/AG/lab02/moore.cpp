#include "moore.h"

void afisRetMoore(retMoore ret)
{
    for (int i = 0; i < ret.noduri; i++)
    {
        printf("%5d ", ret.l[i]);
    }
    cout << "\n";
    for (int i = 0; i < ret.noduri; i++)
    {
        printf("%5d ", ret.p[i]);
    }
    cout << endl;
}
graph readGraph()
{
    ifstream f("graf.txt");
    graph G;
    f >> G.noduri;
    G.nrmuchii = 0;
    int x, y;
    for (int i = 0; i < G.noduri; i++)
    {
        for (int j = 0; j < G.noduri; j++)
        {
            G.matAdj[i][j] = 0;
        }
    }
    while (f >> x >> y)
    {
        G.muchii[G.nrmuchii].first = x;
        G.muchii[G.nrmuchii++].second = y;
        G.matAdj[x][y] = 1;
    }
    return G;
}

retMoore Moore(int u, bool afis)
{
    graph G = readGraph();
    retMoore ret;

    ret.noduri = G.noduri;
    for (int i = 0; i < ret.noduri; i++)
    {
        ret.p[i] = -1;
        ret.l[i] = 0;
    }
    ret.l[u] = 0;
    for (int i = 0; i < G.noduri; i++)
    {
        if (i != u)
        {
            ret.l[i] = inf;
        }
    }
    qint Q;
    Q.push(u);
    while (!Q.empty())
    {
        int x = Q.front();
        Q.pop();
        for (int i = 0; i < G.noduri; i++)
        {
            if (G.matAdj[x][i] == 1)
            {
                if (ret.l[i] == inf)
                {
                    ret.p[i] = x;
                    ret.l[i] = ret.l[x] + 1;
                    Q.push(i);
                }
            }
        }
    }
    if (afis)
        afisRetMoore(ret);
    return ret;
}
