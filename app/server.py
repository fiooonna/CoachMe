import flask
import sqlite3
from flask import request
from flask import jsonify
import collections
import json


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

#Get students and return a key-value object map
@app.route('/get_objects_student', methods=['GET'])
def get_objects_student():
    con = sqlite3.connect('my-db.db')
    cursor = con.execute("SELECT * from Users INNER JOIN Student ON Users.username == Student.username;")
    print("get student objects is executed")
    con.commit()
    rows = cursor.fetchall()

    objects_list = []
    for row in rows:
        print(row)
        d = collections.OrderedDict()
        d['user_id'] = row[0]
        d['gender'] = row[8]
        d['name'] = row[4]
        d['location'] = row[7]
        d['student_id'] = row[10]
        d['goals'] = row[12]
        d['experience'] = row[13]
        d['min_pay'] = row[14]
        d['max_pay'] = row[15]
        d['age'] = row[9]
        d["num_lesson"] = row[16]
        d['remark'] = row[17]
        objects_list.append(d)
    con.close()
    return jsonify(objects_list)


@app.route('/get_student', methods=['GET'])
def get_student():
    con = sqlite3.connect('my-db.db')
    request_username = request.args.get('username', '')
    request_user_id = request.args.get('user_id', '')
    if len(request_username) > 0:
        SelectQuery = "SELECT * from Users INNER JOIN Student ON Users.username == Student.username Where Users.username == :username ;"
        cursor = con.execute(SelectQuery, {"username": request_username})
        con.commit()
    elif len(request_user_id):
        SelectQuery = "SELECT * from Users INNER JOIN Student ON Users.username == Student.username Where Users.user_id == :user_id ;"
        cursor = con.execute(SelectQuery, {"user_id": request_user_id})
        con.commit()
    else:
        cursor = con.execute("SELECT * from Users INNER JOIN Student ON Users.username == Student.username;")
        con.commit()
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
        student_ids.append(row[10])
        usernames.append(row[11])
        goals.append(row[12])
        levels.append(row[13])
        min_pays.append(row[14])
        max_pays.append(row[15])
        numperweeks.append(row[16])
        remarkss.append(row[17])



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
        "age": age,
        "min_pay": min_pays,
        "student_id": student_ids,
        "max_pay": max_pays,
        "numperweek": numperweeks,
        "remarks": remarkss,
    }
    return jsonify(data)

#Get coaches and return a key-value object map
@app.route('/get_objects_coach', methods=['GET'])
def get_objects_coach():
    con = sqlite3.connect('my-db.db')
    cursor = con.execute("SELECT * from Users INNER JOIN Coach ON Users.username == Coach.username;")
    con.commit()
    rows = cursor.fetchall()

    objects_list = []
    for row in rows:
        print(row)
        d = collections.OrderedDict()
        d['user_id'] = row[0]
        d['coach_id'] = row[10]
        d['name'] = row[4]
        d['location'] = row[7]
        d['gender'] = row[8]
        d['expertise'] = row[13]
        d['qualification'] = row[15]
        d['yearExp'] = row[11]
        d['rating'] = row[16]
        d['bookmark'] = row[17]
        d['rated_ppl'] = row[18]
        d['age'] = row[9]
        objects_list.append(d)
    con.close()
    return jsonify(objects_list)

@app.route('/get_coach', methods=['GET'])
def get_coach():
    con = sqlite3.connect('my-db.db')
    request_username = request.args.get('username', '')
    request_user_id = request.args.get('user_id', '')
    if len(request_username) > 0:
        SelectQuery = "SELECT * from Users INNER JOIN Coach ON Users.username == Coach.username Where Users.username == :username ;"
        cursor = con.execute(SelectQuery, {"username": request_username})
        con.commit()
    elif len(request_user_id) > 0:
        SelectQuery = "SELECT * from Users INNER JOIN Coach ON Users.username == Coach.username Where Users.user_id == :user_id;"
        cursor = con.execute(SelectQuery, {"user_id": request_user_id})
        con.commit()
    else:
        cursor = con.execute("SELECT * from Users INNER JOIN Coach ON Users.username == Coach.username;")

    user_ids, ids, email, pw, first_name, last_name, username, address, gender, age = [], [], [], [], [], [], [], [], [], []
    coach_ids, yearExps, usernames, expertises, intros, quas, rating, bookmark, rated_ppls = [], [], [], [], [], [], [], [], []
    
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
        coach_ids.append(row[10])
        yearExps.append(row[11])
        usernames.append(row[12])
        expertises.append(row[13])
        intros.append(row[14])
        quas.append(row[15])
        rating.append(row[16])
        bookmark.append(row[17])
        rated_ppls.append(row[18])


    con.close()
    
    data = {
        "user_id": user_ids,
        "email": email,
        "pw": pw,
        "id": ids,
        "first_name": first_name,
        "last_name": last_name,
        "username": username,
        "location": address,
        "gender": gender,
        "age": age,
        "coach_id":coach_ids,
        "yearExp": yearExps,
        "expertise": expertises,
        "intro": intros,
        "qua": quas,
        "rating": rating,
        "bookmark": bookmark,
        "rated_ppl": rated_ppls
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

@app.route('/get_matched', methods=['GET'])
def matched():
    con = sqlite3.connect('my-db.db')
    
    match_id = request.args.get('match_id', '')
    student_id = request.args.get('student_id', '')
    coach_id = request.args.get('coach_id', '')
    Matched = request.args.get('Matched', '')
    Invited = request.args.get('Invited', '')
    Rating = request.args.get('Rating', '')

    insertQuery = "INSERT INTO Match (match_id, student_id, coach_id, Matched, Invited, Rating) VALUES (?, ?, ?, ?, ?, ?);"
    con.execute(insertQuery, (match_id, student_id, coach_id, Matched, Invited, Rating))
    con.commit()
    
    con.close()
    return 


# adds host="0.0.0.0" to make the server publicly available
app.run(host="0.0.0.0")