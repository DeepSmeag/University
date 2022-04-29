#ifndef PARTICIPANTLIST_H_
#define PARTICIPANTLIST_H_
#include <string.h>
#include "participant.h"
typedef struct
{
    participant *participants;
    int length;
    int capacity;
} participantList;
participantList *initList();
/**
 * @brief Add a participant in repo
 *
 * @param pList
 * @param pp
 */
void addParticipant(participantList *pList, participant *pp);
/**
 * @brief Update a participant in repo
 *
 * @param pList
 * @param lastName
 * @param newFirstName
 * @param newLastName
 * @param newScore
 * @return int 1- error, 0 -success
 */
int updateParticipant(participantList *pList, char *lastName, char *newFirstName, char *newLastName, int newScore);
/**
 * @brief Clear participant data from repo
 *
 * @param pList
 * @param lastName
 */
void removeParticipant(participantList *pList, char *lastName);
/**
 * @brief Add 1 to repo length
 *
 * @param pList
 */
void incrementLength(participantList *pList);
/**
 * @brief Subtract 1 from repo length
 *
 * @param pList
 */
void decrementLength(participantList *pList);
/**
 * @brief Reset repo length to 0
 *
 * @param pList
 */
void initLength(participantList *pList);
/**
 * @brief Reset repo capacity to 16 (default)
 *
 * @param pList
 */
void initCapacity(participantList *pList);
/**
 * @brief Search participant by lastName
 *
 * @param pList
 * @param lastName
 * @return participant* pointer to found participant (or NULL if not found)
 */
participant *searchParticipantByName(participantList *pList, char *lastName);
/**
 * @brief Find index of searched participant
 *
 * @param pList
 * @param lastName
 * @return int - index
 */
int searchParticipantIndex(participantList *pList, char *lastName);
/**
 * @brief Doubles the capacity of the dynamic vector
 *
 * @param pList
 */
void doubleCapacity(participantList *pList);
#endif