from errors.exceptions import RepositoryError
from domain.entities import Student, Problema_laborator, Assignment

class ServiceStudent(object):
    def __init__(self, valid_student, repo_student):
        self.__valid_student = valid_student
        self.__repo_student = repo_student

    

    def add_student(self, id_stud, nume, grupa):
        stud = Student(id_stud, nume, grupa)
        
        self.__valid_student.validate(stud)
        self.__repo_student.add_student(stud)
        self.save_info()
    def modify_student(self, id_stud, nume_nou, grupa_noua):
        stud = Student(id_stud, nume_nou, grupa_noua)
        
        self.__valid_student.validate(stud)
        self.__repo_student.modify_student(stud)
        self.save_info()
    def remove_student(self, id_stud):
        self.__repo_student.remove_student(id_stud)
        self.save_info()
    def no_of_students(self):
        return len(self.__repo_student)

    def get_all_students(self):
        return self.__repo_student.get_all()

    def search_stud_by_id(self, id_s):
        return self.__repo_student.recursive_search_stud_by_id(id_s, 0)

    def save_info(self):
        self.__repo_student.save_info()

class ServiceProblem(object):
    def __init__(self, valid_problem, repo_problem):
        self.__valid_problem = valid_problem
        self.__repo_problem = repo_problem

    def add_problem(self, id_prob, desc, deadline):
        prob = Problema_laborator(id_prob, desc, deadline)
        
        self.__valid_problem.validate(prob)
        self.__repo_problem.add_problem(prob)
        self.save_info()
    def modify_problem(self, id_prob, desc, deadline):
        prob = Problema_laborator(id_prob, desc, deadline)
        
        self.__valid_problem.validate(prob)
        self.__repo_problem.modify_problem(prob)
        self.save_info()
    def remove_problem(self, id_prob):
        self.__repo_problem.remove_problem(id_prob)
        self.save_info()
    def no_of_problems(self):
        return len(self.__repo_problem)

    def get_all_problems(self):
        return self.__repo_problem.get_all()

    def search_prob_by_id(self, id_s):
        return self.__repo_problem.recursive_search_prob_by_id(id_s, 0)

    def save_info(self):
        self.__repo_problem.save_info()

