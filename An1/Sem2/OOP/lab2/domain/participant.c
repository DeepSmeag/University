#include "participant.h"

char *getLastName(participant *pp)
{
    char *lastName = pp->lastName;
    return lastName;
}
char *getFirstName(participant *pp)
{
    char *firstName = pp->firstName;
    return firstName;
}
int getScore(participant *pp)
{
    return pp->score;
}

void setLastName(participant *pp, char *lastName)
{
    pp->lastName = (char *)malloc(sizeof(char) * strlen(lastName) + 1);
    strcpy(pp->lastName, lastName);
}
void setFirstName(participant *pp, char *firstName)
{
    pp->firstName = (char *)malloc(sizeof(char) * strlen(firstName) + 1);
    strcpy(pp->firstName, firstName);
}
void setScore(participant *pp, int score)
{
    pp->score = score;
}

participant *createParticipant(char *firstName, char *lastName, int score)
{
    participant *pp;
    pp = (participant *)malloc(sizeof(participant));
    pp->lastName = (char *)malloc(sizeof(char) * strlen(lastName) + 1);
    pp->firstName = (char *)malloc(sizeof(char) * strlen(firstName) + 1);
    strcpy(pp->lastName, lastName);
    strcpy(pp->firstName, firstName);

    pp->score = score;
    return pp;
}
void clearParticipant(participant *pp)
{
    free(pp->lastName);
    free(pp->firstName);
    pp->score = 0;
}
