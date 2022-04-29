#include <iostream>
#include <fstream>
#include <cstring>
#include "p2.h"
using namespace std;
typedef struct
{
    char mat[22][42];
} labirint;
typedef pair<int, int> point;
typedef struct
{
    point p;
    int d;
} qNode;
typedef queue<qNode> qnodes;
void p3();
void BFSlab(labirint *L, point start, point finish);
void afisL(labirint L);
bool valid(labirint *L, int row, int col);
pair<int, int> getStartingPoint(labirint L);
pair<int, int> getFinishPoint(labirint L);
labirint citire();
// 3. Să se scrie un program care găsește o soluție pentru unul din
// următoarele labirinturi: labirint_1.txt, labirint_2.txt,
//  labirint_cuvinte.txt. Pentru labirintul 1 si 2 punctul de
//   pornire este indicat de litera S și punctul de oprire este
//    indicat de litera F, spre deosebire de labirintul 1 și 2,
//     labirintul 3 nu are o soluție unică.