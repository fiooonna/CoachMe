import sqlite3

# Connects to database
# The .db file is created automatically if it does not exist
con = sqlite3.connect('my-db.db')

# Creates table
con.execute("""CREATE TABLE IF NOT EXISTS Users (
    email varchar(255),
    pw varchar(255),
    id varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    username varchar(255),
    address varchar(255),
    gender varchar(255),
    age varchar(255),
    exp varchar(255),
    expertise varchar(255),
    intro varchar(255),
    qua varchar(255)
);""")

con.close()