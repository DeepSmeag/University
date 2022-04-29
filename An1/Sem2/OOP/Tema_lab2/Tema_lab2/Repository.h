#pragma once

#include "Domain.h"

#ifndef REPOSITORY_H_
#define REPOSITORY_H_

/*
 *  New data type to store all the medicines, list-type
 */

typedef struct
{
	Medicine *medicine;
	int length;
	int capacity;
} Repository;

Repository *create();

/*
 *  Defining a function that creates an empty Array
 *  pre: -
 *
 *  post: an empty Array
 */

// Repository* create_array();

/*
 *  Defining a function that frees the memory occupied by a given Array
 *  pre: Array array
 *
 *  post: -
 */

void destroy_repo(Repository *repo);

/*
 *  Defining a function that adds a new medicine in the repo
 *
 *  pre: medicine-Medicine type
 *		repo-Repository type
 *
 *  post: repo
 */

void add(Repository *repo, Medicine medicine);

/*
 *  Defining a function that searches for a given medicine in the repo
 *
 *  pre: medicine-Medicine type
 *		repo-Repository type
 *
 *  post: 1 if the element is in the repo list
 *		 0 otherwise
 */

int search(Repository *repo, int code);

/*
 * Defining a function that deletes the stock of a given medicine-Medicine type
 *
 * pre: medicine-Medicine type
 *	   repo-Repository type
 *
 * post: repo-Repository type (mention: the given element sets its quantity to 0)
 */

void delete_stock(Repository *repo, int code);

/*
 *  A function that resizes the repository
 *
 *  pre: repo-Repository
 *
 *  post: repo-Repository(mentioned-resized)
 */

void create_medicine(Repository *repo, int code, char *s, float concentration, int quantity);

void n_sort(Repository *repo);

void method(Repository *repo);

void q_sort(Repository *repo);

void type_repo(Repository *repo);

int search_repo(Repository *repo, int code);

void repo_replace(Repository *repo, int i, int code, char *name, float concentration, int quant);

void del_repo(Repository *repo, int i);

void resizer(Repository *repo);

// Cosmin
/**
 * @brief Repo function for new filter
 *
 * @param aux
 * @param repo
 * @param concentration
 */
void repo_concentration_filter(Repository *aux, Repository *repo, float concentration);
// Gata Cosmin

void cmp(int cmd);
#endif // !REPOSITORY_H_