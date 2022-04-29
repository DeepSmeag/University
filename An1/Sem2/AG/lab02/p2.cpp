
#include "p2.h"

void afisp2(int mattranz[10][10], int noduri)
{
    for (int i = 0; i < noduri; i++)
    {
        for (int j = 0; j < noduri; j++)
        {
            cout << mattranz[i][j] << " ";
        }
        cout << endl;
    }
}
void p2()
{
    graph G = readGraph();
    int mattranz[10][10];
    for (int i = 0; i < G.noduri; i++)
    {
        for (int j = 0; j < G.noduri; j++)
        {
            mattranz[i][j] = 0;
        }
    }

    for (int nod = 0; nod < G.noduri; nod++)
    {
        retMoore ret = Moore(nod);
        for (int i = 0; i < G.noduri; i++)
        {
            if (ret.l[i] != inf)
            {
                mattranz[nod][i] = 1;
            }
        }
    }
    afisp2(mattranz, G.noduri);
    // citeste graful
    // incep parcurgere folosind moore dar din fiecare varf
    // updatez mat  de inchidere tranzitiva folosind ret.p !=-1
}