#ifndef VALID_H_
#define VALID_H_

#include <string>

class Valid
{
public:
    Valid();
    ~Valid();

    /**
     * @brief Checks if string is int
     *
     * @param toCheck
     * @return true : is int
     * @return false : is not int
     */
    const bool validStrIsPositiveInt(std::string &toCheck) const;

    /**
     * @brief Checks if string is empty
     *
     * @param toCheck
     * @return true : is empty
     * @return false : is not empty
     */
    const bool validStrIsEmpty(std::string &toCheck) const;
};

#endif