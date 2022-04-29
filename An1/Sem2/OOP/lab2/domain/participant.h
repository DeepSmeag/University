#ifndef PARTICIPANT_H_
#define PARTICIPANT_H_
#include <string.h>
#include <stdlib.h>
typedef struct
{
    char *lastName;
    char *firstName;
    int score;
} participant;

/**
 * @brief Get the Last Name object
 *
 * @param pp
 * @return char* -lastName
 */
char *getLastName(participant *pp);
/**
 * @brief Get the First Name object
 *
 * @param pp
 * @return char*
 */
char *getFirstName(participant *pp);
/**
 * @brief Get the Score object
 *
 * @param pp
 * @return int
 */
int getScore(participant *pp);

/**
 * @brief Set the Last Name object
 *
 * @param pp
 * @param name
 */
void setLastName(participant *pp, char *name);
/**
 * @brief Set the First Name object
 *
 * @param pp
 * @param name
 */
void setFirstName(participant *pp, char *name);
/**
 * @brief Set the Score object
 *
 * @param pp
 * @param score
 */
void setScore(participant *pp, int score);

/**
 * @brief Remove participant data from memory
 *
 * @param pp -empty participant pointer
 */
void clearParticipant(participant *pp);

/**
 * @brief Create a Participant object
 *
 * @param firstName
 * @param lastName
 * @param score
 * @return participant*
 */
participant *createParticipant(char *firstName, char *lastName, int score);

#endif