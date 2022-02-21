from errors.errors import ValidationError, RepositoryError

from domain.entities import Strada
from repo.repo import FileRepoStrada
from service.service import serviceStrada
from valid.valid import validStrada

class Testing(object):
    def __init__(self):
        self.__validStrada = validStrada()
        self.__repoStrada = FileRepoStrada("test_file.csv")
        self.__serviceStrada = serviceStrada(self.__validStrada, self.__repoStrada)

    def __test_create_object(self):
        strada = Strada(97,'W903','Dorobantilor',9)
        assert strada.get_id() == 97
        assert strada.get_nume() == 'W903'
        assert strada.get_strada() == 'Dorobantilor'
        assert strada.get_nr_utilizari() == 9

    def __test_cerinta_1(self):
        t_list = self.__serviceStrada.cerinta1("Dorobantilor")
        strada1 = Strada(7,'W904','Dorobantilor',17)
        strada2 = Strada(97,'W903','Dorobantilor',9)
        assert t_list[0] == strada1
        assert t_list[1] == strada2

    def __test_cerinta_2(self):
        pass


    def run(self):
        self.__test_create_object()
        self.__test_cerinta_1()
        self.__test_cerinta_2()


        print("All tests passed")