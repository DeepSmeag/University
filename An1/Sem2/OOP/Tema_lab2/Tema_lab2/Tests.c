#include "Tests.h"

void test_repo_adaugare()
{
	Repository *repo = create();
	int code = 5;
	// char* s = "Algocalmin";
	float concentration = 22.5;
	int quantity = 1;
	assert(repo->length == 0);
	create_medicine(repo, code, "Algocalmin", concentration, quantity);
	assert(repo->length == 1);
	assert(get_code(repo->medicine[0]) == 5);
	assert(strcmp(get_name(repo->medicine[0]), "Algocalmin") == 0);
	assert(abs(get_concentration(repo->medicine[0]) - 22.5) < 0.0000001);
	assert(get_quantity(repo->medicine[0]) == 1);
	code = 6;
	// s = "Paracetamol";
	concentration = 27.64;
	quantity = 1;
	create_medicine(repo, code, "Paracetamol", concentration, quantity);
	assert(repo->length == 2);
	create_medicine(repo, code, "Paracetamol", concentration, quantity);
	assert(repo->length == 3);
	destroy_repo(repo);
}

void test_repo_delete()
{
	Repository *repo = create();
	int code = 5;
	// char* s = "Algocalmin";
	float concentration = 22.5;
	int quantity = 1;
	create_medicine(repo, code, "Algocalmin", concentration, quantity);
	code = 6;
	// s = "Paracetamol";
	concentration = 27.64;
	quantity = 1;
	create_medicine(repo, code, "Paracetamol", concentration, quantity);
	assert(get_quantity(repo->medicine[0]) == 1);
	delete_stock(repo, 5);
	assert(get_quantity(repo->medicine[0]) == 0);
	destroy_repo(repo);
}

void test_repo_search()
{
	Repository *repo = create();
	int code = 5;
	// char* s = "Algocalmin";
	float concentration = 22.5;
	int quantity = 1;
	create_medicine(repo, code, "Paracetamol", concentration, quantity);
	code = 6;
	// s = "Paracetamol";
	concentration = 27.64;
	quantity = 1;
	create_medicine(repo, code, "Algocalmin", concentration, quantity);
	assert(search(repo, 5) == 1);
	assert(search(repo, 6) == 1);
	assert(search(repo, 7) == 0);
	destroy_repo(repo);
}
void test_valid()
{
	int code = -2;
	float concentration = -24;
	int quant = -2;
	assert(validation(code, 1, 1) == 1);
	assert(validation(1, concentration, 1) == 2);
	assert(validation(1, 1, quant) == 4);
	assert(validation(1, 1, 1) == 0);
}

void test_service_add()
{
	Repository *repo = create();
	Service *serv = create_service(repo);
	int code = 5;
	// char* s = "Algocalmin";
	float concentration = 22.5;
	int quantity = 1;
	assert(repo->length == 0);
	create_mmedicine(serv, code, "Algocalmin", concentration, quantity);
	assert(create_mmedicine(serv, code, "Algocalmin", concentration, quantity) == 3);
	// assert(gl(serv) == 1);
	assert(get_code(repo->medicine[0]) == 5);
	assert(strcmp(get_name(repo->medicine[0]), "Algocalmin") == 0);
	assert(abs(get_concentration(repo->medicine[0]) - 22.5) < 0.0000001);
	assert(get_quantity(repo->medicine[0]) == 2);
	code = 6;
	// s = "Paracetamol";
	concentration = 27.64;
	quantity = 1;
	create_medicine(repo, code, "Paracetamol", concentration, quantity);
	assert(repo->length == 2);
	create_medicine(repo, code, "Paracetamol", concentration, quantity);
	assert(repo->length == 3);
	assert(create_mmedicine(serv, -1, "Paracetamol", concentration, quantity) == 1);
	assert(empty_stock(serv, -1) == 1);
	assert(empty_stock(serv, 6) == 0);
	del_serv(serv, 0);
	replace(serv, 0, 10, "Paracetamol", concentration, quantity);
	destroy_service(serv);
}

