#ifndef SERVICE_H_
#define SERVICE_H_
#include "../repo/repo.h"

/**
 * @brief Service add
 *
 * @param pList
 * @param firstName
 * @param lastName
 * @param score
 * @return int 1-had an error; 0-went good
 */
int serviceAdd(participantList *pList, char *firstName, char *lastName, int score);
/**
 * @brief Service update
 *
 * @param pList
 * @param lastName
 * @param newFirstName
 * @param newLastName
 * @param newScore
 * @return int 1-had an error; 0-went good
 */
int serviceUpdate(participantList *pList, char *lastName, char *newFirstName, char *newLastName, int newScore);
/**
 * @brief Service remove
 *
 * @param pList
 * @param lastName
 * @return int 1-had an error; 0-went good
 */
int serviceRemove(participantList *pList, char *lastName);

/**
 * @brief Service filter by score asc/desc
 *
 * @param pList
 * @param scoreFilter asc/desc
 * @return participantList* - a filtered array
 */
participantList *serviceFilterScore(participantList *pList, int scoreFilter);
/**
 * @brief Service filter by name asc/desc
 *
 * @param pList
 * @param lastNameLetter First letter of last name
 * @return participantList* - a filtered array
 */
participantList *serviceFilterName(participantList *pList, char lastNameLetter);

/**
 * @brief Service sort by score asc/desc
 *
 * @param pList
 * @param order asc=1/desc=-1
 * @return participantList* - a sorted array
 */
participantList *serviceSortName(participantList *pList, int order);
/**
 * @brief Service sort by score
 *
 * @param pList
 * @param order asc=1/desc=-1
 * @return participantList* - a sorted array
 */
participantList *serviceSortScore(participantList *pList, int order);
/**
 * @brief Service clear repo
 *
 * @param pList - repo to clear
 */
void serviceClearRepo(participantList *pList);
#endif