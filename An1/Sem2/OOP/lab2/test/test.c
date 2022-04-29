#include "test.h"
void runTests()
{
    testRepoCreate();
    testRepoAdd();
    testRepoAddMany();
    testRepoUpdate();
    testRepoRemove();
    testRepoFilterScore();
    testRepoFilterName();
    testRepoSortScore();
    testRepoSortName();

    testServiceAdd();
    testServiceUpdate();
    testServiceRemove();
    testServiceFilterScore();
    testServiceFilterName();
    testServiceSortScore();
    testServiceSortName();

    testValidCmd();
    testValidStrIsInt();
    testValidCheckNl();
    testValidScore();

    printf("\n\nTests ran successfully!\n\n");
}

void testRepoCreate()
{
    participantList *pList = repoCreate();
    assert(pList != NULL);
    testPurgeRepo(pList);
}
void testRepoAdd()
{
    participantList *pList = repoCreate();
    // char *firstName = (char *)malloc(10);
    // strcpy(firstName, "Cosmin");
    // char *lastName = (char *)malloc(10);
    // strcpy(lastName, "V");
    char firstName[] = "Cosmin";
    char lastName[] = "V";
    int score = 50;
    repoAdd(pList, firstName, lastName, score);
    assert(strcmp(getFirstName(&((*pList).participants[0])), firstName) == 0);
    assert(strcmp(getLastName(&((*pList).participants[0])), lastName) == 0);
    assert(getScore(&((*pList).participants[0])) == score);
    testPurgeRepo(pList);
}
void testRepoAddMany()
{
    participantList *pList = repoCreate();
    char firstName[] = "Cosmin";
    char lastName[] = "V";
    int score = 50;
    for (int i = 0; i < 100; i++)
    {
        repoAdd(pList, firstName, lastName, score);
    }
    assert(repoAdd(pList, firstName, lastName, score) == 0);
    testPurgeRepo(pList);
}
void testRepoUpdate()
{
    participantList *pList = repoCreate();
    char firstName[] = "Cosmin";
    char lastName[] = "V";
    int score = 50;
    repoAdd(pList, firstName, lastName, score);
    char newFirstName[] = "Marian";
    repoUpdate(pList, lastName, newFirstName, lastName, score);
    assert(repoUpdate(pList, "Lol", newFirstName, lastName, score) == 1);
    assert(strcmp(getFirstName(&(*pList).participants[0]), newFirstName) == 0);
    testPurgeRepo(pList);
}
void testRepoRemove()
{
    participantList *pList = repoCreate();
    char firstName[] = "Cosmin";
    char lastName[] = "V";
    int score = 50;
    assert(pList->length == 0);
    repoAdd(pList, firstName, lastName, score);
    repoAdd(pList, "Cosmin", "Voinopol", 60);
    repoAdd(pList, "A", "M", 60);
    assert(pList->length == 3);
    repoRemove(pList, lastName);
    assert(pList->length == 2);
    assert(repoRemove(pList, "Lol") == 1);

    testPurgeRepo(pList);
}
void testRepoFilterScore()
{
    participantList *pList = repoCreate();
    repoAdd(pList, "C", "V", 1);
    repoAdd(pList, "A", "M", 5);
    participantList *pListFilter1 = repoFilterScore(pList, 0);
    assert(pListFilter1->length == 0);
    participantList *pListFilter2 = repoFilterScore(pList, 2);
    assert(pListFilter2->length == 1);
    assert(strcmp(getLastName(&(pListFilter2->participants[0])), "V") == 0);
    testPurgeRepo(pList);
    testPurgeRepo(pListFilter1);
    testPurgeRepo(pListFilter2);
}
void testRepoFilterName()
{
    participantList *pList = repoCreate();
    repoAdd(pList, "C", "V", 1);
    repoAdd(pList, "A", "M", 5);
    participantList *pListFilter1 = repoFilterName(pList, 'A');
    assert(pListFilter1->length == 0);
    participantList *pListFilter2 = repoFilterName(pList, 'V');
    assert(pListFilter2->length == 1);
    assert(strcmp(getLastName(&(pListFilter2->participants[0])), "V") == 0);
    testPurgeRepo(pListFilter1);
    testPurgeRepo(pListFilter2);
    testPurgeRepo(pList);
}
void testRepoSortScore()
{
    participantList *pList = repoCreate();
    repoAdd(pList, "C", "V", 1);
    repoAdd(pList, "A", "M", 5);
    participantList *pListSort1 = repoSortScore(pList, 1);
    assert(pListSort1->length == 2);
    assert(strcmp(getLastName(&(pListSort1->participants[0])), "V") == 0);
    participantList *pListSort2 = repoSortScore(pList, -1);
    assert(pListSort2->length == 2);
    assert(strcmp(getLastName(&(pListSort2->participants[0])), "M") == 0);
    testPurgeRepo(pListSort1);
    testPurgeRepo(pListSort2);
    testPurgeRepo(pList);
}
void testRepoSortName()
{
    participantList *pList = repoCreate();
    repoAdd(pList, "C", "V", 1);
    repoAdd(pList, "A", "M", 5);
    participantList *pListSort1 = repoSortName(pList, 1);
    assert(pListSort1->length == 2);
    assert(strcmp(getLastName(&(pListSort1->participants[0])), "M") == 0);
    participantList *pListSort2 = repoSortName(pList, -1);
    assert(pListSort2->length == 2);
    assert(strcmp(getLastName(&(pListSort2->participants[0])), "V") == 0);
    testPurgeRepo(pListSort1);
    testPurgeRepo(pListSort2);
    testPurgeRepo(pList);
}

