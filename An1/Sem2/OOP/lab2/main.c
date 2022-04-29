#include "ui/ui.h"
#include "repo/repo.h"
#include "test/test.h"
int main()
{
    participantList *pList = repoCreate();
    runTests();
    UiInit(pList);

    return 0;
}
