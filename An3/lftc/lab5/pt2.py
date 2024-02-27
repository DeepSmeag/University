def recursive_descent_backtracking(input_str):
    # Defining the grammar
    grammar = {
        'S': [['I', 'UNAME', 'CODEMAIN']],
        'I': [['#', 'include', '<', 'iostream', '>']],
        'UNAME': [['using', 'namespace', 'std', ';']],
        'SEMICOLON': [[';']],
        'CODEMAIN': [['int', 'main', '(', ')', 'CODEBLOCK']],
        'CODEBLOCK': [['{', 'CODE' ]],
        'CODE': [['DECLARE', 'ASSIGN', 'RETURN']],
        'DECLARE': [['int', 'ID', ';']],
        'ASSIGN': [['x', '=', '2', ';']],
        'RETURN': [['return', '0', ';', '}']],
        'LPAREN': [['(']],
        'RPAREN': [[')']],
        'LBRACE': [['{']],
        'RBRACE': [['}']],
        'LANGLE': [['<']],
        'RANGLE': [['>']],
        'HASHTAG': [['#']],
        'ID': [['x']],
        'CONSTANT': [['2']]
    }

    # globalStack = [tuple(state, ruleIndex, opIndex)]
    globalStack = [('S', 0, 0)] 

    # solutionsStack = [tuple(solutionString, opIndex)]
    solutionsStack = []
    # derivStack = [tuple(state, ruleIndex, opIndex)]
    derivStack = []
    def checkCandidate(candidate):
        # if inputStr starts with candidate
        inputStrNoSpaces = input_str.replace(' ', '')
        # print("CheckCandidate: candidate", candidate[0])
        # print("CheckCandidate: inputstr", inputStrNoSpaces)
        if inputStrNoSpaces.startswith(candidate[0]):
            return True
        return False
    def isSolution(candidate):
        # if inputStr is candidate
        # if input_str == candidate[0] and globalStack == []:
        #     return True
        # return False
        # print("candidate:",candidate)
        inputStrNoSpaces = input_str.replace(' ', '')
        # print(inputStrNoSpaces, '|', candidate[0])
        if inputStrNoSpaces == candidate[0] and globalStack == []:
            return True

    def isTerminal(state):
        # is a terminal if it's not a full upercase word
        for letter in state:
            if not letter in 'ABCDEFGHIJKLMNOPQRSTUVWXYZ':
                return True
        return False
    def backtrack():
        
        # print('Backtracking')
        # pop last solution
        lastSol = solutionsStack.pop()
        #destroy everything with same op from globalStack until it's empty or we find a different op
        while globalStack and globalStack[-1][2] == lastSol[1]:
            globalStack.pop()
        # check if there is another set with same op beyond the last thing
        index = -1
        for i in range(len(globalStack)-1, -1, -1):
            if globalStack[i][2] == lastSol[1]:
                index = i
                break
        if index != -1:
            while len(globalStack) > index:
                globalStack.pop()
        # pop all elements from globalStack until index
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
        # print()
        # print(state, rule, op, end=' ; ')
        if isTerminal(state):
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
                print('No, it\'s not candidate', end=' ; ')
                if not backtrack():
                    return False, derivStack
        else:
            # is a non-terminal, we derive it
            derivStack.append((state, rule, op))
            for symbol in grammar[state][rule][::-1]:
                if(isTerminal(symbol)):
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


from fip import readFip, fipToCode
fipTable = readFip('fip.txt')
input_sequence = fipToCode(fipTable)
print("INPUT: ", input_sequence)
result, derivStack = recursive_descent_backtracking(input_sequence)



if result:
    print("Input accepted by grammar.")
else:
    print("Input not accepted by grammar.")
printDeriv(derivStack)
