#include <string>

class XO
{
private:
    std::string tabla;
    std::string id, dim;
    std::string jucator, stare;

public:
    XO(std::string id, std::string dim, std::string tabla, std::string jucator, std::string stare) : id{id}, dim{dim}, tabla{tabla}, jucator{jucator}, stare{stare} {}
    // getter id
    std::string getId()
    {
        return id;
    }
    // getter tabla
    std::string getTabla()
    {
        return tabla;
    }
    // getter dim
    std::string getDim()
    {
        return dim;
    }
    // getter jcuator
    std::string getJucator()
    {
        return jucator;
    }
    // getter stare
    std::string getStare()
    {
        return stare;
    }
};