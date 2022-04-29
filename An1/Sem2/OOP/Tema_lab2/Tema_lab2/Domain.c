#include "Domain.h"
#include <string.h>
#include <malloc.h>


/*
*  A function that creates a medicine
*  Pre: code-unique, int
*		name-char
*		concentration-float
*		quantity-int
*
*  Post: returns medicine-struct
*/

Medicine* create_Medicine(int code, char name[], float concentration, int quantity) {
	Medicine* medicine=(Medicine*)malloc(sizeof(Medicine));
	medicine->name = (char*)malloc(strlen(name) + 1);
	medicine->code = code;
	strcpy(medicine->name, name);
	medicine->concentration = concentration;
	medicine->quantity = quantity;
	return medicine;
}


void destroy_Medicine(Medicine* medicine) {
	free(medicine->name);
	//medicine->name = NULL;
	//free(medicine);
}

/*
*  A function that updates the stock of a medicine
*  Pre: Medicine-struct
*
*  Post: Medicine-struct(mention: quantity=quantity+1)
*/

/*
void stock_update(Medicine* medicine){
	medicine->quantity += 1;
}
*/

/*
*  A function that deletes the stock of a medicine
*  Pre: Medicine-struct
*
*  Post: Medicine-struct(mention: quantity=0)
*/

/*
int equals(Medicine medicine1, Medicine medicine2) {
	if (medicine1.code == medicine1.code)
		return 1;
	return 0;
}
*/

int get_code(Medicine medicine) {
	return medicine.code;
}

void set_code(Medicine* medicine, int value) {
	medicine->code = value;
}

char* get_name(Medicine medicine) {
	return medicine.name;
}

void set_name(Medicine* medicine, char* s) {
	medicine->name = (char*)malloc(strlen(s) + 1);
	strcpy(medicine->name, s);
}

float get_concentration(Medicine medicine) {
	return medicine.concentration;
}

void set_concentration(Medicine* medicine, float concentration) {
	medicine->concentration = concentration;
}

int get_quantity(Medicine medicine) {
	return medicine.quantity;
}

void set_quantity(Medicine* medicine, int quantity) {
	medicine->quantity = quantity;
}


int n_lower(Medicine medicine1, Medicine medicine2) {
	if (strcmp(get_name(medicine1), get_name(medicine2)) < 0)
		return 1;
	return 0;
}

int q_lower(Medicine medicine1, Medicine medicine2) {
	if (get_quantity(medicine1) < get_quantity(medicine2))
		return 1;
	return 0;
}

//void method(Medicine medicine1, Medicine medicine2){
//}