
from valid.valid import validStrada
from service.service import serviceStrada
from ui.ui import Console
from repo.repo import FileRepoStrada

from testing.tests import Testing

if __name__ == "__main__":
    validStrada = validStrada()

    repoStrada = FileRepoStrada("strazi.csv")

    serviceStrada = serviceStrada(validStrada, repoStrada)


    ui = Console(serviceStrada)

    tests = Testing()
    tests.run()

    ui.run()