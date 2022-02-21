from domain.entities import Student, Problema_laborator, Assignment
from errors.exceptions import ValidationError, RepositoryError
from validation.validation import ValidateStudent, ValidateProblem, ValidateAssignment
from repository.repo import FileRepoStudent, FileRepoProblem, FileRepoAssignment, RepoStudent, RepoProblem, RepoAssignment
from service.services import ServiceStudent, ServiceProblem, ServiceAssignment

import unittest
class Tests(unittest.TestCase):

    def test_create_problem(self):
        id_prob = "9_2"
        desc = "Ja"
        deadline = 5
        prob = Problema_laborator(id_prob, desc, deadline)
        self.assertEqual(prob.get_id(), id_prob)
        # assert prob.get_id() == id_prob
        self.assertEqual(prob.get_desc(), desc)
        # assert prob.get_desc() == desc
        self.assertEqual(prob.get_deadline(), deadline)
        # assert prob.get_deadline() == deadline
        alta_prob = Problema_laborator(id_prob, "ad", 6)
        self.assertEqual(prob, alta_prob)
        # assert prob == alta_prob

    def test_add_problem(self):
        id_prob = "9_2"
        desc = "La"
        deadline = 6
        repo = FileRepoProblem("test_repo_problem.csv")
        valid = ValidateProblem()
        srv = ServiceProblem(valid, repo)

        self.assertEqual(srv.no_of_problems(), 0)
        # assert(srv.no_of_problems()==0)
        srv.add_problem(id_prob, desc, deadline)
        self.assertEqual(srv.no_of_problems(), 1)
        # assert(srv.no_of_problems()==1)
        prob = repo.search_prob_by_id("9_2")
        self.assertEqual(prob.get_id(), "9_2")
        # assert prob.get_id() == "9_2"
        self.assertEqual(prob.get_desc(), "La")
        # assert prob.get_desc() == "La"
        self.assertEqual(prob.get_deadline(), 6)
        # assert prob.get_deadline() == 6
        srv.remove_problem(id_prob)

    def test_modify_problem(self):
        repo_problem = FileRepoProblem("test_repo_problem.csv")
        valid_problem = ValidateProblem()
        srv_problem = ServiceProblem(valid_problem, repo_problem)
        id_prob = "9_1"
        desc = "Ja"
        deadline = 1
        
        srv_problem.add_problem(id_prob, desc, deadline)
        
        srv_problem.modify_problem(id_prob, "No", 30)
        prob =  repo_problem.search_prob_by_id(id_prob)
        self.assertEqual(prob.get_desc(), "No")
        # assert prob.get_desc() == "No"
        self.assertEqual(prob.get_deadline(), 30)
        # assert prob.get_deadline() == 30
        srv_problem.remove_problem(id_prob)

    def test_remove_problem(self):
        id_prob = "9_1"
        repo_problem = FileRepoProblem("test_repo_problem.csv")
        valid_problem = ValidateProblem()
        srv_problem = ServiceProblem(valid_problem, repo_problem)
        
        
        desc = "Ja"
        deadline = 1

        srv_problem.add_problem(id_prob, desc, deadline)

        srv_problem.remove_problem(id_prob)
        self.assertRaises(RepositoryError, repo_problem.search_prob_by_id, id_prob)
        # try:
        #     repo_problem.search_prob_by_id(id_prob)
        #     assert False
        # except RepositoryError as ve:
        #     assert True
