from flask_app import app
from flask import render_template,redirect,request,session,flash
from flask_app.models.user import User
from flask_app.models.car import Car
from flask_app.models.message import Message
from werkzeug.utils import secure_filename
from flask_bcrypt import Bcrypt
bcrypt = Bcrypt(app)
deUpload = '/Users/Brandon/Documents/DojoWork/Linkden_Projects/Cars/flask_app/static/images'
UPLOAD_FOLDER = deUpload
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif'}

def allowed_file(filename):
    return '.' in filename and \
        filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route('/')
def home():
    key = "bool"
    if key not in session:
        session['bool'] = False
    if not session['bool']:
        return render_template('index.html', cars = Car.get_all())
    else:
        data = {
            'id' : session['user']['id']
        }
        return render_template('index.html', user = User.get_one(data)[0], cars = Car.get_all())

@app.route('/logout')
def logout():
    session['bool'] = False
    session['user'] = {
        'id' : 0
    }
    return redirect('/')

@app.route('/login')
def login():
    return render_template('login.html')

@app.route('/process_login', methods=["POST"])
def process_login():
    data = {
        'email' : request.form['email'],
        'pword' : request.form['password']
    }
    bool = False
    for user in User.get_all():
        if data['email'] == user.email:
            bool = True
            break
    if bool:
        account = User.get_one_by_email(data)
        if not bcrypt.check_password_hash(account[0]['password'], data['pword']):
            flash("Invalid password")
            return redirect('/login')
        else:
            session['user'] = account[0]
            session['bool'] = True
            return redirect('/')
    else:
        flash("Invalid Email")
        return redirect('/login')

@app.route('/register')
def register():
    stateslist = [ 
        'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DC', 'DE', 'FL', 'GA',
        'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME',
        'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM',
        'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX',
        'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY'
        ]
    return render_template('register.html', states = stateslist)

@app.route('/process_register', methods=["POST"])
def process_register():
    if not User.validate_register(request.form):
        return redirect('/register')
    data = {
        'user_name' : request.form['username'],
        'email' : request.form['email'],
        'pword' : bcrypt.generate_password_hash(request.form['password']),
        'phone' : request.form['phone'],
        'city' : request.form['city'],
        'state' : request.form['state'],
        'zip_code' : request.form['zip_code']
    }
    all_users = User.get_all()
    for user in all_users:
        if data['email'] == user.email:
            flash("Invalid Email.")
            return redirect('/')
    User.save(data)
    account = User.get_one_by_email(data)
    session['user'] = account[0]
    session['bool'] = True
    return redirect('/')

@app.route('/yourcars/<int:id>')
def yourcars(id):
    if session['bool']:
        data = {
            'id' : id
        }
        return render_template('yourcars.html', user = User.get_user_with_cars(data))
    else:
        flash("No user logged in")
        return redirect('/')

@app.route('/add_a_car/<int:id>')
def add_a_car(id):
    if session['bool']:
        data = {
            'id' : id
        }
        return render_template('add.html', user = User.get_one(data)[0])
    else:
        flash("No user logged in")
        return redirect('/')

@app.route('/process_add_car/<int:id>', methods=["POST"])
def process_add_recipe(id):
    if not Car.validate_entry(request.form):
        return redirect('/add_a_car/' + str(id))
    data = {
        'image' : '',
        'year' : request.form['year'],
        'make' : request.form['make'],
        'model' : request.form['model'],
        'description' : request.form['description'],
        'price' : request.form['price'],
        'user_id' : id
    }
    file = request.files['image']
    if file.filename == '':
            flash("No image uploaded")
            return redirect("/add_a_car/" + str(id))
    if file and allowed_file(file.filename):
        data['image'] += file.filename
        filename = secure_filename(file.filename)
        file.save(f"{app.config['UPLOAD_FOLDER']}\{filename}")
    else:
        flash("Invalid file type")
        return redirect('/add_a_car/' + str(id))
    Car.save(data)
    return redirect('/yourcars/' + str(id))

@app.route('/list/<int:id>/<int:cid>')
def list(id, cid):
    if session['bool']:
        data = {
            'cid' : cid,
            'lv' : 1
        }
        Car.update_list_value(data)
        return redirect('/yourcars/' + str(id))
    else:
        flash("No user logged in")
        return redirect('/')

@app.route('/remove/<int:id>/<int:cid>')
def remove(id, cid):
    if session['bool']:
        data = {
            'cid' : cid,
            'lv' : 0
        }
        Car.update_list_value(data)
        return redirect('/yourcars/' + str(id))
    else:
        flash("No user logged in")
        return redirect('/')

@app.route('/edit/<int:id>/<int:cid>')
def edit(id, cid):
    if session['bool']:
        data = {
            'id' : id,
            'cid' : cid
        }
        return render_template('edit.html', user = User.get_one(data)[0], car = Car.get_one(data)[0])
    else:
        flash("No user logged in")
        return redirect('/')

