#include <iostream>
#include <fstream>

using namespace std;

int main(int argnum, char **args)
{
    int n = atoi(args[1]);
    int m = atoi(args[2]);
    ofstream file;
    file.open(args[3]);
    // file << n << " " << m << endl;
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < m; j++)
        {
            file << rand() % 100 << " ";
        }
        file << endl;
    }
    file.close();
    return 0;
}