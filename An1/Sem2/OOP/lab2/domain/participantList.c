#include "participantList.h"

participantList *initList()
{
    participantList *pList;
    pList = (participantList *)malloc(sizeof(participantList));
    initLength(pList);
    pList->capacity = 16;
    pList->participants = (participant *)malloc(pList->capacity * sizeof(participant));
    return pList;
}
void addParticipant(participantList *pList, participant *pp)
{
    setFirstName((&(pList->participants[pList->length])), pp->firstName);
    setLastName((&(pList->participants[pList->length])), pp->lastName);
    setScore((&(pList->participants[pList->length])), pp->score);
    incrementLength(pList);
}
int updateParticipant(participantList *pList, char *lastName, char *newFirstName, char *newLastName, int newScore)
{

    participant *pp = searchParticipantByName(pList, lastName);
    if (pp == NULL)
    {
        return 1;
    }
    clearParticipant(pp);
    setFirstName(pp, newFirstName);
    setLastName(pp, newLastName);
    setScore(pp, newScore);
    return 0;
}
// void removeParticipant(participantList *pList, char *lastName)
// {
//     participant *pp = searchParticipantByName(pList, lastName);
//     clearParticipant(pp);
// }
participant *searchParticipantByName(participantList *pList, char *lastName)
{
    participant *pp = NULL;
    for (int i = 0; i < pList->length; i++)
    {
        if (strcmp(getLastName(&(pList->participants[i])), lastName) == 0)
        {
            pp = &(pList->participants[i]);
        }
    }
    return pp;
}
int searchParticipantIndex(participantList *pList, char *lastName)
{
    for (int i = 0; i < pList->length; i++)
    {
        if (strcmp(getLastName(&(pList->participants[i])), lastName) == 0)
        {
            return i;
        }
    }
    return -1;
}

void incrementLength(participantList *pList)
{
    pList->length++;
}
void decrementLength(participantList *pList)
{
    pList->length--;
}
void initLength(participantList *pList)
{
    pList->length = 0;
}
void initCapacity(participantList *pList)
{
    pList->capacity = 16;
}
void doubleCapacity(participantList *pList)
{
    pList->capacity = pList->capacity * 2;
    participant *newParticipants = (participant *)malloc(pList->capacity * sizeof(participant));
    for (int i = 0; i < pList->length; i++)
    {
        newParticipants[i] = pList->participants[i];
    }
    free(pList->participants);
    pList->participants = newParticipants;
}