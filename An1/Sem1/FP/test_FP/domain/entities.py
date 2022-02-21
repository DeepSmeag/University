

class Strada(object):
    def __init__(self, id, nume, strada, numar_utilizari):
        self.__id = id
        self.__nume = nume
        self.__strada = strada
        self.__numar_utilizari = numar_utilizari


    def get_id(self):
        return self.__id

    def get_nume(self):
        return self.__nume

    def get_strada(self):
        return self.__strada

    def get_nr_utilizari(self):
        return self.__numar_utilizari


    def __eq__(self, other):
        return self.get_id() == other.get_id()

    def __str__(self):
        return f"{self.get_id()}, {self.get_nume()}, {self.get_strada()}, {self.get_nr_utilizari()}"


class DTO(object):
    def __init__(self, nume, counter):
        self.__nume = nume
        self.__counter = counter

    def get_nume(self):
        return self.__nume
    def get_counter(self):
        return self.__counter
    def inc_counter(self):
        self.__counter = self.__counter + 1