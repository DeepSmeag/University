#include <bits/stdc++.h>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;
int mat[100][100];
int v, e;
int nrGrup = 1;
int grupAsociat[100];
int viz[100];
void BFS(int s)
{
    queue<int> Q;
    Q.push(s);
    grupAsociat[s] = nrGrup;
    viz[s] = 1;
    while (!Q.empty())
    {
        int x = Q.front();
        Q.pop();
        for (int i = 1; i <= v; i++)
        {
            if (i != x && mat[x][i] == 1 && viz[i] == 0)
            {
                viz[i] = 1;
                grupAsociat[i] = nrGrup;
                Q.push(i);
            }
        }
    }
}
void afisare()
{
    for (int i = 1; i <= nrGrup; i++)
    {
        cout << "Grupul " << i << ": ";
        for (int j = 1; j <= v; j++)
        {
            if (grupAsociat[j] == i)
            {
                cout << j << " ";
            }
        }
        cout << "\n";
    }
}
int main()
{
    ifstream f("in.txt");
    ofstream g("out.txt");

    f >> v >> e;
    for (int i = 1; i <= v; i++)
    {
        viz[i] = 0;
    }
    for (int i = 0; i < e; i++)
    {
        int x, y;
        f >> x >> y;
        mat[x][y] = mat[y][x] = 1;
    }
    for (int i = 1; i <= v; i++)
    {
        grupAsociat[i] = 0;
    }
    for (int i = 1; i <= v; i++)
    {
        if (grupAsociat[i] == 0)
        {
            BFS(i);
            nrGrup++;
        }
    }
    nrGrup--;
    afisare();
    return 0;
}