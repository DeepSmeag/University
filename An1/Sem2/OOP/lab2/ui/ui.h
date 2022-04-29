#ifndef UI_H_
#define UI_H_
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "../repo/repo.h"
/**
 * @brief Initiate UI and main menu
 *
 * @param pList
 */
void UiInit(participantList *pList);
/**
 * @brief UI that gets input to add to repo and calls service add
 *
 * @param pList
 * @return int 1- had an error; 0- went good
 */
int uiAdd(participantList *pList);
/**
 * @brief UI to get input and call service update
 *
 * @param pList
 * @return int 1- had an error; 0- went all good
 */
int uiUpdate(participantList *pList);
/**
 * @brief UI to get input and call service remove
 *
 * @param pList
 * @return int 1- had an error ; 0-went good
 */
int uiRemove(participantList *pList);
/**
 * @brief UI to get input and call service filter (with option)
 *
 * @param pList
 * @return int 1- had an error ; 0-went good
 */
int uiFilter(participantList *pList);
/**
 * @brief UI to get input and call service sort (with option)
 *
 * @param pList
 * @return int 1- had an error ; 0-went good
 */
int uiSort(participantList *pList);
/**
 * @brief Utility to print repo objects
 *
 * @param pList
 */
void uiPrint(participantList *pList);
/**
 * @brief UI to call Valid and check whether given cmd is valid
 *
 * @param cmd
 * @return int 1- is valid; 0-not valid
 */
int uiCheckCmd(char *cmd);
/**
 * @brief Utility to clean newline character from string (if it has it)
 *
 * @param str
 */
void uiCleanStr(char *str);

#endif