#ifndef TEST_H_
#define TEST_H_
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "../service/service.h"
#include "../repo/repo.h"
#include "../domain/participant.h"
#include "../domain/participantList.h"
#include "../valid/valid.h"
/**
 * @brief The main function that calls all tests
 *
 */
void runTests();
/**
 * @brief Testing repo create
 *
 */
void testRepoCreate();
/**
 * @brief Testing repo add
 *
 */
void testRepoAdd();
/**
 * @brief Testing repo add case with overflow of objects
 *
 */
void testRepoAddMany();
/**
 * @brief Testing repo update
 *
 */
void testRepoUpdate();
/**
 * @brief Testing repo remove
 *
 */
void testRepoRemove();
/**
 * @brief Testing repo filter by score
 *
 */
void testRepoFilterScore();
/**
 * @brief Testing repo filter by name
 *
 */
void testRepoFilterName();
/**
 * @brief Testing repo sort by score
 *
 */
void testRepoSortScore();
/**
 * @brief Testing repo sort by name
 *
 */
void testRepoSortName();

/**
 * @brief Testing service add
 *
 */
void testServiceAdd();
/**
 * @brief Testing service update
 *
 */
void testServiceUpdate();
/**
 * @brief Testing service remove
 *
 */
void testServiceRemove();
/**
 * @brief Testing service filter by score
 *
 */
void testServiceFilterScore();
/**
 * @brief Testing service filter by name
 *
 */
void testServiceFilterName();
/**
 * @brief Testing service sort by score
 *
 */
void testServiceSortScore();
/**
 * @brief Testing service sort by name
 *
 */
void testServiceSortName();

/**
 * @brief Testing valid cmd
 *
 */
void testValidCmd();
/**
 * @brief Testing valid string is int
 *
 */
void testValidStrIsInt();
/**
 * @brief Testing valid string has newline
 *
 */
void testValidCheckNl();
/**
 * @brief Testing valid score validation
 *
 */
void testValidScore();

/**
 * @brief Testing repo clear and utility to clear test repo
 *
 * @param pList
 */
void testPurgeRepo(participantList *pList);
#endif