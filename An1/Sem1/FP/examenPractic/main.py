from repo.repo import RepoBook, RepoTestBook
from tests.tests import Tests
from ui.ui import UI
from utils.utils import ServiceBook


if __name__ == "__main__":
    """The core of the application, main function
    """
    repoBook = RepoBook("books.txt")
    repoTestBook = RepoTestBook("booksTest.txt")

    serviceBook = ServiceBook(repoBook)
    serviceTestBook = ServiceBook(repoTestBook)

    ui = UI(serviceBook)

    tests = Tests(serviceTestBook, repoTestBook)

    tests.runAllTests()

    ui.run()
