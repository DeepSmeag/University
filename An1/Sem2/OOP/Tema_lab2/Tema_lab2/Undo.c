#include "Undo.h"

undoStruct *undoCreateHistory(Repository *repo)
{
    undoStruct *undo = (undoStruct *)malloc(sizeof(undoStruct));
    undo->repo = repo;
    undo->undoH = (undoHistory *)malloc(sizeof(undoHistory));
    undoHistory *undoH = undo->undoH;
    undoH->dim = 0;
    undoH->capacity = 4;
    undoH->elems = (Repository **)malloc(undoH->capacity * sizeof(Repository *));
    Repository **undoElems = undoH->elems;
    for (int i = 0; i < undoH->capacity; i++)
    {
        undoElems[i] = (Repository *)malloc(sizeof(Repository));
    }
    undoAdd(undoH, repo);
    return undo;
}

int undoIsEmpty(undoHistory *undoH)
{
    if (undoH->dim < 2)
        return 1;
    return 0;
}

void undoAdd(undoHistory *undoH, Repository *repo)
{
    if (undoH->dim == undoH->capacity)
    {
        undoRedim(undoH);
        // printf("Eroare dimensiune undo\n");
    }
    // spatiul necesar in undoH->elems[dim] pentru repo este alocat la dimensionare
    //  aloc pentru repo->dim elemente, este suficient
    Repository **undoElems = undoH->elems;
    int dim = undoH->dim;
    Repository *currentRepo = undoElems[dim];

    currentRepo->medicine = (Medicine *)malloc(repo->length * sizeof(Medicine));
    // if (currentRepo->medicine == NULL)
    //     printf("Eroare la alocare Medicine\n");
    undoCopy(currentRepo, repo);

    undoH->dim++;
}

int undoDelete(undoHistory *undoH)
{
    if (!undoIsEmpty(undoH))
    {
        int dim = undoH->dim;
        Repository **undoElems = undoH->elems;
        Repository *repoUndo = undoElems[dim - 1];
        destroy_repo(repoUndo);
        undoElems[dim - 1] = (Repository *)malloc(sizeof(Repository));
        undoH->dim--;
        return 0;
    }
    // eroare
    return 1;
}
int undoRestoreRepo(undoHistory *undoH, Repository *repo)
{
    if (!undoIsEmpty(undoH))
    {
        int dim = undoH->dim;
        Repository **undoElems = undoH->elems;
        Repository *repoUndo = undoElems[dim - 1];
        // clear la numele medicamentelor
        for (int i = 0; i < repo->length; i++)
        {
            free(repo->medicine[i].name);
        }
        // si copiem elementele inapoi
        undoCopy(repo, repoUndo);
        return 0;
    }
    return 1;
}
void undoRedim(undoHistory *undoH)
{
    int newCap = undoH->capacity * 2;
    Repository **newHistoryElems = (Repository **)malloc(newCap * sizeof(Repository *));
    Repository **undoElems = undoH->elems;
    for (int i = 0; i < newCap; i++)
    {
        newHistoryElems[i] = (Repository *)malloc(sizeof(Repository));
    }
    for (int i = 0; i < undoH->dim; i++)
    {
        Repository *currentRepo = newHistoryElems[i];
        Repository *repoToCopyFrom = undoElems[i];
        currentRepo->medicine = (Medicine *)malloc(repoToCopyFrom->length * sizeof(Medicine));
        undoCopy(currentRepo, repoToCopyFrom);
    }

    undoClearElems(undoH);
    undoH->elems = newHistoryElems;
    undoH->capacity = newCap;
}
void undoCopy(Repository *r1, Repository *r2)
{
    r1->length = r2->length;
    r1->capacity = r2->capacity;
    for (int i = 0; i < r2->length; i++)
    {
        set_code(&(r1->medicine[i]), r2->medicine[i].code);
        set_concentration(&(r1->medicine[i]), r2->medicine[i].concentration);
        set_name(&(r1->medicine[i]), r2->medicine[i].name);
        set_quantity(&(r1->medicine[i]), r2->medicine[i].quantity);
    }
}
void undoClearElems(undoHistory *undoH)
{
    Repository **undoElems = undoH->elems;
    int dim = undoH->dim;

    int capacity = undoH->capacity;
    for (int i = 0; i < dim; i++)
    {
        Repository *repo = undoElems[i];
        destroy_repo(repo);
    }
    for (int i = dim; i < capacity; i++)
    {
        free(undoElems[i]);
    }

    free(undoElems);
}
void undoPurge(undoStruct *undo)
{
    undoClearElems(undo->undoH);
    free(undo->undoH);

    undo->undoH = NULL;
    undo->repo = NULL;
    free(undo);
    undo = NULL;
}