from flask_app.config.mysqlconnection import connectToMySQL
from flask import flash
class Message:
    def __init__(self, data):
        self.id = data['id']
        self.content = data['content']
        self.recipient_id = data['recipient_id']
        self.created_at = data['created_at']
        self.updated_at = data['updated_at']
        self.user_id = data['user_id']
    @classmethod
    def save(cls, data ):
        query = "INSERT INTO messages ( content, recipient_id , created_at, updated_at, user_id) VALUES ( %(content)s , %(recipient_id)s , NOW() , NOW(), %(user_id)s);"
        return connectToMySQL('carz').query_db( query, data )
    @classmethod
    def get_user_messages(cls, data):
        query = "SELECT * FROM messages WHERE recipient_id = %(id)s"
        return connectToMySQL('carz').query_db( query, data )
    @classmethod
    def delete(cls, data):
        query = "DELETE FROM messages WHERE id = %(mid)s;"
        return connectToMySQL('carz').query_db( query, data )