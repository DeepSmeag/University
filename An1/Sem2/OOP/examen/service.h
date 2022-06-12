#pragma once
#include <iostream>
#include "repo.h"

class Service
{
private:
    Repo repo;

public:
    Service(std::string filename) : repo{filename} {}

    std::vector<XO> getGames();
    // daugare joc
    void addGame(std::string id, std::string dim, std::string tabla, std::string jucator, std::string stare);
    // modificare joc
    void modifyGame(std::string id, std::string dim, std::string tabla, std::string jucator, std::string stare);
    // store file
    void storeFile();
};