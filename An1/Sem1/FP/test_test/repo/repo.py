from domain.obj import Object
class FileRepo(object):
    def __init__(self, file_name):
        self.__repo = []
        self.__file_name = file_name

        self.__read_from_file()

    def add_obj(self, item1, item2):
        obj = Object(item1, item2)
        self.__repo.append(obj)

    def search_for_obj(self):
        pass


    def __read_from_file(self):
        try:
            with open(self.__file_name, 'r') as f_obj:
                line = f_obj.readline().strip()
                while line != "":
                    line = line.split(",")
                    obj = Object(ceva = ceva, ceva2 = ceva2)

                    self.__repo.append(obj)

                    line = f_obj.readline().strip()
        except:
            pass


    def save_info(self):
        self.__store_to_file()
        
    def __store_to_file(self):
        with open(self.__file_name, 'w') as f_obj:
            for _obj in self.__repo:
                f_obj.write(f"{_obj.get_item1()},{_obj.get_item2}\n")

    def search_entries_by_item2(self, item2):
        list = []
        try:
            for item in self.__repo:
                if item.get_item2() == item2:
                    list.append(item)
            if list == []:
                raise EmptyError
        except EmptyError:
            print("List is empty nu exista ce vrem noi")
        
        return list

    def get_distinct_item2(self):
        list = []
        for item in self.__repo:
            for thing in item.get_item2().split(" "):
                if thing not in list:
                    list.append(thing)

        return list
        