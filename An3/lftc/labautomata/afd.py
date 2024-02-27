class DFA:
    def __init__(self, states, alphabet, transition, start_state, accepting_states):
        self.states = set(states)
        self.alphabet = set(alphabet)
        self.transition = transition
        self.start_state = start_state
        self.accepting_states = set(accepting_states)
    def show_dfa(self):
        print("DFA Information:")
        print("States:", ", ".join(self.states))
        print("Alphabet:", ", ".join(self.alphabet))
        print("Start State:", self.start_state)
        print("Accepting States:", ", ".join(self.accepting_states))
        print("Transitions:")
        for (source, symbol), target in self.transition.items():
            print(f"{source} --({symbol})--> {target}")
    def run(self, input_string):
        current_state = self.start_state
        for symbol in input_string:
            if symbol not in self.alphabet:
                return False  # Invalid symbol
            current_state = self.transition.get((current_state, symbol), None)
            if current_state is None:
                return False  # No transition defined

        return current_state in self.accepting_states
    def process_longest_prefix(self, input_string):
        current_state = self.start_state
        longest_prefix = ""
        index = 0

        while index < len(input_string):
            symbol = input_string[index]

            # Check for transitions with the current symbol
            if (current_state, symbol) in self.transition:
                longest_prefix += symbol
                current_state = self.transition[(current_state, symbol)]
                index += 1
            else:
                # Check for transitions with substrings
                found_substring = False
                for substring, target in self.transition.items():
                    if input_string.startswith(substring[1], index):
                        longest_prefix += substring[1]
                        current_state = target
                        index += len(substring)
                        found_substring = True
                        break

                if not found_substring:
                    break

        return longest_prefix

def load_dfa_from_file(file_path):
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

def load_dfa_from_keyboard():
    print("Enter DFA rules:")
    
    states = input("States (comma-separated): ").strip().split(',')
    alphabet = input("Alphabet (comma-separated): ").strip().split(',')
    start_state = input("Start State: ").strip()
    accepting_states = input("Accepting States (comma-separated): ").strip().split(',')
    
    print("Enter transitions (format: source symbol target, one per line):")
    transitions = {}
    while True:
        transition = input().strip()
        if not transition:
            break
        source, symbol, target = transition.split()
        transitions[(source, symbol)] = target
    
    return DFA(states, alphabet, transitions, start_state, accepting_states)

def execute(file, input):
    dfa = load_dfa_from_file(file)
    dfa.show_dfa()
    result = dfa.run(input)
    longest_prefix = dfa.process_longest_prefix(input)
    print(f"Input string '{input}' is accepted: {result}")
    print(f"Longest prefix: {longest_prefix}")

if __name__ == "__main__":
    
    input_string = "1010100"
    input_integer = "+3853910"
    input_lab = "ab"
    file = "labrules.txt"
    execute(file, input_lab)
    
