


class SrvCeva(object):
    def __init__(self, repoCeva, validCeva):
        self.__validCeva = validCeva
        self.__repoCeva = repoCeva

    def add_obj(self, item1, item2):
        self.__validCeva.validate(item1, item2)
        self.__repoCeva.add_obj(item1, item2)
        self.save_info()

    def cerinta1(self, input):
        list = []

        list_obj = self.__repoCeva.get_all()
        for item in list_obj:
            if input in item.get_item2():
                list.append(item)

        if list == []:
            raise EmptyError("There are no such objects")

        list.sort(key = lambda object: object.get_item2(), reverse=True)

    def cerinta2(self):
        list = self.__repoCeva.get_distinct_item2()
        for item2 in list:
            list2 = self.__repoCeva.search_entries_by_item2()
            if list2 == []:
                raise EmptyError("empty")
            print(item2, ":", list2.split(","))


    def save_info(self):
        self.__repoCeva.save_info()