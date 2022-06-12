#include "valid.h"

int Valid::checkAll(std::string dim, std::string tabla, std::string jucator, std::string stare)
{
    const int int3 = 3;
    const int int4 = 4;
    const int int5 = 5;
    int dimInt = 0;
    if (dim == "3")
    {
        dimInt = int3;
    }
    else if (dim == "4")
    {
        dimInt = int4;
    }
    else if (dim == "5")
    {
        dimInt = int5;
    }
    if (dimInt == 0)
        return 0;
    if (tabla.size() != dimInt * dimInt)
    {
        return 0;
    }
    for (auto it : tabla)
    {
        if (it != 'X' && it != 'O' && it != '-')
        {
            return 0;
        }
    }
    if (jucator != "X" && jucator != "O")
    {
        return 0;
    }
    if (stare == "Neinceput" || stare == "In derulare" || stare == "Terminat")
    {
        return 1;
    }
    return 0;
}