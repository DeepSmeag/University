#include "pruferEncode.h"
using namespace std;

int v;
int K[100];
int knr = 0;
void afis()
{
    for (int i = 1; i < knr; i++)
    {
        cout << K[i] << " ";
    }
}
int ceaMaiMicaFrunza()
{
    int minf = v;
    for (int i = 1; i <= v; i++)
    {
        if (nod[i].deg == 1 && i < minf)
            minf = i;
    }
    return minf;
}
int sizeArbore()
{
    int size = 0;
    for (int i = 1; i <= v; i++)
    {
        if (nod[i].p != -1)
            size++;
    }
    return size;
}
void pruferEncode()
{
    for (int i = 0; i < 100; i++)
    {
        K[i] = -1;
    }
    ifstream f("in.txt");

    f >> v;
    for (int i = 0; i <= v; i++)
    {
        nod[i].p = -1;
        nod[i].deg = 0;
    }

    for (int i = 1; i <= v; i++)
    {
        f >> nod[i].p;
        nod[i].deg++;
        nod[nod[i].p].deg++;
    }
    while (sizeArbore() > 0)
    {
        int f = ceaMaiMicaFrunza();
        K[++knr] = nod[f].p;
        nod[nod[f].p].deg--;
        nod[f].p = -1;
        nod[f].deg = 0;
    }
    afis();
}