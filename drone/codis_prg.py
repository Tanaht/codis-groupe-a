# coding=utf-8
from __future__ import print_function
import droneIstic
import socket
import json
from config.Config import Config
from dronekit import LocationGlobal


config = Config()
client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

print('Connexion à %s: %s' % (config.socket_host, config.socket_port))
client.connect((config.socket_host, config.socket_port))

while True:

    response = client.recv(4096)

    if response != "":
        trame = json.loads(response.decode())
        mission = trame['type']
        title = trame['data']['title']

        if mission == "mission_order":
            # decode des points
            liste = []
            elt = trame['data']['trajectory'];
            for point in elt:
                liste.append(LocationGlobal(point['lat'], point['lon'], point['alt']))

            codisDrone = droneIstic.NotreDrone("udpin:{}:{}" .format(config.drone_host, config.drone_port), False, 30)
            codisDrone.changeMission(liste)
            codisDrone.start()

            json_data = json.dumps({
                'type': 'mission_finished',
                'data': {'mission': 10, 'status': True, 'error': ""}
            })
            client.send(json_data.encode())
            break

client.close()
