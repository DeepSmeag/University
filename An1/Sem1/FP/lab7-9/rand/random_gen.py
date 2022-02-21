import random as rand
from string import ascii_lowercase, ascii_uppercase
class GenStudents(object):

    def __init__(self):
        pass


    def gen_rand_student(self):

        id_stud = rand.randint(1, 100)


        lower = ascii_lowercase
        upper = ascii_uppercase
        name = rand.choice(upper) + ''.join(rand.choices(lower, k = rand.randint(1,7))) 

        # name = ''.join(rand.choices(lowe, k=nr_litere))
        # name.capitalize()

        group = rand.choice([211,212,213,214,215,216,217])


        return id_stud, name, group

