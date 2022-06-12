#include "repo.h"
using namespace std;
// constructor repo
Repo::Repo(string fileName) : devices()
{
    loadFile(fileName);
}
// incarcare din fisier in memorie
void Repo::loadFile(string fileName)
{
    ifstream f(fileName);

    if (!f.is_open())
    {
        return;
    }
    string tip, model, an, culoare, pret;
    while (f >> tip >> model >> an >> culoare >> pret)
    {
        // cout << tip << model << an << culoare << pret << "\n";
        Device device(tip, model, an, culoare, pret);
        devices.push_back(device);
    }
    f.close();
}
// return devices ca sa lucram cu ele
vector<Device> Repo::repoGetDevices()
{
    return devices;
}