void testServiceAdd()
{
    participantList *pList = repoCreate();
    char firstName[] = "Cosmin";
    char lastName[] = "V";
    int score = 50;
    serviceAdd(pList, firstName, lastName, score);
    assert(strcmp(getFirstName(&((*pList).participants[0])), firstName) == 0);
    assert(strcmp(getLastName(&((*pList).participants[0])), lastName) == 0);
    assert(getScore(&((*pList).participants[0])) == score);
    testPurgeRepo(pList);
}
void testServiceUpdate()
{
    participantList *pList = repoCreate();
    char firstName[] = "Cosmin";
    char lastName[] = "V";
    int score = 50;
    serviceAdd(pList, firstName, lastName, score);
    char newFirstName[] = "Marian";
    serviceUpdate(pList, lastName, newFirstName, lastName, score);
    assert(strcmp(getFirstName(&((*pList).participants[0])), newFirstName) == 0);
    testPurgeRepo(pList);
}
void testServiceRemove()
{
    participantList *pList = repoCreate();
    char firstName[] = "Cosmin";
    char lastName[] = "V";
    int score = 50;
    assert((*pList).length == 0);
    serviceAdd(pList, firstName, lastName, score);
    assert((*pList).length == 1);
    serviceRemove(pList, lastName);
    assert((*pList).length == 0);
    testPurgeRepo(pList);
}
void testServiceFilterScore()
{
    participantList *pList = repoCreate();
    repoAdd(pList, "C", "V", 1);
    repoAdd(pList, "A", "M", 5);
    participantList *pListFilter1 = serviceFilterScore(pList, 0);
    assert(pListFilter1->length == 0);
    participantList *pListFilter2 = serviceFilterScore(pList, 2);
    assert(pListFilter2->length == 1);
    assert(strcmp(getLastName(&(pListFilter2->participants[0])), "V") == 0);
    testPurgeRepo(pList);
    testPurgeRepo(pListFilter1);
    testPurgeRepo(pListFilter2);
}
void testServiceFilterName()
{
    participantList *pList = repoCreate();
    repoAdd(pList, "C", "V", 1);
    repoAdd(pList, "A", "M", 5);
    participantList *pListFilter1 = serviceFilterName(pList, 'A');
    assert(pListFilter1->length == 0);
    participantList *pListFilter2 = serviceFilterName(pList, 'V');
    assert(pListFilter2->length == 1);
    assert(strcmp(getLastName(&(pListFilter2->participants[0])), "V") == 0);
    testPurgeRepo(pListFilter1);
    testPurgeRepo(pListFilter2);
    testPurgeRepo(pList);
}
void testServiceSortScore()
{
    participantList *pList = repoCreate();
    repoAdd(pList, "C", "V", 1);
    repoAdd(pList, "A", "M", 5);
    participantList *pListSort1 = serviceSortScore(pList, 1);
    assert(pListSort1->length == 2);
    assert(strcmp(getLastName(&(pListSort1->participants[0])), "V") == 0);
    participantList *pListSort2 = serviceSortScore(pList, -1);
    assert(pListSort2->length == 2);
    assert(strcmp(getLastName(&(pListSort2->participants[0])), "M") == 0);
    testPurgeRepo(pListSort1);
    testPurgeRepo(pListSort2);
    testPurgeRepo(pList);
}
void testServiceSortName()
{
    participantList *pList = repoCreate();
    repoAdd(pList, "C", "V", 1);
    repoAdd(pList, "A", "M", 5);
    participantList *pListSort1 = serviceSortName(pList, 1);
    assert(pListSort1->length == 2);
    assert(strcmp(getLastName(&(pListSort1->participants[0])), "M") == 0);
    participantList *pListSort2 = serviceSortName(pList, -1);
    assert(pListSort2->length == 2);
    assert(strcmp(getLastName(&(pListSort2->participants[0])), "V") == 0);
    testPurgeRepo(pListSort1);
    testPurgeRepo(pListSort2);
    testPurgeRepo(pList);
}
void testPurgeRepo(participantList *pList)
{
    repoClearRepo(&pList);
    assert(pList == NULL);
}

void testValidCmd()
{
    assert(validCmd("0") == 1);
    assert(validCmd("10") == 0);
}
void testValidStrIsInt()
{
    assert(validStrIsInt("123") == 1);
    assert(validStrIsInt("12n") == 0);
    assert(validStrIsInt("") == 0);
}
void testValidCheckNl()
{
    assert(validCheckNl("fj\n") == 1);
    assert(validCheckNl("fea") == 0);
}
void testValidScore()
{
    assert(validScore("99") == 1);
    assert(validScore("101") == 0);
}