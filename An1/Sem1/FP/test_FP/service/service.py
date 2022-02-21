

class serviceStrada(object):
    def __init__(self, validStrada, repoStrada):
        self.__validStrada = validStrada
        self.__repoStrada = repoStrada




    def get_all(self):
        return self.__repoStrada.get_all()


    def cerinta1(self, nume):
        return self.__search_strazi_by_nume(nume)

    def __search_strazi_by_nume(self, nume):
        
        strazi = self.__repoStrada.search_strazi_by_nume(nume)

        strazi.sort(key = lambda strada: strada.get_nume(), reverse=True)
        
        return strazi
        

    def __search_strazi_unice(self):
        return self.__repoStrada.search_strazi_unice()


    def cerinta2(self):
        strazi = self.__search_strazi_unice()
        
        cele_mai_utilizate = []

        for _strada_nume in strazi:
            lista_interes = self.__search_strazi_by_nume(_strada_nume)
            highest = 0
            highest_nume = ''
            for _item in lista_interes:

                if _item.get_nr_utilizari() > highest:
                    highest = _item.get_nr_utilizari()
                    highest_nume = _item.get_nume()
                    
            
            
            
            cele_mai_utilizate.append(f"{_strada_nume}, {highest_nume}")
        
        if cele_mai_utilizate == []:
            return ["Nu sunt strazi"]
        return cele_mai_utilizate

    def save_info(self):
        self.__repoStrada.save_info()
