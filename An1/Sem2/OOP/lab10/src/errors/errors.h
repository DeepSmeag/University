#ifndef ERRORS_H_
#define ERRORS_H_
#include <string>
// struct RepoExceptionAlreadyExists : public std::exception
// {
//     const char *what() const throw()
//     {
//         return "Repository error: Entry already exists!";
//     }
// };

// struct RepoExceptionNotFound : public std::exception
// {
//     const char *what() const throw()
//     {
//         return "Repository error: Entry not found!";
//     }
// };

// struct TypeExceptionInvalidValue : public std::exception
// {
//     const char *what() const throw()
//     {
//         return "Type error: invalid value given!";
//     }
// };

class CustomExceptionBase
{
private:
    std::string err;
    std::string ErrType;

public:
    explicit CustomExceptionBase(const std::string &err, const std::string &Errtype);
    const std::string what() const;
};

class RepoException : public CustomExceptionBase
{
public:
    RepoException(const std::string &err);
    ~RepoException();
    // using CustomException::what;
};

class ValidException : public CustomExceptionBase
{
public:
    ValidException(const std::string &err);
    ~ValidException();
    // using CustomException::what;
};
#endif
