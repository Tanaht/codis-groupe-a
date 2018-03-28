# coding=utf-8
from __future__ import print_function
import droneIstic
from utils.SocketIstic import SocketIstic
from config.Config import Config
import os

config = Config()
client = SocketIstic.get_socket()

while True:

    # Start Google Earth Application
    # os.startfile("")

    # wait something from the server socket
    ma_mission = client.wait_a_mission()

    codisDrone = droneIstic.NotreDrone("udpin:{}:{}" .format(config.drone_host, config.drone_port), False, ma_mission.altitude)

    codisDrone.change_patrol_mission(ma_mission.patrol_liste, ma_mission.patrol)
    codisDrone.change_mission(ma_mission.liste)

    # arm and take off the drone
    codisDrone.start()

    # the drone goes to the patrol zone via specifics points
    # if maMission.liste.count > 0:
    #     codisDrone.goto_patrol_zone()

    # the drone starts the patrol sequence
    codisDrone.start_patrol()

    # the drone goes back to the launch position and take on
    codisDrone.stop()

    # we send END MISSION to socket server
    client.send_end_mission()

client.close()


