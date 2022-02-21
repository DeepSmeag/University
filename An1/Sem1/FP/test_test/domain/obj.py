class Object(object):
    def __init__(self, item1, item2):
        self.__item1 = item1
        self.__item2 = item2

    def get_item1(self):
        return self.__item1
    def get_item2(self):
        return self.__item2

    def set_item2(self, n_item2):
        self.__item2 = n_item2
