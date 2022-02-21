from errors.exceptions import RepositoryError
from domain.entities import Student, Problema_laborator, Assignment

class RepoStudent(object):
    def __init__(self):
        self._students = []


    def __len__(self):
        return len(self._students)

    def add_student(self, stud):
        for _stud in self._students:
            if _stud == stud:
                raise RepositoryError("id existent")
        self._students.append(stud)

    def modify_student(self, new_stud):
        old_stud = self.search_stud_by_id(new_stud.get_id())
        old_stud.set_nume(new_stud.get_nume())
        old_stud.set_grupa(new_stud.get_grupa())

    def remove_student(self, id_stud):
        self._students.remove(self.search_stud_by_id(id_stud))

    def search_stud_by_id(self, id_stud):
        not_supposed_to_be_here = True
        for _stud in self._students:
            if _stud.get_id() == id_stud:
                return _stud
        if not_supposed_to_be_here:
            raise RepositoryError("id inexistent")

    def recursive_search_stud_by_id(self, id_stud, index_curent = 0):
        if index_curent >= len(self._students):
            raise RepositoryError("id inexistent")
        if self._students[index_curent].get_id() == id_stud:
            return self._students[index_curent]
        return self.recursive_search_stud_by_id(id_stud, index_curent+1)

    def get_all(self):
        return self._students[:]

    def get_student_name(self, id_stud):
        stud = self.search_stud_by_id(id_stud)
        return stud.get_nume()

    

    


class RepoProblem(object):
    def __init__(self):
        self._problems = []

    def __len__(self):
        return len(self._problems)

    def add_problem(self, prob):
        for _prob in self._problems:
            if _prob == prob:
                raise RepositoryError("id existent")
        self._problems.append(prob)

    def modify_problem(self, new_prob):
        old_prob = self.search_prob_by_id(new_prob.get_id())
        old_prob.set_desc(new_prob.get_desc())
        old_prob.set_deadline(new_prob.get_deadline())

    def remove_problem(self, id_prob):
        self._problems.remove(self.search_prob_by_id(id_prob))

    def search_prob_by_id(self, id_prob):
        not_supposed_to_be_here = True
        for _prob in self._problems:
            if _prob.get_id() == id_prob:
                return _prob
        if not_supposed_to_be_here:
            raise RepositoryError("id inexistent")
    
    def recursive_search_prob_by_id(self, id_prob, index_curent = 0):
        if index_curent >= len(self._problems):
            raise RepositoryError("id inexistent")
        if self._problems[index_curent].get_id() == id_prob:
            return self._problems[index_curent]
        return self.recursive_search_prob_by_id(id_prob, index_curent+1)

    def get_all(self):
        return self._problems[:]



class RepoAssignment(object):
    def __init__(self):
        self._assignments = []

    def __len__(self):
        return len(self._assignments)

    def add_assignment(self, assignment):
        for _assignment in self._assignments:
            if _assignment == assignment:
                raise RepositoryError("id existent")
        self._assignments.append(assignment)

    def grade_assignment(self, assignment_id, grade):
        assignment = self.search_assignment_by_id(assignment_id)
        assignment.set_grade(grade)

    def remove_assignment(self, assignment_id):
        self._assignments.remove(self.search_assignment_by_id(assignment_id))

    def search_assignment_by_id(self, id_assignment):
        not_supposed_to_be_here = True
        for _assignment in self._assignments:
            if _assignment.get_assignment_id() == id_assignment:
                return _assignment
        if not_supposed_to_be_here:
            raise RepositoryError("id inexistent")

    def recursive_search_assignment_by_id(self, id_assignment, index_curent):
        if index_curent >= len(self._assignments):
            raise RepositoryError("id inexistent")
        if self._assignments[index_curent].get_assignment_id() == id_assignment:
            return self._assignments[index_curent]
        return self.recursive_search_assignment_by_id(id_assignment, index_curent+1)

    def get_all(self):
        return self._assignments[:]

    def search_assignments_by_problem_id(self, id_problem):
        stats = []
        not_supposed_to_be_here = True
        for _assignment in self._assignments:
            if _assignment.get_problem_id() == id_problem:
                stats.append(_assignment)
                not_supposed_to_be_here = False
        if not_supposed_to_be_here:
            raise RepositoryError("id inexistent")
        return stats

    def search_assignments_by_student_id(self, id_student):
        stats = []
        not_supposed_to_be_here = True
        for _assignment in self._assignments:
            if _assignment.get_student_id() == id_student:
                stats.append(_assignment)
                not_supposed_to_be_here = False
        if not_supposed_to_be_here:
            raise RepositoryError("id inexistent")
        return stats





