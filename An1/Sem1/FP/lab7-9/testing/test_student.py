from domain.entities import Student, Problema_laborator, Assignment
from errors.exceptions import ValidationError, RepositoryError
from validation.validation import ValidateStudent, ValidateProblem, ValidateAssignment
from repository.repo import FileRepoStudent, FileRepoProblem, FileRepoAssignment, RepoStudent, RepoProblem, RepoAssignment
from service.services import ServiceStudent, ServiceProblem, ServiceAssignment

import unittest
class Tests(unittest.TestCase):

    def test_create_student(self):
            id_stud = 23
            name = "Ja"
            group = 217
            stud = Student(id_stud, name, group)
            self.assertEqual(stud.get_id(), id_stud)
            # assert stud.get_id() == id_stud
            self.assertEqual(stud.get_nume(), name)
            # assert stud.get_nume() == name
            self.assertEqual(stud.get_grupa(), group)
            # assert stud.get_grupa() == group
            alt_nume = "kaka"
            alta_grupa = 218
            alt_stud = Student(id_stud, alt_nume, alta_grupa)
            self.assertEqual(stud, alt_stud)
            # assert stud == alt_stud
            
        
    def test_validate_student(self):
        id_stud = 23
        name = "Ja"
        group = 217
        stud = Student(id_stud, name, group)
        valid = ValidateStudent()
        valid.validate(stud)
        inv_id = -1
        inv_nume = ""
        inv_grupa = -1
        stud_inv = Student(inv_id, inv_nume, inv_grupa)
        self.assertRaises(ValidationError, valid.validate, stud_inv)
        # try:
        #     valid.validate(stud_inv)
        #     assert(False)
        # except ValidationError as ve:
        #     assert str(ve) == "id invalid\nnume invalid\ngrupa invalida\n"

    def test_add_student_repo(self):
        id_stud = 23
        name = "Ja"
        group = 217
        stud = Student(id_stud, name, group)
        repo = FileRepoStudent("test_repo_student.csv")
        
        self.assertEqual(len(repo), 0)
        # assert(len(repo)==0)
        repo.add_student(stud)
        # assert(len(repo)==1)
        self.assertEqual(len(repo), 1)
        stud_gasit = repo.search_stud_by_id(id_stud)
        self.assertEqual(stud_gasit, stud)
        # assert(stud_gasit == stud)
        self.assertEqual(stud.get_nume(), stud_gasit.get_nume())
        # assert(stud.get_nume()==stud_gasit.get_nume())
        self.assertEqual(stud.get_grupa(), stud_gasit.get_grupa())
        # assert(stud.get_grupa()==stud_gasit.get_grupa())
        id_inexist = 24
        self.assertRaises(RepositoryError, repo.search_stud_by_id, id_inexist)
        # try:
        #     stud_gasit = repo.search_stud_by_id(id_inexist)
        #     assert(False)
        # except RepositoryError as re:
        #     assert(str(re) == "id inexistent")
        alt_nume = "lala"
        alta_grupa = 125
        alt_stud = Student(id_stud, alt_nume, alta_grupa)
        self.assertRaises(RepositoryError, repo.add_student, alt_stud)
        # try:
        #     stud_gasit = repo.add_student(alt_stud)
        #     assert(False)
        # except RepositoryError as re:
        #     assert(str(re) == "id existent")
        repo.remove_student(id_stud)

    def test_add_student_srv(self):
        id_stud = 23
        nume = "La"
        grupa = 291
        repo = FileRepoStudent("test_repo_student.csv")
        valid = ValidateStudent()
        srv = ServiceStudent(valid, repo)
        self.assertEqual(srv.no_of_students(), 0)
        # assert(srv.no_of_students()==0)
        srv.add_student(id_stud, nume, grupa)
        self.assertEqual(srv.no_of_students(), 1)
        # assert(srv.no_of_students()==1)
        self.assertRaises(RepositoryError, srv.add_student, id_stud, nume, grupa)
        srv.remove_student(id_stud)
        # try:
        #     srv.add_student(id_stud, nume, grupa)
        #     assert(False)
        # except RepositoryError as re:
        #     assert( str(re) == "id existent")
        inv_id = -1
        inv_nume = ""
        inv_grupa = -1
        self.assertRaises(ValidationError, srv.add_student, inv_id, inv_nume, inv_grupa)
        # try:
        #     srv.add_student(inv_id, inv_nume, inv_grupa)
        #     assert(False)
        # except ValidationError as ve:
        #     assert( str(ve) == "id invalid\nnume invalid\ngrupa invalida\n")
        
    def test_modify_student_repo(self):
        repo_student = FileRepoStudent("test_repo_student.csv")
        stud = Student(1, 'nume', 217)
        repo_student.add_student(stud)
        stud = Student(1, 'alt', 218)
        repo_student.modify_student(stud)
        stud = repo_student.search_stud_by_id(1)
        self.assertEqual(stud.get_grupa(), 218)
        # assert stud.get_grupa() == 218
        self.assertEqual(stud.get_nume(), 'alt')
        # assert stud.get_nume() == 'alt'
        repo_student.remove_student(1)
        self.assertEqual(len(repo_student), 0)

    def test_modify_student_srv(self):
        repo_student = FileRepoStudent("test_repo_student.csv")
        valid_student = ValidateStudent()
        srv_student = ServiceStudent(valid_student, repo_student)
        id_stud = 23
        
        name = "Ja"
        group = 217
        
        srv_student.add_student(id_stud, name, group)
        
        srv_student.modify_student(id_stud, "alt", 218)
        stud =  repo_student.search_stud_by_id(id_stud)
        self.assertEqual(stud.get_nume(), 'alt')
        # assert stud.get_nume() == 'alt'
        self.assertEqual(stud.get_grupa(), 218)
        srv_student.remove_student(id_stud)
        # assert stud.get_grupa() == 218

    def test_remove_student_srv(self):
        repo_student = FileRepoStudent("test_repo_student.csv")
        valid_student = ValidateStudent()
        srv_student = ServiceStudent(valid_student, repo_student)
        id_stud = 23
        
        name = "Ja"
        group = 217
        srv_student.add_student(id_stud, name, group)
        srv_student.remove_student(id_stud)
        self.assertRaises(RepositoryError, repo_student.search_stud_by_id, id_stud)
        
        

    def test_remove_student_repo(self):
        repo_student = FileRepoStudent("test_repo_student.csv")
        stud = Student(1, 'nume', 217)
        
        repo_student.add_student(stud)
        repo_student.remove_student(1)
        self.assertRaises(RepositoryError, repo_student.search_stud_by_id, 1)
        
        self.assertEqual(len(repo_student), 0)
        # try:
        #     repo_student.search_stud_by_id(1)
        #     assert False
        # except RepositoryError as ve:
        #     assert True