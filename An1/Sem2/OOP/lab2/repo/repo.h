#ifndef REPO_H_
#define REPO_H_
#include <stdlib.h>
#include "../domain/participantList.h"

/**
 * @brief Create a repo
 *
 * @return participantList* - a fresh repo
 */
participantList *repoCreate();
/**
 * @brief Add participant to repo
 *
 * @param pList
 * @param firstName
 * @param lastName
 * @param score
 * @return int 1-error;0-good
 */
int repoAdd(participantList *pList, char *firstName, char *lastName, int score);
/**
 * @brief Update participant in repo
 *
 * @param pList
 * @param lastName
 * @param newFirstName
 * @param newLastName
 * @param newScore
 * @return int 1-error;0-good
 */
int repoUpdate(participantList *pList, char *lastName, char *newFirstName, char *newLastName, int newScore);
/**
 * @brief Remove participant from repo
 *
 * @param pList
 * @param lastName
 * @return int 1-error;0-good
 */
int repoRemove(participantList *pList, char *lastName);

/**
 * @brief Filter repo by score
 *
 * @param pList
 * @param scoreFilter - score to filter by
 * @return participantList* - filtered array
 */
participantList *repoFilterScore(participantList *pList, int scoreFilter);
/**
 * @brief Filter repo by name
 *
 * @param pList
 * @param lastNameLetter first letter of lastName
 * @return participantList* - filtered array
 */
participantList *repoFilterName(participantList *pList, char lastNameLetter);

/**
 * @brief Sort repo by name
 *
 * @param pList
 * @param order asc=1,desc=-1
 * @return participantList* - sorted array
 */
participantList *repoSortName(participantList *pList, int order);
/**
 * @brief Sort repo by score
 *
 * @param pList
 * @param order asc=1,desc=-1
 * @return participantList* - a sorted arrat
 */
participantList *repoSortScore(participantList *pList, int order);
/**
 * @brief Search the lastName in the repo
 *
 * @param pList
 * @param lastName
 * @return participant* -pointer to found participant (or NULL if not found)
 */
participant *repoSearchByName(participantList *pList, char *lastName);
/**
 * @brief Shift repo to left to cover holes from removing
 *
 * @param pList
 * @param startPos pos of removed participant
 */
void repoShiftLeft(participantList *pList, int startPos);
/**
 * @brief Clear the repo and free memory
 *
 * @param pList - pointer to repo to clear
 */
void repoClearRepo(participantList **pList);
/**
 * @brief Swap participants in repo, used in sorting
 *
 * @param pp1
 * @param pp2
 */
void repoSwapParticipants(participant *pp1, participant *pp2);
// participant *repoGetAll(participantList pList);
/**
 * @brief Sort depending on ui chosen method
 *
 * @param pList
 * @param cmpMethod
 * @param method
 * @param order
 * @return participantList*
 */
participantList *repoSortMethod(participantList *pList, int (*cmpMethod)(participant *pp1, participant *pp2), int order);
/**
 * @brief Cmp function by name
 *
 * @param pp1
 * @param pp2
 * @return int
 */
int cmpName(participant *pp1, participant *pp2);
/**
 * @brief Cmp function by score
 *
 * @param pp1
 * @param pp2
 * @return int
 */
int cmpScore(participant *pp1, participant *pp2);
#endif