# problema 10


# Se dă o listă de numere întregi a1,...an, determinați toate sub-secvențele cu lungime mai
# mare decât 2 cu proprietatea că: numerele sunt în ordine crescătoare şi numerele
# consecutive au cel puţin o cifră în comun.


# spatiu de cautare: Toate listele de forma [x1,x2,x3,x4,...,xk] cu k=1,len(l); termenii trebuie sa fie consecutivi in lista de origine
#   Toate subsecventele listei input
#   Complexitate: Suma de Combinari = 2^n, unde n = len(l) ; n^2
# ce e un candidat: O lista de forma [x1,x2,...,xk] cu k=2,len(l)
# ce e solutie: Lista candidat care indeplineste conditia si are minim 2 elemente
# conditia de a fi candidat:s proprietatea ca nr din lista sunt in ordine crescatoare si cele consecutive au cel putin o cifra in comun
# conditie sa poata fi solutie: Minim 2 elemente in lista
l = [11, 12, 13, 14, 15, 16, 16, 1, 31, 5, 13, 1, 3, 5, 56]


def is_asc(x, y):
    return x <= y


def have_common_digit(x: int, y: int):
    temp = [int(digit) for digit in str(x)]
    for element in [int(digidwadwat) for digit in str(y)]:
        if element in temp:
            return True

    return False


def verify(k:int, solution:list):
    if check_indices(solution):
        return condition(k, l)
    return False

def check_indices(l):
    for index in range(1,len(l)):
        if l[index] - l[index-1] != 1:
            return False
    return True

def condition(k: int, l:list):
    return (is_asc(l[k-1], l[k]) and ((have_common_digit(l[k-1], l[k]) and l[k]-l[k-1] == 1) or l[k]-l[k-1] != 1))

def print_sol(solution):
    res = []
    for item in solution:
        res.append(l[item])
    print(res)

def translate_sol(solution):
    res = []
    for item in solution:
        res.append(l[item])
    return res

sol_it = []
sol_rec = []

def iterative():

    # start = 0
    
    
    # while start < len(l)-1:
    #     solution = [l[start]]
    #     end = start+1


    #     while end < len(l) and condition(end, l):
    #         solution.append(l[end])
    #         # print(solution)
    #         sol_it.append(solution[:])
    #         end = end+1
        
    #     start = start+1

        
            
    solution = [-1]
    while len(solution) > 0:
        choosed = False
        while not choosed and solution[-1] < len(l) - 1:
            solution[-1] = solution[-1] + 1
            
            if len(solution) == 1:
                choosed = True
            else:
                choosed = verify(solution[-1], solution)
            
        if choosed:
            if len(solution) >=2:
                print_sol(solution)
                sol_it.append(translate_sol(solution))
            
            solution.append(solution[-1])
        else:
            
            solution = solution[:-1]
            


    




def recursive(start: int, k: int):
    if start == len(l)-1:
        return

    else:
        if k< len(l) and condition(k, l):
            # print(l[start:k+1])
            sol_rec.append(l[start:k+1])
            recursive(start, k+1)
            return

    recursive(start+1, start+2)




recursive(0, 1)
# print("\n")
iterative()

def check():
    for index in range(len(sol_it)):
        
        if sol_it[index] != sol_rec[index]:
            return False
    return True

print(check())
