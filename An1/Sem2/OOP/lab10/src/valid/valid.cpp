#include "valid.h"

Valid::Valid() {}
Valid::~Valid() {}

const bool Valid::validStrIsPositiveInt(std::string &toCheck) const
{
    std::string::const_iterator it = toCheck.begin();
    while (it != toCheck.end() && std::isdigit(*it))
    {
        it++;
    }
    return !toCheck.empty() && it == toCheck.end();
}

const bool Valid::validStrIsEmpty(std::string &toCheck) const
{
    return toCheck.empty();
}
