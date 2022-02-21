from ui.printare import *
from validare.validare import validate_input, validate_key, validate_interval
from infrastructure.database import save_data
from utils.utils import get_day,get_sum,get_type

import domain.adaugare as adaugare
import domain.cautare as cautare
import domain.filtrare as filtrare
import domain.stergere as stergere
import domain.rapoarte as rapoarte
import domain.undo as undo

def main_ui(db):
    print_main_ui()
    inp_ans = input("\n\nInput desired option or command: ")
    select_ans_main_ui(inp_ans, db)

        
    
def select_ans_main_ui(inp_ans, db):
    if(inp_ans=="0"):
        print_database(db)
        main_ui(db)
    if(inp_ans=="1"):
        add_ui(db)
    elif(inp_ans=="2"):
        delete_ui(db)
    elif(inp_ans=="3"):
        search_ui(db)
    elif(inp_ans=="4"):
        report_ui(db)
    elif(inp_ans=="5"):
        filter_ui(db)
    elif(inp_ans=="6"):
        db = undo_ui(db)
        main_ui(db)
    elif(inp_ans=="9"):
        print("\n\nProgram is closing...\n\n")
        exit()

    elif(inp_ans.split(' ')[0]=='add'):
        values = inp_ans.split(" ")[1:4]
        
        add_expense_cmd(db, values)
    elif(inp_ans.split(' ')[0]=='remove_day'):
        values = inp_ans.split(" ")[1]
        
        delete_for_day_cmd(db, values)
    elif(inp_ans.split(' ')[0]=='search_type'):
        values=[None, None]
        values.append(inp_ans.split(" ")[1])
        
        search_for_type_cmd(db, values)
    elif(inp_ans.split(' ')[0]=='report_type'):
        values=[None, None]
        values.append(inp_ans.split(" ")[1])
        
        report_sum_by_type_cmd(db, values)
    else:
        print_general_error()
        main_ui(db)


def select_ans_add_ui(inp_ans, db):
    if(inp_ans=="1"):
        add_expense_ui(db)
    elif(inp_ans=="2"):
        change_expense_ui(db)
    elif(inp_ans=="3"):
        
        main_ui(db)
    else:
        print_general_error()
        add_ui(db)
def select_ans_delete_ui(inp_ans, db):
    if(inp_ans=="1"):
        delete_for_day_ui(db)
    elif(inp_ans=="2"):
        delete_for_interval_ui(db)
    elif(inp_ans=="3"):
        delete_for_type_ui(db)
    elif(inp_ans=="4"):
        
        main_ui(db)
    else:
        print_general_error()
        delete_ui(db)
def select_ans_search_ui(inp_ans, db):
    if(inp_ans=="1"):
        search_for_sum_ui(db)
    elif(inp_ans=="2"):
        search_for_day_and_sum_ui(db)
    elif(inp_ans=="3"):
        search_for_type_ui(db)
    elif(inp_ans=="4"):
        
        main_ui(db)
    else:
        print_general_error()
        search_ui(db)
def select_ans_filter_ui(inp_ans, db):
    if(inp_ans=="1"):
        filter_by_type_ui(db)
    elif(inp_ans=="2"):
        filter_by_sum_ui(db)
    elif(inp_ans=="3"):
        
        main_ui(db)
    else:
        print_general_error()
        filter_ui(db)
def select_ans_report_ui(inp_ans, db):
    if(inp_ans=="1"):
        report_sum_by_type_ui(db)
    elif(inp_ans=="2"):
        report_day_max_sum_ui(db)
    elif(inp_ans=="3"):
        report_expense_by_sum_ui(db)
    elif(inp_ans=="4"):
        report_by_type_ui(db)
    elif(inp_ans=="5"):
        main_ui(db)
    else:
        print_general_error()
        report_ui(db)

def add_ui(db):
    print_add_ui()
    inp_ans = input("\n\nInput desired option: ")
    select_ans_add_ui(inp_ans, db)


def add_expense_ui(db):
    print_add_expense_ui()
    inp_day = input("Day: ")
    inp_sum = input("Sum: ")
    inp_type = input("Type: ")

    try:
        validate_input(inp_day, inp_sum, inp_type)
        adaugare.add_expense(db, inp_day, inp_sum, inp_type)
        save_data(db)
    except ValueError as ve:
        print(str(ve))
    
    add_ui(db)

def add_expense_cmd(db, values):
    inp_day = get_day(values)
    inp_sum = get_sum(values)
    inp_type = get_type(values)
    try:
        validate_input(inp_day, inp_sum, inp_type)
        adaugare.add_expense(db, inp_day, inp_sum, inp_type)
        save_data(db)
    except ValueError as ve:
        print(str(ve))
    
    add_ui(db)

def change_expense_ui(db):
    print_change_expense_ui()
    inp_key = input("Enter the desired ID: ")

    
    try:
        validate_key(db, inp_key)
    except ValueError as ve:
        print(str(ve))
    inp_day, inp_sum, inp_type = print_change_expense_prompt()
    try:
        validate_input(inp_day, inp_sum, inp_type)
        adaugare.change_expense(db, inp_key, inp_day, inp_sum, inp_type)
        save_data(db)
    except ValueError as ve:
        print(str(ve))
    
    add_ui(db)
    


