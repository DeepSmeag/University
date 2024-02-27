class AutomatFinit:
    def __init__(self):
        self.stari = set()
        self.alfabet = set()
        self.tranzitii = {}
        self.stari_finale = set()
        self.stare_initiala = None

    def adauga_stare(self, stare):
        self.stari.add(stare)

    def adauga_alfabet(self, simbol):
        self.alfabet.add(simbol)

    def adauga_tranzitie(self, stare_sursa, simbol, stare_destinatie):
        self.tranzitii[(stare_sursa, simbol)] = stare_destinatie

    def adauga_stare_finala(self, stare):
        self.stari_finale.add(stare)

    def seteaza_stare_initiala(self, stare):
        self.stare_initiala = stare

    def este_acceptata(self, secventa):
        stare_curenta = self.stare_initiala
        for simbol in secventa:
            if (stare_curenta, simbol) in self.tranzitii:
                stare_curenta = self.tranzitii[(stare_curenta, simbol)]
            else:
                return False
        return stare_curenta in self.stari_finale

    def cel_mai_lung_prefix_acceptat(self, secventa):
        stare_curenta = self.stare_initiala
        lungime_prefix = 0
        for i, simbol in enumerate(secventa):
            if (stare_curenta, simbol) in self.tranzitii:
                stare_curenta = self.tranzitii[(stare_curenta, simbol)]
                lungime_prefix = i + 1
            else:
                break
        return secventa[:lungime_prefix]

def citeste_automat_de_la_tastatura():
    automat = AutomatFinit()

    # Citire stari
    stari_input = input("Stari: ")
    stari = stari_input.split(',')
    for stare in stari:
        automat.adauga_stare(stare.strip())

    # Citire alfabet
    alfabet_input = input("Alfabet: ")
    alfabet = alfabet_input.split(',')
    for simbol in alfabet:
        automat.adauga_alfabet(simbol.strip())

    # Citire tranzitii
    print("Tranzitii")
    while True:
        tranzitie_input = input("Tranzitie: ")
        if tranzitie_input.lower() == 'stop':
            break
        tranzitie = tranzitie_input.split(',')
        automat.adauga_tranzitie(tranzitie[0].strip(), tranzitie[1].strip(), tranzitie[2].strip())

    stari_finale_input = input("Stari finale: ")
    stari_finale = stari_finale_input.split(',')
    for stare in stari_finale:
        automat.adauga_stare_finala(stare.strip())

    stare_initiala = input("Stare initiala: ")
    automat.seteaza_stare_initiala(stare_initiala.strip())

    return automat


def citeste_automat_din_fisier(nume_fisier):
    automat = AutomatFinit()
    with open(nume_fisier, 'r') as file:
        for line in file:
            tokens = line.split()
            if len(tokens) == 0:
                continue
            if tokens[0] == "Stare":
                automat.adauga_stare(tokens[1])
            elif tokens[0] == "Alfabet":
                automat.adauga_alfabet(tokens[1])
            elif tokens[0] == "Tranzitie":
                automat.adauga_tranzitie(tokens[1], tokens[2], tokens[3])
            elif tokens[0] == "StareFinala":
                automat.adauga_stare_finala(tokens[1])
            elif tokens[0] == "StareInitiala":
                automat.seteaza_stare_initiala(tokens[1])
    return automat

def main():
    nume_fisier = "automat.txt"
    automat = citeste_automat_din_fisier(nume_fisier)
    #automat = citeste_automat_de_la_tastatura()
    while True:
        print("1. Afiseaza multimea starilor")
        print("2. Afiseaza alfabetul")
        print("3. Afiseaza tranzitiile")
        print("4. Afiseaza multimea starilor finale")
        print("5. Verifica daca o secventa este acceptata")
        print("6. Determina cel mai lung prefix acceptat")
        optiune = input()

        if optiune == "1":
            print("Multimea starilor:", automat.stari)
        elif optiune == "2":
            print("Alfabetul:", automat.alfabet)
        elif optiune == "3":
            print("Tranzitiile:")
            for (sursa, simbol), destinatie in automat.tranzitii.items():
                print(f"{sursa} --({simbol})--> {destinatie}")
        elif optiune == "4":
            print("Multimea starilor finale:", automat.stari_finale)
        elif optiune == "5":
            secventa = input("Introdu o secventa de caractere: ")
            if automat.este_acceptata(secventa):
                print("Secventa este acceptata de automat.")
            else:
                print("Secventa nu este acceptata de automat.")
        elif optiune == "6":
            secventa = input("Introdu o secventa de caractere: ")
            prefix_acceptat = automat.cel_mai_lung_prefix_acceptat(secventa)
            print("Cel mai lung prefix acceptat:", prefix_acceptat)

if __name__ == "__main__":
    main()
