import domain.adaugare as adaugare
import domain.cautare as cautare
import domain.filtrare as filtrare
import domain.stergere as stergere
import domain.rapoarte as rapoarte
import domain.undo as undo

from ui.printare import *

def test_phase():
    test_add_expense()
    test_change_expense()

    test_delete_for_day()
    test_delete_for_interval()
    test_delete_for_type()

    test_search_for_sum()
    test_search_for_day_and_sum()
    test_search_for_type()

    test_filter_by_type()
    test_filter_by_sum()

    test_report_by_type()
    test_report_day_max_sum()
    test_report_expense_by_sum()
    test_report_sum_by_type()

    test_undo_test()

def test_add_expense():
    print_test_phase_add()
    db = {'0':[]}
    adaugare.add_expense(db, 1, 1.0, 'mancare')
    
    try:
        assert db == {'0':[], '1': [1,1.0, 'mancare']}
        print("Test Add success")
    except AssertionError:
        print("Test Add failed") 

def test_change_expense():
    print_test_phase_change()
    db = {'0':[], '1': [1,1.0, 'imbracaminte']}
    try:
        adaugare.change_expense(db, '1', 2, 3.0, 'mancare')
        assert db == {'0':[], '1': [2,3.0,'mancare']}
        print("Test Modify success")
    except AssertionError:
        print("Test Modify failed")
    
def test_delete_for_day():
    print_test_delete_for_day()
    
    db = {'0':[], '1': [1,1.0, 'imbracaminte'], '2': [1,2.0, 'mancare']}

    try:
        stergere.delete_for_day(db, 1)
        assert db == {'0':[]}
        print("Test Delete for day succeeded")
    except AssertionError:
        print("Teste Delete for day failed")

def test_delete_for_interval():
    print_test_delete_for_interval()
    db = {'0':[], '1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}

    try:
        stergere.delete_for_interval(db, 2, 5)
        assert db == {'0':[], '1': [1,1.0, 'imbracaminte']}
        print("Test Delete for interval succeeded")
    except AssertionError:
        print("Teste Delete for interval failed")
    
def test_delete_for_type():
    print_test_delete_for_type()
    db = {'0':[], '1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}

    try:
        stergere.delete_for_type(db, 'mancare')
        assert db == {'0':[], '1': [1,1.0, 'imbracaminte']}
        print("Test Delete for type succeeded")
    except AssertionError:
        print("Teste Delete for type failed")

def test_search_for_sum():
    print_test_search_for_sum()
    db = {'0':[], '1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}

    try:
        cautare.search_for_sum(db, 2.0)
        assert db == {'0':[], '2': [5,2.0, 'mancare']}
        print("Test Search for sum succeeded")
    except AssertionError:
        print("Test Search for sum failed")
    
    
def test_search_for_day_and_sum():
    print_test_search_for_day_and_sum()
    db = {'0':[], '1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}

    try:
        cautare.search_for_day_and_sum(db, 2, 3.0)
        assert db == {'0':[], '1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}
        print("Test Search for day and sum succeeded")
    except AssertionError:
        print("Test Search for day and sum failed")
    
def test_search_for_type():
    print_test_search_for_type()
    db = {'0':[], '1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}

    try:
        cautare.search_for_type(db, 'mancare')
        assert db == {'0':[], '2': [5,2.0, 'mancare']}
        print("Test Search for type succeeded")
    except AssertionError:
        print("Test Search for type failed")

def test_filter_by_sum():
    print_test_filter_by_type()
    db = {'0':[], '1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}

    try:
        filtrare.filter_by_sum(db, 5.0)
        assert db == {'0':[]}
        print("Test Filter by sum succeeded")
    except AssertionError:
        print("Test Filter by sum failed")

def test_filter_by_type():
    print_test_filter_by_sum()
    db = {'0':[], '1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}

    try:
        filtrare.filter_by_type(db, 'imbracaminte')
        assert db == {'0':[], '2': [5,2.0, 'mancare']}
        print("Test Filter by type succeeded")
    except AssertionError:
        print("Test Filter by type failed")

def test_report_day_max_sum():
    print_test_report_day_max_sum()
    db = {'0':[], '1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}

    try:
        assert rapoarte.report_day_max_sum(db) == 5
        print("Test Report by day max sum succeeded")
    except AssertionError:
        print("Test Report by day max sum failed")

def test_report_expense_by_sum():
    print_test_report_expense_by_sum()
    db = {'0':[], '1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}
    try:
        
        assert rapoarte.report_expense_by_sum(db, 2.0) == {'0':[], '2': [5,2.0, 'mancare']}
        print("Test Report by sum succeeded")
    except AssertionError:
        print("Test Report by sum failed")

def test_report_by_type():
    print_test_report_by_type()
    db = {'0':[], '1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}
    try:
        assert rapoarte.report_by_type(db) == {'1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}
        print("Test Report by type succeeded")
    except AssertionError:
        print("Test Report by type failed")

def test_report_sum_by_type():
    print_test_report_sum_by_type()
    db = {'1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare'], '3': [2,13.3, 'mancare']}
    try:
        assert rapoarte.report_sum_by_type(db, 'mancare') ==  15.3
        print("Test Report sum by type succeeded")
    except AssertionError:
        print("Test Report sum by type failed")

def test_undo_test():

    db = {'1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare'], '3': [2,13.3, 'mancare']}
    backup = [{'1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}]
    
    try:
        db = undo.undo_test(db, backup)
        assert db == {'1': [1,1.0, 'imbracaminte'], '2': [5,2.0, 'mancare']}
        print("Test undo succeeded")
    except AssertionError:
        print("Test undo failed")
    