def delete_ui(db):
    print_delete_ui()
    inp_ans = input("\n\nInput desired option: ")
    select_ans_delete_ui(inp_ans, db)

def delete_for_day_ui(db):
    print_delete_for_day_ui()
    inp_day = input("Day: ")

    try:
        validate_input(inp_day, None, None)
        stergere.delete_for_day(db, inp_day)
        save_data(db)
    except ValueError as ve:
        print(str(ve))
    
    delete_ui(db)

def delete_for_day_cmd(db, values):
    inp_day = get_day(values)

    try:
        validate_input(inp_day, None, None)
        stergere.delete_for_day(db, inp_day)
        save_data(db)
    except ValueError as ve:
        print(str(ve))
    
    delete_ui(db)

def delete_for_interval_ui(db):
    print_delete_for_interval_ui()
    day_start = input("Day starting: ")
    day_end = input("Day ending: ")

    try:
        validate_input(day_start, None, None)
        validate_input(day_end, None, None)
        validate_interval(day_start, day_end)
        stergere.delete_for_interval(db, day_start, day_end)
        save_data(db)
    except ValueError as ve:
        print(str(ve))
    
    delete_ui(db)

def delete_for_type_ui(db):
    print_delete_for_type_ui()
    inp_type = input("Type: ")

    try:
        validate_input(None, None, inp_type)

        stergere.delete_for_type(db, inp_type)
        save_data(db)
    except ValueError as ve:
        print(str(ve))

    delete_ui(db)


def search_ui(db):
    print_search_ui()
    inp_ans = input("\n\nInput desired option: ")
    select_ans_search_ui(inp_ans, db)


def search_for_sum_ui(db):
    print_search_for_sum_ui()
    inp_sum = input("\nSum: ")
    try:
        validate_input(None, inp_sum, None)
        print_database(cautare.search_for_sum(db, inp_sum))

    except ValueError as ve:
        print(str(ve))
    search_ui(db)

def search_for_day_and_sum_ui(db):
    print_search_for_day_and_sum_ui()
    inp_day = input("Day: ")
    inp_sum = input("Sum: ")
    try:
        validate_input(inp_day, inp_sum, None)
        print_database(cautare.search_for_day_and_sum(db, inp_day, inp_sum))

    except ValueError as ve:
        print(str(ve))
    search_ui(db)


def search_for_type_ui(db):
    print_search_for_type_ui()
    inp_type = input("Type: ")
    try:
        validate_input(None, None, inp_type)
        print_database(cautare.search_for_type(db, inp_type))

    except ValueError as ve:
        print(str(ve))
    search_ui(db)
    
def search_for_type_cmd(db, values):
    inp_type = get_type(values)
    try:
        validate_input(None, None, inp_type)
        print_database(cautare.search_for_type(db, inp_type))

    except ValueError as ve:
        print(str(ve))
    search_ui(db)

def filter_ui(db):
    print_filter_ui()
    inp_ans = input("\n\nInput desired option: ")
    select_ans_filter_ui(inp_ans, db)


def filter_by_type_ui(db):
    print_filter_by_type_ui()
    inp_type = input("Type: ")
    try:
        validate_input(None, None, inp_type)
        db = filtrare.filter_by_type(db, inp_type)
        print_database(db)
        save_data(db)
    except ValueError as ve:
        print(str(ve))
    filter_ui(db)
    


def filter_by_sum_ui(db):
    print_filter_by_sum_ui()
    inp_sum = input("Sum: ")
    try:
        validate_input(None, inp_sum, None)
        db = filtrare.filter_by_sum(db, inp_sum)
        print_database(db)
        save_data(db)
    except ValueError as ve:
        print(str(ve))
    filter_ui(db)

def report_ui(db):
    print_report_ui()
    inp_ans = input("\n\nInput desired option: ")
    select_ans_report_ui(inp_ans, db)

def report_sum_by_type_ui(db):
    print_report_sum_by_type()
    inp_type = input("Type: ") 
    try:
        validate_input(None, None, inp_type)
        print(rapoarte.report_sum_by_type(db, inp_type))
    except ValueError as ve:
        print(str(ve))
    report_ui(db)

def report_sum_by_type_cmd(db, values):
    inp_type = get_type(values)
    try:
        validate_input(None, None, inp_type)
        print(rapoarte.report_sum_by_type(db, inp_type))
    except ValueError as ve:
        print(str(ve))
    report_ui(db)

def report_day_max_sum_ui(db):
    print_report_day_max_sum()
    
    print(rapoarte.report_day_max_sum(db))

    report_ui(db)
def report_expense_by_sum_ui(db):
    print_report_expense_by_sum()
    inp_sum = input("Sum: ")
    try:
        validate_input(None, inp_sum, None)
        print_database(rapoarte.report_expense_by_sum(db, inp_sum))
    except ValueError as ve:
        print(str(ve))
    report_ui(db)

def report_by_type_ui(db):
    print_report_by_type()

    print_database(rapoarte.report_by_type(db))
    
    report_ui(db)

def undo_ui(db):
    db_back = undo.undo(db)
    return db_back