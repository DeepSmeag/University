#pragma once
#include <malloc.h>
#include "Service.h"
#include "Undo.h"
#ifndef USER_INTERFACE_H_
#define USER_INTERFACE_H_

typedef struct
{
	Service *service;
	undoStruct *undo;
} UI;

UI *create_UI(Service *serv, undoStruct *undo);

void destroy_UI(UI *ui);

void print_UI(UI *ui);

void del_UI(UI *ui);

void UI_RUN(UI *ui);

void destroy_UI_aux(UI *ui);

// Cosmin
/**
 * @brief UI for undo functionality
 *
 * @param ui
 */
void UI_undo(UI *ui);
// Gata Cosmin
#endif