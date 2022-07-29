from flask_app.config.mysqlconnection import connectToMySQL
from flask import flash
class Car:
    def __init__( self , data ):
        self.id = data['id']
        self.image = data['image']
        self.year = data['year']
        self.make = data['make']
        self.model = data['model']
        self.description = data['description']
        self.price = data['price']
        self.created_at = data['created_at']
        self.updated_at = data['updated_at']
        self.user_id = data['user_id']
        self.listed = data['listed']
    @classmethod
    def save(cls, data ):
        query = "INSERT INTO cars ( image, year , make , model, description, price, created_at, updated_at, user_id) VALUES ( %(image)s , %(year)s , %(make)s , %(model)s , %(description)s ,%(price)s, NOW() , NOW(), %(user_id)s);"
        return connectToMySQL('carz').query_db( query, data )
    @classmethod
    def get_all(cls):
        query = "SELECT * FROM cars;"
        results = connectToMySQL('carz').query_db(query)
        cars = []
        for car in results:
            cars.append( cls(car) )
        return cars
    @classmethod
    def get_one(cls, data):
        query = "SELECT * FROM cars WHERE id = %(cid)s"
        car = connectToMySQL('carz').query_db(query, data)
        return car
    @classmethod
    def get_car_with_user(cls, data):
        query = "SELECT * FROM cars JOIN users ON users.id = cars.user_id WHERE cars.id = %(cid)s;"
        return connectToMySQL('carz').query_db( query, data )
    @classmethod
    def update_list_value(cls, data):
        query = "UPDATE cars SET listed = %(lv)s WHERE id = %(cid)s;"
        return connectToMySQL('carz').query_db( query, data )
    @classmethod
    def update(cls, data):
        query = "UPDATE cars SET image = %(image)s, year = %(year)s, make = %(make)s, model = %(model)s, description = %(description)s, price = %(price)s WHERE id = %(cid)s;"
        return connectToMySQL('carz').query_db( query, data )
    @classmethod
    def delete(cls, data):
        query = "DELETE FROM cars WHERE id = %(cid)s"
        return connectToMySQL('carz').query_db( query, data )
    @staticmethod
    def validate_entry(cls):
        is_valid = True
        if len(cls['year']) < 4:
            flash("Year must be at least 4 characters")
            is_valid = False
        if len(cls['year']) > 4:
            flash("Year cannot be more than 4 characters")
            is_valid = False
        if len(cls['make']) < 4:
            flash("Make must be at least 4 characters")
            is_valid = False
        if len(cls['model']) < 3:
            flash("Model must be at least 3 characters")
            is_valid = False
        if len(cls['description']) < 10:
            flash("Description must be at least 10 characters")
            is_valid = False
        if not cls['price']:
            flash("Price must have a value")
            is_valid = False
        return is_valid