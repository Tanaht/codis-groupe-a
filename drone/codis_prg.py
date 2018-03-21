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
            patrol_liste = []

            elt1 = trame['data']['trajectory'];
            for point in elt1:
                liste.append(LocationGlobal(point['lat'], point['lon'], point['alt']))

            elt2 = trame['data']['patrol_trajectory'];
            for point in elt2:
                patrol_liste.append(LocationGlobal(point['lat'], point['lon'], point['alt']))

            patrol = trame['data']['patrol']

            codisDrone = droneIstic.NotreDrone("udpin:{}:{}" .format(config.drone_host, config.drone_port), False, 30)

            # codisDrone.change_patrol_mission(patrol_liste, patrol)
            codisDrone.change_mission(liste)

            # arm and take off the drone
            codisDrone.start()

            # the drone goes to the patrol zone via specifics points
            codisDrone.goto_patrol_zone()

            # the drone starts the patrol sequence
            # codisDrone.start_patrol()

            # the drone goes back to the launch position and take on
            codisDrone.stop()

            # we send END MISSION to socket server
            json_data = json.dumps({
                'type': 'mission_finished',
                'data': {'mission': 10, 'status': True, 'error': ""}
            })
            client.send(json_data.encode())
            break

client.close()
