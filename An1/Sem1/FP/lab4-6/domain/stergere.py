from ui.printare import print_delete_for_day_success
from utils.utils import get_day, get_sum, get_type
def delete_for_day(db, day):
    day = int(day)
    db, counter = delete_entry_day(db, day)
    
    
    print_delete_for_day_success(counter)
    
    
    
def delete_entry_day(db, day):
    counter = 0
    try:
        for key, value in list(db.items()):
            
            if key!='0' and get_day(value) == day:
                counter = counter + 1
                del db[key]
    except:
        print("Something went wrong!")
    return db, counter
def delete_for_interval(db, day_start, day_end):

    day_start, day_end = int(day_start), int(day_end)
    db, counter = delete_entry_interval(db, day_start, day_end)

    
    print_delete_for_day_success(counter)


def delete_entry_interval(db, day_start, day_end):
    counter = 0
    try:
        for key, value in list(db.items()):
            
            if key!='0' and get_day(value) in range(day_start, day_end + 1):
                counter = counter + 1
                del db[key]
    except:
        print("Something went wrong!")
    return db, counter
def delete_for_type(db, inp_type):
    

    db, counter = delete_entry_type(db, inp_type)

    
    print_delete_for_day_success(counter)
        
def delete_entry_type(db, inp_type):
    counter = 0
    try:
        for key, value in list(db.items()):
            
            if key!='0' and get_type(value) == inp_type:
                counter = counter + 1
                del db[key]
    except:
        print("Something went wrong!")
    return db, counter