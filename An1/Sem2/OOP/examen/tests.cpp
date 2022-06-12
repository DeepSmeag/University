#include "tests.h"
using namespace std;
void Tests::runTests()
{
    test2();
    test3();
    // test4();
    test5();
    // all good
}
void Tests::test2()
{
    Service service("ftest.in");
    std::string id("10"), dim("2"), tabla("----"), jucator("X"), stare("Neinceput");
    service.addGame(id, dim, tabla, jucator, stare);
    vector<XO> games = service.getGames();
    bool ok = false;
    for (auto &it : games)
    {
        if (it.getId() == "10")
        {
            ok = true;
        }
    }
    assert(ok);
    service.storeFile();
    service.addGame("1", "", "", "", "");
    service.addGame("2", "", "", "", "");
    service.addGame("3", "", "", "", "");
    service.addGame("4", "", "", "", "");
    service.modifyGame("4", "", "", "", "");
    service.modifyGame("5", "", "", "", "");
    // ofstream g("ftest.in");
    // g << "";
}
void Tests::test3()
{
    Service service("ftest.in");
    Valid valid;
    service.addGame("10", "2", "----", "X", "Inceput");
    service.addGame("4", "2", "----", "X", "Inceput");
    service.modifyGame("10", "2", "--X-", "X", "Inceput");
    assert(valid.checkAll("3", "---------", "X", "fals") == 0);
    assert(valid.checkAll("3", "---------", "X", "Terminat") == 1);
    assert(valid.checkAll("3", "---------", "fals", "In derulare") == 0);
    assert(valid.checkAll("3", "---A-----", "X", "In derulare") == 0);
    assert(valid.checkAll("2", "---------", "X", "In derulare") == 0);
    assert(valid.checkAll("3", "---------", "X", "fals") == 0);
    assert(valid.checkAll("4", "---------", "fals", "In derulare") == 0);
    assert(valid.checkAll("5", "---A-----", "X", "In derulare") == 0);
    assert(valid.checkAll("2", "-------", "X", "In derulare") == 0);
    valid.checkAll("2", "--X-", "X", "Inceput");
    // ofstream g("ftest.in");
    // g << "";
}

void Tests::test5()
{
    Service service("ftest.in");
}