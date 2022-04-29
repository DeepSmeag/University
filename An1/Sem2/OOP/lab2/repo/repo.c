#include "repo.h"

participantList *repoCreate()
{
    return initList();
}

int repoAdd(participantList *pList, char *firstName, char *lastName, int score)
{
    if (pList->length == pList->capacity)
    {
        doubleCapacity(pList);
    }
    participant *pp;
    pp = createParticipant(firstName, lastName, score);
    addParticipant(pList, pp);

    clearParticipant(pp);
    free(pp);
    return 0;
}

int repoUpdate(participantList *pList, char *lastName, char *newFirstName, char *newLastName, int newScore)
{
    return updateParticipant(pList, lastName, newFirstName, newLastName, newScore);
}
int repoRemove(participantList *pList, char *lastName)
{
    int ppIndex = searchParticipantIndex(pList, lastName);
    if (ppIndex == -1)
    {
        return 1;
    }
    repoShiftLeft(pList, ppIndex);
    return 0;
}

participantList *repoFilterScore(participantList *pList, int scoreFilter)
{
    participantList *pListFilterScore = repoCreate();
    for (int i = 0; i < pList->length; i++)
    {
        if (getScore(&(pList->participants[i])) < scoreFilter)
        {
            char *firstName, *lastName;
            int score;
            firstName = getFirstName(&(pList->participants[i]));
            lastName = getLastName(&(pList->participants[i]));
            score = getScore(&(pList->participants[i]));
            repoAdd(pListFilterScore, firstName, lastName, score);
        }
    }
    return pListFilterScore;
}
participantList *repoFilterName(participantList *pList, char lastNameLetter)
{
    participantList *pListFilterName = repoCreate();
    for (int i = 0; i < pList->length; i++)
    {
        char *lastName = getLastName(&(pList->participants[i]));
        if (lastName[0] == lastNameLetter)
        {
            char *firstName;
            int score;
            firstName = getFirstName(&(pList->participants[i]));
            score = getScore(&(pList->participants[i]));
            repoAdd(pListFilterName, firstName, lastName, score);
        }
    }
    return pListFilterName;
}

participantList *repoSortName(participantList *pList, int order)
{
    participantList *pListSortName = repoCreate();
    for (int i = 0; i < pList->length; i++)
    {
        char *firstName;
        char *lastName;
        int score;
        firstName = getFirstName(&(pList->participants[i]));
        lastName = getLastName(&(pList->participants[i]));
        score = getScore(&(pList->participants[i]));
        repoAdd(pListSortName, firstName, lastName, score);
    }
    for (int i = 0; i < pList->length - 1; i++)
    {
        for (int j = i + 1; j < pList->length; j++)
        {
            participant *pp1 = &(pListSortName->participants[i]);
            participant *pp2 = &(pListSortName->participants[j]);
            char *lastName1 = getLastName(pp1);
            char *lastName2 = getLastName(pp2);
            if (strcmp(lastName1, lastName2) * order > 0)
            {
                repoSwapParticipants(pp1, pp2);
            }
        }
    }
    return pListSortName;
}
participantList *repoSortScore(participantList *pList, int order)
{
    participantList *pListSortScore = repoCreate();
    for (int i = 0; i < pList->length; i++)
    {
        char *firstName;
        char *lastName;
        int score;
        firstName = getFirstName(&(pList->participants[i]));
        lastName = getLastName(&(pList->participants[i]));
        score = getScore(&(pList->participants[i]));
        repoAdd(pListSortScore, firstName, lastName, score);
    }
    for (int i = 0; i < pList->length - 1; i++)
    {
        for (int j = i + 1; j < pList->length; j++)
        {
            participant *pp1 = &(pListSortScore->participants[i]);
            participant *pp2 = &(pListSortScore->participants[j]);
            if (getScore(pp1) * order > getScore(pp2) * order)
            {
                repoSwapParticipants(pp1, pp2);
            }
        }
    }

    return pListSortScore;
}

int cmpName(participant *pp1, participant *pp2)
{
    return strcmp(getLastName(pp1), getLastName(pp2));
}
int cmpScore(participant *pp1, participant *pp2)
{
    return getScore(pp1) > getScore(pp2) ? 1 : -1;
}
// int cmpMethod(char *method, participant *pp1, participant *pp2)
// {
//     if (strcmp(method, "name") == 0)
//     {
//         return cmpName(pp1, pp2);
//     }
//     else if (strcmp(method, "score") == 0)
//     {
//         return cmpScore(pp1, pp2);
//     }
//     else
//     {
//         return 0;
//     }
// }
participantList *repoSortMethod(participantList *pList, int (*cmpMethod)(participant *pp1, participant *pp2), int order)
{
    participantList *pListSortScore = repoCreate();
    for (int i = 0; i < pList->length; i++)
    {
        char *firstName;
        char *lastName;
        int score;
        firstName = getFirstName(&(pList->participants[i]));
        lastName = getLastName(&(pList->participants[i]));
        score = getScore(&(pList->participants[i]));
        repoAdd(pListSortScore, firstName, lastName, score);
    }
    for (int i = 0; i < pList->length - 1; i++)
    {
        for (int j = i + 1; j < pList->length; j++)
        {
            participant *pp1 = &(pListSortScore->participants[i]);
            participant *pp2 = &(pListSortScore->participants[j]);
            if (cmpMethod(pp1, pp2) * order > 0)
            {
                repoSwapParticipants(pp1, pp2);
            }
        }
    }

    return pListSortScore;
}
// participant *repoSearchByName(participantList *pList, char *lastName)
// {
//     return searchParticipantByName(pList, lastName);
// }
void repoShiftLeft(participantList *pList, int startPos)
{
    participant *ppStart = &pList->participants[startPos];
    clearParticipant(ppStart);
    for (int i = startPos + 1; i < pList->length; i++)
    {
        participant *pp = &pList->participants[i];

        setFirstName(&(*pList).participants[i - 1], getFirstName(&(*pList).participants[i]));
        setLastName(&(*pList).participants[i - 1], getLastName(&(*pList).participants[i]));
        setScore(&(*pList).participants[i - 1], getScore(&(*pList).participants[i]));
        clearParticipant(pp);
    }
    decrementLength(pList);
}

void repoSwapParticipants(participant *pp1, participant *pp2)
{
    char *firstName, *lastName;
    int score;
    firstName = (char *)malloc(sizeof(char) * strlen(pp1->firstName) + 1);
    lastName = (char *)malloc(sizeof(char) * strlen(pp1->lastName) + 1);
    strcpy(firstName, getFirstName(pp1));
    strcpy(lastName, getLastName(pp1));
    score = getScore(pp1);
    clearParticipant(pp1);
    setFirstName(pp1, getFirstName(pp2));
    setLastName(pp1, getLastName(pp2));
    setScore(pp1, getScore(pp2));
    clearParticipant(pp2);
    setFirstName(pp2, firstName);
    setLastName(pp2, lastName);
    setScore(pp2, score);

    free(firstName);
    free(lastName);
}

void repoClearRepo(participantList **pList)
{
    for (int i = 0; i < (*pList)->length; i++)
    {
        participant *pp = &((*pList)->participants[i]);
        clearParticipant(pp);
    }
    initLength(*pList);
    initCapacity(*pList);
    free((*pList)->participants);
    free(*pList);
    *pList = NULL;
}