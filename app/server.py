import flask
import sqlite3
from flask import request

app = flask.Flask(__name__)
#app.config["DEBUG"] = True # Enable debug mode to enable hot-reloader.

@app.route('/project', methods=['GET'])
def project():
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
        insertQuery = "INSERT INTO Users (email, pw, id, first_name, last_name, username, address, gender, age, exp, expertise, intro, qua) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
        con.execute(insertQuery, (email, pw, ids, first_name, last_name, username, address, gender, age, exp, expertise, intro, qua))
        con.commit()

    cursor = con.execute("SELECT * from Users;")
    print(cursor)

    email, pw, ids, first_name, last_name, username, address, gender, age, exp, expertise, intro, qua = [], [], [], [], [], [], [], [], [], [], [], [], []
    
    for row in cursor:
        email.append(row[0])
        pw.append(row[1])
        ids.append(row[2])
        first_name.append(row[3])
        last_name.append(row[4])
        username.append(row[5])
        address.append(row[6])
        gender.append(row[7])
        age.append(row[8])
        exp.append(row[9])
        expertise.append(row[10])
        intro.append(row[11])
        qua.append(row[12])
        
    con.close()
    
    data = {
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
        "expertise": expertise,
        "intro": intro,
        "qua": qua
    }
    return data

# adds host="0.0.0.0" to make the server publicly available
app.run(host="0.0.0.0")
