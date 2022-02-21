from infrastructure.database import initialize_db, initialize_backup
from ui.ui import main_ui
from testing.testare import test_phase


    



if __name__ == '__main__':
    # test_phase()
    db = initialize_db()
    backup = initialize_backup(db)
    # db = generate_random_db(50)
    # print_database(db)
    
    main_ui(db)
    

