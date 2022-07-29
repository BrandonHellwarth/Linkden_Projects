from __future__ import print_function # In python 2.7
import sys
from flask_app.config.mysqlconnection import connectToMySQL
from flask_app.models.car import Car
from flask_app.models.message import Message
from flask import flash
import re
EMAIL_REGEX = re.compile(r'^[a-zA-Z0-9.+_-]+@[a-zA-Z0-9._-]+\.[a-zA-Z]+$')
PASSWORD_REGEX = re.compile(r'\d.*[A-Z]|[A-Z].*\d')
class User:
    @classmethod
    def save(cls, data ):
        query = "INSERT INTO users ( user_name , email , password, phone, city, state, zip_code, created_at, updated_at) VALUES ( %(user_name)s, %(email)s , %(pword)s , %(phone)s, %(city)s, %(state)s, %(zip_code)s, NOW() , NOW());"
        return connectToMySQL('carz').query_db( query, data )
    def __init__( self , data ):
        self.id = data['id']
        self.user_name = data['user_name']
        self.email = data['email']
        self.password = data['password']
        self.phone = data['phone']
        self.city = data['city']
        self.state = data['state']
        self.zip_code = data['zip_code']
        self.created_at = data['created_at']
        self.updated_at = data['updated_at']
        self.cars = []
        self.messages = []
    @classmethod
    def get_all(cls):
        query = "SELECT * FROM users;"
        results = connectToMySQL('carz').query_db(query)
        users = []
        for user in results:
            users.append( cls(user) )
        return users
    @classmethod
    def get_one(cls, data):
        query = "SELECT * FROM users WHERE id = %(id)s;"
        user = connectToMySQL('carz').query_db(query, data)
        return user
    @classmethod
    def get_one_by_email(cls, data):
        query = "SELECT * FROM users WHERE email = %(email)s;"
        user = connectToMySQL('carz').query_db(query, data)
        return user
    @classmethod
    def get_user_with_cars(cls, data):
        query ="SELECT * FROM users LEFT JOIN cars ON cars.user_id = users.id WHERE users.id = %(id)s;"
        results = connectToMySQL('carz').query_db(query, data)
        user = cls(results[0])
        for row_from_db in results:
            car_data = {
                "id" : row_from_db["cars.id"],
                "image" : row_from_db["image"],
                "year" : row_from_db["year"],
                "make" : row_from_db["make"],
                "model" : row_from_db["model"],
                "description" : row_from_db['description'],
                "price" : row_from_db['price'],
                "created_at" : row_from_db["cars.created_at"],
                "updated_at" : row_from_db["cars.updated_at"],
                "user_id" : row_from_db["user_id"],
                "listed" : row_from_db["listed"]
            }
            user.cars.append(Car(car_data))
        return user
    @classmethod
    def get_users_with_recieved_messages(cls):
        query ="SELECT * FROM users JOIN messages ON messages.recipient_id = users.id;"
        results = connectToMySQL('carz').query_db(query)
        if len(results) == 0:
            return False
        return results
    @classmethod
    def get_recieved_message_info(cls, data):
        query = "SELECT * FROM users JOIN messages ON user_id = users.id WHERE recipient_id = %(id)s;"
        results = connectToMySQL('carz').query_db(query, data)
        if len(results) == 0:
            return False
        return results
    @classmethod
    def update(cls, data):
        query = "UPDATE users SET user_name = %(user_name)s, email = %(email)s, password = %(password)s, phone = %(phone)s, city = %(city)s, state = %(state)s, zip_code = %(zip_code)s WHERE id = %(id)s"
        results = connectToMySQL('carz').query_db(query, data)
        return results
    @staticmethod
    def validate_register(cls):
        is_valid = True
        if len(cls['username']) < 1:
            flash("User Name must be at least 2 characters.")
            is_valid = False
        if len(cls['password']) < 8:
            flash("Password must be more than 8 characters.")
            is_valid = False
        if not EMAIL_REGEX.match(cls['email']): 
            flash("Invalid email address!")
            is_valid = False
        if not PASSWORD_REGEX.match(cls['password']): 
            flash("Password must contain at least one uppercase letter and one number.")
            is_valid = False
        if cls['password'] != cls['password_confirm']:
            flash("Passwords must match.")
            is_valid = False
        if len(cls['phone']) < 10 or len(cls['phone']) > 10:
            flash("Phone number must be 10 characters.")
            is_valid = False
        if len(cls['city']) < 2:
            flash("City must be at least 2 characters.")
            is_valid = False
        if len(cls['zip_code']) < 5 or len(cls['zip_code']) > 5:
            flash("Zip code must be 5 characters.")
        return is_valid