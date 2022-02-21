from ui.ui import Console
from service.services import ServiceStudent, ServiceProblem, ServiceAssignment
from repository.repo import RepoStudent, RepoProblem, RepoAssignment, FileRepoStudent, FileRepoProblem, FileRepoAssignment
from validation.validation import ValidateStudent, ValidateProblem, ValidateAssignment
from rand.random_gen import GenStudents
from testing.tests import Tests
import unittest

if __name__ == '__main__':
    valid_student = ValidateStudent()
    valid_problem = ValidateProblem()
    valid_assignment = ValidateAssignment()

    repo_student = FileRepoStudent("repo_student.csv")
    repo_problem = FileRepoProblem("repo_problem.csv")
    repo_assignment = FileRepoAssignment("repo_assignment.csv")
    # repo_student = RepoStudent()
    # repo_problem = RepoProblem()
    # repo_assignment = RepoAssignment()

    srv_student = ServiceStudent(valid_student, repo_student)
    srv_problem = ServiceProblem(valid_problem, repo_problem)
    srv_assignment = ServiceAssignment(valid_assignment, repo_assignment, repo_student, repo_problem)

    rand_student = GenStudents() 

    ui = Console(srv_student, srv_problem, srv_assignment, rand_student)

    # tests = Tests()
    # tests.run_all_tests()
    # unittest.main()
    #toate problemele avute de studentul cu cele mai multe note
    ui.run()