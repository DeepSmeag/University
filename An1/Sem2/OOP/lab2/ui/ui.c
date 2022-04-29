#include "ui.h"
#include "../valid/valid.h"
#include "../repo/repo.h"
#include "../service/service.h"

void UiInit(participantList *pList)
{
    while (1)
    {
        char cmd[256];

        printf("\n\
                1. Add\n\
                2. Update\n\
                3. Delete\n\
                4. Filter by...\n\
                5. Sort by...\n\
                6. Print participants\n\n\
                0. Exit\n");
        printf(">>");
        fgets(cmd, sizeof(cmd), stdin);
        cmd[strlen(cmd) - 1] = '\0';
        if (!uiCheckCmd(cmd))
        {
            printf("Invalid command.\n");
            continue;
        }
        int cmdInt = cmd[0] - '0';
        if (cmdInt == 0)
        {
            serviceClearRepo(pList);
            break;
        }
        else if (cmdInt == 1)
        {
            uiAdd(pList);
        }
        else if (cmdInt == 2)
        {
            uiUpdate(pList);
        }
        else if (cmdInt == 3)
        {
            uiRemove(pList);
        }
        else if (cmdInt == 4)
        {
            uiFilter(pList);
        }
        else if (cmdInt == 5)
        {
            uiSort(pList);
        }
        else if (cmdInt == 6)
        {
            uiPrint(pList);
        }
    }
}
int uiCheckCmd(char *cmd)
{
    if (validCmd(cmd))
        return 1;
    return 0;
}
int uiAdd(participantList *pList)
{
    char *firstName, *lastName, *score;
    char buffer[256];

    printf("First name: ");
    fgets(buffer, sizeof(buffer), stdin);
    firstName = (char *)malloc(strlen(buffer) + 1);
    strcpy(firstName, buffer);
    uiCleanStr(firstName);
    printf("Last name: ");
    fgets(buffer, sizeof(buffer), stdin);
    lastName = (char *)malloc(strlen(buffer) + 1);
    strcpy(lastName, buffer);
    uiCleanStr(lastName);
    printf("Score: ");
    fgets(buffer, sizeof(buffer), stdin);
    score = (char *)malloc(strlen(buffer) + 1);
    strcpy(score, buffer);
    uiCleanStr(score);
    if (!validScore(score))
    {
        printf("Invalid score\n");
        free(firstName);
        free(lastName);
        free(score);
        return 1;
    }
    int scoreInt;
    scoreInt = strtol(score, NULL, 10);
    if (serviceAdd(pList, firstName, lastName, scoreInt))
    {
        printf("Error: Name not found!\n");
    }
    free(firstName);
    free(lastName);
    free(score);
    return 0;
}

int uiUpdate(participantList *pList)
{
    char *newFirstName, *lastName, *newLastName, *newScore;
    char buffer[256];
    printf("Last name to update: ");
    fgets(buffer, sizeof(buffer), stdin);
    lastName = (char *)malloc(strlen(buffer) + 1);
    strcpy(lastName, buffer);
    uiCleanStr(lastName);

    printf("New first name: ");
    fgets(buffer, sizeof(buffer), stdin);
    newFirstName = (char *)malloc(strlen(buffer) + 1);
    strcpy(newFirstName, buffer);
    uiCleanStr(newFirstName);
    printf("New last name: ");
    fgets(buffer, sizeof(buffer), stdin);
    newLastName = (char *)malloc(strlen(buffer) + 1);
    strcpy(newLastName, buffer);
    uiCleanStr(newLastName);
    printf("New score: ");
    fgets(buffer, sizeof(buffer), stdin);
    newScore = (char *)malloc(strlen(buffer) + 1);
    strcpy(newScore, buffer);
    uiCleanStr(newScore);
    if (!validScore(newScore))
    {
        printf("Invalid score\n");
        free(lastName);
        free(newFirstName);
        free(newLastName);
        free(newScore);
        return 1;
    }
    int scoreInt;
    scoreInt = strtol(newScore, NULL, 10);
    if (serviceUpdate(pList, lastName, newFirstName, newLastName, scoreInt))
    {
        printf("Error: Name not found!\n");
    }
    free(lastName);
    free(newFirstName);
    free(newLastName);
    free(newScore);
    return 0;
}

