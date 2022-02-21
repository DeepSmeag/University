def creeaza_joc (id_joc, titlu, rating):
    # Functie care creeaza un joc pe baza unui id_joc intreg, unui titlu si al unui rating 
    # input: id_joc - intreg
    #        titlu - string
    #        rating - float
    # output: rez - un joc cu idul id_joc, titlul titlu si ratingul rating
    return {
        'id_joc': id_joc,
        'titlu': titlu,
        'rating': rating
    }
    pass


def get_id_joc(joc):
    # Functie care returneaza idul jocului joc
    #input: joc- un joc
    #output: rez - intreg, idul jocului joc
    return joc['id_joc']

def get_title(joc):
    return joc['titlu']

def get_rating(joc):
    return joc['rating']

def egale_jocuri(joc0, joc1):
    return get_id_joc(joc0) == get_id_joc(joc1)

def to_str_joc(joc):
    return str(joc['id_joc'])+":"+joc["titlu"]+"->"+str(joc["rating"])

def test_creeaza_joc():
    id_joc = 23
    titlu = "Fifa"
    rating = 89.67
    joc = creeaza_joc(id_joc, titlu, rating)
    assert(get_id_joc(joc)==id_joc)
    assert(get_title(joc)==titlu)
    assert(abs(get_rating(joc)-rating)<0.00001)

def valideaza_joc(joc):
    #Functie care verifica daca idul jocului joc este pozitiv, daca titlul este nevid si daca ratingul este cuprins intre 0 si 100
    # input: joc - un joc
    # output: -, daca jocul este valid
    # raises: Exception cu textul (1)
    #   "id invalid!\n", daca id_joc <= 0
    #   "titlu invalid!\n", daca titlul == ""
    #   "rating invalid!\n", daca rating<0 sau rating >100
    err = ""
    if get_id_joc(joc)<=0:
        err += "id invalid!\n"
    if get_title(joc)=="":
        err += "titlu invalid!\n"
    rating = get_rating(joc)
    if rating<0 or rating>100:
        err += "rating invalid!\n"
    if len(err)>0:
        raise Exception(err)

def adauga_joc_in_lista(l, joc):
    # Functie care adauga un joc joc in lista de jocuri l unic identificabile dupa id_joc doar daca nu exista deja un joc cu acelasi id in lista
    # input: l- lista de jocuri unic identificabile dupa id_joc
    #        joc - un joc
    # output: -, daca adaugarea are loc 
    #raises: Exception cu textul (2)
    #           "joc existent!\n", daca exista deja un joc cu acelasi id_joc ca si joc in lista l 
    for _joc in l:
        if egale_jocuri(_joc, joc):
            raise Exception("joc existent!\n")
    l.append(joc)


def srv_adauga_in_lista(l, id_joc, titlu, rating):
    # Functie care creeaza un joc pe baza id_joc intreg, unui titlu si unui rating nr. real, incearca sa valideze jocul si daca acesta este valid incearca sa il adauge in lista de jocuri unic identificabile l doar daca nu exista deja un joc cu acelasi id_joc in lista
    # input: l - lista de jocuri unic identificabile dupa id_joc
    #        id_joc - un intreg
    #        titlu - string
    #        rating - float
    # output: -, daca adaugarea reuseste cu succes
    # raises: Exception cu textul 
    #           (1) si (2)
    joc = creeaza_joc(id_joc, titlu, rating)
    valideaza_joc(joc)
    adauga_joc_in_lista(l, joc)
    pass

def test_srv_adauga_in_lista():
    l = []
    srv_adauga_in_lista(l, 23, "Fifa", 56.78)
    assert(len(l)==1)
    try:
        srv_adauga_in_lista(l,23,"NBA", 45.67)
        assert(False)
    except Exception as ex:
        assert(str(ex) == "joc existent!\n")
    try:
        srv_adauga_in_lista(l, 23, "", 56.78)
        assert(False)
    except Exception as ex:
        assert(str(ex) == "titlu invalid!\n")

def test_adauga_joc_in_lista():
    l=[]
    joc = creeaza_joc(23, "Fifa", 45.67)
    adauga_joc_in_lista(l, joc)
    assert(len(l)==1)
    assert(get_id_joc(joc)==get_id_joc(l[0]))
    assert(get_title(joc)==get_title(l[0]))
    assert(get_rating(joc)==get_rating(l[0]))
    try:
        adauga_joc_in_lista(l, joc)
        assert(False)
    except Exception as ex:
        assert(str(ex) == "joc existent!\n")
    pass

def test_valideaza_joc():
    joc = creeaza_joc(23, "Fifa", 89.45)
    valideaza_joc(joc)
    invalid_id_joc = creeaza_joc(-23, "Fifa", 45.67)
    try:
        valideaza_joc(invalid_id_joc)
        assert(False)
    except Exception as ex:
        assert(str(ex) == "id invalid!\n")
    invalid_joc = creeaza_joc(-23, "", -45.67)
    try:
        valideaza_joc(invalid_joc)
        assert(False)
    except Exception as ex:
        assert(str(ex) == "id invalid!\ntitlu invalid!\nrating invalid!\n")


def run_teste():

    test_creeaza_joc()
    test_valideaza_joc()
    test_adauga_joc_in_lista()
    test_srv_adauga_in_lista()


def ui_adauga_joc(l):
    try:
        id_joc = int(input("id joc:"))
    except ValueError:
        print("valoare numerica invalida pentru id!")
        return
    titlu = input("titlu:")
    try:
        rating = float(input("rating:"))
    except ValueError:
        print("valoare numerica invalida pentru rating!")
        return
    srv_adauga_in_lista(l, id_joc, titlu, rating)


def ui_print_jocuri(l):
    for _joc in l:
        print(to_str_joc(_joc))
    pass

def run():
    l = []
    while True:
        cmd = input(">>>")
        if cmd == "exit":
            return

        if cmd == "":
            continue
        if cmd == "add_joc":
            try:
                ui_adauga_joc(l)
            except Exception as ex:
                print(ex)
        elif cmd == "print_jocuri":
            ui_print_jocuri(l)
        else:
            print("comanda invalida!")


if __name__ == '__main__':
    run_teste()
    run()


