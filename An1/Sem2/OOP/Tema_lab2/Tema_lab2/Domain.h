#pragma once

#include <stdio.h>
#include <stdlib.h>


#ifndef DOMAIN_H_
#define DOMAIN_H_

/**
 * New data type to store a medicine
 */
typedef struct {
	int code;
	char* name;
	float concentration;
	int quantity;
} Medicine;


/*
*  Defining a function that creates a medicine
*  Pre: code-unique, int 
*		name-char
*		concentration-float
*		quantity-int
* 
*  Post: returns medicine-struct
*/

Medicine* create_Medicine(int code, char *name, float concentration, int quantity);


/*
*  Defining a function that updates the stock of a medicine
*  Pre: Medicine-struct
* 
*  Post: Medicine-struct(mention: quantity=quantity+1)
*/

void stock_update(Medicine* medicine);

/*
*  Defining a function that deletes the stock of a medicine
*  Pre: Medicine-struct
* 
*  Post: Medicine-struct(mention: quantity=0)
*/


int equals(Medicine medicine1, Medicine medicine2);

int get_code(Medicine medicine);

void set_code(Medicine* medicine, int value);

char* get_name(Medicine medicine);
 
void set_name(Medicine* medicine, char* s);

float get_concentration(Medicine medicine);

void set_concentration(Medicine* medicine, float concentration);

int get_quantity(Medicine medicine);

void set_quantity(Medicine* medicine, int quantity);

int n_lower(Medicine medicine1, Medicine medicine2);

int q_lower(Medicine medicine1, Medicine medicine2);

#endif /* DOMAIN_H_ */
