#include "valid.h"

int validCmd(char *cmd)
{

    if (strlen(cmd) == 1 && cmd[0] >= '0' && cmd[0] <= '6')
    {
        return 1;
    }
    return 0;
}
int validScore(char *str)
{
    if (validStrIsInt(str))
    {
        int strInt = strtol(str, NULL, 10);
        if (strInt <= 100)
            return 1;
    }
    return 0;
}
int validStrIsInt(char *str)
{
    if (strlen(str) < 1)
        return 0;
    for (int i = 0; i < strlen(str); i++)
    {
        if (str[i] < '0' || str[i] > '9')
            return 0;
    }
    return 1;
}
int validCheckNl(char *str)
{
    for (int i = 0; i < strlen(str); i++)
    {
        if (str[i] == '\n')
            return 1;
    }
    return 0;
}