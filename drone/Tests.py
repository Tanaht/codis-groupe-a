import unittest
from droneIstic import NotreDrone
from dronekit import LocationGlobal, Command
from pymavlink import mavutil
from config.Config import Config
from utils import get_distance_metres

class Tests(unittest.TestCase):

    def setUp(self):
        self.started = False
        self.config = Config()
        self.patrol = 0
        self.simulation = False
        self.notreDrone = NotreDrone("udpin:{}:{}" .format(self.config.drone_host, self.config.drone_port), False, 30)
        self.listePoints = [LocationGlobal(48.1148383, -1.6388297, 30),
                       LocationGlobal(48.1153379, -1.6391757, 30)]
        self.patrolListe = [LocationGlobal(48.1161849, -1.6390014, 30),
                       LocationGlobal(48.1164571, -1.6373706, 30),
                       LocationGlobal(48.1155689, -1.6360724, 30)]

    def tearDown(self):
        if(self.started):
            self.notreDrone.stop()

    # initialisation (prise en compte de la simulation)
    def test_init(self):
        if(self.simulation == False):
            assert self.notreDrone.sitl != None
        else:
            assert self.notreDrone.sitl == None

    # NotreDrone.arm_and_takeoff arme le drone
    def test_armed(self):
        self.notreDrone.arm_and_takeoff(10)
        assert self.notreDrone.vehicle.armed == True

    # NotreDrone.arm_and_takeoff place le drone a l'altitude voulue
    def test_takeOff(self):
        self.notreDrone.arm_and_takeoff(10)
        assert self.notreDrone.vehicle.location.global_relative_frame.alt >= 10 * 0.95

    # NotreDrone.change_mission affecte bien les points aux commandes du drone
    def test_change_mission1(self):
        self.notreDrone.change_mission(self.listePoints)
        commands = self.notreDrone.vehicle.commands
        assert commands[0].__getattribute__("x") == self.listePoints[0].lat

    def test_change_mission2(self):
        self.notreDrone.change_mission(self.listePoints)
        commands = self.notreDrone.vehicle.commands
        assert commands[len(commands)-2].__getattribute__("x") == self.listePoints[len(self.listePoints)-1].lat

    # Le dernier point est duplique
    def test_change_mission3(self):
        self.notreDrone.change_mission(self.listePoints)
        commands = self.notreDrone.vehicle.commands
        assert commands[len(commands)-1].__getattribute__("x") == self.listePoints[len(self.listePoints)-1].lat

    # Le type de patrouille est bien associe a la mission
    def test_change_patrol_mission1(self):
        self.notreDrone.change_patrol_mission(self.patrolListe, self.patrol)
        assert self.notreDrone.patrol == self.patrol;

    # Start mission du drone
    def test_startDrone1(self):
        self.notreDrone.change_mission(self.listePoints)
        self.notreDrone.change_patrol_mission(self.patrolListe, self.patrol)
        self.started = True
        self.notreDrone.start()
        assert self.notreDrone.vehicle.armed == True

    # Start mission du drone
    def test_startDrone2(self):
        self.notreDrone.change_mission(self.listePoints)
        self.notreDrone.change_patrol_mission(self.patrolListe, self.patrol)
        self.started = True
        self.notreDrone.start()
        assert self.notreDrone.vehicle.location.global_relative_frame.alt >= 10 * 0.95

    # Go to patrol zone
    def test_goToPatrolZone(self):
        self.notreDrone.change_mission(self.listePoints)
        self.notreDrone.change_patrol_mission(self.patrolListe, self.patrol)
        self.started = True
        self.notreDrone.start()
        self.notreDrone.goto_patrol_zone()
        assert (get_distance_metres(self.vehicle.location.global_frame, self.listePoints[len(self.listePoints-1)])) < 1

    # Apres le stop, le drone redescend a l'altitude 0
    def test_patrol(self):
        self.notreDrone.change_mission(self.listePoints)
        self.notreDrone.change_patrol_mission(self.patrolListe, self.patrol)
        self.started = True
        self.notreDrone.start()
        self.notreDrone.goto_patrol_zone()
        self.notreDrone.stop()
        assert self.notreDrone.vehicle.location.global_relative_frame.alt == 0

    # Apres le stop, le drone est bien desarme
    def test_patrol(self):
        self.notreDrone.change_mission(self.listePoints)
        self.notreDrone.change_patrol_mission(self.patrolListe, self.patrol)
        self.started = True
        self.notreDrone.start()
        self.notreDrone.goto_patrol_zone()
        self.notreDrone.stop()
        assert self.notreDrone.vehicle.armed == False