@app.route('/process_edit_car/<int:id>/<int:cid>', methods=["POST"])
def process_edit_recipe(id, cid):
    if not Car.validate_entry(request.form):
        return redirect('/edit/' + str(id) + '/' + str(cid))
    data = {
        'image' : '',
        'year' : request.form['year'],
        'make' : request.form['make'],
        'model' : request.form['model'],
        'description' : request.form['description'],
        'price' : request.form['price'],
        'cid' : cid
    }
    file = request.files['image']
    if file.filename == '':
            data['image'] = request.form['old_photo']
            return redirect('/yourcars/' + str(id))
    if file and allowed_file(file.filename):
        data['image'] += file.filename
        filename = secure_filename(file.filename)
        file.save(f"{app.config['UPLOAD_FOLDER']}\{filename}")
    else:
        flash("Invalid file type")
        return redirect('/add_a_car/' + str(id))
    Car.update(data)
    return redirect('/yourcars/' + str(id))

@app.route('/delete/<int:id>/<int:cid>')
def delete(id, cid):
    if session['bool']:
        data = {
            'cid' : cid
        }
        Car.delete(data)
        return redirect('/yourcars/' + str(id))
    else:
        flash("No user logged in")
        return redirect('/')

@app.route('/view/<int:cid>')
def view(cid):
    if not session['bool']:
        data = {
            'cid' : cid
        }
        return render_template('view.html', car = Car.get_car_with_user(data)[0])
    else:
        data = {
            'id' : session['user']['id'],
            'cid' : cid
        }
        return render_template('view.html', user = User.get_one(data)[0], car = Car.get_car_with_user(data)[0])

@app.route('/view_user/<int:id>')
def view_user(id):
    if not session['bool']:
        data = {
            'id' : id
        }
        return render_template('view_user.html', user = User.get_one(data)[0])
    if session['bool'] and session['user']['id'] == id:
        return redirect('/')
    else:
        data = {
            'id' : id
        }
        data2 = {
            'id' : session['user']['id']
        }
        return render_template('view_user.html', user = User.get_one(data)[0], user2 = User.get_one(data2)[0])

@app.route('/send_message/<int:id>', methods=["POST"])
def send_message(id):
    if not session['bool']:
        flash("You need to be logged in to send messages.")
        return redirect('/view_user/' + str(id))
    elif len(request.form['message']) < 1:
        flash("Message must have content")
        return redirect('/view_user/' + str(id))
    else:
        data = {
            'content' : request.form['message'],
            'recipient_id' : id,
            'user_id' : session['user']['id']
        }
        Message.save(data)
        flash("Message Sent!")
        return redirect('/view_user/' + str(id))

@app.route('/messages/<int:id>')
def messages(id):
    if session['bool']:
        data = {
            'id' : id
        }
        if not User.get_recieved_message_info(data) and not User.get_users_with_recieved_messages():
            return render_template('messages.html', user=User.get_one(data)[0])
        elif not User.get_recieved_message_info(data):
            return render_template('messages.html', user=User.get_one(data)[0],recipients = User.get_users_with_recieved_messages())
        elif not User.get_users_with_recieved_messages():
            return render_template('messages.html', user=User.get_one(data)[0],messages = User.get_recieved_message_info(data))
        else:
            return render_template('messages.html',user=User.get_one(data)[0], messages = User.get_recieved_message_info(data), recipients = User.get_users_with_recieved_messages())
    else:
        flash("No user logged in")
        return redirect('/')

@app.route('/reply/<int:id>', methods=["POST"])
def reply(id):
    return redirect('/view_user/' + str(id))

@app.route('/account/<int:id>')
def account(id):
    if session['bool']:
        data = {
            'id' : id
        }
        return render_template('account.html', user=User.get_one(data)[0])
    else:
        flash("No user logged in")
        return redirect('/')

@app.route('/account_edit/<int:id>')
def account_edit(id):
    if session['bool']:
        data = {
            'id' : id
        }
        stateslist = [ 
        'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DC', 'DE', 'FL', 'GA',
        'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME',
        'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM',
        'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX',
        'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY'
        ]
        return render_template('account_edit.html', user=User.get_one(data)[0], states = stateslist)
    else:
        flash("No user logged in")
        return redirect('/')

@app.route('/process_account_edit/<int:id>', methods=["POST"])
def process_account_edit(id):
    if not User.validate_register(request.form):
        return redirect('/account_edit/' + str(id))
    data = {
        'user_name' : request.form['username'],
        'email' : request.form['email'],
        'password' : bcrypt.generate_password_hash(request.form['password']),
        'phone' : request.form['phone'],
        'city' : request.form['city'],
        'state' : request.form['state'],
        'zip_code' : request.form['zip_code'],
        'id' : id
    }
    all_users = User.get_all()
    user1 = User.get_one(data)[0]
    print(user1, file=sys.stderr)
    for user in all_users:
        if data['email'] == user.email:
            if data['email'] != user1['email']:
                flash("Invalid Email.")
                return redirect('/account/' + str(id))
    User.update(data)
    return redirect('/account/' + str(id))