int uiRemove(participantList *pList)
{
    char *lastName;
    char buffer[256];
    printf("Last Name to remove: ");
    fgets(buffer, sizeof(buffer), stdin);
    lastName = (char *)malloc(strlen(buffer) + 1);
    strcpy(lastName, buffer);
    uiCleanStr(lastName);
    if (serviceRemove(pList, lastName))
    {
        printf("Error: Name not found!\n");
    }
    free(lastName);
    return 0;
}

int uiFilter(participantList *pList)
{
    char *choice;
    char buffer[256];
    printf("Choose score / name: ");
    fgets(buffer, sizeof(buffer), stdin);
    choice = (char *)malloc(strlen(buffer) + 1);
    strcpy(choice, buffer);
    uiCleanStr(choice);
    if (strcmp(choice, "score") == 0)
    {
        char *score;
        char buffer[256];
        printf("Score to filter: ");
        fgets(buffer, sizeof(buffer), stdin);
        score = (char *)malloc(strlen(buffer) + 1);
        strcpy(score, buffer);
        uiCleanStr(score);

        if (!validScore(score))
        {
            printf("Invalid score\n");
            free(score);
            return 1;
        }
        int scoreInt;
        scoreInt = strtol(score, NULL, 10);
        free(score);
        participantList *pListFilterScore;
        pListFilterScore = serviceFilterScore(pList, scoreInt);
        uiPrint(pListFilterScore);
        serviceClearRepo(pListFilterScore);
    }
    else if (strcmp(choice, "name") == 0)
    {
        char *lastName;
        char buffer[256];
        printf("Last name letter to filter (only first letter): ");
        fgets(buffer, sizeof(buffer), stdin);
        lastName = (char *)malloc(strlen(buffer) + 1);
        strcpy(lastName, buffer);
        uiCleanStr(lastName);
        if (strlen(lastName) < 1)
        {
            printf("Error: Invalid letter/name\n");
            free(lastName);
            return 1;
        }
        participantList *pListFilterName;
        pListFilterName = serviceFilterName(pList, lastName[0]);
        free(lastName);
        uiPrint(pListFilterName);
        serviceClearRepo(pListFilterName);
    }
    else
    {
        printf("Error: Invalid choice");
        free(choice);
        return 1;
    }
    free(choice);
    return 0;
}
int uiSort(participantList *pList)
{
    char *choice1, *choice2;
    char buffer[256];
    printf("Choose score / name: ");
    fgets(buffer, sizeof(buffer), stdin);
    choice1 = (char *)malloc(strlen(buffer) + 1);
    strcpy(choice1, buffer);
    uiCleanStr(choice1);
    printf("Choose asc / desc: ");
    fgets(buffer, sizeof(buffer), stdin);
    choice2 = (char *)malloc(strlen(buffer) + 1);
    strcpy(choice2, buffer);
    uiCleanStr(choice2);
    int choice2Option = 0;
    if (strcmp(choice2, "asc") == 0)
    {
        choice2Option = 1;
    }
    if (strcmp(choice2, "desc") == 0)
    {
        choice2Option = -1;
    }
    if (strcmp(choice1, "score") == 0 && choice2Option != 0)
    {
        participantList *pListSortScore;
        pListSortScore = serviceSortScore(pList, choice2Option);

        uiPrint(pListSortScore);
        serviceClearRepo(pListSortScore);
    }
    else if (strcmp(choice1, "name") == 0 && choice2Option != 0)
    {
        participantList *pListSortName;
        pListSortName = serviceSortName(pList, choice2Option);

        uiPrint(pListSortName);
        serviceClearRepo(pListSortName);
    }
    else
    {
        printf("Error: Invalid choice");
        free(choice1);
        free(choice2);
        return 1;
    }
    free(choice1);
    free(choice2);
    return 0;
}
void uiPrint(participantList *pList)
{
    if (pList->length)
        for (int i = 0; i < pList->length; i++)
        {
            printf("%s : %s : %d\n", getLastName(&((*pList).participants[i])), getFirstName(&((*pList).participants[i])), getScore(&((*pList).participants[i])));
        }
    else
        printf("There are no participants!\n");
}
void uiCleanStr(char *str)
{
    for (int i = 0; i < strlen(str); i++)
    {
        if (str[i] == '\n')
        {
            str[i] = '\0';
            break;
        }
    }
}