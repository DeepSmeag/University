from domain.domain import Book


class ServiceBook(object):
    def __init__(self, repoBook):
        self.__repoBook = repoBook

    def add(self, id, title, author, yearAp, price):
        """Service for add functionality
        params:
            id (int): [description]
            title (str): [description]
            author (str): [description]
            yearAp (int): [description]
            price (int): [description]
        """
        self.__repoBook.add(id, title, author, yearAp, price)
        self.__repoBook.saveInfo()

    def rm(self, cif):
        """Service for remove functionality
        params:
            cif (int): the digit to search for
        """

        def cifInYear(year, cif):
            stryear = str(year)
            strcif = str(cif)
            if strcif in stryear:
                return True
            return False

        toDel = []
        books = self.getAll()
        for book in books:
            if cifInYear(book.getYearAp(), cif):
                toDel.append(book.getId())

        self.__repoBook.rm(toDel)
        self.__repoBook.saveInfo()

    def getFilter(self):
        """Service for providing the current filter
        returns:
            [type]: [description]
        """
        return self.__repoBook.getFilter()

    def filter(self):
        """Service for providing the filtered books
        returns:
            list: list of books
        """
        rez = []
        books = self.getAll()
        strfilt, intfilt = self.getFilter()
        if strfilt != "" and intfilt != -1:
            for book in books:
                if strfilt in book.getTitle() and intfilt > book.getPrice():
                    rez.append(book)
        elif strfilt != "" and intfilt == -1:
            for book in books:
                if strfilt in book.getTitle():
                    rez.append(book)
        elif strfilt == "" and intfilt != -1:
            for book in books:
                if intfilt > book.getPrice():
                    rez.append(book)
        else:
            return books
        return rez

    def updateFilter(self, strfilt, intfilt):
        """Service for updating the current filter
        params:
            strfilt (str): [description]
            intfilt (int): [description]
        """
        self.__repoBook.updateFilter(strfilt, intfilt)

    def undo(self):
        """Undo last operation"""
        self.__repoBook.undo()
        self.__repoBook.saveInfo()

    def undodel(self):
        """Undo last del operation"""
        self.__repoBook.undodel()
        self.__repoBook.saveInfo()

    def getAll(self):
        """Service to get all books
        returns:
            list: list of books
        """
        return self.__repoBook.getAll()
