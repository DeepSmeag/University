#include "domain.h"
#include <fstream>
#include <iostream>
#include <vector>
#include <assert.h>
class Repo
{
private:
    vector<Device> devices;

public:
    // comentarii in fisierul cpp
    Repo(string fileName);

    vector<Device> repoGetDevices();
    void loadFile(string filename);
};