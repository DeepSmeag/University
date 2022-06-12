#include "service.h"
using namespace std;
// service pt retur devices
vector<Device> Service::serviceGetDevices()
{
    return repo.repoGetDevices();
}
// functie utilitara de conversie la int
int getPrice(string s)
{
    const int base = 10;
    int price = 0;
    for (auto &it : s)
    {
        price += (it - '0');
        price *= base;
    }
    return price / base;
}
// service sortam dupa pret
vector<Device> Service::serviceSortPret()
{

    vector<Device> vect = repo.repoGetDevices();
    sort(vect.begin(), vect.end(), [&](Device d1, Device d2)
         { return getPrice(d1.getPret()) < getPrice(d2.getPret()); });
    return vect;
}
// service sortam dupa model
vector<Device> Service::serviceSortModel()
{
    vector<Device> vect = repo.repoGetDevices();
    sort(
        vect.begin(), vect.end(), [&](Device d1, Device d2)
        { return d1.getModel() < d2.getModel(); });
    return vect;
}