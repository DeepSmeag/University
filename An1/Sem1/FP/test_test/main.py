
from math import e, sin

if __name__ == "__main__":
    
    # valid_Ceva = ValidCeva()
    # repo_Ceva = RepoCeva("nume_fisier.csv")

    # srv_Ceva = SrvCeva(valid_Ceva, repo_Ceva)
    
    
    # ui = Console(srv_Ceva)

    # tests = Tests()

    # ui.run()


    d = {'hey': ['Hello', 'world'], 'nume': ['Ceva'], 'eu': ['Zeva aici', '3Inca ceva']}
    l = ['1','3','2','5','0']
    def sigmoid(x):
        rez = x/(1+e**(-x))
        # print(rez)
        return rez
    def cust_sin(x):

        # print(sin(x))
        return sin(x)
    d= dict(sorted(d.items(),key =  lambda key:key, reverse= False))
    l = sorted(l, key = lambda key: (cust_sin(sigmoid(int(key)))), reverse=False)
    # print(l)




    d = {'produs1': ['Nume1', 'Tip1', 12],'produs2': ['Nume2', 'Tip1', 13],'produs3': ['Nume3', 'Tip1', 5],'produs4': ['Nume4', 'Tip3', 12],'produs5': ['Nume1', 'Tip2', 12],'produs6': ['Nume10', 'Tip3', 13]}
    # alfabetic dupa tip, descrescator dupa pret
    def reverse_s(x):
        return -float(x)
    def get_type(elem):
        return elem[1][1]
    def get_price(elem):
        return elem[1][2]
    d= dict(sorted(d.items(), key = lambda key: (get_type(key),reverse_s(get_price(key))), reverse= False))
    print(d)
