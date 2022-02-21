
from utils.utils import get_day, get_sum, get_type
def search_for_condition_sum(db, inp_sum):
    try:
        for key, value in list(db.items()):
            if key!='0' and get_sum(value) < inp_sum:
                del db[key]
    except:
        print("Something went wrong!")
    return db
def search_for_condition_day_and_sum(db, inp_day, inp_sum):
    try:
        for key, value in list(db.items()):
            if key!='0' and ( get_sum(value) > inp_sum or get_day(value) > inp_day):
                del db[key]
    except:
        print("Something went wrong!")
    return db
def search_for_condition_type(db, inp_type):
    try:
        for key, value in list(db.items()):  
            if key!='0' and get_type(value) != inp_type:
                del db[key]
    except:
        print("Something went wrong!")
    return db


def search_for_sum(db, inp_sum):
    inp_sum = float(inp_sum)
    db_copy = db.copy()
    db_copy = search_for_condition_sum(db_copy, inp_sum)
    
    return db_copy 
    
    
    
def search_for_day_and_sum(db, inp_day, inp_sum):
    inp_day, inp_sum = int(inp_day), float(inp_sum)
    db_copy = db.copy()
    db_copy = search_for_condition_day_and_sum(db_copy, inp_day, inp_sum)
    
    return db_copy
    
    
       
def search_for_type(db, inp_type):
    
    db_copy = db.copy()
    db_copy = search_for_condition_type(db_copy, inp_type)
    
    return db_copy

    
        
