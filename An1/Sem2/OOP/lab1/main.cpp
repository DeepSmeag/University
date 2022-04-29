#include <iostream>
#include <stdio.h>
using namespace std;
// 13
// Descompune in factori primi un numar natural nenul dat.
int sum(int a, int b)
{
    return a + b;
}
void desc_primi(int x);
void p1(int x);
void p2(int x);
void p3(int x);
void p4(int k, int m);
void p15(int n);
void p16(int n);
void p17(int x);
int isPrime(int x)
{
    if (x == 2)
        return 1;
    if (x % 2 == 0)
        return 0;
    for (int i = 3; i * i <= x; i += 2)
    {
        if (x % i == 0)
            return 0;
    }
    return 1;
}
int cmmdc(int a, int b)
{
    int r;
    r = a % b;
    if (r)
        return cmmdc(b, r);
    else
        return b;
}
int main(int, char **)
{
    int n, x;

    // desc_primi(n);
    while (1)
    {
        int cmd;
        printf("\n\
                1. Descompunere primi \n\
                2. Primele n numere \n\
                3. Metode de a scrie n ca suma de n nr consec \n\
                0. Exit\n");
        printf(">>");
        scanf("%d", &cmd);
        if (cmd == 0)
        {
            break;
        }
        if (cmd == 1)
        {
            cin >> n;
            desc_primi(n);
        }
        if (cmd == 2)
        {
            cin >> n;
            p2(n);
        }
        if (cmd == 3)
        {
            cin >> n;
            p3(n);
        }
    }
    // p17(n);
    // int s = 0;
    // for (int i = 0; i < n; i++)
    // {
    //     cin >> x;
    //     s = sum(s, x);
    // }
    // cout << "suma:" << s << endl;
    // n = cin.get();
    // cin.ignore();
    // cin.get();
    return 0;
}
/**
 * @brief Descompune in factori primi un numar natural nenul dat.
 *
 * @param x:int
 */
void desc_primi(int x)
{
    int d = 2;
    while (x != 1)
    {
        if (x % d == 0)
        {
            cout << d << " ";
            while (x % d == 0)
            {
                x /= d;
            }
        }
        d++;
    }
    cout << endl;
}
/**
 * @brief Genereaza numere prime mai mici decat un numar natural dat.
 *
 * @param x:int
 */
void p1(int x)
{
    if (x > 2)
    {
        cout << "2 ";
    }
    for (int i = 3; i < x; i += 2)
    {
        if (isPrime(i))
        {
            cout << i << " ";
        }
    }
}
/**
 * @brief   Genereaza primele n (n natural dat) numere prime.
 *
 * @param x-int
 */
void p2(int x)
{
    int i = 2;
    while (x > 0)
    {
        while (!isPrime(i))
            i++;
        cout << i << " ";
        x--;
        i++;
    }
}

/**
 * @brief  Determina toate reprezentarile posibile a unui numar natural ca suma

   de numere naturale consecutive.
 *
 * @param x:int
 */
void p3(int x)
{

    for (int i = 0; i < x / 2; i++)
    {
        int s = i;
        int j = i + 1;
        while (s < x)
        {
            s += j;
            j++;
        }
        if (s == x)
        {
            for (int k = i; k < j; k++)
            {
                cout << k << " ";
            }
            cout << endl;
        }
    }
}
/**
 * @brief Determina primele 10 numere naturale strict mai mari ca 2, care au

    proprietatea ca toate numerele naturale strict mai mici decat ele

    si care sunt prime cu ele sunt numere prime.
 *
 * @param x
 */
void p17(int x)
{
    int nr = x;
    int d = 3;
    while (nr > 0)
    {
        int ok = 1;
        for (int i = 2; i < d; i++)
        {
            if (!isPrime(i) && cmmdc(i, d) == 1)
            {
                ok = 0;
                break;
            }
        }
        if (ok == 1)
        {
            cout << d << " ";
            nr--;
        }
        d++;
    }
}
