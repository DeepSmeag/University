from domain.domain import Book


class Tests(object):
    def __init__(self, serviceBook, repoBook):
        self.__serviceBook = serviceBook
        self.__repoBook = repoBook

    def testgen(self):
        """Testing that objects get generated correctly"""
        id = 1
        title = "Ion"
        author = "Rebreanu"
        yearAp = "1900"
        book = Book(id, title, author, yearAp)
        assert book.getId() == id
        assert book.getTitle() == title
        assert book.getAuthor() == author
        assert book.getYearAp() == yearAp

    def testadd(self):
        """Test add functionality"""
        id = 1
        title = "Ion"
        author = "Rebreanu"
        yearAp = "1900"
        price = 10
        assert len(self.__serviceBook.getAll()) == 0
        self.__serviceBook.add(id, title, author, yearAp, price)
        assert len(self.__serviceBook.getAll()) == 1
        self.__repoBook.purge()

    def testrm(self):
        """Test remove functionality"""
        self.__serviceBook.add(1, "title1", "author1", 1905, 10)
        self.__serviceBook.add(2, "title2", "author2", 1902, 15)
        self.__serviceBook.add(3, "title3", "author3", 1903, 20)
        self.__serviceBook.add(4, "title4", "author4", 1904, 25)
        self.__serviceBook.rm(3)

        books = self.__serviceBook.getAll()
        for book in books:
            if book.getYearAp() == 1903:
                assert False
        self.__repoBook.purge()

    def testgetfilter(self):
        """Testing the getfilter function from serviceBook"""

        strfilt, intfilt = self.__serviceBook.getFilter()
        assert strfilt == ""
        assert intfilt == -1

        self.__repoBook.purge()

    def testupdatefilter(self):
        """Testing the update filter functionality from serviceBook"""
        strfilt, intfilt = self.__serviceBook.getFilter()
        assert strfilt == ""
        assert intfilt == -1

        self.__serviceBook.updateFilter("alo", 100)
        strfilt, intfilt = self.__serviceBook.getFilter()

        assert strfilt == "alo"
        assert intfilt == 100

    def testfilter(self):
        """Test filter functionality"""
        self.__serviceBook.add(1, "title1", "author1", 1905, 10)
        self.__serviceBook.add(2, "title2", "author2", 1902, 15)
        self.__serviceBook.add(3, "title3", "author3", 1903, 20)
        self.__serviceBook.add(4, "title4", "author4", 1904, 25)

        self.__serviceBook.updateFilter("tit", 10)
        filter1 = self.__serviceBook.filter()
        assert filter1 == []

        self.__serviceBook.updateFilter("tit", 26)
        filter2 = self.__serviceBook.filter()
        assert self.__serviceBook.getAll() == filter2

        self.__serviceBook.updateFilter("tit", -1)
        filter3 = self.__serviceBook.filter()
        assert self.__serviceBook.getAll() == filter3

        self.__serviceBook.updateFilter("", 11)
        filter4 = self.__serviceBook.filter()
        assert len(filter4) == 1 and filter4[0].getPrice() == 10

        self.__serviceBook.updateFilter("", -1)

        self.__repoBook.purge()

    def testundo(self):
        """Test undo functionality"""
        self.__serviceBook.add(1, "title1", "author1", 1905, 10)
        self.__serviceBook.add(2, "title2", "author2", 1902, 15)
        self.__serviceBook.add(3, "title3", "author3", 1903, 20)
        self.__serviceBook.add(4, "title4", "author4", 1904, 25)

        self.__serviceBook.undo()

        assert len(self.__serviceBook.getAll()) == 3
        self.__serviceBook.rm(9)
        assert len(self.__serviceBook.getAll()) == 0
        self.__serviceBook.undo()
        assert len(self.__serviceBook.getAll()) == 3

        self.__repoBook.purge()

    def testdelundo(self):
        """Tests undo last del functionality"""
        self.__serviceBook.add(1, "title1", "author1", 1905, 10)
        self.__serviceBook.add(2, "title2", "author2", 1902, 15)
        self.__serviceBook.add(3, "title3", "author3", 1903, 20)
        self.__serviceBook.add(4, "title4", "author4", 1904, 25)

        self.__serviceBook.rm(9)
        assert len(self.__serviceBook.getAll()) == 0
        self.__serviceBook.undodel()
        assert len(self.__serviceBook.getAll()) == 4
        self.__serviceBook.rm(5)
        assert len(self.__serviceBook.getAll()) == 3
        self.__serviceBook.undodel()
        assert len(self.__serviceBook.getAll()) == 4
        self.__repoBook.purge()

    def runAllTests(self):
        """Run all tests"""
        print(
            """
              
              Running tests...
              
              """
        )

        self.testadd()
        self.testrm()
        self.testgetfilter()
        self.testupdatefilter()
        self.testfilter()
        self.testundo()
        self.testdelundo()

        print(
            """
              
              Finished running tests!
              
        """
        )
