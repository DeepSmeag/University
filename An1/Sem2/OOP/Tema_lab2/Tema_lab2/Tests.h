#ifndef TESTS_H_
#define TESTS_H_

#include "Repository.h"
#include "Validator.h"
#include "Service.h"
#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <stdlib.h>
#include "Domain.h"
#include <stdio.h>
#include "Undo.h"

void run_tests();
void test_repo_delete();
void test_repo_adaugare();
void test_repo_search();
void test_valid();
void test_service_add();
void test_serv_search();
// Cosmin
/**
 * @brief Test function for undo
 *
 */
void test_undo();
/**
 * @brief Test function for new filter
 *
 */
void test_serv_filter_concentration();
// Gata Cosmin
#endif