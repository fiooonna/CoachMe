import sqlite3

# Connects to database
# The .db file is created automatically if it does not exist
con = sqlite3.connect('my-db.db')

# Creates table

# need to delete exp, expertise, qua in User entity
# didnt make disjoint relationship, assuming user will only belong to either Student or Coach
con.execute("""CREATE TABLE IF NOT EXISTS Users (
    user_id varchar(255) not null PRIMARY KEY,
    id varchar(255),
    email varchar(255),
    pw varchar(255),
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

con.execute("""CREATE TABLE IF NOT EXISTS Student (
    student_id varchar(255) PRIMARY KEY,
    goal varchar(255),
    level varchar(255),
    prefer_price_range varchar(255),
    lesson_num varchar(255),
    FOREIGN KEY(id) REFERENCES User(user_id) ON DELETE CASCADE ON UPDATE NO ACTION
);""")

con.execute("""CREATE TABLE IF NOT EXISTS Coach (
    student_id varchar(255) PRIMARY KEY,
    yearExp varchar(255),
    price varchar(255),
    FOREIGN KEY(id) REFERENCES User(user_id) ON DELETE CASCADE ON UPDATE NO ACTION
);""")

con.close()
