from infrastructure.database import read_backup, save_backup
from ui.printare import print_undo_error

def undo(db):
    backup = read_backup()
    try:
        db = backup[-1]
        del(backup[-1])
        save_backup(backup)
        
    except IndexError:
        print_undo_error()
    return db




def undo_test(db, backup):
    try:
        db = backup[-1]
        del(backup[-1])
        
        
    except IndexError:
        print_undo_error()
    return db