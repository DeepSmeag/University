#include "errors.h"

CustomExceptionBase::CustomExceptionBase(const std::string &err, const std::string &ErrType) : err{err}, ErrType{ErrType} {}
const std::string CustomExceptionBase::what() const
{
    return ErrType + ": " + err;
}

RepoException::RepoException(const std::string &err) : CustomExceptionBase(err, "Repository Exception") {}
ValidException::ValidException(const std::string &err) : CustomExceptionBase(err, "Validation Exception") {}

RepoException::~RepoException() {}
ValidException::~ValidException() {}