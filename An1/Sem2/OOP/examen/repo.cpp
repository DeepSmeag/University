#include "repo.h"
using namespace std;
void Repo::loadFile()
{
    std::ifstream f(filename);
    std::string tabla, jucator, stare;
    std::string id, dim;
    while (f >> id >> dim >> tabla >> jucator >> stare)
    {
        if (stare == "In")
        {
            string tmp;
            f >> tmp;
            stare = stare + " " + tmp;
        }
        XO game(id, dim, tabla, jucator, stare);
        games.push_back(game);
    }
    f.close();
}
void Repo::storeFile()
{
    std::ofstream g(filename);
    for (auto &it : games)
    {
        g << it.getId() << " " << it.getDim() << " " << it.getTabla() << " " << it.getJucator() << " " << it.getStare() << endl;
    }
    g.close();
}
std::vector<XO> &Repo::getGames()
{
    return games;
}
void Repo::addGame(XO game)
{
    games.push_back(game);
}
int Repo::findGame(std::string id)
{
    int i = 0;
    for (auto &it : games)
    {
        if (it.getId() == id)
        {
            return i;
        }
        i++;
    }
    return -1;
}