class FileRepoStudent(RepoStudent):
    
    def __init__(self, file_name):
        super().__init__()
        # self.__students = []
        self.__file_name = file_name
        self.__read_from_file()

    def add_student(self, stud):
        super().add_student(stud)
        self.save_info()

    def modify_student(self, new_stud):
        super().modify_student(new_stud)
        self.save_info()

    def remove_student(self, id_stud):
        super().remove_student(id_stud)
        self.save_info()

    def save_info(self):
        self.__store_to_file()

    def __read_from_file(self):
        try:
            with open(self.__file_name, 'r') as f_student:
                line = f_student.readline().strip()
                while line != "":
                    line = line.split(",")
                    
                    student = Student(id_stud = int(line[0]), nume = line[1], grupa = int(line[2]))
                    self._students.append(student)
                    
                    line = f_student.readline().strip()
            print("Successfully loaded students") 
        except ValueError:
            print("Invalid value encountered")
        except IndexError:
            print("Invalid data format")
        except FileNotFoundError:
            with open(self.__file_name, 'x') as f_student:
                self.__read_from_file()
            print("File not found...Creating it...")

    def __store_to_file(self):
        try:
            with open(self.__file_name, 'w') as f_student:
                
                for _stud in self._students:
                    f_student.write(f"{_stud.get_id()},{_stud.get_nume()},{_stud.get_grupa()}\n")
                    
        except ValueError:
            print("Invalid value encountered")
        except IndexError:
            print("Invalid data format")
        except FileNotFoundError:
            print("File not found")

class FileRepoProblem(RepoProblem):
    def __init__(self, file_name):
        super().__init__()  
        self.__file_name = file_name
        self.__read_from_file()

    def add_problem(self, prob):
        super().add_problem(prob)
        self.save_info()

    def modify_problem(self, new_prob):
        super().modify_problem(new_prob)
        self.save_info()

    def remove_problem(self, id_prob):
        super().remove_problem(id_prob)
        self.save_info()

    def save_info(self):
        self.__store_to_file()

    def __read_from_file(self):
        try:
            with open(self.__file_name, 'r') as f_problem:
                line = f_problem.readline().strip()
                while line != "":
                    line = line.split(",")
                    problem = Problema_laborator(id_prob = line[0], desc = line[1], deadline = int(line[2]))
                    self._problems.append(problem)
                    line = f_problem.readline().strip()
            print("Successfully loaded problems")     
        except ValueError:
            print("Invalid value encountered")
        except IndexError:
            print("Invalid data format")
        except FileNotFoundError:
            with open(self.__file_name, 'x') as f_problem:
                self.__read_from_file()
            print("File not found...Creating it...")

    def __store_to_file(self):
        try:
            with open(self.__file_name, 'w') as f_problem:
                
                for _prob in self._problems:
                    f_problem.write(f"{_prob.get_id()},{_prob.get_desc()},{_prob.get_deadline()}\n")
                    
        except ValueError:
            print("Invalid value encountered")
        except IndexError:
            print("Invalid data format")
        except FileNotFoundError:
            print("File not found")

class FileRepoAssignment(RepoAssignment):
    def __init__(self, file_name):
        super().__init__()  
        self.__file_name = file_name
        self.__read_from_file()

    def add_assignment(self, assignment):
        super().add_assignment(assignment)
        self.save_info()

    def grade_assignment(self, assignment_id, grade):
        super().grade_assignment(assignment_id, grade)
        self.save_info()

    def remove_assignment(self, assignment_id):
        super().remove_assignment(assignment_id)
        self.save_info()

    def save_info(self):
        self.__store_to_file()

    def __read_from_file(self):
        try:
            with open(self.__file_name, 'r') as f_assignment:
                line = f_assignment.readline().strip()
                while line != "":
                    line = line.split(",")
                    try:
                        grade_r = int(line[3])
                        
                    except IndexError:
                        grade_r = None
                    except ValueError:
                        grade_r = None
                    assignment = Assignment(assignment_id = int(line[0]), student_id = int(line[1]), problem_id = line[2], grade = grade_r)
                    # self.add_assignment(assignment)
                    self._assignments.append(assignment)
                    
                    line = f_assignment.readline().strip()
            print("Successfully loaded assignments")     
        except ValueError:
            print(assignment)
            print("Invalid value encountered")
        except IndexError:
            print("Invalid data format")
        except FileNotFoundError:
            with open(self.__file_name, 'x') as f_assignment:
                self.__read_from_file()
            print("File not found...Creating it...")

    def __store_to_file(self):
        try:
            with open(self.__file_name, 'w') as f_assignment:
                
                for _assignment in self._assignments:
                    f_assignment.write(f"{_assignment.get_assignment_id()},{_assignment.get_student_id()},{_assignment.get_problem_id()},{_assignment.get_grade()}\n")
                    
        except ValueError:
            print("Invalid value encountered")
        except IndexError:
            print("Invalid data format")
        except FileNotFoundError:
            print("File not found")