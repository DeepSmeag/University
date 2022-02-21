from domain.domain import Book


class RepoBook(object):
    def __init__(self, filename):
        self.__filename = filename
        self._books = []
        self._last_state = []
        self._last_state_del = []
        self._strfilt = ""
        self._intfilt = -1
        self.__load()
        self.__saveLastState()
        self.__saveLastDelState()

    def getFilter(self):
        """Getter for filter to print in UI
        returns:
            str,int: the filters
        """
        return self._strfilt, self._intfilt


    def updateFilter(self, strfilt, intfilt):
        """Updates the filter
        params:
            strfilt (str): title filter
            intfilt (int): price filter
        """
        self._strfilt = strfilt
        self._intfilt = intfilt

    def add(self, id, title, author, yearAp, price):
        """Add book to repo
        params:
            id (int): [description]
            title (str): [description]
            author (str): [description]
            yearAp (int): [description]
            price (int): [description]
        """
        self.__saveLastState()
        book = Book(id, title, author, yearAp, price)
        self._books.append(book)

    def __saveLastState(self):
        """Save previous state of repo"""
        self._last_state.clear()
        for book in self._books:
            self._last_state.append(book)

    def __restoreLastState(self):
        """Restore last state of repo"""
        self._books.clear()
        for book in self._last_state:
            self._books.append(book)

    def __saveLastDelState(self):
        """Save last state of repo before a delete"""
        self._last_state_del.clear()
        for book in self._books:
            self._last_state_del.append(book)

    def __restoreLastDelState(self):
        """Restore last state of repo before a delete"""
        self._books.clear()
        for book in self._last_state_del:
            self._books.append(book)

    def undo(self):
        """Undo by calling restore function"""
        self.__restoreLastState()

    def undodel(self):
        """Undo last delete"""
        self.__restoreLastDelState()

    def rm(self, toDel):
        """Removes book based on criteria
        params:
            toDel (list): the list of books' ids to delete
        """
        self.__saveLastState()
        self.__saveLastDelState()
        for id in toDel:
            for book in self._books:
                if book.getId() == id:
                    self._books.remove(book)

    def __load(self):
        """Load function called to load the repo from file"""
        with open(self.__filename, "r") as f:
            lines = f.readlines()
            for line in lines:

                line = line.strip().split(",")

                book = Book(int(line[0]), line[1], line[2], int(line[3]), int(line[4]))
                self._books.append(book)

    def _store(self):
        """Save the repo in a file (or update it)"""
        with open(self.__filename, "w") as f:
            for book in self._books:
                f.write(
                    f"{int(book.getId())},{book.getTitle()},{book.getAuthor()},{int(book.getYearAp())},{int(book.getPrice())}\n"
                )

    def saveInfo(self):
        """Save info interface to utils"""
        self._store()

    def getAll(self):
        """Get all books
        returns:
            list: the list of books
        """
        return self._books


class RepoTestBook(RepoBook):
    def __init__(self, filename):
        super().__init__(filename)
        self.__filename = filename
        self._books = []
        self._last_state = []

    def purge(self):
        """Clear test file so that other tests can start clean"""
        self._books.clear()
        self._store()
