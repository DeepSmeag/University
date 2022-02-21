from errors.errors import ValidationError

class validStrada(object):
    def __init__(self):
        pass


    def valid(id, nume, strada, numar_utilizari):
        if id<0:
            raise ValidationError()

        if numar_utilizari<0:
            raise ValidationError()