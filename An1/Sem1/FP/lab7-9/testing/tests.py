from domain.entities import Student, Problema_laborator, Assignment
from errors.exceptions import ValidationError, RepositoryError
from validation.validation import ValidateStudent, ValidateProblem, ValidateAssignment
from repository.repo import FileRepoStudent, FileRepoProblem, FileRepoAssignment, RepoStudent, RepoProblem, RepoAssignment
from service.services import ServiceStudent, ServiceProblem, ServiceAssignment

import unittest


class Tests(unittest.TestCase):
    pass
    


    


    
    


    # def run_all_tests(self):
    #     print("Starting tests...")

    #     self.__test_create_student()
    #     self.__test_validate_student()
    #     self.__test_add_student_repo()
    #     self.__test_add_student_srv()
    #     self.__test_modify_student_srv()
    #     self.__test_modify_student_repo()
    #     self.__test_create_problem()
    #     self.__test_add_problem()
    #     self.__test_modify_problem()
    #     self.__test_remove_problem()
    #     self.__test_create_assignment()
    #     self.__test_validate_assignment()
    #     self.__test_add_assignment()
    #     self.__test_note_assignment()
    #     self.__test_remove_assignment()

    #     print("Finished all tests...")