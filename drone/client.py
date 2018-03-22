#!/usr/bin/env python
# coding: utf-8

import socket
import time
import json
from config.Config import Config

config = Config()
socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('Start socket server %s: %s' % (config.socket_host, config.socket_port))
socket.bind((config.socket_host, config.socket_port))

while True:
    socket.listen(1)
    client, address = socket.accept()
    print("{} connected".format(address))

    json_data = {
      'type': 'mission_order',
      'data': {
        'title': "highway to hell",
        'patrol': 0,
        'trajectory': [
            {'lat': 48.1148383, 'lon': -1.6388297, 'alt': 30, 'photo': False},
            {'lat': 48.1153379, 'lon': -1.6391757, 'alt': 30, 'photo': False}
        ],
        'patrol_trajectory': [
            # {'lat': 48.1148383, 'lon': -1.6388297, 'alt': 30, 'photo': False},
            # {'lat': 48.1153379, 'lon': -1.6391757, 'alt': 30, 'photo': False},
            {'lat': 48.1161849, 'lon': -1.6390014, 'alt': 30, 'photo': False},
            {'lat': 48.1164571, 'lon': -1.6373706, 'alt': 30, 'photo': False},
            {'lat': 48.1155689, 'lon': -1.6360724, 'alt': 30, 'photo': False}
            # {'lat': 48.1152322, 'lon': -1.6378534, 'alt': 30, 'photo': False}
        ]
      }
    }
    client.sendall((json.dumps(json_data)).encode())
    time.sleep(1)

    while True:
        response = client.recv(4096)
        if response != "":
            trame = json.loads(response.decode())
            mission = trame['type']

            if mission == "mission_finished":
                print("PARCOURS TERMINE !")
                break;
            elif mission == "drone_status":
                print "POSITION DRONE :", format(trame['data']['position'])
                time.sleep(0.5)
            else:
                print "STATUS UNKNOWN :", mission
                break;
    print("Close")
    client.close()
socket.close()