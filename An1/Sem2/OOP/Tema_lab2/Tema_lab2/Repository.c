

#include "Repository.h"
#include "Domain.h"
#include <malloc.h>

/*
 *  Defining a function that adds a new medicine in the repo
 *
 *  pre: medicine-Medicine type
 *		repo-Repository type
 *
 *  post: repo
 */

/*void add(Repository* repo, Medicine medicine)
{
	repo->medicine[repo->length] = medicine;
	repo->length++;
}
*/

/*
 *  Defining a function that searches for a given medicine in the repo
 *
 *  pre: medicine-Medicine type
 *		repo-Repository type
 *
 *  post: 1 if the element is in the repo list
 *		 0 otherwise
 */

int search(Repository *repo, int code)
{
	int i = 0;
	for (i = 0; i < repo->length; i++)
		if (get_code((repo->medicine[i])) == code)
			return 1;
	return 0;
}
/*
 * Defining a function that deletes the stock of a given medicine-Medicine type
 *
 * pre: medicine-Medicine type
 *	   repo-Repository type
 *
 * post: repo-Repository type (mention: the given element sets its quantity to 0)
 */

void delete_stock(Repository *repo, int code)
{
	for (int i = 0; i < repo->length; i++)
		if (get_code(repo->medicine[i]) == code)
			set_quantity(&(repo->medicine[i]), 0);
}

void create_medicine(Repository *repo, int code, char *s, float concentration, int quantity)
{

	if (repo->length == repo->capacity)
		resizer(repo);

	Medicine *aux = create_Medicine(code, s, concentration, quantity);
	set_code(&(repo->medicine[repo->length]), aux->code);
	set_name(&(repo->medicine[repo->length]), aux->name);
	set_concentration(&(repo->medicine[repo->length]), aux->concentration);
	set_quantity(&(repo->medicine[repo->length]), aux->quantity);
	repo->length++;

	destroy_Medicine(aux);
	free(aux);
}

void add_medicine(Repository *repo, Medicine medicine)
{
	if (repo->length == repo->capacity)
		resizer(repo);
	repo->medicine[repo->length] = medicine;
	repo->length++;
}

void resizer(Repository *repo)
{

	int newCap = repo->capacity * 2;
	Medicine *newList = (Medicine *)malloc(sizeof(Medicine) * newCap);
	for (int i = 0; i < repo->length; i++)
		newList[i] = repo->medicine[i];

	free(repo->medicine);
	repo->medicine = newList;
	repo->capacity = newCap;

	// free(repo);
}

Repository *create()
{
	Repository *r;
	r = (Repository *)malloc(sizeof(Repository));
	r->length = 0;
	r->capacity = 2;
	r->medicine = (Medicine *)malloc(sizeof(Medicine) * r->capacity);
	return r;
}

void destroy_repo(Repository *repo)
{
	for (int i = 0; i < repo->length; i++)
	{
		destroy_Medicine(&(repo->medicine[i]));
		// free(&repo->medicine);
	}
	// free(repo->medicine);
	free(repo->medicine);
	// repo->medicine = NULL;
	free(repo);
}

/*
void destroy(Repository* repo) {
	int i;
	for (i = 0; i < repo->length; i++)
		free(&(repo->medicine[i]));
	free(repo->medicine);
	free(repo);
}
*/

void n_sort(Repository *repo)
{
	int i, j;
	Medicine aux;
	for (i = 0; i < repo->length - 1; i++)
		for (j = i + 1; j < repo->length; j++)
			if (n_lower(repo->medicine[i], repo->medicine[j]) == 0)
			{
				aux = repo->medicine[i];
				repo->medicine[i] = repo->medicine[j];
				repo->medicine[j] = aux;
			}
}

void q_sort(Repository *repo)
{
	int i, j;
	Medicine aux;
	for (i = 0; i < repo->length - 1; i++)
		for (j = i + 1; j < repo->length; j++)
			if (q_lower(repo->medicine[i], repo->medicine[j]) == 0)
			{
				aux = repo->medicine[i];
				repo->medicine[i] = repo->medicine[j];
				repo->medicine[j] = aux;
			}
}

void q_add(Repository *repo, int code, int quantity)
{
	for (int i = 0; i < repo->length; i++)
		if (get_code(repo->medicine[i]) == code)
			set_quantity(&(repo->medicine[i]), get_quantity(repo->medicine[i]) + quantity);
}

/*
void type_repo(Repository* repo) {
	for (int i = 0; i < repo->length; i++)
		printf("%d. %s -> %f : %d\n", get_code(repo->medicine[i]), get_name(repo->medicine[i]), get_concentration(repo->medicine[i]), get_quantity(repo->medicine[i]));
	printf("\n");
}
*/

void destroy_repo_aux(Repository *repo)
{
	free(repo->medicine);
	free(repo);
}

void gll(Repository *repo)
{
	return repo->length;
}

void repo_n_filter(Repository *aux, Repository *repo, char c)
{
	for (int i = 0; i < repo->length; i++)
	{
		// char s[256];
		// strcpy(s, get_name(repo->medicine[i]));
		// printf("%d %d\n", s[0], c);
		if (get_name(repo->medicine[i])[0] == c)
		{
			add_medicine(aux, repo->medicine[i]);
		}
	}
}

void repo_q_filter(Repository *aux, Repository *repo, int q)
{
	for (int i = 0; i < repo->length; i++)
		if (get_quantity(repo->medicine[i]) < q)
		{
			// char* s = get_name(repo->medicine[i]);
			add_medicine(aux, repo->medicine[i]);
		}
}
// Cosmin
void repo_concentration_filter(Repository *aux, Repository *repo, float concentration)
{
	for (int i = 0; i < repo->length; i++)
		if (get_concentration(repo->medicine[i]) < concentration)
		{
			// char* s = get_name(repo->medicine[i]);
			add_medicine(aux, repo->medicine[i]);
		}
}
// Gata Cosmin

int search_repo(Repository *repo, int code)
{
	for (int i = 0; i < repo->length; i++)
		if (get_code(repo->medicine[i]) == code)
			return i;
	return -1;
}

void ssort(Repository *repo, int (*method_lower)(Medicine medicine1, Medicine medicine2), int cmd)
{
	Medicine aux;
	for (int i = 0; i < repo->length - 1; i++)
		for (int j = i + 1; j < repo->length; j++)
			if (method_lower(repo->medicine[i], repo->medicine[j]) == 0)
			{
				aux = repo->medicine[i];
				repo->medicine[i] = repo->medicine[j];
				repo->medicine[j] = aux;
			}
}

void repo_replace(Repository *repo, int i, int code, char *name, float concentration, int quant)
{
	Medicine *aux = create_Medicine(code, name, concentration, quant);
	destroy_Medicine(&(repo->medicine[i]));
	set_code(&(repo->medicine[i]), aux->code);
	set_name(&(repo->medicine[i]), aux->name);
	set_concentration(&(repo->medicine[i]), aux->concentration);
	set_quantity(&(repo->medicine[i]), aux->quantity);
	destroy_Medicine(aux);
	free(aux);
}

void del_repo(Repository *repo, int i)
{
	set_quantity(&(repo->medicine[i]), 0);
}

/*
int ret_code(Repository* repo, int i) {
	return repo->medicine[i].code;
}

char* ret_name(Repository* repo, int i) {
	return repo->medicine[i].name;
}

int ret_quantity(Repository* repo, int i) {
	return repo->medicine[i].quantity;
}

float ret_concentration(Repository* repo, int i) {
	return repo->medicine[i].concentration;
}
*/