class ServiceAssignment(object):
    def __init__(self, valid_assignment, repo_assignment, repo_student, repo_problem):
        self.__valid_assignment = valid_assignment
        self.__repo_assignment = repo_assignment
        self.__repo_student = repo_student
        self.__repo_problem = repo_problem

    def add_assignment(self, id_assignment, id_student, id_problem, grade = None):
        assignment = Assignment(id_assignment, id_student, id_problem, grade)
        try:
            if self.__repo_student.search_stud_by_id(id_student) is not None:
                student_is_valid = True
        except RepositoryError:
            student_is_valid = False  
        
        try:
            if self.__repo_problem.search_prob_by_id(id_problem) is not None:
                problem_is_valid = True
        except RepositoryError:
            problem_is_valid = False
        
            
        self.__valid_assignment.validate(assignment, student_is_valid, problem_is_valid)
        self.__repo_assignment.add_assignment(assignment)
        self.save_info()
    def grade_assignment(self, assignment_id, grade):
        self.__repo_assignment.grade_assignment(assignment_id, grade)
        self.save_info()
    def remove_assignment(self, assignment_id):
        self.__repo_assignment.remove_assignment(assignment_id)
        self.save_info()
    def search_assignment_by_id(self, assignment_id):
        return self.__repo_assignment.recursive_search_assignment_by_id(assignment_id, 0)

    def search_assignments_by_problem_id(self, id_problem):
        return self.__repo_assignment.search_assignments_by_problem_id(id_problem)

    def get_student_name(self, id_student):
        return self.__repo_student.get_student_name(id_student)

    def no_of_assignments(self):
        return len(self.__repo_assignment)

    def get_all_assignments(self):
        return self.__repo_assignment.get_all()

    def save_info(self):
        self.__repo_assignment.save_info()

    def search_assignments_by_student_id(self, id_student):
        return self.__repo_assignment.search_assignments_by_student_id(id_student)

    def __calculate_mean(self, stud_assignments):
        mean = 0
        for _assignment in stud_assignments:
            try:
                mean += _assignment.get_grade()
            except TypeError:
                continue
        mean = mean/ len(stud_assignments)
        if mean == 0:
            return 10
        return mean
    def stats_mean(self):
        stats = []
        for _stud in self.__repo_student.get_all():
            try:
                stud_assignments = self.search_assignments_by_student_id(_stud.get_id())
                mean = self.__calculate_mean(stud_assignments)
            except RepositoryError as re:
                continue
            if(mean<5.0):
                stats.append(str(_stud.get_nume()) + " -> " + str(mean) )
        return stats

    def order_name(self, id_problem):
        stats = self.search_assignments_by_problem_id(id_problem)
        #order by name
        stats = self.__sort_bingo(stats, key = lambda assignment: self.get_student_name(assignment.get_student_id()), reversed = False, cmp = None)
        # stats.sort(key=lambda assignment: self.get_student_name(assignment.get_student_id()), reverse=False)
        stats_decoded = []
        for _assignment in stats:
            assignment_id = _assignment.get_assignment_id()
            student_name = self.get_student_name(_assignment.get_student_id())
            problem_id = _assignment.get_problem_id()
            grade = _assignment.get_grade()
            assignment_instance = "Assignment ID: ["+str(assignment_id)+"],Student: ["+str(student_name)+"], Lab ID: ["+str(problem_id)+"]-> "+str(grade)
            stats_decoded.append(assignment_instance)

        return stats_decoded

    def __sort_grade(self, stats):
        for index_i in range(len(stats)-1):
            for index_j in range(index_i+1, len(stats)):
                try:
                    if stats[index_i].get_grade() > stats[index_j].get_grade():
                        stats[index_i], stats[index_j] = stats[index_j], stats[index_i]
                except TypeError:
                    if stats[index_i].get_grade() == None:
                        stats[index_i], stats[index_j] = stats[index_j], stats[index_i]
        return stats

    def __sort_merge(self, inp_list, key = None, reversed = False, cmp = None):
        if key is None:
            def key(x):
                return x
        if cmp is None:
            factor = 1
            if reversed == True:
                factor = -1
            def cmp(one, another):
                
                if key(one) is None:
                    return 1 * factor
                if key(one) <= key(another):
                    return -1 * factor
                else:
                    return 1 * factor
        l = 0
        r= len(inp_list)-1
        m = l + (r-1)//2
        
        n1 = m - l + 1
        n2 = r - m
    
        # create temp arrays
        L = [0] * (n1)
        R = [0] * (n2)
    
        # Copy data to temp arrays L[] and R[]
        
        L = inp_list[:n1]
        R[j] = inp_list[n1:n2]
    
        # Merge the temp arrays back into arr[l..r]
        i = 0     # Initial index of first subarray
        j = 0     # Initial index of second subarray
        k = l     # Initial index of merged subarray

        L = self.__sort_grade(L)
        R = self.__sort_grade(R)


        while i < n1 and j < n2:
            if cmp(L[i], R[j])<0:
                inp_list[k] = L[i]
                i += 1
            else:
                inp_list[k] = R[j]
                j += 1
            k += 1
    
        # Copy the remaining elements of L[], if there
        # are any
        while i < n1:
            inp_list[k] = L[i]
            i += 1
            k += 1
    
        # Copy the remaining elements of R[], if there
        # are any
        while j < n2:
            inp_list[k] = R[j]
            j += 1
            k += 1
        return inp_list
        # l is for left index and r is right index of the
        # sub-array of arr to be sorted

    def __sort_bingo(self, inp_list, key = None, reversed = False, cmp = None):
        if key is None:
            def key(x):
                return x
        if cmp is None:
            factor = 1
            if reversed == True:
                factor = -1
            def cmp(one, another):
                if key(one) <= key(another):
                    return -1 * factor
                else:
                    return 1 * factor
        
        last = len(inp_list) - 1

        nextMax = inp_list[last]
        for i in range(last-1,-1,-1):
            if cmp(inp_list[i], nextMax)>0:
                nextMax = inp_list[i]
        
        while (last>0) and key(inp_list[last])==key(nextMax):
            last = last -1

        while last >0:
            prevMax = nextMax
            nextMax = inp_list[last]

            for i in range(last-1, -1 , -1):
                if cmp(inp_list[i],  nextMax)>0:
                    if key(inp_list[i]) != key(prevMax):
                        nextMax = inp_list[i]
                    else:
                        inp_list[i], inp_list[last] = inp_list[last], inp_list[i]

            while last > 0 and key(inp_list[last]) == key(nextMax):
                last = last -1
            
        return inp_list

    def order_grade(self, id_problem):
        stats = self.search_assignments_by_problem_id(id_problem)
        #order by grade
        try:
            # stats.sort(key=lambda assignment: assignment.get_grade(), reverse=False)
            stats = self.__sort_merge(stats, key = lambda assignment: assignment.get_grade(), reversed= False)
        except TypeError:
            print("Warning: There are ungraded assignments! \n")
        stats_decoded = []
        for _assignment in stats:
            assignment_id = _assignment.get_assignment_id()
            student_name = self.get_student_name(_assignment.get_student_id())
            problem_id = _assignment.get_problem_id()
            grade = _assignment.get_grade()
            assignment_instance = "Assignment ID: ["+str(assignment_id)+"],Student: ["+str(student_name)+"], Lab ID: ["+str(problem_id)+"]-> "+str(grade)
            stats_decoded.append(assignment_instance)
        return stats_decoded

    def stats_most(self):
        stats = []
        id_most = 0
        most_no_problems = 0
        for _stud in self.__repo_student.get_all():
            try:
                stud_assignments = self.search_assignments_by_student_id(_stud.get_id())
                no_problems = len(stud_assignments)
                if no_problems > most_no_problems:
                    most_no_problems = no_problems
                    id_most = _stud.get_id()
            except RepositoryError as re:
                continue
        for _problem in self.__repo_problem.get_all():
            for _assignment in self.__repo_assignment.get_all():
                if _assignment.get_student_id() == id_most:
                    stats.append(_problem)
                    break
                

        return stats
