
#include "p3.h"
int n = 21, m = 41;
point d[4] = {
    {-1, 0},
    {0, 1},
    {1, 0},
    {0, -1},
};
// sus dr jos stanga st-sus dr-sus st-jos dr-jos
bool valid(labirint *L, int row, int col)
{
    if (row >= 0 && row < n && col >= 0 && col < m)
        return true;
    return false;
}
labirint citire()
{
    ifstream f("labirint_1.txt");
    labirint L;
    for (int i = 0; i < n; i++)
    {
        string s;
        getline(f, s);
        strcpy(L.mat[i], s.c_str());
    }
    return L;
}
void afisL(labirint L)
{
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < m; j++)
        {
            cout << L.mat[i][j];
        }
        cout << endl;
    }
}
point getStartingPoint(labirint L)
{
    point p;
    p.first = 0;
    for (int j = 0; j < m; j++)
    {
        if (L.mat[0][j] == 'S')
        {
            p.second = j;
            break;
        }
    }
    return p;
}
point getFinishPoint(labirint L)
{
    point p;
    p.first = n - 1;
    for (int j = 0; j < m; j++)
    {
        if (L.mat[n - 1][j] == 'F')
        {
            p.second = j;
            break;
        }
    }
    return p;
}
void BFSlab(labirint *L, point start, point finish)
{
    bool visited[n][m];
    memset(visited, false, sizeof(visited));
    visited[start.first][start.second] = true;
    qnodes Q;
    qNode parents[n][m];
    qNode s = {start, 0};
    parents[s.p.first][s.p.second] = {{-1, -1}, 0};
    Q.push(s);
    while (!Q.empty())
    {
        qNode curr = Q.front();

        if (curr.p == finish)
        {
            break;
        }

        Q.pop();
        for (int i = 0; i < 4; i++)
        {
            int row = curr.p.first + d[i].first;
            int col = curr.p.second + d[i].second;
            if (!visited[row][col] && valid(L, row, col) && L->mat[row][col] != '1')
            {

                visited[row][col] = true;
                qNode adj = {{row, col}, curr.d + 1};
                parents[row][col] = {curr.p, curr.d + 1};

                Q.push(adj);
            }
        }
    }
    while (!Q.empty())
    {
        Q.pop();
    }
    int row, col;
    row = parents[finish.first][finish.second].p.first;
    col = parents[finish.first][finish.second].p.second;
    while (row != start.first || col != start.second)
    {
        L->mat[row][col] = '+';
        int cprow = row;
        row = parents[row][col].p.first;
        col = parents[cprow][col].p.second;
    }
}
void p3()
{
    labirint L = citire();
    point startPoint, finishPoint;
    startPoint = getStartingPoint(L);
    finishPoint = getFinishPoint(L);
    BFSlab(&L, startPoint, finishPoint);
    afisL(L);

    return;
}