from errors.errors import RepositoryError
from domain.entities import Strada, DTO

class FileRepoStrada(object):
    def __init__(self, file_name):
        self.__strazi = []
        self.__file_name = file_name
        self.__dtos = []
        self.__read_from_file()


    def __read_from_file(self):
        try:
            with open(self.__file_name, 'r') as f_strazi:
                lines = f_strazi.readlines()
                
                for line in lines:
                    line = line.split(',')
                    id_strada = int(line[0])
                    nume = line[1]
                    strada = line[2]
                    nr_utilizari = int(line[3])

                    _strada = Strada(id_strada, nume , strada, nr_utilizari)

                    self.__strazi.append(_strada)
            
               
        except ValueError:
            print("Invalid input, abandoning")
        except FileNotFoundError:
            with open(self.__file_name, 'x') as f_strada:
                self.__read_from_file()
        except IndexError:
            print("Invalid input, abandoning")


    def search_strazi_by_nume(self, nume):
        strazi = []
        for _strada in self.__strazi:
            if _strada.get_strada() == nume:
                strazi.append(_strada)
        if strazi == []:
            raise RepositoryError("Nu exista strazi cu acest nume")
        return strazi

    def search_strazi_unice(self):
        st = []
        for _strada in self.__strazi:
            nume_strada = _strada.get_strada()
            if nume_strada not in st:
                st.append(nume_strada)
        if st == []:
            raise RepositoryError("Nu exista strazi")
        return st


    def save_info(self):
        self.__store_to_file()

    def __store_to_file(self):
        with open(self.__file_name, 'w') as f_strazi:
            for _strada in self.__strazi:
                f.write(f"{_strada.get_id()},{_strada.get_nume},{_strada.get_strada},{_strada.get_nr_utilizatori}")


