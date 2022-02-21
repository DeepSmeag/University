from errors.exceptions import ValidationError

class ValidateStudent(object):
    def __init__(self):
        pass

    def validate(self, stud):
        errors = ""
        if stud.get_id() < 0:
            errors +="id invalid\n"
        if stud.get_nume()== "":
            errors +="nume invalid\n"
        if stud.get_grupa()<0:
            errors +="grupa invalida\n"
        
        if len(errors)>0:
            raise ValidationError(errors)

class ValidateProblem(object):
    def __init__(self):
        pass

    def validate(self, prob):
        errors = ""
        id = prob.get_id().split('_')
        try:
            lab = int(id[0])
            problem = int(id[1])
            if lab<=0 or problem<=0:
                raise ValueError
        except IndexError as ie:
            errors +="id invalid\n"
        except ValueError as ve:
            errors +="id invalid\n"
        if prob.get_desc()== "":
            errors +="desc invalid\n"
        if prob.get_deadline()<0 or prob.get_deadline()>31:
            errors +="deadline invalid\n"
        
        if len(errors)>0:
            raise ValidationError(errors)

class ValidateAssignment(object):
    def __init__(self):
        pass

    def validate(self, assignment, student_is_valid, problem_is_valid):
        errors = ""
        


        if assignment.get_assignment_id() < 0:
            errors +="id invalid\n"
        if assignment.get_student_id() < 0 or not student_is_valid:
            errors +="id student invalid\n"
        
        try:
            id_prob = assignment.get_problem_id().split('_')
            try:
                lab = int(id_prob[0])
                problem = int(id_prob[1])
                if lab<=0 or problem<=0 or not problem_is_valid:
                    raise ValueError
            except IndexError as ie:
                raise AttributeError
            except ValueError as ve:
                raise AttributeError
        except AttributeError as ae:
            errors +="id problema invalid\n"
        
        nota = assignment.get_grade()
        if nota != None and (nota<=0.0 or nota>10.0):
            errors +="nota invalida\n"
        
        

        if len(errors)>0:
            raise ValidationError(errors)