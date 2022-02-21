from utils.utils import get_day,get_sum,get_type
from ui.printare import print_database
from infrastructure.database import create_db, add_to_db
def report_sum_by_type(db, inp_type):
    max_sum = 0

    for key, value in list(db.items()):
        if key != '0' and get_type(value) == inp_type:
            max_sum += get_sum(value)

    return max_sum

def report_day_max_sum(db):
    max_sum = 0
    max_day = -1

    for key, value in list(db.items()):
        if key != '0' and get_sum(value) > max_sum:
            max_day = get_day(value)
            max_sum = get_sum(value)

    return max_day

def report_expense_by_sum(db, inp_sum):
    inp_sum = float(inp_sum)
    db_report = create_db()

    for key, value in list(db.items()):
        if key != '0' and get_sum(value) == inp_sum:
            db_report = add_to_db(db_report, key, value)

    return db_report


def report_by_type(db):

    db_report = db.copy()
    del(db_report['0'])
    db_report = dict(sorted(db_report.items(), key = lambda item: get_type(item[1])))

    return db_report



