from ui.printare import input_day_error, input_sum_error, input_type_error

def validate_add_expense_ui(db, in_day, in_sum, in_type, testing = False):
    # Function to validate input on add expense option
    try:
        in_day = int(in_day)
        if in_day<1 or in_day>31:
            raise Exception
        in_sum = float(in_sum)
        if in_sum<0.0:
            raise Exception
        assert(in_type in ['mancare','intretinere','imbracaminte','telefon','altele'])
    except:
        if not testing:
            print("\n\nInput is incorrect! Please try again...")
            add_ui(db)
        raise Exception
    return in_day, in_sum


def validate_change_expense_ui(db, in_key, testing = False):
    try:
        if int(in_key)<1 or db[in_key] == None:
            raise Exception
    except:
        if not testing:
            print("\n\nID is invalid! Please choose another...")
            add_ui(db)
        raise Exception
    return in_key
def validate_change_expenses_params_ui(db, in_day, in_sum, in_type):
    try:
        in_day = int(in_day)
        if in_day<1 or in_day>31:
            raise Exception
        in_sum = float(in_sum)
        if in_sum<0.0:
            raise Exception
        assert(in_type in ['mancare','intretinere','imbracaminte','telefon','altele'])
    except:
        print("\n\nInput is incorrect! Please try again...")
        change_ui(db)
    return in_day, in_sum

def validate_delete_for_day(db, day, testing = False):
    try:
        day = int(day)
        if day<0 or day>31:
            raise Exception
    except:
        if not testing:
            print("\n\nDay is incorrect! Please try again...")
            delete_ui(db)
        raise Exception
    return day

def validate_delete_for_interval(db, day_start, day_end, testing = False):
    try:
        day_start = int(day_start)
        day_end = int(day_end)
        if day_start<0 or day_start>31 or day_end<0 or day_end>31:
            raise Exception
        if day_start > day_end:
            raise Exception
    except:
        if not testing:
            print("\n\nInput is invalid! Please try again...")
            delete_ui(db)
        raise Exception
    return day_start, day_end

def validate_delete_for_type(db, inp_type, testing = False):
    try:
        assert(inp_type in ['mancare','intretinere','imbracaminte','telefon','altele'])
    except:
        if not testing:
            print("Type is incorrect, please try again!")
            delete_ui(db)
        raise Exception
    return inp_type
        
def validate_search_for_sum(db, inp_sum, testing = False):
    try:
        inp_sum = float(inp_sum)
        if inp_sum<0.0: 
            raise Exception
    except:
        
        if not testing:
            print("\n\Input is incorrect! Please try again...")
            search_ui(db)
        raise Exception
    return inp_sum

def validate_search_for_day_and_sum(db, inp_day, inp_sum, testing = False):
    try:
        inp_day = int(inp_day)
        if inp_day<1 or inp_day>31:
            raise Exception 
        inp_sum = float(inp_sum)
        if inp_sum<0.0:
            raise Exception
    except:
        if not testing:
            print("\n\Input is incorrect! Please try again...")
            search_ui(db)
        raise Exception
    return inp_day, inp_sum

def validate_search_for_type(db, inp_type, testing = False):
    try:
        assert(inp_type in ['mancare','intretinere','imbracaminte','telefon','altele'])
    except:
        if not testing:
            print("Type is incorrect, please try again!")
            search_ui(db)
        raise Exception
    return inp_type

def validate_filter_by_type(db, inp_type, testing = False):
    try:
        assert(inp_type in ['mancare','intretinere','imbracaminte','telefon','altele'])
    except:
        if not testing:
            print("Type is incorrect, please try again!")
            filter_ui(db)
        raise Exception
    return inp_type

def validate_filter_by_sum(db, inp_sum, testing = False):
    try:
        inp_sum = float(inp_sum)
        if inp_sum<0.0:
            raise Exception
    except Exception:
        if not testing:
            print("\n\Input is incorrect! Please try again...")
            filter_ui(db)
        raise Exception
    return inp_sum


def validate_input(inp_day = None, inp_sum = None, inp_type = None):
    err_string = ""
    if inp_day != None:
        try:
            inp_day = int(inp_day)
        
            if inp_day<1 or inp_day>31:
                raise ValueError 
        except ValueError as ve:
            err_string+=(input_day_error())

    if inp_sum != None:
        try:
            inp_sum = float(inp_sum)
            if inp_sum < 0.0:
                raise ValueError
        except ValueError:
            err_string+=(input_sum_error())
    if inp_type != None:
        try:
            assert inp_type in ['mancare','intretinere','imbracaminte','telefon','altele']
        except AssertionError:
            err_string+=(input_type_error())
    if len(err_string)>0:
        raise ValueError(err_string)

def validate_interval(day_start, day_end):
    if int(day_start) > int(day_end):
        raise ValueError("\n\nInterval is invalid\n\n")

def validate_key(db, inp_key):
    if db[inp_key] == None:
        raise ValueError("\n\nID is invalid!\n\n")

