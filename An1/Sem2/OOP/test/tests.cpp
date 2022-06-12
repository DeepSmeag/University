#include "tests.h"
using namespace std;
// functie mare de rulare teste
void Tests::run()
{
    testCreation();

    testSortPret();
    testSortModel();
    testOpenFail();
    cout << "Tests ran successfully!\n";
}
// testam crearea, adica incarcarea
void Tests::testCreation()
{
    Service service("testdevices.txt");
    vector<Device> devices = service.serviceGetDevices();
    assert(devices.size() == 10);
    for (auto &it : devices)
    {
        it.getAn();
        it.getCuloare();
        it.getModel();
        it.getPret();
        it.getTip();
    }
}
// testam sortarea dupa pret
void Tests::testSortPret()
{
    Service service("testdevices.txt");
    auto vect = service.serviceSortPret();
    assert(vect.size() == 10);
}
// testam sortarea dupa model
void Tests::testSortModel()
{
    Service service("testdevices.txt");
    auto vect = service.serviceSortModel();
    assert(vect.size() == 10);
}
// in caz ca fisierul nu poate fi deschis
void Tests::testOpenFail()
{
    Service service("lfalla.txt");
}