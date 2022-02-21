from utils.utils import create_instance, get_last_key
from ui.printare import print_add_expense_succeeded_ui, print_change_expense_succeeded_ui
from infrastructure.database import add_to_db
def add_expense(db, inp_day, inp_sum, inp_type):

    expense_instance = create_instance(inp_day, inp_sum, inp_type)
    
    last_key = get_last_key(db)

    next_key = str(last_key + 1)
    
    db = add_to_db(db, next_key, expense_instance)
    

    
    print_add_expense_succeeded_ui(db[next_key], next_key)
    
    

def change_expense(db, key, new_day, new_sum, new_type):
    
    
    new_expense_instance = create_instance(new_day, new_sum, new_type)

    db = add_to_db(db, key, new_expense_instance)
    
    
    
    print_change_expense_succeeded_ui(db[key], key)
    
        
