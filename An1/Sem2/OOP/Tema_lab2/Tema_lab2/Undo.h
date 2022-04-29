#ifndef _UNDO_H
#define _UNDO_H
#include "Repository.h"
#include <stdlib.h>
typedef struct
{
    void *elems;
    int dim;
    int capacity;
} vectDinamic;

// Istoricul mare (lista care contine micile liste de iteratii de repo)
typedef vectDinamic undoHistory;

typedef struct
{
    undoHistory *undoH;
    Repository *repo;
} undoStruct;
typedef struct
{
    int curent;
} iterator;

/**
 * @brief Creates undo struct object
 *
 * @param repo
 * @return undoStruct*
 */
undoStruct *undoCreateHistory(Repository *repo);
/**
 * @brief Boolean function that returns whether history is empty
 *
 * @param undoH
 * @return int 0/1
 */

int undoIsEmpty(undoHistory *undoH);
/**
 * @brief "Saves" the current state of repo, before it changes
 *
 * @param undoH
 * @param repo
 */
void undoAdd(undoHistory *undoH, Repository *repo);
/**
 * @brief Deletes the last entry in the history (used after restoring repo to previous state)
 *
 * @param undoH
 * @return int
 */
int undoDelete(undoHistory *undoH);
/**
 * @brief Restores repo to previous state, if any
 *
 * @param undoH
 * @param repo
 * @return int
 */
int undoRestoreRepo(undoHistory *undoH, Repository *repo);

/**
 * @brief Resize undo if current capacity is maxed out
 *
 * @param undoH
 */
void undoRedim(undoHistory *undoH);
/**
 * @brief Copies repo r2 into repo r1
 *
 * @param r1
 * @param r2
 */
void undoCopy(Repository *r1, Repository *r2);
/**
 * @brief Clears the elements of the undo history
 *
 * @param undoH
 */

void undoClearElems(undoHistory *undoH);
/**
 * @brief Deletes the whole undo history, used when exiting the application
 *
 * @param undoStruct
 */
void undoPurge(undoStruct *undoStruct);
#endif