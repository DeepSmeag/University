from errors.errors import ValidationError, RepositoryError

class Console(object):
    def __init__(self, serviceStrada):
        self.__serviceStrada = serviceStrada


    def __print_main_ui(self):
        print("""
            Available commands:

            - 1         Cerinta 1
            - 2         Cerinta 2

            - exit
        
        """)


    def __ui_cerinta1(self):
        inp = input("Nume strada: ")
        try:
            strazi = self.__serviceStrada.cerinta1(inp)
            
            for _strada in strazi:
                print(_strada)
        except RepositoryError as re:
            print(str(re))


    def __ui_cerinta2(self):
        try:
            raspuns = self.__serviceStrada.cerinta2()
            for _item in raspuns:
                print(_item)
        except RepositoryError as re:
            print(str(ve))

    def run(self):
        

        while True:
            self.__print_main_ui()
            inp = input(">>>")

            if inp == "1":
                self.__ui_cerinta1()
            elif inp == "2":
                self.__ui_cerinta2()

            elif inp=="exit":
                # self.__serviceStrada.save_info()
                return