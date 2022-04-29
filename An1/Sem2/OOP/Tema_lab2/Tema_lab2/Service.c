#include "Repository.h"
#include "Validator.h"
#include "Service.h"

Service *create_service(Repository *repo)
{
	Service *serv = (Service *)malloc(sizeof(Service));
	serv->repo = repo;

	return serv;
}
int create_mmedicine(Service *serv, int code, char *name, float concentration, int quantity)
{
	if (validation(code, concentration, quantity) != 0)
		return (validation(code, concentration, quantity));
	if (search(serv->repo, code) == 1)
	{
		q_add(serv->repo, code, quantity);
		return 3;
	}
	create_medicine(serv->repo, code, name, concentration, quantity);
	return 0;
}

int empty_stock(Service *serv, int code)
{
	if (validation(code, 1, 1) != 0)
		return validation(code, 1, 1);
	delete_stock(serv->repo, code);
	return 0;
}

void serv_sort(Service *serv, int cmd)
{
	if (cmd == 1)
		ssort(serv->repo, n_lower);
	else if (cmd == 2)
		ssort(serv->repo, q_lower);
}

/*
Repository* get_repo(Service* service) {
	return service->repo;
}

int ret_code_serv(Service* serv, int i) {
	return ret_code(serv->repo, i);
}

char* ret_name_serv(Service* serv,int i) {
	return ret_name(serv->repo, i);
}

float ret_concentration_serv(Service* serv, int i) {
	return ret_concentration(serv->repo,i);
}

int ret_quantity_serv(Service* serv, int i) {
	return ret_quantity(serv->repo, i);
}
*/

void destroy_service(Service *serv)
{
	destroy_repo(serv->repo);
	free(serv);
}

void serv_n_filter(Service *s_aux, Service *serv, char c)
{
	repo_n_filter(s_aux->repo, serv->repo, c);
}

void serv_q_filter(Service *s_aux, Service *serv, int q)
{
	repo_q_filter(s_aux->repo, serv->repo, q);
}

// Cosmin
void serv_concentration_filter(Service *s_aux, Service *serv, float concentration)
{
	repo_concentration_filter(s_aux->repo, serv->repo, concentration);
}
// Gata Cosmin

/*
void type_serv(Service* service) {
	type_repo(service->repo);
}
*/

int gl(Service *service)
{
	return gll(service->repo);
}

void destroy_service_aux(Service *s_aux)
{
	destroy_repo_aux(s_aux->repo);
	free(s_aux);
}

int search_serv(Service *service, int cod)
{
	return search_repo(service->repo, cod);
}

void replace(Service *service, int i, int code, char *name, float concentration, int quant)
{
	repo_replace(service->repo, i, code, name, concentration, quant);
}

void del_serv(Service *service, int i)
{
	del_repo(service->repo, i);
}

void serv_n_sort(Service *serv)
{
	n_sort(serv->repo);
}

void serv_q_sort(Service *serv)
{
	q_sort(serv->repo);
}
