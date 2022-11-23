import flask
import sqlite3
from flask import request
from flask import jsonify

app = flask.Flask(__name__)
#app.config["DEBUG"] = True # Enable debug mode to enable hot-reloader.

@app.route('/coach', methods=['GET'])
def coach():
    con = sqlite3.connect('my-db.db')

    
    email = request.args.get('email', '')
    pw = request.args.get('pw', '')
    ids = request.args.get('ids', '')
    first_name = request.args.get('first_name', '')
    last_name = request.args.get('last_name', '')
    username = request.args.get('username', '')
    address = request.args.get('address', '')
    gender = request.args.get('gender', '')
    age = request.args.get('age', '')
    exp = request.args.get('exp', '')
    expertise = request.args.get('expertise', '')
    intro = request.args.get('intro', '')
    qua = request.args.get('qua', '')


    if len(email) > 0:
        insertQuery = "INSERT INTO Users (id, email, pw, first_name, last_name, username, address, gender, age) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
        con.execute(insertQuery, (ids, email, pw, first_name, last_name, username, address, gender, age))
        con.commit()

    insertQuery = "INSERT INTO Coach (username, expertise, yearExp, intro, qua) VALUES (?, ?, ?, ?, ?);"
    con.execute(insertQuery, (username, expertise, exp, intro, qua))
    con.commit()
    data = {"added coach": 200}
    return data

@app.route('/student', methods=['GET'])
def student():
    con = sqlite3.connect('my-db.db')

    
    email = request.args.get('email', '')
    pw = request.args.get('pw', '')
    ids = request.args.get('ids', '')
    first_name = request.args.get('first_name', '')
    last_name = request.args.get('last_name', '')
    username = request.args.get('username', '')
    address = request.args.get('address', '')
    gender = request.args.get('gender', '')
    age = request.args.get('age', '')
    exp = request.args.get('exp', '')
    target = request.args.get('target', '')
    numperweek = request.args.get('numperweek', '')
    min_pay = request.args.get('min_pay', '')
    max_pay = request.args.get('max_pay', '')
    remarks = request.args.get('remarks', '')

    if len(email) > 0:
        insertQuery = "INSERT INTO Users (id, email, pw, first_name, last_name, username, address, gender, age) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
        con.execute(insertQuery, (ids, email, pw, first_name, last_name, username, address, gender, age))
        con.commit()

    insertQuery = "INSERT INTO Student (username, goal, level, min_pay, max_pay, lesson_num, remark) VALUES (?, ?, ?, ?, ?, ?, ?);"
    con.execute(insertQuery, (username, target, exp, min_pay, max_pay, numperweek, remarks))
    con.commit()

    cursor = con.execute("SELECT * from Users;")
    print(cursor)

    user_ids, ids, email, pw, first_name, last_name, username, address, gender, age = [], [], [], [], [], [], [], [], [], []
    
    for row in cursor:
        user_ids.append(row[0])
        ids.append(row[1])  
        email.append(row[2])
        pw.append(row[3])
        first_name.append(row[4])
        last_name.append(row[5])
        username.append(row[6])
        address.append(row[7])
        gender.append(row[8])
        age.append(row[9])


    cursor = con.execute("SELECT * from Student;")
    print(cursor)

    targets, exps, min_pays, max_pays, numperweeks, remarkss = [],[],[],[],[],[]
    
    for row in cursor:
        targets.append(row[0])
        exps.append(row[1])
        min_pays.append(row[2])
        max_pays.append(row[3])
        numperweeks.append(row[4])
        remarkss.append(row[5])
    con.close()
    
    data = {
        "user_id": user_ids,
        "email": email,
        "pw": pw,
        "id": ids,
        "first_name": first_name,
        "last_name": last_name,
        "username": username,
        "address": address,
        "gender": gender,
        "age": age,
        "exp": exp,
        "target": targets,
        "min_pay": min_pays,
        "max_pay": max_pays,
        "numperweek": numperweeks,
        "remarks": remarkss,
    }
    return data



