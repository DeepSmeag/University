from errors.errors import ValidationError, RepositoryError
class Console(object):
    def __init__(self, srvObject):
        self.__srv_obj = srvObject

    def run(self):
        self.__main_ui()


    def __print_main_ui(self):
        print("""
                Available commands:

                - cerinta1
                - cerinta2

                - exit
            
            """)
    def __main_ui(self):
        self.__print_main_ui()
        while True:
            

            inp = input(">>>")

            if inp == "ceritna1":
                self.__ui_cerinta1()
            elif inp == "cerinta2":
                pass

            elif inp == "exit":
                return

    def __ui_cerinta1(self):
        inp = input("Input pt asta")
        try:
            self.__srv_obj.ceritna1(inp)
        except ValidationError as ve:
            print(str(ve))
        except RepositoryError as re:
            print(str(re))
