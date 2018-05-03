# coding=utf-8
import socket
import json
from mission import Mission
from config.Config import Config
from datetime import datetime
import time


# Manage the socket with the server. Drone will receive a mission and send positions and photos
class SocketIstic:
    client = None
    used = False

    # SINGLETON
    @classmethod
    def get_socket(cls):
        if cls.client is None:
            config = Config()
            cls.client = SocketIstic(config.socket_host, config.socket_port)
        return cls.client

    # Constructor, don't use it!
    # Prefer : SocketIstic socket = SocketIstic.get_socket()
    def __init__(self, host, port):
        self.client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        print("Connexion à %s: %s" % (host, port))
        self.client.connect((host, port))

    # message to the server : SEND SITUATION
    def send_position(self, location, battery, interventionId):
        json_data = {
            'type': 'SEND_SITUATION',
            'datas': {
                'altitude': location.alt,
                'location': {
                    'lat': location.lat,
                    'lng': location.lon
                    },
                'battery': battery,
                'interventionId': interventionId
            }
        }
        self.send(json_data)

    # message to the server : SEND_PHOTO
    def send_photo(self, location, image, interventionId):
        now = datetime.now()
        json_data = {
            'type': "SEND_PHOTO",
            'datas': {
                'photo': image,
                'date': time.mktime(now.timetuple()),
                'interventionId': interventionId,
                'pointId': 1,
                'location': {
                    'lat': location.lat,
                    'lng': location.lon
                }
            }
        }
        self.send(json_data)

    # message to the server : END_MISSION
    def send_end_mission(self):
        json_data = {
            'type': 'mission_finished',
            'datas': {}
        }
        self.send(json_data)

    # wait a mission from the server
    def wait_a_mission(self):
        ma_mission = ""
        if not self.used:
            self.used = True
            response = self.client.recv(4096)
            print("Received : " + response)
            if response != "!" and response != "STOP":
                response = response.replace("!","")
                if response != "":
                    ma_mission = Mission(json.loads(response.decode()))
            else:
                ma_mission = response
            self.used = False
        return ma_mission

    # close the socket
    def close(self):
        self.client.close()

    # send data to the server
    # \n in the end of data trame for the java server program (readline)
    def send(self, data):
        if not self.used:
            self.used = True
            self.client.sendall((json.dumps(data) + "\n").encode())
            print("DATA SENT")
            self.used = False