void test_serv_search()
{
	Repository *repo = create();
	Service *serv = create_service(repo);
	int code = 5;
	// char* s = "Algocalmin";
	float concentration = 22.5;
	int quantity = 1;
	assert(repo->length == 0);
	create_mmedicine(serv, code, "Algocalmin", concentration, quantity);
	assert(gl(serv) == 1);
	assert(get_code(repo->medicine[0]) == 5);
	assert(strcmp(get_name(repo->medicine[0]), "Algocalmin") == 0);
	assert(abs(get_concentration(repo->medicine[0]) - 22.5) < 0.0000001);
	assert(get_quantity(repo->medicine[0]) == 1);
	code = 6;
	// s = "Paracetamol";
	concentration = 27.64;
	quantity = 1;
	create_medicine(repo, code, "Paracetamol", concentration, quantity);
	assert(repo->length == 2);
	assert(search_serv(serv, 5) == 0);
	assert(search_serv(serv, 72) == -1);
	assert(search_serv(serv, 6) == 1);
	create_mmedicine(serv, 7, "Paracetamol", concentration, quantity - 1);
	create_mmedicine(serv, 8, "Paracetamol", concentration, quantity + 1);
	create_mmedicine(serv, 9, "Paracetamol", concentration, quantity);
	serv_n_sort(serv);
	serv_q_sort(serv);
	serv_sort(serv, 1);
	serv_sort(serv, 2);
	destroy_service(serv);
}

void test_serv_filter()
{
	Repository *aux = create();
	Service *s_aux = create_service(aux);
	Repository *repo = create();
	Service *serv = create_service(repo);
	create_mmedicine(serv, 1, "Algocalmin", 12.5, 7);
	create_mmedicine(serv, 2, "Autan", 17, 3);
	create_mmedicine(serv, 3, "Analgezic", 18.7, 12);
	create_mmedicine(serv, 4, "Paracetamol", 15.5, 9);
	serv_n_filter(s_aux, serv, 'A');
	serv_q_filter(s_aux, serv, 10);
	destroy_service_aux(s_aux);
	destroy_service(serv);
}
// Cosmin
void test_undo()
{
	Repository *repo = create();
	undoStruct *undo = undoCreateHistory(repo);
	Service *serv = create_service(repo);
	assert(undoRestoreRepo(undo->undoH, repo) == 1);
	assert(undoDelete(undo->undoH) == 1);
	assert(undoIsEmpty(undo->undoH) == 1);
	undoAdd(undo->undoH, repo);
	create_mmedicine(serv, 1, "Algocalmin", 12.5, 7);
	assert(undo->undoH->dim == 2);
	assert(undoRestoreRepo(undo->undoH, repo) == 0);
	assert(undoDelete(undo->undoH) == 0);
	undoAdd(undo->undoH, repo);
	create_mmedicine(serv, 2, "Algocalmin", 12.5, 7);
	undoAdd(undo->undoH, repo);
	create_mmedicine(serv, 3, "Algocalmin", 12.5, 7);
	undoAdd(undo->undoH, repo);
	create_mmedicine(serv, 4, "Algocalmin", 12.5, 7);
	undoAdd(undo->undoH, repo);
	create_mmedicine(serv, 5, "Algocalmin", 12.5, 7);
	assert(undoRestoreRepo(undo->undoH, repo) == 0);
	assert(undoDelete(undo->undoH) == 0);
	destroy_service(serv);
	undoPurge(undo);
}
void test_serv_filter_concentration()
{
	Repository *aux = create();
	Service *s_aux = create_service(aux);
	Repository *repo = create();
	Service *serv = create_service(repo);
	create_mmedicine(serv, 1, "Algocalmin", 12.5, 7);
	create_mmedicine(serv, 2, "Autan", 17, 3);
	create_mmedicine(serv, 3, "Analgezic", 18.7, 12);
	create_mmedicine(serv, 4, "Paracetamol", 15.5, 9);
	serv_concentration_filter(s_aux, serv, 18);
	assert(s_aux->repo->length == 3);
	serv_concentration_filter(s_aux, serv, 15);
	assert(s_aux->repo->length == 4);
	destroy_service_aux(s_aux);
	destroy_service(serv);
}
// Gata Cosmin
void run_tests()
{
	test_repo_adaugare();
	test_repo_delete();
	test_repo_search();
	test_service_add();
	test_serv_search();
	test_serv_filter();
	test_valid();
	// Cosmin
	test_undo();
	test_serv_filter_concentration();
	// Gata Cosmin
}
