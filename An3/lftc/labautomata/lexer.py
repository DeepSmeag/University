from afd import DFA

class Lexer:
    def __init__(self, fileName):
        self.input = None
        self.fileName = fileName
        self.pos = 0
        self.afds = []
        self.ts = {}
        self.constants = {}
        self.ids = {}

    def readInput(self):
        # read from fileName
        with open(self.fileName, 'r') as file:
            self.input = file.read()

    def addAfd(self, fileName):
        # initiate afd from the rules defined in fileName
        self.afds.append(self.load_dfa_from_file(fileName))

    def runLexer(self):
        # using the afd's, line by line process the input and check for the longest prefix
        # then print the longest prefix and the type of token
        lines = self.input.split('\n')
        for line in lines:
            # print("Line: ----------------------\n", line,'\n----------------------\n')
            index = 0
            while index < len(line):
                afdIndex = 0
                for afd in self.afds:
                    prefix = afd.process_longest_prefix(line[index:])
                    if prefix:
                        if prefix not in self.ts:
                            self.ts[prefix] = (afdIndex, len(self.ts))
                        if (afdIndex == 1 or afdIndex == 2) and prefix not in self.constants:
                            self.constants[prefix] = len(self.constants)
                        elif (afdIndex == 3) and prefix not in self.ids:
                            self.ids[prefix] = len(self.ids)
                        # print(prefix, '----', afdIndex)
                        index += len(prefix) - 1
                        break
                    afdIndex += 1
                index += 1
    def buildFIP(self):
        for key in self.ts:


            if(key in self.constants):
                # print(key, '----', self.ts[key], '----', self.constants[key])
                # write a formatted version of the print above
                print(f'{key:12} | {self.ts[key][0]:3} -- {self.ts[key][1]:3} | {self.constants[key]:10}')
            elif(key in self.ids):
                # print(key, '----', self.ts[key], '----', self.ids[key])
                print(f'{key:12} | {self.ts[key][0]:3} -- {self.ts[key][1]:3} | {self.ids[key]:10}')
            else:
                # print(key, '----', self.ts[key], '----', 'XXX')
                print(f'{key:12} | {self.ts[key][0]:3} -- {self.ts[key][1]:3} | {"XXX":10}')

    def showDfas(self):
        for afd in self.afds:
            afd.show_dfa()
    def load_dfa_from_file(self, file_path):
        with open(file_path, 'r') as file:
            lines = file.readlines()

        states = []
        alphabet = []
        transition = {}
        start_state = None
        accepting_states = []

        parsing_transitions = False

        for line in lines:
            line = line.strip()
            if not line or line.startswith('//'):
                continue  # Skip empty lines and comments

            if line == "Transitions:":
                parsing_transitions = True
                continue

            if parsing_transitions:
                parts = line.split()
                if len(parts) == 3:
                    source, symbol, target = parts
                    transition[(source, symbol)] = target
                else:
                    parsing_transitions = False
            else:
                key, value = line.split(':')
                value = value.strip()
                if key == "States":
                    states = value.split()
                elif key == "Alphabet":
                    alphabet = value.split()
                elif key == "Start State":
                    start_state = value
                elif key == "Accepting States":
                    accepting_states = value.split()

        return DFA(states, alphabet, transition, start_state, accepting_states)

lexer = Lexer("code.txt")
lexer.readInput()
lexer.addAfd("allrules.txt")
lexer.addAfd("decrules.txt")
lexer.addAfd("intrules.txt")
lexer.addAfd("idrules.txt")
lexer.runLexer()
lexer.buildFIP()