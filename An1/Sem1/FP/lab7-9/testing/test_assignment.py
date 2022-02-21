from domain.entities import Student, Problema_laborator, Assignment
from errors.exceptions import ValidationError, RepositoryError
from validation.validation import ValidateStudent, ValidateProblem, ValidateAssignment
from repository.repo import FileRepoStudent, FileRepoProblem, FileRepoAssignment, RepoStudent, RepoProblem, RepoAssignment
from service.services import ServiceStudent, ServiceProblem, ServiceAssignment
import unittest
class Tests(unittest.TestCase):

    def test_create_assignment(self):
        id_assignment = 1
        id_stud = 2
        id_prob = "2_1"
        assignment = Assignment(id_assignment, id_stud, id_prob)
        self.assertEqual(assignment.get_assignment_id(), id_assignment)
        # assert assignment.get_assignment_id() == id_assignment
        self.assertEqual(assignment.get_grade(), None)
        # assert assignment.get_grade() == None
        self.assertEqual(assignment.get_problem_id(), id_prob)
        # assert assignment.get_problem_id() == id_prob
        self.assertEqual(assignment.get_student_id(), id_stud)
        # assert assignment.get_student_id() == id_stud
        alt_assignment = Assignment(id_assignment, 2, "1_2")
        self.assertEqual(assignment, alt_assignment)
        # assert assignment == alt_assignment

    def test_validate_assignment(self):
        assignment_id = 1
        student_id = 1
        problem_id = "1_2"
        assignment = Assignment(assignment_id, student_id, problem_id)
        valid = ValidateAssignment()
        valid.validate(assignment, True, True)
        inv_id = -1
        inv_student_id = -1
        inv_problem_id = 2
        inv_assignment = Assignment(inv_id, inv_student_id, inv_problem_id)
        self.assertRaises(ValidationError, valid.validate, inv_assignment, True, True)
        # try:
        #     valid.validate(inv_assignment, True, True)
        #     assert(False)
        # except ValidationError as ve:
        #     assert str(ve) == "id invalid\nid student invalid\nid problema invalid\n"

    def test_add_assignment(self):
        assignment_id = 1
        student_id = 1
        problem_id = "1_2"
        grade = 10
        repo_assignment = FileRepoAssignment("test_repo_assignment.csv")
        valid_assignment = ValidateAssignment()
        repo_student = FileRepoStudent("test_repo_student.csv")
        repo_problem = FileRepoProblem("test_repo_problem.csv")
        srv_assignment = ServiceAssignment(valid_assignment, repo_assignment, repo_student, repo_problem)
        
        valid_student = ValidateStudent()
        srv_student = ServiceStudent(valid_student, repo_student)
        
        srv_student.add_student(1, "nume", 216)

        
        valid_problem = ValidateProblem()
        srv_problem = ServiceProblem(valid_problem, repo_problem)
        
        srv_problem.add_problem("1_2", "desc", 5)

        self.assertEqual(srv_assignment.no_of_assignments(), 0)
        # assert(srv_assignment.no_of_assignments()==0)
        srv_assignment.add_assignment(assignment_id, student_id, problem_id, grade)
        self.assertEqual(srv_assignment.no_of_assignments(), 1)
        # assert(srv_assignment.no_of_assignments()==1)
        assignment = repo_assignment.search_assignment_by_id(1)
        self.assertEqual(assignment.get_assignment_id(), 1)
        # assert assignment.get_assignment_id() == 1
        self.assertEqual(assignment.get_student_id(), 1)
        # assert assignment.get_student_id() == 1
        self.assertEqual(assignment.get_problem_id(), "1_2")
        # assert assignment.get_problem_id() == "1_2"
        self.assertEqual(assignment.get_grade(), 10)
        # assert assignment.get_grade() == 10
        srv_assignment.remove_assignment(assignment_id)
        srv_student.remove_student(student_id)
        srv_problem.remove_problem(problem_id)

    def test_note_assignment(self):
        assignment_id = 1
        student_id = 1
        problem_id = "1_2"
        repo_assignment = FileRepoAssignment("test_repo_assignment.csv")
        valid_assignment = ValidateAssignment()
        repo_student = FileRepoStudent("test_repo_student.csv")
        repo_problem = FileRepoProblem("test_repo_problem.csv")
        srv_assignment = ServiceAssignment(valid_assignment, repo_assignment, repo_student, repo_problem)
        # srv_assignment.remove_assignment(assignment_id)
        valid_student = ValidateStudent()
        srv_student = ServiceStudent(valid_student, repo_student)
        
        srv_student.add_student(1, "nume", 216)

        valid_problem = ValidateProblem()
        srv_problem = ServiceProblem(valid_problem, repo_problem)
        
        srv_problem.add_problem("1_2", "desc", 5)

        self.assertEqual(srv_assignment.no_of_assignments(), 0)
        # assert(srv_assignment.no_of_assignments()==0)
        srv_assignment.add_assignment(assignment_id, student_id, problem_id, grade=None)
        self.assertEqual(srv_assignment.no_of_assignments(), 1)
        # assert(srv_assignment.no_of_assignments()==1)
        assignment = repo_assignment.search_assignment_by_id(1)
        self.assertEqual(assignment.get_grade(), None)
        # assert assignment.get_grade() == None
        
        srv_assignment.grade_assignment(assignment_id, 10)
        self.assertEqual(assignment.get_grade(), 10)
        # assert assignment.get_grade() == 10
        srv_assignment.remove_assignment(assignment_id)
        srv_student.remove_student(student_id)
        srv_problem.remove_problem(problem_id)

    def test_remove_assignment(self):
        repo_assignment = FileRepoAssignment("test_repo_assignment.csv")
        valid_assignment = ValidateAssignment()
        repo_student = FileRepoStudent("test_repo_student.csv")
        repo_problem = FileRepoProblem("test_repo_problem.csv")
        srv_assignment = ServiceAssignment(valid_assignment, repo_assignment, repo_student, repo_problem)

        valid_student = ValidateStudent()
        srv_student = ServiceStudent(valid_student, repo_student)
        
        srv_student.add_student(1, "nume", 216)

        valid_problem = ValidateProblem()
        srv_problem = ServiceProblem(valid_problem, repo_problem)
        
        srv_problem.add_problem("1_2", "desc", 5)

        assignment_id = 1
        student_id = 1
        problem_id = "1_2"
        
        srv_assignment.add_assignment(assignment_id, student_id, problem_id, grade=None)
        self.assertRaises(AssertionError, self.assertRaises, RepositoryError, srv_assignment.search_assignment_by_id, 1)
        
        # try:
        #     srv_assignment.search_assignment_by_id(1)
        #     assert True
        # except RepositoryError as ve:
        #     assert False
        srv_assignment.remove_assignment(assignment_id)
        self.assertRaises(RepositoryError, srv_assignment.search_assignment_by_id, 1)
        # try:
        #     srv_assignment.search_assignment_by_id(1)
        #     assert False
        # except RepositoryError as ve:
        #     assert True
        
        srv_student.remove_student(1)
        srv_problem.remove_problem("1_2")