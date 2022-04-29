#include "User_interface.h"
#include <stdio.h>
#include <string.h>

UI *create_UI(Service *serv, undoStruct *undo)
{
	UI *ui = (UI *)malloc(sizeof(UI));
	ui->service = serv;
	ui->undo = undo;
	return ui;
}

void destroy_UI(UI *ui)
{
	destroy_service(ui->service);
	undoPurge(ui->undo);

	free(ui);
}

void add_UI(UI *ui)
{
	int code;
	char name[101];
	float concentration;
	int quant;
	int err;
	printf("Introduceti datele\nCod:");
	scanf("%d", &code);
	printf("Nume:");
	scanf("%s", name);
	printf("Concentratie:");
	scanf("%f", &concentration);
	printf("Cantitate:");
	scanf("%d", &quant);
	undoAdd(ui->undo->undoH, ui->service->repo);
	err = create_mmedicine(ui->service, code, name, concentration, quant);
	if (err == 1)
		printf("Cod invalid!");
	else if (err == 2)
		printf("Concentratie invalida!");
	else if (err == 3)
		printf("Stoc actualizat!");
	else if (err == 0)
		printf("Medicament adaugat cu succes!");
	else if (err == 4)
		printf("Cantitate invalida!");
	if (err != 0 && err != 3)
	{
		undoDelete(ui->undo->undoH);
	}
}

void change_UI(UI *ui)
{
	printf("Modificati medicamentul cu codul:");
	int cod, i;
	scanf("%d", &cod);
	i = search_serv(ui->service, cod);
	if (i == -1)
		printf("Cod gresit!");
	else
	{
		int code;
		char name[101];
		float concentration;
		int quant;
		int err;
		printf("Introduceti datele noi\nCod:");
		scanf("%d", &code);
		printf("Nume:");
		scanf("%s", name);
		printf("Concentratie:");
		scanf("%f", &concentration);
		printf("Cantitate:");
		scanf("%d", &quant);
		undoAdd(ui->undo->undoH, ui->service->repo);
		replace(ui->service, i, code, name, concentration, quant);
		printf("Medicament modificat cu succes!");
	}
}

void print_UI(UI *ui)
{
	int code;
	int quantity;
	float concentration;
	for (int i = 0; i < ui->service->repo->length; i++)
	{
		/*
		code = ret_code_serv(ui->service, i);
		quantity = ret_quantity_serv(ui->service, i);
		strcpy(nume, ret_name_serv(ui->service, i));
		concentration = ret_concentration_serv(ui->service, i);
		*/
		code = get_code(ui->service->repo->medicine[i]);
		quantity = get_quantity(ui->service->repo->medicine[i]);
		concentration = get_concentration(ui->service->repo->medicine[i]);
		printf("%d. %s -> %f : %d\n", code, get_name(ui->service->repo->medicine[i]), concentration, quantity);
	}
	// type_serv(ui->service);
}

void del_UI(UI *ui)
{
	int cod, i;
	printf("Codul produsului trecut pe stoc 0:");
	scanf("%d", &cod);
	i = search_serv(ui->service, cod);
	if (i == -1)
		printf("Cod gresit!");
	else
	{
		undoAdd(ui->undo->undoH, ui->service->repo);
		del_serv(ui->service, i);
		printf("Stoc golit!");
	}
}

void filter_UI(UI *ui)
{
	int cmd;
	Repository *aux = create();
	undoStruct *undo = undoCreateHistory(aux);
	Service *s_aux = create_service(aux);
	UI *ui_aux = create_UI(s_aux, undo);
	printf("1.Filtrare dupa nume!\n2.Filtrare dupa cantitate!\n3.Cosmin-filtrare dupa concentratie.\n");
	scanf("%d", &cmd);
	if (cmd == 1)
	{
		printf("Introduceti litera dupa care se face filtrarea: ");
		char *c = NULL;
		scanf("%s", &c);
		serv_n_filter(ui_aux->service, ui->service, c);
	}
	else if (cmd == 2)
	{
		printf("Introduceti stocul maxim: ");
		int q;
		scanf("%d", &q);
		serv_q_filter(ui_aux->service, ui->service, q);
	}
	else if (cmd == 3)
	{
		printf("Introduceti concentratia maxim: ");
		float concentration;
		scanf("%f", &concentration);
		serv_concentration_filter(ui_aux->service, ui->service, concentration);
	}
	print_UI(ui_aux);
	destroy_UI_aux(ui_aux);
	undoPurge(undo);
}

void destroy_UI_aux(UI *ui_aux)
{
	destroy_service_aux(ui_aux->service);
	free(ui_aux);
}

void UI_sort(UI *ui)
{
	int cmd;
	printf("1.Sortare dupa nume!\n2.Sortare dupa cantitate!\n");
	scanf("%d", &cmd);
	if (cmd == 1)
		serv_n_sort(ui->service);
	else if (cmd == 2)
		serv_q_sort(ui->service);
	print_UI(ui);
}

void UI_sort_2(UI *ui)
{
	int cmd;
	printf("1.Sortare dupa nume!\n2.Sortare dupa cantitate!\n");
	scanf("%d", &cmd);
	serv_sort(ui->service, cmd);
	print_UI(ui);
}
// Cosmin
void UI_undo(UI *ui)
{
	if (undoRestoreRepo(ui->undo->undoH, ui->service->repo) == 1)
	{
		printf("Eroare: istoricul a ajuns la inceput, nu exista alte undo-uri!\n");
	}
	else
	{
		undoDelete(ui->undo->undoH);
	}
}
// Gata Cosmin
void UI_RUN(UI *ui)
{
	int cmd;
	while (1)
	{
		printf("Alegeti o functionalitate:\n");
		printf("1. Adaugare medicament\n");
		printf("2. Modificare medicament\n");
		printf("3. Printeaza medicamente\n");
		printf("4. Stergere stoc medicament\n");
		printf("5. Filtrare\n");
		printf("6. Sortare\n");
		printf("7. Exit\n\n");
		// Cosmin
		printf("9. Undo\n");
		// Gata Cosmin
		printf(">>>");
		scanf("%d", &cmd);

		if (cmd == 1)
			add_UI(ui);
		else if (cmd == 2)
			change_UI(ui);
		else if (cmd == 3)
			print_UI(ui);
		else if (cmd == 4)
			del_UI(ui);
		else if (cmd == 5)
			filter_UI(ui);
		else if (cmd == 6)
			UI_sort(ui);
		else if (cmd == 8)
			UI_sort_2(ui);
		// Cosmin
		else if (cmd == 9)
			UI_undo(ui);
		// Gata Cosmin
		else
		{
			destroy_UI(ui);
			return 0;
		}
	}
}