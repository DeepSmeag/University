from os.path import exists
import json
from random import randint, choice, uniform





def get_entry_by_id(db, id):
    #getter to return entry
    return db[id]


def set_entry_by_id(db, id, inp_day, inp_sum, inp_type):
    # setter to update entry by given id
    db[id] = [inp_day, inp_sum, inp_type]





def create_db():
    db = { '0': []}
    return db

def add_to_db(db, key, value):
    db[key] = value
    return db

def initialize_db():
    # If json file does not exist, create it and create the database structure as provided inside the documentation file
    if not exists('db.json'):
        db = open("db.json", "x")
        dictionary = {0:{}}
        db.write(json.dumps(dictionary, indent= 4, sort_keys= True))
        db.close()  
    # Attempt to load database to memory
    with open('db.json') as db_file:
        data = json.load(db_file)
        return data

def initialize_backup(db_init):
    if not exists('backup.json'):
        db = open("backup.json", "x")
        backup = [db_init]
        db.write(json.dumps(backup, indent= 4, sort_keys= True))
        db.close()  
    # Attempt to load database to memory
    with open('backup.json') as db_file:
        data = json.load(db_file)
        return data

def read_backup():
    with open('backup.json') as db_file:
        data = json.load(db_file)
        return data

def save_backup(backup):
    db = open("backup.json", "w")
    db.write(json.dumps(backup, indent= 4, sort_keys= True))
    db.close()

def update_backup(db):
    backup_file = open("backup.json", "r")
    backup = json.load(backup_file)
    backup_file.close()
    backup.append(db)
    save_backup(backup)
    



def generate_random_db(no_elements):
    db = {
        '0': {}
    }
    for elem in range(1, no_elements + 1):
        rand_day = randint(1,31)
        rand_sum = round(uniform(0,100.0),2)
        rand_type =  choice(['mancare','intretinere','imbracaminte','telefon','altele'])
        db[str(elem)] = {'day': rand_day, 'sum': rand_sum, 'type': rand_type}
    return db

def save_data(db):
    with open('db.json', 'w') as save_file:
        save_file.write(json.dumps(db, indent= 4, sort_keys= True))
    update_backup(db)
