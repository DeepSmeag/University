#include "p2.h"

using namespace std;

bool Johnson(muchie *m, node *nodes, funcval *h, int v, int e, int **mat)
{
    for (int i = 0; i < v; i++)
    {
        m[i + e].x = v;
        m[i + e].y = i;
        m[i + e].w = 0;
    }

    // bellman
    if (!Bellman(m, nodes, v + 1, e + v, v))
    {
        return false;
    }
    else
    {
        for (int i = 0; i < v + 1; i++)
        {
            h[i].val = nodes[i].d;
            h[i].i = i;
        }
        for (int j = 0; j < e + v; j++)
        {
            m[j].w = m[j].w + h[m[j].x].val - h[m[j].y].val;
        }
        // rulam Dijkstra pentru a determina toate drumurile cele mai scurte

        for (int i = 0; i < v; i++)
        {
            Dijkstra(m, nodes, i, v, e);

            for (int j = 0; j < v; j++)
            {
                if (nodes[j].d == INF)
                {
                    mat[i][j] = INF;
                }
                else
                {
                    mat[i][j] = nodes[j].d - h[i].val + h[j].val; //?
                }
            }
        }
        return true;
    }
}

void p2(string in, string out)
{
    ifstream fin(in);
    ofstream fout(out);

    int v, e, s;
    fin >> v >> e;
    muchie *m = new muchie[e + v];
    node *nodes = new node[v + 1];
    funcval *h = new funcval[v + 1];
    for (int i = 0; i < e; i++)
    {
        fin >> m[i].x >> m[i].y >> m[i].w;
    }

    int **mat;
    mat = new int *[v];
    for (int i = 0; i < v; i++)
    {
        mat[i] = new int[v];
    }

    if (Johnson(m, nodes, h, v, e, mat))
    {
        // afis m

        for (int i = 0; i < e; i++)
        {
            fout << m[i].x << " " << m[i].y << " " << m[i].w << endl;
        }

        // afis mat
        for (int i = 0; i < v; i++)
        {
            for (int j = 0; j < v; j++)
            {
                if (mat[i][j] == INF)
                    fout << "INF ";
                else
                    fout << mat[i][j] << " ";
            }
            fout << "\n";
        }
    }
    else
    {
        fout << -1;
    }
    delete[] m;
    delete[] nodes;
    delete[] h;
    for (int i = 0; i < v; i++)
    {
        delete[] mat[i];
    }
    delete[] mat;

    fin.close();
    fout.close();
}