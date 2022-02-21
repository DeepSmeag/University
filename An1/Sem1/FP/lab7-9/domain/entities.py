
class Student(object):
    
    def __init__(self, id_stud, nume, grupa):
        self.__id_stud = id_stud
        self.__nume = nume
        self.__grupa = grupa
    
    def get_id(self):
        return self.__id_stud

    def get_nume(self):
        return self.__nume
    def set_nume(self, value):
        self.__nume = value

    def get_grupa(self):
        return self.__grupa
    def set_grupa(self, value):
        self.__grupa = value

    def __eq__(self, other):
        return self.__id_stud == other.__id_stud

    def __str__(self):
        return "[" + str(self.__id_stud) + "]" + self.__nume + "->" + str(self.__grupa)
        
class Problema_laborator(object):
    def __init__(self, id_prob, desc, deadline):
        self.__id_prob = id_prob
        self.__desc = desc
        self.__deadline = deadline

    def get_id(self):
        return self.__id_prob
        
    def get_desc(self):
        return self.__desc
    def set_desc(self, value):
        self.__desc = value

    def get_deadline(self):
        return self.__deadline
    def set_deadline(self, value):
        self.__deadline = value

    def __eq__(self, other):
        return self.__id_prob == other.__id_prob

    def __str__(self):
        return "[" + str(self.__id_prob) + "]" + self.__desc + "->" + str(self.__deadline)



class Assignment(object):

    def __init__(self, assignment_id, student_id, problem_id, grade = None):
        self.__assignment_id = assignment_id
        self.__student_id = student_id
        self.__problem_id = problem_id
        self.__grade = grade

    def get_assignment_id(self):
        return self.__assignment_id

    def get_student_id(self):
        return self.__student_id

    def get_problem_id(self):
        return self.__problem_id

    def get_grade(self):
        return self.__grade
    def set_grade(self, value):
        self.__grade = value

    def __eq__(self, other):
        return self.__assignment_id == other.__assignment_id

    def __str__(self):
        return "Assignment ID: [" + str(self.__assignment_id) +  "]," + " Student ID: [" + str(self.__student_id) + "]," + " Lab ID: [" + self.__problem_id + "]" + "->" + str(self.__grade)

    
        
    