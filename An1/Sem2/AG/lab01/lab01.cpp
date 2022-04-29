
#include <bits/stdc++.h>
#include <fstream>
using namespace std;

ifstream f("in.txt");
ifstream f2("in2.txt");
int mat[100][100];
int matdist[100][100];
int adj[100][100];
int incid[100][100];
int listadj[100][100];
int adj2[100][100];

class Graph
{
public:
    map<int, bool> visited;
    map<int, list<int>> adj;

    // function to add an edge to graph
    void addEdge(int v, int w);

    // DFS traversal of the vertices
    // reachable from v
    void DFS(int v);
};

void Graph::addEdge(int v, int w)
{
    adj[v].push_back(w); // Add w to v’s list.
}

void Graph::DFS(int v)
{
    // Mark the current node as visited and
    // print it
    visited[v] = true;
    cout << v << " ";

    // Recur for all the vertices adjacent
    // to this vertex
    list<int>::iterator i;
    for (i = adj[v].begin(); i != adj[v].end(); ++i)
        if (!visited[*i])
            DFS(*i);
}

void afis(int mat[100][100], int l, int c)
{
    for (int i = 0; i < l; i++)
    {
        for (int j = 0; j < c; j++)
        {
            cout << mat[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
}
void verif(int mat1[100][100], int mat2[100][100], int l)
{
    for (int i = 0; i < l; i++)
    {
        for (int j = 0; j < l; j++)
        {
            if (mat1[i][j] != mat2[i][j])
            {
                cout << "False";
                return;
            }
        }
    }
    cout << "True";
}
void pr1()
{
    int noduri;
    f >> noduri;
    int x, y;
    int nrmuchii = 0;
    while (f >> x >> y)
    {
        adj[x - 1][y - 1] = adj[y - 1][x - 1] = 1;
        // adj de adiacenta e gata
    }

    afis(adj, noduri, noduri);
    for (int i = 0; i < noduri; i++)
    {
        for (int j = i + 1; j < noduri; j++)
        {
            if (adj[i][j] == 1)
            {
                listadj[i][0]++;
                int temp = listadj[i][0];
                listadj[i][temp] = j + 1;
                listadj[j][0]++;
                temp = listadj[j][0];
                listadj[j][temp] = i + 1;
            }
        }
    }
    afis(listadj, noduri, noduri);

    for (int i = 0; i < noduri; i++)
    {
        for (int j = 1; j <= listadj[i][0]; j++)
        {
            if (listadj[i][j] - 1 > i)
            {
                incid[i][nrmuchii] = incid[listadj[i][j] - 1][nrmuchii] = 1;
                nrmuchii++;
            }
        }
    }

    afis(incid, noduri, nrmuchii);

    for (int muchie = 0; muchie < nrmuchii; muchie++)
    {
        int cap = 0, coada = 0;
        for (int nod = 0; nod < noduri; nod++)
        {
            if (incid[nod][muchie] != 0)
            {
                if (cap == 0)
                    cap = nod + 1;
                coada = nod + 1;
            }
        }
        adj2[cap - 1][coada - 1] = adj2[coada - 1][cap - 1] = 1;
    }
    afis(adj2, noduri, noduri);
    verif(adj, adj2, noduri);
}

void pr2()
{

    int noduri;
    Graph g;
    f2 >> noduri;
    int x, y;
    int grade[100];
    for (int i = 0; i < 100; i++)
        grade[i] = 0;
    while (f2 >> x >> y)
    {
        mat[x - 1][y - 1] = mat[y - 1][x - 1] = 1;
        grade[x - 1]++;
        grade[y - 1]++;
        g.addEdge(x - 1, y - 1);
    }
    // a)
    afis(mat, noduri, noduri);
    for (int i = 0; i < noduri; i++)
    {
        int ok = 0;
        for (int j = 0; j < noduri; j++)
        {
            if (mat[i][j] == 1)
            {
                ok = 1;
                break;
            }
        }
        if (ok == 0)
            cout << "nodul " << i + 1 << " este izolat" << endl;
    }
    // b)
    for (int i = 1; i < noduri; i++)
    {
        if (grade[0] != grade[i])
        {
            cout << "Graful nu e regular" << endl;
            break;
        }
    }
    // c)
}
int main()
{
    // pr1();
    pr2();
    return 0;
}