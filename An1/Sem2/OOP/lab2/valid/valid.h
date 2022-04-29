#ifndef VALID_H_
#define VALID_H_
#include <string.h>
#include <stdlib.h>

/**
 * @brief Test if the UI cmd is valid
 *
 * @param cmd
 * @return int 1 - good; 0 - bad
 */
int validCmd(char *cmd);
/**
 * @brief Check if a string is a valid int
 *
 * @param str
 * @return int 1- good; 0-bad
 */
int validStrIsInt(char *str);
/**
 * @brief Check if a string has a newline character
 *
 * @param str
 * @return int 1-has \n; 0- doesn't have
 */
int validCheckNl(char *str);
/**
 * @brief Check if score parameter is valid (int 0-100)
 *
 * @param str
 * @return int 1- is valid; 0- is not valid
 */
int validScore(char *str);
#endif