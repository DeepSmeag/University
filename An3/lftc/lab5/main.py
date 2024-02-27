def recursive_descent_backtracking(input_str):
    # Defining the grammar
    grammar = {
        'A': [['a', 'A', 'a'], ['a', 'B'], ['a']],
        'B': [['b', 'B'], ['b']]
    }

    # globalStack = [tuple(state, ruleIndex, opIndex)]
    globalStack = [('A', 0, 0)] 

    # solutionsStack = [tuple(solutionString, opIndex)]
    solutionsStack = []
    # derivStack = [tuple(state, ruleIndex, opIndex)]
    derivStack = []
    def checkCandidate(candidate):
        # if inputStr starts with candidate
        if input_str.startswith(candidate[0]):
            return True
        return False
    def isSolution(candidate):
        # if inputStr is candidate
        if input_str == candidate[0] and globalStack == []:
            return True
        return False
    
    def backtrack():
        # pop last solution
        lastSol = solutionsStack.pop()
        #destroy everything with same op from globalStack until it's empty or we find a different op
        while globalStack and globalStack[-1][2] == lastSol[1]:
            globalStack.pop()
        # if globalStack is empty, go to derivation and use that
        if globalStack == []:
            # print(derivStack, end=' ; ')
            # while there are derivations where we cannot increase the rule, pop them (it's the end of the branch)
            while derivStack and derivStack[-1][1] == len(grammar[derivStack[-1][0]]) - 1:
                # print('Popping derivStack')
                derivStack.pop()
            # if we get an empty derivStack, we are at the end of BT
            if derivStack == []:
                # print('BT ended')
                return False
            else:
                # pop all solution with op > derivStack[-1][2]
                while solutionsStack and solutionsStack[-1][1] > derivStack[-1][2]:
                    solutionsStack.pop()
                # we have other derivations to try out
                # increase rule
                # print('Increasing rule', end=' ; ')
                lastDeriv = derivStack.pop()
                newLastDeriv = (lastDeriv[0], lastDeriv[1]+1, lastDeriv[2])
                derivStack.append(newLastDeriv)
                # add the derivation symbols to globalStack
                rule = derivStack[-1][1]
                op = derivStack[-1][2]
                # print(derivStack, end=' ; ')
                for symbol in grammar[derivStack[-1][0]][rule][::-1]:
                    if(symbol.islower()):
                        globalStack.append((symbol, rule, op+1))
                    else:
                        globalStack.append((symbol, 0, op+1))
                # print(globalStack, end=' ; ')
        return True

    while globalStack:
        # print('globalStack', globalStack, ' ------------ ', 'solutionsStack: ', solutionsStack, ' ------------ ', 'derivStack: ', derivStack)
        state, rule, op = globalStack.pop()
        
        if state.islower():
            # it's a terminal, we've got a lot of work to do
            if(solutionsStack != []):
                currentSol = solutionsStack[-1].copy()
            else:
                currentSol = ["", op]
            currentSol[0] += state
            currentSol[1] = op
            solutionsStack.append(currentSol)
            # print(currentSol, end=' ; ')
            if checkCandidate(currentSol):
                # print('Yes, it\'s candidate', end=' ; ')
                if isSolution(currentSol):
                    # print('Yes, it\'s solution', end=' ; ')
                    return True, derivStack
                else:
                    if len(currentSol[0]) >= len(input_str):
                        # we've gone too far
                        if not backtrack():
                            return False, derivStack
            else:
                # print('No, it\'s not candidate', end=' ; ')
                if not backtrack():
                    return False, derivStack
        else:
            # is a non-terminal, we derive it
            derivStack.append((state, rule, op))
            for symbol in grammar[state][rule][::-1]:
                if(symbol.islower()):
                    globalStack.append((symbol, rule, op+1))
                else:
                    globalStack.append((symbol, 0, op+1))
        # print()            


    return False, derivStack

def printDeriv(derivStack):
    print('Derivation: ', end='')
    for state, rule, op in derivStack:
        print("{}{}".format(state, rule), end='')
    print()

# Test the backtracking algorithm
input_sequence = "aaabbb"

result = recursive_descent_backtracking(input_sequence)
for seq in [input_sequence, "bab", "aaabb", "aaabbbbbb", "aaabbb", "aaabaa"]:
    print(seq, end=': ')
    result, derivStack = recursive_descent_backtracking(seq)


    if result:
        print("Input accepted by grammar.")
    else:
        print("Input not accepted by grammar.")
    printDeriv(derivStack)
