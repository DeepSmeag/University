

def create_instance(inp_day, inp_sum, inp_type):
    return [int(inp_day), float(inp_sum), inp_type]

def get_last_key(db):
    return int(list(db.keys())[-1])

def get_day(value):
    return value[0]

def get_sum(value):
    return value[1]

def get_type(value):
    return value[2]
