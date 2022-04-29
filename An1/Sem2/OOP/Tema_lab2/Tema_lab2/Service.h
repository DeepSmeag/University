#pragma once

#include "Repository.h"
#include "Validator.h"
#include <stdio.h>
#include "Undo.h"

#ifndef SERVICE_H_
#define SERVICE_H_

typedef struct
{
	Repository *repo;

} Service;

Service *create_service(Repository *repo);

void destroy_service(Service *serv);

int create_mmedicine(Service *serv, int code, char *name, float concentration, int quantity);

int empty_stock(Service *serv, int code);

Repository *get_repo(Service *service);

void type_serv(Service *serv);

int search_serv(Service *serv, int code);

void replace(Service *service, int i, int code, char *name, float concentration, int quant);

void del_serv(Service *service, int i);

// Cosmin
/**
 * @brief Service for new filter
 *
 * @param s_aux
 * @param serv
 * @param concentration
 */
void serv_concentration_filter(Service *s_aux, Service *serv, float concentration);
// Gata Cosmin

#endif