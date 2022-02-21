class Book(object):
    def __init__(self, id, title, author, yearAp, price):
        self.__id = id
        self.__title = title
        self.__author = author
        self.__yearAp = yearAp
        self.__price = price

    def getId(self):
        """Getter for id attribute
        returns:
            int: id
        """
        return self.__id

    def getTitle(self):
        """Gett for title attribute
        returns:
            str: title
        """
        return self.__title

    def getAuthor(self):
        """Getter for author
        returns:
            str: author
        """
        return self.__author

    def getYearAp(self):
        """Getter for year of apparition
        returns:
            int: year
        """
        return self.__yearAp

    def getPrice(self):
        """Getter for price of book
        returns:
            int: price of book
        """
        return self.__price

    def __str__(self):
        """To show books when called
        returns:
            string: nice formatted string to display book
        """
        return f"{self.getId()},{self.getTitle()},{self.getAuthor()},{self.getYearAp()},{self.getPrice()}"