@app.route('/get_student', methods=['GET'])
def get_student():
    con = sqlite3.connect('my-db.db')

    cursor = con.execute("SELECT * from Users INNER JOIN Student ON Users.username == Student.username;")
    print(cursor)

    user_ids, ids, email, pw, first_name, last_name, username, address, gender, age = [], [], [], [], [], [], [], [], [], []
    student_ids, usernames, goals, levels, min_pays, max_pays, numperweeks, remarkss = [], [], [], [], [], [], [], []
    for row in cursor:
        print(row)
        user_ids.append(row[0])
        ids.append(row[1])  
        email.append(row[2])
        pw.append(row[3])
        first_name.append(row[4])
        last_name.append(row[5])
        username.append(row[6])
        address.append(row[7])
        gender.append(row[8])
        age.append(row[9])
        student_ids.append(row[14])
        usernames.append(row[15])
        goals.append(row[16])
        levels.append(row[17])
        min_pays.append(row[18])
        max_pays.append(row[19])
        numperweeks.append(row[20])
        remarkss.append(row[21])



    con.close()
    
    data =[{
        "user_id": user_ids,
        "email": email,
        "pw": pw,
        "id": ids,
        "first_name": first_name,
        "last_name": last_name,
        "username": username,
        "address": address,
        "gender": gender,
        "age": age,
        "min_pay": min_pays,
        "student_id": student_ids,

        "max_pay": max_pays,
        "numperweek": numperweeks,
        "remarks": remarkss,
    }]
    return jsonify(data)



@app.route('/get_coach', methods=['GET'])
def get_coach():
    con = sqlite3.connect('my-db.db')

    cursor = con.execute("SELECT * from Users INNER JOIN Coach ON Users.username == Coach.username;")
    print(cursor)

    user_ids, ids, email, pw, first_name, last_name, username, address, gender, age = [], [], [], [], [], [], [], [], [], []
    coach_ids, yearExps, usernames, expertises, intros, quas = [], [], [], [], [], []
    
    for row in cursor:
        print(row)
        user_ids.append(row[0])
        ids.append(row[1])  
        email.append(row[2])
        pw.append(row[3])
        first_name.append(row[4])
        last_name.append(row[5])
        username.append(row[6])
        address.append(row[7])
        gender.append(row[8])
        age.append(row[9])


    cursor = con.execute("SELECT * from Student;")
    print(cursor)

    targets, exps, min_pays, max_pays, numperweeks, remarkss = [],[],[],[],[],[]
    
    for row in cursor:
        targets.append(row[0])
        exps.append(row[1])
        min_pays.append(row[2])
        max_pays.append(row[3])
        numperweeks.append(row[4])
        remarkss.append(row[5])
    con.close()
    
    data = {
        "user_id": user_ids,
        "email": email,
        "pw": pw,
        "id": ids,
        "first_name": first_name,
        "last_name": last_name,
        "username": username,
        "address": address,
        "gender": gender,
        "age": age,
        "exp": exps,
        "target": targets,
        "min_pay": min_pays,
        "max_pay": max_pays,
        "numperweek": numperweeks,
        "remarks": remarkss,
    }
    return jsonify(data)


@app.route('/get_user', methods=['GET'])
def get_user():
    con = sqlite3.connect('my-db.db')

    cursor = con.execute("SELECT * from Users;")
    print(cursor)
    user_ids, ids, email, pw, first_name, last_name, username, address, gender, age = [], [], [], [], [], [], [], [], [], []

    for row in cursor:
        print(row)
        user_ids.append(row[0])
        ids.append(row[1])
        email.append(row[2])
        pw.append(row[3])
        first_name.append(row[4])
        last_name.append(row[5])
        username.append(row[6])
        address.append(row[7])
        gender.append(row[8])
        age.append(row[9])

    con.close()
    
    data ={
        "user_id": user_ids,
        "email": email,
        "pw": pw,
        "id": ids,
        "first_name": first_name,
        "last_name": last_name,
        "username": username,
        "address": address,
        "gender": gender,
        "age": age
    }

    
    return jsonify(data)

# adds host="0.0.0.0" to make the server publicly available
app.run(host="0.0.0.0")