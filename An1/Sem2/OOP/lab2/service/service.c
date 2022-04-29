#include "service.h"

int serviceAdd(participantList *pList, char *firstName, char *lastName, int score)
{
    return repoAdd(pList, firstName, lastName, score);
}
int serviceUpdate(participantList *pList, char *lastName, char *newFirstName, char *newLastName, int newScore)
{
    return repoUpdate(pList, lastName, newFirstName, newLastName, newScore);
}
int serviceRemove(participantList *pList, char *lastName)
{
    return repoRemove(pList, lastName);
}
participantList *serviceFilterScore(participantList *pList, int scoreFilter)
{
    return repoFilterScore(pList, scoreFilter);
}
participantList *serviceFilterName(participantList *pList, char lastNameLetter)
{
    return repoFilterName(pList, lastNameLetter);
}

participantList *serviceSortName(participantList *pList, int order)
{
    return repoSortMethod(pList, cmpName, order);
}
participantList *serviceSortScore(participantList *pList, int order)
{
    return repoSortMethod(pList, cmpScore, order);
}

void serviceClearRepo(participantList *pList)
{
    repoClearRepo(&pList);
}
// participant *serviceGetAll(participantList pList)
// {
//     participant *list = repoGetAll(pList);
//     return list;
// }