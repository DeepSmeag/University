from errors.exceptions import ValidationError, RepositoryError

class Console(object):
    def __init__(self, srv_student, srv_problem, srv_assignment, rand_students):
        self.__srv_student = srv_student
        self.__srv_problem = srv_problem
        self.__srv_assignment = srv_assignment
        self.__rand_students = rand_students

    def __print_exit(self):
        print("""
            Leaving program...
        """)
        
    def __ui_add_student(self, values=None):
        if values!=None:
            try:
                id_stud = int(values[0])
                nume = values[1]
                grupa = int(values[2])
            except IndexError as ie:
                print("Input incomplete. Please try again")
                return
            except ValueError as ve:
                print("Invalid values")
                return
        else:
            try:
                id_stud = int(input("Id student: "))
            except ValueError as ve:
                print("Id invalid")
                return
            nume = input("Nume: ")
            try:
                grupa = int(input("Grupa: "))
            except ValueError as ve:
                print("Grupa invalida")
                return
                
        self.__srv_student.add_student(id_stud, nume, grupa)
        print("Student added successfully")
    
    def __ui_modify_student(self, id_mod=None):
        if id_mod == None:
            id_mod = input("ID of student to modify: ")
        try:
            id_mod = int(id_mod)
        except ValueError as ve:
            print("Invalid ID")
        nume = input("Nume: ")
        try:
            grupa = int(input("Grupa: "))
        except ValueError as ve:
            print("Grupa invalida")
        self.__srv_student.modify_student(id_mod, nume, grupa)
        print("Student modified successfully")

    def __ui_remove_student(self, id_rm = None):
        if id_rm == None:
            id_rm = input("ID of student to remove: ")
        try:
            id_rm = int(id_rm)
        except ValueError as ve:
            print("Invalid ID")
        
        self.__srv_student.remove_student(id_rm)
        print("Student removed successfully")

    def __ui_print_students(self):
        no_of_students = self.__srv_student.no_of_students()
        if no_of_students == 0:
            print("There are no students")
            return
        for _stud in self.__srv_student.get_all_students():
            print(_stud)

    def __ui_search_student(self, id_s = None):
        if id_s == None:
            id_s = input("ID of student to search:")
        try:
            id_s = int(id_s)
        except ValueError as ve:
            print("ID invalid")
        print(self.__srv_student.search_stud_by_id(id_s))

    def __ui_add_problem(self, values=None):
        if values!=None:
            try:
                id_prob = values[0]
                desc = values[1]
                deadline = int(values[2])
            except IndexError as ie:
                print("Input incomplete. Please try again")
            except ValueError as ve:
                print("Invalid values")
        else:
            try:
                id_prob = input("Id problem: ")
            except ValueError as ve:
                print("Id invalid")
            desc = input("Desc: ")
            try:
                deadline = int(input("Deadline: "))
            except ValueError as ve:
                print("Deadline invalid")

        self.__srv_problem.add_problem(id_prob, desc, deadline)
        print("Problem added successfully")

    def __ui_modify_problem(self, id_mod=None):
        if id_mod == None:
            id_mod = input("ID of problem to modify: ")
        
        desc = input("Desc: ")
        try:
            deadline = int(input("Deadline: "))
        except ValueError as ve:
            print("Deadline invalid")
        self.__srv_problem.modify_problem(id_mod, desc, deadline)
        print("Problem modified successfully")

    def __ui_remove_problem(self, id_rm = None):
        if id_rm == None:
            id_rm = input("ID of problem to remove: ")
        
        self.__srv_problem.remove_problem(id_rm)
        print("Problem removed successfully")

    def __ui_print_problems(self):
        no_of_problems = self.__srv_problem.no_of_problems()
        if no_of_problems == 0:
            print("There are no problems")
            return
        for _prob in self.__srv_problem.get_all_problems():
            print(_prob)
    
    def __ui_search_problem(self, id_s = None):
        if id_s == None:
            id_s = input("ID of problem to search: ")
        
        print(self.__srv_problem.search_prob_by_id(id_s))
        
    def __ui_add_assignment(self, values = None):
        grade = None
        if values!=None:
            try:
                id_assignment = int(values[0])
                id_student = int(values[1])
                id_problem = values[2]
                try:
                    if values[3] == "":
                        raise IndexError
                    grade = int(values[3])
                except IndexError as ie:
                    # grade = int(input("Grade: "))
                    pass
            except IndexError as ie:
                print("Input incomplete. Please try again")
            except ValueError as ve:
                print("Invalid values")
        else:
            try:
                id_assignment = int(input("Id assignment: "))
            except ValueError as ve:
                print("Id invalid")
            try:
                id_student = int(input("Id student: "))
            except ValueError as ve:
                print("Id invalid")
            try:
                id_problem = input("Id problem: ")
            except ValueError as ve:
                print("Id invalid")
            try:
                grade = int(input("Grade: "))
                if grade == "":
                    grade = None
            except ValueError as ve:
                print("Grade invalid")

        self.__srv_assignment.add_assignment(id_assignment, id_student, id_problem, grade)
        print("Assignment added successfully")

    def __ui_grade_assignment(self, values = None):
        if values!= None:
            try:
                id_assignment = int(values[0])
                grade = int(values[1])
            except IndexError as ie:
                print("Input incomplete. Please try again")
            except ValueError as ve:
                print("Invalid values") 
        else:
            try:
                if_assignment = int(input("Id assignment: "))
                grade = int(input("Grade: "))
            except ValueError as ve:
                print("Invalid values")
        self.__srv_assignment.grade_assignment(id_assignment, grade)
        print("Assignment graded successfully")

    def __ui_remove_assignment(self, id_s = None):
        if id_s!= None:
            try:
                id_s = int(id_s)
            except ValueError as ve:
                print("Invalid value")
        self.__srv_assignment.remove_assignment(id_s)
        print("Assignment removed successfully")

    def __ui_print_assignments(self):
        no_of_assignments = self.__srv_assignment.no_of_assignments()
        if no_of_assignments == 0:
            print("There are no assignments")
            return
        for _assignment in self.__srv_assignment.get_all_assignments():
            print(_assignment)

    def __ui_gen_rand_students(self, no_of_students = None):
        if no_of_students != None:
            try:
                no_of_students = int(no_of_students)
            except ValueError:
                print("Input incorect")
        else:
            try:
                no_of_students = int(input("No. of students: "))
            except ValueError:
                print("Input incorect")
        for _student_index in range(1, no_of_students+1):
            id_stud, nume, grupa = self.__rand_students.gen_rand_student()
            try:
                self.__srv_student.add_student(id_stud, nume, grupa)
            except RepositoryError:
                pass

    def __ui_stats_order_name(self, id_prob):
        stats = self.__srv_assignment.order_name(id_prob)
        for elem in stats:
            print(elem,"\n")
        

    def __ui_stats_order_grade(self, id_prob):
        stats = self.__srv_assignment.order_grade(id_prob)
        for elem in stats:
            print(elem,"\n")

    def __ui_stats_order(self, values = None):
        if values != None:
            try:
                arg = values[0]
            except IndexError:
                arg = input("Name / Grade: ") 
            try:
                id_prob = values[1]
            except IndexError:
                id_prob = input("ID Problem: ")   
         
        if(arg == "name"):
            self.__ui_stats_order_name(id_prob)
        elif(arg == "grade"):
            self.__ui_stats_order_grade(id_prob)
        else:
            print("Invalid argument")    

    def __ui_stats_mean(self):
        stats = self.__srv_assignment.stats_mean()
        for elem in stats:
            print(elem,"\n")

    def __ui_stats_most(self):
        stats = self.__srv_assignment.stats_most()
        for elem in stats:
            print(elem, '\n')


    def __ui_print_available_commands(self):
        print("""

            Available commands: 
            - add_student [id] [name] [group]
            - modify_student [id_to_search] 
            - remove_student [id_to_search]
            - print_students
            - search_student [id_to_search]

            - add_problem [id] [desc] [deadline]
            - modify_problem [id_to_search]
            - remove_problem [id_to_search]
            - print_problems
            - search_problem [id_to_search]

            - add_assignment [id_assignment] [id_student] [id_problem] [opt: grade]
            - grade_assignment [id_assignment] [grade]
            - remove_assignment [id_assignment]
            - print_assignments

            - gen_rand_students [no_of_students]

            - stats_order [name/grade] [id_problem]
            - stats_mean

            - stats_most

            -exit
        
        """)
    
    def run(self):
        while True:
            self.__ui_print_available_commands()
            cmd = input(">>>")
            cmd = cmd.split(' ')
            if cmd[0] == "exit":
                self.__srv_student.save_info()
                self.__srv_problem.save_info()
                self.__srv_assignment.save_info()
                self.__print_exit()
                
                
                return
            elif cmd[0] == "":
                continue
            elif cmd[0] == "add_student":
                try:
                    if len(cmd) > 1:
                        self.__ui_add_student(cmd[1:])
                    else:
                        self.__ui_add_student()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))
            
            elif cmd[0] == "modify_student":
                try:
                    if len(cmd) > 1:
                        self.__ui_modify_student(cmd[1])
                    else:
                        self.__ui_modify_student()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))

            elif cmd[0] == 'remove_student':
                try:
                    if len(cmd) > 1:
                        self.__ui_remove_student(cmd[1])
                    else:
                        self.__ui_remove_student()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))

            elif cmd[0] == "print_students":
                self.__ui_print_students()

            elif cmd[0] == "search_student":
                try:
                    if len(cmd) > 1:
                        self.__ui_search_student(cmd[1])
                    else:
                        self.__ui_search_student()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))

            elif cmd[0] == "add_problem":
                try:
                    if len(cmd) > 1:
                        self.__ui_add_problem(cmd[1:])
                    else:
                        self.__ui_add_problem()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))
            
            elif cmd[0] == "modify_problem":
                try:
                    if len(cmd) > 1:
                        self.__ui_modify_problem(cmd[1])
                    else:
                        self.__ui_modify_problem()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))

            elif cmd[0] == "remove_problem":
                try:
                    if len(cmd) > 1:
                        self.__ui_remove_problem(cmd[1])
                    else:
                        self.__ui_remove_problem()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))

            elif cmd[0] == "print_problems":
                self.__ui_print_problems()

            elif cmd[0] == "search_problem":
                try:
                    if len(cmd) > 1:
                        self.__ui_search_problem(cmd[1])
                    else:
                        self.__ui_search_problem()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))

            elif cmd[0] == "add_assignment":
                try:
                    if len(cmd) > 1:
                        self.__ui_add_assignment(cmd[1:])
                    else:
                        self.__ui_add_assignment()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))

            elif cmd[0] == "grade_assignment":
                try:
                    if len(cmd) > 1:
                        self.__ui_grade_assignment(cmd[1:])
                    else:
                        self.__ui_grade_assignment()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))

            elif cmd[0] == "remove_assignment":
                try:
                    if len(cmd) > 1:
                        self.__ui_remove_assignment(cmd[1])
                    else:
                        self.__ui_remove_assignment()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))

            elif cmd[0] == 'gen_rand_students':
                try:
                    if len(cmd) > 1:
                        self.__ui_gen_rand_students(cmd[1])
                    else:
                        self.__ui_gen_rand_students()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))
            
            elif cmd[0] == "print_assignments":
                self.__ui_print_assignments()

            elif cmd[0] == "stats_order":
                try:
                    if len(cmd) > 1:
                        self.__ui_stats_order(cmd[1:])
                    else:
                        self.__ui_stats_order()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))

            elif cmd[0] == "stats_mean":
                try:
                    self.__ui_stats_mean()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))

            elif cmd[0] == "stats_most":
                try:
                    self.__ui_stats_most()
                except ValidationError as ve:
                    print("Validation error:\n"+str(ve))
                except RepositoryError as re:
                    print("Repository error:\n"+str(re))

            else:
                print("Comanda invalida")