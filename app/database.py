import sqlite3

# Connects to database
# The .db file is created automatically if it does not exist
con = sqlite3.connect('my-db.db')

# Creates table
con.execute("""DROP TABLE IF EXISTS Users""")
con.execute("""DROP TABLE IF EXISTS Student""")
con.execute("""DROP TABLE IF EXISTS Coach""")
# need to delete exp, expertise, qua in User entity
# didnt make disjoint relationship, assuming user will only belong to either Student or Coach

con.execute("""CREATE TABLE IF NOT EXISTS Users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    id varchar(255),
    email varchar(255),
    pw varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    username varchar(255) UNIQUE,
    address varchar(255),
    gender varchar(255),
    age varchar(255),
    UNIQUE(username, user_id)
);""")

con.execute("""CREATE TABLE IF NOT EXISTS Student (
    student_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username varchar(255) UNIQUE,
    goal varchar(255),
    level varchar(255),
    min_pay varchar(255),
    max_pay varchar(255),
    lesson_num varchar(255),
    remark varchar(255),
    UNIQUE(username, student_id)
);""")

con.execute("""CREATE TABLE IF NOT EXISTS Coach (
    coach_id INTEGER PRIMARY KEY AUTOINCREMENT,

    yearExp varchar(255),
    username varchar(255) UNIQUE,
    expertise varchar(255),
    intro varchar(255),
    qua varchar(255),
    UNIQUE(username, coach_id)
);""")

#FOREIGN KEY(user_id) REFERENCES User(user_id) ON DELETE CASCADE ON UPDATE NO ACTION,

con.close()