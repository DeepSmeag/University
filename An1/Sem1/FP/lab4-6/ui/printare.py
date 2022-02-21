
def print_main_ui():
    print("""
            Hi there! Please select the desired functionality by typing the correct number.
            0. Show all database entries
            1. Add/Modify expense
            2. Delete expense(s)
            3. Search for expense(s)
            4. Print expense report 
            5. Filter expense(s) 
            6. Undo last operation 

            9. Exit program
        """)

def print_database(db):
    print('\n\n Here are all the entries:\n\n')
    for key, value in db.items():
        if(key!='0'):
            print(key,": ",value)

def print_add_ui():
    print("""
        1. Add new expense (day/sum/type)
        2. Update expense (new day/sum/type)

        3. Go back to main menu
    """)
def print_add_expense_ui():
    print("""
        Please input day / sum / type below.
    """)
def print_change_expense_ui():
    print("""
        Please enter the ID of the transaction you want to change:
    """)
def print_change_expense_prompt():
    new_day = input("New day: ")
    new_sum = input("New sum: ")
    new_type = input("New type: ")
    return new_day, new_sum, new_type

def print_entry_in_database(entry, key):
    print(key,":",entry,'\n')

def print_add_expense_succeeded_ui(entry, key):
    print("""
        The entry has been added to the database!
        Here it is:
    """)
    print_entry_in_database(entry, key)
    print("Going back to the Add Menu...")
def print_change_expense_succeeded_ui(entry, key):
    print("""
        The entry has been changed in the database!
        Here it is:
    """)
    print_entry_in_database(entry, key)
    print("Going back to the Add Menu...")

def print_delete_ui():
    print("""
        1. Delete expense for a given day
        2. Delete expense for a given interval
        3. Delete expense of a certain type

        4. Back to main menu
    """)

def print_delete_for_day_ui():
    print("Please enter the day to delete: ")

def print_delete_for_day_success(counter):
    print(f"""
        {counter} items have been successfully deleted from the database!

        Going back to the Delete menu...
    """)

def print_delete_for_interval_ui():
    print("""
        Please input the interval you want to delete.

    """)


def print_delete_for_type_ui():
    print("""
        Please input the desired type to delete, from the following categories:
        [mancare, intretinere, imbracaminte, telefon, altele]

    """)

def print_search_ui():
    print("""
        1. Search for every expense greater than a given sum
        2. Search for every expense done before a given day and smaller than a given sum
        3. Search for every expense of a given type

        4. Back to main menu
    """)
def print_search_for_sum_ui():
    print("Please type the reference sum: ")

def print_search_for_day_and_sum_ui():
    print("Please type reference day and sum: ")

def print_search_for_type_ui():
    print("\
        Please type the reference type among the available options: \
        [mancare, intretinere, imbracaminte, telefon, altele] \
            \
    ")

def print_filter_ui():
    print("""
        1. Filter expenses by a certain type
        2. Filter expenses by sum

        3. Back to main menu
    """)

def print_filter_by_type_ui():
    print("""
        Please enter the type to be filtered out:

    """)

def print_filter_by_sum_ui():
    print("""
        Please enter the sum to filter by:

    """)


def print_test_phase_add():
    print("""
        Now testing the add functionality...
    """)
def print_test_phase_change():
    print("""
        Now testing the change functionality...
    """)
def print_test_delete_for_day():
    print("""
        Now testing the delete for day functionality...
    """)
    pass
def print_test_delete_for_interval():
    print("""
        Now testing the delete for interval functionality...
    """)
    pass
def print_test_delete_for_type():
    print("""
        Now testing the delete for type functionality...
    """)
    pass

def print_test_search_for_sum():
    print("""
        Now testing the search for sum functionality...
    """)
    pass
def print_test_search_for_day_and_sum():
    print("""
        Now testing the search for interval functionality...
    """)
    pass
def print_test_search_for_type():
    print("""
        Now testing the search for type functionality...
    """)
    pass


def print_test_filter_by_type():
    print("""
        Now testing the filter by type functionality...
    """)
def print_test_filter_by_sum():
    print("""
        Now testing the filter by sum functionality...
    """)

def print_test_report_day_max_sum():
    print("""
        Now testing the report day max functionality...
    """)
def print_test_report_expense_by_sum():
    print("""
        Now testing the report expense by sum functionality...
    """)
def print_test_report_by_type():
    print("""
        Now testing the report by type functionality...
    """)
def print_test_report_sum_by_type():
    print("""
        Now testing the report sum by type functionality...
    """)
def print_general_error():
    print("\n\nSomething went wrong! Please try again!\n\n")

def input_day_error():
    return "\n\nDay is invalid!\n\n"

def input_sum_error():
    return "\n\nSum is invalid!\n\n"

def input_type_error():
    return "\n\nType is invalid!\n\n"


def print_report_sum_by_type():
    print("""
        Please input type to calculate total sum
    """)

def print_report_day_max_sum():
    print("""
        Finding max sum day...
    """)

def print_report_expense_by_sum():
    print("""
        Input sum to show all expenses with that sum
    """)

def print_report_by_type():
    print("""
        Sorting expenses by type...
    """)

def print_report_ui():
    print("""
        Please choose between these options:

        1. Report total sum for given type
        2. Find day with max sum spent
        3. Find all expenses with given sum
        4. Sort by type

        5. Go back to main menu
    """)

def print_undo_error():
    print("""
        You cannot undo anymore
    """)

