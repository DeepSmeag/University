#include "domain.h"
#include <vector>
#include <fstream>
class Repo
{
private:
    std::vector<XO> games;
    std::string filename;

public:
    Repo(std::string filename) : filename{filename}
    {
        loadFile();
    }
    // incarcare din fisier
    void loadFile();
    // incarcare in fisier
    void storeFile();
    std::vector<XO> &getGames();
    // adaugare joc
    void addGame(XO game);
    // modificare jhoc
    void modifyGame(XO &game);
    int findGame(std::string id);
};