#include <iostream>
#include <fstream>
#include <cmath>
#include <vector>
#include <algorithm>
using namespace std;
int n;
typedef struct
{
    int x, y;
} point;
point points[1000];
typedef pair<int, int> intPair;
struct muchie
{
    int x, y;
    double cost;
    muchie(int x, int y, double cost) : x{x}, y{y}, cost{cost} {}
};
double dist(point A, point B)
{
    return sqrt((A.x - B.x) * (A.x - B.x) + (A.y - B.y) * (A.y - B.y));
}

struct Graph
{
    int V, E;
    vector<muchie> edges;

    Graph(int V, int E)
    {
        this->V = V;
        this->E = E;
    }

    void addEdge(muchie m)
    {
        edges.push_back(m);
    }

    double kruskal();
};

struct multimiSep
{
    int *parent, *rank;
    int n;

    multimiSep(int n)
    {
        this->n = n;
        parent = new int[n + 1];
        rank = new int[n + 1];

        // initial multimi diferite si rang 0, rangul e practic nr. de multimi merge-uite
        for (int i = 0; i <= n; i++)
        {
            rank[i] = 0;

            // consideram fiecare elem ca fiind propriul parinte, pe baza parintelui facem diferenta
            parent[i] = i;
        }
    }

    int find(int u)
    {
        // toate elem pe ruta cu un parinte vor avea acel parinte
        // asta e practic cautarea pentru parintele care defineste multimea
        // asta ne da multimea distincta
        if (u != parent[u])
            parent[u] = find(parent[u]);
        return parent[u];
    }

    // unim multimi
    void merge(int x, int y)
    {
        x = find(x), y = find(y);

        // arbore mai mic devine subarbore al celui mai mare
        if (rank[x] > rank[y])
            parent[y] = x;
        else
            parent[x] = y;

        if (rank[x] == rank[y])
            rank[y]++;
    }
};

vector<pair<int, int>> vect;
double Graph::kruskal()
{
    double totalWeight = 0.0; // weight total

    // sortare muchii
    sort(
        edges.begin(), edges.end(), [&](muchie m1, muchie m2)
        { return (m1.cost - m2.cost < 0.0); });

    // facem multimile
    multimiSep multimi(V);

    vector<muchie>::iterator it;
    for (it = edges.begin(); it != edges.end(); it++)
    {
        int u = it->x;
        int v = it->y;

        int set_u = multimi.find(u);
        int set_v = multimi.find(v);

        // daca sunt multimi diferite
        if (set_u != set_v)
        {
            // adaugam muchia in arbore min
            vect.push_back(make_pair(u, v));

            // update weight
            // cout << totalWeight << "\n";
            totalWeight += it->cost;

            // multimi imbinate
            multimi.merge(set_u, set_v);
        }
    }

    return (double)totalWeight;
}
void arbMin()
{
    int V = n;
    int E = n * (n - 1) / 2;
    Graph G(V, E);
    for (int i = 0; i < V - 1; i++)
    {
        for (int j = i + 1; j < V; j++)
        {
            muchie m1(i, j, dist(points[i], points[j]));
            G.addEdge(m1);
        }
    }

    ofstream g("out.txt");
    double totalWeight = G.kruskal();

    g << (double)totalWeight;
}

// x x x
// x x x
// x x x
int main(int argc, char **argv)
{
    ifstream f("in.txt");
    ofstream g("out.txt");

    f >> n;

    for (int i = 0; i < n; i++)
    {
        int x, y;
        f >> x >> y;
        points[i].x = x;
        points[i].y = y;
        // modelam un punct 2D ca fiind un singur nod pe graf ca ne pasa doar de costul final
    }
    arbMin();
    return 0;
}
// punctul citit devine index intr-un vector de puncte 2D
/**
 * Pentru fiecare punct din vector cream muchie cu cost asociat
 * Functia de calcul cost este cea de distanta euclidiana
 * Aplicam kruskal pe toate punctele respective
 * Din moment ce sunt modelate ca indexi nu ne pasa de diferenta de reprezentare
 *
 *
 * Kruskal:
 * Avem vector de muchii, sortam crescator
 * Cream n multimi disjuncte
 * Pe masura ce cream arborele de cost minim:
 *  Trecem prin muchii
 *  Vedem capetele care sunt
 *  Verificam daca capetele sunt in multimi disjuncte
 *  Daca sunt in multimi disjuncte (implicit nu au fost adaugate in arborele mare care va fi multimea mare de la final)
 *      impreunam multimile si adaugam costul muchiei la costul total
 *  Imbinam multimile
 *  Trecem mai departe
 *
 */