# coding=utf-8
from __future__ import print_function
import droneIstic
from utils.SocketIstic import SocketIstic
from config.Config import Config
import threading
import time
import os


class Drone(threading.Thread):
    """Thread manage a simple lifecycle of a drone."""

    codisDrone = None
    turnOff = False

    def __init__(self, mission):
        super(Drone, self).__init__()
        self.mission = mission
        # self._stop_event = threading.Event()

    def stopped(self):
        return self._stop_event.is_set()

    def run(self):

        self.codisDrone = droneIstic.NotreDrone("udpin:{}:{}".format(config.drone_host, config.drone_port), False)

        while True:
            if not self.turnOff:
                if self.mission.patrol_liste > 0:
                    self.codisDrone.droneStopped = False

                    self.codisDrone.setAltitude(self.mission.altitude)
                    self.codisDrone.setIntervention(self.mission.interventionId)

                    self.codisDrone.change_patrol_mission(self.mission.patrol_liste, self.mission.patrol)
                    self.codisDrone.change_mission(self.mission.liste)

                    # arm and take off the drone
                    self.codisDrone.start()

                    # the drone goes to the patrol zone via specifics points
                    # if maMission.liste.count > 0:
                    #     self.codisDrone.goto_patrol_zone()

                    # the drone starts the patrol sequence
                    self.codisDrone.start_patrol()

                    # the drone goes back to the launch position and take on
                    self.codisDrone.stop()

                # we send END MISSION to socket server
                client.send_end_mission()

                self.codisDrone.droneStopped = True;

        # self._stop_event.set()

    def stop(self):
        # the drone goes back to the launch position and take on
        self._stop_event.set()

    def setTurnOff(self, value):
        self.turnOff = value
        self.codisDrone.turnoff(value)

    def setMissionAndTurnOn(self, mission):
        self.mission = mission
        self.setTurnOff(False)

    def getDroneStopped(self):
        return self.codisDrone.droneStopped


config = Config()
client = SocketIstic.get_socket()
drone_1 = None

while True:

    # Start Google Earth Application
    # os.startfile("")

    # wait something from the server socket
    ma_mission = client.wait_a_mission()

    if ma_mission == "STOP":
        print("STOP !!")
        if drone_1 is not None and drone_1.isAlive():
            drone_1.setTurnOff()
    elif ma_mission != "!" and ma_mission != "":
        print("New Mission !!")
        if drone_1 is None:
            drone_1 = Drone(ma_mission)
            drone_1.start()
        elif drone_1.isAlive():
            drone_1.setTurnOff(True)
            while not drone_1.getDroneStopped():
                time.sleep(1)
            drone_1.setMissionAndTurnOn(ma_mission)

    time.sleep(0.2)

client.close()


