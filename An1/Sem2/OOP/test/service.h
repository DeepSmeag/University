#include "repo.h"
#include <algorithm>
class Service
{
private:
    Repo repo;

public:
    // comentarii in fisierul cpp
    Service(string fileName) : repo(fileName){};
    vector<Device> serviceSortPret();
    vector<Device> serviceSortModel();
    vector<Device> serviceGetDevices();
};