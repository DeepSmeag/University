#include <iostream>
#include <fstream>
#include <queue>
#define inf 9999
using namespace std;
typedef queue<int> qint;
typedef pair<int, int> listAdj;
typedef struct
{
    int matAdj[10][10];
    listAdj muchii[10];
    int nrmuchii;
    int noduri;
} graph;
typedef struct
{
    int l[10];
    int p[10];
    int noduri;
} retMoore;

graph readGraph();
retMoore Moore(int u, bool afis = false);
void afisRetMoore(retMoore ret);

// 1. Implementați algoritmul lui Moore pentru un graf
// orientat neponderat (algoritm bazat pe Breath-first search,
// vezi cursul 2). Datele sunt citete din fisierul
// graf.txt. Primul rând din graf.txt conține numărul vârfurilor,
//  iar următoarele rânduri conțin muchiile grafului.
// Programul trebuie să afiseze lanțul cel mai scurt
// dintr-un vârf (vârful sursa poate fi citit de la tastatura).