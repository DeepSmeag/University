class UI(object):
    def __init__(self, serviceBook):
        self.__serviceBook = serviceBook

    def __print(self):
        """Prints the current repo on demand"""
        books = self.__serviceBook.getAll()
        for book in books:
            print(book)

    def __print_filter(self):
        """Constantly prints the current filter and list of filtered books"""
        strfilt, intfilt = self.__serviceBook.getFilter()
        if strfilt != "":
            print(strfilt + " : " + str(intfilt))
        else:
            print('""' + " : " + str(intfilt))
        booksFiltered = self.__serviceBook.filter()
        if booksFiltered != []:
            for book in booksFiltered:
                print(book)
        else:
            print(
                """
                  Filter does not include any book
                  """
            )

    def run(self):
        """The main part of the UI, runs as long as the applciation does"""

        while True:
            self.__print_filter()
            print(
                """
                  add
                  rm
                  filter
                  undo
                  undodel
                  print
                  
                  exit
                  
                  """
            )
            cmd = input(">>>")
            if cmd == "add":
                self.__ui_add()
            elif cmd == "rm":
                self.__ui_rm()
            elif cmd == "filter":
                self.__ui_filter()
            elif cmd == "undo":
                self.__ui_undo()
            elif cmd == "undodel":
                self.__ui_undodel()
            elif cmd == "print (maybe debug)":
                self.__ui_print()
            elif cmd == "exit":
                return

    def __ui_add(self):
        """UI for add functionality"""
        id = int(input("ID:"))
        title = input("Title:")
        author = input("Author:")
        yearAp = int(input("Year apparition:"))
        price = int(input("Price:"))
        self.__serviceBook.add(id, title, author, yearAp, price)

    def __ui_rm(self):
        """UI for remove functionality"""
        cif = input("Digit: ")
        self.__serviceBook.rm(cif)

    def __ui_filter(self):
        """UI for update filter functionality"""
        strfilt = input("Filter Title:")
        intfilt = int(input("Filter Price:"))

        self.__serviceBook.updateFilter(strfilt, intfilt)

    def __ui_undo(self):
        """UI for undo functionality"""
        self.__serviceBook.undo()

    def __ui_undodel(self):
        """UI for undo functionality"""
        self.__serviceBook.undodel()

    def __ui_print(self):
        self.__print()
