#include "service.h"

std::vector<XO> Service::getGames()
{
    return repo.getGames();
}
void Service::addGame(std::string id, std::string dim, std::string tabla, std::string jucator, std::string stare)
{
    XO game(id, dim, tabla, jucator, stare);
    repo.addGame(game);
    repo.storeFile();
}
void Service::storeFile()
{
    repo.storeFile();
}
void Service::modifyGame(std::string id, std::string dim, std::string tabla, std::string jucator, std::string stare)
{
    XO game(id, dim, tabla, jucator, stare);
    int i = repo.findGame(id);
    if (i < 0)
    {
        // fail, nu a fost gasit
    }
    else
    {
        repo.getGames().at(i) = game;
        repo.storeFile();
    }
}