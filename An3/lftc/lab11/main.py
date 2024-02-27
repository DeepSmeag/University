def parse_grammar(filename):
    with open(filename, 'r') as file:
        lines = file.readlines()

    productions = [line.strip().split(' -> ') for line in lines]
    terminals = set()
    # build list of all characters on left side
    left_side = [production[0] for production in productions]

    for production in productions:
        if len(production) == 2:
            right_side = production[1]
            for symbol in right_side:
                if symbol.isalpha() and (not (symbol in left_side)):
                    terminals.add(symbol)

    return terminals

if __name__ == "__main__":
    filename = "reg.txt"
    terminals = parse_grammar(filename)
    
    print("Terminals in the grammar:")
    print(terminals)
