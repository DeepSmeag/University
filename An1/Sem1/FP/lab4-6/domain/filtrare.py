from ui.printare import print_database
from utils.utils import get_sum, get_type
def filter_by_condition_type(db, inp_type):
    try:
        for key, value in list(db.items()):  
            if key!='0' and get_type(value) == inp_type:
                del db[key]
    except:
        print("Something went wrong!")
    return db
def filter_by_condition_sum(db, inp_sum):
    try:
        for key, value in list(db.items()):
            if key!='0' and get_sum(value) < inp_sum:
                del db[key]
    except:
        print("Something went wrong!")
    return db


def filter_by_type(db, inp_type):
    
    db_copy = db.copy()
    db = filter_by_condition_type(db, inp_type)
    
    
    return db
      
def filter_by_sum(db, inp_sum):
    inp_sum = float(inp_sum)
    
    db = filter_by_condition_sum(db, inp_sum)
    
    
    return db
    
